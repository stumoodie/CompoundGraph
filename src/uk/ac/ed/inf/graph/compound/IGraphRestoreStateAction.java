package uk.ac.ed.inf.graph.compound;


public interface IGraphRestoreStateAction {
	
	/**
	 * The compound graph upon which is the origin of this action.
	 * @return the source compound graph.
	 */
	ICompoundGraph getSource();
	
	/**
	 * This is the subgraph of elements that are removed from the graph by the restore operation.
	 * @return the subgraph of removed elements, which can be empty. 
	 */
	ISubCompoundGraph getRemovedElements();
	
	/**
	 * This is the subgraph of elements that are restored, i.e. added to the graph by the restore operation.
	 * @return the subgraph of restored elements, which can be empty. 
	 */
	ISubCompoundGraph getRestoredElements();
}
