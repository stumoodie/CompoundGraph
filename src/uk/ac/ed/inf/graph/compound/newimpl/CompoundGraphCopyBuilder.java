package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundGraphCopyBuilder implements ICompoundGraphCopyBuilder {
	private ISubCompoundGraph sourceSubCigraph;
	private final IChildCompoundGraph destSubCigraph;
	private ISubCompoundGraphFactory subGraphFactory;
	private final Map<ICompoundGraphElement, ICompoundGraphElement> oldNewEquivList;
	private ISubCompoundGraph copiedComponents;

	public CompoundGraphCopyBuilder(IChildCompoundGraph dest){
		this.oldNewEquivList = new HashMap<ICompoundGraphElement, ICompoundGraphElement>();
		this.copiedComponents = null; 
		this.destSubCigraph = dest;
	}
	
	@Override
	public ISubCompoundGraph getCopiedComponents() {
		return this.copiedComponents;
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
	public boolean canCopyHere() {
		ISubCompoundGraph subGraph = this.sourceSubCigraph;
		return subGraph != null && subGraph.isInducedSubgraph()
			&& subGraph.isConsistentSnapShot() && !subGraph.containsRoot();
	}
	
	@Override
	@Deprecated
	public boolean canCopyHere(ISubCompoundGraph subGraph) {
		this.setSourceSubgraph(subGraph);
		return this.canCopyHere();
	}

	@Override
	@Deprecated
	public void copyHere(ISubCompoundGraph subGraph) {
		this.setSourceSubgraph(subGraph);
		this.makeCopy();
	}

	@Override
	public void makeCopy() {
		this.copiedComponents = null;
		this.oldNewEquivList.clear();
		this.subGraphFactory = this.destSubCigraph.getSuperGraph().subgraphFactory();
		copyNodes();
		// avoid holding onto additional unneeded memory
		this.oldNewEquivList.clear();
		this.copiedComponents = this.subGraphFactory.createSubgraph();
	}

	private void copyNodes(){
		initRootNodeEquivalent(); 
		Iterator<ICompoundGraphElement> sourceNodeIter = this.sourceSubCigraph.edgeLastElementIterator();
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

	private ICompoundEdge copyEdge(ICompoundEdge srcEdge, ICompoundGraphElement parent, ICompoundNode outNode, ICompoundNode inNode) {
		ICompoundChildEdgeFactory edgefact = parent.getChildCompoundGraph().edgeFactory();
		edgefact.setPair(new CompoundNodePair(outNode, inNode));
		return edgefact.createEdge();
	}

	private ICompoundNode copyNode(ICompoundNode srcNode, ICompoundGraphElement destParentNode){
		ICompoundNodeFactory fact = destParentNode.getChildCompoundGraph().nodeFactory();
		ICompoundNode newNode = fact.createNode();
		return newNode;
	}

	@Override
	public void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph) {
		this.sourceSubCigraph = sourceSubCompoundGraph;
	}

}
