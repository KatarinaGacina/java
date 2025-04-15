package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Class which represents exception that occurred while creating tokens with ScriptLexer.
 * @author Katarina Gacina
 */
public class ScriptLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which creates exception without any details of it.
	 */
	public ScriptLexerException() {
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
	public ScriptLexerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/**
	 * Constructor which creates exception with details of message and exception cause.
	 * @param message the message about exception 
	 * @param cause cause of the exception (null represents unknown or nonexistent cause)
	 */
	public ScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor which creates exception with message details.
	 * @param message the message about exception
	 */
	public ScriptLexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor which creates exception with exception cause.
	 * @param cause cause of the exception (null represents unknown or nonexistent cause)
	 */
	public ScriptLexerException(Throwable cause) {
		super(cause);
	}
	
	
}
