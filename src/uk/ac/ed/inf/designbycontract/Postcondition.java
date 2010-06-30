package uk.ac.ed.inf.designbycontract;

public class Postcondition {

	public Postcondition(){
		
	}
	
	public boolean assertion(boolean result, String label){
		if(!result){
			throw new PostConditionException(label);
		}
		return result;
	}
	
}
