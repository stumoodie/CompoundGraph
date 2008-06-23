package uk.ed.inf.graph.colour;

public interface IColourFactory<C> {

	C createNewInstance();
	
	C createdCopyOf(C original);
	
}
																													