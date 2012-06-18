tree grammar CompoundGraphFixtureTree;

options{
	output=template;
	language=Java;
	ASTLabelType=CommonTree;
	tokenVocab=CompoundGraphFixture;
}

@header{
package uk.ac.ed.inf.graph.compound.fixturegen.output;

import java.util.LinkedList;
}

@members{
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
}

fixture returns [List<String> nodeList, List<String> edgeList, String packName]
@init{
  $packName = this.packageName;
}
	:	fn=fixture_name n+=def["root"]+ { $nodeList = nodes; $edgeList = edges; }
		-> fixture(package={$packName}, name={$fn.nameVal},defs={$n}, nodes={$nodeList}, edges={$edgeList})
	;
	
fixture_name returns [String nameVal]
@after{
  this.name = $nameVal;
}
	:	^(NAME n=ID)
		{ $nameVal = $n.text; }
	;
	
def [String parentNode]
	:	n=node[$parentNode]
		-> { $n.st }
	|	e=edge[$parentNode]
		-> { $e.st }
	;


node [String parentNode]
	:	^(NODE i=ID n=STRING d+=def[$i.text]+ ) { nodes.add($i.text); }
		-> branch_node(id={$i.text}, parent={$parentNode}, name={$n.text}, defs={$d})
	|	^(NODE i=ID n=STRING)  { nodes.add($i.text); }
		-> leaf_node(id={$i.text}, parent={$parentNode}, name={$n.text})
	;

edge [String parentNode]
	:	^(EDGE ^(e=ID n=STRING?) o=ID i=ID  d+=def[$e.text]+)  { edges.add($e.text); }
		-> branch_edge(id={$e.text}, name={$n.text}, out={$o.text}, in={$i.text}, parent={$parentNode}, defs={$d})
	|	^(EDGE ^(e=ID n=STRING?) o=ID i=ID)  { edges.add($e.text); }
		-> leaf_edge(id={$e.text}, name={$n.text}, out={$o.text}, in={$i.text}, parent={$parentNode})
	;
