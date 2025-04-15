package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class that represents script lexer.
 * @author Katarina Gacina
 */
public class ScriptLexer {
	/**
	 * input text
	 */
	private char[] data;
	
	/**
	 * current token
	 */
	private ScriptToken token; 
	
	/**
	 * index of first unprocessed character
	 */
	private int currentIndex;
	
	/**
	 * lexer state
	 */
	private ScriptLexerState state;
	 
	/**
	 * Constructor which takes input text which will be tokenized.
	 * @param text input text
	 * @throws ScriptLexerException if it is given a null reference
	 */
	public ScriptLexer(String text) { 
		if (text == null) throw new ScriptLexerException();
		  
		this.data = text.toCharArray();
		this.currentIndex = 0;
		
		this.state = ScriptLexerState.BASIC;
	}
	
	/**
	 * Function returns last generated token. 
	 * It does not start generating the next token.
	 * @return ScriptToken last generated token
	 */
	public ScriptToken getScriptToken() {
		return this.token;
	}
	
	/**
	 * Function generates and returns next token.
	 * @return ScriptToken next token
	 * @throws ScriptLexerException if the error in the process of creating token occurs
	 */
	public ScriptToken nextScriptToken() {
		if (this.token != null && this.token.getType() == ScriptTokenType.EOF) { // if we try to generate next token after end of file
			throw new ScriptLexerException();
		}
		
		if (this.currentIndex >= this.data.length || this.data.length == 0) { // if we reached end of the file
			if (this.state != ScriptLexerState.BASIC) throw new ScriptLexerException();
			
			this.token = new ScriptToken(ScriptTokenType.EOF, new Element());
			 
			return this.token;
		}
		
		StringBuilder sb = new StringBuilder();
		
		if (this.state == ScriptLexerState.BASIC) { // BASIC generates text tokens or tokens which indicate change of state
			
			boolean text = false; //indicates if lexer read some text before finding symbols for state change
			
			while (this.currentIndex < this.data.length) {
				if (this.data[this.currentIndex] == '{') {
					if (++this.currentIndex < this.data.length && this.data[this.currentIndex] == '$') {
						this.state = ScriptLexerState.EXTENDED;
						this.currentIndex++;
						
						this.token = new ScriptToken(ScriptTokenType.CHANGE, new ElementString("{$"));

						break;
					} else {
						this.currentIndex--; 
					}
				} else if (this.data[this.currentIndex] == '\\') {
					this.currentIndex++;
					
					if (!notInvalidEscapeText()) throw new ScriptLexerException();
				}
				
				text = true;
				
				sb.append(this.data[this.currentIndex]);
				this.currentIndex++;
			} 
			
			if (text) {
				this.token = new ScriptToken(ScriptTokenType.TEXT, new ElementString(sb.toString()));
			}
			
		} else if (this.state == ScriptLexerState.EXTENDED) { //EXTENDED lexer expects symbols that indicate echo statement, for-loop or end of for-loop
			removeIgnoredCharacters();
			
			if (Character.isLetter(this.data[this.currentIndex])) {
				while (this.currentIndex < this.data.length && 
				(!Character.isWhitespace(this.data[this.currentIndex]) && this.data[this.currentIndex] != '$')) {
						sb.append(this.data[this.currentIndex]);
						this.currentIndex++;
				}

				String tokenContent = sb.toString();

				if (tokenContent.toLowerCase().equals("end")) {
					removeIgnoredCharacters();
					
					if (this.data[this.currentIndex] == '$') {
						this.currentIndex++;
						
						if (this.currentIndex < this.data.length && this.data[this.currentIndex] == '}') {
							this.currentIndex++;
							
							this.state = ScriptLexerState.BASIC;
							
							this.token = new ScriptToken(ScriptTokenType.END, new ElementString("END"));
						} else {
							throw new ScriptLexerException();
						}
					} else {
						throw new ScriptLexerException();
					}
				} else if (tokenContent.toLowerCase().equals("for")) {

					this.state = ScriptLexerState.FOR;
					
					this.token = new ScriptToken(ScriptTokenType.FOR, new ElementString("FOR"));
				} else {
					
					throw new ScriptLexerException();
				}
			} else {
				if (this.data[this.currentIndex] == '=') {

					this.currentIndex++;

					this.state = ScriptLexerState.ECHO;
					
					this.token = new ScriptToken(ScriptTokenType.ECHO, new ElementString("="));
				} else {
					throw new ScriptLexerException();
				}
			}
			
		} else if (this.state == ScriptLexerState.FOR) { //FOR indicates lexer reads from for-loop expression
			
			removeIgnoredCharacters();

			if (Character.isDigit(this.data[this.currentIndex])) {
				Element e = getNumberElement(sb);
				this.token = new ScriptToken(ScriptTokenType.FORELEMENT, e);
				
			} else if (this.data[this.currentIndex] == '-') {
				this.currentIndex++;
				
				if (this.currentIndex < this.data.length) {
					if (Character.isDigit(this.data[this.currentIndex])) {
						sb.append('-');
						
						Element e = getNumberElement(sb);
						this.token = new ScriptToken(ScriptTokenType.FORELEMENT, e);
						
					} else {
						this.currentIndex--;
						
						Element e = getOperatorElement(sb);
						this.token = new ScriptToken(ScriptTokenType.FORELEMENT, e);
						
					}
				} else {
					Element e = getOperatorElement(sb);
					this.token = new ScriptToken(ScriptTokenType.FORELEMENT, e);
					
				}
				
			} else if (Character.isLetter(this.data[this.currentIndex])) {
				Element e = getVariableElement(sb);
				this.token = new ScriptToken(ScriptTokenType.FORELEMENT, e);
				
			} else if (this.data[this.currentIndex] == '"') {
				Element e = getStringElement(sb);
				this.token = new ScriptToken(ScriptTokenType.FORELEMENT, e);
				
			} else if (this.data[this.currentIndex] == '$') {
				this.currentIndex++;
				
				if (this.currentIndex < this.data.length && this.data[this.currentIndex] == '}') {
					this.currentIndex++;
					
					this.state = ScriptLexerState.BASIC;
					
					this.token = new ScriptToken(ScriptTokenType.CHANGE, new ElementString("$}"));
				} else {
					throw new ScriptLexerException();
				}
			} else {
				throw new ScriptLexerException(); 
			}
			
		} else { //ECHO indicates lexer reads from echo statement
			
			removeIgnoredCharacters();

			if (Character.isDigit(this.data[this.currentIndex])) {
				Element element = getNumberElement(sb);
				this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
				
			} else if (Character.isLetter(this.data[this.currentIndex])) {
				Element element = getVariableElement(sb);
				this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
				
			} else if (this.data[this.currentIndex] == '"') {
				Element element = getStringElement(sb);
				this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
				
			} else if (this.data[this.currentIndex] == '@') {
				Element element = getFunctionElement(sb);
				this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
				
			} else if (this.data[this.currentIndex] == '-') {
				this.currentIndex++;
				
				if (this.currentIndex < this.data.length) {
					if (Character.isDigit(this.data[this.currentIndex])) {
						sb.append('-');
						
						Element element = getNumberElement(sb);
						this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
						
					} else {
						this.currentIndex--;
						
						Element element = getOperatorElement(sb);
						this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
					}
				} else {
					Element element = getOperatorElement(sb);
					this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
					
				}
				
			} else if (this.data[this.currentIndex] == '+' || this.data[this.currentIndex] == '*' 
					|| this.data[this.currentIndex] == '/' || this.data[this.currentIndex] == '^') {
				
				Element element = getOperatorElement(sb);
				this.token = new ScriptToken(ScriptTokenType.ECHOELEMENT, element);
				
			} else if (this.data[this.currentIndex] == '$') {
				this.currentIndex++;
				
				if (this.currentIndex < this.data.length && this.data[this.currentIndex] == '}') {
					this.currentIndex++;
					
					this.state = ScriptLexerState.BASIC;
					
					this.token = new ScriptToken(ScriptTokenType.CHANGE, new ElementString("$}"));
				} else {
					throw new ScriptLexerException();
				}
			} else {
				throw new ScriptLexerException();
			}
		}
		
		return this.token;
	}
	
	
	/**
	 * Function generates operator element.
	 * @param sb StringBuilder
	 * @return Element it returns an element which is ElementOperator
	 */
	private Element getOperatorElement(StringBuilder sb) {
		sb.append(this.data[this.currentIndex]);
		this.currentIndex++;
		
		return new ElementOperator(sb.toString());
	}
	
