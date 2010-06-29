package uk.ed.inf.graph.compound.newimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class CompoundGraphCopyBuilder implements ICompoundGraphCopyBuilder {
	private ISubCompoundGraph sourceSubCigraph;
	private IChildCompoundGraph destSubCigraph;
	private ISubCompoundGraphFactory subGraphFactory;
	private final Map<ICompoundGraphElement, ICompoundGraphElement> oldNewEquivList;

	public CompoundGraphCopyBuilder(){
		this.oldNewEquivList = new HashMap<ICompoundGraphElement, ICompoundGraphElement>();
	}
	
	@Override
	public ISubCompoundGraph getCopiedComponents() {
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
	public void makeCopy() {
		this.oldNewEquivList.clear();
		this.subGraphFactory = this.destSubCigraph.getSuperGraph().subgraphFactory();
		copyNodes();
		// avoid holding onto additional unneeded memory
		this.oldNewEquivList.clear();
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
				ICompoundNode equivOutNode = (ICompoundNode)this.oldNewEquivList.get(srcEdge.getConnectedNodes().getOutNode());
				ICompoundNode equivInNode = (ICompoundNode)this.oldNewEquivList.get(srcEdge.getConnectedNodes().getInNode());
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
		edgefact.setPair(outNode, inNode);
		return edgefact.createEdge();
	}

	private ICompoundNode copyNode(ICompoundNode srcNode, ICompoundGraphElement destParentNode){
		ICompoundNodeFactory fact = destParentNode.getChildCompoundGraph().nodeFactory();
		ICompoundNode newNode = fact.createNode();
		return newNode;
	}

	@Override
	public void setDestinatChildCompoundGraph(IChildCompoundGraph childCompoundGraph) {
		this.destSubCigraph = childCompoundGraph;
	}

	@Override
	public void setSourceSubgraph(ISubCompoundGraph sourceSubCompoundGraph) {
		this.sourceSubCigraph = sourceSubCompoundGraph;
	}

}
