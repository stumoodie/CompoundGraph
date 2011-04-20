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

package uk.ac.ed.inf.bitstring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.BitSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BitStringBufferTest {
	
	private BitStringBuffer testBitStringBuffer ;
	private BitStringBuffer testBitStringBuffer2 ;
	
	public static final int [] NUMERICAL = {0,1,2,3,4,5 , 64} ;

	@Before
	public void setUp() throws Exception {
		
		testBitStringBuffer = new BitStringBuffer (3) ;
		testBitStringBuffer.flip(1) ;
		
		testBitStringBuffer2 = new BitStringBuffer (3) ;
		testBitStringBuffer2.flip(0) ;
		testBitStringBuffer2.flip(1) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHashCode() {
		assertEquals ( "same object" , testBitStringBuffer.hashCode() , testBitStringBuffer.hashCode()) ;
		assertNotSame ( "not same object" , testBitStringBuffer.hashCode() , testBitStringBuffer2.hashCode()) ;
	}

	@Test
	public final void testBitStringBufferIBitString() {
		IBitString aBitString = new BitString ( 3 ) ; 
		testBitStringBuffer = new BitStringBuffer ( aBitString ) ;
		
		
		assertEquals ( "from BitString" , aBitString.get(NUMERICAL[0]) , testBitStringBuffer.get(NUMERICAL[0])) ;
		assertEquals ( "from BitString" , aBitString.get(NUMERICAL[1]) , testBitStringBuffer.get(NUMERICAL[1])) ;
		assertEquals ( "from BitString" , aBitString.get(NUMERICAL[2]) , testBitStringBuffer.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testAnd() {
		BitSet result = new BitSet (NUMERICAL[3]) ;
		result.flip(NUMERICAL[1]) ;
		
		testBitStringBuffer2.and(testBitStringBuffer) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testAndNot() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		
		testBitStringBuffer2.andNot(testBitStringBuffer) ;
		
		assertEquals ( "andNot operator" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "andNot operator" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "andNot operator" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testCardinality() {
		assertEquals ("2 true" , NUMERICAL[2] , testBitStringBuffer2.cardinality()) ;
		assertEquals ("1 true" , NUMERICAL[1] , testBitStringBuffer.cardinality()) ;
	}

	@Test
	public final void testClear() {
		BitSet result = new BitSet (3) ;		
		testBitStringBuffer.clear() ;
		
		assertEquals ( "clear" , result.get(NUMERICAL[0]) , testBitStringBuffer.get(NUMERICAL[0])) ;
		assertEquals ( "clear" , result.get(NUMERICAL[1]) , testBitStringBuffer.get(NUMERICAL[1])) ;
		assertEquals ( "clear" , result.get(NUMERICAL[2]) , testBitStringBuffer.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testClearIntInt() {
		BitSet result = new BitSet (3) ;
		result.flip(NUMERICAL[0]) ;
		
		testBitStringBuffer2.clear( 1 , 2 ) ;
		
		assertEquals ( "clear int int" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "clear int int" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "clear int int" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testClearInt() {
		BitSet result = new BitSet (3) ;
		result.flip(NUMERICAL[1]) ;
		
		testBitStringBuffer2.clear( 0 ) ;
		
		assertEquals ( "clear int" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "clear int" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "clear int" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testEqualsObject() {
		BitStringBuffer aBitSTringBuffer = new BitStringBuffer ( 3 ) ;
		aBitSTringBuffer.flip(1) ;
		
		
		assertEquals ( "same Object " , aBitSTringBuffer , testBitStringBuffer ) ;
	}

	@Test
	public final void testFlipIntInt() {
		BitSet result = new BitSet (3) ;
		result.flip(NUMERICAL[2]) ;
		
		testBitStringBuffer.flip(1, 3) ;
		
		assertEquals ( "flip int int" , result.get(NUMERICAL[0]) , testBitStringBuffer.get(NUMERICAL[0])) ;
		assertEquals ( "flip int int" , result.get(NUMERICAL[1]) , testBitStringBuffer.get(NUMERICAL[1])) ;
		assertEquals ( "flip int int" , result.get(NUMERICAL[2]) , testBitStringBuffer.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testFlipInt() {
		BitSet result = new BitSet (3) ;
		
		testBitStringBuffer.flip(1) ;
		
		assertEquals ( "flip" , result.get(NUMERICAL[0]) , testBitStringBuffer.get(NUMERICAL[0])) ;
		assertEquals ( "flip" , result.get(NUMERICAL[1]) , testBitStringBuffer.get(NUMERICAL[1])) ;
		assertEquals ( "flip" , result.get(NUMERICAL[2]) , testBitStringBuffer.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testGetIntInt() {
		BitSet result = new BitSet (3) ;
		result.flip(NUMERICAL[0]) ;
		result.flip(NUMERICAL[1]) ;
		
		IBitString other = testBitStringBuffer2.get( 0 , 3) ;
		
		assertEquals ( "get" , result.get(NUMERICAL[0]) , other.get(NUMERICAL[0])) ;
		assertEquals ( "get" , result.get(NUMERICAL[1]) , other.get(NUMERICAL[1])) ;
		assertEquals ( "get" , result.get(NUMERICAL[2]) , other.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testGetInt() {
		assertEquals ( "get" , true , testBitStringBuffer.get(NUMERICAL[1])) ;
	}

	@Test
	public final void testIntersects() {
		assertTrue ( "intersects" , testBitStringBuffer.intersects(testBitStringBuffer2));
		
		BitStringBuffer result = new BitStringBuffer (3) ;
		result.flip(NUMERICAL[0]) ;
		result.flip(NUMERICAL[2]) ;
		
		assertFalse ( "intersects" , testBitStringBuffer.intersects(result));
	}

	@Test
	public final void testIsEmpty() {
		assertFalse ( "notEmpty" , testBitStringBuffer.isEmpty()) ;
		testBitStringBuffer.clear() ;
		assertTrue ( "Empty" , testBitStringBuffer.isEmpty()) ;
		
	}

	@Test
	public final void testLength() {
		assertEquals ( "length" , NUMERICAL[2] , testBitStringBuffer.length()) ;

		BitStringBuffer other = new BitStringBuffer (NUMERICAL[3]) ;
		other.flip(NUMERICAL[2]) ;
		assertEquals ( "length of last true" , NUMERICAL[3] , other.length()) ;
		
		other = new BitStringBuffer ( 3 ) ;
		assertEquals ( "nothing in" , NUMERICAL[0] , other.length()) ;
	}

	@Test
	public final void testNextClearBit() {
		assertEquals("fist index" , NUMERICAL[0] , testBitStringBuffer.nextClearBit(NUMERICAL[0])) ;
		assertEquals("last index" , NUMERICAL[2] , testBitStringBuffer2.nextClearBit(NUMERICAL[0])) ;
	}

	@Test
	public final void testNextSetBit() {
		assertEquals("second index" , NUMERICAL[1] , testBitStringBuffer.nextSetBit(NUMERICAL[0])) ;
		assertEquals("first index" , NUMERICAL[0] , testBitStringBuffer2.nextSetBit(NUMERICAL[0])) ;
	}

	@Test
	public final void testOr() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		result.flip(1) ;
		
		testBitStringBuffer2.or(testBitStringBuffer) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testSetIntBoolean() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		result.flip(1) ;
		result.flip(2) ;
		
		testBitStringBuffer2.set(2, true) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testSetIntIntBoolean() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		result.flip(1) ;
		result.flip(2) ;
		
		testBitStringBuffer.set(0, 3 , true) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , testBitStringBuffer.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , testBitStringBuffer.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , testBitStringBuffer.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testSetIntInt() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		result.flip(1) ;
		result.flip(2) ;
		
		testBitStringBuffer.set(0, 3 ) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , testBitStringBuffer.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , testBitStringBuffer.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , testBitStringBuffer.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testSetInt() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		result.flip(1) ;
		result.flip(2) ;
		
		testBitStringBuffer2.set(2 ) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testSize() {
		assertEquals( "size" , NUMERICAL[6] , testBitStringBuffer.size() ) ;
	}

	@Test
	public final void testToString() {
		assertEquals ( "single bit" , "{1}" , testBitStringBuffer.toString()) ;
		assertEquals ( "two bit" , "{0, 1}" , testBitStringBuffer2.toString()) ;
	}

	@Test
	public final void testToBitString() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		result.flip(1) ;
		
		IBitString fromBitStringBuffer = testBitStringBuffer2.toBitString() ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , fromBitStringBuffer.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , fromBitStringBuffer.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , fromBitStringBuffer.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testXor() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		
		testBitStringBuffer2.xor(testBitStringBuffer) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , testBitStringBuffer2.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , testBitStringBuffer2.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , testBitStringBuffer2.get(NUMERICAL[2])) ;
	}

}
