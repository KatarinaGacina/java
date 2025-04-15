package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test
	public void testQueryParser1() {
		QueryParser parser = new QueryParser("  jmbag =\"0000000003\"");
		
		assertEquals("0000000003", parser.getQueriedJMBAG());
		assertEquals(true, parser.isDirectQuery());
	}
	
	@Test 
	public void testQueryParser2() {
		QueryParser parser = new QueryParser("  firstName =\"Antea\"");
		
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
		assertEquals(false, parser.isDirectQuery());
	}
	
	@Test
	public void testQueryParser3() {
		QueryParser parser = new QueryParser("  jmbag >\"0000000003\" And firstName= \"Antea\"");
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000003", ComparisonOperators.GREATER));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Antea", ComparisonOperators.EQUALS));
		
		assertArrayEquals(list.toArray(), parser.getQuery().toArray()); 
	}
	
	@Test
	public void testQueryParser4() {
		assertThrows(IllegalStateException.class, () -> new QueryParser("  jmbag >\"0000000003\" And firstName= \"Antea\" and")); 
	}
	
	@Test
	public void testQueryParser5() {
		assertThrows(IllegalStateException.class, () -> new QueryParser("  jmbag >\"0000000003\" And and firstName= \"Antea\"")); 
	}
	
	@Test
	public void testQueryParser6() {
		assertThrows(IllegalStateException.class, () -> new QueryParser("  jmbag >\"0000000003\" And ime= \"Antea\"")); 
	}
}
