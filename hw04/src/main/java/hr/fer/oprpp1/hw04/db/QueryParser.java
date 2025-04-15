package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents query parser.
 * @author Katarina Gacina
 * 
 */
public class QueryParser {
	
	/**
	 * query lexer used for parsing the document
	 */
	private QueryLexer queryLexer; 
	
	/**
	 * list of conditional expressions in query
	 */
	private List<ConditionalExpression> queryExpressions;
	
	
	/**
	 * true if query contains only conditional expression with jmbag strategy and equals operator
	 */
	
	private boolean directQuery;
	/**
	 * JMBAG contained in direct query
	 */
	private String queriedJMBAG;

	
	/**
	 * Constructor that accepts query text.
	 * @param redak query text
	 * @throws NullPointerException if it was given a null reference
	 */
	public QueryParser(String redak) {
		if (redak == null) throw new NullPointerException();
		
		queryLexer = new QueryLexer(redak);
		
		queryExpressions = new ArrayList<>();
		directQuery = true;
		
		parseText();
	}
	
	/**
	 * Function used for the query parsing.
	 * It uses query lexer for the production of tokens.
	 * @throws IllegalStateException() if exception occurs due to the incorrect query format
	 */
	private void parseText() {
		QueryToken token = queryLexer.nextToken();

		ConditionalExpression expression;
		IFieldValueGetter strategy;
		IComparisonOperator operator;
		
		boolean andFirst = true;
		boolean andEmpty = false;
		
		while (token.getType() != QueryTokenType.EOF) {
			if (token.getType() == QueryTokenType.IDENTIFIER) {
				
				andFirst = false;
				andEmpty = false;
				
				if (token.getValue().equals("jmbag")) {
					strategy = FieldValueGetters.JMBAG;
				} else if (token.getValue().equals("lastName")) {
					strategy = FieldValueGetters.LAST_NAME;
					directQuery = false;
					
				} else {
					strategy = FieldValueGetters.FIRST_NAME;
					directQuery = false;
					
				}
				
				token = queryLexer.nextToken();
				
				if (token.getType() == QueryTokenType.OPERATOR) {
					if (token.getValue().equals("=")) {
						operator = ComparisonOperators.EQUALS;
						
					} else if (token.getValue().equals("<")) {
						operator = ComparisonOperators.LESS;
						directQuery = false;
						
					} else if (token.getValue().equals(">")) {
						operator = ComparisonOperators.GREATER;
						directQuery = false;
						
					} else if (token.getValue().equals("LIKE")) {
						operator = ComparisonOperators.LIKE;
						directQuery = false;
						
					} else if (token.getValue().equals("<=")) {
						operator = ComparisonOperators.LESS_OR_EQUALS;
						directQuery = false;
						
					} else if (token.getValue().equals(">=")) {
						operator = ComparisonOperators.GREATER_OR_EQUALS;
						directQuery = false;
						
					} else if (token.getValue().equals("!=")) {
						operator = ComparisonOperators.NOT_EQUALS;
						directQuery = false;
						
					} else {
						throw new IllegalStateException();
					}
				} else {
					throw new IllegalStateException();
				}
				
				token = queryLexer.nextToken();
				
				if (token.getType() == QueryTokenType.VALUE) {
					expression = new ConditionalExpression(strategy, token.getValue(), operator);
					
					queryExpressions.add(expression);
					
					if (directQuery) {
						queriedJMBAG = token.getValue();
					}
					
				} else {
					System.out.println(token.getValue());
					throw new IllegalStateException(); 
				}
			} else if (token.getType() == QueryTokenType.AND) {
				directQuery = false;
				
				if (andFirst || andEmpty) {
					throw new IllegalStateException();
				}
				
				andEmpty = true;
				
			} else {
				throw new IllegalStateException();
				
			}

			token = queryLexer.nextToken();
		}
		
		if (andEmpty) throw new IllegalStateException();
		
	}
	
	/**
	 * Function checks for direct query.
	 * @return true if direct query, otherwise false
	 */
	public boolean isDirectQuery() {
		return directQuery;
	}
	
	/**
	 * Function returns JMBAG contained in the direct query.
	 * @return String JMBAG
	 * @throws IllegalStateException if query is not a direct query
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) throw new IllegalStateException();
		
		return queriedJMBAG;
	}
	
	/**
	 * Function returns list of conditional expressions contained in the query.
	 * @return List<ConditionalExpression> list of conditional expressions contained in the query
	 */
	public List<ConditionalExpression> getQuery() {
		return queryExpressions;
	}

}
