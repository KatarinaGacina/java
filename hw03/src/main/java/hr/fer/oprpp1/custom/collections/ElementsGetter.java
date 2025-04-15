
package hr.fer.oprpp1.custom.collections;

/**
 * Interface for the iterator of collection elements.
 * @author Katarina Gacina
 * @param <E> the type of elements returned by this iterator
 */
public interface ElementsGetter<E> {
	
	/**
	 * Function checks whether the collection has next element.
	 * @return true if the collection has next element, false if the collection does not have next element
	 */
	public boolean hasNextElement();
	
	/**
	 * Function returns next element from the collection.
	 * @return E next element from the collection
	 */
	public E getNextElement();
	
	/**
	 * Function processes, performs an action, on the all remaining (not iterated) elements of the collection.
	 * @param p processor which determines an action performed on the collection element
	 * @throws NullPointerException if it is given a null reference instead processor reference
	 */
	public default void processRemaining(Processor<? super E> p) {
		if (p == null) throw new NullPointerException();
		
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
