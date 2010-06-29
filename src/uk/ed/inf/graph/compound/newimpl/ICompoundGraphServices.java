package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraphCopyBuilder;
import uk.ed.inf.graph.compound.ICompoundGraphMoveBuilder;
import uk.ed.inf.graph.util.IndexCounter;

public interface ICompoundGraphServices {

	ICompoundGraphCopyBuilder newCopyBuilder();
	
	ICompoundGraphMoveBuilder newMoveBuilder();

	IndexCounter getIndexCounter();
}
