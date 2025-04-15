package hr.fer.oprpp1.hw04.db;

/**
 * Class that contains public static final variables of type IFieldValueGetter.
 * @author Katarina Gacina
 *
 */
public class FieldValueGetters {
	
	/**
	 * Returns first name from StudentRecord.
	 */
	public static final IFieldValueGetter FIRST_NAME = (student) -> {
		return student.getFirstName();
		};
	
	/**
	* Returns last name from StudentRecord.
	*/
	public static final IFieldValueGetter LAST_NAME = (student) -> {
		return student.getLastName();
		};
	
	/**
	* Returns jmbag from StudentRecord.
	*/
	public static final IFieldValueGetter JMBAG = (student) -> {
		return student.getJmbag();
		};
}
