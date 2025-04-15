package hr.fer.oprpp1.hw04.db;

/**
 * Class that contains public static final variables of type IComparisonOperator.
 * @author Katarina Gacina
 *
 */
public class ComparisonOperators {
	
	/**
	 * implements operator less than by using function compareTo
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> { //value1 String, value2 the String to be compared
		if (value1.length() > value2.length()) {
			value1 = value1.substring(0, value2.length());
		}
		
		return value1.compareTo(value2) < 0; 
		};
	
	/**
	 * implements operator less than or equals by using function compareTo
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		if (value1.length() > value2.length()) {
			value1 = value1.substring(0, value2.length());
		}
		
		return value1.compareTo(value2) <= 0;
		};
	
	/**
	 * implements operator greater than by using function compareTo
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		if (value1.length() > value2.length()) {
			value1 = value1.substring(0, value2.length());
		}
		
		return value1.compareTo(value2) > 0;
		};
	
	/**
	* implements operator greater than or equals by using function compareTo
	*/
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		if (value1.length() > value2.length()) {
			value1 = value1.substring(0, value2.length());
		}
		
		return value1.compareTo(value2) >= 0;
		};
	
	/**
	 * implements operator equals by using function compareTo
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return value1.equals(value2);
		};
	
	/**
	 * implements operator not equals by using function compareTo
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return !(value1.equals(value2));
		};
	
	/**
	 * implements operator like
	 * @throws IllegalArgumentException if the given value contains more than one * character
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		if (value2.indexOf('*') != value2.lastIndexOf('*')) throw new IllegalArgumentException();
		
		int index = value2.indexOf('*');
		
		if (index == -1) {
			return value1.equals(value2);
		}
		
		if (index == 0) {
			return value1.endsWith(value2.substring(1));
		} else if (index == value2.length() - 1) {
			return value1.startsWith(value2.substring(0, index));
		} else {
			return (value1.startsWith(value2.substring(0, index)) && value1.endsWith(value2.substring(index + 1)) 
					&& value1.length() >= value2.length() - 1);
		}
		
		}; 
}
