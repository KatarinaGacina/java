package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents one QueryLexer unit.
 * @author Katarina Gacina
 */
public class QueryToken {
	
	/**
	 * token type 
	 */
	QueryTokenType type;
	
	/**
	 * token value
	 */
	String value;
	
	/**
	 * Constructor which accepts arguments of token type and value.
	 * @param type type of token
	 * @param value value of token
	 */
	public QueryToken(QueryTokenType type, String value) {
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
	 * @return QueryTokenType token type
	 */
	public QueryTokenType getType() {
		return this.type;
	}
}
