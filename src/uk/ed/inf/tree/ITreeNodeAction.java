package uk.ed.inf.tree;

public interface ITreeNodeAction<T extends ITreeNode<T>> {

	void visit(T node);
	
}
