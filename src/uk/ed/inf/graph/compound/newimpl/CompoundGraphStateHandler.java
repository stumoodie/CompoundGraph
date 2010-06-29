package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ed.inf.bitstring.BitStringBuffer;
import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.state.GeneralGraphState;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IGraphStateHandler;
import uk.ed.inf.graph.state.IRestorableGraph;
import uk.ed.inf.graph.state.IRestorableGraphElement;

public class CompoundGraphStateHandler implements IGraphStateHandler {
	private final IRestorableGraph graph;
	private IBitString elementStatus;
	
	public CompoundGraphStateHandler(IRestorableGraph graph){
		this.graph = graph;
	}
	
	@Override
	public IGraphState createGraphState() {
		recordNodes();
		IGraphState state = new GeneralGraphState(this.graph, elementStatus);
		return state;
	}

	private void recordNodes(){
		BitStringBuffer elementStatus = new BitStringBuffer();
		Iterator<IRestorableGraphElement> iter = this.graph.restorableElementIterator();
		while(iter.hasNext()){
			IRestorableGraphElement node = iter.next();
			if(!node.isRemoved()){
				elementStatus.set(node.getIndex(), true);
			}
			else{
				elementStatus.set(node.getIndex(), false);
			}
		}
		this.elementStatus = elementStatus.toBitString();
	}

	@Override
	public IRestorableGraph getGraph() {
		return this.graph;
	}

	@Override
	public void restoreState(IGraphState previousState) {
		if ( previousState.getGraph() != this.getGraph()){
			throw new IllegalArgumentException ( "The state must belong to the same Graph.") ;
		}
		// mark all nodes and edges as removed
		Iterator<IRestorableGraphElement> elementIterator = this.graph.restorableElementIterator();
		while ( elementIterator.hasNext()){
			elementIterator.next().markRemoved(true) ;
		}
		
		// then mark those in bit list as restored.
		this.elementStatus = previousState.getElementStates();
		restoreNodes();
	}

	private void restoreNodes(){
		Iterator<IRestorableGraphElement> iter = this.graph.restorableElementIterator();
		while(iter.hasNext()){
			IRestorableGraphElement element = iter.next();
			int nodeIdx = element.getIndex();
			if(nodeIdx < this.elementStatus.length()){
				boolean nodeState = this.elementStatus.get(nodeIdx);
				element.markRemoved(!nodeState);
			}
			else{
				element.markRemoved(true);
			}
		}
	}
}
