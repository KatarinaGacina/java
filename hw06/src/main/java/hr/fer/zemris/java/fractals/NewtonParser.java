package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;

/**
 * Class that represents complex numbers parser.
 * @author Katarina Gacina
 *
 */
public class NewtonParser {
	
	/**
	 * lexer used for parsing the complex number
	 */
	private NewtonLexer scriptLexer;
	
	/**
	 * Default constructor. It creates lexer for complex numbers.
	 */
	public NewtonParser() {
			
		this.scriptLexer = new NewtonLexer();
	}
	
	/**
	 * Function parses through one complex number representation.
	 * @param text complex number representation
	 * @return Complex complex number
	 */
	public Complex startParsing(String text) {
		if (text == null) throw new NullPointerException();
		
		scriptLexer.setText(text);
		return parseText();
	}
	
	/**
	 * Function used for the complex numbers parsing.
	 * It uses lexer for the production of tokens.
	 * @throws IllegalStateException if exception occurs due to the incorrect text format
	 */
	private Complex parseText() {
		
		NewtonToken token = this.scriptLexer.nextToken();

		boolean negativno = false;
		boolean imaginarno = false;
		
		double x = 0;
		double y = 0;
		
		boolean procitaoX = false;
		boolean procitaoY = false;
		
		while (token.getType() != NewtonLexerState.EOF) { // EOF means we reached the end of the document
			
			if (token.getType() == NewtonLexerState.NUMBER) {
				double br = Double.parseDouble(token.getValue());
				
				if (negativno) {
					br = -br;
					negativno = false;
				}
			
				if (imaginarno) {
					if (procitaoY) throw new IllegalStateException();
					
					if (y == - 1) {
						y = -br;
					} else {
						y = br;
					}
					
					imaginarno = false;
					
				} else {
					if (procitaoX) throw new IllegalStateException();
					
					x = br;
				}
				
			} else if (token.getType() == NewtonLexerState.I) {
				imaginarno = true;
				
				if (negativno) {
					y = -1;
					negativno = false;
					
				} else {
					y = 1;
				}
				
			} else if (token.getType() == NewtonLexerState.MINUS) {
				negativno = true;
				
			}
			
			token = this.scriptLexer.nextToken();
			
		}
		
		return new Complex(x, y);

	}
	
}
