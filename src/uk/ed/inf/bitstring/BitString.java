package uk.ed.inf.bitstring;

import java.util.BitSet;


public class BitString implements IBitString {
	private final BitSet bitVector;
	
	BitString(){
		this.bitVector = new BitSet();
	}
	
	BitString(int vectorSize){
		this.bitVector = new BitSet(vectorSize);
		this.bitVector.set(0, vectorSize-1, false);
	}
	
	BitString(BitString other){
		this(other.bitVector.size());
		this.bitVector.or(other.bitVector);
	}

	BitString(BitSet rep){
		this.bitVector = rep;
	}
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#and(uk.ed.inf.graph.impl.BitString)
	 */
	public IBitString and(BitString set) {
		BitString retVal = new BitString(this);
		retVal.bitVector.and(set.bitVector);
		return retVal;
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#andNot(uk.ed.inf.graph.impl.BitString)
	 */
	public IBitString andNot(BitString set) {
		BitString retVal = new BitString(this);
		retVal.bitVector.andNot(set.bitVector);
		return retVal;
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#cardinality()
	 */
	public int cardinality() {
		return bitVector.cardinality();
	}
	
	
	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#get(int, int)
	 */
	public IBitString get(int fromIndex, int toIndex) {
		return new BitString(bitVector.get(fromIndex, toIndex));
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#get(int)
	 */
	public boolean get(int bitIndex) {
		return bitVector.get(bitIndex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bitVector == null) ? 0 : bitVector.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#intersects(uk.ed.inf.graph.impl.BitString)
	 */
	public boolean intersects(BitString set) {
		return bitVector.intersects(set.bitVector);
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#isEmpty()
	 */
	public boolean isEmpty() {
		return bitVector.isEmpty();
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#length()
	 */
	public int length() {
		return bitVector.length();
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#nextClearBit(int)
	 */
	public int nextClearBit(int fromIndex) {
		return bitVector.nextClearBit(fromIndex);
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#nextSetBit(int)
	 */
	public int nextSetBit(int fromIndex) {
		return bitVector.nextSetBit(fromIndex);
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#or(uk.ed.inf.graph.impl.BitString)
	 */
	public IBitString or(BitString set) {
		BitString retVal = new BitString(this);
		retVal.bitVector.or(set.bitVector);
		return retVal;
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#size()
	 */
	public int size() {
		return bitVector.size();
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#toString()
	 */
	public String toString() {
		return bitVector.toString();
	}

	/* (non-Javadoc)
	 * @see uk.ed.inf.graph.impl.IBitString#xor(uk.ed.inf.graph.impl.BitString)
	 */
	public IBitString xor(BitString set) {
		BitString retVal = new BitString(this);
		retVal.bitVector.xor(set.bitVector);
		return retVal;
	}


	public boolean[] toArray() {
		boolean retVal[] = new boolean[this.length()];
		for(int i = 0; i < this.length(); i++){
			retVal[i] = this.get(i);
		}
		return retVal;
	}

	public boolean[] toArray(boolean[] a) {
		boolean retVal[] = a; 
		if(retVal.length < this.length()){
			retVal = new boolean[this.length()]; 
		}
		for(int i = 0; i < this.length(); i++){
			retVal[i] = this.get(i);
		}
		return retVal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BitString other = (BitString) obj;
		if (bitVector == null) {
			if (other.bitVector != null)
				return false;
		} else if (!bitVector.equals(other.bitVector))
			return false;
		return true;
	}

}
