package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Class that is a node representing an echo command.
 * @author Katarina Gacina
 */
public class EchoNode extends Node {
	/**
	 * array of elements inside echo command
	 */
	private Element[] elements;
	
	/**
	 * Constructor that accepts an array of elements.
	 * @param elements elements that echo command contains
	 */
	public EchoNode(Element[] elements) {
		super();
		
		this.elements = elements;
	}
	
	/**
	 * Function that returns node elements.
	 * @return Element[] an array of elements contained in echo command
 	 */
	public Element[] getElements() {
		return this.elements;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EchoNode)) throw new UnsupportedOperationException();
		
		return true;
	}
}
