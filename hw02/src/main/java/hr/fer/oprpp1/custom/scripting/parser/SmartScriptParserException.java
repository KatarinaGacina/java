package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Class which represents an exception that occurred while parsing the document with SmartScriptParser.
 * @author Katarina Gacina
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which creates exception without any details of it.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor which creates exception with details of message, exception cause,
	 *  whether suppression should be enabled and whether writable stack trace should be enabled.
	 * @param message the message about exception 
	 * @param cause cause of the exception (null represents unknown or nonexistent cause)
	 * @param enableSuppression whether suppression is enabled
	 * @param writableStackTrace whether writable stack trace is enabled
	 */
	public SmartScriptParserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/**
	 * Constructor which creates exception with details of message and exception cause.
	 * @param message the message about exception 
	 * @param cause cause of the exception (null represents unknown or nonexistent cause)
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor which creates exception with message details.
	 * @param message the message about exception
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructor which creates exception with exception cause.
	 * @param cause cause of the exception (null represents unknown or nonexistent cause)
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
}
