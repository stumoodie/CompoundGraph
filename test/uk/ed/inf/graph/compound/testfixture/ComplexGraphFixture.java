package uk.ed.inf.graph.compound.testfixture;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

import uk.ac.ed.inf.designbycontract.PostConditionException;
import uk.ac.ed.inf.designbycontract.PreConditionException;
import uk.ed.inf.graph.compound.CompoundNodePair;
import uk.ed.inf.graph.compound.IChildCompoundGraph;
import uk.ed.inf.graph.compound.ICompoundChildEdgeFactory;
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

public class ComplexGraphFixture implements IGraphTestFixture {
	public static final String GRAPH_ID = "graph";
	public static final String NODE1_ID = "node1";
	public static final String NODE2_ID = "node2";
	public static final String NODE3_ID = "node3";
	public static final String NODE4_ID = "node4";
	public static final String NODE5_ID = "node5";
	public static final String NODE6_ID = "node6";
	public static final String EDGE1_ID = "edge1";
	public static final String EDGE2_ID = "edge2";
	public static final String EDGE3_ID = "edge3";
	public static final String EDGE4_ID = "edge4";
	public static final int ROOT_NODE_IDX = 0;
	public static final int NODE1_IDX = 1;
	public static final int NODE2_IDX = 2;
	public static final int NODE3_IDX = 3;
	public static final int NODE4_IDX = 4;
	public static final int NODE5_IDX = 5;
	public static final int NODE6_IDX = 6;
	public static final int EDGE1_IDX = 7;
	public static final int EDGE2_IDX = 8;
	public static final int EDGE3_IDX = 9;
	public static final int EDGE4_IDX = 10;
	
