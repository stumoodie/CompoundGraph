/**
 * 
 */
package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public interface IElementTreeFilter {
	boolean matched(ICompoundGraphElement element);
}