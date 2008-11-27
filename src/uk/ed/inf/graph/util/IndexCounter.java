package uk.ed.inf.graph.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread safe index counter.
 * @author smoodie
 *
 */
public class IndexCounter {
	public static final int DEFAULT_INITIAL_IDX = 0; 
	private final AtomicInteger indexCntr;
	
	public IndexCounter(){
		this.indexCntr = new AtomicInteger(DEFAULT_INITIAL_IDX);
	}
	
	public IndexCounter(int initialValue){
		this.indexCntr = new AtomicInteger(initialValue);
	}
	
	public int nextIndex(){
		return this.indexCntr.incrementAndGet();
	}
	
	public int getLastIndex(){
		return this.indexCntr.get();
	}
	
	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
	    builder.append("(indexCntr=");
	    builder.append(this.indexCntr.get());
	    builder.append(")");
	    return builder.toString();
	}
}