	private final Mockery mockery;
	private final String prefix;

//	private ICompoundNode node1;
//	private ICompoundNode node2;
//	private ICompoundNode node3;
//	private ICompoundNode node4;
//	private ICompoundNode node5;
//	private ICompoundEdge edge1;
//	private ICompoundEdge edge2;
//	private ICompoundEdge edge3;
//	private IRootCompoundNode rootNode;
//	private ICompoundGraph graph;
//	private IRootChildCompoundGraph rootChildGraph;
//	private IChildCompoundGraph node1ChildGraph;
//	private IChildCompoundGraph node2ChildGraph;
//	private IChildCompoundGraph node3ChildGraph;
//	private IChildCompoundGraph node4ChildGraph;
//	private IChildCompoundGraph node5ChildGraph;
//	private IChildCompoundGraph edge1ChildGraph;
//	private IChildCompoundGraph edge2ChildGraph;
//	private IChildCompoundGraph edge3ChildGraph;
//	private ITree<ICompoundGraphElement> elementTree;
//	private ICompoundEdge edge4;
//	private IChildCompoundGraph edge4ChildGraph;
	private final Map<String,IGraphObjectBuilder> builderMap;
	private final List<String> creationDepList = Arrays.asList(new String[]{GRAPH_ID, NODE1_ID, NODE2_ID, EDGE3_ID, NODE6_ID, EDGE1_ID, NODE3_ID, NODE5_ID, EDGE2_ID, NODE4_ID, EDGE4_ID});
	private Map<String, Boolean> elementRemovalMap;
//	private final List<String> creationDepList = Arrays.asList(new String[]{GRAPH_ID, "elementTree", NODE1_ID, NODE2_ID, "edge3", "edge1", NODE3_ID, NODE5_ID, "edge2", NODE4_ID, "edge4"});
//	private ICompoundEdgeFactory edgeFactory;
//	private ICompoundNodeFactory nodeFactory;
//	private ISubCompoundGraphFactory subgraphFactory;
//	private CompoundNodePair edge2Ends;
//	private CompoundNodePair edge1Ends;
//	private CompoundNodePair edge3Ends;
//	private CompoundNodePair edge4Ends;
//	private ICompoundChildEdgeFactory edge1ChildEdgeFactory;
	private final IGraphObjectBuilder builders[] = {
			new NodeBuilder(NODE1_ID, new INodeConstructor(){
		
				@Override
				public ICompoundNode createCompoundNode() {
					final ICompoundNode node1 = mockery.mock(ICompoundNode.class, createMockName("node1"));
					mockery.checking(new Expectations(){{
						allowing(node1).getLevel(); will(returnValue(1));
						allowing(node1).getIndex(); will(returnValue(NODE1_IDX));
						allowing(node1).getParent(); will(returnValue(getRootNode()));
						allowing(node1).getRoot(); will(returnValue(getRootNode()));
						allowing(node1).getDegree(); will(returnValue(1));
						allowing(node1).getInDegree(); will(returnValue(0));
						allowing(node1).getOutDegree(); will(returnValue(1));
						allowing(node1).isRemoved();  will(getRemovalState(NODE1_ID));;
						allowing(node1).getGraph(); will(returnValue(getGraph()));
						allowing(node1).isLink(); will(returnValue(false));
						allowing(node1).isNode(); will(returnValue(true));
//						allowing(node1).markRemoved(with(isOneOf(true, false)));
					}});
					return node1;
				}
		

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundNode node1) {
					final IChildCompoundGraph node1ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("node1ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(node1).getChildCompoundGraph(); will(returnValue(node1ChildGraph));

						allowing(node1ChildGraph).getRoot(); will(returnValue(getNode1()));
						allowing(node1ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(node1ChildGraph).numNodes(); will(returnValue(1));
						allowing(node1ChildGraph).numEdges(); will(returnValue(1));
						allowing(node1ChildGraph).numElements(); will(returnValue(2));
						allowing(node1ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
						allowing(node1ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//						allowing(node1ChildGraph).addNode(with(any(ICompoundNode.class)));
//						allowing(node1ChildGraph).addEdge(with(any(ICompoundEdge.class)));
					}});
					return node1ChildGraph;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}

				@Override
				public boolean buildChildGraph(final IChildCompoundGraph node1ChildGraph) {
					mockery.checking(new Expectations(){{
						allowing(node1ChildGraph).containsNode(with(equalTo(getNode2()))); will(returnValue(true));
						allowing(node1ChildGraph).containsNode(with(not(getNode2()))); will(returnValue(false));
						allowing(node1ChildGraph).containsNode(with(equalTo(NODE2_IDX))); will(returnValue(true));
						allowing(node1ChildGraph).containsNode(with(not(NODE2_IDX))); will(returnValue(false));
						allowing(node1ChildGraph).elementIterator(); will(returnIterator(getNode2()));
					}});
					return true;
				}


				@Override
				public boolean buildNode(final ICompoundNode node1) {
					mockery.checking(new Expectations(){{
						allowing(node1).childIterator(); will(returnIterator(getNode2()));
						allowing(node1).levelOrderIterator(); will(returnIterator(node1, getNode2(), getEdge3()));
						allowing(node1).hasEdgeWith(with(isOneOf(getNode6()))); will(returnValue(true));
						allowing(node1).hasEdgeWith(with(not(isOneOf(getNode6())))); will(returnValue(false));
						allowing(node1).hasOutEdgeTo(with(isOneOf(getNode6()))); will(returnValue(true));
						allowing(node1).hasOutEdgeTo(with(not(isOneOf(getNode6())))); will(returnValue(false));
						allowing(node1).hasInEdgeFrom(with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node1).getEdgesWith(with(isOneOf(getNode6()))); will(returnIterator(getEdge1()));
						allowing(node1).getEdgesWith(with(not(isOneOf(getNode6())))); will(throwException(new PreConditionException("mock exception")));
						allowing(node1).getOutEdgesTo(with(isOneOf(getNode6()))); will(returnIterator(getEdge1()));
						allowing(node1).getOutEdgesTo(with(not(isOneOf(getNode6())))); will(throwException(new PreConditionException("mock exception")));
						allowing(node1).getInEdgesFrom(with(any(ICompoundNode.class))); will(throwException(new PreConditionException("mock exception")));
						allowing(node1).connectedNodeIterator(); will(returnIterator(getNode6()));
						allowing(node1).edgeIterator(); will(returnIterator(getEdge1()));
						allowing(node1).getInEdgeIterator(); will(returnIterator());
						allowing(node1).getOutEdgeIterator(); will(returnIterator(getEdge1()));
						allowing(node1).isChild(with(isOneOf(getNode2(), getEdge3()))); will(returnValue(true));
						allowing(node1).isChild(with(not(isOneOf(getNode2(), getEdge3())))); will(returnValue(false));
					}});
					return true;
				}


				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}


				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return false;
				}


				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
			}),
			new NodeBuilder(NODE2_ID, new INodeConstructor(){
		
	
				@Override
				public boolean buildChildGraph(final IChildCompoundGraph node2ChildGraph) {
					mockery.checking(new Expectations(){{
						allowing(node2ChildGraph).elementIterator(); will(returnIterator(getEdge3()));
					}});
					return true;
				}

				@Override
				public boolean buildNode(final ICompoundNode node2) {
					mockery.checking(new Expectations(){{
						allowing(node2).childIterator(); will(returnIterator(getEdge3()));
						allowing(node2).isChild(with(isOneOf(getEdge3()))); will(returnValue(true));
						allowing(node2).isChild(with(not(isOneOf(getEdge3())))); will(returnValue(false));
						allowing(node2).levelOrderIterator(); will(returnIterator(node2, getEdge3()));
						allowing(node2).getEdgesWith(with(getNode3())); will(returnIterator(getEdge4()));
						allowing(node2).getEdgesWith(with(node2)); will(returnIterator(getEdge3(), getEdge3()));
						allowing(node2).getOutEdgesTo(with(getNode3())); will(returnIterator(getEdge4()));
						allowing(node2).getOutEdgesTo(with(node2)); will(returnIterator(getEdge3()));
						allowing(node2).hasOutEdgeTo(with(isOneOf(getNode3(), getNode2()))); will(returnValue(true));
						allowing(node2).hasOutEdgeTo(with(not(isOneOf(getNode3(), getNode2())))); will(returnValue(false));
						allowing(node2).getInEdgesFrom(with(node2)); will(returnIterator(getEdge3()));
						allowing(node2).hasInEdgeFrom(with(isOneOf(getNode2()))); will(returnValue(true));
						allowing(node2).hasInEdgeFrom(with(not(isOneOf(getNode2())))); will(returnValue(false));
						allowing(node2).edgeIterator(); will(returnIterator(getEdge4(), getEdge3(), getEdge3()));
					}});
					return true;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return true;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundNode node2) {
					final IChildCompoundGraph node2ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("node2ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(node2).getChildCompoundGraph(); will(returnValue(node2ChildGraph));
						allowing(node2ChildGraph).getRoot(); will(returnValue(node2));
						allowing(node2ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(node2ChildGraph).numNodes(); will(returnValue(0));
						allowing(node2ChildGraph).numEdges(); will(returnValue(1));
						allowing(node2ChildGraph).numElements(); will(returnValue(1));
						allowing(node2ChildGraph).numEdges(); will(returnValue(1));
						allowing(node2ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
						allowing(node2ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
					}});
					return node2ChildGraph;
				}

				@Override
				public ICompoundNode createCompoundNode() {
					final ICompoundNode node2 = mockery.mock(ICompoundNode.class, createMockName(NODE2_ID));
					mockery.checking(new Expectations(){{
						allowing(node2).getGraph(); will(returnValue(getGraph()));
						allowing(node2).getLevel(); will(returnValue(2));
						allowing(node2).getIndex(); will(returnValue(NODE2_IDX));
						allowing(node2).getParent(); will(returnValue(getNode1()));
						allowing(node2).getRoot(); will(returnValue(getRootNode()));
						allowing(node2).getDegree(); will(returnValue(3));
						allowing(node2).getInDegree(); will(returnValue(1));
						allowing(node2).getOutDegree(); will(returnValue(2));
						allowing(node2).isRemoved();  will(getRemovalState(NODE2_ID));;
						allowing(node2).getGraph(); will(returnValue(getGraph()));
						allowing(node2).isLink(); will(returnValue(false));
						allowing(node2).isNode(); will(returnValue(true));
//						allowing(node2).markRemoved(with(isOneOf(true, false)));
//						allowing(node2).hasEdgeWith(with(isOneOf(node2, getNode3()))); will(returnValue(true));
//						allowing(node2).hasEdgeWith(with(not(isOneOf(node2, getNode3())))); will(returnValue(false));
//						allowing(node2).hasOutEdgeTo(with(isOneOf(getNode3(), node2))); will(returnValue(true));
//						allowing(node2).hasOutEdgeTo(with(not(isOneOf(getNode3(), node2)))); will(returnValue(false));
//						allowing(node2).hasInEdgeFrom(with(isOneOf(node2))); will(returnValue(true));
//						allowing(node2).hasInEdgeFrom(with(not(isOneOf(node2)))); will(returnValue(false));
						allowing(node2).connectedNodeIterator(); will(returnIterator(getNode3(), node2, node2));
					}});
					return node2;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return false;
				}

				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
			}),
			new NodeBuilder(NODE3_ID, new INodeConstructor(){
		
				@Override
				public boolean buildChildGraph(IChildCompoundGraph node) {
					return true;
				}

				@Override
				public boolean buildNode(final ICompoundNode node3) {
					mockery.checking(new Expectations(){{
						allowing(node3).getOutEdgeIterator(); will(returnIterator(getEdge2()));
						allowing(node3).levelOrderIterator(); will(returnIterator(node3));
						allowing(node3).getInEdgeIterator(); will(returnIterator(getEdge4()));
						allowing(node3).getOutEdgesTo(with(isOneOf(getNode5()))); will(returnIterator(getEdge2()));
						allowing(node3).getInEdgesFrom(with(isOneOf(getNode2()))); will(returnIterator(getEdge4()));
						allowing(node3).connectedNodeIterator(); will(returnIterator(getNode2(), getNode5()));
						allowing(node3).getEdgesWith(with(getNode2())); will(returnIterator(getEdge4()));
						allowing(node3).getEdgesWith(with(getNode5())); will(returnIterator(getEdge2()));
						allowing(node3).edgeIterator(); will(returnIterator(getEdge2(), getEdge4()));
						allowing(node3).hasEdgeWith(with(isOneOf(getNode2(), getNode5()))); will(returnValue(true));
						allowing(node3).hasEdgeWith(with(not(isOneOf(getNode2(), getNode5())))); will(returnValue(false));
						allowing(node3).hasOutEdgeTo(with(isOneOf(getNode5()))); will(returnValue(true));
						allowing(node3).hasOutEdgeTo(with(not(isOneOf(getNode5())))); will(returnValue(false));
						allowing(node3).hasInEdgeFrom(with(isOneOf(getNode2()))); will(returnValue(true));
						allowing(node3).hasInEdgeFrom(with(not(isOneOf(getNode2())))); will(returnValue(false));
						allowing(node3).isChild(with(any(ICompoundGraphElement.class))); will(returnValue(false));
					}});
					return true;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundNode node3) {
					final IChildCompoundGraph node3ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("node3ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(node3).getChildCompoundGraph(); will(returnValue(node3ChildGraph));

						allowing(node3ChildGraph).getRoot(); will(returnValue(node3));
						allowing(node3ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(node3ChildGraph).numNodes(); will(returnValue(0));
						allowing(node3ChildGraph).numEdges(); will(returnValue(0));
						allowing(node3ChildGraph).numElements(); will(returnValue(0));
						allowing(node3ChildGraph).containsNode(with(any(Integer.class))); will(returnValue(false));
						allowing(node3ChildGraph).containsNode(with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node3ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
						allowing(node3ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
						allowing(node3ChildGraph).elementIterator(); will(returnIterator());
					}});
					return node3ChildGraph;
				}

				@Override
				public ICompoundNode createCompoundNode() {
					final ICompoundNode node3 = mockery.mock(ICompoundNode.class, createMockName(NODE3_ID));
					mockery.checking(new Expectations(){{
						allowing(node3).getGraph(); will(returnValue(getGraph()));
						allowing(node3).getLevel(); will(returnValue(2));
						allowing(node3).getIndex(); will(returnValue(NODE3_IDX));
						allowing(node3).getParent(); will(returnValue(getEdge1()));
						allowing(node3).getRoot(); will(returnValue(getRootNode()));
						allowing(node3).childIterator(); will(returnIterator());
						allowing(node3).isRemoved();  will(getRemovalState(NODE3_ID));;
						allowing(node3).getDegree(); will(returnValue(2));
						allowing(node3).getInDegree(); will(returnValue(1));
						allowing(node3).getOutDegree(); will(returnValue(1));
//						allowing(node3).addInEdge(with(any(ICompoundEdge.class)));
//						allowing(node3).addOutEdge(with(any(ICompoundEdge.class)));
						allowing(node3).isLink(); will(returnValue(false));
						allowing(node3).isNode(); will(returnValue(true));
						allowing(node3).connectedNodeIterator(); will(returnIterator(getNode2(), getNode5()));
//						allowing(node3).markRemoved(with(isOneOf(true, false)));
						allowing(node3).isAncestor(with(isOneOf(getEdge1(), getRootNode()))); will(returnValue(true));
						allowing(node3).isAncestor(with(not(isOneOf(getEdge1(), getRootNode())))); will(returnValue(false));
					}});
					return node3;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return false;
				}

				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
		
			}),
			new NodeBuilder(NODE4_ID, new INodeConstructor(){

				@Override
				public boolean buildChildGraph(IChildCompoundGraph node) {
					return true;
				}

				@Override
				public boolean buildNode(ICompoundNode node) {
					return true;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundNode node4) {
					final IChildCompoundGraph node4ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("node4ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(node4).getChildCompoundGraph(); will(returnValue(node4ChildGraph));

						allowing(node4ChildGraph).getRoot(); will(returnValue(node4));
						allowing(node4ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(node4ChildGraph).numNodes(); will(returnValue(0));
						allowing(node4ChildGraph).numEdges(); will(returnValue(0));
						allowing(node4ChildGraph).numElements(); will(returnValue(0));
						allowing(node4ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
						allowing(node4ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
						allowing(node4ChildGraph).elementIterator(); will(returnIterator());
					}});
					return node4ChildGraph;
				}

				@Override
				public ICompoundNode createCompoundNode() {
					final ICompoundNode node4 = mockery.mock(ICompoundNode.class, createMockName("node4"));
					mockery.checking(new Expectations(){{
						allowing(node4).getLevel(); will(returnValue(3));
						allowing(node4).getIndex(); will(returnValue(NODE4_IDX));
						allowing(node4).getParent(); will(returnValue(getEdge2()));
						allowing(node4).getRoot(); will(returnValue(getRootNode()));
						allowing(node4).childIterator(); will(returnIterator());
						allowing(node4).levelOrderIterator(); will(returnIterator(node4));
						allowing(node4).isRemoved();  will(getRemovalState(NODE4_ID));;
						allowing(node4).getGraph(); will(returnValue(getGraph()));
						allowing(node4).isLink(); will(returnValue(false));
						allowing(node4).isNode(); will(returnValue(true));
						allowing(node4).hasEdgeWith(with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node4).connectedNodeIterator(); will(returnIterator());
						allowing(node4).edgeIterator(); will(returnIterator());
						allowing(node4).getInEdgeIterator(); will(returnIterator());
						allowing(node4).getOutEdgeIterator(); will(returnIterator());
//						allowing(node4).markRemoved(with(isOneOf(true, false)));
					}});
					return node4;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return false;
				}

				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
		
			}),
			new NodeBuilder(NODE5_ID, new INodeConstructor(){

				@Override
				public boolean buildChildGraph(IChildCompoundGraph node) {
					return true;
				}

				@Override
				public boolean buildNode(final ICompoundNode node5) {
					mockery.checking(new Expectations(){{
						allowing(node5).compareTo(with(isOneOf(getNode4()))); will(returnValue(1));
						allowing(node5).compareTo(with(isOneOf(getEdge2(), getEdge4()))); will(returnValue(-1));
						allowing(node5).hasEdgeWith(getNode3()); will(returnValue(true));
						allowing(node5).hasEdgeWith(with(not(getNode3()))); will(returnValue(false));
						allowing(node5).getEdgesWith(getNode3()); will(returnIterator(getEdge2()));
						allowing(node5).getInEdgesFrom(with(isOneOf(getNode3()))); will(returnIterator( getEdge2() ));
						allowing(node5).getInEdgesFrom(with(not(isOneOf(getNode3())))); will(throwException(new PostConditionException("mock exception")));
						allowing(node5).connectedNodeIterator(); will(returnIterator(getNode3()));
						allowing(node5).edgeIterator(); will(returnIterator(getEdge2()));
						allowing(node5).isChild(with(any(ICompoundGraphElement.class))); will(returnValue(false));
					}});
					return true;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundNode node5) {
					final IChildCompoundGraph node5ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("node5ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(node5).getChildCompoundGraph(); will(returnValue(node5ChildGraph));

						allowing(node5ChildGraph).getRoot(); will(returnValue(node5));
						allowing(node5ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(node5ChildGraph).numNodes(); will(returnValue(0));
						allowing(node5ChildGraph).numEdges(); will(returnValue(0));
						allowing(node5ChildGraph).numElements(); will(returnValue(0));
						allowing(node5ChildGraph).containsDirectedEdge(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node5ChildGraph).containsConnection(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node5ChildGraph).elementIterator(); will(returnIterator());
					}});
					return node5ChildGraph;
				}

				@Override
				public ICompoundNode createCompoundNode() {
					final ICompoundNode node5 = mockery.mock(ICompoundNode.class, createMockName("node5"));
					mockery.checking(new Expectations(){{
						allowing(node5).getLevel(); will(returnValue(2));
						allowing(node5).getIndex(); will(returnValue(NODE5_IDX));
						allowing(node5).getParent(); will(returnValue(getEdge1()));
						allowing(node5).getRoot(); will(returnValue(getRootNode()));
						allowing(node5).childIterator(); will(returnIterator());
						allowing(node5).isRemoved();  will(getRemovalState(NODE5_ID));;
//						allowing(node5).addInEdge(with(any(ICompoundEdge.class)));
//						allowing(node5).addOutEdge(with(any(ICompoundEdge.class)));
						allowing(node5).isAncestor(with(isOneOf(getEdge1(), getRootNode()))); will(returnValue(true));
						allowing(node5).isAncestor(with(not(isOneOf(getEdge1(), getRootNode())))); will(returnValue(false));
						allowing(node5).getGraph(); will(returnValue(getGraph()));
						allowing(node5).isLink(); will(returnValue(false));
						allowing(node5).isNode(); will(returnValue(true));
						allowing(node5).compareTo(node5); will(returnValue(0));
						allowing(node5).compareTo(with(isOneOf(getRootNode(), getNode1(), getNode2(), getNode3()))); will(returnValue(1));
						allowing(node5).compareTo(with(isOneOf(getEdge1(), getEdge3()))); will(returnValue(-1));
						allowing(node5).hasOutEdgeTo(with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node5).getOutEdgesTo(with(any(ICompoundNode.class))); will(throwException(new PostConditionException("mock exception")));
//						allowing(node5).markRemoved(with(isOneOf(true, false)));
					}});
					return node5;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return false;
				}

				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
		
			}),
			new NodeBuilder(NODE6_ID, new INodeConstructor(){

				@Override
				public boolean buildChildGraph(final IChildCompoundGraph childGraph) {
					return true;
				}

				@Override
				public boolean buildNode(final ICompoundNode node6) {
					mockery.checking(new Expectations(){{
						allowing(node6).compareTo(with(isOneOf(getNode3(), getNode4(), getNode5()))); will(returnValue(1));
						allowing(node6).compareTo(with(isOneOf(getEdge1(), getEdge2(), getEdge4()))); will(returnValue(-1));
						allowing(node6).hasEdgeWith(getNode1()); will(returnValue(true));
						allowing(node6).hasEdgeWith(with(not(getNode1()))); will(returnValue(false));
						allowing(node6).getEdgesWith(getNode1()); will(returnIterator(getEdge1()));
						allowing(node6).getEdgesWith(with(not(getNode1()))); will(returnIterator());
						allowing(node6).getInEdgesFrom(with(isOneOf(getNode1()))); will(returnIterator( getEdge1() ));
						allowing(node6).hasOutEdgeTo(with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node6).connectedNodeIterator(); will(returnIterator(getNode1()));
						allowing(node6).edgeIterator(); will(returnIterator(getEdge1()));
						allowing(node6).levelOrderIterator(); will(returnIterator(node6));
					}});
					return true;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return true;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundNode node6) {
					final IChildCompoundGraph node6ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("node6ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(node6).getChildCompoundGraph(); will(returnValue(node6ChildGraph));

						allowing(node6ChildGraph).getRoot(); will(returnValue(node6));
						allowing(node6ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(node6ChildGraph).numNodes(); will(returnValue(0));
						allowing(node6ChildGraph).numEdges(); will(returnValue(0));
						allowing(node6ChildGraph).numElements(); will(returnValue(0));
						allowing(node6ChildGraph).containsDirectedEdge(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node6ChildGraph).containsConnection(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
						allowing(node6ChildGraph).elementIterator(); will(returnIterator());
					}});
					return node6ChildGraph;
				}

				@Override
				public ICompoundNode createCompoundNode() {
					final ICompoundNode node6 = mockery.mock(ICompoundNode.class, createMockName("node6"));
					mockery.checking(new Expectations(){{
						allowing(node6).getLevel(); will(returnValue(1));
						allowing(node6).getIndex(); will(returnValue(NODE6_IDX));
						allowing(node6).getParent(); will(returnValue(getRootNode()));
						allowing(node6).getRoot(); will(returnValue(getRootNode()));
						allowing(node6).childIterator(); will(returnIterator());
						allowing(node6).isRemoved();  will(getRemovalState(NODE6_ID));;
//						allowing(node6).addInEdge(with(any(ICompoundEdge.class)));
//						allowing(node6).addOutEdge(with(any(ICompoundEdge.class)));
						allowing(node6).getGraph(); will(returnValue(getGraph()));
						allowing(node6).isLink(); will(returnValue(false));
						allowing(node6).isNode(); will(returnValue(true));
						allowing(node6).compareTo(node6); will(returnValue(0));
						allowing(node6).compareTo(with(isOneOf(getRootNode(), getNode1(), getNode2()))); will(returnValue(1));
						allowing(node6).compareTo(with(isOneOf(getEdge3()))); will(returnValue(-1));
//						allowing(node6).markRemoved(with(isOneOf(true, false)));
					}});
					return node6;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(final IChildCompoundGraph childGraph) {
					final ICompoundNodeFactory nodeFactory = mockery.mock(ICompoundNodeFactory.class, createMockName("node6NodeFactory"));
					mockery.checking(new Expectations(){{
						allowing(childGraph).nodeFactory(); will(returnValue(nodeFactory));
						
						allowing(nodeFactory).getParentNode(); will(returnValue(childGraph.getRoot()));
					}});
					return nodeFactory;
				}
		

				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(final IChildCompoundGraph childGraph) {
					final ICompoundChildEdgeFactory edgeFactory = mockery.mock(ICompoundChildEdgeFactory.class, createMockName("node6EdgeFactory"));
					mockery.checking(new Expectations(){{
						allowing(childGraph).edgeFactory(); will(returnValue(edgeFactory));
						
						allowing(edgeFactory).getParent(); will(returnValue(childGraph.getRoot()));
					}});
					return edgeFactory;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return true;
				}
		
			}),
			new EdgeBuilder(EDGE1_ID, new IEdgeConstructor(){

				@Override
				public boolean buildChildGraph(final IChildCompoundGraph edge1ChildGraph) {
					mockery.checking(new Expectations(){{
						allowing(edge1ChildGraph).containsNode(with(isOneOf(getNode3(), getNode5()))); will(returnValue(true));
						allowing(edge1ChildGraph).containsNode(with(not(isOneOf(getNode3(), getNode5())))); will(returnValue(false));
						allowing(edge1ChildGraph).containsEdge(getEdge2()); will(returnValue(true));
						allowing(edge1ChildGraph).containsEdge(with(not(getEdge2()))); will(returnValue(false));
						allowing(edge1ChildGraph).containsEdge(with(EDGE2_IDX)); will(returnValue(true));
						allowing(edge1ChildGraph).containsEdge(with(not(EDGE2_IDX))); will(returnValue(false));
						allowing(edge1ChildGraph).containsNode(with(isOneOf(NODE3_IDX, NODE5_IDX))); will(returnValue(true));
						allowing(edge1ChildGraph).containsNode(with(not(isOneOf(NODE3_IDX, NODE5_IDX)))); will(returnValue(false));
						allowing(edge1ChildGraph).containsDirectedEdge(with(getNode3()), with(getNode5())); will(returnValue(true));
						allowing(edge1ChildGraph).containsDirectedEdge(with(not(getNode3())), with(not(getNode5()))); will(returnValue(false));
						allowing(edge1ChildGraph).containsConnection(with(getNode3()), with(getNode5())); will(returnValue(true));
						allowing(edge1ChildGraph).containsConnection(with(getNode5()), with(getNode3())); will(returnValue(true));
						allowing(edge1ChildGraph).containsConnection(with(not(getNode3())), with(not(getNode5()))); will(returnValue(false));
						allowing(edge1ChildGraph).elementIterator(); will(returnIterator(getNode3(), getEdge2(), getNode5()));
//						allowing(edge1ChildGraph).addEdge(with(any(ICompoundEdge.class)));
						allowing(edge1ChildGraph).nodeIterator(); will(returnIterator(getNode3(), getNode5()));
					}});
					return true;
				}

				@Override
				public boolean buildEdge(final ICompoundEdge edge1) {
					final CompoundNodePair edge1Ends = new CompoundNodePair(getNode1(), getNode6());
					mockery.checking(new Expectations(){{
						allowing(edge1).compareTo(getEdge2()); will(returnValue(1));
						allowing(edge1).compareTo(getEdge3()); will(returnValue(1));
						allowing(edge1).compareTo(getEdge4()); will(returnValue(1));
						allowing(edge1).getConnectedNodes(); will(returnValue(edge1Ends));
						allowing(edge1).hasEnds(with(isOneOf(edge1Ends, edge1Ends.reversedNodes()))); will(returnValue(true)); 
						allowing(edge1).hasEnds(with(not(isOneOf(edge1Ends, edge1Ends.reversedNodes())))); will(returnValue(false));
						allowing(edge1).childIterator(); will(returnIterator(getNode3(), getEdge2(), getNode5()));
						allowing(edge1).levelOrderIterator(); will(returnIterator(edge1, getNode3(), getEdge2(), getNode5(), getNode4()));
					}});
					return true;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return true;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundEdge edge1) {
					final IChildCompoundGraph edge1ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("edge1ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(edge1).getChildCompoundGraph(); will(returnValue(edge1ChildGraph));

						allowing(edge1ChildGraph).getRoot(); will(returnValue(edge1));
						allowing(edge1ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(edge1ChildGraph).numNodes(); will(returnValue(2));
						allowing(edge1ChildGraph).numEdges(); will(returnValue(1));
						allowing(edge1ChildGraph).numElements(); will(returnValue(3));
					}});
					return edge1ChildGraph;
				}

				@Override
				public ICompoundEdge createCompoundEdge() {
					final ICompoundEdge edge1 = mockery.mock(ICompoundEdge.class, createMockName("edge1"));
					mockery.checking(new Expectations(){{
						allowing(edge1).getLevel(); will(returnValue(1));
						allowing(edge1).getIndex(); will(returnValue(EDGE1_IDX));
						allowing(edge1).getParent(); will(returnValue(getRootNode()));
						allowing(edge1).getRoot(); will(returnValue(getRootNode()));
						allowing(edge1).isRemoved();  will(getRemovalState(EDGE1_ID));;
						allowing(edge1).getGraph(); will(returnValue(getGraph()));
						allowing(edge1).compareTo(edge1); will(returnValue(0));
						allowing(edge1).isLink(); will(returnValue(true));
						allowing(edge1).isNode(); will(returnValue(false));
//						allowing(edge1).markRemoved(with(isOneOf(true, false)));
					}});
					return edge1;
				}

				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(final IChildCompoundGraph childGraph) {
					final ICompoundChildEdgeFactory edge1ChildEdgeFactory = mockery.mock(ICompoundChildEdgeFactory.class, createMockName("edge1ChildEdgeFactory"));
					mockery.checking(new Expectations(){{
						allowing(childGraph).edgeFactory(); will(returnValue(edge1ChildEdgeFactory));
						
//						allowing(edge1ChildEdgeFactory).setPair(with(any(CompoundNodePair.class)));
//						ignoring(edge1ChildEdgeFactory).createEdge();
					}});
					return edge1ChildEdgeFactory;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
				
			}), 
			new EdgeBuilder(EDGE2_ID, new IEdgeConstructor(){

				@Override
				public boolean buildChildGraph(final IChildCompoundGraph edge2ChildGraph) {
					mockery.checking(new Expectations(){{
						allowing(edge2ChildGraph).elementIterator(); will(returnIterator(getNode4()));
						allowing(edge2ChildGraph).nodeIterator(); will(returnIterator(getNode4()));
					}});
					return true;
				}

				@Override
				public boolean buildEdge(final ICompoundEdge edge2) {
					final CompoundNodePair edge2Ends = new CompoundNodePair(getNode3(), getNode5());
					mockery.checking(new Expectations(){{
						allowing(edge2).childIterator(); will(returnIterator(getNode4()));
						allowing(edge2).levelOrderIterator(); will(returnIterator(edge2, getNode4()));
						allowing(edge2).compareTo(with(isOneOf(getRootNode(), getNode1(), getNode2(), getNode3(), getNode4(), getNode5(), getEdge1()))); will(returnValue(1));
						allowing(edge2).compareTo(edge2); will(returnValue(0));
						allowing(edge2).compareTo(with(isOneOf(getEdge3(), getEdge4()))); will(returnValue(-1));
						allowing(edge2).getConnectedNodes(); will(returnValue(edge2Ends));
						allowing(edge2).hasEnds(with(isOneOf(edge2Ends, edge2Ends.reversedNodes()))); will(returnValue(true)); 
						allowing(edge2).hasEnds(with(not(isOneOf(edge2Ends, edge2Ends.reversedNodes())))); will(returnValue(false));
						allowing(edge2).isChild(with(isOneOf(getNode4()))); will(returnValue(true));
						allowing(edge2).isChild(with(not(isOneOf(getNode4())))); will(returnValue(false));
					}});
					return true;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return false;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundEdge edge2) {
					final IChildCompoundGraph edge2ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("edge2ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(edge2).getChildCompoundGraph(); will(returnValue(edge2ChildGraph));

						allowing(edge2ChildGraph).getRoot(); will(returnValue(edge2));
						allowing(edge2ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(edge2ChildGraph).numNodes(); will(returnValue(1));
						allowing(edge2ChildGraph).numEdges(); will(returnValue(0));
						allowing(edge2ChildGraph).numElements(); will(returnValue(1));
						allowing(edge2ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
						allowing(edge2ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
					}});
					return edge2ChildGraph;
				}

				@Override
				public ICompoundEdge createCompoundEdge() {
					final ICompoundEdge edge2 = mockery.mock(ICompoundEdge.class, createMockName(EDGE2_ID));
					mockery.checking(new Expectations(){{
						allowing(edge2).getLevel(); will(returnValue(2));
						allowing(edge2).getIndex(); will(returnValue(EDGE2_IDX));
						allowing(edge2).getParent(); will(returnValue(getEdge1()));
						allowing(edge2).getRoot(); will(returnValue(getRootNode()));
						allowing(edge2).isRemoved(); will(getRemovalState(EDGE2_ID));
						allowing(edge2).getGraph(); will(returnValue(getGraph()));
						allowing(edge2).isLink(); will(returnValue(true));
						allowing(edge2).isNode(); will(returnValue(false));
//						allowing(edge2).markRemoved(with(isOneOf(true, false)));
					}});
					return edge2;
				}

				@Override
				public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
					return null;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
				
			}),
			new EdgeBuilder(EDGE3_ID, new IEdgeConstructor(){

				@Override
				public boolean buildChildGraph(IChildCompoundGraph childGraph) {
					return true;
				}

				@Override
				public boolean buildEdge(final ICompoundEdge edge3) {
					final CompoundNodePair edge3Ends = new CompoundNodePair(getNode2(), getNode2());
					mockery.checking(new Expectations(){{
						allowing(edge3).compareTo(with(isOneOf(getRootNode(), getNode1(), getNode2(), getNode3(), getNode4(), getNode5(), getEdge1(), getEdge2()))); will(returnValue(1));
						allowing(edge3).compareTo(with(isOneOf(getEdge4()))); will(returnValue(-1));
						allowing(edge3).getConnectedNodes(); will(returnValue(edge3Ends));
						allowing(edge3).hasEnds(with(isOneOf(edge3Ends, edge3Ends.reversedNodes()))); will(returnValue(true)); 
						allowing(edge3).hasEnds(with(not(isOneOf(edge3Ends, edge3Ends.reversedNodes())))); will(returnValue(false));
					}});
					return false;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return true;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundEdge edge3) {
					final IChildCompoundGraph edge3ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("edge3ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(edge3).getChildCompoundGraph(); will(returnValue(edge3ChildGraph));

						allowing(edge3ChildGraph).getRoot(); will(returnValue(edge3));
						allowing(edge3ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(edge3ChildGraph).numNodes(); will(returnValue(0));
						allowing(edge3ChildGraph).numEdges(); will(returnValue(0));
						allowing(edge3ChildGraph).numElements(); will(returnValue(0));
						allowing(edge3ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
						allowing(edge3ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
						allowing(edge3ChildGraph).elementIterator(); will(returnIterator());
					}});
					return edge3ChildGraph;
				}

				@Override
				public ICompoundEdge createCompoundEdge() {
					final ICompoundEdge edge3 = mockery.mock(ICompoundEdge.class, createMockName("edge3"));
					mockery.checking(new Expectations(){{
						allowing(edge3).getGraph(); will(returnValue(getGraph()));
						allowing(edge3).getLevel(); will(returnValue(3));
						allowing(edge3).getIndex(); will(returnValue(EDGE3_IDX));
						allowing(edge3).getParent(); will(returnValue(getNode2()));
						allowing(edge3).getRoot(); will(returnValue(getRootNode()));
						allowing(edge3).childIterator(); will(returnIterator());
						allowing(edge3).levelOrderIterator(); will(returnIterator(edge3));
						allowing(edge3).isRemoved(); will(getRemovalState(EDGE3_ID));
						allowing(edge3).isLink(); will(returnValue(true));
						allowing(edge3).isNode(); will(returnValue(false));
						allowing(edge3).compareTo(edge3); will(returnValue(0));
//						allowing(edge3).markRemoved(with(isOneOf(true, false)));
					}});
					return edge3;
				}

				@Override
				public ICompoundChildEdgeFactory createEdgeFactory(final IChildCompoundGraph edge3ChildGraph) {
					final ICompoundChildEdgeFactory edge3ChildEdgeFactory = mockery.mock(ICompoundChildEdgeFactory.class, createMockName("edge3ChildEdgeFactory"));
					mockery.checking(new Expectations(){{
						allowing(edge3ChildGraph).edgeFactory(); will(returnValue(edge3ChildEdgeFactory));
					}});
					return edge3ChildEdgeFactory;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
				
			}),
			new EdgeBuilder(EDGE4_ID, new IEdgeConstructor(){

				@Override
				public boolean buildChildGraph(IChildCompoundGraph childGraph) {
					return true;
				}

				@Override
				public boolean buildEdge(final ICompoundEdge edge4) {
					final CompoundNodePair edge4Ends = new CompoundNodePair(getNode2(), getNode3());
					mockery.checking(new Expectations(){{
						allowing(edge4).getConnectedNodes(); will(returnValue(edge4Ends));
						allowing(edge4).hasEnds(with(isOneOf(edge4Ends, edge4Ends.reversedNodes()))); will(returnValue(true)); 
						allowing(edge4).hasEnds(with(not(isOneOf(edge4Ends, edge4Ends.reversedNodes())))); will(returnValue(false));
						allowing(edge4).isChild(with(any(ICompoundGraphElement.class))); will(returnValue(false));
					}});
					return true;
				}

				@Override
				public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
					return false;
				}

				@Override
				public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
					return false;
				}

				@Override
				public IChildCompoundGraph createCompoundChildGraph(final ICompoundEdge edge4) {
					final IChildCompoundGraph edge4ChildGraph = mockery.mock(IChildCompoundGraph.class, createMockName("edge4ChildGraph"));
					mockery.checking(new Expectations(){{
						allowing(edge4).getChildCompoundGraph(); will(returnValue(edge4ChildGraph));

						allowing(edge4ChildGraph).getRoot(); will(returnValue(edge4));
						allowing(edge4ChildGraph).getSuperGraph(); will(returnValue(getGraph()));
						allowing(edge4ChildGraph).numNodes(); will(returnValue(0));
						allowing(edge4ChildGraph).numEdges(); will(returnValue(0));
						allowing(edge4ChildGraph).numElements(); will(returnValue(0));
						allowing(edge4ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
						allowing(edge4ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
						allowing(edge4ChildGraph).elementIterator(); will(returnIterator());
					}});
					return edge4ChildGraph;
				}

				@Override
				public ICompoundEdge createCompoundEdge() {
					final ICompoundEdge edge4 = mockery.mock(ICompoundEdge.class, createMockName("edge4"));
					mockery.checking(new Expectations(){{
						allowing(edge4).getLevel(); will(returnValue(1));
						allowing(edge4).getIndex(); will(returnValue(EDGE4_IDX));
						allowing(edge4).getParent(); will(returnValue(getRootNode()));
						allowing(edge4).getRoot(); will(returnValue(getRootNode()));
						allowing(edge4).childIterator(); will(returnIterator());
						allowing(edge4).levelOrderIterator(); will(returnIterator(edge4));
						allowing(edge4).isRemoved(); will(getRemovalState(EDGE4_ID));
						allowing(edge4).getGraph(); will(returnValue(getGraph()));
						allowing(edge4).compareTo(getRootNode()); will(returnValue(-1));
						allowing(edge4).compareTo(with(not(edge4))); will(returnValue(-1));
						allowing(edge4).compareTo(edge4); will(returnValue(0));
						allowing(edge4).isLink(); will(returnValue(true));
						allowing(edge4).isNode(); will(returnValue(false));
//						allowing(edge4).markRemoved(with(isOneOf(true, false)));
					}});
					return edge4;
				}

				@Override
				public ICompoundEdgeFactory createEdgeFactory(IChildCompoundGraph childGraph) {
					return null;
				}

				@Override
				public ICompoundNodeFactory createNodeFactory(IChildCompoundGraph childGraph) {
					return null;
				}
				
			}),
	};

	
	public class RemoveElementAction implements Action {
		private final String elementId;
		
		public RemoveElementAction(String elementId){
			this.elementId = elementId;
		}
		
		@Override
		public void describeTo(Description descn) {
			descn.appendText("sets removal state to: ").appendValue(this.elementId);
		}

		@Override
		public Object invoke(Invocation invocation) throws Throwable {
			Boolean removalState = (Boolean)invocation.getParameter(0);
			elementRemovalMap.put(elementId, removalState);
			return null;
		}
		
	}
	
	@Override
	public Action setRemovalState(String elementId){
		return new RemoveElementAction(elementId);
	}
	
	
	public class GetRemoveElementAction implements Action {
		private String elementId;
		
		public GetRemoveElementAction(String elementId){
			this.elementId = elementId;
		}
		
		@Override
		public void describeTo(Description descn) {
			descn.appendText("get removal state");
		}

		@Override
		public Object invoke(Invocation invocation) throws Throwable {
			boolean retVal = elementRemovalMap.get(elementId);
			return retVal;
		}
		
	}
	
	public Action getRemovalState(String elementId){
		return new GetRemoveElementAction(elementId);
	}
	
	
	public ComplexGraphFixture(final Mockery mockery, String prefix){
		this.mockery = mockery;
		this.prefix = prefix;
		this.builderMap = new HashMap<String, IGraphObjectBuilder>();
		this.elementRemovalMap = new HashMap<String, Boolean>();
		IGraphObjectBuilder graphBuilder = new GraphBuilder(GRAPH_ID, new IGraphConstructor() {
			
			@Override
			public IRootCompoundNode createRootNode(final ICompoundGraph graph) {
				final IRootCompoundNode rootNode = mockery.mock(IRootCompoundNode.class, createMockName("rootNode"));
				mockery.checking(new Expectations(){{
					allowing(graph).getRoot(); will(returnValue(rootNode));
					
					allowing(rootNode).getLevel(); will(returnValue(0));
					allowing(rootNode).getIndex(); will(returnValue(0));
					allowing(rootNode).getParent(); will(returnValue(rootNode));
					allowing(rootNode).getRoot(); will(returnValue(rootNode));
					allowing(rootNode).getGraph(); will(returnValue(graph));
					allowing(rootNode).isRemoved(); will(returnValue(false));
					allowing(rootNode).isLink(); will(returnValue(false));
					allowing(rootNode).isNode(); will(returnValue(true));
					allowing(rootNode).connectedNodeIterator(); will(returnIterator());
//					allowing(rootNode).markRemoved(with(isOneOf(true, false)));
				}});
				return rootNode;
			}
			
			@Override
			public ICompoundNodeFactory createNodeFactory(final ICompoundGraph graph) {
				final ICompoundNodeFactory nodeFactory = mockery.mock(ICompoundNodeFactory.class, createMockName("nodeFactory"));
				final IRootChildCompoundGraph childGraph = graph.getRoot().getChildCompoundGraph();
				mockery.checking(new Expectations(){{
					allowing(graph).nodeFactory(); will(returnValue(nodeFactory));
					
					allowing(childGraph).nodeFactory(); will(returnValue(nodeFactory));
					
//					ignoring(nodeFactory);
				}});
				return nodeFactory;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public ICompoundGraph createGraph() {
				final ICompoundGraph graph = mockery.mock(ICompoundGraph.class, createMockName("graph"));
				final ITree<ICompoundGraphElement> elementTree = mockery.mock(ITree.class, createMockName("elementTree"));
				mockery.checking(new Expectations(){{
					allowing(graph).getElementTree(); will(returnValue(elementTree));
					allowing(graph).numElements(); will(returnValue(11));
				}});
				return graph;
			}
			
			@Override
			public ICompoundEdgeFactory createEdgeFactory(final ICompoundGraph graph) {
				final ICompoundEdgeFactory edgeFactory = mockery.mock(ICompoundEdgeFactory.class, createMockName("edgeFactory"));
				mockery.checking(new Expectations(){{
					allowing(graph).edgeFactory(); will(returnValue(edgeFactory));
					
//					ignoring(edgeFactory);
				}});
				return edgeFactory;
			}
			
			@Override
			public IRootChildCompoundGraph createChildGraph(final IRootCompoundNode rootNode) {
				final IRootChildCompoundGraph rootChildGraph = mockery.mock(IRootChildCompoundGraph.class, createMockName("rootChildGraph"));
				final ICompoundChildEdgeFactory rootChildEdgeFactory = mockery.mock(ICompoundChildEdgeFactory.class, createMockName("rootChildGraphEdgeFactory"));
				final ICompoundGraph graph = rootNode.getGraph();
				mockery.checking(new Expectations(){{
					allowing(rootNode).getChildCompoundGraph(); will(returnValue(rootChildGraph));

					allowing(rootChildGraph).getRoot(); will(returnValue(rootNode));
					allowing(rootChildGraph).getSuperGraph(); will(returnValue(graph));
					allowing(rootChildGraph).numNodes(); will(returnValue(6));
					allowing(rootChildGraph).numEdges(); will(returnValue(4));
					allowing(rootChildGraph).numElements(); will(returnValue(9));

					allowing(rootChildGraph).edgeFactory(); will(returnValue(rootChildEdgeFactory));
					
//					ignoring(rootChildEdgeFactory);
				}});
				return rootChildGraph;
			}
			
			@Override
			public boolean buildRootNode(final IRootCompoundNode rootNode) {
				mockery.checking(new Expectations(){{
					allowing(rootNode).childIterator(); will(returnIterator(getNode1(), getNode6(), getEdge1(), getEdge4()));
				}});
				return true;
			}
			
			@Override
			public boolean buildNodeFactory(ICompoundNodeFactory nodeFactory) {
				return true;
			}
			
			@Override
			public boolean buildGraph(final ICompoundGraph graph) {
				final IRootCompoundNode rootNode = graph.getRoot();
				final ITree<ICompoundGraphElement> elementTree = graph.getElementTree();
				mockery.checking(new Expectations(){{
					allowing(graph).nodeIterator(); will(returnIterator(rootNode, getNode1(), getNode6(), getNode5(), getNode2(), getNode3(), getNode4()));
					allowing(graph).restorableElementIterator(); will(returnIterator(rootNode, getNode1(), getNode6(), getNode5(), getNode2(), getNode3(), getNode4(), getEdge1(), getEdge2(), getEdge3(), getEdge4()));
					
					allowing(elementTree).isAncestor(with(getEdge2()), with(isOneOf(getEdge1(), getRootNode()))); will(returnValue(true));
					allowing(elementTree).isAncestor(with(equalTo(getEdge2())), with(not(isOneOf(getEdge1(), getRootNode())))); will(returnValue(false));
					allowing(elementTree).isDescendant(with(getEdge2()), with(isOneOf(getNode4()))); will(returnValue(true));
					allowing(elementTree).isDescendant(with(getEdge2()), with(not(isOneOf(getNode4())))); will(returnValue(false));
					
					allowing(elementTree).isAncestor(getNode1(), getRootNode()); will(returnValue(true));
					allowing(elementTree).isAncestor(with(equalTo(getNode1())), with(not(getRootNode()))); will(returnValue(false));
					allowing(elementTree).isDescendant(with(getNode1()), with(isOneOf(getNode2(), getEdge3()))); will(returnValue(true));
					allowing(elementTree).isDescendant(with(getNode1()), with(not(isOneOf(getNode2(), getEdge3())))); will(returnValue(false));

					allowing(elementTree).isDescendant(with(getNode2()), with(isOneOf(getEdge3()))); will(returnValue(true));
					allowing(elementTree).isDescendant(with(getNode2()), with(not(isOneOf(getEdge3())))); will(returnValue(false));
					
					allowing(elementTree).getLowestCommonAncestor(getNode3(), getNode5()); will(returnValue(getEdge1()));
					allowing(elementTree).getLowestCommonAncestor(getNode5(), getNode3()); will(returnValue(getEdge1()));
					allowing(elementTree).getLowestCommonAncestor(getNode2(), getNode3()); will(returnValue(getRootNode()));
				}});
				return true;
			}
			
			@Override
			public boolean buildEdgeFactory(ICompoundEdgeFactory edgeFactory) {
				return true;
			}
			
			@Override
			public boolean buildChildGraph(final IRootChildCompoundGraph rootChildGraph) {
				mockery.checking(new Expectations(){{
					allowing(rootChildGraph).elementIterator(); will(returnIterator(getEdge4(), getNode1(), getEdge1(), getNode6()));
				}});
				return true;
			}

			@Override
			public boolean buildSubgraphFactory(ISubCompoundGraphFactory subgraphFactory) {
				return true;
			}

			@Override
			public ISubCompoundGraphFactory createSubgraphFactory(final ICompoundGraph graph) {
				final ISubCompoundGraphFactory subgraphFactory = mockery.mock(ISubCompoundGraphFactory.class, createMockName("subgraphFactory"));
				mockery.checking(new Expectations(){{
					allowing(graph).subgraphFactory(); will(returnValue(subgraphFactory));

//					ignoring(subgraphFactory);
				}});
				return subgraphFactory;
			}
		});
//		IGraphObjectBuilder graphBuilder = new IGraphObjectBuilder() {
//			
//			@Override
//			public String getElementId() {
//				return "graph";
//			}
//			
//			@SuppressWarnings("unchecked")
//			@Override
//			public void create() {
//				graph = mockery.mock(ICompoundGraph.class, createMockName("graph"));
//				rootNode = mockery.mock(IRootCompoundNode.class, createMockName("rootNode"));
//				elementTree = mockery.mock(ITree.class, createMockName("elementTree"));
//				final IRootChildCompoundGraph rootChildGraph = mockery.mock(IRootChildCompoundGraph.class, createMockName("rootChildGraph"));
//				edgeFactory = mockery.mock(ICompoundEdgeFactory.class, createMockName("edgeFactory"));
//				nodeFactory = mockery.mock(ICompoundNodeFactory.class, createMockName("nodeFactory"));
////				this.subgraphFactory = this.mockery.mock(ISubCompoundGraphFactory.class, createMockName("subgraphFactory"));
//				mockery.checking(new Expectations(){{
//					allowing(rootChildGraph).getRoot(); will(returnValue(rootNode));
//
//					allowing(graph).getRoot(); will(returnValue(rootNode));
//					allowing(graph).getElementTree(); will(returnValue(elementTree));
//					allowing(graph).edgeFactory(); will(returnValue(edgeFactory));
//					allowing(graph).nodeFactory(); will(returnValue(nodeFactory));
//					
//					allowing(rootNode).getChildCompoundGraph(); will(returnValue(rootChildGraph));
//					allowing(rootNode).getLevel(); will(returnValue(0));
//					allowing(rootNode).getIndex(); will(returnValue(0));
//					allowing(rootNode).getParent(); will(returnValue(rootNode));
//					allowing(rootNode).getRoot(); will(returnValue(rootNode));
//					allowing(rootNode).getGraph(); will(returnValue(graph));
//					allowing(rootNode).isRemoved(); will(returnValue(false));
//					allowing(rootNode).isLink(); will(returnValue(false));
//					allowing(rootNode).isNode(); will(returnValue(true));
//					allowing(rootNode).connectedNodeIterator(); will(returnIterator());
//					
//					allowing(rootChildGraph).getRoot(); will(returnValue(rootNode));
//					allowing(rootChildGraph).getSuperGraph(); will(returnValue(graph));
//					allowing(rootChildGraph).getNumNodes(); will(returnValue(6));
//					allowing(rootChildGraph).getNumEdges(); will(returnValue(4));
//					allowing(rootChildGraph).numElements(); will(returnValue(9));
//					
//				}});
//			}
//			
////			@Override
////			public void buildTree() {
////				buildGraph(graph);
////			}
//
//			@Override
//			public void buildGraphStructure() {
//				final IChildCompoundGraph rootChildGraph = rootNode.getChildCompoundGraph();
//				mockery.checking(new Expectations(){{
//					allowing(graph).nodeIterator(); will(returnIterator(rootNode, getNode1(), getNode5(), getNode2(), getNode3(), getNode4()));
//					
//					allowing(rootNode).childIterator(); will(returnIterator(getNode1(), getEdge1(), getEdge4()));
//					
//					allowing(rootChildGraph).elementIterator(); will(returnIterator(getEdge4(), getNode1(), getEdge1()));
//
//					allowing(elementTree).isAncestor(with(getEdge2()), with(isOneOf(getEdge1(), getRootNode()))); will(returnValue(true));
//					allowing(elementTree).isAncestor(with(equalTo(getEdge2())), with(not(isOneOf(getEdge1(), getRootNode())))); will(returnValue(false));
//					allowing(elementTree).isDescendant(with(getEdge2()), with(isOneOf(getNode4()))); will(returnValue(true));
//					allowing(elementTree).isDescendant(with(getEdge2()), with(not(isOneOf(getNode4())))); will(returnValue(false));
//					
//					allowing(elementTree).isAncestor(getNode1(), getRootNode()); will(returnValue(true));
//					allowing(elementTree).isAncestor(with(equalTo(getNode1())), with(not(getRootNode()))); will(returnValue(false));
//					allowing(elementTree).isDescendant(with(getNode1()), with(isOneOf(getNode2(), getEdge3()))); will(returnValue(true));
//					allowing(elementTree).isDescendant(with(getNode1()), with(not(isOneOf(getNode2(), getEdge3())))); will(returnValue(false));
//
//					allowing(elementTree).isDescendant(with(getNode2()), with(isOneOf(getEdge3()))); will(returnValue(true));
//					allowing(elementTree).isDescendant(with(getNode2()), with(not(isOneOf(getEdge3())))); will(returnValue(false));
//					
//					allowing(elementTree).getLowestCommonAncestor(getNode3(), getNode5()); will(returnValue(getEdge1()));
//				}});
//			}
//		};
//		IGraphObjectBuilder treeBuilder = new IGraphObjectBuilder() {
//			
//			@Override
//			public String getElementId() {
//				return "elementTree";
//			}
//			
//			@Override
//			public void create() {
//				createElementTree();
//				buildElementTree(elementTree);
//			}
//			
////			@Override
////			public void buildTree() {
////				buildElementTree(elementTree);
////			}
//
//			@Override
//			public void buildGraphStructure() {
//			}
//		};
		this.builderMap.put(graphBuilder.getElementId(), graphBuilder);
//		this.builderMap.put(treeBuilder.getElementId(), treeBuilder);
		for(IGraphObjectBuilder element : this.builders){
			this.builderMap.put(element.getElementId(), element);
			this.elementRemovalMap.put(element.getElementId(), false);
		}
		SortedSet<String> sortedDepSet = new TreeSet<String>(new Comparator<String>() {

		@Override
		public int compare(String o1, String o2) {
			int retVal = 0;
			if(o1.equals("graph")){
				retVal = -1;
			}
			else{
				retVal = o1.compareTo(o2);
			}
			return retVal;
		}
		});
		sortedDepSet.addAll(this.builderMap.keySet());
//		this.creationDepList = new LinkedList<String>(sortedDepSet);
//		this.buildDepList = new LinkedList<String>(sortedDepSet);
	}
	
	@Override
	public void setElementRemoved(String elementId, boolean markRemoved){
		this.elementRemovalMap.put(elementId, true);
	}
	
	@Override
	public boolean isRemoved(String elementId){
		return this.elementRemovalMap.get(elementId);
	}
	
	public void redefineNode(String nodeId, INodeConstructor newConstructor){
		NodeBuilder nodeBuilder = (NodeBuilder)this.builderMap.get(nodeId);
		nodeBuilder.setNodeContructor(newConstructor);
	}
	
	public void redefineEdge(String edgeId, IEdgeConstructor newConstructor){
		EdgeBuilder edgeBuilder = (EdgeBuilder)this.builderMap.get(edgeId);
		edgeBuilder.setEdgeContructor(newConstructor);
	}
	
	public void redefineGraph(IGraphConstructor newConstructor){
		GraphBuilder graphBuilder = (GraphBuilder)this.builderMap.get(GRAPH_ID);
		graphBuilder.setGraphContructor(newConstructor);
	}
	
//	protected void buildNode1ChildGraph(IChildCompoundGraph element) {
//		this.mockery.checking(new Expectations(){{
//			allowing(node1ChildGraph).getRoot(); will(returnValue(getNode1()));
//			allowing(node1ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(node1ChildGraph).getNumNodes(); will(returnValue(1));
//			allowing(node1ChildGraph).getNumEdges(); will(returnValue(1));
//			allowing(node1ChildGraph).numElements(); will(returnValue(2));
//			allowing(node1ChildGraph).containsNode(with(equalTo(getNode2()))); will(returnValue(true));
//			allowing(node1ChildGraph).containsNode(with(not(getNode2()))); will(returnValue(false));
//			allowing(node1ChildGraph).containsEdge(with(equalTo(edge4))); will(returnValue(true));
//			allowing(node1ChildGraph).containsEdge(with(not(edge4))); will(returnValue(false));
//			allowing(node1ChildGraph).containsNode(with(equalTo(NODE2_IDX))); will(returnValue(true));
//			allowing(node1ChildGraph).containsNode(with(not(NODE2_IDX))); will(returnValue(false));
//			allowing(node1ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(node1ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(node1ChildGraph).elementIterator(); will(returnIterator(getNode2()));
//			allowing(node1ChildGraph).addNode(with(any(ICompoundNode.class)));
//			allowing(node1ChildGraph).addEdge(with(any(ICompoundEdge.class)));
//		}});
//	}

//	protected void buildNode2ChildGraph(IChildCompoundGraph element) {
//		this.mockery.checking(new Expectations(){{
//			allowing(node2ChildGraph).getRoot(); will(returnValue(getNode2()));
//			allowing(node2ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(node2ChildGraph).getNumNodes(); will(returnValue(0));
//			allowing(node2ChildGraph).getNumEdges(); will(returnValue(1));
//			allowing(node2ChildGraph).numElements(); will(returnValue(1));
//			allowing(node2ChildGraph).getNumEdges(); will(returnValue(1));
//			allowing(node2ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(node2ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(node2ChildGraph).elementIterator(); will(returnIterator(edge3));
//		}});
//	}

//	protected void buildNode3ChildGraph(IChildCompoundGraph element) {
//		this.mockery.checking(new Expectations(){{
//			allowing(node3ChildGraph).getRoot(); will(returnValue(getNode3()));
//			allowing(node3ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(node3ChildGraph).getNumNodes(); will(returnValue(0));
//			allowing(node3ChildGraph).getNumEdges(); will(returnValue(0));
//			allowing(node3ChildGraph).numElements(); will(returnValue(0));
//			allowing(node3ChildGraph).containsNode(with(any(Integer.class))); will(returnValue(false));
//			allowing(node3ChildGraph).containsNode(with(any(ICompoundNode.class))); will(returnValue(false));
//			allowing(node3ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(node3ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(node3ChildGraph).elementIterator(); will(returnIterator());
//		}});
//	}

//	protected void buildNode4ChildGraph(IChildCompoundGraph element) {
//		this.mockery.checking(new Expectations(){{
//			allowing(node4ChildGraph).getRoot(); will(returnValue(getNode4()));
//			allowing(node4ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(node4ChildGraph).getNumNodes(); will(returnValue(0));
//			allowing(node4ChildGraph).getNumEdges(); will(returnValue(0));
//			allowing(node4ChildGraph).numElements(); will(returnValue(0));
//			allowing(node4ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(node4ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(node4ChildGraph).elementIterator(); will(returnIterator());
//		}});
//	}
//
//	protected void buildNode5ChildGraph(IChildCompoundGraph element) {
//		this.mockery.checking(new Expectations(){{
//			allowing(node5ChildGraph).getRoot(); will(returnValue(getNode5()));
//			allowing(node5ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(node5ChildGraph).getNumNodes(); will(returnValue(0));
//			allowing(node5ChildGraph).getNumEdges(); will(returnValue(0));
//			allowing(node5ChildGraph).numElements(); will(returnValue(0));
//			allowing(node5ChildGraph).containsDirectedEdge(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
//			allowing(node5ChildGraph).containsConnection(with(any(ICompoundNode.class)), with(any(ICompoundNode.class))); will(returnValue(false));
//			allowing(node5ChildGraph).elementIterator(); will(returnIterator());
//		}});
//	}

//	@SuppressWarnings("unchecked")
//	protected void createElementTree() {
//		this.elementTree = this.mockery.mock(ITree.class, createMockName("elementTree"));
//	}

//	public void setCreationDependencies(String ... creationDeps){
//		this.setCreationDependencies(Arrays.asList(creationDeps));
//	}
//	
//	public void setCreationDependencies(List<String> creationDepList){
//		if(!this.builderMap.keySet().containsAll(creationDepList)) throw new IllegalArgumentException("one or more element id's not recognised");
//		
//		this.creationDepList.clear();
//		this.creationDepList.addAll(creationDepList);
//	}
//	
//	public void setBuildDependencies(String ... creationDeps){
//		this.setBuildDependencies(Arrays.asList(creationDeps));
//	}
//	
//	public void setBuildDependencies(List<String> creationDepList){
//		if(!this.builderMap.keySet().containsAll(creationDepList)) throw new IllegalArgumentException("one or more element id's not recognised");
//
//		this.buildDepList.clear();
//		this.buildDepList.addAll(creationDepList);
//	}
	
	public Mockery getMockery(){
		return this.mockery;
	}
	
	
//	public void createElements(){
//		for(String element : this.creationDepList){
//			IGraphObjectBuilder builder = this.builderMap.get(element);
//			builder.create();
//			if(builder instanceof ElementBuilder){
//				assignVariables((ElementBuilder)builder);
//			}
//		}
//	}
	
//	private void assignVariables(ElementBuilder builder) {
//		if(builder.getElementId().equals(NODE2_ID)){
//			node2 = (ICompoundNode)builder.getElement();
//			node2ChildGraph = builder.getChildGraph();
//		}
//		else if(builder.getElementId().equals(NODE3_ID)){
//			node3 = (ICompoundNode)builder.getElement();
//			node3ChildGraph = builder.getChildGraph();
//		}
//		else if(builder.getElementId().equals("edge2")){
//			edge2 = (ICompoundEdge)builder.getElement();
//			edge2ChildGraph = builder.getChildGraph();
//		}
//	}

//	public void setBuilder(String elementId, RedefinableObjectBuilder newBuilder){
//		if(!this.builderMap.containsKey(newBuilder.getElementId())) throw new IllegalArgumentException("unrecognised element builder. id=" + newBuilder.getElementId());
//		IGraphObjectBuilder eb = getElementBuilder(elementId);
//		newBuilder.setBuilder(eb);
//		this.builderMap.put(newBuilder.getElementId(), newBuilder);
//	}

//	private void buildTree(){
//		for(String element : this.buildDepList){
//			this.builderMap.get(element).buildTree();
//		}
//	}
	
//	public void buildGraphStructure(){
//		for(String element : this.buildDepList){
//			this.builderMap.get(element).buildGraphStructure();
//		}
//	}
	
//	protected void buildEdge1(ICompoundEdge iCompoundEdge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge1).getChildCompoundGraph(); will(returnValue(edge1ChildGraph));
//			allowing(edge1).getLevel(); will(returnValue(1));
//			allowing(edge1).getIndex(); will(returnValue(EDGE1_IDX));
//			allowing(edge1).getParent(); will(returnValue(getRootNode()));
//			allowing(edge1).getRoot(); will(returnValue(getRootNode()));
//			allowing(edge1).childIterator(); will(returnIterator(getNode3(), edge2, getNode5()));
//			allowing(edge1).isRemoved(); will(returnValue(false));
//			allowing(edge1).getGraph(); will(returnValue(graph));
//			allowing(edge1).compareTo(edge1); will(returnValue(0));
//			allowing(edge1).compareTo(edge2); will(returnValue(1));
//			allowing(edge1).compareTo(edge3); will(returnValue(1));
//			allowing(edge1).compareTo(edge4); will(returnValue(1));
//			allowing(edge1).isLink(); will(returnValue(true));
//			allowing(edge1).isNode(); will(returnValue(false));
//		}});
//	}

//	protected void buildElementTree(ITree<ICompoundGraphElement> tree){
//		this.mockery.checking(new Expectations(){{
//			
//			allowing(elementTree).isAncestor(with(getEdge2()), with(isOneOf(getEdge1(), getRootNode()))); will(returnValue(true));
//			allowing(elementTree).isAncestor(with(equalTo(getEdge2())), with(not(isOneOf(getEdge1(), getRootNode())))); will(returnValue(false));
//			allowing(elementTree).isDescendant(with(getEdge2()), with(isOneOf(getNode4()))); will(returnValue(true));
//			allowing(elementTree).isDescendant(with(getEdge2()), with(not(isOneOf(getNode4())))); will(returnValue(false));
//			
//			allowing(elementTree).isAncestor(getNode1(), getRootNode()); will(returnValue(true));
//			allowing(elementTree).isAncestor(with(equalTo(getNode1())), with(not(getRootNode()))); will(returnValue(false));
//			allowing(elementTree).isDescendant(with(getNode1()), with(isOneOf(getNode2(), getEdge3()))); will(returnValue(true));
//			allowing(elementTree).isDescendant(with(getNode1()), with(not(isOneOf(getNode2(), getEdge3())))); will(returnValue(false));
//
//			allowing(elementTree).isDescendant(with(getNode2()), with(isOneOf(getEdge3()))); will(returnValue(true));
//			allowing(elementTree).isDescendant(with(getNode2()), with(not(isOneOf(getEdge3())))); will(returnValue(false));
//			
//			allowing(elementTree).getLowestCommonAncestor(getNode3(), getNode5()); will(returnValue(getEdge1()));
//		}});
//	}
	
//	protected void buildEdge2(ICompoundEdge edge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge2).getChildCompoundGraph(); will(returnValue(edge2ChildGraph));
//			allowing(edge2).getLevel(); will(returnValue(2));
//			allowing(edge2).getIndex(); will(returnValue(EDGE2_IDX));
//			allowing(edge2).getParent(); will(returnValue(edge1));
//			allowing(edge2).getRoot(); will(returnValue(getRootNode()));
//			allowing(edge2).childIterator(); will(returnIterator(getNode4()));
//			allowing(edge2).isRemoved(); will(returnValue(false));
//			allowing(edge2).getGraph(); will(returnValue(graph));
//			allowing(edge2).isLink(); will(returnValue(true));
//			allowing(edge2).isNode(); will(returnValue(false));
//			allowing(edge2).compareTo(with(isOneOf(getRootNode(), getNode1(), getNode2(), getNode3(), getNode4(), getNode5(), edge1))); will(returnValue(1));
//			allowing(edge2).compareTo(edge2); will(returnValue(0));
//			allowing(edge2).compareTo(with(isOneOf(edge3, edge4))); will(returnValue(-1));
//		}});
//	}

//	protected void buildEdge3(ICompoundEdge iCompoundEdge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge3).getChildCompoundGraph(); will(returnValue(edge3ChildGraph));
//			allowing(edge3).getLevel(); will(returnValue(3));
//			allowing(edge3).getIndex(); will(returnValue(EDGE3_IDX));
//			allowing(edge3).getParent(); will(returnValue(getNode2()));
//			allowing(edge3).getRoot(); will(returnValue(rootNode));
//			allowing(edge3).childIterator(); will(returnIterator());
//			allowing(edge3).isRemoved(); will(returnValue(false));
//			allowing(edge3).isLink(); will(returnValue(true));
//			allowing(edge3).isNode(); will(returnValue(false));
//			allowing(edge3).compareTo(with(isOneOf(rootNode, getNode1(), getNode2(), getNode3(), getNode4(), getNode5(), edge1, edge2))); will(returnValue(1));
//			allowing(edge3).compareTo(edge3); will(returnValue(0));
//			allowing(edge3).compareTo(with(isOneOf(edge4))); will(returnValue(-1));
//		}});
//	}

//	protected void buildEdge4(ICompoundEdge iCompoundEdge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge4).getChildCompoundGraph(); will(returnValue(edge4ChildGraph));
//			allowing(edge4).getLevel(); will(returnValue(1));
//			allowing(edge4).getIndex(); will(returnValue(EDGE4_IDX));
//			allowing(edge4).getParent(); will(returnValue(getRootNode()));
//			allowing(edge4).getRoot(); will(returnValue(getRootNode()));
//			allowing(edge4).childIterator(); will(returnIterator());
//			allowing(edge4).isRemoved(); will(returnValue(false));
//			allowing(edge4).getGraph(); will(returnValue(graph));
//			allowing(edge4).compareTo(getRootNode()); will(returnValue(-1));
//			allowing(edge4).compareTo(with(not(edge4))); will(returnValue(-1));
//			allowing(edge4).compareTo(edge4); will(returnValue(0));
//			allowing(edge4).isLink(); will(returnValue(true));
//			allowing(edge4).isNode(); will(returnValue(false));
//		}});
//	}

//	protected void buildEdge1ChildGraph(IChildCompoundGraph iCompoundEdge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge1ChildGraph).getRoot(); will(returnValue(edge1));
//			allowing(edge1ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(edge1ChildGraph).getNumNodes(); will(returnValue(2));
//			allowing(edge1ChildGraph).getNumEdges(); will(returnValue(1));
//			allowing(edge1ChildGraph).numElements(); will(returnValue(3));
//			allowing(edge1ChildGraph).containsNode(with(isOneOf(getNode3(), getNode5()))); will(returnValue(true));
//			allowing(edge1ChildGraph).containsNode(with(not(isOneOf(getNode3(), getNode5())))); will(returnValue(false));
//			allowing(edge1ChildGraph).containsEdge(edge2); will(returnValue(true));
//			allowing(edge1ChildGraph).containsEdge(with(not(edge2))); will(returnValue(false));
//			allowing(edge1ChildGraph).containsEdge(with(EDGE2_IDX)); will(returnValue(true));
//			allowing(edge1ChildGraph).containsEdge(with(not(EDGE2_IDX))); will(returnValue(false));
//			allowing(edge1ChildGraph).containsNode(with(isOneOf(NODE3_IDX, NODE5_IDX))); will(returnValue(true));
//			allowing(edge1ChildGraph).containsNode(with(not(isOneOf(NODE3_IDX, NODE5_IDX)))); will(returnValue(false));
//			allowing(edge1ChildGraph).containsDirectedEdge(with(getNode3()), with(getNode5())); will(returnValue(true));
//			allowing(edge1ChildGraph).containsDirectedEdge(with(not(getNode3())), with(not(getNode5()))); will(returnValue(false));
//			allowing(edge1ChildGraph).containsConnection(with(getNode3()), with(getNode5())); will(returnValue(true));
//			allowing(edge1ChildGraph).containsConnection(with(getNode5()), with(getNode3())); will(returnValue(true));
//			allowing(edge1ChildGraph).containsConnection(with(not(getNode3())), with(not(getNode5()))); will(returnValue(false));
//			allowing(edge1ChildGraph).elementIterator(); will(returnIterator(getNode3(), edge2, getNode5()));
//			allowing(edge1ChildGraph).edgeFactory(); will(returnValue(edge1ChildEdgeFactory));
//			
//			allowing(edge1ChildEdgeFactory).setPair(with(any(CompoundNodePair.class)));
//			ignoring(edge1ChildEdgeFactory).createEdge();
//		}});
//	}

//	protected void buildEdge2ChildGraph(IChildCompoundGraph edge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge2ChildGraph).getRoot(); will(returnValue(edge2));
//			allowing(edge2ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(edge2ChildGraph).getNumNodes(); will(returnValue(1));
//			allowing(edge2ChildGraph).getNumEdges(); will(returnValue(0));
//			allowing(edge2ChildGraph).numElements(); will(returnValue(1));
//			allowing(edge2ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(edge2ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(edge2ChildGraph).elementIterator(); will(returnIterator(getNode4()));
//		}});
//	}

//	protected void buildEdge3ChildGraph(IChildCompoundGraph iCompoundEdge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge3ChildGraph).getRoot(); will(returnValue(edge3));
//			allowing(edge3ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(edge3ChildGraph).getNumNodes(); will(returnValue(0));
//			allowing(edge3ChildGraph).getNumEdges(); will(returnValue(0));
//			allowing(edge3ChildGraph).numElements(); will(returnValue(0));
//			allowing(edge3ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(edge3ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(edge3ChildGraph).elementIterator(); will(returnIterator());
//		}});
//	}

//	protected void buildEdge4ChildGraph(IChildCompoundGraph iCompoundEdge) {
//		this.mockery.checking(new Expectations(){{
//			allowing(edge4ChildGraph).getRoot(); will(returnValue(edge4));
//			allowing(edge4ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(edge4ChildGraph).getNumNodes(); will(returnValue(0));
//			allowing(edge4ChildGraph).getNumEdges(); will(returnValue(0));
//			allowing(edge4ChildGraph).numElements(); will(returnValue(0));
//			allowing(edge4ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(edge4ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(edge4ChildGraph).elementIterator(); will(returnIterator());
//		}});
//	}

//	protected void buildNode1(final ICompoundNode node1) {
//		this.mockery.checking(new Expectations(){{
//			allowing(node1).getChildCompoundGraph(); will(returnValue(node1ChildGraph));
//			allowing(node1).getLevel(); will(returnValue(1));
//			allowing(node1).getIndex(); will(returnValue(NODE1_IDX));
//			allowing(node1).getParent(); will(returnValue(rootNode));
//			allowing(node1).getRoot(); will(returnValue(rootNode));
//			allowing(node1).childIterator(); will(returnIterator(node2));
//			allowing(node1).getDegree(); will(returnValue(2));
//			allowing(node1).getInDegree(); will(returnValue(1));
//			allowing(node1).getOutDegree(); will(returnValue(1));
//			allowing(node1).isRemoved(); will(returnValue(false));
//			allowing(node1).getGraph(); will(returnValue(graph));
//			allowing(node1).isLink(); will(returnValue(false));
//			allowing(node1).isNode(); will(returnValue(true));
//			allowing(node1).hasEdgeWith(with(isOneOf(node2, node3))); will(returnValue(true));
//			allowing(node1).hasEdgeWith(with(not(isOneOf(node2, node3)))); will(returnValue(false));
//			allowing(node1).hasOutEdgeTo(with(isOneOf(node3))); will(returnValue(true));
//			allowing(node1).hasOutEdgeTo(with(not(isOneOf(node3)))); will(returnValue(false));
//			allowing(node1).hasInEdgeFrom(with(isOneOf(getNode2()))); will(returnValue(true));
//			allowing(node1).hasInEdgeFrom(with(not(isOneOf(getNode2())))); will(returnValue(false));
//			allowing(node1).getEdgesWith(with(isOneOf(getNode2(), getNode3()))); will(returnIterator(edge1, edge4));
//			allowing(node1).getEdgesWith(with(not(isOneOf(getNode2(), getNode3())))); will(throwException(new PreConditionException("mock exception")));
//			allowing(getNode1()).getOutEdgesTo(with(isOneOf(getNode3()))); will(returnIterator(edge4));
//			allowing(getNode1()).getOutEdgesTo(with(not(isOneOf(getNode3())))); will(throwException(new PreConditionException("mock exception")));
//			allowing(getNode1()).getInEdgesFrom(with(isOneOf(getNode2()))); will(returnIterator(edge1));
//			allowing(getNode1()).getInEdgesFrom(with(not(isOneOf(getNode2())))); will(throwException(new PreConditionException("mock exception")));
//			allowing(getNode1()).connectedNodeIterator(); will(returnIterator(getNode2(), getNode3()));
//			allowing(getNode1()).edgeIterator(); will(returnIterator(edge1, edge4));
//			allowing(getNode1()).getInEdgeIterator(); will(returnIterator(edge1));
//			allowing(getNode1()).getOutEdgeIterator(); will(returnIterator(edge4));
//		}});
//	}

//	protected void buildNode2(ICompoundNode iCompoundNode) {
//		this.mockery.checking(new Expectations(){{
//			allowing(getNode2()).getGraph(); will(returnValue(graph));
//			allowing(getNode2()).getChildCompoundGraph(); will(returnValue(node2ChildGraph));
//			allowing(getNode2()).getLevel(); will(returnValue(2));
//			allowing(getNode2()).getIndex(); will(returnValue(NODE2_IDX));
//			allowing(getNode2()).getParent(); will(returnValue(getNode1()));
//			allowing(getNode2()).getRoot(); will(returnValue(rootNode));
//			allowing(getNode2()).childIterator(); will(returnIterator(edge3));
//			allowing(getNode2()).getDegree(); will(returnValue(3));
//			allowing(getNode2()).getInDegree(); will(returnValue(1));
//			allowing(getNode2()).getOutDegree(); will(returnValue(2));
//			allowing(getNode2()).isRemoved(); will(returnValue(false));
//			allowing(getNode2()).getGraph(); will(returnValue(graph));
//			allowing(getNode2()).isLink(); will(returnValue(false));
//			allowing(getNode2()).isNode(); will(returnValue(true));
//			allowing(getNode2()).hasEdgeWith(with(isOneOf(getNode2(), getNode1()))); will(returnValue(true));
//			allowing(getNode2()).hasEdgeWith(with(not(isOneOf(getNode2(), getNode1())))); will(returnValue(false));
//			allowing(getNode2()).hasOutEdgeTo(with(isOneOf(getNode1(), getNode2()))); will(returnValue(true));
//			allowing(getNode2()).hasOutEdgeTo(with(not(isOneOf(getNode1(), getNode2())))); will(returnValue(false));
//			allowing(getNode2()).hasInEdgeFrom(with(isOneOf(getNode2()))); will(returnValue(true));
//			allowing(getNode2()).hasInEdgeFrom(with(not(isOneOf(getNode2())))); will(returnValue(false));
//			allowing(getNode2()).getEdgesWith(with(getNode1())); will(returnIterator(edge1));
//			allowing(getNode2()).getEdgesWith(with(getNode2())); will(returnIterator(edge3));
//			allowing(getNode2()).getOutEdgesTo(with(getNode1())); will(returnIterator(edge1));
//			allowing(getNode2()).getOutEdgesTo(with(getNode2())); will(returnIterator(edge3));
//			allowing(getNode2()).getInEdgesFrom(with(getNode2())); will(returnIterator(edge3));
//			allowing(getNode2()).connectedNodeIterator(); will(returnIterator(getNode1(), getNode2()));
//		}});
//	}

//	protected void buildNode3(ICompoundNode iCompoundNode) {
//		this.mockery.checking(new Expectations(){{
//			allowing(getNode3()).getGraph(); will(returnValue(graph));
//			allowing(getNode3()).getChildCompoundGraph(); will(returnValue(node3ChildGraph));
//			allowing(getNode3()).getLevel(); will(returnValue(2));
//			allowing(getNode3()).getIndex(); will(returnValue(NODE3_IDX));
//			allowing(getNode3()).getParent(); will(returnValue(edge1));
//			allowing(getNode3()).getRoot(); will(returnValue(rootNode));
//			allowing(getNode3()).childIterator(); will(returnIterator());
//			allowing(getNode3()).isRemoved(); will(returnValue(false));
//			allowing(getNode3()).getOutEdgeIterator(); will(returnIterator(edge2));
//			allowing(getNode3()).getInEdgeIterator(); will(returnIterator(edge4));
//			allowing(getNode3()).getDegree(); will(returnValue(2));
//			allowing(getNode3()).getInDegree(); will(returnValue(1));
//			allowing(getNode3()).getOutDegree(); will(returnValue(1));
//			allowing(getNode3()).addInEdge(with(any(ICompoundEdge.class)));
//			allowing(getNode3()).addOutEdge(with(any(ICompoundEdge.class)));
//			allowing(getNode3()).isLink(); will(returnValue(false));
//			allowing(getNode3()).isNode(); will(returnValue(true));
//			allowing(getNode3()).hasEdgeWith(with(isOneOf(getNode1(), getNode5()))); will(returnValue(true));
//			allowing(getNode3()).hasEdgeWith(with(not(isOneOf(getNode1(), getNode5())))); will(returnValue(false));
//			allowing(getNode3()).hasOutEdgeTo(with(isOneOf(getNode5()))); will(returnValue(true));
//			allowing(getNode3()).hasOutEdgeTo(with(not(isOneOf(getNode5())))); will(returnValue(false));
//			allowing(getNode3()).hasInEdgeFrom(with(isOneOf(getNode1()))); will(returnValue(true));
//			allowing(getNode3()).hasInEdgeFrom(with(not(isOneOf(getNode1())))); will(returnValue(false));
//			allowing(getNode3()).getOutEdgesTo(with(isOneOf(getNode5()))); will(returnIterator( edge2 ));
//			allowing(getNode3()).getOutEdgesTo(with(not(isOneOf(getNode5())))); will(throwException(new PostConditionException("mock exception")));
//			allowing(getNode3()).getInEdgesFrom(with(isOneOf(getNode1()))); will(returnIterator( edge2 ));
//			allowing(getNode3()).getInEdgesFrom(with(not(isOneOf(getNode1())))); will(throwException(new PostConditionException("mock exception")));
//			allowing(getNode3()).connectedNodeIterator(); will(returnIterator(getNode1(), getNode5()));
//			allowing(getNode3()).getEdgesWith(with(getNode1())); will(returnIterator(edge4));
//			allowing(getNode3()).getEdgesWith(with(getNode5())); will(returnIterator(edge2));
//		}});
//	}

//	protected void buildNode4(ICompoundNode iCompoundNode) {
//		this.mockery.checking(new Expectations(){{
//			allowing(getNode4()).getChildCompoundGraph(); will(returnValue(node4ChildGraph));
//			allowing(getNode4()).getLevel(); will(returnValue(3));
//			allowing(getNode4()).getIndex(); will(returnValue(NODE4_IDX));
//			allowing(getNode4()).getParent(); will(returnValue(edge2));
//			allowing(getNode4()).getRoot(); will(returnValue(rootNode));
//			allowing(getNode4()).childIterator(); will(returnIterator());
//			allowing(getNode4()).isRemoved(); will(returnValue(false));
//			allowing(getNode4()).getGraph(); will(returnValue(graph));
//			allowing(getNode4()).isLink(); will(returnValue(false));
//			allowing(getNode4()).isNode(); will(returnValue(true));
//			allowing(getNode4()).hasEdgeWith(with(any(ICompoundNode.class))); will(returnValue(false));
//			allowing(getNode4()).connectedNodeIterator(); will(returnIterator());
//			allowing(getNode4()).edgeIterator(); will(returnIterator());
//			allowing(getNode4()).getInEdgeIterator(); will(returnIterator());
//			allowing(getNode4()).getOutEdgeIterator(); will(returnIterator());
//		}});
//	}
//
//	protected void buildNode5(ICompoundNode iCompoundNode) {
//		this.mockery.checking(new Expectations(){{
//			allowing(getNode5()).getChildCompoundGraph(); will(returnValue(node5ChildGraph));
//			allowing(getNode5()).getLevel(); will(returnValue(2));
//			allowing(getNode5()).getIndex(); will(returnValue(NODE5_IDX));
//			allowing(getNode5()).getParent(); will(returnValue(edge1));
//			allowing(getNode5()).getRoot(); will(returnValue(rootNode));
//			allowing(getNode5()).childIterator(); will(returnIterator());
//			allowing(getNode5()).isRemoved(); will(returnValue(false));
//			allowing(getNode5()).addInEdge(with(any(ICompoundEdge.class)));
//			allowing(getNode5()).addOutEdge(with(any(ICompoundEdge.class)));
//			allowing(getNode5()).getGraph(); will(returnValue(graph));
//			allowing(getNode5()).isLink(); will(returnValue(false));
//			allowing(getNode5()).isNode(); will(returnValue(true));
//			allowing(getNode5()).compareTo(with(isOneOf(rootNode, getNode1(), getNode2(), getNode3(), getNode4()))); will(returnValue(1));
//			allowing(getNode5()).compareTo(with(isOneOf(edge1, edge2, edge3, edge4))); will(returnValue(-1));
//			allowing(getNode5()).compareTo(getNode5()); will(returnValue(0));
//			allowing(getNode5()).hasEdgeWith(getNode3()); will(returnValue(true));
//			allowing(getNode5()).hasEdgeWith(with(not(getNode3()))); will(returnValue(false));
//			allowing(getNode5()).hasOutEdgeTo(with(any(ICompoundNode.class))); will(returnValue(false));
//			allowing(getNode5()).getEdgesWith(getNode3()); will(returnIterator(edge2));
//			allowing(getNode5()).getOutEdgesTo(with(any(ICompoundNode.class))); will(throwException(new PostConditionException("mock exception")));
//			allowing(getNode5()).getInEdgesFrom(with(isOneOf(getNode3()))); will(returnIterator( edge2 ));
//			allowing(getNode5()).getInEdgesFrom(with(not(isOneOf(getNode3())))); will(throwException(new PostConditionException("mock exception")));
//			allowing(getNode5()).connectedNodeIterator(); will(returnIterator(getNode3()));
//		}});
//	}

//	protected void buildGraph(final ICompoundGraph graph, final ICompoundNode rootNode){
//		final IChildCompoundGraph rootChildGraph = rootNode.getChildCompoundGraph();
//		this.mockery.checking(new Expectations(){{
//			allowing(graph).getRoot(); will(returnValue(rootNode));
//			allowing(graph).getElementTree(); will(returnValue(elementTree));
//			allowing(graph).edgeFactory(); will(returnValue(edgeFactory));
//			allowing(graph).nodeFactory(); will(returnValue(nodeFactory));
//			allowing(graph).nodeIterator(); will(returnIterator(rootNode, getNode1(), getNode5(), getNode2(), getNode3(), getNode4()));
//			
//			allowing(rootNode).getChildCompoundGraph(); will(returnValue(rootChildGraph));
//			allowing(rootNode).getLevel(); will(returnValue(0));
//			allowing(rootNode).getIndex(); will(returnValue(0));
//			allowing(rootNode).getParent(); will(returnValue(rootNode));
//			allowing(rootNode).getRoot(); will(returnValue(rootNode));
//			allowing(rootNode).childIterator(); will(returnIterator(getNode1(), getEdge1(), getEdge4()));
//			allowing(rootNode).getGraph(); will(returnValue(graph));
//			allowing(rootNode).isRemoved(); will(returnValue(false));
//			allowing(rootNode).isLink(); will(returnValue(false));
//			allowing(rootNode).isNode(); will(returnValue(true));
//			allowing(rootNode).connectedNodeIterator(); will(returnIterator());
//			
//			allowing(rootChildGraph).getRoot(); will(returnValue(rootNode));
//			allowing(rootChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(rootChildGraph).getNumNodes(); will(returnValue(6));
//			allowing(rootChildGraph).getNumEdges(); will(returnValue(4));
//			allowing(rootChildGraph).numElements(); will(returnValue(9));
//			allowing(rootChildGraph).elementIterator(); will(returnIterator(getEdge4(), getNode1(), getEdge1()));
//			
//		}});
//	}	
//
//	protected IRootCompoundNode createCompoundGraph() {
//		this.graph = this.mockery.mock(ICompoundGraph.class, createMockName("graph"));
//		final IRootCompoundNode rootNode = this.mockery.mock(IRootCompoundNode.class, createMockName("rootNode"));
//		final IRootChildCompoundGraph rootChildGraph = this.mockery.mock(IRootChildCompoundGraph.class, createMockName("rootChildGraph"));
//		this.edgeFactory = this.mockery.mock(ICompoundEdgeFactory.class, createMockName("edgeFactory"));
//		this.nodeFactory = this.mockery.mock(ICompoundNodeFactory.class, createMockName("nodeFactory"));
////		this.subgraphFactory = this.mockery.mock(ISubCompoundGraphFactory.class, createMockName("subgraphFactory"));
//		mockery.checking(new Expectations(){{
//			allowing(rootNode).getChildCompoundGraph(); will(returnValue(rootChildGraph));
//			allowing(rootChildGraph).getRoot(); will(returnValue(rootNode));
//		}});
//		return rootNode;
//	}

//	protected ICompoundEdge createEdge1() {
//		return this.edge1 = this.mockery.mock(ICompoundEdge.class, createMockName("edge1"));
//	}

//	protected ICompoundEdge createEdge2() {
//		return this.edge2 = this.mockery.mock(ICompoundEdge.class, createMockName("edge2"));
//	}

//	protected ICompoundEdge createEdge3() {
//		return this.edge3 = this.mockery.mock(ICompoundEdge.class, createMockName("edge3"));
//	}

//	protected ICompoundEdge createEdge4() {
//		return this.edge4 = this.mockery.mock(ICompoundEdge.class, createMockName("edge4"));
//	}

//	protected IChildCompoundGraph createEdge1ChildGraph() {
//		this.edge1ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge1ChildGraph"));
//		this.edge1ChildEdgeFactory = this.mockery.mock(ICompoundChildEdgeFactory.class, createMockName("edge1ChildEdgeFactory"));
//		return this.edge1ChildGraph;
//	}

//	protected IChildCompoundGraph createEdge2ChildGraph() {
//		return this.edge2ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge2ChildGraph"));
//	}

//	protected IChildCompoundGraph createEdge3ChildGraph() {
//		return this.edge3ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge3ChildGraph"));
//	}

//	protected IChildCompoundGraph createEdge4ChildGraph() {
//		return this.edge4ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("edge4ChildGraph"));
//	}

	protected String createMockName(String string) {
		StringBuilder buf = new StringBuilder(prefix);
		buf.append(string);
		return buf.toString();
	}

//	protected ICompoundNode createNode1() {
//		final ICompoundNode node1 = this.mockery.mock(ICompoundNode.class, createMockName("node1"));
//		final IChildCompoundGraph node1ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node1ChildGraph"));
//		this.mockery.checking(new Expectations(){{
//			allowing(node1).getChildCompoundGraph(); will(returnValue(node1ChildGraph));
//			allowing(node1).getLevel(); will(returnValue(1));
//			allowing(node1).getIndex(); will(returnValue(NODE1_IDX));
//			allowing(node1).getParent(); will(returnValue(getRootNode()));
//			allowing(node1).getRoot(); will(returnValue(getRootNode()));
//			allowing(node1).getDegree(); will(returnValue(2));
//			allowing(node1).getInDegree(); will(returnValue(1));
//			allowing(node1).getOutDegree(); will(returnValue(1));
//			allowing(node1).isRemoved(); will(returnValue(false));
//			allowing(node1).getGraph(); will(returnValue(graph));
//			allowing(node1).isLink(); will(returnValue(false));
//			allowing(node1).isNode(); will(returnValue(true));
//
//			allowing(node1ChildGraph).getRoot(); will(returnValue(getNode1()));
//			allowing(node1ChildGraph).getSuperGraph(); will(returnValue(graph));
//			allowing(node1ChildGraph).getNumNodes(); will(returnValue(1));
//			allowing(node1ChildGraph).getNumEdges(); will(returnValue(1));
//			allowing(node1ChildGraph).numElements(); will(returnValue(2));
//			allowing(node1ChildGraph).containsEdge(with(any(Integer.class))); will(returnValue(false));
//			allowing(node1ChildGraph).containsEdge(with(any(ICompoundEdge.class))); will(returnValue(false));
//			allowing(node1ChildGraph).addNode(with(any(ICompoundNode.class)));
//			allowing(node1ChildGraph).addEdge(with(any(ICompoundEdge.class)));
//		}});
//		return node1;
//	}

//	protected ICompoundNode createNode2() {
//		this.node2 = this.mockery.mock(ICompoundNode.class, createMockName(NODE2_ID));
////		this.mockery.checking(new Expectations(){{
////			allowing(node2).getGraph(); will(returnValue(graph));
////		}});
//		return this.node2;
//	}

//	protected ICompoundNode createNode3() {
//		this.node3 = this.mockery.mock(ICompoundNode.class, createMockName(NODE3_ID));
////		this.mockery.checking(new Expectations(){{
////			allowing(node3).getGraph(); will(returnValue(graph));
////		}});
//		return this.node3;
//	}

//	protected ICompoundNode createNode4() {
//		this.node4 = this.mockery.mock(ICompoundNode.class, createMockName("node4"));
//		return this.node4;
//	}
//
//	protected ICompoundNode createNode5() {
//		this.node5 = this.mockery.mock(ICompoundNode.class, createMockName("node5"));
////		this.mockery.checking(new Expectations(){{
////			allowing(node5).getGraph(); will(returnValue(graph));
////		}});
//		return this.node5;
//	}

//	protected IChildCompoundGraph createNode1ChildGraph(){
//		return this.mockery.mock(IChildCompoundGraph.class, createMockName("node1ChildGraph"));
//	}
	
//	protected IChildCompoundGraph createNode2ChildGraph() {
//		return this.mockery.mock(IChildCompoundGraph.class, createMockName("node2ChildGraph"));
//	}

//	protected IChildCompoundGraph createNode3ChildGraph() {
//		return this.node3ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node3ChildGraph"));
//	}

//	protected IChildCompoundGraph createNode4ChildGraph() {
//		return this.node4ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node4ChildGraph"));
//	}
//
//	protected IChildCompoundGraph createNode5ChildGraph() {
//		return this.node5ChildGraph = this.mockery.mock(IChildCompoundGraph.class, createMockName("node5ChildGraph"));
//	}

	public ICompoundNode getNode1() {
		return ((INodeBuilder)this.builderMap.get(NODE1_ID)).getCompoundNode();
	}

	public ICompoundNode getNode2() {
		return ((INodeBuilder)this.builderMap.get(NODE2_ID)).getCompoundNode();
	}

	public ICompoundNode getNode3() {
		return ((INodeBuilder)this.builderMap.get(NODE3_ID)).getCompoundNode();
	}

	public ICompoundNode getNode4() {
		return ((INodeBuilder)this.builderMap.get(NODE4_ID)).getCompoundNode();
	}

	public ICompoundNode getNode5() {
		return ((INodeBuilder)this.builderMap.get(NODE5_ID)).getCompoundNode();
	}

	public ICompoundNode getNode6() {
		return ((INodeBuilder)this.builderMap.get(NODE6_ID)).getCompoundNode();
	}

	public ICompoundEdge getEdge1() {
		return ((IEdgeBuilder)this.builderMap.get(EDGE1_ID)).getCompoundEdge();
	}

	public ICompoundEdge getEdge2() {
		return ((IEdgeBuilder)this.builderMap.get(EDGE2_ID)).getCompoundEdge();
	}

	public ICompoundEdge getEdge3() {
		return ((IEdgeBuilder)this.builderMap.get(EDGE3_ID)).getCompoundEdge();
	}

	public ICompoundEdge getEdge4() {
		return ((IEdgeBuilder)this.builderMap.get(EDGE4_ID)).getCompoundEdge();
	}

	@Override
	public IRootCompoundNode getRootNode() {
		return ((IGraphBuilder)this.builderMap.get(GRAPH_ID)).getRootNode();
	}

	public ICompoundGraph getGraph() {
		return ((IGraphBuilder)this.builderMap.get(GRAPH_ID)).getGraph();
	}

	public void buildFixture() {
		for(String element : this.creationDepList){
			IGraphObjectBuilder builder = this.builderMap.get(element);
			builder.create();
		}
		for(String element : this.creationDepList){
			IGraphObjectBuilder builder = this.builderMap.get(element);
			builder.buildGraphStructure();
		}
	}

	@Override
	public ICompoundEdge getEdge(String edgeId) {
		return ((IEdgeBuilder)this.builderMap.get(edgeId)).getCompoundEdge();
	}

	@Override
	public ICompoundNode getNode(String nodeId) {
		return ((INodeBuilder)this.builderMap.get(nodeId)).getCompoundNode();
	}

}
