package hr.fer.oprpp1.custom.collections;

/**
 * Interface which represents model of an object capable of performing some operation on the passed object.
 * @author Katarina Gacina
 * @param <T> the type of the input argument
 */

@FunctionalInterface
public interface Processor<T> {
	
	/**
	 * Function accepts an object and performs an action on it.
	 * @param value the input argument
	 */
	public void process(T value);
	
}
