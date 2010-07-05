package uk.ed.inf.graph.compound.newimpl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;

import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ed.inf.graph.compound.IRootChildCompoundGraph;
import uk.ed.inf.graph.compound.IRootCompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;
import uk.ed.inf.tree.ITree;

public class ComplexGraphFixture {
	public interface ElementBuilder {
		
		void createElement();

		Object getElement();
		
		void buildElement();
		
	}
	
	public static final int ROOT_NODE_IDX = 0;
	public static final int NODE1_IDX = 1;
	public static final int NODE2_IDX = 2;
	public static final int NODE3_IDX = 3;
	public static final int NODE4_IDX = 4;
	public static final int NODE5_IDX = 5;
	public static final int EDGE1_IDX = 6;
	public static final int EDGE2_IDX = 7;
	public static final int EDGE3_IDX = 8;
	public static final int EDGE4_IDX = 9;
	
	private final Mockery mockery;
	private final String prefix;

	private ICompoundNode node1;
	private ICompoundNode node2;
	private ICompoundNode node3;
	private ICompoundNode node4;
	private ICompoundNode node5;
	private ICompoundEdge edge1;
	private ICompoundEdge edge2;
	private ICompoundEdge edge3;
	private IRootCompoundNode rootNode;
	private ICompoundGraph graph;
	private IRootChildCompoundGraph rootChildGraph;
	private IChildCompoundGraph node1ChildGraph;
	private IChildCompoundGraph node2ChildGraph;
	private IChildCompoundGraph node3ChildGraph;
	private IChildCompoundGraph node4ChildGraph;
	private IChildCompoundGraph node5ChildGraph;
	private IChildCompoundGraph edge1ChildGraph;
	private IChildCompoundGraph edge2ChildGraph;
	private IChildCompoundGraph edge3ChildGraph;
	private ITree<ICompoundGraphElement> elementTree;
	private ICompoundEdge edge4;
	private IChildCompoundGraph edge4ChildGraph;
	private final Map<String, ElementBuilder> builderMap;
	private final List<String> creationDepList;
	private final List<String> buildDepList;
	private ICompoundEdgeFactory edgeFactory;
	private ICompoundNodeFactory nodeFactory;
	private ISubCompoundGraphFactory subgraphFactory;
	
