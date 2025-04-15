package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class represents student database.
 * @author Katarina Gacina
 *
 */
public class StudentDatabase {
	
	/**
	 * list of StudentRecord objects contained in the database
	 */
	private List<StudentRecord> studenti;
	
	/**
	 * map of (key, value) pairs: (JMBAG, StudentRecord) contained in the database;
	 * an index for fast retrieval of student records when JMBAG is known
	 */
	private Map<String, StudentRecord> index;
	
	
	/**
	 * Constructor accepts list of database lines.
	 * @param data list of database lines; each line = one student
	 * @throws IllegalArgumentException for duplicate jmbag or if the grade is not in [1, 5] range
	 */
	public StudentDatabase(List<String> data) {
		studenti = new ArrayList<>();
		index = new HashMap<>();
		
		for (String student : data) {
			String[] studentData = student.split("\t");
			 
			//provjera
			if (this.forJMBAG(studentData[0]) != null) throw new IllegalArgumentException();
			if (Integer.valueOf(studentData[3]) < 1 || Integer.valueOf(studentData[3]) > 5) throw new IllegalArgumentException();
		
			StudentRecord newStudent = new StudentRecord(studentData[0], studentData[1], studentData[2], studentData[3]);
			studenti.add(newStudent);
			index.put(studentData[0], newStudent);
		}
	}
	
	/**
	 * Function returns number of StudentRecord objects in database
	 * @return int number of StudentRecord objects in database
	 */
	public int getSize() {
		return studenti.size();
	}
	
	/**
	 * Function returns StudentRecord with the required JMBAG.
	 * It uses an index for fast retrieval of student records.
	 * @param jmbag the requested jmbag
	 * @return StudentRecord if database contains StudentRecord with the requested jmbag, otherwise null
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * Function returns list of StudentRecord objects witch are accepted by the given filter.
	 * @param filter an instance of functional interface IFilter
	 * @return List<StudentRecord> list of StudentRecord objects witch are accepted by the given filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filterStudents = new ArrayList<>();
		
		for (StudentRecord student : studenti) {
			if (filter.accepts(student)) {
				filterStudents.add(student);
			}
		}
		
		return filterStudents;
	}
	
}
