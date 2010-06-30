package uk.ac.ed.inf.designbycontract;

public class Precondition {

	public Precondition(){
		
	}
	
	public boolean assertion(boolean result, String label){
		if(!result){
			throw new PreConditionException(label);
		}
		return result;
	}
	
}
