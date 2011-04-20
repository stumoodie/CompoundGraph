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
package uk.ac.ed.inf.graph.compound.testfixture;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class ReturnIteratorWithRemovalAction implements Action {
    private final Collection<? extends ICompoundGraphElement> collection;
    
    public ReturnIteratorWithRemovalAction(Collection<? extends ICompoundGraphElement> collection) {
    	this.collection = collection;
    }
    
    public ReturnIteratorWithRemovalAction(ICompoundGraphElement... array) {
        this(Arrays.asList(array));
    }
    
    @Override
	public Iterator<ICompoundGraphElement> invoke(Invocation invocation) throws Throwable {
    	List<ICompoundGraphElement> iterCollection = new LinkedList<ICompoundGraphElement>();
    	for(ICompoundGraphElement el : collection){
    		if(!el.isRemoved()){
    			iterCollection.add(el);
    		}
    	}
        return iterCollection.iterator();
    }
    
    @Override
	public void describeTo(Description description) {
        description.appendValueList("return iterator over ", ", ", "", collection);
    }
}