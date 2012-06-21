// $ANTLR 3.4 /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g 2012-06-15 23:32:13

package uk.ac.ed.inf.graph.compound.fixturegen.output;


import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEarlyExitException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class CompoundGraphFixtureParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "EDGE", "EOL", "ESC_SEQ", "HEX_DIGIT", "ID", "NAME", "NODE", "OCTAL_ESC", "STRING", "UNICODE_ESC", "WS", "'('", "')'"
    };

    public static final int EOF=-1;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int COMMENT=4;
    public static final int EDGE=5;
    public static final int EOL=6;
    public static final int ESC_SEQ=7;
    public static final int HEX_DIGIT=8;
    public static final int ID=9;
    public static final int NAME=10;
    public static final int NODE=11;
    public static final int OCTAL_ESC=12;
    public static final int STRING=13;
    public static final int UNICODE_ESC=14;
    public static final int WS=15;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public CompoundGraphFixtureParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public CompoundGraphFixtureParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return CompoundGraphFixtureParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g"; }


    public static class fixture_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fixture"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:17:1: fixture : fixture_name defns EOF -> fixture_name defns ;
    public final CompoundGraphFixtureParser.fixture_return fixture() throws RecognitionException {
        CompoundGraphFixtureParser.fixture_return retval = new CompoundGraphFixtureParser.fixture_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token EOF3=null;
        CompoundGraphFixtureParser.fixture_name_return fixture_name1 =null;

        CompoundGraphFixtureParser.defns_return defns2 =null;


        Object EOF3_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_fixture_name=new RewriteRuleSubtreeStream(adaptor,"rule fixture_name");
        RewriteRuleSubtreeStream stream_defns=new RewriteRuleSubtreeStream(adaptor,"rule defns");
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:17:9: ( fixture_name defns EOF -> fixture_name defns )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:17:11: fixture_name defns EOF
            {
            pushFollow(FOLLOW_fixture_name_in_fixture48);
            fixture_name1=fixture_name();

            state._fsp--;

            stream_fixture_name.add(fixture_name1.getTree());

            pushFollow(FOLLOW_defns_in_fixture50);
            defns2=defns();

            state._fsp--;

            stream_defns.add(defns2.getTree());

            EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_fixture52);  
            stream_EOF.add(EOF3);


            // AST REWRITE
            // elements: defns, fixture_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 18:3: -> fixture_name defns
            {
                adaptor.addChild(root_0, stream_fixture_name.nextTree());

                adaptor.addChild(root_0, stream_defns.nextTree());

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fixture"


    public static class fixture_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fixture_name"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:21:1: fixture_name : NAME ID EOL -> ^( NAME ID ) ;
    public final CompoundGraphFixtureParser.fixture_name_return fixture_name() throws RecognitionException {
        CompoundGraphFixtureParser.fixture_name_return retval = new CompoundGraphFixtureParser.fixture_name_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token NAME4=null;
        Token ID5=null;
        Token EOL6=null;

        Object NAME4_tree=null;
        Object ID5_tree=null;
        Object EOL6_tree=null;
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleTokenStream stream_EOL=new RewriteRuleTokenStream(adaptor,"token EOL");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:22:2: ( NAME ID EOL -> ^( NAME ID ) )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:22:4: NAME ID EOL
            {
            NAME4=(Token)match(input,NAME,FOLLOW_NAME_in_fixture_name72);  
            stream_NAME.add(NAME4);


            ID5=(Token)match(input,ID,FOLLOW_ID_in_fixture_name74);  
            stream_ID.add(ID5);


            EOL6=(Token)match(input,EOL,FOLLOW_EOL_in_fixture_name76);  
            stream_EOL.add(EOL6);


            // AST REWRITE
            // elements: ID, NAME
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 23:3: -> ^( NAME ID )
            {
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:23:6: ^( NAME ID )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                stream_NAME.nextNode()
                , root_1);

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fixture_name"


    public static class defns_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "defns"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:26:1: defns : ( def )+ EOL -> ( def )+ ;
    public final CompoundGraphFixtureParser.defns_return defns() throws RecognitionException {
        CompoundGraphFixtureParser.defns_return retval = new CompoundGraphFixtureParser.defns_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token EOL8=null;
        CompoundGraphFixtureParser.def_return def7 =null;


        Object EOL8_tree=null;
        RewriteRuleTokenStream stream_EOL=new RewriteRuleTokenStream(adaptor,"token EOL");
        RewriteRuleSubtreeStream stream_def=new RewriteRuleSubtreeStream(adaptor,"rule def");
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:27:2: ( ( def )+ EOL -> ( def )+ )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:27:4: ( def )+ EOL
            {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:27:4: ( def )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==EDGE||LA1_0==NODE) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:27:4: def
            	    {
            	    pushFollow(FOLLOW_def_in_defns98);
            	    def7=def();

            	    state._fsp--;

            	    stream_def.add(def7.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            EOL8=(Token)match(input,EOL,FOLLOW_EOL_in_defns101);  
            stream_EOL.add(EOL8);


            // AST REWRITE
            // elements: def
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 28:3: -> ( def )+
            {
                if ( !(stream_def.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_def.hasNext() ) {
                    adaptor.addChild(root_0, stream_def.nextTree());

                }
                stream_def.reset();

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "defns"


    public static class def_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "def"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:31:1: def : ( node | edge );
    public final CompoundGraphFixtureParser.def_return def() throws RecognitionException {
        CompoundGraphFixtureParser.def_return retval = new CompoundGraphFixtureParser.def_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        CompoundGraphFixtureParser.node_return node9 =null;

        CompoundGraphFixtureParser.edge_return edge10 =null;



        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:31:5: ( node | edge )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NODE) ) {
                alt2=1;
            }
            else if ( (LA2_0==EDGE) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:31:7: node
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_node_in_def118);
                    node9=node();

                    state._fsp--;

                    adaptor.addChild(root_0, node9.getTree());

                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:32:4: edge
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_edge_in_def123);
                    edge10=edge();

                    state._fsp--;

                    adaptor.addChild(root_0, edge10.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "def"


    public static class node_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "node"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:36:1: node : ( NODE '(' ID ( STRING )? ( def )+ ')' -> ^( NODE ID STRING ( def )+ ) | NODE '(' ID ( STRING )? ')' -> ^( NODE ID STRING ) );
    public final CompoundGraphFixtureParser.node_return node() throws RecognitionException {
        CompoundGraphFixtureParser.node_return retval = new CompoundGraphFixtureParser.node_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token NODE11=null;
        Token char_literal12=null;
        Token ID13=null;
        Token STRING14=null;
        Token char_literal16=null;
        Token NODE17=null;
        Token char_literal18=null;
        Token ID19=null;
        Token STRING20=null;
        Token char_literal21=null;
        CompoundGraphFixtureParser.def_return def15 =null;


        Object NODE11_tree=null;
        Object char_literal12_tree=null;
        Object ID13_tree=null;
        Object STRING14_tree=null;
        Object char_literal16_tree=null;
        Object NODE17_tree=null;
        Object char_literal18_tree=null;
        Object ID19_tree=null;
        Object STRING20_tree=null;
        Object char_literal21_tree=null;
        RewriteRuleTokenStream stream_NODE=new RewriteRuleTokenStream(adaptor,"token NODE");
        RewriteRuleTokenStream stream_17=new RewriteRuleTokenStream(adaptor,"token 17");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_def=new RewriteRuleSubtreeStream(adaptor,"rule def");
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:36:6: ( NODE '(' ID ( STRING )? ( def )+ ')' -> ^( NODE ID STRING ( def )+ ) | NODE '(' ID ( STRING )? ')' -> ^( NODE ID STRING ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==NODE) ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==16) ) {
                    int LA6_2 = input.LA(3);

                    if ( (LA6_2==ID) ) {
                        switch ( input.LA(4) ) {
                        case STRING:
                            {
                            int LA6_4 = input.LA(5);

                            if ( (LA6_4==EDGE||LA6_4==NODE) ) {
                                alt6=1;
                            }
                            else if ( (LA6_4==17) ) {
                                alt6=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 6, 4, input);

                                throw nvae;

                            }
                            }
                            break;
                        case EDGE:
                        case NODE:
                            {
                            alt6=1;
                            }
                            break;
                        case 17:
                            {
                            alt6=2;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 6, 3, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 2, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:36:8: NODE '(' ID ( STRING )? ( def )+ ')'
                    {
                    NODE11=(Token)match(input,NODE,FOLLOW_NODE_in_node134);  
                    stream_NODE.add(NODE11);


                    char_literal12=(Token)match(input,16,FOLLOW_16_in_node136);  
                    stream_16.add(char_literal12);


                    ID13=(Token)match(input,ID,FOLLOW_ID_in_node138);  
                    stream_ID.add(ID13);


                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:36:20: ( STRING )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==STRING) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:36:20: STRING
                            {
                            STRING14=(Token)match(input,STRING,FOLLOW_STRING_in_node140);  
                            stream_STRING.add(STRING14);


                            }
                            break;

                    }


                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:36:28: ( def )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==EDGE||LA4_0==NODE) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:36:28: def
                    	    {
                    	    pushFollow(FOLLOW_def_in_node143);
                    	    def15=def();

                    	    state._fsp--;

                    	    stream_def.add(def15.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);


                    char_literal16=(Token)match(input,17,FOLLOW_17_in_node146);  
                    stream_17.add(char_literal16);


                    // AST REWRITE
                    // elements: NODE, def, STRING, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 37:3: -> ^( NODE ID STRING ( def )+ )
                    {
                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:37:6: ^( NODE ID STRING ( def )+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        stream_NODE.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_1, 
                        stream_STRING.nextNode()
                        );

                        if ( !(stream_def.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_def.hasNext() ) {
                            adaptor.addChild(root_1, stream_def.nextTree());

                        }
                        stream_def.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:38:4: NODE '(' ID ( STRING )? ')'
                    {
                    NODE17=(Token)match(input,NODE,FOLLOW_NODE_in_node167);  
                    stream_NODE.add(NODE17);


                    char_literal18=(Token)match(input,16,FOLLOW_16_in_node169);  
                    stream_16.add(char_literal18);


                    ID19=(Token)match(input,ID,FOLLOW_ID_in_node171);  
                    stream_ID.add(ID19);


                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:38:16: ( STRING )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==STRING) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:38:16: STRING
                            {
                            STRING20=(Token)match(input,STRING,FOLLOW_STRING_in_node173);  
                            stream_STRING.add(STRING20);


                            }
                            break;

                    }


                    char_literal21=(Token)match(input,17,FOLLOW_17_in_node176);  
                    stream_17.add(char_literal21);


                    // AST REWRITE
                    // elements: STRING, NODE, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 39:3: -> ^( NODE ID STRING )
                    {
                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:39:6: ^( NODE ID STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        stream_NODE.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_1, 
                        stream_STRING.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "node"


    public static class edge_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "edge"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:42:1: edge : ( EDGE '(' ID ( STRING )? ID ID ( def )+ ')' -> ^( EDGE ^( ID ( STRING )? ) ID ID ( def )+ ) | EDGE '(' ID ( STRING )? ID ID ')' -> ^( EDGE ^( ID ( STRING )? ) ID ID ) );
    public final CompoundGraphFixtureParser.edge_return edge() throws RecognitionException {
        CompoundGraphFixtureParser.edge_return retval = new CompoundGraphFixtureParser.edge_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token EDGE22=null;
        Token char_literal23=null;
        Token ID24=null;
        Token STRING25=null;
        Token ID26=null;
        Token ID27=null;
        Token char_literal29=null;
        Token EDGE30=null;
        Token char_literal31=null;
        Token ID32=null;
        Token STRING33=null;
        Token ID34=null;
        Token ID35=null;
        Token char_literal36=null;
        CompoundGraphFixtureParser.def_return def28 =null;


        Object EDGE22_tree=null;
        Object char_literal23_tree=null;
        Object ID24_tree=null;
        Object STRING25_tree=null;
        Object ID26_tree=null;
        Object ID27_tree=null;
        Object char_literal29_tree=null;
        Object EDGE30_tree=null;
        Object char_literal31_tree=null;
        Object ID32_tree=null;
        Object STRING33_tree=null;
        Object ID34_tree=null;
        Object ID35_tree=null;
        Object char_literal36_tree=null;
        RewriteRuleTokenStream stream_17=new RewriteRuleTokenStream(adaptor,"token 17");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
        RewriteRuleTokenStream stream_EDGE=new RewriteRuleTokenStream(adaptor,"token EDGE");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_def=new RewriteRuleSubtreeStream(adaptor,"rule def");
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:43:2: ( EDGE '(' ID ( STRING )? ID ID ( def )+ ')' -> ^( EDGE ^( ID ( STRING )? ) ID ID ( def )+ ) | EDGE '(' ID ( STRING )? ID ID ')' -> ^( EDGE ^( ID ( STRING )? ) ID ID ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==EDGE) ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==16) ) {
                    int LA10_2 = input.LA(3);

                    if ( (LA10_2==ID) ) {
                        int LA10_3 = input.LA(4);

                        if ( (LA10_3==STRING) ) {
                            int LA10_4 = input.LA(5);

                            if ( (LA10_4==ID) ) {
                                int LA10_5 = input.LA(6);

                                if ( (LA10_5==ID) ) {
                                    int LA10_6 = input.LA(7);

                                    if ( (LA10_6==17) ) {
                                        alt10=2;
                                    }
                                    else if ( (LA10_6==EDGE||LA10_6==NODE) ) {
                                        alt10=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 10, 6, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 10, 5, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 10, 4, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA10_3==ID) ) {
                            int LA10_5 = input.LA(5);

                            if ( (LA10_5==ID) ) {
                                int LA10_6 = input.LA(6);

                                if ( (LA10_6==17) ) {
                                    alt10=2;
                                }
                                else if ( (LA10_6==EDGE||LA10_6==NODE) ) {
                                    alt10=1;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 10, 6, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 10, 5, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 10, 3, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 2, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }
            switch (alt10) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:43:4: EDGE '(' ID ( STRING )? ID ID ( def )+ ')'
                    {
                    EDGE22=(Token)match(input,EDGE,FOLLOW_EDGE_in_edge199);  
                    stream_EDGE.add(EDGE22);


                    char_literal23=(Token)match(input,16,FOLLOW_16_in_edge201);  
                    stream_16.add(char_literal23);


                    ID24=(Token)match(input,ID,FOLLOW_ID_in_edge203);  
                    stream_ID.add(ID24);


                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:43:16: ( STRING )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==STRING) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:43:16: STRING
                            {
                            STRING25=(Token)match(input,STRING,FOLLOW_STRING_in_edge205);  
                            stream_STRING.add(STRING25);


                            }
                            break;

                    }


                    ID26=(Token)match(input,ID,FOLLOW_ID_in_edge208);  
                    stream_ID.add(ID26);


                    ID27=(Token)match(input,ID,FOLLOW_ID_in_edge210);  
                    stream_ID.add(ID27);


                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:43:30: ( def )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==EDGE||LA8_0==NODE) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:43:30: def
                    	    {
                    	    pushFollow(FOLLOW_def_in_edge212);
                    	    def28=def();

                    	    state._fsp--;

                    	    stream_def.add(def28.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);


                    char_literal29=(Token)match(input,17,FOLLOW_17_in_edge215);  
                    stream_17.add(char_literal29);


                    // AST REWRITE
                    // elements: ID, EDGE, STRING, ID, def, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 44:3: -> ^( EDGE ^( ID ( STRING )? ) ID ID ( def )+ )
                    {
                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:44:6: ^( EDGE ^( ID ( STRING )? ) ID ID ( def )+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        stream_EDGE.nextNode()
                        , root_1);

                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:44:13: ^( ID ( STRING )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(
                        stream_ID.nextNode()
                        , root_2);

                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:44:18: ( STRING )?
                        if ( stream_STRING.hasNext() ) {
                            adaptor.addChild(root_2, 
                            stream_STRING.nextNode()
                            );

                        }
                        stream_STRING.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        if ( !(stream_def.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_def.hasNext() ) {
                            adaptor.addChild(root_1, stream_def.nextTree());

                        }
                        stream_def.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:45:4: EDGE '(' ID ( STRING )? ID ID ')'
                    {
                    EDGE30=(Token)match(input,EDGE,FOLLOW_EDGE_in_edge243);  
                    stream_EDGE.add(EDGE30);


                    char_literal31=(Token)match(input,16,FOLLOW_16_in_edge245);  
                    stream_16.add(char_literal31);


                    ID32=(Token)match(input,ID,FOLLOW_ID_in_edge247);  
                    stream_ID.add(ID32);


                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:45:16: ( STRING )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==STRING) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:45:16: STRING
                            {
                            STRING33=(Token)match(input,STRING,FOLLOW_STRING_in_edge249);  
                            stream_STRING.add(STRING33);


                            }
                            break;

                    }


                    ID34=(Token)match(input,ID,FOLLOW_ID_in_edge252);  
                    stream_ID.add(ID34);


                    ID35=(Token)match(input,ID,FOLLOW_ID_in_edge254);  
                    stream_ID.add(ID35);


                    char_literal36=(Token)match(input,17,FOLLOW_17_in_edge256);  
                    stream_17.add(char_literal36);


                    // AST REWRITE
                    // elements: ID, STRING, ID, ID, EDGE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 46:3: -> ^( EDGE ^( ID ( STRING )? ) ID ID )
                    {
                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:46:6: ^( EDGE ^( ID ( STRING )? ) ID ID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        stream_EDGE.nextNode()
                        , root_1);

                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:46:13: ^( ID ( STRING )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(
                        stream_ID.nextNode()
                        , root_2);

                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:46:18: ( STRING )?
                        if ( stream_STRING.hasNext() ) {
                            adaptor.addChild(root_2, 
                            stream_STRING.nextNode()
                            );

                        }
                        stream_STRING.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "edge"

    // Delegated rules


 

    public static final BitSet FOLLOW_fixture_name_in_fixture48 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_defns_in_fixture50 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_fixture52 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_fixture_name72 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_fixture_name74 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EOL_in_fixture_name76 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_def_in_defns98 = new BitSet(new long[]{0x0000000000000860L});
    public static final BitSet FOLLOW_EOL_in_defns101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_node_in_def118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_edge_in_def123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NODE_in_node134 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_node136 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_node138 = new BitSet(new long[]{0x0000000000002820L});
    public static final BitSet FOLLOW_STRING_in_node140 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_def_in_node143 = new BitSet(new long[]{0x0000000000020820L});
    public static final BitSet FOLLOW_17_in_node146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NODE_in_node167 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_node169 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_node171 = new BitSet(new long[]{0x0000000000022000L});
    public static final BitSet FOLLOW_STRING_in_node173 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_node176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EDGE_in_edge199 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_edge201 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge203 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_STRING_in_edge205 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge208 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge210 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_def_in_edge212 = new BitSet(new long[]{0x0000000000020820L});
    public static final BitSet FOLLOW_17_in_edge215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EDGE_in_edge243 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_edge245 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge247 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_STRING_in_edge249 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge252 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge254 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_edge256 = new BitSet(new long[]{0x0000000000000002L});

}