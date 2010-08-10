package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class CompoundEdgeFactory extends CommonEdgeFactory implements ICompoundEdgeFactory {
	private final ICompoundGraph graph;
	
	public CompoundEdgeFactory(ICompoundGraph graph){
		super();
		this.graph = graph;
	}
	
	@Override
	public ICompoundGraphElement getParent() {
		ICompoundGraphElement lcmNode = null;
		if(this.getCurrentNodePair() != null){
			CompoundNodePair pair = this.getCurrentNodePair();
			lcmNode = this.getGraph().getElementTree().getLowestCommonAncestor(pair.getOutNode(), pair.getInNode());
		}
		return lcmNode;
	}
	
	@Override
	public ICompoundGraph getGraph(){
		return this.graph;
	}

}
