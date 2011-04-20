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
import java.util.LinkedList;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class IsIn<T extends ICompoundGraphElement> extends BaseMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
    	this.collection = new LinkedList<T>();
    	for(T el : collection){
    		if(!el.isRemoved()){
    			this.collection.add(el);
    		}
    	}
    }
    
    public IsIn(T[] elements) {
        collection = Arrays.asList(elements);
    }
    
    @Override
	public boolean matches(Object o) {
        return collection.contains(o);
    }

    @Override
	public void describeTo(Description buffer) {
        buffer.appendText("one of ");
        buffer.appendValueList("{", ", ", "}", collection);
    }
    
    @Factory
    public static <T extends ICompoundGraphElement> Matcher<T> isIn(Collection<T> collection) {
        return new IsIn<T>(collection);
    }
    
    @Factory
    public static <T extends ICompoundGraphElement> Matcher<T> isIn(T[] elements) {
        return new IsIn<T>(elements);
    }
    
    @Factory
    public static <T extends ICompoundGraphElement> Matcher<T> isOneOf(T... elements) {
        return isIn(elements);
    }
}
