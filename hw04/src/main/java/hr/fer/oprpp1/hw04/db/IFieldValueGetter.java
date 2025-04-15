package hr.fer.oprpp1.hw04.db;

/**
 * Interface used for accessing field values of type StudentRecord.
 * @author Katarina Gacina
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Function returns requested field value from record. 
	 * @param record student
	 * @return String
	 */
	public String get(StudentRecord record);
}
