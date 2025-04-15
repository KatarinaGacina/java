package hr.fer.zemris.java.gui.calc;

import java.util.function.UnaryOperator;

/**
 * Interface that represents unary operator strategy.
 * @author Katarina Gacina
 *
 */
public interface UnaryOperatorStrategy {
	
	/**
	 * Function accepts two unary operators and depending on the given boolean value performs one of the operators.
	 * @param operator original unary operator
	 * @param invOperator inverted unary operator
	 * @param inv boolean that represents which operator is needed, original or inverted
	 */
	public void operation(UnaryOperator<Double> operator, UnaryOperator<Double> invOperator, boolean inv);
}
