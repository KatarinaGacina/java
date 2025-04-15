package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	@Test
	public void testFilter1() {
		QueryParser parser = new QueryParser("  jmbag >=\"0000000003\" And firstName= \"Antea\"");
		QueryFilter filter = new QueryFilter(parser.getQuery());
		StudentRecord student1 = new StudentRecord("0000000003", "Bosnić", "Antea", "5");
		StudentRecord student2 = new StudentRecord("0000000005", "Bosnić", "Iva", "5");
		
		assertEquals(true, filter.accepts(student1));
		assertEquals(false, filter.accepts(student2));
	}
}
