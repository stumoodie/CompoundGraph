package uk.ed.inf.tree;

import java.util.HashSet;
import java.util.Set;

class LCACalculator<T extends ITreeNode<T>> {
	private final T rootNode;
	private T lcaNode;
	private boolean rootVisited;
	private final Set<T> visitedThisNodes;

	public LCACalculator(T rootNode) {
		this.rootNode = rootNode;
		this.lcaNode = null;
		this.visitedThisNodes = new HashSet<T>();
	}

	public T getLCANode(){
		return this.lcaNode;
	}

	public boolean wasLCAFound(){
		return this.lcaNode != null && this.rootVisited;
	}
	
	public void findLowestCommonAncestor(T thisNode, T thatNode) {
		if(thisNode == null || thatNode == null) throw new NullPointerException("parameters cannot be null");
		if(!thisNode.getRoot().equals(thatNode.getRoot())) throw new IllegalArgumentException("nodes must belong to the same graph");

		this.visitedThisNodes.clear();
		this.lcaNode = null;
		populateVisitedNodeList(thisNode);
		// only look and see if you can find the common node if this one
		// is in in the tree defined by the root node.
		if(this.rootVisited){
			// not that if a match if found then by definition both this and that
			// nodes must belong to the tree defined by the root.
			findCommonNode(thatNode);
		}
	}

	// store path to root from thisNode
	private void populateVisitedNodeList(final T thisNode) {
		T previousNode = null;
		T currNode = thisNode;
		this.rootVisited = false;
		// iterate down to know root for this tree or stop
		// if a root node is detected (i.e. parent matches itself)
		do {
			visitedThisNodes.add(currNode);
			if(currNode == rootNode) this.rootVisited = true;
			previousNode = currNode;
			currNode = currNode.getParent();
		} while (previousNode != this.rootNode && currNode != previousNode);
	}

	// now see where thatNode shares a common node
	private void findCommonNode(final T thatNode) {
		T previousNode = null;
		T currNode = thatNode;
		this.lcaNode = null;
		// iterate down to known root for this tree or stop
		// if a root node is detected (i.e. parent matches itself)
		do {
			if (visitedThisNodes.contains(currNode)) {
				this.lcaNode = currNode;
			} else {
				previousNode = currNode;
				currNode = previousNode.getParent();
			}
		}
		while (previousNode != this.rootNode && previousNode != currNode && this.lcaNode == null);
	}
}
