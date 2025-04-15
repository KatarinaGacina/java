package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class which represents an implementation of resizable array-backed collection of objects.
 * @author Katarina Gacina
 * @param <E> the type of elements in the list
 */
public class ArrayIndexedCollection<E> implements List<E> {
	 
	/**
	 * default collection size
	 */
	static final int DEFAULT_VELICINA = 16;
	
	/**
	 * current collection size
	 */
	private int size;
	/**
	 * an array of Object references, its size is the collection capacity 
	 */
	private Object[] elements;
	
	/**
	 * number of ArrayIndexedCollection modifications (array reallocation or shifting elements);
	 * initially set to 0
	 */
	private long modificationCount;
	{
		this.modificationCount = 0;
	}
	
	
	/**
	 * Constructor allocates an array of the given size and initialises current capacity on 0.
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
	 * Constructor allocates an array of the given size or of the size of given collection, depends on which value is greater,
	 * and adds elements from the given collection to the new collection.
	 * @param other collection whose elements are added to the new collection
	 * @param size int which represents the wanted capacity of the collection
	 * @throws NullPointerException if the null reference is given as argument of type Collection 
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends E> other, int size) {
		if (other == null) throw new NullPointerException();
		
		int length = size;
		if (other.size() > size) {
			length = other.size();
		}
		
		this.elements = new Object[length];
		this.size = 0;
		
		int brElemenata = other.toArray().length;
		for (int i = 0; i < brElemenata; i++) {
			this.add((E) other.toArray()[i]);
		}
	}
	
	/**
	 * Constructor allocates an array of size {@value #DEFAULT_VELICINA} or of size of the given collection, depends on which value is greater,
	 * and adds elements from the given collection to the new collection.
	 * @param other collection whose elements are added 
	 * @throws NullPointerException if the null reference is given as argument of type Collection 
	*/
	public ArrayIndexedCollection(Collection<? extends E> other) {
		this(other, DEFAULT_VELICINA);
	}
	
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
	public void add(E value) {
		if (value == null) throw new NullPointerException();
		
		if (this.elements.length == this.size) {
			Object[] copy = Arrays.copyOf(this.elements, this.elements.length * 2);
			this.elements = copy;
		}
		
		this.elements[this.size++] = value;
		
		this.modificationCount++;
	}
	
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
                
            	this.modificationCount++;
                
                return true;
            }
        }
		
		return false;
	}
	
	/**
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
	
	/**
	 * Function removes all elements from the collection and sets size to 0.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
		
		this.modificationCount++;
	}
	
	/**
	 * @throws IndexOutOfBoundException if index is less than 0 or greater than the current size of the collection - 1
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();

		return (E) this.elements[index];
	}
	
	/**
	 * Function inserts the object at the given position in the collection and, 
	 * if needed, shifts elements at position and at greater positions one place toward the end.
	 * @param value object which is inserted in the collection
	 * @param position int position in the collection where the given object is inserted
	 * @throws IndexOutOfBoundException if position is less than 0 or greater than the current size of the collection
	 */
	@Override
	public void insert(E value, int position) {
		if (position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		
		if (position == this.size) {
			this.add(value);
		} else {
			@SuppressWarnings("unchecked")
			E lastElement = (E) this.elements[this.size - 1];
			
			for (int i = this.size - 1; i > position; i--) {
				this.elements[i] = this.elements[i - 1];
			}
			this.elements[position] = value;
			
			this.modificationCount++;
			
			if (this.size == this.elements.length) {
				this.add(lastElement);
			} else {
				this.elements[this.size++] = lastElement;
			}
		}
	}
	
	@Override
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
	@Override
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
		
		this.modificationCount++;
		
		this.size--;
	}
	
	/**
	 * Private static class which represents ElementGetter for class ArrayIndexedCollection.
	 * @author Katarina Gacina
	 */
	private static class ElementsGetterArray<E> implements ElementsGetter<E> {
		/**
		 * number of previous modifications in the moment of creating an ElementGetter (iterator)
		 */
		private final long savedModificationCount;
		
		/**
		 * starting index of iteration
		 */
		private int trenutniIndex;
		/**
		 * number of elements that are not iterated
		 */
		private int preostaloElemenata;
		
		/**
		 * reference to the ArrayIndexedCollection we want to iterate
		 */
		ArrayIndexedCollection<E> a;
		
		/**
		 * Constructor which initialises starting index to 0, 
		 * number of non iterated collection elements to the collection size 
		 * and accepts the reference of the collection.
		 * @param a ArrayIndexedCollection<E> for which the ElementsGetter is created
		 */
		private ElementsGetterArray(ArrayIndexedCollection<E> a) { 
			this.a = a;
			
			this.savedModificationCount = a.modificationCount;
			
			this.trenutniIndex = 0;
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
		@SuppressWarnings("unchecked")
		@Override
		public E getNextElement() {
			if (this.savedModificationCount != a.modificationCount) throw new ConcurrentModificationException();

			if (this.preostaloElemenata < 1) throw new NoSuchElementException();
			
			this.preostaloElemenata--;
			
			return (E) a.elements[this.trenutniIndex++];
		}
		
	}
	
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ElementsGetterArray<E>(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < this.size) {
			 a =  (T[]) Arrays.copyOf(this.elements, this.size, a.getClass());
		} else {
			a = (T[]) Arrays.copyOfRange(this.elements, 0, this.size);
	        
	        for (int i = this.size; i < a.length; i++) {
	        	a[i] = null;
	        }
		}
         
        return a;
	}
}

