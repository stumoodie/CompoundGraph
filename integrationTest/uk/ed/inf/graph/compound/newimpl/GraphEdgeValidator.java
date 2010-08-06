package uk.ed.inf.graph.compound.newimpl;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.testfixture.IteratorTestUtility;

public class GraphEdgeValidator {
	private final List<ICompoundEdge> childEdges;
	private final List<ICompoundNode> childNodes;
	private final SortedSet<ICompoundGraphElement> children;
	private final ICompoundEdge testInstance; 
	private ICompoundNode inNode;
	private ICompoundNode outNode;
	private final int expectedLevel;
	private final ICompoundGraphElement expectedParent;
	
	public GraphEdgeValidator(ICompoundEdge testNode, int expectedLevel, ICompoundGraphElement parent, ICompoundGraphElement ... childrenArgs){
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
	}
	
	
	public void validate(){
		assertEquals("expected parent", this.expectedParent, testInstance.getParent());
		assertEquals("expected level", this.expectedLevel, this.testInstance.getLevel());
		assertEquals("expected num elements", children.size(), testInstance.getChildCompoundGraph().numElements());
		assertEquals("expected num nodes", childNodes.size(), testInstance.getChildCompoundGraph().numNodes());
		assertEquals("expected num edges", childEdges.size(), testInstance.getChildCompoundGraph().numEdges());
		IteratorTestUtility<ICompoundGraphElement> childrenTest = new IteratorTestUtility<ICompoundGraphElement>(this.children.toArray(new ICompoundGraphElement[0]));
		childrenTest.testSortedIterator(this.testInstance.childIterator());
		assertEquals("expected outNode", this.outNode, this.testInstance.getConnectedNodes().getOutNode());
		assertEquals("expected inNode", this.inNode, this.testInstance.getConnectedNodes().getInNode());
	}

	public void setExpectedInNode(ICompoundNode expectedInNode){
		this.inNode = expectedInNode;
	}

	public void setExpectedOutNode(ICompoundNode expectedOutNode){
		this.outNode = expectedOutNode;
	}

}
