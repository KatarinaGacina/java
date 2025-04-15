package hr.fer.oprpp1.hw02.prob1;

/**
 * Class which represents a simple lexer whit two states.
 * @author Katarina Gacina
 */
public class Lexer {
	/**
	 * input text
	 */
	private char[] data;
	
	/**
	 * current token
	 */
	private Token token;
	
	/**
	 * index of first unprocessed character
	 */
	private int currentIndex; 
	
	/**
	 * lexer state
	 */
	private LexerState state;
	
	/**
	 * Constructor which takes input text which will be tokenized.
	 * @param text input text
	 * @throws NullPointerException if it is given a null reference
	 */
	public Lexer(String text) { 
		if (text == null) throw new NullPointerException();
		 
		this.data = text.toCharArray();
		this.currentIndex = 0;
		
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Function returns last generated token. 
	 * It does not start generating the next token.
	 * @return Token last generated token
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Function used to set lexer state.
	 * Depending on state lexer uses different rules for generating tokens.
	 * @param state LexerState wanted lexer state
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException();
		
		this.state = state;
	}
	
	/**
	 * Function generates and returns next token.
	 * @return Token next token
	 * @throws LexerException if the error in the process of creating token occurs
	 */
	public Token nextToken() {
		if (this.token != null && this.token.getType() == TokenType.EOF) { //throws exception if nextToken is called after last token was generated
			throw new LexerException();
		}
		
		while (this.currentIndex < this.data.length) { //removes ignored characters
			if (charIgnored(this.data[this.currentIndex])) {
				this.currentIndex++;
				
				continue;
			}
			
			break;
		}
		
		if (this.currentIndex >= this.data.length || this.data.length == 0) { //creates EOF token if end of input text is reached
			this.token = new Token(TokenType.EOF, null);
			
			return this.token;
		} 
		
		if (this.state == LexerState.BASIC) { 
			if (this.data[this.currentIndex] == '\\') { 
				
				this.currentIndex++; 

				if (!notInvalidEscape()) throw new LexerException();
				
				this.token = wordFunction();
			} else if (Character.isLetter(this.data[this.currentIndex])) {
				this.token = wordFunction();
			} else if (Character.isDigit(this.data[this.currentIndex])) {
				this.token = numberFunction();
			} else if (this.data[this.currentIndex] == '#') { //change of lexer state to EXTENDED
				setState(LexerState.EXTENDED);
				
				this.currentIndex++;
				
				this.token = new Token(TokenType.SYMBOL, '#');
			} else {
				this.token = symbolFunction();
			}
		} else {
			if (this.data[this.currentIndex] == '#') { //change of lexer state to BASIC
				this.state = LexerState.BASIC;
				
				this.currentIndex++;
				
				this.token = new Token(TokenType.SYMBOL, '#');
			} else {
				StringBuilder sb = new StringBuilder();
				
				while (this.currentIndex < this.data.length) {
					if (this.data[this.currentIndex] == '#') {
						break;
					} else if (!charIgnored(this.data[this.currentIndex])) {				
						sb.append(this.data[this.currentIndex]);
							
						this.currentIndex++;
					} else {
						break;
					} 
				}
				
				this.token = new Token(TokenType.WORD, sb.toString());
			}

		}
		
		return this.token;
	}
	
	/**
	 * Function generates word token.
	 * @return Token word token (TokenType = WORD)
	 */
	private Token wordFunction() {
		TokenType next_type = TokenType.WORD;
		String next_element;
		
		StringBuilder sb = new StringBuilder();

		sb.append(this.data[this.currentIndex++]);
		
		while (true) {
			if (Character.isLetter(this.data[this.currentIndex])) {
				sb.append(this.data[this.currentIndex]);
				
				this.currentIndex++;
			} else if (this.data[this.currentIndex] == '\\') {
				this.currentIndex++;
				
				if (!notInvalidEscape()) throw new LexerException();
				
				sb.append(this.data[this.currentIndex]);
				
				this.currentIndex++;;
			} else {
				break;
			}
			
			if (this.currentIndex >= this.data.length) break;
		}
		
		next_element = sb.toString();
		
		return new Token(next_type, next_element);
	}
	
	/**
	 * Function generates number token.
	 * @return Token word token (TokenType = NUMBER)
	 */
	private Token numberFunction() {
		TokenType next_type = TokenType.NUMBER;
		Long next_element;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.data[this.currentIndex++]);
		
		while (true) {
			if (Character.isDigit(this.data[this.currentIndex])) {
				sb.append(this.data[this.currentIndex]);
				
				this.currentIndex++;
			} else {
				break;
			}
			
			if (this.currentIndex >= this.data.length) break;
		}
		 
		if (!isLong(sb.toString())) {
			throw new LexerException("Nije odgovarajuci ulaz!");
		}
		
		next_element = Long.parseLong(sb.toString());
		
		return new Token(next_type, next_element);
	}
	
	/**
	 * Function generates symbol token.
	 * @return Token word token (TokenType = SYMBOL)
	 */
	private Token symbolFunction() {
		TokenType next_type = TokenType.SYMBOL;
		Character next_element = (Character) this.data[this.currentIndex++];
		
		return new Token(next_type, next_element);
	}
	
	
	/**
	 * Function checks whether the character can be skipped.
	 * @param c tested character
	 * @return true if the character can be skipped, false if the character cannot be skipped
	 */
	private boolean charIgnored(char c) {
		if (c == '\r') return true;
		if (c == '\n') return true;
		if (c == '\t') return true;
		if (c == ' ') return true;
		
		return false;
	}
	
	/**
	 * Function checks whether the escape of the character is invalid.
	 * @return true if the escape is used before digit or character '\', false otherwise
	 */
	private boolean notInvalidEscape() {
		if (this.currentIndex >= this.data.length) return false;
		
		if (Character.isDigit(this.data[this.currentIndex])) return true;
		if (this.data[this.currentIndex] == '\\') return true;
		
		return false;
	}
	
	/**
	 * Function checks if the number can be written in long format.
	 * @param number number represented as string
	 * @return true if number can be stored as long, false if number cannot be stored as long
	 */
	private static boolean isLong(String number) { 
		try {  
			Long.parseLong(number);
			return true;
		} catch (NumberFormatException e) { 
			return false;  
		}  
	}

}
