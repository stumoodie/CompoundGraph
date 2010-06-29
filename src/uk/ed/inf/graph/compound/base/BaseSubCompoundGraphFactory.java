/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ed.inf.graph.compound.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ISubCompoundGraphFactory;

public class BaseSubCompoundGraphFactory implements ISubCompoundGraphFactory {
	private static final String DEBUG_PROP_NAME = "uk.ed.inf.graph.compound.base.debugging";
	// added debug checks to graph
	private final boolean debuggingEnabled;
	private final BaseSubCompoundGraphBuilder builder;
	private final Set<ICompoundNode> nodeList = new HashSet<ICompoundNode>();
	private final Set<ICompoundEdge> edgeList = new HashSet<ICompoundEdge>();

	protected BaseSubCompoundGraphFactory(BaseSubCompoundGraphBuilder builder){
		this.builder = builder;
		this.debuggingEnabled = Boolean.getBoolean(DEBUG_PROP_NAME);
	}
	
	public final void addNode(ICompoundNode node){
		this.nodeList.add(node);
	}
	
	public final void addEdge(ICompoundEdge edge){
		this.edgeList.add(edge);
	}
	
	public final void addElement(ICompoundGraphElement element){
		if(element.isNode()){
			this.nodeList.add((ICompoundNode)element);
		}
		else{
			this.edgeList.add((ICompoundEdge)element);
		}
	}

	/**
	 * Get an iterator for the set of nodes added to this factory. It will node contain
	 * any addition nodes in child compound graphs of these nodes. You should create the
	 * subgraph to get this.
	 * @return iterator of <code>CiNode</code>s.
	 */
	public Iterator<ICompoundNode> nodeIterator(){
		return this.nodeList.iterator();
	}
	
	public int numNodes(){
		return this.nodeList.size();
	}
	
	/**
	 * Get an iterator of the set of edges added to this factory. It will not return any incident
	 * edges derived from the nodes selected here or between nodes in any child compound graphs of
	 * the selected nodes. You should create the subgraph to get this. 
	 */
	public Iterator<ICompoundEdge> edgeIterator(){
		return this.edgeList.iterator();
	}
	
	public int numEdges(){
		return this.edgeList.size();
	}
	
	/**
	 * Creates a subgraph that includes dangling edges and contains nodes from the compound graphs of
	 *  of each subgraph.
	 *  @return the newly created subgraph, cannot be null. 
	 */
	public BaseSubCompoundGraph createSubgraph() {
		builder.setNodeList(this.nodeList);
		builder.setEdgeList(this.edgeList);
		builder.expandChildNodes();
		builder.addAdditionalNodes();
		builder.addAdditionalEdges();
		if(builder.hasAdditionalNodes()){
			builder.expandChildNodes();
		}
		builder.buildSubgraph();
		return builder.getSubgraph();
	}

	
	/**
	 * Creates an induced subgraph that contains all incident edges between the nodes in this graph.
	 * Child nodes in the compound-graph of each node are also included. 
	 * Unattached (dangling) edges defined in the factory are omitted from this sub-graph.
	 * <p>
	 * Postcondition:
	 * <code>retVal.isInducedSubgraph() == true</code>
	 * @return the newly created induced subgraph.    
	 */
	public BaseSubCompoundGraph createInducedSubgraph(){
		builder.setNodeList(this.nodeList);
		
		// because we are creating an induced graph we ignore any edges that are selected since if they are
		// not incident to the selected nodes they will result in a non-induced graph.
		builder.expandChildNodes();
		builder.addIncidentEdges();
		builder.addAdditionalNodes();
		// don't add additional edges since this must break "inducedness" of the graph if the edge is not already in the graph.  
		if(builder.hasAdditionalNodes()){
			// since new nodes have been added we need to recalculate children and incident edges. 
			builder.expandChildNodes();
			builder.addIncidentEdges();
		}
		builder.buildSubgraph();
		BaseSubCompoundGraph retVal = builder.getSubgraph();
		if(debuggingEnabled && !retVal.isInducedSubgraph()){
			throw new IllegalStateException("The nodes and edges chosen in the factory would not permit the creation of an induced subgraph");
		}
		return retVal;
	}
	
	public BaseSubCompoundGraph createPermissiveInducedSubgraph(){
		builder.setNodeList(this.nodeList);
		builder.setEdgeList(this.edgeList);
		builder.expandChildNodes();
		builder.addIncidentEdges();
		builder.addAdditionalNodes();
		builder.addAdditionalEdges();
		if(builder.hasAdditionalNodes()){
			// since new nodes have been added we need to recalculate children and incident edges. 
			builder.expandChildNodes();
			builder.addIncidentEdges();
		}
		builder.buildSubgraph();
		BaseSubCompoundGraph retVal = builder.getSubgraph();
		return retVal;
	}
	
	public final BaseCompoundGraph getGraph(){
		return this.builder.getGraph();
	}
	
}
