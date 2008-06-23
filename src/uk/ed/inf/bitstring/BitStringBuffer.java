package uk.ed.inf.bitstring;

import java.util.BitSet;


public class BitStringBuffer {
	private final BitSet bitVector;
	
	public BitStringBuffer(){
		this.bitVector = new BitSet();
	}
	
	public BitStringBuffer(int vectorSize){
		this.bitVector = new BitSet(vectorSize);
		this.bitVector.set(0, vectorSize-1, false);
	}
	
	public BitStringBuffer(IBitString bitString){
		this.bitVector = new BitSet();
	}
	
	public void and(BitStringBuffer set) {
		bitVector.and(set.bitVector);
	}

	public void andNot(BitStringBuffer set) {
		bitVector.andNot(set.bitVector);
	}

	public int cardinality() {
		return bitVector.cardinality();
	}

	public void clear() {
		bitVector.clear();
	}

	public void clear(int fromIndex, int toIndex) {
		bitVector.clear(fromIndex, toIndex);
	}

	public void clear(int bitIndex) {
		bitVector.clear(bitIndex);
	}

	public boolean equals(Object obj) {
		return bitVector.equals(obj);
	}

	public void flip(int fromIndex, int toIndex) {
		bitVector.flip(fromIndex, toIndex);
	}

	public void flip(int bitIndex) {
		bitVector.flip(bitIndex);
	}

	public IBitString get(int fromIndex, int toIndex) {
		return new BitString(bitVector.get(fromIndex, toIndex));
	}

	public boolean get(int bitIndex) {
		return bitVector.get(bitIndex);
	}

	public int hashCode() {
		return bitVector.hashCode();
	}

	public boolean intersects(BitStringBuffer set) {
		return bitVector.intersects(set.bitVector);
	}

	public boolean isEmpty() {
		return bitVector.isEmpty();
	}

	public int length() {
		return bitVector.length();
	}

	public int nextClearBit(int fromIndex) {
		return bitVector.nextClearBit(fromIndex);
	}

	public int nextSetBit(int fromIndex) {
		return bitVector.nextSetBit(fromIndex);
	}

	public void or(BitStringBuffer set) {
		bitVector.or(set.bitVector);
	}

	public void set(int bitIndex, boolean value) {
		bitVector.set(bitIndex, value);
	}

	public void set(int fromIndex, int toIndex, boolean value) {
		bitVector.set(fromIndex, toIndex, value);
	}

	public void set(int fromIndex, int toIndex) {
		bitVector.set(fromIndex, toIndex);
	}

	public void set(int bitIndex) {
		bitVector.set(bitIndex);
	}

	public int size() {
		return bitVector.size();
	}

	public String toString() {
		return bitVector.toString();
	}
	
	public BitString toBitString(){
		return new BitString(this.bitVector);
	}

	public void xor(BitStringBuffer set) {
		bitVector.xor(set.bitVector);
	}
}
