package hr.fer.oprpp1.custom.collections;

/**
 * 
 * Interface which represents model of an object capable of performing some operation on the passed object.
 * @author Katarina Gacina
 */

@FunctionalInterface
public interface Processor {
	
	/**
	 * Function accepts an object and performs an action on it.
	 * @param value object on which the action is performed
	 */
	public void process(Object value);
	
}