	/**
	 * Function generates function element.
	 * @param sb StringBuilder
	 * @return Element it returns an element which is ElementFunction
	 */
	private Element getFunctionElement(StringBuilder sb) {
		this.currentIndex++;

		while (this.currentIndex < this.data.length) {
			if (Character.isLetter(this.data[this.currentIndex]) || Character.isDigit(this.data[this.currentIndex])
					|| this.data[this.currentIndex] == '_') {
				sb.append(this.data[this.currentIndex]);
				
				this.currentIndex++;
			} else {
				break;
			}
		}
		
		return new ElementFunction(sb.toString());
	}
	
	/**
	 * Function generates string element.
	 * @param sb StringBuilder
	 * @return Element it returns an element which is ElementString
	 */
	private Element getStringElement(StringBuilder sb) {
		this.currentIndex++;
		
		while (this.currentIndex < this.data.length && this.data[this.currentIndex] != '"') {
			if (this.data[this.currentIndex] == '\\') {
				this.currentIndex++;
				
				if (!notInvalidEscapeTag()) {
					if (this.data[this.currentIndex] == 'n') {
						sb.append('\\');
						sb.append('n');
					} else if (this.data[this.currentIndex] == 't') {
						sb.append('\\');
						sb.append('t');
					} else if (this.data[this.currentIndex] == 'r') {
						sb.append('\\');
						sb.append('r');
					} else {
						throw new ScriptLexerException();
					}
					
					this.currentIndex++;

				} else {
					sb.append(this.data[this.currentIndex]);

					this.currentIndex++;
				}
		
			} else {
				sb.append(this.data[this.currentIndex]);
				this.currentIndex++;
			}
		}
		
		this.currentIndex++;

		if (sb.charAt(0) == '\\' || sb.charAt(sb.length() - 1) == '\\') throw new ScriptLexerException();
		
		return new ElementString(sb.toString());
	}
	 
