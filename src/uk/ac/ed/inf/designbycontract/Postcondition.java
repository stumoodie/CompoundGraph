package uk.ac.ed.inf.designbycontract;

public class Postcondition extends Assertion {

	public Postcondition(){
		
	}

	@Override
	protected void failAssertion(String label) {
		throw new PostConditionException(label);
	}
	
}
