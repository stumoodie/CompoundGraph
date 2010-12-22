package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubGraph;
import uk.ac.ed.inf.graph.compound.ISubGraphFactory;

public class SubgraphFactory implements ISubGraphFactory {
	private final ICompoundGraph graph;
	private final Set<ICompoundGraphElement> elements;

	public SubgraphFactory(ICompoundGraph graph){
		this.graph = graph;
		this.elements = new TreeSet<ICompoundGraphElement>();
	}
	
	@Override
	public ICompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public ISubGraph createSubgraph() {
		SubGraph retVal = new SubGraph(this.graph);
		for(ICompoundGraphElement el : this.elements){
			retVal.addElement(el);
		}
		return retVal;
	}

	@Override
	public void addElement(ICompoundGraphElement element) {
		this.elements.add(element);
	}

	@Override
	public int numElements() {
		return this.elements.size();
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return this.elements.iterator();
	}

}
