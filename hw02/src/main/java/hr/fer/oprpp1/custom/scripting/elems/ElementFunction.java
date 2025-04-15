package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class used for function representation.
 * @author Katarina Gacina
 */
public class ElementFunction extends Element {
	/**
	 * function name 
	 */
	private String name;
	
	/**
	 * Constructor which accepts function name.
	 * @param name function name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Function returns the stored function name.
	 * @return String stored function name
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
