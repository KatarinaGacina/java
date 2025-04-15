package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Interface that represent binary strategy.
 * @author Katarina Gacina
 *
 */
public interface BinaryStrategy {
	
	/**
	 * Function accepts two binary operators and depending on the given boolean value returns one of them.
	 * @param operator original double binary operator
	 * @param invOperator inverted double binary operator
	 * @param inv boolean that represents which operator is needed, original or inverted
	 * @return double binary operator chosen by using boolean value
	 */
	public DoubleBinaryOperator operator(DoubleBinaryOperator operator, DoubleBinaryOperator invOperator, boolean inv);
}
