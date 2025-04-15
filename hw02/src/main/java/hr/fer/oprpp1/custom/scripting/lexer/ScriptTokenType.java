package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * ScriptToken types that can be used.
 * @author Katarina Gacina
 */
public enum ScriptTokenType {
	/**
	 * element is text
	 */
	TEXT,
	
	/**
	 * for-loop expression is entered
	 */
	FOR,
	
	/**
	 * echo statement is entered
	 */
	ECHO,
	
	/**
	 * element is in the echo statement
	 */
	ECHOELEMENT,
	
	/**
	 * element is in the for-loop expression
	 */
	FORELEMENT,
	
	/**
	 * state of the lexer is changed
	 */
	CHANGE,
	
	/**
	 * for-loop is closed
	 */
	END,
	
	/**
	 * end of file
	 */
	EOF
}