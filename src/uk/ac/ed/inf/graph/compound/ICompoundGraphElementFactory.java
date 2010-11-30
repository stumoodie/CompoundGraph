package uk.ac.ed.inf.graph.compound;

public interface ICompoundGraphElementFactory {

	void setParent(ICompoundGraphElement parent);

	void setIndex(int nodeIndex);

	void setAttribute(IElementAttribute newAttribute);

	ICompoundEdge createEdge(ICompoundNode outNode, ICompoundNode inNode);

	ICompoundNode createNode();

}
