package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class used for integer representation.
 * @author Katarina Gacina
 */
public class ElementConstantInteger extends Element {
	/**
	 * number value
	 */
	private int value;
	
	/**
	 * Constructor which accepts integer number value.
	 * @param value number that is stored
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Function returns string representation of the stored integer number.
	 * @return String string representation of the stored integer number
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
