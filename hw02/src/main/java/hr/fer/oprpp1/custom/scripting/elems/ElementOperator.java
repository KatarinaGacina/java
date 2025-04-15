package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class used for operator representation.
 * @author Katarina Gacina
 */
public class ElementOperator extends Element {
	/**
	 * operator symbol
	 */
	private String symbol;
	
	/**
	 * Constructor which accepts operator.
	 * @param symbol represents an operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Function returns the stored operator.
	 * @return String stored operator
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
}
