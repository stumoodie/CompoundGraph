package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundNodeFactory implements ICompoundNodeFactory {
	private final ICompoundGraphElement parent;
	private IElementAttributeFactory attributeFactory; 

	public CompoundNodeFactory(ICompoundGraphElement parent){
		this.parent = parent;
	}
	
	private void notifyNodeCreated(ICompoundNode retVal){
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
	public ICompoundNode createNode() {
		int nodeIndex = CompoundGraph.getIndexCounter(this.getGraph()).nextIndex();
		attributeFactory.setDestinationAttribute(this.parent.getAttribute());
		IElementAttribute newAttribute = this.attributeFactory.createAttribute();
		CompoundNode retVal = new CompoundNode(parent, nodeIndex, newAttribute);
		parent.getChildCompoundGraph().addNode(retVal);
		notifyNodeCreated(retVal);
		return retVal;
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.parent.getGraph();
	}

	@Override
	public ICompoundGraphElement getParentNode() {
		return this.parent;
	}

	@Override
	public boolean canCreateNode() {
		boolean retVal = false;
		if(this.attributeFactory != null){
			this.attributeFactory.setDestinationAttribute(this.parent.getAttribute());
			retVal = this.attributeFactory.canCreateAttribute(); 
		}
		return retVal;
	}

	@Override
	public void setAttributeFactory(IElementAttributeFactory attribute) {
		this.attributeFactory = attribute;
	}

	@Override
	public IElementAttributeFactory getAttributeFactory() {
		return this.attributeFactory;
	}

}