	public ComplexGraphFixture(final Mockery mockery, String prefix){
		this.mockery = mockery;
		this.prefix = prefix;
		this.builderMap = new HashMap<String, ElementBuilder>();
		this.builderMap.put("graph", new ElementBuilder() {
			
			@Override
			public IRootCompoundNode getElement() {
				return rootNode;
			}
			
			@Override
			public void createElement() {
				createCompoundGraph();
			}
			
			@Override
			public void buildElement() {
				buildGraph(graph);
			}
		});
		this.builderMap.put("elementTree", new ElementBuilder() {
			
			@Override
			public IRootCompoundNode getElement() {
				return rootNode;
			}
			
			@Override
			public void createElement() {
				createElementTree();
			}
			
			@Override
			public void buildElement() {
				buildElementTree(elementTree);
			}
		});
		this.builderMap.put("node1", new ElementBuilder() {
			
			@Override
			public ICompoundNode getElement() {
				return node1;
			}
			
			@Override
			public void createElement() {
				createNode1();
			}
			
			@Override
			public void buildElement() {
				buildNode1(this.getElement());
			}
		});
		this.builderMap.put("node2", new ElementBuilder() {
			
			@Override
			public ICompoundNode getElement() {
				return node2;
			}
			
			@Override
			public void createElement() {
				createNode2();
			}
			
			@Override
			public void buildElement() {
				buildNode2(this.getElement());
			}
		});
		this.builderMap.put("node3", new ElementBuilder() {
			
			@Override
			public ICompoundNode getElement() {
				return node3;
			}
			
			@Override
			public void createElement() {
				createNode3();
			}
			
			@Override
			public void buildElement() {
				buildNode3(this.getElement());
			}
		});
		this.builderMap.put("node4", new ElementBuilder() {
			
			@Override
			public ICompoundNode getElement() {
				return node4;
			}
			
			@Override
			public void createElement() {
				createNode4();
			}
			
			@Override
			public void buildElement() {
				buildNode4(this.getElement());
			}
		});
		this.builderMap.put("node5", new ElementBuilder() {
			
			@Override
			public ICompoundNode getElement() {
				return node5;
			}
			
			@Override
			public void createElement() {
				createNode5();
			}
			
			@Override
			public void buildElement() {
				buildNode5(this.getElement());
			}
		});
		this.builderMap.put("node1ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return node1ChildGraph;
			}
			
			@Override
			public void createElement() {
				createNode1ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildNode1ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("node2ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return node2ChildGraph;
			}
			
			@Override
			public void createElement() {
				createNode2ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildNode2ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("node3ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return node3ChildGraph;
			}
			
			@Override
			public void createElement() {
				createNode3ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildNode3ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("node4ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return node4ChildGraph;
			}
			
			@Override
			public void createElement() {
				createNode4ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildNode4ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("node5ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return node5ChildGraph;
			}
			
			@Override
			public void createElement() {
				createNode5ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildNode5ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("edge1", new ElementBuilder() {
			
			@Override
			public ICompoundEdge getElement() {
				return edge1;
			}
			
			@Override
			public void createElement() {
				createEdge1();
			}
			
			@Override
			public void buildElement() {
				buildEdge1(this.getElement());
			}
		});
		this.builderMap.put("edge2", new ElementBuilder() {
			
			@Override
			public ICompoundEdge getElement() {
				return edge2;
			}
			
			@Override
			public void createElement() {
				createEdge2();
			}
			
			@Override
			public void buildElement() {
				buildEdge2(this.getElement());
			}
		});
		this.builderMap.put("edge3", new ElementBuilder() {
			
			@Override
			public ICompoundEdge getElement() {
				return edge3;
			}
			
			@Override
			public void createElement() {
				createEdge3();
			}
			
			@Override
			public void buildElement() {
				buildEdge3(this.getElement());
			}
		});
		this.builderMap.put("edge4", new ElementBuilder() {
			
			@Override
			public ICompoundEdge getElement() {
				return edge4;
			}
			
			@Override
			public void createElement() {
				createEdge4();
			}
			
			@Override
			public void buildElement() {
				buildEdge4(this.getElement());
			}
		});
		this.builderMap.put("edge1ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return edge1ChildGraph;
			}
			
			@Override
			public void createElement() {
				createEdge1ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildEdge1ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("edge2ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return edge2ChildGraph;
			}
			
			@Override
			public void createElement() {
				createEdge2ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildEdge2ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("edge3ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return edge3ChildGraph;
			}
			
			@Override
			public void createElement() {
				createEdge3ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildEdge3ChildGraph(this.getElement());
			}
		});
		this.builderMap.put("edge4ChildGraph", new ElementBuilder() {
			
			@Override
			public IChildCompoundGraph getElement() {
				return edge4ChildGraph;
			}
			
			@Override
			public void createElement() {
				createEdge4ChildGraph();
			}
			
			@Override
			public void buildElement() {
				buildEdge4ChildGraph(this.getElement());
			}
		});
		this.creationDepList = new LinkedList<String>(this.builderMap.keySet());
		this.buildDepList = new LinkedList<String>(this.builderMap.keySet());
	}
	
	protected void buildNode1ChildGraph(IChildCompoundGraph element) {
		this.mockery.checking(new Expectations(){{
			allowing(node1ChildGraph).getRoot(); will(returnValue(node1));
			allowing(node1ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(node1ChildGraph).getNumNodes(); will(returnValue(1));
			allowing(node1ChildGraph).getNumEdges(); will(returnValue(1));
			allowing(node1ChildGraph).numElements(); will(returnValue(2));
			allowing(node1ChildGraph).containsNode(with(equalTo(node2))); will(returnValue(true));
			allowing(node1ChildGraph).containsNode(with(not(node2))); will(returnValue(false));
			allowing(node1ChildGraph).containsEdge(with(equalTo(edge4))); will(returnValue(true));
			allowing(node1ChildGraph).containsEdge(with(not(edge4))); will(returnValue(false));
			allowing(node1ChildGraph).containsNode(with(equalTo(NODE2_IDX))); will(returnValue(true));
			allowing(node1ChildGraph).containsNode(with(not(NODE2_IDX))); will(returnValue(false));
			allowing(node1ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
			allowing(node1ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
		}});
	}

	protected void buildNode2ChildGraph(IChildCompoundGraph element) {
		this.mockery.checking(new Expectations(){{
			allowing(node2ChildGraph).getRoot(); will(returnValue(node2));
			allowing(node2ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(node2ChildGraph).getNumNodes(); will(returnValue(0));
			allowing(node2ChildGraph).getNumEdges(); will(returnValue(1));
			allowing(node2ChildGraph).numElements(); will(returnValue(1));
			allowing(node2ChildGraph).getNumEdges(); will(returnValue(1));
			allowing(node2ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
			allowing(node2ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
		}});
	}

	protected void buildNode3ChildGraph(IChildCompoundGraph element) {
		this.mockery.checking(new Expectations(){{
			allowing(node3ChildGraph).getRoot(); will(returnValue(node3));
			allowing(node3ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(node3ChildGraph).getNumNodes(); will(returnValue(0));
			allowing(node3ChildGraph).getNumEdges(); will(returnValue(0));
			allowing(node3ChildGraph).numElements(); will(returnValue(0));
			allowing(node3ChildGraph).containsNode(with(any(Integer.class))); will(returnValue(false));
			allowing(node3ChildGraph).containsNode(with(any(ICompoundNode.class))); will(returnValue(false));
			allowing(node3ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
			allowing(node3ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
		}});
	}

	protected void buildNode4ChildGraph(IChildCompoundGraph element) {
		this.mockery.checking(new Expectations(){{
			allowing(node4ChildGraph).getRoot(); will(returnValue(node4));
			allowing(node4ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(node4ChildGraph).getNumNodes(); will(returnValue(0));
			allowing(node4ChildGraph).getNumEdges(); will(returnValue(0));
			allowing(node4ChildGraph).numElements(); will(returnValue(0));
			allowing(node4ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
			allowing(node4ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
		}});
	}

	protected void buildNode5ChildGraph(IChildCompoundGraph element) {
		this.mockery.checking(new Expectations(){{
			allowing(node5ChildGraph).getRoot(); will(returnValue(node5));
			allowing(node5ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(node5ChildGraph).getNumNodes(); will(returnValue(0));
			allowing(node5ChildGraph).getNumEdges(); will(returnValue(0));
			allowing(node5ChildGraph).numElements(); will(returnValue(0));
			allowing(node5ChildGraph).containsDirectedEdge(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
			allowing(node5ChildGraph).containsConnection(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
		}});
	}

	@SuppressWarnings("unchecked")
	protected void createElementTree() {
		this.elementTree = this.mockery.mock(ITree.class, createMockName("elementTree"));
	}

	public void setCreationDependencies(List<String> creationDepList){
		this.creationDepList.clear();
		this.creationDepList.addAll(creationDepList);
	}
	
	public void setBuildDependencies(List<String> creationDepList){
		this.buildDepList.clear();
		this.buildDepList.addAll(creationDepList);
	}
	
	public Mockery getMockery(){
		return this.mockery;
	}
	
	
	public void createElements(){
		for(String element : this.creationDepList){
			this.builderMap.get(element).createElement();
		}
	}

	public void buildObjects(){
		for(String element : this.buildDepList){
			this.builderMap.get(element).buildElement();
		}
	}
	
	protected void buildEdge1(ICompoundEdge iCompoundEdge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge1).getChildCompoundGraph(); will(returnValue(edge1ChildGraph));
			allowing(edge1).getLevel(); will(returnValue(1));
			allowing(edge1).getIndex(); will(returnValue(EDGE1_IDX));
			allowing(edge1).getParent(); will(returnValue(rootNode));
			allowing(edge1).getRoot(); will(returnValue(rootNode));
			allowing(edge1).childIterator(); will(returnIterator(node3, edge2, node5));
			allowing(edge1).getConnectedNodes(); will(returnValue(new CompoundNodePair(node2, node1)));
			allowing(edge1).isRemoved(); will(returnValue(false));
			allowing(edge1).getGraph(); will(returnValue(graph));
			allowing(edge1).compareTo(edge1); will(returnValue(0));
			allowing(edge1).compareTo(edge2); will(returnValue(1));
			allowing(edge1).compareTo(edge3); will(returnValue(1));
			allowing(edge1).compareTo(edge4); will(returnValue(1));
			allowing(edge1).isLink(); will(returnValue(true));
			allowing(edge1).isNode(); will(returnValue(false));
		}});
	}

	protected void buildElementTree(ITree<ICompoundGraphElement> tree){
		this.mockery.checking(new Expectations(){{
			
			allowing(elementTree).isAncestor(with(edge2), with(isOneOf(edge1, rootNode))); will(returnValue(true));
			allowing(elementTree).isAncestor(with(equalTo(edge2)), with(not(isOneOf(edge1, rootNode)))); will(returnValue(false));
			allowing(elementTree).isDescendant(with(edge2), with(isOneOf(node4))); will(returnValue(true));
			allowing(elementTree).isDescendant(with(edge2), with(not(isOneOf(node4)))); will(returnValue(false));
			
			allowing(elementTree).isAncestor(node1, rootNode); will(returnValue(true));
			allowing(elementTree).isAncestor(with(equalTo(node1)), with(not(rootNode))); will(returnValue(false));
			allowing(elementTree).isDescendant(with(node1), with(isOneOf(node2, edge3))); will(returnValue(true));
			allowing(elementTree).isDescendant(with(node1), with(not(isOneOf(node2, edge3)))); will(returnValue(false));

		}});
	}
	
	protected void buildEdge2(ICompoundEdge edge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge2).getChildCompoundGraph(); will(returnValue(edge2ChildGraph));
			allowing(edge2).getLevel(); will(returnValue(2));
			allowing(edge2).getIndex(); will(returnValue(EDGE2_IDX));
			allowing(edge2).getParent(); will(returnValue(edge1));
			allowing(edge2).getRoot(); will(returnValue(rootNode));
			allowing(edge2).childIterator(); will(returnIterator(node4));
			allowing(edge2).getConnectedNodes(); will(returnValue(new CompoundNodePair(node3, node5)));
			allowing(edge2).isRemoved(); will(returnValue(false));
			allowing(edge2).getGraph(); will(returnValue(graph));
			allowing(edge2).isLink(); will(returnValue(true));
			allowing(edge2).isNode(); will(returnValue(false));
		}});
	}

	protected void buildEdge3(ICompoundEdge iCompoundEdge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge3).getChildCompoundGraph(); will(returnValue(edge3ChildGraph));
			allowing(edge3).getLevel(); will(returnValue(3));
			allowing(edge3).getIndex(); will(returnValue(EDGE3_IDX));
			allowing(edge3).getParent(); will(returnValue(node2));
			allowing(edge3).getRoot(); will(returnValue(rootNode));
			allowing(edge3).childIterator(); will(returnIterator());
			allowing(edge3).getConnectedNodes(); will(returnValue(new CompoundNodePair(node2, node2)));
			allowing(edge3).isRemoved(); will(returnValue(false));
			allowing(edge3).getGraph(); will(returnValue(graph));
			allowing(edge3).isLink(); will(returnValue(true));
			allowing(edge3).isNode(); will(returnValue(false));
		}});
	}

	protected void buildEdge4(ICompoundEdge iCompoundEdge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge4).getChildCompoundGraph(); will(returnValue(edge4ChildGraph));
			allowing(edge4).getLevel(); will(returnValue(1));
			allowing(edge4).getIndex(); will(returnValue(EDGE4_IDX));
			allowing(edge4).getParent(); will(returnValue(rootNode));
			allowing(edge4).getRoot(); will(returnValue(rootNode));
			allowing(edge4).childIterator(); will(returnIterator());
			allowing(edge4).getConnectedNodes(); will(returnValue(new CompoundNodePair(node1, node3)));
			allowing(edge4).isRemoved(); will(returnValue(false));
			allowing(edge4).getGraph(); will(returnValue(graph));
			allowing(edge4).compareTo(rootNode); will(returnValue(-1));
			allowing(edge4).compareTo(with(not(edge4))); will(returnValue(-1));
			allowing(edge4).compareTo(edge4); will(returnValue(0));
			allowing(edge4).isLink(); will(returnValue(true));
			allowing(edge4).isNode(); will(returnValue(false));
		}});
	}

	protected void buildEdge1ChildGraph(IChildCompoundGraph iCompoundEdge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge1ChildGraph).getRoot(); will(returnValue(edge1));
			allowing(edge1ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(edge1ChildGraph).getNumNodes(); will(returnValue(2));
			allowing(edge1ChildGraph).getNumEdges(); will(returnValue(1));
			allowing(edge1ChildGraph).numElements(); will(returnValue(3));
			allowing(edge1ChildGraph).containsNode(with(isOneOf(node3, node5))); will(returnValue(true));
			allowing(edge1ChildGraph).containsNode(with(not(isOneOf(node3, node5)))); will(returnValue(false));
			allowing(edge1ChildGraph).containsEdge(edge2); will(returnValue(true));
			allowing(edge1ChildGraph).containsEdge(with(not(edge2))); will(returnValue(false));
			allowing(edge1ChildGraph).containsEdge(with(EDGE2_IDX)); will(returnValue(true));
			allowing(edge1ChildGraph).containsEdge(with(not(EDGE2_IDX))); will(returnValue(false));
			allowing(edge1ChildGraph).containsNode(with(isOneOf(NODE3_IDX, NODE5_IDX))); will(returnValue(true));
			allowing(edge1ChildGraph).containsNode(with(not(isOneOf(NODE3_IDX, NODE5_IDX)))); will(returnValue(false));
			allowing(edge1ChildGraph).containsDirectedEdge(with(node3), with(node5)); will(returnValue(true));
			allowing(edge1ChildGraph).containsDirectedEdge(with(not(node3)), with(not(node5))); will(returnValue(false));
			allowing(edge1ChildGraph).containsConnection(with(node3), with(node5)); will(returnValue(true));
			allowing(edge1ChildGraph).containsConnection(with(node5), with(node3)); will(returnValue(true));
			allowing(edge1ChildGraph).containsConnection(with(not(node3)), with(not(node5))); will(returnValue(false));
		}});
	}

	protected void buildEdge2ChildGraph(IChildCompoundGraph edge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge2ChildGraph).getRoot(); will(returnValue(edge2));
			allowing(edge2ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(edge2ChildGraph).getNumNodes(); will(returnValue(1));
			allowing(edge2ChildGraph).getNumEdges(); will(returnValue(0));
			allowing(edge2ChildGraph).numElements(); will(returnValue(1));
			allowing(edge2ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
			allowing(edge2ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
		}});
	}

