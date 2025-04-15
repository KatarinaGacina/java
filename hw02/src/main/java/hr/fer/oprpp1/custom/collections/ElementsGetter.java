
package hr.fer.oprpp1.custom.collections;

/**
 * Interface for the iterator of collection elements.
 * @author Katarina Gacina
 */
public interface ElementsGetter {
	
	/**
	 * Function checks whether the collection has next element.
	 * @return true if the collection has next element, false if the collection does not have next element
	 */
	public boolean hasNextElement();
	
	/**
	 * Function returns next element from the collection.
	 * @return Object next element from the collection
	 */
	public Object getNextElement();
	
	/**
	 * Function processes, performs an action, on the all remaining (not iterated) elements of the collection.
	 * @param p processor which determines an action performed on the collection element
	 */
	public default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
