package uk.ac.ed.inf.designbycontract;

public class Precondition extends Assertion {

	public Precondition(){
		
	}

	@Override
	protected void failAssertion(String label) {
		throw new PreConditionException(label);
	}
		
}
