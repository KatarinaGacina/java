package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	
	@Test
	public void testJMBAG() {
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		
		assertEquals("0000000003", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void testFIRSTNAME() {
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		
		assertEquals("Andrea", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	public void testLASTNAME() {
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
		
		assertEquals("Bosnić", FieldValueGetters.LAST_NAME.get(record));
	}

}
