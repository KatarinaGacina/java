package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class which represents command-line application which accepts one command-line argument.
 * @author Katarina Gacina
 */
public class StackDemo {
	
	/**
	 * Function which evaluates expression given in postfix notation.
	 * @param args[0] given expression in postfix notation, where expression elements are separated with one or more empty spaces.
	*/
	public static void main(String[] args) {
		if (args.length == 0) return;

		String[] elements = args[0].split("\\s+");

		ObjectStack stack = new ObjectStack();
		
		int result;
		int prvi, drugi;
		boolean nepoznatiOperator;
		
		for(String o : elements) {
			try {
				int value = Integer.parseInt(o);
				stack.push(value);
			} catch (NumberFormatException e) {
				result = 0;
				
				try {
					prvi = (int) stack.pop();
					drugi = (int) stack.pop();
					
					nepoznatiOperator = false;
					
					switch(o) {
					  case "+":
					    result = drugi + prvi;
					    break;
					  case "-":
						result = drugi - prvi;  
					    break;
					  case "/":
						result = drugi / prvi;  
						break;
					  case "*":
						result = drugi * prvi;
						break;
					  case "%":
						result = drugi % prvi;
						break;
					  default:
						  nepoznatiOperator = true;
					}
					
					if (nepoznatiOperator) {
						System.out.println("Nepoznati operator!");
						return;
					} else {
						stack.push(result);
					}
					
				} catch (ClassCastException | EmptyStackException e2) {
					System.out.println("Pogreška! Stog je ili prazan ili mu je predan krivi izraz!");
					return;
				}
			}
		}

		if (stack.size() != 1) {
			System.out.println("error");
		} else {
			System.out.println("Expression evaluates to " + stack.pop());
		}
	}

}

