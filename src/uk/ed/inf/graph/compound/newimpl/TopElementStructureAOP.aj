package uk.ed.inf.graph.compound.newimpl;

import uk.ed.inf.graph.compound.ICompoundGraphElement;

public aspect TopElementStructureAOP {

	pointcut elementAddition(): target(ElementTreeStructure) && call(void addElement(ICompoundGraphElement));
	
}
