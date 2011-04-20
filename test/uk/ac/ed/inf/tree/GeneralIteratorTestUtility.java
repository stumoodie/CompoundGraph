/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/
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
