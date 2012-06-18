package uk.ac.ed.inf.graph.compound.fixturegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.DOTTreeGenerator;
import org.antlr.runtime.tree.Tree;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;

import uk.ac.ed.inf.graph.compound.fixturegen.output.CompoundGraphFixtureLexer;
import uk.ac.ed.inf.graph.compound.fixturegen.output.CompoundGraphFixtureParser;
import uk.ac.ed.inf.graph.compound.fixturegen.output.CompoundGraphFixtureTree;

public class GraphFixtureGenerator {
	private static final String OUTPUT_OPTION = "output";
	private static final String PACKAGE_OPTION = "package";
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final int SUCCESS = 0;
	private static final int FAIL = 1;
	private static final List<String> HELP_OPTIONS = Arrays.asList("?", "h", "help");
	private static final String USAGE = "program <options> <input file>"; //$NON-NLS-1$
	private static final int NUM_EXPECTED_NONOPTIONS = 1;
	private final OptionParser cmdLineParser = new OptionParser();
	private final OptionSpec<Void> helpOption;
	private File inputFile;
//	private final boolean initialised;
	private boolean areParamatersValid;
	private boolean commandLineRead = false;
	private File outputFile;
	private String packageName;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphFixtureGenerator gen = new GraphFixtureGenerator();
		try {
			gen.processParameters(args);
			if(gen.areParametersValid()){
				gen.executeGenerator();
				System.exit(SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(FAIL);
		}
	}

	
	public GraphFixtureGenerator(){
		this.cmdLineParser.posixlyCorrect(true);
		this.cmdLineParser.accepts(PACKAGE_OPTION, "Name of package to use.").withRequiredArg();
		this.cmdLineParser.accepts(OUTPUT_OPTION, "Name of output directory to write file to.").withRequiredArg();
		this.helpOption = this.cmdLineParser.acceptsAll(HELP_OPTIONS, "Display command line usage");
	}
	
	private boolean areParametersValid() {
		return this.areParamatersValid;
	}


	private void processParameters(String[] args){
		try {
			readCommandLine(args);
			if (this.commandLineRead) {
				validateParameters();
			}
		} catch (RuntimeException e) {
			logger.fatal("Error: A bug has been detected.", e);
		} catch (Exception e) {
			logger.fatal("An error was detected", e);
		}
	}

	private void validateParameters() {
		this.setParamatersValid(true);
		if (this.inputFile != null && (!this.inputFile.isFile() || !this.inputFile.canRead())) {
			logger.fatal(this.inputFile + " is not a file or cannot be read");
			this.setParamatersValid(false);
		}
		else if (this.outputFile != null && (!this.outputFile.isDirectory() || !this.outputFile.canWrite())) {
			logger.fatal(this.outputFile + " is not a directory or cannot be written to.");
			this.setParamatersValid(false);
		}
		else if (this.packageName != null && this.packageName.length() == 0) {
			logger.fatal("The package name is not set");
			this.setParamatersValid(false);
		}
	}
	
	private void setParamatersValid(boolean areParamatersValid) {
		this.areParamatersValid = areParamatersValid;
	}

	private void readCommandLine(String[] args) {
		try {
			OptionSet options = cmdLineParser.parse(args);
			if (options.has(helpOption)) {
				try {
					System.out.println(USAGE);
					this.cmdLineParser.printHelpOn(System.out);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			else if(options.has(OUTPUT_OPTION) && options.has(PACKAGE_OPTION) && options.nonOptionArguments().size() == NUM_EXPECTED_NONOPTIONS){
				String fileName = (String)options.valueOf(OUTPUT_OPTION);
				this.outputFile = new File(fileName);
				this.commandLineRead  = true;
				this.packageName = (String)options.valueOf(PACKAGE_OPTION);
				this.commandLineRead  = true;
				this.inputFile = new File(options.nonOptionArguments().get(0));
				this.commandLineRead  = true;
			}
			else {
				System.err.println("Invalid arguments: type --help for correct usage."); //$NON-NLS-1$
			}
		} catch (OptionException e) {
			System.err.println(e.getMessage());
		}
	}

    private static void dumpTree(Object ast, File oFile) throws FileNotFoundException{
		DOTTreeGenerator dtg = new DOTTreeGenerator();
		StringTemplate st = dtg.toDOT((Tree)ast);
		PrintStream os = new PrintStream(new FileOutputStream(oFile)); 
		os.println(st);
		os.close();
    }
    
	public void executeGenerator() throws IOException, RecognitionException{
//		InputStream in = this.getClass().getResourceAsStream("testGraphDefn.graph"); //new FileInputStream("testGraphDefn.graph");
		InputStream in = new FileInputStream(this.inputFile);
		CompoundGraphFixtureLexer lexer = new CompoundGraphFixtureLexer(new ANTLRInputStream(in));
		CompoundGraphFixtureParser parser = new CompoundGraphFixtureParser(new CommonTokenStream(lexer));;
		
		CompoundGraphFixtureParser.fixture_return ast = parser.fixture();
		in.close();

		dumpTree(ast.getTree(), new File("treeGrammar.dot"));

//		CompoundGraphFixtureTree treeParser = new CompoundGraphFixtureTree(new CommonTreeNodeStream(ast.getTree()), 49000, new RecognizerSharedState());
		CompoundGraphFixtureTree treeParser = new CompoundGraphFixtureTree(new CommonTreeNodeStream(ast.getTree()), this.packageName);
		Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("CompoundGraphTemplate.stg"));
		treeParser.setTemplateLib(new StringTemplateGroup(reader));
		reader.close();
			
		CompoundGraphFixtureTree.fixture_return result = treeParser.fixture();
		String className = treeParser.getName();
		System.out.println(result.getTemplate());
		File oFileName = new File(this.outputFile, className + "GraphTestFixture" + ".java"); 
		PrintWriter fout = new PrintWriter(new FileOutputStream(oFileName));
		fout.print(result.getTemplate());
		fout.close();
	}
	
}
