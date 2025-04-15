package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class that is a node representing a single for-loop construct.
 * @author Katarina Gacina
 */
public class ForLoopNode extends Node {
	/**
	 * for-loop variable
	 */
	private ElementVariable variable;
	/**
	 * for-loop start of the expression
	 */
	private Element startExpression;
	/**
	 * for-loop end of the expression
	 */
	private Element endExpression;
	/**
	 * for-loop step in the expression
	 */
	private Element stepExpression;
	
	/**
	 * Constructor which accepts for-loop variable, start of the expression, 
	 * end of the expression and step in the expression.
	 * @param variable for-loop variable
	 * @param startExpression for-loop start of the expression
	 * @param endExpression for-loop end of the expression
	 * @param stepExpression for-loop step in the expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Constructor which accepts for-loop variable, start of the expression, 
	 * end of the expression.
	 * For-loop step in the expression is initialised to null.
	 * @param variable for-loop variable
	 * @param startExpression for-loop start of the expression
	 * @param endExpression for-loop end of the expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}
	
	/**
	 * Function returns for-loop variable,
	 * @return ElementVariable for-loop variable
	 */
	public ElementVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * Function returns for-loop start of the expression.
	 * @return Element for-loop start of the expression
	 */
	public Element getStartExpression() {
		return this.startExpression;
	}
	
	/**
	 * Function returns for-loop end of the expression.
	 * @return Element for-loop end of the expression
	 */
	public Element getEndExpression() {
		return this.endExpression;
	}
	
	/**
	 * Function returns for-loop step in the expression.
	 * @return Element for-loop step in the expression
	 */
	public Element getStepExpression() {
		return this.stepExpression;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ForLoopNode)) throw new UnsupportedOperationException();
		
		int size1 = this.numberOfChildren();
		int size2 = ((ForLoopNode) obj).numberOfChildren();
		
		if (size1 != size2) return false;
		
		for (int i = 0; i < size1; i++) {
			if (!(this.getChild(i).equals(((ForLoopNode) obj).getChild(i)))) {
				return false;
			}
		}
		
		return true;
	}
}
