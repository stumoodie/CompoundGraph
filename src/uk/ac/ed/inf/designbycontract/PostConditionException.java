package uk.ac.ed.inf.designbycontract;

public class PostConditionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String PREFIX_STR = "Postcondition: ";

	public PostConditionException(String message) {
		super(prefixStr(message));
	}

	private static String prefixStr(String message){
		StringBuilder buf = new StringBuilder(PREFIX_STR);
		buf.append(message);
		return buf.toString();
	}
}
