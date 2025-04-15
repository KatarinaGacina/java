package hr.fer.zemris.java.gui.layouts;

/**
 * Exception that indicates that user tries to add illegal component or mutiple components on same spot.
 * 
 * @author Katarina Gacina
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public CalcLayoutException() {
	}

	/**
	 * Konstruktor.
	 * @param message poruka koja opisuje pogrešku
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
}
