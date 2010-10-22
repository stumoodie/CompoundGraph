package uk.ac.ed.inf.designbycontract;

public class PreConditionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String PREFIX_STR = "Precondition: ";

	public PreConditionException(String message) {
		super(prefixStr(message));
	}

	private static String prefixStr(String message){
		StringBuilder buf = new StringBuilder(PREFIX_STR);
		buf.append(message);
		return buf.toString();
	}
}
