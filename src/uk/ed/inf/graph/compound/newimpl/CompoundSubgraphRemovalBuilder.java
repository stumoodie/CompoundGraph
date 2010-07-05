package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.graph.compound.ISubgraphRemovalBuilder;

public class CompoundSubgraphRemovalBuilder implements ISubgraphRemovalBuilder {
	private final ICompoundGraph owningGraph;
	private ISubCompoundGraph subCompoundGraph;
	
	protected CompoundSubgraphRemovalBuilder(ICompoundGraph owningGraph){
		this.owningGraph = owningGraph;
	}
	
	
	@Override
	public boolean canRemoveSubgraph() {
		return subCompoundGraph != null && subCompoundGraph.getSuperGraph().equals(this.owningGraph) && !subCompoundGraph.containsRoot() && subCompoundGraph.isConsistentSnapShot();
	}


	@Override
	public ISubCompoundGraph getRemovalSubgraph() {
		return this.subCompoundGraph;
	}

	@Override
	public void removeSubgraph(ISubCompoundGraph subgraph) {
		this.setRemovalSubgraph(subgraph);
		this.removeSubgraph();
	}

	private void removeElements(ISubCompoundGraphFactory selnFactory, Iterator<ICompoundGraphElement> elementIterator) {
		while(elementIterator.hasNext()){
			ICompoundGraphElement node = elementIterator.next();
			node.markRemoved(true);
			selnFactory.addElement(node);
		}
	}

	@Override
	public void setRemovalSubgraph(ISubCompoundGraph subgraph) {
		this.subCompoundGraph = subgraph;
	}

	@Override
	public ICompoundGraph getGraph(){
		return this.owningGraph;
	}


	@Override
	public void removeSubgraph() {
		ISubCompoundGraphFactory selnFactory = this.owningGraph.subgraphFactory();
		removeElements(selnFactory, this.subCompoundGraph.elementIterator());
	}
	
}
