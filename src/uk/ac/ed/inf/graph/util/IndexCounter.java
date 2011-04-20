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

package uk.ac.ed.inf.graph.util;

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
