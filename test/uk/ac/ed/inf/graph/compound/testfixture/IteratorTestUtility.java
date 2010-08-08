package uk.ac.ed.inf.graph.compound.testfixture;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class IteratorTestUtility <I extends ICompoundGraphElement> {
	private final List<I> expectedResults;
	
	public IteratorTestUtility(I ... expectedResults){
		this.expectedResults = new ArrayList<I>(Arrays.asList(expectedResults));
	}
	
	public IteratorTestUtility(List<I> expectedResultList){
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

	public void testSortedIterator(Iterator<I> elementIterator) {
		SortedSet<I> actualResults = new TreeSet<I>(new Comparator<I>(){

			@Override
			public int compare(I o1, I o2) {
				return (o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() > o2.getIndex() ? 1 : 0));
			}
			
		});
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
