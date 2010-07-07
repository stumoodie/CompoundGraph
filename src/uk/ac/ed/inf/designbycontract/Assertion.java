package uk.ac.ed.inf.designbycontract;

public abstract class Assertion {

	public boolean assertion(boolean result, String label){
		if(!result){
			failAssertion(label);
		}
		return result;
	}
	
	
	public boolean implies(boolean implication, boolean impliedAssertion){
		boolean retVal = true;
		if(implication){
			retVal = impliedAssertion;
		}
		return retVal;
	}
	
	protected abstract void failAssertion(String label);
}
