package hr.fer.oprpp1.hw02.prob1;

/**
 * Class which represents one lexer unit.
 * @author Katarina Gacina
 */
public class Token {
	
	/**
	 * token type 
	 */
	TokenType type;
	/**
	 * token value
	 */
	Object value;
	
	/**
	 * Constructor which accepts arguments of token type and value.
	 * @param type type of token
	 * @param value object value of token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Function returns token value.
	 * @return Object token value
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Function returns token type.
	 * @return TokenType token type
	 */
	public TokenType getType() {
		return this.type;
	}
}
