package uk.ac.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.testfixture.IteratorTestUtility;

public class GraphNodeValidator {
	private final List<ICompoundEdge> childEdges;
	private final List<ICompoundNode> childNodes;
	private final SortedSet<ICompoundGraphElement> children;
	private final ICompoundNode testInstance; 
	private final List<ICompoundEdge> inEdges;
	private final List<ICompoundEdge> outEdges;
	private final int expectedLevel;
	private final ICompoundGraphElement expectedParent;
	
	public GraphNodeValidator(ICompoundNode testNode, int expectedLevel, ICompoundGraphElement parent, ICompoundGraphElement ... childrenArgs){
		this.testInstance = testNode;
		this.expectedLevel = expectedLevel;
		this.expectedParent = parent;
		this.childEdges = new LinkedList<ICompoundEdge>();
		this.childNodes = new LinkedList<ICompoundNode>();
		this.children = new TreeSet<ICompoundGraphElement>(new Comparator<ICompoundGraphElement>(){

			@Override
			public int compare(ICompoundGraphElement o1, ICompoundGraphElement o2) {
				int retVal = o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0); 
				return retVal;
			}
			
		});
		for(ICompoundGraphElement el : childrenArgs){
			this.children.add(el);
			if(el.isNode()){
				childNodes.add((ICompoundNode)el);
			}
			else{
				childEdges.add((ICompoundEdge)el);
			}
		}
		this.inEdges = new LinkedList<ICompoundEdge>();
		this.outEdges = new LinkedList<ICompoundEdge>();
	}
	
	
	public void validate(){
		assertEquals("expected parent", this.expectedParent, testInstance.getParent());
		assertEquals("expected level", this.expectedLevel, this.testInstance.getLevel());
		assertEquals("expected degree", getExpectedDegree(), testInstance.getDegree());
		assertEquals("expected in degree", this.inEdges.size(), testInstance.getInDegree());
		assertEquals("expected out degree", this.outEdges.size(), testInstance.getOutDegree());
		assertEquals("expected num elements", children.size(), testInstance.getChildCompoundGraph().numElements());
		assertEquals("expected num nodes", childNodes.size(), testInstance.getChildCompoundGraph().numNodes());
		assertEquals("expected num edges", childEdges.size(), testInstance.getChildCompoundGraph().numEdges());
		IteratorTestUtility<ICompoundGraphElement> childrenTest = new IteratorTestUtility<ICompoundGraphElement>(this.children.toArray(new ICompoundGraphElement[0]));
		childrenTest.testSortedIterator(this.testInstance.childIterator());
		IteratorTestUtility<ICompoundEdge> inEdgeTest = new IteratorTestUtility<ICompoundEdge>(this.inEdges.toArray(new ICompoundEdge[0]));
		inEdgeTest.testSortedIterator(this.testInstance.getInEdgeIterator());
		IteratorTestUtility<ICompoundEdge> outEdgeTest = new IteratorTestUtility<ICompoundEdge>(this.outEdges.toArray(new ICompoundEdge[0]));
		outEdgeTest.testSortedIterator(this.testInstance.getOutEdgeIterator());
	}

	public void setExpectedInEdges(ICompoundEdge ... expectedInEdges){
		for(ICompoundEdge inEdge : expectedInEdges){
			this.inEdges.add(inEdge);
		}
	}

	public void setExpectedOutEdges(ICompoundEdge ... expectedOutEdges){
		for(ICompoundEdge inEdge : expectedOutEdges){
			this.outEdges.add(inEdge);
		}
	}

	private int getExpectedDegree() {
		return this.inEdges.size() + this.outEdges.size();
	}
	
}
