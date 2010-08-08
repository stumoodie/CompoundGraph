package uk.ac.ed.inf.graph.compound;

public interface IRootCompoundNode extends ICompoundNode {

	@Override
	IRootChildCompoundGraph getChildCompoundGraph();
	
}
