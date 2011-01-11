package uk.ac.ed.inf.graph.compound.testfixture;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;

public class ReturnIteratorWithRemovalAction implements Action {
    private final Collection<? extends ICompoundGraphElement> collection;
    
    public ReturnIteratorWithRemovalAction(Collection<? extends ICompoundGraphElement> collection) {
    	this.collection = collection;
    }
    
    public ReturnIteratorWithRemovalAction(ICompoundGraphElement... array) {
        this(Arrays.asList(array));
    }
    
    @Override
	public Iterator<ICompoundGraphElement> invoke(Invocation invocation) throws Throwable {
    	List<ICompoundGraphElement> iterCollection = new LinkedList<ICompoundGraphElement>();
    	for(ICompoundGraphElement el : collection){
    		if(!el.isRemoved()){
    			iterCollection.add(el);
    		}
    	}
        return iterCollection.iterator();
    }
    
    @Override
	public void describeTo(Description description) {
        description.appendValueList("return iterator over ", ", ", "", collection);
    }
}