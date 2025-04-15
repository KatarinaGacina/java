package hr.fer.zemris.java.fractals;

/**
 * Class that represents complex numbers lexer.
 * @author Katarina Gacina
 */
public class NewtonLexer {
	
	/**
	 * index of first unprocessed character
	 */
	private int currentIndex;
	
	/**
	 * input text
	 */
	private char[] data;
	
	/**
	 * current token
	 */
	private NewtonToken token;
	
	/**
	 * Default constructor.
	 */
	public NewtonLexer() {
	
	}
	
	/**
	 * Function that accepts one line that represents complex number.
	 * @param line complex number representation
	 */
	public void setText(String line) {
		this.data = line.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Function returns last generated token. 
	 * It does not start generating the next token.
	 * @return NewtonToken last generated token
	 */
	public NewtonToken getToken() {
		return this.token;
	}
	
	/**
	 * Function generates and returns next token.
	 * @return NewtonToken next token
	 * @throws IllegalStateException if the error in the process of creating token occurs
	 */
	public NewtonToken nextToken() {

		StringBuilder tokenSb = new StringBuilder();

		while (currentIndex < data.length) {
			if (charIgnored(data[currentIndex])) {
				currentIndex++;
				
				continue;
			}
			
			break;
		}
		
		if (currentIndex >= data.length || data.length == 0) {
			this.token = new NewtonToken(NewtonLexerState.EOF, tokenSb.toString());
			return this.token;
		}
		
		if (Character.isDigit(data[currentIndex])) {
			tokenSb.append(getBroj());
			
			this.token = new NewtonToken(NewtonLexerState.NUMBER, tokenSb.toString());
			return this.token;
			
		} else if (Character.isLetter(data[currentIndex])) {
			
			if (data[currentIndex] == 'i') {
				currentIndex++;
				
				this.token = new NewtonToken(NewtonLexerState.I, "i");
				return this.token;
			} else {
				throw new IllegalStateException();
			}
			
		} else if (data[currentIndex] == '+') {
			
			currentIndex++;
			
			this.token = new NewtonToken(NewtonLexerState.PLUS, "+");
			return this.token;
			
		} else if (data[currentIndex++] == '-') {
			
			if (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
					tokenSb.append("-");
					tokenSb.append(getBroj());
					
					this.token = new NewtonToken(NewtonLexerState.NUMBER, tokenSb.toString());
					return this.token;
				
			} else {
				this.token = new NewtonToken(NewtonLexerState.MINUS, "-");
				return this.token;
			}
			
		} else {
			throw new IllegalStateException();
			
		}
	}
	
	/**
	 * Function generates string element.
	 * @return String it returns a string object
	 */
	private String getBroj() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(data[currentIndex++]);
		
		while (currentIndex < data.length) {
			if (Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
				
			} else {
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
		if (c == '\r') return true;
		if (c == '\n') return true;
		if (c == '\t') return true;
		if (c == ' ') return true;
		
		return false;
	}

}
