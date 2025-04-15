package hr.fer.oprpp1.hw04.db;

/**
 * Enumeration which contains all possible types of QueryToken.
 * @author Katarina Gacina
 *
 */
public enum QueryTokenType {
	EOF, //end of query text
	IDENTIFIER,
	VALUE,
	AND,
	OPERATOR
}
