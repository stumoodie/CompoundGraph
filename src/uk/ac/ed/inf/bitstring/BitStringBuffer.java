/*
Copyright 2009, Court of the University of Edinburgh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package uk.ac.ed.inf.bitstring;

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

//	public boolean equals(Object obj) {
//		return bitVector.equals(obj);
//	}

	
	
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

	@Override
	public String toString() {
		return bitVector.toString();
	}
	
	public BitString toBitString(){
		return new BitString(this.bitVector);
	}

	public void xor(BitStringBuffer set) {
		bitVector.xor(set.bitVector);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bitVector == null) ? 0 : bitVector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BitStringBuffer)) {
			return false;
		}
		BitStringBuffer other = (BitStringBuffer) obj;
		if (bitVector == null) {
			if (other.bitVector != null) {
				return false;
			}
		} else if (!bitVector.equals(other.bitVector)) {
			return false;
		}
		return true;
	}
}
