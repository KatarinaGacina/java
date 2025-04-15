package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * States in which lexer can be.
 * @author Katarina Gacina
 */
public enum ScriptLexerState {
	/**
	 * signalises for-loop or echo statement is expected
	 */
	EXTENDED,
	/**
	 * reads text, initial state
	 */
	BASIC,
	/**
	 * reads for loop expression parts
	 */
	FOR,
	/**
	 * reads elements contained in echo statement
	 */
	ECHO
}
