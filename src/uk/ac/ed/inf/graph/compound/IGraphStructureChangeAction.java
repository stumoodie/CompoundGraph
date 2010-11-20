package uk.ac.ed.inf.graph.compound;


public interface IGraphStructureChangeAction {
	enum GraphStructureChangeType { ELEMENT_ADDED, SUBGRAPH_REMOVED, SUBGRAPH_MOVED, SUBGRAPH_COPIED };
	
	GraphStructureChangeType getChangeType();
	
	ISubCompoundGraph originalSubgraph();
	
	ISubCompoundGraph changedSubgraph();
}
