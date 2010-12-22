package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElementFactory;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.IGraphStructureChangeAction;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundGraphCopyBuilder implements ICompoundGraphCopyBuilder {
	private ISubCompoundGraph sourceSubgraph;
	private final IChildCompoundGraph destChildGraph;
	private ISubCompoundGraphFactory copiedElementSubgraphFactory;
	private final Map<ICompoundGraphElement, ICompoundGraphElement> oldNewEquivList;
	private ISubCompoundGraph copiedComponents;
//	private IElementAttributeCopyFactory attributeCopyFactory;
	private final ICompoundGraphElementFactory elementFactory;

	public CompoundGraphCopyBuilder(IChildCompoundGraph dest, ICompoundGraphElementFactory elementFactory){
		this.oldNewEquivList = new HashMap<ICompoundGraphElement, ICompoundGraphElement>();
		this.copiedComponents = null; 
		this.destChildGraph = dest;
		this.elementFactory = elementFactory;
	}
	
	@Override
	public ISubCompoundGraph getCopiedComponents() {
		return this.copiedComponents;
	}

	@Override
	public IChildCompoundGraph getDestinationChildGraph() {
		return this.destChildGraph;
	}

	@Override
	public ISubCompoundGraph getSourceSubgraph() {
		return this.sourceSubgraph;
	}

	@Override
	public boolean canCopyHere() {
		ISubCompoundGraph subGraph = this.sourceSubgraph;
		boolean retVal = subGraph != null && !subGraph.hasOrphanedEdges()
			&& subGraph.isConsistentSnapShot() && !subGraph.containsRoot();
//			&& this.attributeCopyFactory != null;
		if(retVal){
			// check that attributes can copy
//			this.attributeCopyFactory.setDestinationAttribute(this.destSubCigraph.getRoot().getAttribute());
			Iterator<ICompoundGraphElement> topElIter = subGraph.topElementIterator();
			while(retVal && topElIter.hasNext()){
				ICompoundGraphElement topElement = topElIter.next();
				IElementAttributeFactory attributeCopyFactory = topElement.getAttribute().elementAttributeCopyFactory();
				attributeCopyFactory.setDestinationAttribute(this.destChildGraph.getRoot().getAttribute());
//				this.attributeCopyFactory.setElementToCopy(topElement.getAttribute());
				retVal = attributeCopyFactory.canCreateAttribute();
			}
		}
		return retVal;
	}
	
	@Override
	public void makeCopy() {
		this.copiedComponents = null;
		this.oldNewEquivList.clear();
		this.copiedElementSubgraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
		copyNodes();
		// avoid holding onto additional unneeded memory
		this.oldNewEquivList.clear();
		this.copiedComponents = this.copiedElementSubgraphFactory.createSubgraph();
		ICompoundGraph graph = this.destChildGraph.getSuperGraph();
		graph.notifyGraphStructureChange(new IGraphStructureChangeAction(){

			@Override
			public GraphStructureChangeType getChangeType() {
				return GraphStructureChangeType.SUBGRAPH_COPIED;
			}

			@Override
			public ISubCompoundGraph originalSubgraph() {
				return sourceSubgraph;
			}

			@Override
			public ISubCompoundGraph changedSubgraph() {
				return copiedComponents;
			}
		});
	}

	private void copyNodes(){
		initRootNodeEquivalent(); 
		Iterator<ICompoundGraphElement> sourceNodeIter = this.sourceSubgraph.edgeLastElementIterator();
		while(sourceNodeIter.hasNext()){
			ICompoundGraphElement srcElement = sourceNodeIter.next();
			ICompoundGraphElement newElement = null;
			if(srcElement instanceof ICompoundNode){
				ICompoundGraphElement equivParent = this.oldNewEquivList.get(srcElement.getParent());
				newElement = copyNode((ICompoundNode)srcElement, equivParent);
			}
			else if(srcElement instanceof ICompoundEdge){
				ICompoundEdge srcEdge = (ICompoundEdge)srcElement;
				ICompoundGraphElement equivParent = this.oldNewEquivList.get(srcElement.getParent());
				CompoundNodePair srcNodePair = srcEdge.getConnectedNodes();
				ICompoundNode equivOutNode = (ICompoundNode)this.oldNewEquivList.get(srcNodePair.getOutNode());
				ICompoundNode equivInNode = (ICompoundNode)this.oldNewEquivList.get(srcNodePair.getInNode());
				newElement = copyEdge((ICompoundEdge)srcElement, equivParent, equivOutNode, equivInNode);
			}
			else{
				throw new RuntimeException("Unrecognised element type");
			}
			this.oldNewEquivList.put(srcElement, newElement);
			this.copiedElementSubgraphFactory.addElement(newElement);
		}
	}

	private void initRootNodeEquivalent() {
		Iterator<ICompoundGraphElement> topElements = this.sourceSubgraph.elementIterator();
		while(topElements.hasNext()){
			ICompoundGraphElement topElement = topElements.next();
			this.oldNewEquivList.put(topElement.getParent(), this.destChildGraph.getRoot());
		}
		
	}

	private ICompoundEdge copyEdge(ICompoundEdge srcEdge, ICompoundGraphElement parent, ICompoundNode outNode, ICompoundNode inNode) {
//		ICompoundChildEdgeFactory edgefact = parent.getChildCompoundGraph().edgeFactory();
//		this.attributeCopyFactory.setElementToCopy(srcEdge.getAttribute());
		IElementAttributeFactory attributeCopyFactory = srcEdge.getAttribute().elementAttributeCopyFactory();
		attributeCopyFactory.setDestinationAttribute(parent.getAttribute());
		attributeCopyFactory.setOutAttribute(outNode.getAttribute());
		attributeCopyFactory.setInAttribute(inNode.getAttribute());
		IElementAttribute edgeAttribute = attributeCopyFactory.createAttribute();
		int index = parent.getGraph().getIndexCounter().nextIndex();
		this.elementFactory.setParent(parent);
		this.elementFactory.setIndex(index);
		this.elementFactory.setAttribute(edgeAttribute);
		ICompoundEdge retVal = this.elementFactory.createEdge(outNode, inNode);
//		ICompoundEdge retVal = new CompoundEdge(parent, index, edgeAttribute, outNode, inNode);
		parent.getChildCompoundGraph().addEdge(retVal);
		return retVal;
//		edgefact.setAttributeFactory(attributeCopyFactory);
//		edgefact.setPair(new CompoundNodePair(outNode, inNode));
//		return edgefact.createEdge();
	}

	private ICompoundNode copyNode(ICompoundNode srcNode, ICompoundGraphElement destParentNode){
//		ICompoundNodeFactory fact = destParentNode.getChildCompoundGraph().nodeFactory();
		IElementAttributeFactory attributeCopyFactory = srcNode.getAttribute().elementAttributeCopyFactory();
		attributeCopyFactory.setDestinationAttribute(destParentNode.getAttribute());
//		this.attributeCopyFactory.setElementToCopy(srcNode.getAttribute());
//		fact.setAttributeFactory(attributeCopyFactory);
//		ICompoundNode newNode = fact.createNode();
		int nodeIndex = destParentNode.getGraph().getIndexCounter().nextIndex();
		IElementAttribute newAttribute = attributeCopyFactory.createAttribute();
		this.elementFactory.setIndex(nodeIndex);
		this.elementFactory.setParent(destParentNode);
		this.elementFactory.setAttribute(newAttribute);
		ICompoundNode retVal = this.elementFactory.createNode();
//		CompoundNode retVal = new CompoundNode(destParentNode, nodeIndex, newAttribute);
		destParentNode.getChildCompoundGraph().addNode(retVal);
		return retVal;
	}

	@Override
	public void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph) {
		this.sourceSubgraph = sourceSubCompoundGraph;
	}

//	@Override
//	public void setElementAttributeFactory(IElementAttributeCopyFactory elementAttributeFactory) {
//		this.attributeCopyFactory = elementAttributeFactory;
//	}

//	@Override
//	public IElementAttributeCopyFactory getElementAttributeFactory() {
//		return this.attributeCopyFactory;
//	}

}
