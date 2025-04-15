package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComparisonExpressionsTest {
	
	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Ana", "Iva"));
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Ana", "Iva"));
	}
	
	@Test
	public void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Ana", "Iva"));
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AABA", "*BA"));
		assertEquals(true, oper.satisfied("ABAA", "AB*"));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Ana", "A*na*"));
		
	}
	
	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertEquals(false, oper.satisfied("3", "1"));
		assertEquals(true, oper.satisfied("1", "3"));
	}
	
	@Test
	public void testLessEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertEquals(false, oper.satisfied("3", "1"));
		assertEquals(true, oper.satisfied("1", "3"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertEquals(true, oper.satisfied("3", "1"));
		assertEquals(false, oper.satisfied("1", "3"));
	}
	
	@Test
	public void testGreaterEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertEquals(true, oper.satisfied("3", "1"));
		assertEquals(false, oper.satisfied("1", "3"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}

}
