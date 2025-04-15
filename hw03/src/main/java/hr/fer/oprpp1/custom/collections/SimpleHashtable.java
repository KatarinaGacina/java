package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class represents an implementation of the map that enables the storage of pairs (key, value)
 * by using hash table.
 * @author Katarina Gacina
 *
 * @param <K> type of the keys
 * @param <V> type of the values
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * default size of the hash table
	 */
	static final int DEFAULT_SIZE = 16;
	
	/**
	 * maximum allowed table occupancy
	 */
	static final double TABLE_OCCUPANCY = 0.75;

	
	/**
	 * hash table, an array of table slots
	 */
	TableEntry<K,V>[] table;
	
	/**
	 * number of pairs stored in the table
	 */
	int size;
	 
	/**
	 * number of times that the map was modified
	 */
	long modificationCount;
	{
		this.modificationCount = 0;
	}
	
	/**
	 * Default constructor.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K,V>[])new TableEntry[DEFAULT_SIZE];
	} 
	
	/**
	 * Constructor which accepts parameter size.
	 * Size changes to the first number equal or greater than the given size which is power of 2.
	 * @param size number of minimum wanted slots in the hash table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int size) {
		if (size < 1) throw new IllegalArgumentException();
		
		if (!((size & (size - 1)) == 0)) {
			int numberOfShifts = 0;
			
	        while (size != 0) {
	            size = size >> 1;
				numberOfShifts += 1;
	        }
	        
	        size = 1 << numberOfShifts;
		}
        
		table = (TableEntry<K,V>[])new TableEntry[size];
	}
	
	/**
	 * Function returns number of pairs stored in the hash table.
	 * @return number of pairs stored in the hash table
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Function returns whether the hash table is empty or not.
	 * @return true if hash table is empty, false if hash table contains one or more pairs
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Class that represents one pair: key - value.
	 * @author Katarina Gacina
	 *
	 * @param <K> type of the key
	 * @param <V> type of the value
	 */
	public static class TableEntry<K, V> {
		/**
		 * key parameter
		 */
		private K key;
		
		/**
		 * value parameter
		 */
		private V value;
		
		/**
		 * next pair in the same slot of the hash table
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructor which accepts key, value and reference to the next pair in the same slot of the hash table.
		 * @param key key parameter
		 * @param value value parameter
		 * @param next reference to the next pair in the same slot of the hash table
		 * @throws NullPointerException key must not be a null reference
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) throw new NullPointerException();
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Function return pair key.
		 * @return K key
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Function returns pair value.
		 * @return V value
		 */
		public V getValue() {
			return this.value;
		}
		
		/**
		 * Function sets pair value.
		 * @param value new value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append(this.getKey());
			sb.append("=");
			sb.append(this.getValue());
			
			return sb.toString();
		}
	}
	
	/**
	 * Function puts a pair into the hash table. 
	 * If hash table already contains a pair with the same key, old value is replaced with the new one.
	 * If the occupancy of the hash table is equal or greater than {@value #TABLE_OCCUPANCY}, 
	 * pairs are reallocated in the new hash table with double size.
	 * @param key pair key
	 * @param value pair value
	 * @throws NullPointerException key must not be a null reference
	 */
	public void put(K key, V value) {
		
		if (key == null) throw new NullPointerException();
		
		if (this.size / this.table.length >= SimpleHashtable.TABLE_OCCUPANCY) {
			
			@SuppressWarnings("unchecked")
			TableEntry<K,V>[] newTable = (TableEntry<K,V>[])new TableEntry[this.table.length * 2];
			
			TableEntry<K,V>[] elements = this.toArray();
			for (int i = 0; i < elements.length; i++) {
				newTable = putElement(newTable, elements[i].getKey(), elements[i].getValue());
			}
			
			this.table = newTable;
			
		}
		
		if (!this.containsKey(key)) this.size++;
		
		this.table = putElement(table, key, value);

	}
	
	/**
	 * Function puts a pair into the given hash table. 
	 * If hash table already contains a pair with the same key, old value is replaced with the new one.
	 * @param table hash table 
	 * @param key pair key
	 * @param value pair value
	 * @returns TableEntry<K, V>[] updated hash table
	*/
	private TableEntry<K, V>[] putElement(TableEntry<K, V>[] table, K key, V value) {
		int slot = Math.abs(key.hashCode()) % table.length;

		TableEntry<K, V> slotElement = table[slot];
		
		if (slotElement == null) {
			table[slot] = new TableEntry<K, V>(key, value, null);
			 
		} else { 
			
			while (true) {
				if ((slotElement.getKey()).equals(key)) {
					slotElement.setValue(value);
					
					break;
				}

				if (slotElement.next == null) {
					slotElement.next = new TableEntry<K, V>(key, value, null);
					
					break;
				}
				
				slotElement = slotElement.next;
			}
		}
		
		return table;
	}
	

	/**
	 * Function removes pair with the given key from the hash table.
	 * @param key key of the pair that is removed from the hash table
	 * @return V value of the removed pair, 
	 * null if it was given a null reference or hash table does not contain pair with the given key 
	 */
	public V remove(Object key) {	
		if (key == null) return null;
		
		int slot = Math.abs(key.hashCode()) % this.table.length;
		
		V removedValue = null;
		
		TableEntry<K, V> slotElement = this.table[slot];
		
		if ((slotElement.getKey()).equals(key)) {
			removedValue = slotElement.getValue();
			slotElement = slotElement.next;
			
			this.table[slot] = slotElement;

			this.size--;
			
			this.modificationCount++;
			
		} else {
			TableEntry<K, V> prevSlotElement = slotElement;
			slotElement = slotElement.next;
			
			while (slotElement != null) {
				if ((slotElement.getKey()).equals(key)) {
					
					prevSlotElement.next = slotElement.next;
					slotElement.next = null;
					
					this.size--;
					
					removedValue = slotElement.getValue();
					
					this.modificationCount++;
					
					break;
				}
				
				prevSlotElement = slotElement;
				slotElement = slotElement.next;
			}
		}
		
		return removedValue;
	}
	
	/**
	 * Function returns the value for the given key.
	 * @param key key parameter
	 * @return V value of the pair with the given key from the hash table, 
	 * null if it was given a null reference or hash table does not contain pair with the given key 
	 */
	public V get(Object key) {
		if (key == null) return null;
		
		V requestedValue = null;
		
		int slot = Math.abs(key.hashCode()) % this.table.length;
		TableEntry<K, V> tableSlot = this.table[slot];
		
		while (tableSlot != null) {
			if ((tableSlot.getKey()).equals(key)) {
				requestedValue = tableSlot.getValue();
				
				break;
			}
			
			tableSlot = tableSlot.next;
		}

		return requestedValue;
	}
	
	/**
	 * Function checks if hash table contains a pair with the given key.
	 * @param key searched key
	 * @return true if the hash table contains a pair with the given key, 
	 * false if it was given a null reference or hash table does not contain pair with the given key 
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false;
		
		int slot = Math.abs(key.hashCode()) % this.table.length;
		TableEntry<K, V> tableSlot = this.table[slot];
		
		while (tableSlot != null) {
			if ((tableSlot.getKey()).equals(key)) return true;
			
			tableSlot = tableSlot.next;
		}
		
		return false;
	}
	
	/**
	 * Function checks if hash table contains any pair with the given value.
	 * @param value searched value
	 * @return true if the hash table contains at least one pair with the given value, 
	 * false if it was given a null reference or hash table does not contain any pair with the given value
	 */
	public boolean containsValue(Object value) {
		
		TableEntry<K, V> tableSlot;
		for (int i = 0; i < this.table.length; i++) {
			tableSlot = this.table[i];
			
			while (tableSlot != null) {
				if ((tableSlot.getValue()).equals(value)) return true;
				
				tableSlot = tableSlot.next;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		TableEntry<K, V> element;
		for (int i = 0; i < this.table.length; i++) {
			element = this.table[i];
			
			while (element != null) {
				sb.append(element.toString());
				sb.append(" ");
				
				element = element.next;
			}
		}
		
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Function returns an array of all pairs stored in the hash table.
	 * Pairs are stored in an array in the same order as they appear in the hash table.
	 * @return TableEntry<K, V>[] an array of all pairs stored in the hash table
	 */
	public TableEntry<K, V>[] toArray() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] polje = (TableEntry<K,V>[])new TableEntry[this.size];
		
		int j = 0;
		
		TableEntry<K, V> element;
		for (int i = 0; i < this.table.length; i++) {
			element = this.table[i];
			
			while (element != null) {
				polje[j] = element;
				
				j++;
				element = element.next;
			}
		}
		
		return polje;
	}
	
	/**
	 * Function removes all pairs from the hash table.
	 */
	public void clear() {
		if (this.size > 0) {
			for (int i = 0; i < this.table.length; i++) {
				this.table[i] = null;
			}
			
			this.size = 0;
		}
	}
	
	
	/**
	 * Class that represents an iterator for SimpleHashtable.
	 * @author Katarina Gacina
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		/**
		 * number of previous modifications of the SimpleHashtable in the moment of iterator creation
		 */
		private long savedModificationCount;
		   
		/**
		 * current pair of the iteration
		 */
        TableEntry<K,V> trenutniElement;
        
        /**
         * next pair of the iteration
         */
        TableEntry<K,V> sljedeciElement;
        
        /**
         * index of the slot where the next pair is stored
         */
        int slotIndex; 
		
        /**
         * Default constructor.
         * Initially current pair is null 
         * and next pair is the first stored pair in the hash table.
         */
		public IteratorImpl() {
			savedModificationCount = modificationCount;
			
			slotIndex = 0;
			trenutniElement = null;
			sljedeciElement = null;
			
			for (int i = 0; i < table.length; i++) { 
				if (table[i] != null) {
					sljedeciElement = table[i];
					slotIndex = i;
					
					break;
				}
			}
		}
		
		/**
		 * Function checks if there is any more pairs stored in the hash table.
		 * @throws ConcurrentModificationException if the hash table was changed from the outside while iterating
		 */
		public boolean hasNext() {
			if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();

			return sljedeciElement != null;
		}
		
		/**
		 * Function returns next pair for the hash table.
		 * @throws NoSuchElementException if next pair does not exist
		 * @throws ConcurrentModificationException if the hash table was changed from the outside while iterating
		 */
		@SuppressWarnings("rawtypes")
		public SimpleHashtable.TableEntry next() {
			if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			
			if (sljedeciElement == null) {
				throw new NoSuchElementException();
			}
			
			trenutniElement = sljedeciElement;
			
			if (trenutniElement.next != null) {
				sljedeciElement = trenutniElement.next;
				
			} else {
				slotIndex++;

				while (slotIndex < table.length && table[slotIndex] == null) {
						slotIndex++;
				} 
				
				if (slotIndex >= table.length) {
					sljedeciElement = null;
				} else {
					sljedeciElement = table[slotIndex];
				}
				
			}

			return trenutniElement;
		}
		
		/**
		 * Function removes pair from the hash table.
		 * @throws ConcurrentModificationException if the hash table was changed from the outside while iterating
		 * @throws IllegalStateException if the function tries to remove the same element more than once
		 */
		public void remove() {
			if (trenutniElement == null) throw new IllegalStateException();
			
			if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			
			SimpleHashtable.this.remove(trenutniElement.key);
			modificationCount = savedModificationCount;
			
			trenutniElement = null;
		}
		
	}
	
	/**
	 * Function returns new iterator for wanted SimpleHashtable.
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

}
