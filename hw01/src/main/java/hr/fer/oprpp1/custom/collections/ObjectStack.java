package hr.fer.oprpp1.custom.collections;

/**
 * Class represents stack implementation.
 * @author Katarina Gacina
 */
public class ObjectStack {
	/**
	 * stack is realized as instance of an ArrayIndexedCollection
	 */
	private ArrayIndexedCollection stack;
	
	/**
	 * Constructor which initialises empty stack;
	 */
	public ObjectStack() {
		this.stack = new ArrayIndexedCollection();
	}
	
	/**
	 * Function whether the stack is empty.
	 * @return true if stack is empty, false if stack contains one or more elements.
	 */
	public boolean isEmpty() {
		return this.stack.isEmpty();
	}
	
	/**
	 * Function returns number of objects on stack
	 * @return int number of objects on stack
	 */
	public int size() {
		return this.stack.size();
	}
	
	/**
	 * Function adds one object on the top of the stack.
	 * @param value object to be pushed on the stack
	 * @throws NullPointerException if the given object is null reference
	 */
	public void push(Object value) {
		if (value == null) throw new NullPointerException();
		
		this.stack.add(value);
	}
	
	/**
	 * Function removes object from the top of the stack.
	 * @return Object object at the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object pop() {
		if (this.stack.isEmpty()) throw new EmptyStackException();
		
		Object removedObject = this.stack.get(this.stack.size() - 1);
		this.stack.remove(this.stack.size() - 1);
		
		return removedObject;
	}
	
	/**
	 * Function returns object from the top of the stack, without removing it.
	 * @return Object object from the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object peek() {
		if (this.stack.isEmpty()) throw new EmptyStackException();
		
		return this.stack.get(this.stack.size() - 1);
	}
	
	/**
	 * Function removes all objects on the stack.
	 */
	public void clear() {
		this.stack.clear();
	}
}

