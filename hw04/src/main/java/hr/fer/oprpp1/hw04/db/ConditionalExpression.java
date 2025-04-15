package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents conditional expression.
 * @author Katarina Gacina
 *
 */
public class ConditionalExpression {
	
	/**
	 * strategy
	 */
	private IFieldValueGetter strategy;
	
	/**
	 * literal
	 */
	private String literal;
	
	/**
	 * operator
	 */
	private IComparisonOperator operatorStrategy; 
	
	/**
	 * Constructor which accepts four argument of type String.
	 * @param strategy 
	 * @param literal 
	 * @param operatorStrategy
	 */
	public ConditionalExpression(IFieldValueGetter strategy, String literal, IComparisonOperator operatorStrategy) {
		this.strategy = strategy;
		this.literal = literal;
		this.operatorStrategy = operatorStrategy;
	}
	
	/**
	 * Function returns strategy.
	 * @return IFieldValueGetter strategy
	 */
	public IFieldValueGetter getStrategy() {
		return strategy;
	}
	
	/**
	 * Function returns literal.
	 * @return String literal
	 */
	public String getLiteral() {
		return literal;
	}
	
	/**
	 * Function returns operator strategy.
	 * @return IComparisonOperator operation strategy
	 */
	public IComparisonOperator getOperatorStrategy() {
		return operatorStrategy;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ConditionalExpression)) return false;
		
		ConditionalExpression expr = (ConditionalExpression) other;
		if (expr.getLiteral().equals(literal)) {
			if (expr.getOperatorStrategy() == operatorStrategy) {
				if (expr.getStrategy() == strategy) {
					return true;
				}
			}
		}
		
		return true;
	}

}
