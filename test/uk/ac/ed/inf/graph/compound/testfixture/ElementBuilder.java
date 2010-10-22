/**
 * 
 */
package uk.ac.ed.inf.graph.compound.testfixture;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public abstract class ElementBuilder implements IGraphObjectBuilder {
	
	private ICompoundGraphElement element;
//	private IChildCompoundGraph childGraph;
	
	public final ICompoundGraphElement getElement(){
		return element;
	}
	
//	public final IChildCompoundGraph getChildGraph(){
//		return childGraph;
//	}
	
	protected abstract ICompoundGraphElement createElement();
	
//	public abstract IChildCompoundGraph createChildGraph();

//	public abstract void buildElement();
	
//	public abstract void buildChildGraph();
	
//	protected void setElement(ICompoundGraphElement element){
//		this.element = element;
//	}
	
//	protected void setChildGraph(IChildCompoundGraph childGraph){
//		this.childGraph = childGraph;
//	}
	
	@Override
	public void create(){
		element = createElement();
//		childGraph = createChildGraph();
//		buildElement();
//		buildChildGraph();
	}
	
//	@Override
//	public void buildTree(){
//	}
}