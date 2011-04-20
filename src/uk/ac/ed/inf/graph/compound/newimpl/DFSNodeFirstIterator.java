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
package uk.ac.ed.inf.graph.compound.newimpl;

import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class DFSNodeFirstIterator implements Iterator<ICompoundGraphElement> {
	private final Deque<ICompoundGraphElement> stack;
	private final IElementTreeFilter filter;
	
	public DFSNodeFirstIterator(Iterator<ICompoundGraphElement> topElements, IElementTreeFilter filter){
		this.filter = filter;
		this.stack = new LinkedList<ICompoundGraphElement>();
		SortedSet<ICompoundGraphElement> cache = createSortedCache();
		while(topElements.hasNext()){
			ICompoundGraphElement el = topElements.next();
			if(filter.matched(el)){
				cache.add(el);
			}
		}
		for(ICompoundGraphElement topElement : cache){
			stack.push(topElement);
		}
	}
	
	@Override
	public boolean hasNext() {
		return !this.stack.isEmpty();
	}

	@Override
	public ICompoundGraphElement next() {
		ICompoundGraphElement retVal = this.stack.pop();
		populate(retVal);
		return retVal;
	}
	
	private SortedSet<ICompoundGraphElement> createSortedCache(){
		SortedSet<ICompoundGraphElement> cache = new TreeSet<ICompoundGraphElement>(new Comparator<ICompoundGraphElement>(){

			@Override
			public int compare(ICompoundGraphElement o1, ICompoundGraphElement o2) {
				int retVal = 0;
				if(o1.isNode() && o2.isEdge()){
					retVal = 1;
				}
				else if(o1.isEdge() && o2.isNode()){
					retVal = -1;
				}
				if(retVal == 0){
					retVal = o1.getIndex() < o2.getIndex() ? 1 : (o1.getIndex() > o2.getIndex() ? -1 : 0);
				}
				return retVal;
			}
			
		});
		return cache;
	}

	private void populate(ICompoundGraphElement parent) {
		// strategy is to sort the elements here do that the nodes in the child graph are DF traversed before the links 
		Iterator<ICompoundGraphElement> iter = parent.getChildCompoundGraph().unfilteredElementIterator();
		SortedSet<ICompoundGraphElement> cache = createSortedCache();
		while(iter.hasNext()){
			ICompoundGraphElement el = iter.next();
			if(filter.matched(el)){
				cache.add(el);
			}
		}
		for(ICompoundGraphElement element : cache){
			this.stack.push(element);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Removal is not supported by this interator");
	}

}
