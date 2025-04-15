package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class used for Double number representation.
 * @author Katarina Gacina
 */
public class ElementConstantDouble extends Element {
	/**
	 * number value
	 */
	private double value;
	
	/**
	 * Constructor which accepts double number value.
	 * @param value number that is stored
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Function returns string representation of the stored double number.
	 * @return String string representation of the stored double number
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
