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
package uk.ac.ed.inf.graph.basic;


public interface IBasicPair<
	N extends INode,
	E extends IEdge
> {
	
	/**
	 * Is this node contained in this end pair.
	 * @param node The node to test, can be null.
	 * @return true is node is contained, else false.
	 */
	boolean containsNode(N node);

	/**
	 * Tests if this end pair is made up of these two nodes. This is ignores the direction of
	 * the edge these edges make up. In all cases, even for directed edges the following holds:
	 * <p>
	 * <code>hasEnds(endOne, endTwo) == hasEnds(endTwo, endOne)</code>
	 * @param endOne The first node end.
	 * @param endTwo The second node end.
	 * @return true if both nodes make up the end pair, false otherwise.
	 */
	boolean hasEnds(N endOne, N endTwo);
	
	/**
	 * Get the other node to this one in the pair.
	 * @param node The node to test, cannot be null.
	 * @return The other node of the pair. If the nodes in the pair are identical then
	 *  will return the same instance as <code>node</code>.
	 */
	N getOtherNode(N node);
	
	/**
	 * Test whether a pair is equal, that is do they include the same pair of nodes.
	 * The ordering is important and reciprocal pairs are not regarded as equivalent.
	 * This is in addition to the standard contact for equals. 
	 * @param other the other object to compare for equality.
	 * @return true if they are equals by the equals contract, false otherwise.
	 */
	boolean equals(Object other);
	
	/**
	 * The hashCode that should given identical behaviour to equals. That is is equals returns
	 * true both object should have the same hash code value.
	 * @return
	 */
	int hashCode();
}