	/**
	 * Function generates variable element.
	 * @param sb StringBuilder
	 * @return Element it returns an element which is ElementVariable
	 */
	private Element getVariableElement(StringBuilder sb) {
		sb.append(this.data[this.currentIndex++]);

		while (this.currentIndex < this.data.length) {
			if (Character.isLetter(this.data[this.currentIndex]) || Character.isDigit(this.data[this.currentIndex])
					|| this.data[this.currentIndex] == '_') {
				sb.append(this.data[this.currentIndex]);
				
				this.currentIndex++;
			} else {
				break;
			}
		}

		return new ElementVariable(sb.toString());
	}
	
	/**
	 * Function generates number element.
	 * @param sb StringBuilder
	 * @return Element it returns an element which is either ElementConstantInteger or ElementConstantDouble
	 * @throws ScriptLexerException if number cannot be written in expected format or second dot occurs
	 */
	private Element getNumberElement(StringBuilder sb) {
		sb.append(this.data[this.currentIndex++]);
		
		boolean expectedDouble = false;
		
		while (this.currentIndex < this.data.length) {
			if (Character.isDigit(this.data[this.currentIndex])) {
				sb.append(this.data[this.currentIndex]);
				
				this.currentIndex++;
				
			} else if (this.data[this.currentIndex] == '.') {
				if (!expectedDouble) {
					expectedDouble = true;
					
					sb.append(this.data[this.currentIndex]);
					
					this.currentIndex++;
					
				} else {
					throw new ScriptLexerException();
				}
			} else {
				break;
			}
		}

		if (expectedDouble) {
			if (!isDouble(sb.toString())) {
				throw new ScriptLexerException();
			}
			
			return new ElementConstantDouble(Double.parseDouble(sb.toString()));
		} else {
			if (!isInteger(sb.toString())) {
				throw new ScriptLexerException();
			}
			
			return new ElementConstantInteger(Integer.parseInt(sb.toString()));
		}
	}

	
	/**
	 * Function skips the characters that can be ignored.
	 */
	private void removeIgnoredCharacters() {
		while (this.currentIndex < this.data.length) {
			if (charIgnored(this.data[this.currentIndex])) {
				this.currentIndex++;
				
				continue;
			}
			
			break;
		}
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
	 * Function checks whether the escape of the character is invalid in text.
	 * @return true in case of escaping characters '\' or '{', otherwise false
	 */
	private boolean notInvalidEscapeText() {
		if (this.currentIndex >= this.data.length) return false;
		
		if (this.data[this.currentIndex] == '\\') return true;
		if (this.data[this.currentIndex] == '{') return true;
		
		return false;
	}
	
	/**
	 * Function checks whether the escape of the character is invalid in string inside the tag.
	 * @return true in case of escaping characters '\' or '"', otherwise false
	 */
	private boolean notInvalidEscapeTag() {
		if (this.currentIndex >= this.data.length) return false;
		
		if (this.data[this.currentIndex] == '\\') return true;
		if (this.data[this.currentIndex] == '"') return true;
		
		return false;
	}
	
	/**
	 * Function checks if the number can be written in integer format.
	 * @param number number represented as string
	 * @return true if number can be stored as integer, false if number cannot be stored as integer
	 */
	private static boolean isInteger(String number) { 
		try {  
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) { 
			return false;  
		}  
	}
	
	/**
	 * Function checks if the number can be written in double format.
	 * @param number number represented as string
	 * @return true if number can be stored as double, false if number cannot be stored as double
	 */
	private static boolean isDouble(String number) { 
		try {  
			Double.parseDouble(number); 
			return true;
		} catch (NumberFormatException e) { 
			return false;  
		}  
	}
	
}
