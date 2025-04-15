package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents a student.
 * @author Katarina Gacina
 *
 */
public class StudentRecord {
	
	/**
	 * student jmbag
	 */
	private String jmbag;
	
	/**
	 * student last name
	 */
	private String lastName;
	
	/**
	 * student first name
	 */
	private String firstName;
	
	/**
	 * student final grade
	 */
	private String finalGrade;
	
	/**
	 * Constructor that accepts four String values.
	 * @param jmbag student jmbag
	 * @param lastName student last name
	 * @param firstName student first name
	 * @param finalGrade student final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Function returns value of hash based on jmbag.
	 * @return int hash value
	 */
	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}
	
	/**
	 * Function checks if the student records are equal.
	 * The two student records are treated as equal if jmbags are equal.
	 * @return true if student records are equal, otherwise false
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StudentRecord)) return false;
		
		if (jmbag.equals(((StudentRecord) obj).jmbag)) return true;
		
		return false;
	}
	
	/**
	 * Function returns student jmbag.
	 * @return String jmbag
	 */
	String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Function returns student last name.
	 * @return String lastName
	 */
	String getLastName() {
		return lastName;
	}
	
	/**
	 * Function returns student first name.
	 * @return String firstName
	 */
	String getFirstName() {
		return firstName;
	}
	
	/**
	 * Function returns student final grade.
	 * @return String finalGrade
	 */
	String getGrade() {
		return finalGrade;
	}
	
}
