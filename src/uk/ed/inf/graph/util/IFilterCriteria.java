package uk.ed.inf.graph.util;


public interface IFilterCriteria<F> {

	boolean matched(F testObj);
	
}
