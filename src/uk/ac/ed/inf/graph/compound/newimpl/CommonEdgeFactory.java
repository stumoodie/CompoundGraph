package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public abstract class CommonEdgeFactory implements ICompoundEdgeFactory {
	private CompoundNodePair pair;
	private IElementAttributeFactory edgeAttribute;
	
	protected CommonEdgeFactory(){
	}
	
	@Override
	public void setAttributeFactory(IElementAttributeFactory attribute){
		this.edgeAttribute = attribute;
	}
	
	@Override
	public IElementAttributeFactory getAttributeFactory(){
		return this.edgeAttribute;
	}
	
	@Override
	public boolean canCreateEdge() {
		boolean retVal = false;
		if(this.isValidNodePair(this.pair) && this.edgeAttribute != null){
			this.edgeAttribute.setDestinationAttribute(getParent().getAttribute());
			this.edgeAttribute.setInAttribute(this.pair.getInNode().getAttribute());
			this.edgeAttribute.setOutAttribute(this.pair.getOutNode().getAttribute());
			retVal = this.edgeAttribute.canCreateAttribute();
		}
		return retVal; 
	}

	private void notifyEdgeCreated(ICompoundEdge retVal){
		final ISubCompoundGraphFactory subgraphFact = this.getGraph().subgraphFactory();
		subgraphFact.addElement(retVal);
		ICompoundGraph graph = this.getGraph();
		graph.notifyGraphStructureChange(new IGraphStructureChangeAction(){

			@Override
			public GraphStructureChangeType getChangeType() {
				return GraphStructureChangeType.ELEMENT_ADDED;
			}

			@Override
			public ISubCompoundGraph originalSubgraph() {
				return null;
			}

			@Override
			public ISubCompoundGraph changedSubgraph() {
				return subgraphFact.createSubgraph();
			}
		});

	}
	
	@Override
	public ICompoundEdge createEdge() {
		this.edgeAttribute.setDestinationAttribute(getParent().getAttribute());
		this.edgeAttribute.setOutAttribute(pair.getOutNode().getAttribute());
		this.edgeAttribute.setInAttribute(pair.getInNode().getAttribute());
		IElementAttribute edgeAttribute = this.edgeAttribute.createAttribute();
		ICompoundEdge retVal = new CompoundEdge(getParent(), this.getGraph().getIndexCounter().nextIndex(),
				edgeAttribute, this.pair.getOutNode(), this.pair.getInNode());
		getParent().getChildCompoundGraph().addEdge(retVal);
		notifyEdgeCreated(retVal);
		return retVal;
	}

	@Override
	public CompoundNodePair getCurrentNodePair() {
		return this.pair;
	}

	@Override
	public boolean isValidNodePair(CompoundNodePair nodePair) {
		return nodePair != null
			&& nodePair.getGraph().equals(this.getGraph())
			&& isParentLowestCommonAncestor(nodePair)
			&& !nodePair.getOutNode().isAncestor(nodePair.getInNode())
			&& !nodePair.getInNode().isAncestor(nodePair.getOutNode());
	}

	protected abstract boolean isParentLowestCommonAncestor(CompoundNodePair nodePair);
	
	@Override
	public void setPair(CompoundNodePair nodePair) {
		this.pair = nodePair;
	}
}
