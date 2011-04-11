package uk.ac.ed.inf.graph.compound;


public interface IGraphStructureChangeListener {

	void graphStructureChange(IGraphStructureChangeAction e);

	void notifyRestoreCompleted(IGraphRestoreStateAction e);
}
