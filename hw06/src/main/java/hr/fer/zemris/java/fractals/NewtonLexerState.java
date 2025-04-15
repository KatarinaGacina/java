package hr.fer.zemris.java.fractals;

public enum NewtonLexerState {
	/**
	 * number
	 */
	NUMBER,
	/**
	 * imaginary unit
	 */
	I,
	/**
	 * positive
	 */
	PLUS,
	/**
	 * negative
	 */
	MINUS,
	/**
	 * end of file
	 */
	EOF
}
