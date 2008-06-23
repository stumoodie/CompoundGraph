package uk.ed.inf.graph.state;

public interface IRestorableGraphElement {

	/**
	 * Used to reset the removal status of a graph edge or node.
	 * @param setRemoved
	 */
	void markRemoved(boolean setRemoved);
	
}
