package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;

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
		return this.isValidNodePair(this.pair) && this.edgeAttribute != null
			&& this.edgeAttribute.canCreateAttribute();
	}

	@Override
	public ICompoundEdge createEdge() {
		IElementAttribute edgeAttribute = this.edgeAttribute.createAttribute();
		ICompoundEdge retVal = new CompoundEdge(getParent(), CompoundGraph.getIndexCounter(this.getGraph()).nextIndex(),
				edgeAttribute, this.pair.getOutNode(), this.pair.getInNode());
		getParent().getChildCompoundGraph().addEdge(retVal);
		return retVal;
	}

	@Override
	public CompoundNodePair getCurrentNodePair() {
		return this.pair;
	}

	@Override
	public boolean isValidNodePair(CompoundNodePair nodePair) {
		return nodePair != null && nodePair.getGraph().equals(this.getGraph());
	}

	@Override
	public void setPair(CompoundNodePair nodePair) {
		this.pair = nodePair;
	}
}
