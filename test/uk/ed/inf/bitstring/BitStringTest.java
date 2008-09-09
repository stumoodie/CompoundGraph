package uk.ed.inf.bitstring;

import static org.junit.Assert.*;

import java.util.BitSet;
import java.lang.reflect.Array;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.util.TeeXMLDocumentFilterImpl;

public class BitStringTest {
	
	private BitString testBitString ;
	private BitString testBitString2 ;
	
	public static final int [] NUMERICAL = {0,1,2,3,4,5 , 64} ;

	@Before
	public void setUp() throws Exception {
		
		BitSet tempSet = new BitSet (NUMERICAL[3]) ;
		tempSet.flip(NUMERICAL[1]) ;
		
		testBitString = new BitString( tempSet) ;
		
		tempSet = new BitSet (NUMERICAL[3]) ;
		tempSet.flip(NUMERICAL[0]) ;
		tempSet.flip(NUMERICAL[1]) ;
		
		testBitString2 = new BitString ( tempSet ) ;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHashCode() {
		assertEquals ( "hashcode" , testBitString.hashCode() , testBitString.hashCode() ) ;
	}

	@Test
	public final void testBitStringBitString() {
		BitSet result = new BitSet (NUMERICAL[3]) ;
		result.flip(NUMERICAL[1]) ;
		
		BitString newBitString = new BitString ( testBitString) ;
		assertEquals ( "new from BitString" , result.get(NUMERICAL[0]) , testBitString.get(NUMERICAL[0])) ;
		assertEquals ( "new from BitString" , result.get(NUMERICAL[1]) , testBitString.get(NUMERICAL[1])) ;
		assertEquals ( "new from BitString" , result.get(NUMERICAL[2]) , testBitString.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testBitStringBitSet() {
		BitSet result = new BitSet (NUMERICAL[3]) ;
		result.flip(NUMERICAL[1]) ;
		
		BitString newBitString = new BitString ( result) ;
		assertEquals ( "new from BitSet" , result.get(NUMERICAL[0]) , testBitString.get(NUMERICAL[0])) ;
		assertEquals ( "new from BitSet" , result.get(NUMERICAL[1]) , testBitString.get(NUMERICAL[1])) ;
		assertEquals ( "new from BitSet" , result.get(NUMERICAL[2]) , testBitString.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testAnd() {
		BitSet result = new BitSet (NUMERICAL[3]) ;
		result.flip(NUMERICAL[1]) ;
		
		IBitString andBitString = testBitString2.and(testBitString) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , andBitString.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , andBitString.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , andBitString.get(NUMERICAL[2])) ;
		
	}

	@Test
	public final void testAndNot() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		
		IBitString andNotBitString = testBitString2.andNot(testBitString) ;
		
		assertEquals ( "andNot operator" , result.get(NUMERICAL[0]) , andNotBitString.get(NUMERICAL[0])) ;
		assertEquals ( "andNot operator" , result.get(NUMERICAL[1]) , andNotBitString.get(NUMERICAL[1])) ;
		assertEquals ( "andNot operator" , result.get(NUMERICAL[2]) , andNotBitString.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testCardinality() {
		assertEquals ("2 true" , NUMERICAL[2] , testBitString2.cardinality()) ;
		assertEquals ("1 true" , NUMERICAL[1] , testBitString.cardinality()) ;
	}

	@Test
	public final void testEqualsObject() {
		assertTrue ( "same object" , testBitString.equals(testBitString) );
		assertFalse ( "other object" , testBitString.equals( testBitString2 ) );
	}

	@Test
	public final void testGetIntInt() {
		BitSet result = new BitSet (3) ;
		result.flip(1) ;
		
		IBitString getBitString = testBitString.get(0, 2) ; 
		
		assertEquals ( "get int int" , result.get(NUMERICAL[0]) , getBitString.get(NUMERICAL[0])) ;
		assertEquals ( "get int int" , result.get(NUMERICAL[1]) , getBitString.get(NUMERICAL[1])) ;
		assertEquals ( "get int int" , result.get(NUMERICAL[2]) , getBitString.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testGetInt() {
		assertEquals ( "get int" , false , testBitString.get(NUMERICAL[0])) ;
		assertEquals ( "get int" , true , testBitString.get(NUMERICAL[1])) ;
		assertEquals ( "get int" , false , testBitString.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testIntersects() {
		assertTrue ( "intersects" , testBitString.intersects(testBitString2)) ;
	}

	@Test
	public final void testIsEmpty() {
		assertFalse ( "notEmpty" , testBitString.isEmpty()) ;
	}

	@Test
	public final void testLength() {
		assertEquals ( "length" , NUMERICAL[2] , testBitString.length()) ;
		
		BitSet aBitSet = new BitSet ( 3 ) ;
		aBitSet.flip(2);
		IBitString other = new BitString (aBitSet) ;
		assertEquals ( "length of last true" , NUMERICAL[3] , other.length()) ;
		
		aBitSet = new BitSet ( 3 ) ;
		other = new BitString (aBitSet) ;
		assertEquals ( "nothing in" , NUMERICAL[0] , other.length()) ;
		
	}

	@Test
	public final void testNextClearBit() {
		assertEquals("fist index" , NUMERICAL[0] , testBitString.nextClearBit(NUMERICAL[0])) ;
		assertEquals("last index" , NUMERICAL[2] , testBitString2.nextClearBit(NUMERICAL[0])) ;
	}

	@Test
	public final void testNextSetBit() {
		assertEquals("second index" , NUMERICAL[1] , testBitString.nextSetBit(NUMERICAL[0])) ;
		assertEquals("first index" , NUMERICAL[0] , testBitString2.nextSetBit(NUMERICAL[0])) ;
	}

	@Test
	public final void testOr() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		result.flip(1) ;
		
		IBitString orBitString = testBitString2.or(testBitString) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , orBitString.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , orBitString.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , orBitString.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testSize() {
		assertEquals( "size" , NUMERICAL[6] , testBitString.size() ) ;
	}

	@Test
	public final void testToString() {
		assertEquals ( "single bit" , "{1}" , testBitString.toString()) ;
		assertEquals ( "two bit" , "{0, 1}" , testBitString2.toString()) ;
	}

	@Test
	public final void testXor() {
		BitSet result = new BitSet (3) ;
		result.flip(0) ;
		
		IBitString xorBitString = testBitString2.xor(testBitString) ;
		
		assertEquals ( "and operator" , result.get(NUMERICAL[0]) , xorBitString.get(NUMERICAL[0])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[1]) , xorBitString.get(NUMERICAL[1])) ;
		assertEquals ( "and operator" , result.get(NUMERICAL[2]) , xorBitString.get(NUMERICAL[2])) ;
	}

	@Test
	public final void testToArray() {
		boolean boolArray [] = {false , true , false } ;
		
		boolean generatedArray [] = testBitString.toArray() ;
		
		for ( int a = 0 ; a < Array.getLength(generatedArray) ; a++)
		{
			assertEquals ( "same" , boolArray[a] , generatedArray[a]) ;
		}
	}

	@Test
	public final void testToArrayBooleanArray() {
		boolean boolArray [] = {false , true , false } ;
		boolean generatedArray [] = {true } ;
		
		 generatedArray = testBitString.toArray(generatedArray) ;
		 
			for ( int a = 0 ; a < Array.getLength(generatedArray) ; a++)
			{
				assertEquals ( "same" , boolArray[a] , generatedArray[a]) ;
			}
	}

}
