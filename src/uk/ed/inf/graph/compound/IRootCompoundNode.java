package uk.ed.inf.graph.compound;

public interface IRootCompoundNode extends ICompoundNode {

	@Override
	IRootChildCompoundGraph getChildCompoundGraph();
	
}
