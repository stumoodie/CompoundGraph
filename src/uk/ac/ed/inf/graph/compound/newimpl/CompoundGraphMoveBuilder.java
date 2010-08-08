package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundGraphMoveBuilder implements ICompoundGraphMoveBuilder {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ISubCompoundGraph sourceSubCigraph;
	private final IChildCompoundGraph destChildGraph;
	private ISubCompoundGraphFactory subGraphFactory;
	private final Map<ICompoundGraphElement, ICompoundGraphElement> oldNewEquivList;
	private ISubCompoundGraphFactory removalSubGraphFactory;

	public CompoundGraphMoveBuilder(IChildCompoundGraph destn){
		this.oldNewEquivList = new HashMap<ICompoundGraphElement, ICompoundGraphElement>();
		this.destChildGraph = destn;
		this.subGraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
		this.removalSubGraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
	}
	
	@Override
	public ISubCompoundGraph getMovedComponents() {
		return this.subGraphFactory.createSubgraph();
	}

	@Override
	public IChildCompoundGraph getDestinationChildGraph() {
		return this.destChildGraph;
	}

	@Override
	public ISubCompoundGraph getSourceSubgraph() {
		return this.sourceSubCigraph;
	}

	@Override
	@Deprecated
	public boolean canMoveHere(ISubCompoundGraph subgraph) {
		this.setSourceSubgraph(subgraph);
		return this.canMoveHere();
	}
	
	@Override
	public boolean canMoveHere() {
		ISubCompoundGraph subGraph = this.sourceSubCigraph;
		boolean retVal = subGraph != null
			&& subGraph.getSuperGraph().equals(this.destChildGraph.getSuperGraph())
			&& subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot()
			&& !subGraph.containsRoot();
		if(retVal){
			boolean isDestnChildOfTopNode = false;
			int numTopElsWithDifferntParents = subGraph.numTopElements();
			Iterator<ICompoundGraphElement> topNodeIter = subGraph.topElementIterator();
			while(topNodeIter.hasNext() && !isDestnChildOfTopNode){
				ICompoundGraphElement topNode = topNodeIter.next();
				ICompoundGraphElement destn = this.destChildGraph.getRoot();
				if(topNode.getParent().equals(destn)){
					numTopElsWithDifferntParents--;
				}
				isDestnChildOfTopNode = topNode.isChild(destn);
			}
			retVal = !isDestnChildOfTopNode && numTopElsWithDifferntParents > 0;
		}
		if(retVal){
			// now check if has any incident nodes in the subgraph as these will violate the compound graph structure
			Iterator<ICompoundNode> incidentNodeIterator = ((ICompoundNode)this.destChildGraph.getRoot()).connectedNodeIterator();
			while(incidentNodeIterator.hasNext() && retVal){
				ICompoundNode incidentNode = incidentNodeIterator.next();
				retVal = !subGraph.containsNode(incidentNode);
			}
		}
		return retVal;
	}

	@Override
	@Deprecated
	public void moveHere(ISubCompoundGraph subGraph) {
		this.setSourceSubgraph(subGraph);
		this.makeMove();
	}

	@Override
	public void makeMove() {
		this.oldNewEquivList.clear();
		this.subGraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
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
			ICompoundGraphElement equivParent = this.oldNewEquivList.get(srcElement.getParent());
			if(srcElement instanceof ICompoundNode){
				if(logger.isTraceEnabled()){
					logger.trace("Creating node src=" + srcElement + ", parent=" + equivParent);
				}
				if(!srcElement.getParent().equals(equivParent)){
					newElement = moveNode((ICompoundNode)srcElement, equivParent);
				}
				else{
					newElement = srcElement;
				}
			}
			else if(srcElement instanceof ICompoundEdge){
				ICompoundEdge srcEdge = (ICompoundEdge)srcElement;
				CompoundNodePair srcNodePair = srcEdge.getConnectedNodes();
				ICompoundNode equivOutNode = (ICompoundNode)this.oldNewEquivList.get(srcNodePair.getOutNode());
				ICompoundNode equivInNode = (ICompoundNode)this.oldNewEquivList.get(srcNodePair.getInNode());
				if(logger.isTraceEnabled()){
					logger.trace("Creating edge src=" + srcElement + ", parent=" + equivParent +", outNode=" + equivOutNode + ", inNode=" + equivInNode);
				}
				// test if edge connections and parent are unchanged. If so leave edge alone. otherwise create a new edge
				if(!(equivInNode.equals(srcNodePair.getInNode()) && equivOutNode.equals(srcNodePair.getOutNode()) && equivParent.equals(srcElement.getParent()))){
					newElement = moveEdge((ICompoundEdge)srcElement, equivParent, equivOutNode, equivInNode);
				}
				else{
					newElement = srcElement;
				}
			}
			else{
				throw new RuntimeException("Unrecognised element type");
			}
			if(logger.isTraceEnabled()){
				logger.trace("Moving src=" + srcElement + ", tgt=" + newElement);
			}
			this.oldNewEquivList.put(srcElement, newElement);
			this.subGraphFactory.addElement(newElement);
		}
	}

	private void initRootNodeEquivalent() {
		Iterator<ICompoundGraphElement> topElements = this.sourceSubCigraph.topElementIterator();
		while(topElements.hasNext()){
			ICompoundGraphElement topElement = topElements.next();
			ICompoundGraphElement topParent = topElement.getParent();
			ICompoundGraphElement destElement = this.destChildGraph.getRoot(); 
			if(logger.isTraceEnabled()){
				logger.trace("topElement=" + topElement + ", topParent=" + topParent +", destRoot=" + destElement);
			}
			this.oldNewEquivList.put(topParent, destElement);
		}
		
	}

	private ICompoundEdge moveEdge(ICompoundEdge srcEdge, ICompoundGraphElement parent, ICompoundNode outNode, ICompoundNode inNode) {
		ICompoundEdge retVal = srcEdge;
		ICompoundChildEdgeFactory edgefact = parent.getChildCompoundGraph().edgeFactory();
		edgefact.setPair(new CompoundNodePair(outNode, inNode));
		retVal = edgefact.createEdge();
		srcEdge.markRemoved(true);
		this.removalSubGraphFactory.addElement(srcEdge);
		return retVal;
	}

	private ICompoundNode moveNode(ICompoundNode srcNode, ICompoundGraphElement destParentNode){
		ICompoundNode newNode = srcNode; 
		ICompoundNodeFactory fact = destParentNode.getChildCompoundGraph().nodeFactory();
		newNode = fact.createNode();
		srcNode.markRemoved(true);
		this.removalSubGraphFactory.addElement(srcNode);
		return newNode;
	}

	@Override
	public void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph) {
		this.sourceSubCigraph = sourceSubCompoundGraph;
		if(sourceSubCompoundGraph != null){
			this.removalSubGraphFactory = this.sourceSubCigraph.getSuperGraph().subgraphFactory();
		}
		else{
			this.removalSubGraphFactory = this.destChildGraph.getSuperGraph().subgraphFactory();
		}
	}

	@Override
	public ISubCompoundGraph getRemovedComponents() {
		return this.removalSubGraphFactory.createSubgraph();
	}

}
