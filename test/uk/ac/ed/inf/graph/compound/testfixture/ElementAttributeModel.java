package uk.ac.ed.inf.graph.compound.testfixture;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.IElementAttributeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttributeModel;

public class ElementAttributeModel implements IElementAttributeModel {
	private final List<IElementAttribute> attribs;

	public ElementAttributeModel(){
		this.attribs = new LinkedList<IElementAttribute>();
	}
	
	@Override
	public <E extends IElementAttribute> IElementAttributeFactory elementAttributeFactory(Class<E> discriminator) {
		IElementAttributeFactory retVal = null;
		if(discriminator.getClass().equals(NodeElementAttribute.class)){
			retVal = new NodeElementAttributeFactory();
		}
		else if(discriminator.getClass().equals(NodeElementAttribute.class)){
			retVal = new EdgeElementAttributeFactory();
		}
		return retVal;
	}

	@Override
	public int numElementAttributes(Class<? extends IElementAttribute> discriminator) {
		int i = 0;
		for(IElementAttribute attrib : this.attribs){
			if(attrib.getClass().equals(discriminator)){
				i++;
			}
		}
		return i;
	}

	@Override
	public Iterator<IElementAttribute> elementAttributeIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addElementAttribute(IElementAttribute attribute) {
		// TODO Auto-generated method stub

	}

	@Override
	public ICompoundGraph getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IElementAttribute getRootAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

}
