package uk.ed.inf.tree;

import java.util.Iterator;

class LevelOrderTreeWalker<T extends ITreeNode<T>> implements ITreeWalker<T> {
	private final T rootNode;
	private final ITreeNodeAction<T> visitor;
	
	public LevelOrderTreeWalker(T startNode, ITreeNodeAction<T> visitor){
		this.rootNode = startNode;
		this.visitor = visitor;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.tree.ITreeVisitor#getStarttNode()
	 */
	public T getStartNode(){
		return this.rootNode;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.tree.ITreeVisitor#getVisitor()
	 */
	public ITreeNodeAction<T> getAction(){
		return this.visitor;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.tree.ITreeVisitor#visitTree()
	 */
	public void visitTree(){
		Iterator<T> iter = new LevelOrderTreeIterator<T>(this.rootNode);
		while(iter.hasNext()){
			T node = iter.next();
			visitor.visit(node);
		}
	}
}
