package hr.fer.oprpp1.custom.collections;

/**
 * Interface which represents model of an object used for testing the other object.
 * @author User
 *
 */
@FunctionalInterface
public interface Tester {
	
	/**
	 * Function test the acceptance of the object.
	 * @param obj object which is tested
	 * @return true if object is accepted, false if object is not accepted
	 */
	boolean test (Object obj);
}
