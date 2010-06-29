/**
 * 
 */
package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraphElement;

public interface IElementTreeFilter {
	boolean matched(ICompoundGraphElement element);
}