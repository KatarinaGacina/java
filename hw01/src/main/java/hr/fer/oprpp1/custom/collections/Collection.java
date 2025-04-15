package hr.fer.oprpp1.custom.collections;

/**
 * Class represents some general collection of objects.
 * @author Katarina Gacina
 */
public class Collection {
	
	/**
	 * Default constructor.
	 */
	protected Collection() {}
	
	/**
	 * Function checks if the collection is empty.
	 * @return true if the collection is empty; false if collection contains one or more objects
	 */
	public boolean isEmpty() {
		if (this.size() == 0) return true;
		return false;
	}
	
	/**
	 * Function returns current number of elements in the collection.
	 * @return 0
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Function adds object at the end of the collection.
	 * @param value object to be added at the end of the collection
	 */
	public void add(Object value) {
	}
	
	/**
	 * Function determines whether collection contains object by using equals method.
	 * @param value searched object in the collection
	 * @return false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Function removes one occurrence of the given object from the collection. 
	 * @param value object whose one occurrence needs to be removed from the collection
	 * @return false
	 */
	boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Function allocates new array of the collection size and fills it with collection elements.
	 * @return Object[] new array with collection elements
	 * @throws UnsupportedOperationException because this class has no storage capacity
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Function calls method processor.process(.) on each elemnt of the collection.
	 * @param processor instance of Processor, with whose function process, action on collection element is performed
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * Function adds all elements to collection from the given collection.
	 * Given collection remains unchanged.
	 * @param other collection whose elements are added
	 */
	public void addAll(Collection other) {
		/**
		 * Local class which implements action of adding objects into collection.
		 */
		class LocalProcessor extends Processor {
			/** 
			 * Function accepts an object and performs an action of adding the object to the collection.
			 * @param value object on which the action is performed
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		LocalProcessor lp = new LocalProcessor();
		other.forEach(lp);
	}
	
	/**
	 * Function removes all elements from the collection.
	 */
	public void clear() {
	}
}

