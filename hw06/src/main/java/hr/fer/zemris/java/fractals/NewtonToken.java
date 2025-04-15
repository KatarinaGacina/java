package hr.fer.zemris.java.fractals;

/**
 * Class which represents one NewtonLexer unit.
 * @author Katarina Gacina
 */
public class NewtonToken {
	/**
	 * token type 
	 */
	NewtonLexerState type;
	/**
	 * token value
	 */
	String value;
	
	/**
	 * Constructor which accepts arguments of token type and value.
	 * @param type type of token
	 * @param value String value of token
	 */
	public NewtonToken(NewtonLexerState type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Function returns token value.
	 * @return String token value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Function returns token type.
	 * @return TokenType token type
	 */
	public NewtonLexerState getType() {
		return this.type;
	}
}

