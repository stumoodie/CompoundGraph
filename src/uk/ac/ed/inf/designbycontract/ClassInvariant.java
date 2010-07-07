package uk.ac.ed.inf.designbycontract;


public class ClassInvariant extends Assertion {

	public ClassInvariant(){
		
	}

	@Override
	protected void failAssertion(String label) {
		throw new InvariantException(label);
	}
	
}
