package uk.ed.inf.bitstring;



public interface IBitString {

	IBitString and(BitString set);

	IBitString andNot(BitString set);

	int cardinality();

	boolean equals(Object obj);

	IBitString get(int fromIndex, int toIndex);

	boolean get(int bitIndex);

	int hashCode();

	boolean intersects(BitString set);

	boolean isEmpty();

	int length();

	IBitString or(BitString set);

	int size();

	String toString();

	IBitString xor(BitString set);

	boolean[] toArray();

	boolean[] toArray(boolean[] a);
}