package uk.ed.inf.graph.compound.newimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundGraphMoveBuilder implements ICompoundGraphMoveBuilder {
	private ISubCompoundGraph sourceSubCigraph;
	private final IChildCompoundGraph destSubCigraph;
	private ISubCompoundGraphFactory subGraphFactory;
	private final Map<ICompoundGraphElement, ICompoundGraphElement> oldNewEquivList;
	private ISubCompoundGraphFactory removalSubGraphFactory;

	public CompoundGraphMoveBuilder(IChildCompoundGraph destn){
		this.oldNewEquivList = new HashMap<ICompoundGraphElement, ICompoundGraphElement>();
		this.destSubCigraph = destn;
	}
	
	@Override
	public ISubCompoundGraph getMovedComponents() {
		return this.subGraphFactory.createSubgraph();
	}

	@Override
	public IChildCompoundGraph getDestinationChildGraph() {
		return this.destSubCigraph;
	}

	@Override
	public ISubCompoundGraph getSourceSubgraph() {
		return this.sourceSubCigraph;
	}

	@Override
	public boolean canMoveHere(ISubCompoundGraph subgraph) {
		this.setSourceSubgraph(subgraph);
		return this.canMoveHere();
	}
	
	@Override
	public boolean canMoveHere() {
		ISubCompoundGraph subGraph = this.sourceSubCigraph;
		boolean retVal = subGraph != null && subGraph.getSuperGraph().equals(this.destSubCigraph.getSuperGraph())
			&& subGraph.isInducedSubgraph() && subGraph.isConsistentSnapShot()
			&& !subGraph.containsElement(this.destSubCigraph.getRoot());
		if(retVal){
			retVal = false;
			Iterator<? extends ICompoundNode> topNodeIter = subGraph.topNodeIterator();
			while(topNodeIter.hasNext()){
				ICompoundNode topNode = topNodeIter.next();
				if(!topNode.getParent().equals(this.destSubCigraph.getRoot())){
					retVal = true;
				}
			}
		}
		return retVal;
	}

	@Override
	public void moveHere(ISubCompoundGraph subGraph) {
		this.setSourceSubgraph(subGraph);
		this.makeMove();
	}

	@Override
	public void makeMove() {
		this.oldNewEquivList.clear();
		this.subGraphFactory = this.destSubCigraph.getSuperGraph().subgraphFactory();
		moveElements();
		// avoid holding onto additional unneeded memory
		this.oldNewEquivList.clear();
	}

	private void moveElements(){
		initRootNodeEquivalent(); 
		Iterator<ICompoundGraphElement> sourceNodeIter = this.sourceSubCigraph.edgeLastElementIterator();
		while(sourceNodeIter.hasNext()){
			ICompoundGraphElement srcElement = sourceNodeIter.next();
			ICompoundGraphElement newElement = null;
			if(srcElement instanceof ICompoundNode){
				ICompoundGraphElement equivParent = this.oldNewEquivList.get(srcElement.getParent());
				newElement = moveNode((ICompoundNode)srcElement, equivParent);
			}
			else if(srcElement instanceof ICompoundEdge){
				ICompoundEdge srcEdge = (ICompoundEdge)srcElement;
				ICompoundGraphElement equivParent = this.oldNewEquivList.get(srcElement.getParent());
				ICompoundNode equivOutNode = (ICompoundNode)this.oldNewEquivList.get(srcEdge.getConnectedNodes().getOutNode());
				ICompoundNode equivInNode = (ICompoundNode)this.oldNewEquivList.get(srcEdge.getConnectedNodes().getInNode());
				newElement = moveEdge((ICompoundEdge)srcElement, equivParent, equivOutNode, equivInNode);
			}
			else{
				throw new RuntimeException("Unrecognised element type");
			}
			this.oldNewEquivList.put(srcElement, newElement);
			this.subGraphFactory.addElement(newElement);
		}
	}

	private void initRootNodeEquivalent() {
		Iterator<ICompoundGraphElement> topElements = this.sourceSubCigraph.elementIterator();
		while(topElements.hasNext()){
			ICompoundGraphElement topElement = topElements.next();
			this.oldNewEquivList.put(topElement.getParent(), this.destSubCigraph.getRoot());
		}
		
	}

	private ICompoundEdge moveEdge(ICompoundEdge srcEdge, ICompoundGraphElement parent, ICompoundNode outNode, ICompoundNode inNode) {
		ICompoundEdge retVal = srcEdge;
		if(!srcEdge.getParent().equals(parent)){
			ICompoundChildEdgeFactory edgefact = parent.getChildCompoundGraph().edgeFactory();
			edgefact.setPair(new CompoundNodePair(outNode, inNode));
			retVal = edgefact.createEdge();
			srcEdge.markRemoved(true);
			this.removalSubGraphFactory.addElement(srcEdge);
		}
		return retVal;
	}

	private ICompoundNode moveNode(ICompoundNode srcNode, ICompoundGraphElement destParentNode){
		ICompoundNode newNode = srcNode; 
		if(!srcNode.getParent().equals(destParentNode.getParent())){
			ICompoundNodeFactory fact = destParentNode.getChildCompoundGraph().nodeFactory();
			newNode = fact.createNode();
			srcNode.markRemoved(true);
			this.removalSubGraphFactory.addElement(srcNode);
		}
		return newNode;
	}

	@Override
	public void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph) {
		this.sourceSubCigraph = sourceSubCompoundGraph;
	}

	@Override
	public ISubCompoundGraph getRemovedComponents() {
		return this.removalSubGraphFactory.createSubgraph();
	}

}
