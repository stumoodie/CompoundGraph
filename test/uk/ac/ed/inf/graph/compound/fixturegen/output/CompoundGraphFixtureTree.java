// $ANTLR 3.4 /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g 2012-06-18 14:26:40

package uk.ac.ed.inf.graph.compound.fixturegen.output;

import java.util.LinkedList;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import java.util.HashMap;
@SuppressWarnings({"all", "warnings", "unchecked"})
public class CompoundGraphFixtureTree extends TreeParser {
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
    public TreeParser[] getDelegates() {
        return new TreeParser[] {};
    }

    // delegators


    public CompoundGraphFixtureTree(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }
    public CompoundGraphFixtureTree(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected StringTemplateGroup templateLib =
  new StringTemplateGroup("CompoundGraphFixtureTreeTemplates", AngleBracketTemplateLexer.class);

public void setTemplateLib(StringTemplateGroup templateLib) {
  this.templateLib = templateLib;
}
public StringTemplateGroup getTemplateLib() {
  return templateLib;
}
/** allows convenient multi-value initialization:
 *  "new STAttrMap().put(...).put(...)"
 */
public static class STAttrMap extends HashMap {
  public STAttrMap put(String attrName, Object value) {
    super.put(attrName, value);
    return this;
  }
  public STAttrMap put(String attrName, int value) {
    super.put(attrName, new Integer(value));
    return this;
  }
}
    public String[] getTokenNames() { return CompoundGraphFixtureTree.tokenNames; }
    public String getGrammarFileName() { return "/Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g"; }


      private List<String> nodes = new LinkedList<String>();
      private List<String> edges = new LinkedList<String>();
      private String packageName;
      private String name;

      public CompoundGraphFixtureTree(TreeNodeStream s, String packageName){
        super(s);
        this.packageName = packageName;
      }


      public String getName(){
        return this.name;
      }


    public static class fixture_return extends TreeRuleReturnScope {
        public List<String> nodeList;
        public List<String> edgeList;
        public String packName;
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "fixture"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:33:1: fixture returns [List<String> nodeList, List<String> edgeList, String packName] : fn= fixture_name (n+= def[\"root\"] )+ -> fixture(package=$packNamename=$fn.nameValdefs=$nnodes=$nodeListedges=$edgeList);
    public final CompoundGraphFixtureTree.fixture_return fixture() throws RecognitionException {
        CompoundGraphFixtureTree.fixture_return retval = new CompoundGraphFixtureTree.fixture_return();
        retval.start = input.LT(1);


        List list_n=null;
        CompoundGraphFixtureTree.fixture_name_return fn =null;

        RuleReturnScope n = null;

          retval.packName = this.packageName;

        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:37:2: (fn= fixture_name (n+= def[\"root\"] )+ -> fixture(package=$packNamename=$fn.nameValdefs=$nnodes=$nodeListedges=$edgeList))
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:37:4: fn= fixture_name (n+= def[\"root\"] )+
            {
            pushFollow(FOLLOW_fixture_name_in_fixture62);
            fn=fixture_name();

            state._fsp--;


            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:37:21: (n+= def[\"root\"] )+
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
            	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:37:21: n+= def[\"root\"]
            	    {
            	    pushFollow(FOLLOW_def_in_fixture66);
            	    n=def("root");

            	    state._fsp--;

            	    if (list_n==null) list_n=new ArrayList();
            	    list_n.add(n.getTemplate());


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


             retval.nodeList = nodes; retval.edgeList = edges; 

            // TEMPLATE REWRITE
            // 38:3: -> fixture(package=$packNamename=$fn.nameValdefs=$nnodes=$nodeListedges=$edgeList)
            {
                retval.st = templateLib.getInstanceOf("fixture",new STAttrMap().put("package", retval.packName).put("name", (fn!=null?fn.nameVal:null)).put("defs", list_n).put("nodes", retval.nodeList).put("edges", retval.edgeList));
            }



            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fixture"


    public static class fixture_name_return extends TreeRuleReturnScope {
        public String nameVal;
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "fixture_name"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:41:1: fixture_name returns [String nameVal] : ^( NAME n= ID ) ;
    public final CompoundGraphFixtureTree.fixture_name_return fixture_name() throws RecognitionException {
        CompoundGraphFixtureTree.fixture_name_return retval = new CompoundGraphFixtureTree.fixture_name_return();
        retval.start = input.LT(1);


        CommonTree n=null;

        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:45:2: ( ^( NAME n= ID ) )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:45:4: ^( NAME n= ID )
            {
            match(input,NAME,FOLLOW_NAME_in_fixture_name121); 

            match(input, Token.DOWN, null); 
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_fixture_name125); 

            match(input, Token.UP, null); 


             retval.nameVal = (n!=null?n.getText():null); 

            }


              this.name = retval.nameVal;

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fixture_name"


    public static class def_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "def"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:49:1: def[String parentNode] : (n= node[$parentNode] -> { $n.st }|e= edge[$parentNode] -> { $e.st });
    public final CompoundGraphFixtureTree.def_return def(String parentNode) throws RecognitionException {
        CompoundGraphFixtureTree.def_return retval = new CompoundGraphFixtureTree.def_return();
        retval.start = input.LT(1);


        CompoundGraphFixtureTree.node_return n =null;

        CompoundGraphFixtureTree.edge_return e =null;


        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:50:2: (n= node[$parentNode] -> { $n.st }|e= edge[$parentNode] -> { $e.st })
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
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:50:4: n= node[$parentNode]
                    {
                    pushFollow(FOLLOW_node_in_def146);
                    n=node(parentNode);

                    state._fsp--;


                    // TEMPLATE REWRITE
                    // 51:3: -> { $n.st }
                    {
                        retval.st =  (n!=null?n.st:null) ;
                    }



                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:52:4: e= edge[$parentNode]
                    {
                    pushFollow(FOLLOW_edge_in_def160);
                    e=edge(parentNode);

                    state._fsp--;


                    // TEMPLATE REWRITE
                    // 53:3: -> { $e.st }
                    {
                        retval.st =  (e!=null?e.st:null) ;
                    }



                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "def"


    public static class node_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "node"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:57:1: node[String parentNode] : ( ^( NODE i= ID n= STRING (d+= def[$i.text] )+ ) -> branch_node(id=$i.textparent=$parentNodename=$n.textdefs=$d)| ^( NODE i= ID n= STRING ) -> leaf_node(id=$i.textparent=$parentNodename=$n.text));
    public final CompoundGraphFixtureTree.node_return node(String parentNode) throws RecognitionException {
        CompoundGraphFixtureTree.node_return retval = new CompoundGraphFixtureTree.node_return();
        retval.start = input.LT(1);


        CommonTree i=null;
        CommonTree n=null;
        List list_d=null;
        RuleReturnScope d = null;
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:58:2: ( ^( NODE i= ID n= STRING (d+= def[$i.text] )+ ) -> branch_node(id=$i.textparent=$parentNodename=$n.textdefs=$d)| ^( NODE i= ID n= STRING ) -> leaf_node(id=$i.textparent=$parentNodename=$n.text))
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==NODE) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==DOWN) ) {
                    int LA4_2 = input.LA(3);

                    if ( (LA4_2==ID) ) {
                        int LA4_3 = input.LA(4);

                        if ( (LA4_3==STRING) ) {
                            int LA4_4 = input.LA(5);

                            if ( (LA4_4==UP) ) {
                                alt4=2;
                            }
                            else if ( (LA4_4==EDGE||LA4_4==NODE) ) {
                                alt4=1;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 4, 4, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 4, 3, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 2, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:58:4: ^( NODE i= ID n= STRING (d+= def[$i.text] )+ )
                    {
                    match(input,NODE,FOLLOW_NODE_in_node182); 

                    match(input, Token.DOWN, null); 
                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_node186); 

                    n=(CommonTree)match(input,STRING,FOLLOW_STRING_in_node190); 

                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:58:26: (d+= def[$i.text] )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==EDGE||LA3_0==NODE) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:58:26: d+= def[$i.text]
                    	    {
                    	    pushFollow(FOLLOW_def_in_node194);
                    	    d=def((i!=null?i.getText():null));

                    	    state._fsp--;

                    	    if (list_d==null) list_d=new ArrayList();
                    	    list_d.add(d.getTemplate());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);


                    match(input, Token.UP, null); 


                     nodes.add((i!=null?i.getText():null)); 

                    // TEMPLATE REWRITE
                    // 59:3: -> branch_node(id=$i.textparent=$parentNodename=$n.textdefs=$d)
                    {
                        retval.st = templateLib.getInstanceOf("branch_node",new STAttrMap().put("id", (i!=null?i.getText():null)).put("parent", parentNode).put("name", (n!=null?n.getText():null)).put("defs", list_d));
                    }



                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:60:4: ^( NODE i= ID n= STRING )
                    {
                    match(input,NODE,FOLLOW_NODE_in_node232); 

                    match(input, Token.DOWN, null); 
                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_node236); 

                    n=(CommonTree)match(input,STRING,FOLLOW_STRING_in_node240); 

                    match(input, Token.UP, null); 


                     nodes.add((i!=null?i.getText():null)); 

                    // TEMPLATE REWRITE
                    // 61:3: -> leaf_node(id=$i.textparent=$parentNodename=$n.text)
                    {
                        retval.st = templateLib.getInstanceOf("leaf_node",new STAttrMap().put("id", (i!=null?i.getText():null)).put("parent", parentNode).put("name", (n!=null?n.getText():null)));
                    }



                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "node"


    public static class edge_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "edge"
    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:64:1: edge[String parentNode] : ( ^( EDGE ^(e= ID (n= STRING )? ) o= ID i= ID (d+= def[$e.text] )+ ) -> branch_edge(id=$e.textname=$n.textout=$o.textin=$i.textparent=$parentNodedefs=$d)| ^( EDGE ^(e= ID (n= STRING )? ) o= ID i= ID ) -> leaf_edge(id=$e.textname=$n.textout=$o.textin=$i.textparent=$parentNode));
    public final CompoundGraphFixtureTree.edge_return edge(String parentNode) throws RecognitionException {
        CompoundGraphFixtureTree.edge_return retval = new CompoundGraphFixtureTree.edge_return();
        retval.start = input.LT(1);


        CommonTree e=null;
        CommonTree n=null;
        CommonTree o=null;
        CommonTree i=null;
        List list_d=null;
        RuleReturnScope d = null;
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:65:2: ( ^( EDGE ^(e= ID (n= STRING )? ) o= ID i= ID (d+= def[$e.text] )+ ) -> branch_edge(id=$e.textname=$n.textout=$o.textin=$i.textparent=$parentNodedefs=$d)| ^( EDGE ^(e= ID (n= STRING )? ) o= ID i= ID ) -> leaf_edge(id=$e.textname=$n.textout=$o.textin=$i.textparent=$parentNode))
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==EDGE) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==DOWN) ) {
                    int LA8_2 = input.LA(3);

                    if ( (LA8_2==ID) ) {
                        int LA8_3 = input.LA(4);

                        if ( (LA8_3==DOWN) ) {
                            int LA8_4 = input.LA(5);

                            if ( (LA8_4==STRING) ) {
                                int LA8_5 = input.LA(6);

                                if ( (LA8_5==UP) ) {
                                    int LA8_6 = input.LA(7);

                                    if ( (LA8_6==ID) ) {
                                        int LA8_7 = input.LA(8);

                                        if ( (LA8_7==ID) ) {
                                            int LA8_8 = input.LA(9);

                                            if ( (LA8_8==UP) ) {
                                                alt8=2;
                                            }
                                            else if ( (LA8_8==EDGE||LA8_8==NODE) ) {
                                                alt8=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 8, 8, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 8, 7, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 8, 6, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 8, 5, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA8_4==UP) ) {
                                int LA8_6 = input.LA(6);

                                if ( (LA8_6==ID) ) {
                                    int LA8_7 = input.LA(7);

                                    if ( (LA8_7==ID) ) {
                                        int LA8_8 = input.LA(8);

                                        if ( (LA8_8==UP) ) {
                                            alt8=2;
                                        }
                                        else if ( (LA8_8==EDGE||LA8_8==NODE) ) {
                                            alt8=1;
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 8, 8, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 8, 7, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 8, 6, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 8, 4, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 8, 3, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 2, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }
            switch (alt8) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:65:4: ^( EDGE ^(e= ID (n= STRING )? ) o= ID i= ID (d+= def[$e.text] )+ )
                    {
                    match(input,EDGE,FOLLOW_EDGE_in_edge279); 

                    match(input, Token.DOWN, null); 
                    e=(CommonTree)match(input,ID,FOLLOW_ID_in_edge284); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:65:19: (n= STRING )?
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==STRING) ) {
                            alt5=1;
                        }
                        switch (alt5) {
                            case 1 :
                                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:65:19: n= STRING
                                {
                                n=(CommonTree)match(input,STRING,FOLLOW_STRING_in_edge288); 

                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }


                    o=(CommonTree)match(input,ID,FOLLOW_ID_in_edge294); 

                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_edge298); 

                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:65:41: (d+= def[$e.text] )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==EDGE||LA6_0==NODE) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:65:41: d+= def[$e.text]
                    	    {
                    	    pushFollow(FOLLOW_def_in_edge303);
                    	    d=def((e!=null?e.getText():null));

                    	    state._fsp--;

                    	    if (list_d==null) list_d=new ArrayList();
                    	    list_d.add(d.getTemplate());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);


                    match(input, Token.UP, null); 


                     edges.add((e!=null?e.getText():null)); 

                    // TEMPLATE REWRITE
                    // 66:3: -> branch_edge(id=$e.textname=$n.textout=$o.textin=$i.textparent=$parentNodedefs=$d)
                    {
                        retval.st = templateLib.getInstanceOf("branch_edge",new STAttrMap().put("id", (e!=null?e.getText():null)).put("name", (n!=null?n.getText():null)).put("out", (o!=null?o.getText():null)).put("in", (i!=null?i.getText():null)).put("parent", parentNode).put("defs", list_d));
                    }



                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:67:4: ^( EDGE ^(e= ID (n= STRING )? ) o= ID i= ID )
                    {
                    match(input,EDGE,FOLLOW_EDGE_in_edge351); 

                    match(input, Token.DOWN, null); 
                    e=(CommonTree)match(input,ID,FOLLOW_ID_in_edge356); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:67:19: (n= STRING )?
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==STRING) ) {
                            alt7=1;
                        }
                        switch (alt7) {
                            case 1 :
                                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixtureTree.g:67:19: n= STRING
                                {
                                n=(CommonTree)match(input,STRING,FOLLOW_STRING_in_edge360); 

                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }


                    o=(CommonTree)match(input,ID,FOLLOW_ID_in_edge366); 

                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_edge370); 

                    match(input, Token.UP, null); 


                     edges.add((e!=null?e.getText():null)); 

                    // TEMPLATE REWRITE
                    // 68:3: -> leaf_edge(id=$e.textname=$n.textout=$o.textin=$i.textparent=$parentNode)
                    {
                        retval.st = templateLib.getInstanceOf("leaf_edge",new STAttrMap().put("id", (e!=null?e.getText():null)).put("name", (n!=null?n.getText():null)).put("out", (o!=null?o.getText():null)).put("in", (i!=null?i.getText():null)).put("parent", parentNode));
                    }



                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "edge"

    // Delegated rules


 

    public static final BitSet FOLLOW_fixture_name_in_fixture62 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_def_in_fixture66 = new BitSet(new long[]{0x0000000000000822L});
    public static final BitSet FOLLOW_NAME_in_fixture_name121 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_fixture_name125 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_node_in_def146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_edge_in_def160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NODE_in_node182 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_node186 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_STRING_in_node190 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_def_in_node194 = new BitSet(new long[]{0x0000000000000828L});
    public static final BitSet FOLLOW_NODE_in_node232 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_node236 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_STRING_in_node240 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EDGE_in_edge279 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_edge284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_edge288 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_edge294 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge298 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_def_in_edge303 = new BitSet(new long[]{0x0000000000000828L});
    public static final BitSet FOLLOW_EDGE_in_edge351 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_edge356 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_edge360 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_edge366 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_edge370 = new BitSet(new long[]{0x0000000000000008L});

}