package uk.ac.ed.inf.graph.compound.testfixture;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class IsIn<T extends ICompoundGraphElement> extends BaseMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
    	this.collection = new LinkedList<T>();
    	for(T el : collection){
    		if(!el.isRemoved()){
    			this.collection.add(el);
    		}
    	}
    }
    
    public IsIn(T[] elements) {
        collection = Arrays.asList(elements);
    }
    
    @Override
	public boolean matches(Object o) {
        return collection.contains(o);
    }

    @Override
	public void describeTo(Description buffer) {
        buffer.appendText("one of ");
        buffer.appendValueList("{", ", ", "}", collection);
    }
    
    @Factory
    public static <T extends ICompoundGraphElement> Matcher<T> isIn(Collection<T> collection) {
        return new IsIn<T>(collection);
    }
    
    @Factory
    public static <T extends ICompoundGraphElement> Matcher<T> isIn(T[] elements) {
        return new IsIn<T>(elements);
    }
    
    @Factory
    public static <T extends ICompoundGraphElement> Matcher<T> isOneOf(T... elements) {
        return isIn(elements);
    }
}
