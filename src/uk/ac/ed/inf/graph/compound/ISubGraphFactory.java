package uk.ac.ed.inf.graph.compound;

import java.util.Iterator;

public interface ISubGraphFactory {

	ICompoundGraph getGraph();
	
	ISubGraph createSubgraph();
	
	void addElement(ICompoundGraphElement element);
	
	int numElements();
	
	Iterator<ICompoundGraphElement> elementIterator();

	
}
