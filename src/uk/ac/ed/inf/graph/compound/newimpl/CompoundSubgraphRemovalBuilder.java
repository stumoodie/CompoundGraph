package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ac.ed.inf.graph.compound.ISubgraphRemovalBuilder;

public class CompoundSubgraphRemovalBuilder implements ISubgraphRemovalBuilder {
	private final ICompoundGraph owningGraph;
	private ISubCompoundGraph subCompoundGraph;
	private ISubCompoundGraphFactory selnFactory;
	
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
	@Deprecated
	public void removeSubgraph(ISubCompoundGraph subgraph) {
		this.setRemovalSubgraph(subgraph);
		this.removeSubgraph();
	}

	private void removeElement(ICompoundGraphElement element){
		if(!element.isRemoved()){
			element.markRemoved(true);
			selnFactory.addElement(element);
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
		selnFactory = this.owningGraph.subgraphFactory();
		Iterator<ICompoundGraphElement> elementIterator = this.subCompoundGraph.elementIterator();
		while(elementIterator.hasNext()){
			ICompoundGraphElement element = elementIterator.next();
			if(element instanceof ICompoundNode){
				ICompoundNode node = (ICompoundNode)element;
				Iterator<ICompoundEdge> iter = node.edgeIterator();
				while(iter.hasNext()){
					ICompoundEdge edge = iter.next();
					removeElement(edge);
				}
			}
			removeElement(element);
		}
	}
	
}