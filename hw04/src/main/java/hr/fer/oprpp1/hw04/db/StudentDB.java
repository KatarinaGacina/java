package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class enables interaction with the database by using query commands.
 * @author Katarina Gacina
 *
 */
public class StudentDB {
	
	/**
	 * Creates StudentDatabase
	 * , reads query commands and prints output
	 * , runs until command exit is given.  
	 */
	public static void main(String[] args) throws IOException {
		Path filePath = Paths.get("src/main/resources/database.txt").toAbsolutePath();
		List<String> file = Files.readAllLines(filePath, StandardCharsets.UTF_8);
		
		try {
			StudentDatabase database = new StudentDatabase(file);
		
			Scanner scanner = new Scanner(System.in);
			System.out.print("> ");
			while (scanner.hasNextLine()) { 
				String redak = scanner.nextLine();
				
				if ((redak.toLowerCase()).equals("exit")) {
					System.out.println("Goodbye!");
					break; 
				}
				
				if (redak.startsWith("query")) { 
					try {
						QueryParser parser = new QueryParser(redak.substring(5));
						
						if (parser.isDirectQuery()) {
							StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
							
							formatDirectQueryOutput(r);
							
						} else {
							List<StudentRecord> filteredStudents = new ArrayList<>();
							
							int maxJMBAG = 0;
							int maxLastName = 0;
							int maxFirstName = 0;
							
							for (StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
								filteredStudents.add(r);
								
								if (r.getJmbag().length() > maxJMBAG) {
									maxJMBAG = r.getJmbag().length();
								}
								if (r.getLastName().length() > maxLastName) {
									maxLastName = r.getLastName().length();
								}
								if (r.getFirstName().length() > maxFirstName) {
									maxFirstName = r.getFirstName().length();
								}
							}
							
							formatQueryOutput(filteredStudents, maxJMBAG, maxLastName, maxFirstName);
						}
						
					} catch (IllegalStateException ex) {
						System.out.println("Query exception!");
						System.out.println();
					}
					
				} else {
					System.out.println("Unrecognized command!");
					System.out.println();
				}	
				
				System.out.print("> ");
			}
			
			scanner.close();
		
		} catch(IllegalArgumentException ex) {
			System.out.println("Invalid database or query format!");
		}
		
	}
	
	/**
	 * Function that formats and prints output for direct query.
	 * @param r the requested student record
	 */
	private static void formatDirectQueryOutput(StudentRecord r) {
		System.out.println("Using index for record retrieval.");
		
		if (r == null) {
			System.out.println("Searched JMBAG does not exist.");
			
		} else {
			StringBuilder line = new StringBuilder();
			
			line.append("+");
			for (int i = 0; i < r.getJmbag().length() + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < r.getLastName().length() + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < r.getFirstName().length() + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < 3; i++) {
				line.append("=");
			}
			line.append("+");
			
			System.out.println(line.toString());
			
			line.setLength(0);
			
			line.append("| ");
			line.append(r.getJmbag());
			line.append(" | ");
			line.append(r.getLastName());
			line.append(" | ");
			line.append(r.getFirstName());
			line.append(" | ");
			line.append(r.getGrade());
			line.append(" |");
			
			System.out.println(line.toString());
			
			line.setLength(0);
			
			line.append("+");
			for (int i = 0; i < r.getJmbag().length() + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < r.getLastName().length() + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < r.getFirstName().length() + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < 3; i++) {
				line.append("=");
			}
			line.append("+");
			
			System.out.println(line.toString());
			
			System.out.println("Records selected: 1");
		}
		
		System.out.println();
		
	}
	
	/**
	 * Function that formats and prints output for query.
	 * @param studenti list of student records that satisfy query conditional expressions
	 * @param maxJMBAGLen length of the longest jmbag
	 * @param maxLastNameLen length of the longest last name
	 * @param maxFirstNameLen length of the longest first name
	 */
	private static void formatQueryOutput(List<StudentRecord> studenti, int maxJMBAGLen, int maxLastNameLen, int maxFirstNameLen) {

		if (studenti.size() == 0) {
			System.out.println("Records selected: 0");
			
		} else {
			StringBuilder line = new StringBuilder();
			
			line.append("+");
			for (int i = 0; i < maxJMBAGLen + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < maxLastNameLen + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < maxFirstNameLen + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < 3; i++) {
				line.append("=");
			}
			line.append("+");
			
			System.out.println(line.toString());
			
			line.setLength(0);
			
			for (StudentRecord r : studenti) {
				line.append("| ");
				line.append(r.getJmbag());
				for (int i = r.getJmbag().length(); i < maxJMBAGLen; i++) {
					line.append(" ");
				}
				
				line.append(" | ");
				line.append(r.getLastName());
				for (int i = r.getLastName().length(); i < maxLastNameLen; i++) {
					line.append(" ");
				}
				
				line.append(" | ");
				line.append(r.getFirstName());
				for (int i = r.getFirstName().length(); i < maxFirstNameLen; i++) {
					line.append(" ");
				}
				
				line.append(" | ");
				line.append(r.getGrade());
				line.append(" |");
				
				System.out.println(line.toString());
				
				line.setLength(0);
			}
			
			line.append("+");
			for (int i = 0; i < maxJMBAGLen + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < maxLastNameLen + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < maxFirstNameLen + 2; i++) {
				line.append("=");
			}
			line.append("+");
			for (int i = 0; i < 3; i++) {
				line.append("=");
			}
			line.append("+");
			
			System.out.println(line.toString());
			
			System.out.println("Records selected: " + studenti.size());
		}
		
		System.out.println();
		
	}

}
