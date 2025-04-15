package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class which represents the implementation of linked list-backed collection of objects.
 * @author Katarina Gacina
 */
public class LinkedListIndexedCollection implements List {
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
	 * number of LinkedListIndexedCollection modifications (array reallocation or shifting elements);
	 * initially set to 0
	 */
	private long modificationCount;
	{
		this.modificationCount = 0;
	}
	
	
	/**
	 * Constructor which initialises the collection size to 0 
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
	
	@Override
	public int size() {
		return this.size;
	}
	
	/**
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
		
		this.modificationCount++;
	}
	
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
		
		this.modificationCount++;
		
		return true;
	}
	
	/**
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
	
	/**
	 * Function sets references to the first and the last list node to null and sets the collection size to 0.
	 */
	@Override
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
		
		this.modificationCount++;
	}
	
	/**
	 * @throws IndexOutOfBoundException if index is less than 0 or greater than the current size of the collection - 1
	 */
	@Override
	public Object get(int index) {
		if (index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();
		
		ListNode cvor = this.pronadiIndeks(index);
		
		return cvor.value;
	}
	
	/**
	 * @throws IndexOutOfBoundException if position is less than 0 or greater than the current size of the collection
	 */
	@Override
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
	    
	    this.modificationCount++;
	}
	
	@Override
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
	
	@Override
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
	    
	    this.modificationCount++;
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
	
	/**
	 * Private static class which represents ElementGetter for class LinkedListIndexedCollection.
	 * @author Katarina Gacina
	 */
	private static class ElementsGetterLinkedList implements ElementsGetter {
		/**
		 * number of previous modifications in the moment of creating an ElementGetter (iterator)
		 */
		private final long savedModificationCount;
		
		/**
		 * reference to the LinkedListIndexedCollection we want to iterate
		 */
		LinkedListIndexedCollection a;
		
		/**
		 * current list node
		 */
		private ListNode trenutniCvor;
		/**
		 * number of elements that are not iterated
		 */
		private int preostaloElemenata;
		
		/**
		 * Constructor which initialises starting index to 0, 
		 * number of non iterated collection elements to the collection size 
		 * and accepts the reference of the collection.
		 * @param a LinkedListIndexedCollection for which the ElementsGetter is created
		 */
		public ElementsGetterLinkedList(LinkedListIndexedCollection a) {
			this.savedModificationCount = a.modificationCount;
			
			this.a = a;
			
			this.trenutniCvor = a.first;
			this.preostaloElemenata = a.size();
		}
		
		/**
		 * @throws ConcurrentModificationException if the collection was changed while iterating
		 */
		@Override
		public boolean hasNextElement() {
			if (this.savedModificationCount != a.modificationCount) throw new ConcurrentModificationException();
			
			if (this.preostaloElemenata > 0) return true;
			
			return false;
		}
		
		/**
		 * @throws ConcurrentModificationException if the collection was changed while iterating
		 */
		@Override
		public Object getNextElement() {
			if (this.savedModificationCount != a.modificationCount) throw new ConcurrentModificationException();
			
			if (this.preostaloElemenata < 1) throw new NoSuchElementException();
			
			Object element = this.trenutniCvor.value;
			this.trenutniCvor = this.trenutniCvor.next;
			
			this.preostaloElemenata--;
			
			return element;
		}
		
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterLinkedList(this);
	}
}

