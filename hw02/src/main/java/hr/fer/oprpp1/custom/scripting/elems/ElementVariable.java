package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class used for variable representation.
 * @author Katarina Gacina
 */
public class ElementVariable extends Element {
	/**
	 * variable
	 */
	private String name;
	
	/**
	 * Constructor which accepts variable name.
	 * @param name variable name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Function returns the stored variable name.
	 * @return String stored variable name
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
