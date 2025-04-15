package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	@Test
	public void testForJMBAG() {
		Path filePath = Paths.get("C:\\Users\\User\\Documents\\5. semestar\\JAVA\\Labosi\\DZ4\\hw04-0036533280\\src\\main\\resources\\database.txt");
		
		try {
			List<String> file = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(file);
			
			assertEquals(new StudentRecord("0000000003", "Bosnić", "Andrea", "4"), database.forJMBAG("0000000003"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFilter() {
		IFilter filter1 = (student) -> {return true;};
		IFilter filter2 = (student) -> {return false;};
		
		Path filePath = Paths.get("C:\\Users\\User\\Documents\\5. semestar\\JAVA\\Labosi\\DZ4\\hw04-0036533280\\src\\main\\resources\\database.txt");
		
		try {
			List<String> file = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(file);
			
			assertEquals(database.getSize(), database.filter(filter1).size());
			assertEquals(0, database.filter(filter2).size());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testWrongJMBAG() {
		List<String> file = new ArrayList<>();
		file.add("0000000003" + "\t" + "Bosnic" + "\t" + "Andrea" + "\t" + "5");
		file.add("0000000003" + "\t" + "Bosnic" + "\t" + "Ana" + "\t" + "5");
			
		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(file));		

	}
	
	@Test
	public void testWrongGrade() {
		List<String> file1 = new ArrayList<>();
		file1.add("0000000003" + "\t" + "Bosnic" + "\t" + "Andrea" + "\t" + "6");
		
		List<String> file2 = new ArrayList<>();
		file2.add("0000000003" + "\t" + "Bosnic" + "\t" + "Ana" + "\t" + "0");
			
		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(file1));		
		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(file1));
	}
}
