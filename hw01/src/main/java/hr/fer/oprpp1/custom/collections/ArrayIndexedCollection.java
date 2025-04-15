package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * Class which represents an implementation of resizable array-backed collection of objects.
 * @author Katarina Gacina
 */
public class ArrayIndexedCollection extends Collection {
	 
	/**
	 * default collection size
	 */
	static final int DEFAULT_VELICINA = 16;
	
	/**
	 * current collection size
	 */
	private int size;
	/**
	 * an array of Object references to the elements of the collection, whose size is collection capacity 
	 */
	private Object[] elements;
	
	/**
	 * Constructor allocates an array of given size and initialises current capacity on 0.
	 * @param size int which represents the size of the allocated array
	 * @throws IllegalArgumentException if the given size is less than 1
	 */
	public ArrayIndexedCollection(int size) {
		if (size < 1) throw new IllegalArgumentException();
		
		this.elements = new Object[size];
		this.size = 0;
	}
	
	/**
	 * Constructor allocates an array of {@value #DEFAULT_VELICINA} and initialises current capacity on 0.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_VELICINA);
	}
	
	/**
	 * Constructor allocates an array of given size or of size of the given collection, depends on which value is greater,
	 * and adds elements from the given collection to the new collection.
	 * @param other reference to the collection whose elements are added to the new collection
	 * @param size int which represents wanted capacity of the collection
	 * @throws NullPointerException if the null reference is given as argument of type Collection 
	 */
	public ArrayIndexedCollection(Collection other, int size) {
		if (other == null) throw new NullPointerException();
		
		int length = size;
		if (other.size() > size) {
			length = other.size();
		}
		
		this.elements = new Object[length];
		this.size = 0;
		
		int brElemenata = other.toArray().length;
		for (int i = 0; i < brElemenata; i++) {
			this.add(other.toArray()[i]);
		}
	}
	
	/**
	 * * Constructor allocates an array of size {@value #DEFAULT_VELICINA} or of size of the given collection, depends on which value is greater,
	 * and adds elements from the given collection to the new collection.
	 * @param other reference to the collection whose elements are added 
	 * @throws NullPointerException if the null reference is given as argument of type Collection 
	*/
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_VELICINA);
	}
	
	/**
	 * @return int current collection size
	 */
	@Override
	public int size() {		
		return this.size;
	}
	
	/**
	 * Function adds object at the end of the collection.
	 * In case the capacity of the collection is reached, its value doubles.
	 * @param value object to be added at the end of the collection
	 * @throws NullPointerException element of the collection must not be null reference
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		
		if (this.elements.length == this.size) {
			Object[] copy = Arrays.copyOf(this.elements, this.elements.length * 2);
			this.elements = copy;
		}
		
		this.elements[this.size++] = value;
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
		
		int provjera = 0;
		
		while (provjera < this.size - 1) {
			if (this.elements[provjera].equals(value)) return true; 
			
			provjera++;
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
		for (int i = 0; i <= this.size - 1; i++) {
            if (this.elements[i].equals(value)) {
                for(int j = i; j < this.size - 1; j++) {
                    this.elements[j] = this.elements[j+1];
                }
                
                this.elements[--this.size] = null;
                
                return true;
            }
        }
		
		return false;
	}
	
	/**
	 * Function allocates new array of the collection size and fills it with collection elements.
	 * @return Object[] new array with collection elements
	 * @throws UnsupportedOperationException if the collection is empty
	 */
	@Override
	public Object[] toArray() {
		if (this.size == 0) throw new UnsupportedOperationException();
		
		Object[] array = new Object[this.size];
		for (int i = 0; i <= this.size - 1; i++) {
			array[i] = this.elements[i];
		}
		
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		for (Object o : this.elements) {
			if (o == null) break;
			
			processor.process(o);
		}
	}
	
	/**
	 * Function removes all elements from the collection and sets size to 0.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] = null;
		}
		
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

		return this.elements[index];
	}
	
	/**
	 * Function inserts the object at the given position in the collection and, 
	 * if needed, shifts elements at position and at greater positions one place toward the end.
	 * @param value object which is inserted in the collection
	 * @param position int position in the collection where the given object is inserted
	 * @throws IndexOutOfBoundException if position is less than 0 or greater than the current size of the collection
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		
		if (position == this.size) {
			this.add(value);
		} else {
			Object lastElement = this.elements[this.size - 1];
			
			for (int i = this.size - 1; i > position; i--) {
				this.elements[i] = this.elements[i - 1];
			}
			this.elements[position] = value;
			
			if (this.size == this.elements.length) {
				this.add(lastElement);
			} else {
				this.elements[this.size++] = lastElement;
			}
		}
	}
	
	/**
	 * Function returns the position of the first occurrence of the given object in the collection.
	 * @param value object for which index of the first occurrence is searched
	 * @return int position of the first occurrence of the given object in the collection or 
	 * -1 if the given object does not exist in the collection or was given a null reference 
	 */
	public int indexOf(Object value) {
		if (value == null) return -1;
		
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Function removes object from the given index in the collection, and shifts elements at greater positions one place to left.
	 * @param index int position from which we want to remove the object from the collection
	 * @throws IndexOutOfBoundException if index is less than 0 or greater than the current size of the collection - 1
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		if (!(index == this.size - 1)) {
			for (int i = index; i < this.size - 1; i++) {
				this.elements[i] = this.elements[i+1];
	        }
		}
		
		this.elements[this.size - 1] = null;
		
		this.size--;
	}
}

