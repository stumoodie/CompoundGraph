package uk.ac.ed.inf.graph.compound;

public interface ICompoundGraphElementVisitor {

	void visitNode(ICompoundNode node);
	
	void visitEdge(ICompoundEdge edge);
	
}
