package uk.ac.ed.inf.designbycontract;


public class ClassInvariant {

	public ClassInvariant(){
		
	}
	
	public boolean assertion(boolean result, String label){
		if(!result){
			throw new InvariantException(label);
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
}
