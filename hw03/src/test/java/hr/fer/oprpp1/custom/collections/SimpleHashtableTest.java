package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {
	
	@Test
	public void testEmptyTable() {
		SimpleHashtable<String, String> s = new SimpleHashtable<>(15);
		
		assertEquals(0, s.size());
		assertEquals(true, s.isEmpty());
	}
	
	@Test
	public void testInvalidTableSize() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<String, Integer>(0));
	}
	
	@Test
	public void testPut() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 2);
		s.put("Jasna", 3);
		
		assertEquals("[Ante=2 Ivana=2 Jasna=3]", s.toString());
	}
	
	@Test
	public void testNullKey() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(1);
		
		assertThrows(NullPointerException.class, () -> s.put(null, null));
	}
	
	@Test
	public void testRemove() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 2);
		s.remove("Ivana");
		s.put("Jasna", 3);
		s.put("Kristina", 5);
		s.put("Luka", 4);
		s.put("Katarina", 5);
		s.remove("Luka");
		
		assertEquals(null, s.remove("Klara"));
		assertEquals(null, s.remove(null));
		
		assertEquals("[Katarina=5 Ante=2 Jasna=3 Kristina=5]", s.toString());
	}
	
	@Test
	public void testGet() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Luka", 4);
		
		assertEquals(4, s.get("Luka"));
	}
	
	@Test
	public void testGetNull() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Luka", 4);
		
		assertEquals(null, s.get("Ante"));
		assertEquals(null, s.get(null));
	}
	
	@Test
	public void testContainsKey() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 2);
		
		assertEquals(true, s.containsKey("Jasna"));
	}
	
	@Test
	public void testContainsKeyFalse() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 2);
		
		assertEquals(false, s.containsKey("Marko"));
		assertEquals(false, s.containsKey(null));
	}
	
	@Test
	public void testContainsValue() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 4);
		
		assertEquals(true, s.containsValue(2));
		assertEquals(true, s.containsValue(4));
	}
	
	@Test
	public void testContainsValueFalse() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
	
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 2);
		
		assertEquals(false, s.containsValue("dva"));
		assertEquals(false, s.containsValue(3));
		assertEquals(false, s.containsValue(null));
	}
	
	@Test
	public void testClear1() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
		
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 2);
		
		s.clear();
		
		assertEquals(0, s.size());
	}
	
	@Test
	public void testClear2() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>();

		s.clear();
		
		assertEquals(0, s.size());
	}
	
	@Test
	public void testToArray() {
		SimpleHashtable<String, Integer> s = new SimpleHashtable<>(2);
		
		s.put("Ivana", 2); 
		s.put("Ante", 2);
		s.put("Jasna", 2);
		s.put("Kristina", 5);
		
		TableEntry<String, Integer>[] polje = s.toArray();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < polje.length; i++) {
			sb.append(polje[i].getKey());
			sb.append(" ");
		}
		
		assertEquals("Ante Ivana Jasna Kristina ", sb.toString());
	}

	@Test
	public void testIterator() {
		SimpleHashtable<String,Integer> s = new SimpleHashtable<>(8);
		
		s.put("Ivana", 2);
		s.put("Ante", 2);
		s.put("Jasna", 2);
		s.put("Kristina", 5);
		s.put("Emanuela", 5);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = s.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); 
			}
		}
		
		assertEquals("[Emanuela=5 Ante=2 Jasna=2 Kristina=5]", s.toString());
	}
	
	@Test
	public void testIteratorNoSuchElement() {
		SimpleHashtable<String,Integer> s = new SimpleHashtable<>(5);
		
		s.put("Ivana", 2);
		s.put("Ante", 2);
		s.put("Jasna", 2);
		s.put("Kristina", 5);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = s.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); 
			}
		}
		
		assertEquals("[Ante=2 Jasna=2 Kristina=5]", s.toString());
		
		assertThrows(NoSuchElementException.class, () -> iter.next());
	}
	
	@Test
	public void testIteratorConcurrentModification() {
		SimpleHashtable<String,Integer> s = new SimpleHashtable<>(8);
		
		s.put("Ivana", 2);
		s.put("Jasna", 2);
		s.put("Kristina", 5);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = s.iterator();
		
		iter.next();
		
		s.remove("Ivana");
		
		assertThrows(ConcurrentModificationException.class, () -> iter.remove());
		assertThrows(ConcurrentModificationException.class, () -> iter.hasNext());
		assertThrows(ConcurrentModificationException.class, () -> iter.next());	
	}
	
	@Test
	public void testIteratorRemoveIllegalState() {
		SimpleHashtable<String,Integer> s = new SimpleHashtable<>(5);
		s.put("Ivana", 2);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = s.iterator();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> iter.remove());
	}

}
