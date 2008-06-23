package uk.ed.inf.tree;

public interface ITreeWalker<T extends ITreeNode<T>> {

	T getStartNode();

	ITreeNodeAction<T> getAction();

	void visitTree();

}