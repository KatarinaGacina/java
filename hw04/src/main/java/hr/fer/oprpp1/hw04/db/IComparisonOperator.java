package hr.fer.oprpp1.hw04.db;

/**
 * Interface used for implementing option of comparing two objects of type String.
 * @author Katarina Gacina
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Function returns true if condition satisfied.
	 * @param value1 first String
	 * @param value2 second String
	 * @return true if condition satisfied, otherwise return false
	 */
	public boolean satisfied(String value1, String value2);
}
