package hr.fer.oprpp1.custom.collections;

/**
 * Class which represents the implementation of linked list-backed collection of objects.
 * @author Katarina Gacina
 */
public class LinkedListIndexedCollection extends Collection{
	/**
	 * Class which represents list node.
	 * @author Katarina Gacina
	 */
	private static class ListNode {
		/**
		 * previous list node
		 */
		private ListNode previous;
		/**
		 * next list node
		 */
		private ListNode next;
		/**
		 * object stored in the list node
		 */
		private Object value;
	}
	
	/**
	 * collection size
	 */
	private int size;
	/**
	 * reference to the first ListNode
	 */
	private ListNode first;
	/**
	 * reference to the last ListNode
	 */
	private ListNode last;
	
	/**
	 * Constructor which initialises collection size to 0 
	 * and sets null reference as the reference of the first node and the last node of the collection.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = this.last = null;
	}
	
	/**
	 * Constructor which adds all the elements of the given collection to the new collection.
	 * @param other reference to the collection whose elements are added to the new collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();

		this.addAll(other);
	}
	
	/**
	 * @return int collection size
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Function adds object at the end of the collection.
	 * In case capacity of the collection is reached, its value doubles.
	 * @param value object to be added at the end of the collection
	 * @throws NullPointerException element of the collection must not be null reference
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		
		ListNode newNode = new ListNode(); 
		
		newNode.value = value;
		newNode.next = null;
		newNode.previous = this.last;
		
		if (this.last == null) {
			this.first = newNode;
		} else {
			this.last.next = newNode;
		}

		this.last = newNode;
		
		this.size++;
	}
	
	/**
	 * Function determines whether collection contains object by using equals method.
	 * @param value searched object in the collection
	 * @return false if object is null reference or its value is not in the collection, 
	 * true if its value occurs in the collection
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) return false;
		
		ListNode pomNode = this.first;
		
		for (int i = 0; i < this.size; i++) {
			if ((pomNode.value).equals(value)) {
				return true;
			}
			
			pomNode = pomNode.next;
		}
		
		return false;
	}
	
	/**
	 * Function removes first occurrence of the given object from the collection. 
	 * It determines the occurrence of the object by using method equals.
	 * @param value object whose first occurrence needs to be removed from the collection
	 * @return true if the object is removed,
	 * false if the object is not found or was given a null reference
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) return false;
		
		ListNode pomNode;
		pomNode = this.first;
		
		int i;
		for (i = 0; i < this.size(); i++) {
			if ((pomNode.value).equals(value))  {
				break;
			}
			
			pomNode = pomNode.next;
		}
	
		if (i >= this.size) return false;

		if (i == 0 && this.size == 1) {
			this.first = null;
			this.last = null;
		} else if (i == 0) {
			this.first = pomNode.next;
			pomNode.next.previous = null;
		} else if (i == this.size - 1) {
			this.last = pomNode.previous;
			pomNode.previous.next = null;
		} else {
			pomNode.next.previous = pomNode.previous;
			pomNode.previous.next = pomNode.next;
		}
		
		this.size--;
		
		return true;
	}
	
	/**
	 * Function allocates new array of the collection size and fills it with collection elements.
	 * @return Object[] new array with collection elements
	 * @throws UnsupportedOperationException if the collection is empty
	 */
	@Override
	public Object[] toArray() {
		if (this.first == null) throw new UnsupportedOperationException();
		
		Object[] values = new Object[this.size];
		
		ListNode pomNode = this.first;
		for (int i = 0; i < this.size; i++) {
			values[i] = pomNode.value;
			
			pomNode = pomNode.next;
		}
		
		return values;
	}

	@Override
	public void forEach(Processor processor) {
		ListNode node = this.first;
		
		for (int i = 0; i < this.size; i++) {
			processor.process(node.value);
			
			node = node.next;
		}
	}
	
	/**
	 * Function sets references to the first and the last list node to null and sets the collection size to 0.
	 */
	@Override
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
	}
	
	/**
	 * Function returns the object from the given index in the collection.
	 * @param index int represents index of the object in the collection
	 * @return Object object from the given index in the collection
	 * @throws IndexOutOfBoundException if index is less than 0 or greater than the current size of the collection - 1
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();
		
		ListNode cvor = this.pronadiIndeks(index);
		
		return cvor.value;
	}
	
	/**
	 * Function inserts the object at the given position in the collection.
	 * @param value object which is inserted in the collection
	 * @param position int position in the collection where the given object is inserted
	 * @throws IndexOutOfBoundException if position is less than 0 or greater than the current size of the collection
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		
		ListNode newNode = new ListNode();		
		newNode.value = value;
		newNode.next = null;
		newNode.previous = null;
		
		if (this.first == null) {
			this.first = this.last = newNode;
			
			this.size++;
			return;
		}

		ListNode pomNode = this.first;
		for (int i = 0; i < position; i++) {
			pomNode = pomNode.next;
		}
		if (pomNode == null) { //umetanje na kraj
			this.last.next = newNode;
			newNode.previous = this.last;
			this.last = newNode;
		} else if (position == 0) { //umetanje na pocetak
			this.first.previous = newNode;
			newNode.next = this.first;
			this.first = newNode;
		} else {
			newNode.next = pomNode;
			newNode.previous = pomNode.previous;
			newNode.previous.next = newNode;
			pomNode.previous = newNode;
		}
		
	    this.size++;
	}
	
	/**
	 * Function returns the position of the first occurrence of the given object in the collection.
	 * @param value object for which index of the first occurrence is searched
	 * @return int position of the first occurrence of the given object in the collection or 
	 * -1 if the given object does not exist in the collection or was given a null reference 
	 */
	public int indexOf(Object value) {
		if (value == null) return -1;
		
		ListNode pomNode = this.first;
		
		int i = 0;
		while (pomNode != null) { 
			if ((pomNode.value).equals(value)) {
				return i;
			}
			
			i++;
			pomNode = pomNode.next;
		}
		
		return -1;
	}
	
	/**
	 * Function removes object from the given index in the collection.
	 * @param index int position from which we want to remove the object from the collection
	 * @throws IndexOutOfBoundException if index is less than 0 or greater than the current size of the collection - 1
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();
		
		ListNode oldNode;
		
		if (index == 0) {
			oldNode = this.first;
			
			this.first = this.first.next;
			this.first.previous = null;
		} else if (index == this.size - 1) {
			oldNode = this.last;
			
			this.last = this.last.previous;
			this.last.next = null;
		} else {
			oldNode = this.pronadiIndeks(index);
			
			oldNode.previous.next = oldNode.next;
			oldNode.next.previous = oldNode.previous;
		}
		
		oldNode.next = null;
		oldNode.previous = null;
		
	    this.size--;
	}
	
	/**
	 * Function returns the list node from the specific index in the collection.
	 * @param index int index of the list node in the collection
	 * @return ListNode list node which is located at the given index in the collection
	 */
	private ListNode pronadiIndeks(int index) {
		int pom = this.size / 2;
		ListNode cvor;
		
		if (index < pom) {
			cvor = this.first;
			
			for (int i = 0; i < index; i++) {
				cvor = cvor.next;
			}
		} else {
			cvor = this.last;
			
			for (int i = this.size - 1; i > index; i--) {
				cvor = cvor.previous;
			}
		}
		
		return cvor;
	}
}

