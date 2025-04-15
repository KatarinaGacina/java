package hr.fer.oprpp1.custom.collections;

/**
 * Interface which represents model of an object used for testing the other object.
 * @author Katarina Gacina
 * @param <T> the type of the input argument
 */
@FunctionalInterface
public interface Tester<T> {
	
	/**
	 * Function tests the acceptance of the given argument.
	 * @param t the input argument
	 * @return true if object is accepted, false if object is not accepted
	 */
	boolean test (T t);
}
