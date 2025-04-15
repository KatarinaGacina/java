package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class that is a node representing some textual data.
 * @author Katarina Gacina
 */
public class TextNode extends Node {
	/**
	 * text data
	 */
	private String text;
	
	/**
	 * Constructor which accepts text data.
	 * @param text given string text data
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Function returns text data.
	 * @return String text data.
	 */
	public String getText() {
		return this.text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TextNode)) throw new UnsupportedOperationException();
		
		return true;
	}
}
