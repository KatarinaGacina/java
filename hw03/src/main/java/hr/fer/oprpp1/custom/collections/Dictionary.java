package hr.fer.oprpp1.custom.collections;

/**
 * Class represents dictionary implementation.
 * @author Katarina Gacina
 *
 * @param <K> type of the keys in dictionary
 * @param <V> type of the values in dictionary
 */
public class Dictionary<K, V> {
	
	/**
	 * ArrayIndexedCollection of pairs(key, value) in the dictionary
	 */
	private ArrayIndexedCollection<Pair<K, V>> dictionary;
	
	/**
	 * Private static class which represents one pair: key - value in the dictionary
	 * @author Katarina Gacina
	 *
	 * @param <K> type of the key 
	 * @param <V> type of the value
	 */
	private static class Pair<K, V> {
		/**
		 * variable key
		 */
		private K key;
		/**
		 * variable value
		 */
		private V value;
		
		/**
		 * Constructor which accepts key and value.
		 * @param key pair key
		 * @param value pair value
		 */
		Pair(K key, V value) {
			if (key == null) throw new NullPointerException();
			
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Constructor that accepts key. 
		 * It sets value to null.
		 * @param key pair key
		 */
		Pair(K key) {
			this(key, null);
		}
		
		/**
		 * Function returns true if two pair keys are equal, otherwise it returns false.
		 * @return true if pair keys are equal, otherwise it returns false
		 */
		@Override
		public boolean equals(Object obj) { 
			if (!(obj instanceof Pair)) return false;
			
			@SuppressWarnings({ "rawtypes" })
			Pair other = (Pair) obj;
			
			if ((this.key).equals(other.key)) return true;
			
			return false;
		}
	}
	
	/**
	 * Constructor creates an empty dictionary.
	 */
	public Dictionary() {
		this.dictionary = new ArrayIndexedCollection<Pair<K, V>>();
	}
	
	/**
	 * Function checks if the dictionary is empty.
	 * @return true if the dictionary is empty, false if the dictionary contains one or more elements
	 */
	public boolean isEmpty() {
		return this.dictionary.isEmpty();
	}
	
	/**
	 * Function returns number of pairs(key - value) in the dictionary
	 * @return int number of pairs(key - value) in the dictionary
	 */
	public int size() {
		return this.dictionary.size();
	}
	
	/**
	 * Function clears everything from the dictionary.
	 */
	public void clear() {
		this.dictionary.clear();
	}
	
	/**
	 * Function adds new pair to the dictionary. 
	 * If the dictionary already contains pair with the same key, 
	 * old value will be replaced with new.
	 * @param key pair key
	 * @param value pair value
	 * @return V if given pair already exists in the dictionary (same key), old value is returned, 
	 * otherwise null
	 */
	public V put(K key, V value) {
		Pair<K, V> newPair = new Pair<K, V>(key, value);
		
		V oldValue = null;

		if (this.dictionary.contains(newPair)) {
			int i = this.dictionary.indexOf(newPair);
			
			oldValue = this.dictionary.get(i).value;
			
			this.dictionary.remove(i);
			this.dictionary.insert(newPair, i);
		} else {
			this.dictionary.add(newPair);
		}
		
		return oldValue;
	}
	
	/**
	 * Function returns the value for the given key from the dictionary.
	 * @param key pair key
	 * @return null if pair with the given key does not exist in the dictionary or
	 * value for the given key if it exists in the dictionary
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		if (this.dictionary.isEmpty()) return null;
		
		int i = this.dictionary.indexOf(new Pair<K, V>((K) key));
		
		if (i < 0) {
			return null;
		} else {
			return this.dictionary.get(i).value;
		}

	}
	
	/**
	 * Function removes pair: key - value from the dictionary if the keys match.
	 * @param key key of the pair that should be removed from the dictionary
	 * @return null if there is no pair with the given key in the dictionary, 
	 * otherwise value of the pair removed from the dictionary
	 */
	public V remove(K key) {
		if (this.dictionary.isEmpty()) return null;
		
		V oldValue;
		
		int i = this.dictionary.indexOf(new Pair<K, V>(key));
		
		if (i < 0) {
			oldValue = null;
		} else {
			oldValue = this.dictionary.get(i).value;
			
			this.dictionary.remove(i);
		}
		
		return oldValue;
	}
}
