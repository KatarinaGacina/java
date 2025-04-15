package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents query lexer.
 * @author Katarina Gacina
 * 
 */
public class QueryLexer {
	
	/**
	 * index of first unprocessed query character
	 */
	private int currentIndex;
	
	/**
	 * query text
	 */
	private char[] data;
	
	/**
	 * current token
	 */
	private QueryToken token;
	
	/**
	 * Constructor which takes query text which will be tokenized.
	 * @param text query text
	 * @throws IllegalStateException if it is given a null reference
	 */
	public QueryLexer(String tekst) {
		if (tekst == null) throw new IllegalStateException();
		
		this.data = tekst.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Function returns last generated token. 
	 * It does not start generating the next token.
	 * @return QueryToken last generated token
	 */
	public QueryToken getToken() {
		return this.token;
	}
	
	/**
	 * Function generates and returns next query token.
	 * @return QueryToken next token
	 * @throws IllegalStateException if the error in the process of creating token occurs
	 */
	public QueryToken nextToken() {

		while (currentIndex < data.length) {
			if (charIgnored(data[currentIndex])) {
				currentIndex++;
				
				continue;
			}
			
			break;
		}
		
		if (currentIndex >= data.length || data.length == 0) {
			token = new QueryToken(QueryTokenType.EOF, "");
			
			return token;
		}
		
		if (Character.isLetter(data[currentIndex])) {
			String word = getWord();
			
			switch(word) {
				case "jmbag":
					token = new QueryToken(QueryTokenType.IDENTIFIER, word);
					break;
				case "lastName":
					token = new QueryToken(QueryTokenType.IDENTIFIER, word);
					break;
				case "firstName":
					token = new QueryToken(QueryTokenType.IDENTIFIER, word);
					break;
				case "LIKE":
					token = new QueryToken(QueryTokenType.OPERATOR, word);
					break; 
				default:
					if ((word.toLowerCase()).equals("and")) {
						token = new QueryToken(QueryTokenType.AND, word);
					} else {
						throw new IllegalStateException();
					}
			}
			
		} else if (data[currentIndex] == '"') {
			token = new QueryToken(QueryTokenType.VALUE, getString());
			
		} else {
			StringBuilder sbOperator = new StringBuilder();
			sbOperator.append(data[currentIndex++]);
			
			if (currentIndex < data.length) {
				if (data[currentIndex] == '=') {
					sbOperator.append(data[currentIndex++]);
				}
			}
			
			token = new QueryToken(QueryTokenType.OPERATOR, String.valueOf(sbOperator.toString()));
		}
		
		return token;
	}
	
	/**
	 * Function generates a word.
	 * @return String word
	 */
	private String getWord() {
		StringBuilder sb = new StringBuilder();

		sb.append(data[currentIndex++]);
		
		while (currentIndex < data.length) {
			if (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);
				
				this.currentIndex++;
	
			} else {
				break;
			}
			
			if (currentIndex >= data.length) break;
		}

		return sb.toString();
	}
	
	/**
	 * Function generates a string word.
	 * @return String string word
	 */
	private String getString() {
		StringBuilder sb = new StringBuilder();

		currentIndex++;
		if (currentIndex >= data.length) throw new IllegalStateException();
		
		while (currentIndex < data.length) {
			if (data[currentIndex] != '"') {
				sb.append(data[currentIndex]);
				
				this.currentIndex++;
	
			} else {
				this.currentIndex++;
				
				break;
			}
			
			if (currentIndex >= data.length) break;
		}

		return sb.toString();
	}
	
	/**
	 * Function checks whether the character can be skipped.
	 * @param c tested character
	 * @return true if the character can be skipped, false if the character cannot be skipped
	 */
	private boolean charIgnored(char c) {
		if (c == '\n') return true;
		if (c == '\t') return true;
		if (c == '\r') return true;
		if (c == ' ') return true;
		
		return false;
	}

}
