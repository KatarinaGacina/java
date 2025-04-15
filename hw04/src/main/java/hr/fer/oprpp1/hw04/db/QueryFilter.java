package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class that is used for filtering StudentRecords based on all given conditional expressions.
 * @author Katarina Gacina
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * list of conditional expressions that have to be satisfied
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Constructor that accepts list of ConditionalExpression objects.
	 * @param lista
	 */
	public QueryFilter(List<ConditionalExpression> lista) {
		this.expressions = lista;
	} 
	
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression condition : expressions) {
			IFieldValueGetter strategy = condition.getStrategy();
			IComparisonOperator operator = condition.getOperatorStrategy();
			String literal = condition.getLiteral();
			
			if (!(operator.satisfied(strategy.get(record), literal))) {
				return false;
			}
		} 
		
		return true;
	}

}
