package uk.ed.inf.graph.compound.newimpl;

public aspect TopElementStructureAOP {

	pointcut elementAddition(): target(ElementTreeStructure) && call(void addElement(ICompoundGraphElement));
	
}
