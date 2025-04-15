package hr.fer.oprpp1.hw04.db;

/**
 * Interface used for accepting StudentRecords.
 * @author Katarina Gacina
 *
 */
public interface IFilter {
	
	/**
	 * Function returns true if record is accepted.
	 * @param record student
	 * @return true if record is accepted, otherwise false
	 */
	public boolean accepts(StudentRecord record);
}
