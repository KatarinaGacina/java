package hr.fer.oprpp1.custom.collections;

/**
 * Class represents stack implementation.
 * @author Katarina Gacina
 * @param <E> the type of elements on the stack
 */
public class ObjectStack<E> {
	/**
	 * stack is realized as an instance of an ArrayIndexedCollection
	 */
	private ArrayIndexedCollection<E> stack;
	
	/**
	 * Constructor which initialises an empty stack;
	 */
	public ObjectStack() {
		this.stack = new ArrayIndexedCollection<E>();
	}
	
	/**
	 * Function checks whether the stack is empty.
	 * @return true if the stack is empty, false if the stack contains one or more elements.
	 */
	public boolean isEmpty() {
		return this.stack.isEmpty();
	}
	
	/**
	 * Function returns number of objects on the stack
	 * @return int number of objects on the stack
	 */
	public int size() {
		return this.stack.size();
	}
	
	/**
	 * Function adds one element on the top of the stack.
	 * @param value element to be pushed on the stack
	 * @throws NullPointerException if the given object is null reference
	 */
	public void push(E value) {
		if (value == null) throw new NullPointerException();
		
		this.stack.add(value);
	}
	
	/**
	 * Function removes element from the top of the stack.
	 * @return E element at the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@SuppressWarnings("unchecked")
	public E pop() {
		if (this.stack.isEmpty()) throw new EmptyStackException();
		
		Object removedObject = this.stack.get(this.stack.size() - 1);
		this.stack.remove(this.stack.size() - 1);
		
		return (E) removedObject;
	}
	
	/**
	 * Function returns element from the top of the stack, without removing it.
	 * @return E element from the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public E peek() {
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

