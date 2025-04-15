package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class used for string representation.
 * @author Katarina Gacina
 */
public class ElementString extends Element {
	/**
	 * string
	 */
	private String value;
	
	/**
	 * Constructor which accepts string value.
	 * @param value string value
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Function returns the stored string.
	 * @return String stored string
	 */
	@Override
	public String asText() {
		return this.value;
	}
}
