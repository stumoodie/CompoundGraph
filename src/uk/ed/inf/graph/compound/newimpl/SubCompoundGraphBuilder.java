package uk.ed.inf.graph.compound.newimpl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ed.inf.graph.compound.ICompoundEdge;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.compound.ICompoundNode;
import uk.ed.inf.graph.compound.ICompoundNodePair;
import uk.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ed.inf.graph.compound.ISubCompoundGraphBuilder;

public class SubCompoundGraphBuilder implements ISubCompoundGraphBuilder {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ICompoundGraph graph;
	private final Set<ICompoundGraphElement> topElements;
	private ISubCompoundGraph currentSubgraph;
	private final Set<ICompoundGraphElement> visited;
	private final Set<ICompoundGraphElement> originalEdgeSelection;

	public SubCompoundGraphBuilder(ICompoundGraph graph){
		this.graph = graph;
		this.topElements = new HashSet<ICompoundGraphElement>();
		visited = new HashSet<ICompoundGraphElement>();
		this.originalEdgeSelection = new HashSet<ICompoundGraphElement>();
	}
	
	@Override
	public void addIncidentEdges() {
		Set<ICompoundGraphElement> initialElements = new HashSet<ICompoundGraphElement>(this.topElements);
		Set<ICompoundGraphElement> missingIncidentEdges = new HashSet<ICompoundGraphElement>();
		do {
			Iterator<ICompoundGraphElement> elementIter = initialElements.iterator();
			while (elementIter.hasNext()) {
				ICompoundGraphElement element = elementIter.next();
				// this iterator will return the root node as the first node, so
				// their is no
				// need to deal with this explicitly.
				Iterator<ICompoundGraphElement> iter = element.levelOrderIterator();
//				boolean isRoot = true;
				while (iter.hasNext()) {
					ICompoundGraphElement childElement = iter.next();
					// if this node has already been visited, then so must its children and so there is no point in continuing
					if(logger.isTraceEnabled()){
						logger.trace("Visiting element: " + childElement);
					}
					if (childElement.isNode()) {
						ICompoundNode node = (ICompoundNode) childElement;
						Iterator<ICompoundEdge> edgeIter = node.edgeIterator();
						while (edgeIter.hasNext()) {
							ICompoundEdge incidentEdge = edgeIter.next();
							// check the edge is new and that it is incident to another node in this subgraph
							if (!visited.contains(incidentEdge)) {
								missingIncidentEdges.add(incidentEdge);
								// make all incident edges top nodes. They must be. 
								this.topElements.add(incidentEdge);
								if(logger.isTraceEnabled()){
									logger.trace("Added edge to missingList: " + incidentEdge);
								}
							}
//							// prune children from top node list
//							if (isRoot) {
//								isRoot = false;
//							} else {
//								this.topNodeList.remove(childElement);
//							}
						}
					}
					// record element as having been visited
					visited.add(childElement);
				}
				initialElements = new HashSet<ICompoundGraphElement>(missingIncidentEdges);
			}
		}
		while (!missingIncidentEdges.isEmpty());
		pruneUnselectedNonIncidentEdges();
	}

	private void pruneUnselectedNonIncidentEdges() {
		for(ICompoundGraphElement topEl : new HashSet<ICompoundGraphElement>(this.topElements)){
			if(topEl.isLink()){
				ICompoundEdge topEdge = (ICompoundEdge)topEl;
				ICompoundNodePair pair = topEdge.getConnectedNodes();
				// remove if not incident and not part of the original selection: this preserves only non-incident edges if they were in the original selection 
				if(!this.originalEdgeSelection.contains(topEl) && (!visited.contains(pair.getOutNode()) || !visited.contains(pair.getInNode()))){
					this.topElements.remove(topEl);
				}		
			}
		}
	}

	@Override
	public void buildSubgraph() {
		SubCompoundGraph subGraph = new SubCompoundGraph(this.graph);
		for(ICompoundGraphElement element : this.topElements){
			subGraph.addTopNode(element);
		}
		this.currentSubgraph = subGraph;
	}

	@Override
	public void expandChildNodes() {
		this.visited.clear();
		Set<ICompoundGraphElement> initialElements = new HashSet<ICompoundGraphElement>(this.topElements);
		Iterator<ICompoundGraphElement> elementIter = initialElements.iterator();
		while (elementIter.hasNext()) {
			ICompoundGraphElement element = elementIter.next();
			// this iterator will return the root node as the first node, so
			// their is no
			// need to deal with this explicitly.
			Iterator<ICompoundGraphElement> iter = element.levelOrderIterator();
			boolean isRoot = true;
			boolean skip = false;
			while (iter.hasNext() && !skip) {
				ICompoundGraphElement childElement = iter.next();
				// if this node has already been visited, then so must its children and so there is no point in continuing
				if (visited.contains(childElement)) {
					skip = true;
					if(logger.isTraceEnabled()){
						logger.trace("Skipping visited element: " + childElement);
					}
				} else {
					if(logger.isTraceEnabled()){
						logger.trace("Visiting element: " + childElement);
					}
					// prune children from top node list
					if (isRoot) {
						isRoot = false;
					} else {
						this.topElements.remove(childElement);
					}
					// add node to allNode list
					visited.add(childElement);
				}
			}
		}
	}

	@Override
	public ICompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public ISubCompoundGraph getSubgraph() {
		return this.currentSubgraph;
	}

	@Override
	public void setElementList(Set<? extends ICompoundGraphElement> elementList) {
		this.topElements.clear();
		this.originalEdgeSelection.clear();
		
		for(ICompoundGraphElement element : elementList){
			this.topElements.add(element);
			if(element.isLink()){
				this.originalEdgeSelection.add(element);
			}
		}
	}

	@Override
	public void removeNonIncidentEdges() {
		// assumes that the graph here is fully build and so the non-incident edges will be topElements
		for(ICompoundGraphElement topEl : new HashSet<ICompoundGraphElement>(this.topElements)){
			if(topEl.isLink()){
				ICompoundEdge topEdge = (ICompoundEdge)topEl;
				ICompoundNodePair pair = topEdge.getConnectedNodes();
				// remove if not incident 
				if(!visited.contains(pair.getOutNode()) || !visited.contains(pair.getInNode())){
					this.topElements.remove(topEl);
				}		
			}
		}
	}

}
