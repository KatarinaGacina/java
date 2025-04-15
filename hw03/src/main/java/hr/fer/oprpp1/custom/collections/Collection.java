package hr.fer.oprpp1.custom.collections;

/**
 * Interface for the collections.
 * @author Katarina Gacina
 * @param <E> the type of elements in the collection
 */
public interface Collection<E> {
	
	/**
	 * Function checks if the collection is empty.
	 * @return true if the collection is empty; false if the collection contains one or more elements
	 */
	public default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Function returns the current number of elements in the collection.
	 * @return int current collection size
	 */
	public int size();
	
	/**
	 * Function adds element at the end of the collection.
	 * @param value element to be added at the end of the collection
	 */
	public void add(E value);
	
	/**
	 * Function determines whether the collection contains the object by using equals method.
	 * @param value searched object in the collection
	 * @return true if the collection contains object, false if the collection does not contain object or is given a null reference
	 */
	public boolean contains(Object value);
	
	/**
	 * Function removes one occurrence of the given object from the collection. 
	 * @param value object whose one occurrence needs to be removed from the collection
	 * @return true if the object is removed, false if the object is not removed or is given null reference
	 */
	boolean remove(Object value);
	
	/**
	 * Function allocates a new array of the collection size and fills it with the collection elements.
	 * @return Object[] new array with the collection elements
	 * @throws UnsupportedOperationException if the collection is empty
	 */
	public Object[] toArray();
	
	/**
	 * Function returns the given array filled with the collection elements.
	 * @param <T> the type of the array to contain the collection
	 * @param a the array into which the elements of the collection should be stored
	 * @return an array containing all of the elements in this collection
	 */
	public <T> T[] toArray(T[] a);	
			
	/**
	 * Function iterates throw the collection elements and performs an action on each of them.
	 * @param processor reference to the processor, with whose function process, action on the collection element is performed
	 */
	public default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> collectionGetter = createElementsGetter();
		
		while (collectionGetter.hasNextElement()) {
			E element = collectionGetter.getNextElement();
			
			processor.process(element);
		}
	}
	
	/**
	 * Function adds all elements to the collection from the given collection.
	 * Given collection remains unchanged.
	 * @param other collection whose elements are added
	 */
	public default void addAll(Collection<? extends E> other) {
		/**
		 * Local class which implements an action of adding objects into the collection.
		 */
		class LocalProcessor implements Processor<E> {
			/** 
			 * Function accepts an element and performs an action of adding the element to the collection.
			 * @param value element on which the action is performed
			 */
			@Override
			public void process(E value) {
				add(value);
			}
		}
		
		LocalProcessor lp = new LocalProcessor();
		other.forEach(lp);
	}
	
	/**
	 * Function removes all the elements from the collection.
	 */
	public void clear();
	
	/**
	 * Function creates ElementsGetter, an iterator.
	 * @return ElementsGetter an iterator for the collection
	 */
	public ElementsGetter<E> createElementsGetter();
	
	/**
	 * Function adds all elements which tester accepts to the collection from the given collection.
	 * @param col collection whose elements are added if accepted
	 * @param tester returns true if element is accepted 
	 */
	public default void addAllSatisfying(Collection<? extends E> col, Tester<E> tester) {
		ElementsGetter<? extends E> collectionGetter = col.createElementsGetter();
		
		while (collectionGetter.hasNextElement()) {
			E element = collectionGetter.getNextElement();
			
			if (tester.test(element)) {
				add(element);
			}
		}
	}
}

