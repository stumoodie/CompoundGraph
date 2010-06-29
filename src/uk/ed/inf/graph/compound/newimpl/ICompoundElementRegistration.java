package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundNode;

public interface ICompoundElementRegistration {

	void registerEdge(ICompoundEdge edge);
	
	void registerNode(ICompoundNode node);
	
}
