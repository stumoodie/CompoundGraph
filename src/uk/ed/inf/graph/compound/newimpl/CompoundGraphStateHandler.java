package uk.ed.inf.graph.compound.newimpl;

import java.util.Iterator;

import uk.ed.inf.bitstring.BitStringBuffer;
import uk.ed.inf.bitstring.IBitString;
import uk.ed.inf.graph.compound.ICompoundGraph;
import uk.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ed.inf.graph.state.GeneralGraphState;
import uk.ed.inf.graph.state.IGraphState;
import uk.ed.inf.graph.state.IGraphStateHandler;

public class CompoundGraphStateHandler implements IGraphStateHandler {
	private final ICompoundGraph graph;
	private IBitString elementStatus;
	
	public CompoundGraphStateHandler(ICompoundGraph graph){
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
		Iterator<ICompoundGraphElement> iter = this.graph.getElementTree().levelOrderIterator();
		while(iter.hasNext()){
			ICompoundGraphElement node = iter.next();
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
	public ICompoundGraph getGraph() {
		return this.graph;
	}

	@Override
	public void restoreState(IGraphState previousState) {
		if ( previousState.getGraph() != this.getGraph()){
			throw new IllegalArgumentException ( "The state must belong to the same Graph.") ;
		}
		// mark all nodes and edges as removed
		Iterator<ICompoundGraphElement> elementIterator = this.graph.getElementTree().levelOrderIterator() ;
		while ( elementIterator.hasNext()){
			elementIterator.next().markRemoved(true) ;
		}
		
		// then mark those in bit list as restored.
		this.elementStatus = previousState.getElementStates();
		restoreNodes();
	}

	private void restoreNodes(){
		Iterator<ICompoundGraphElement> iter = this.graph.getElementTree().levelOrderIterator();
		while(iter.hasNext()){
			ICompoundGraphElement element = iter.next();
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
