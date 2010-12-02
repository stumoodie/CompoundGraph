package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphBuilder;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class SubCompoundGraphFactory implements ISubCompoundGraphFactory {
	private final ICompoundGraph graph;
	private final Set<ICompoundGraphElement> elements;
	private final ISubCompoundGraphBuilder builder;
	
	public SubCompoundGraphFactory(ICompoundGraph graph){
		this.graph = graph;
		this.builder = new SubCompoundGraphBuilder(this.graph);
		this.elements = new HashSet<ICompoundGraphElement>();
	}
	
	@Override
	public void addElement(ICompoundGraphElement element) {
		this.elements.add(element);
	}

	@Override
	public ISubCompoundGraph createInducedSubgraph() {
		builder.setElementList(this.elements);
		
		// because we are creating an induced graph we ignore any edges that are selected since if they are
		// not incident to the selected nodes they will result in a non-induced graph.
		builder.expandChildNodes();
		builder.removeNonIncidentEdges();
		builder.addIncidentEdges();
		builder.buildSubgraph();
		ISubCompoundGraph retVal = builder.getSubgraph();
		return retVal;
	}

	@Override
	public ISubCompoundGraph createPermissiveInducedSubgraph() {
		builder.setElementList(this.elements);
		builder.expandChildNodes();
		builder.addIncidentEdges();
		builder.buildSubgraph();
		ISubCompoundGraph retVal = builder.getSubgraph();
		return retVal;
	}

	@Override
	public ISubCompoundGraph createSubgraph() {
		builder.setElementList(this.elements);
		builder.expandChildNodes();
		builder.buildSubgraph();
		return builder.getSubgraph();
	}

	@Override
	public Iterator<ICompoundGraphElement> elementIterator() {
		return this.elements.iterator();
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.graph;
	}


	@Override
	public int numElements() {
		return this.elements.size();
	}

}
