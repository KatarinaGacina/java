package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Class which represents one ScriptLexer unit.
 * @author Katarina Gacina
 */
public class ScriptToken {
	/**
	 * token type 
	 */
	ScriptTokenType type;
	/**
	 * token value
	 */
	Element value;
	
	/**
	 * Constructor which accepts arguments of token type and value.
	 * @param type type of token
	 * @param value Element value of token
	 */
	public ScriptToken(ScriptTokenType type, Element value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Function returns token value.
	 * @return Element token value
	 */
	public Element getValue() {
		return this.value;
	}
	
	/**
	 * Function returns token type.
	 * @return TokenType token type
	 */
	public ScriptTokenType getType() {
		return this.type;
	}
}
