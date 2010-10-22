package uk.ac.ed.inf.graph.compound;

public interface IElementAttributeFactory {

	/**
	 * Tests if the factory is in a state to successfully create an attribute.
	 *   
	 * @return true if it is, false otherwise.
	 */
	boolean canCreateAttribute();
	
	/**
	 * Sets the destination attribute, which is the attribute of the <code>CompoundGraphElement</code> that will be 
	 * the parent of any newly created element attribute associated with the child <code>CompoundGraphElement</code>. 
	 * 
	 * @param attribute the destination attribute
	 */
	void setDestinationAttribute(IElementAttribute attribute);

	/**
	 * Get the destination attribute.
	 * @return
	 */
	IElementAttribute getDestinationAttribute();
	
	/**
	 * Create the new attribute.
	 * @return the new attribute instance.
	 * @throws IllegalStateException if <code>canCreateAttribute() == false</code>. 
	 */
	IElementAttribute createAttribute();
	
	void setOutAttribute(IElementAttribute attribute);
	
	IElementAttribute getOutAttribute();

	void setInAttribute(IElementAttribute attribute);
	
	IElementAttribute getInAttribute();
}
