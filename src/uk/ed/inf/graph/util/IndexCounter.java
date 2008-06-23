package uk.ed.inf.graph.util;

public class IndexCounter {
	public static final int DEFAULT_INITIAL_IDX = 0; 
	private int indexCntr;
	
	public IndexCounter(){
		this.indexCntr = DEFAULT_INITIAL_IDX;
	}
	
	public IndexCounter(int initialValue){
		this.indexCntr = initialValue;
	}
	
	public int nextIndex(){
		return ++indexCntr;
	}
	
	public int getLastIndex(){
		return indexCntr;
	}
	
}
