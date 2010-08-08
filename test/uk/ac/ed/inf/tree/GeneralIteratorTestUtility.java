package uk.ac.ed.inf.tree;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class GeneralIteratorTestUtility <I> {
	private final List<I> expectedResults;
	private Comparator<I> comparator;
	
	public GeneralIteratorTestUtility(I ... expectedResults){
		this.expectedResults = new ArrayList<I>(Arrays.asList(expectedResults));
	}
	
	public GeneralIteratorTestUtility(List<I> expectedResultList){
		this.expectedResults = new ArrayList<I>(expectedResultList);
	}
	
	public void testIterator(Iterator<I> testIter){
		List<I> actualResults = new LinkedList<I>();
		while(testIter.hasNext()){
			I node = testIter.next();
			actualResults.add(node);
		}
		assertEquals("expected num elements", expectedResults.size(), actualResults.size());
		int cntr = 0;
		for(I actualNode : actualResults){
			assertEquals("expected element", expectedResults.get(cntr++), actualNode);
		}

	}

	public Comparator<I> getComparator(){
		return this.comparator;
	}
	
	public void setComparator(Comparator<I> comp){
		this.comparator = comp;
	}
	
	public void testSortedIterator(Iterator<I> elementIterator) {
		SortedSet<I> actualResults = new TreeSet<I>();
		if(this.comparator != null){
			actualResults = new TreeSet<I>(this.comparator);
		}
		while(elementIterator.hasNext()){
			I element = elementIterator.next();
			actualResults.add(element);
		}
		assertEquals("expected num elements", expectedResults.size(), actualResults.size());
		int cntr = 0;
		for(I actualNode : actualResults){
			assertEquals("expected element", expectedResults.get(cntr++), actualNode);
		}
	}
	
}
