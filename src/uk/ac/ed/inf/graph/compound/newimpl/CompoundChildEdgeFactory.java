package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundChildEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class CompoundChildEdgeFactory extends CommonEdgeFactory implements ICompoundChildEdgeFactory {
	private final ICompoundGraphElement parent;
	
	public CompoundChildEdgeFactory(ICompoundGraphElement parent){
		super();
		this.parent = parent;
	}
	
	@Override
	public ICompoundGraphElement getParent() {
		return parent;
	}

	@Override
	public ICompoundGraph getGraph() {
		return getParent().getGraph();
	}

//	@Override
//	public boolean isValidNodePair(CompoundNodePair nodePair){
//		boolean retVal = super.isValidNodePair(nodePair);
//		if(retVal){
//			retVal = this.getGraph().getElementTree().getLowestCommonAncestor(nodePair.getOutNode(), nodePair.getInNode()).equals(getParent());
//		}
//		return retVal;
//	}

	@Override
	protected boolean isParentLowestCommonAncestor(CompoundNodePair nodePair) {
		return nodePair != null
			&& getGraph().getElementTree().getLowestCommonAncestor(nodePair.getOutNode(), nodePair.getInNode()).equals(parent);
	}
}
