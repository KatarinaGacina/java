package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionsTest {
	
	@Test
	public void testConditionalExpression() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE);
		
		assertEquals(FieldValueGetters.LAST_NAME, expr.getStrategy());
		assertEquals("Bos*", expr.getLiteral());
		assertEquals(ComparisonOperators.LIKE, expr.getOperatorStrategy());
	}
	

}