	protected void buildEdge3ChildGraph(IChildCompoundGraph iCompoundEdge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge3ChildGraph).getRoot(); will(returnValue(edge3));
			allowing(edge3ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(edge3ChildGraph).getNumNodes(); will(returnValue(0));
			allowing(edge3ChildGraph).getNumEdges(); will(returnValue(0));
			allowing(edge3ChildGraph).numElements(); will(returnValue(0));
			allowing(edge3ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
			allowing(edge3ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
		}});
	}

	protected void buildEdge4ChildGraph(IChildCompoundGraph iCompoundEdge) {
		this.mockery.checking(new Expectations(){{
			allowing(edge4ChildGraph).getRoot(); will(returnValue(edge4));
			allowing(edge4ChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(edge4ChildGraph).getNumNodes(); will(returnValue(0));
			allowing(edge4ChildGraph).getNumEdges(); will(returnValue(0));
			allowing(edge4ChildGraph).numElements(); will(returnValue(0));
			allowing(edge4ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
			allowing(edge4ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
		}});
	}

	protected void buildNode1(final ICompoundNode node1) {
		this.mockery.checking(new Expectations(){{
			allowing(node1).getChildCompoundGraph(); will(returnValue(node1ChildGraph));
			allowing(node1).getLevel(); will(returnValue(1));
			allowing(node1).getIndex(); will(returnValue(NODE1_IDX));
			allowing(node1).getParent(); will(returnValue(rootNode));
			allowing(node1).getRoot(); will(returnValue(rootNode));
			allowing(node1).childIterator(); will(returnIterator(node2));
			allowing(node1).getDegree(); will(returnValue(2));
			allowing(node1).getInDegree(); will(returnValue(1));
			allowing(node1).getOutDegree(); will(returnValue(1));
			allowing(node1).isRemoved(); will(returnValue(false));
			allowing(node1).getGraph(); will(returnValue(graph));
			allowing(node1).isLink(); will(returnValue(false));
			allowing(node1).isNode(); will(returnValue(true));
		}});
	}

	protected void buildNode2(ICompoundNode iCompoundNode) {
		this.mockery.checking(new Expectations(){{
			allowing(node2).getChildCompoundGraph(); will(returnValue(node2ChildGraph));
			allowing(node2).getLevel(); will(returnValue(2));
			allowing(node2).getIndex(); will(returnValue(NODE2_IDX));
			allowing(node2).getParent(); will(returnValue(node1));
			allowing(node2).getRoot(); will(returnValue(rootNode));
			allowing(node2).childIterator(); will(returnIterator(edge3));
			allowing(node2).getDegree(); will(returnValue(3));
			allowing(node2).getInDegree(); will(returnValue(1));
			allowing(node2).getOutDegree(); will(returnValue(2));
			allowing(node2).isRemoved(); will(returnValue(false));
			allowing(node2).getGraph(); will(returnValue(graph));
			allowing(node2).isLink(); will(returnValue(false));
			allowing(node2).isNode(); will(returnValue(true));
		}});
	}

	protected void buildNode3(ICompoundNode iCompoundNode) {
		this.mockery.checking(new Expectations(){{
			allowing(node3).getGraph(); will(returnValue(graph));
			allowing(node3).getChildCompoundGraph(); will(returnValue(node3ChildGraph));
			allowing(node3).getLevel(); will(returnValue(2));
			allowing(node3).getIndex(); will(returnValue(NODE3_IDX));
			allowing(node3).getParent(); will(returnValue(edge1));
			allowing(node3).getRoot(); will(returnValue(rootNode));
			allowing(node3).childIterator(); will(returnIterator());
			allowing(node3).isRemoved(); will(returnValue(false));
			allowing(node3).getOutEdgeIterator(); will(returnIterator(edge2));
			allowing(node3).getInEdgeIterator(); will(returnIterator(edge4));
			allowing(node3).getDegree(); will(returnValue(2));
			allowing(node3).getInDegree(); will(returnValue(1));
			allowing(node3).getOutDegree(); will(returnValue(1));
			allowing(node3).addInEdge(with(any(ICompoundEdge.class)));
			allowing(node3).addOutEdge(with(any(ICompoundEdge.class)));
			allowing(node3).isLink(); will(returnValue(false));
			allowing(node3).isNode(); will(returnValue(true));
		}});
	}

	protected void buildNode4(ICompoundNode iCompoundNode) {
		this.mockery.checking(new Expectations(){{
			allowing(node4).getChildCompoundGraph(); will(returnValue(node4ChildGraph));
			allowing(node4).getLevel(); will(returnValue(3));
			allowing(node4).getIndex(); will(returnValue(NODE4_IDX));
			allowing(node4).getParent(); will(returnValue(edge2));
			allowing(node4).getRoot(); will(returnValue(rootNode));
			allowing(node4).childIterator(); will(returnIterator());
			allowing(node4).isRemoved(); will(returnValue(false));
			allowing(node4).getGraph(); will(returnValue(graph));
			allowing(node4).isLink(); will(returnValue(false));
			allowing(node4).isNode(); will(returnValue(true));
		}});
	}

	protected void buildNode5(ICompoundNode iCompoundNode) {
		this.mockery.checking(new Expectations(){{
			allowing(node5).getChildCompoundGraph(); will(returnValue(node5ChildGraph));
			allowing(node5).getLevel(); will(returnValue(2));
			allowing(node5).getIndex(); will(returnValue(NODE5_IDX));
			allowing(node5).getParent(); will(returnValue(edge1));
			allowing(node5).getRoot(); will(returnValue(rootNode));
			allowing(node5).childIterator(); will(returnIterator());
			allowing(node5).isRemoved(); will(returnValue(false));
			allowing(node5).addInEdge(with(any(ICompoundEdge.class)));
			allowing(node5).addOutEdge(with(any(ICompoundEdge.class)));
			allowing(node5).getGraph(); will(returnValue(graph));
			allowing(node5).isLink(); will(returnValue(false));
			allowing(node5).isNode(); will(returnValue(true));
			allowing(node5).compareTo(with(isOneOf(rootNode, node1, node2, node3, node4))); will(returnValue(1));
			allowing(node5).compareTo(with(isOneOf(edge1, edge2, edge3, edge4))); will(returnValue(-1));
			allowing(node5).compareTo(node5); will(returnValue(0));
		}});
	}

	protected void buildGraph(final ICompoundGraph graph){
		this.mockery.checking(new Expectations(){{
			allowing(graph).getRoot(); will(returnValue(rootNode));
			allowing(graph).getElementTree(); will(returnValue(elementTree));
			allowing(graph).edgeFactory(); will(returnValue(edgeFactory));
			allowing(graph).nodeFactory(); will(returnValue(nodeFactory));
			
			allowing(rootNode).getChildCompoundGraph(); will(returnValue(rootChildGraph));
			allowing(rootNode).getLevel(); will(returnValue(0));
			allowing(rootNode).getIndex(); will(returnValue(0));
			allowing(rootNode).getParent(); will(returnValue(rootNode));
			allowing(rootNode).getRoot(); will(returnValue(rootNode));
			allowing(rootNode).childIterator(); will(returnIterator(node1, edge1, edge4));
			allowing(rootNode).getGraph(); will(returnValue(graph));
			allowing(rootNode).isRemoved(); will(returnValue(false));
			allowing(rootNode).isLink(); will(returnValue(false));
			allowing(rootNode).isNode(); will(returnValue(true));
			
			allowing(rootChildGraph).getRoot(); will(returnValue(rootNode));
			allowing(rootChildGraph).getSuperGraph(); will(returnValue(graph));
			allowing(rootChildGraph).getNumNodes(); will(returnValue(6));
			allowing(rootChildGraph).getNumEdges(); will(returnValue(4));
			allowing(rootChildGraph).numElements(); will(returnValue(9));
			
		}});
	}	

	protected void createCompoundGraph() {
		this.graph = this.mockery.mock(ICompoundGraph.class, createMockName("graph"));
		this.rootNode = this.mockery.mock(IRootCompoundNode.class, createMockName("rootNode"));
		this.rootChildGraph = this.mockery.mock(IRootChildCompoundGraph.class, createMockName("rootChildGraph"));
		this.edgeFactory = this.mockery.mock(ICompoundEdgeFactory.class, createMockName("edgeFactory"));
		this.nodeFactory = this.mockery.mock(ICompoundNodeFactory.class, createMockName("nodeFactory"));
		this.subgraphFactory = this.mockery.mock(ISubCompoundGraphFactory.class, createMockName("subgraphFactory"));
	}

	protected void createEdge1() {
		this.edge1 = this.mockery.mock(ICompoundEdge.class, createMockName("edge1"));
	}

	protected void createEdge2() {
		this.edge2 = this.mockery.mock(ICompoundEdge.class, createMockName("edge2"));
	}

	protected void createEdge3() {
		this.edge3 = this.mockery.mock(ICompoundEdge.class, createMockName("edge3"));
	}

	protected void createEdge4() {
		this.edge4 = this.mockery.mock(ICompoundEdge.class, createMockName("edge4"));
	}

	protected void createEdge1ChildGraph() {
		this.edge1ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge1ChildGraph"));
	}

	protected void createEdge2ChildGraph() {
		this.edge2ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge2ChildGraph"));
	}

	protected void createEdge3ChildGraph() {
		this.edge3ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge3ChildGraph"));
	}

	protected void createEdge4ChildGraph() {
		this.edge4ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge4ChildGraph"));
	}

	protected String createMockName(String string) {
		StringBuilder buf = new StringBuilder(prefix);
		buf.append(string);
		return buf.toString();
	}

	protected void createNode1() {
		this.node1 = this.mockery.mock(ICompoundNode.class, createMockName("node1"));
	}

	protected void createNode2() {
		this.node2 = this.mockery.mock(ICompoundNode.class, createMockName("node2"));
	}

	protected void createNode3() {
		this.node3 = this.mockery.mock(ICompoundNode.class, createMockName("node3"));
	}

	protected void createNode4() {
		this.node4 = this.mockery.mock(ICompoundNode.class, createMockName("node4"));
	}

	protected void createNode5() {
		this.node5 = this.mockery.mock(ICompoundNode.class, createMockName("node5"));
	}

	protected void createNode1ChildGraph(){
		this.node1ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node1ChildGraph"));
	}
	
	protected void createNode2ChildGraph() {
		this.node2ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node2ChildGraph"));
	}

	protected void createNode3ChildGraph() {
		this.node3ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node3ChildGraph"));
	}

	protected void createNode4ChildGraph() {
		this.node4ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node4ChildGraph"));
	}

	protected void createNode5ChildGraph() {
		this.node5ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node5ChildGraph"));
	}

	public ICompoundNode getNode1() {
		return node1;
	}

	public void setNode1(ICompoundNode node1) {
		this.node1 = node1;
	}

	public ICompoundNode getNode2() {
		return node2;
	}

	public void setNode2(ICompoundNode node2) {
		this.node2 = node2;
	}

	public ICompoundNode getNode3() {
		return node3;
	}

	public void setNode3(ICompoundNode node3) {
		this.node3 = node3;
	}

	public ICompoundNode getNode4() {
		return node4;
	}

	public void setNode4(ICompoundNode node4) {
		this.node4 = node4;
	}

	public ICompoundNode getNode5() {
		return node5;
	}

	public void setNode5(ICompoundNode node5) {
		this.node5 = node5;
	}

	public ICompoundEdge getEdge1() {
		return edge1;
	}

	public void setEdge1(ICompoundEdge edge1) {
		this.edge1 = edge1;
	}

	public ICompoundEdge getEdge2() {
		return edge2;
	}

	public void setEdge2(ICompoundEdge edge2) {
		this.edge2 = edge2;
	}

	public ICompoundEdge getEdge3() {
		return edge3;
	}

	public void setEdge3(ICompoundEdge edge3) {
		this.edge3 = edge3;
	}

	public IRootCompoundNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(IRootCompoundNode rootNode) {
		this.rootNode = rootNode;
	}

	public ICompoundGraph getGraph() {
		return graph;
	}

	public void setGraph(ICompoundGraph graph) {
		this.graph = graph;
	}

	public IRootChildCompoundGraph getRootChildGraph() {
		return rootChildGraph;
	}

	public void setRootChildGraph(IRootChildCompoundGraph rootChildGraph) {
		this.rootChildGraph = rootChildGraph;
	}

	public IChildCompoundGraph getNode1ChildGraph() {
		return node1ChildGraph;
	}

	public void setNode1ChildGraph(IChildCompoundGraph node1ChildGraph) {
		this.node1ChildGraph = node1ChildGraph;
	}

	public IChildCompoundGraph getNode2ChildGraph() {
		return node2ChildGraph;
	}

	public void setNode2ChildGraph(IChildCompoundGraph node2ChildGraph) {
		this.node2ChildGraph = node2ChildGraph;
	}

	public IChildCompoundGraph getNode3ChildGraph() {
		return node3ChildGraph;
	}

	public void setNode3ChildGraph(IChildCompoundGraph node3ChildGraph) {
		this.node3ChildGraph = node3ChildGraph;
	}

	public IChildCompoundGraph getNode4ChildGraph() {
		return node4ChildGraph;
	}

	public void setNode4ChildGraph(IChildCompoundGraph node4ChildGraph) {
		this.node4ChildGraph = node4ChildGraph;
	}

	public IChildCompoundGraph getNode5ChildGraph() {
		return node5ChildGraph;
	}

	public void setNode5ChildGraph(IChildCompoundGraph node5ChildGraph) {
		this.node5ChildGraph = node5ChildGraph;
	}

	public IChildCompoundGraph getEdge1ChildGraph() {
		return edge1ChildGraph;
	}

	public void setEdge1ChildGraph(IChildCompoundGraph edge1ChildGraph) {
		this.edge1ChildGraph = edge1ChildGraph;
	}

	public IChildCompoundGraph getEdge2ChildGraph() {
		return edge2ChildGraph;
	}

	public void setEdge2ChildGraph(IChildCompoundGraph edge2ChildGraph) {
		this.edge2ChildGraph = edge2ChildGraph;
	}

	public IChildCompoundGraph getEdge3ChildGraph() {
		return edge3ChildGraph;
	}

	public void setEdge3ChildGraph(IChildCompoundGraph edge3ChildGraph) {
		this.edge3ChildGraph = edge3ChildGraph;
	}

	public ITree<ICompoundGraphElement> getElementTree() {
		return elementTree;
	}

	public void setElementTree(ITree<ICompoundGraphElement> elementTree) {
		this.elementTree = elementTree;
	}

	public ICompoundEdge getEdge4() {
		return edge4;
	}

	public void setEdge4(ICompoundEdge edge4) {
		this.edge4 = edge4;
	}

	public IChildCompoundGraph getEdge4ChildGraph() {
		return edge4ChildGraph;
	}

	public void setEdge4ChildGraph(IChildCompoundGraph edge4ChildGraph) {
		this.edge4ChildGraph = edge4ChildGraph;
	}

	public ICompoundEdgeFactory getEdgeFactory() {
		return this.edgeFactory;
	}

	public ICompoundNodeFactory getNodeFactory() {
		return this.nodeFactory;
	}

	public ISubCompoundGraphFactory getSubgraphFactory() {
		return this.subgraphFactory;
	}

}
