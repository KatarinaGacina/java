package hr.fer.oprpp1.custom.collections;

/**
 * Interface for the list.
 * @author Katarina Gacina
 * @param <E> the type of elements in the list
 */
public interface List<E> extends Collection<E> {
	/**
	 * Function returns the element from the given index in the collection.
	 * @param index int represents index of the element in the collection
	 * @return E element from the given index in the collection
	 * @throws IndexOutOfBoundException if index is less than 0 or greater than the current size of the collection - 1
	 */
	public E get(int index);
	
	/**
	 * Function inserts the element at the given position in the collection.
	 * @param value element which is inserted in the collection
	 * @param position int position in the collection where the given element is inserted
	 * @throws IndexOutOfBoundException if position is less than 0 or greater than the current size of the collection
	 */
	public void insert(E value, int position);
	
	/**
	 * Function returns the position of the first occurrence of the given object in the collection.
	 * @param value object for which index of the first occurrence is searched
	 * @return int position of the first occurrence of the given object in the collection or 
	 * -1 if the given object does not exist in the collection or was given a null reference 
	 */
	public int indexOf(Object value);
	
	/**
	 * Function removes element from the given index in the collection.
	 * @param index int position from which we want to remove the element from the collection
	 * @throws IndexOutOfBoundException if index is less than 0 or greater than the current size of the collection - 1
	 */
	public void remove(int index);
}
