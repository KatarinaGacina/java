package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {
	
	@Test
	public void testAddingNullKey() {
		Dictionary<String, String> d = new Dictionary<String, String>();
		
		assertThrows(NullPointerException.class, () -> d.put(null, null));
	}
	
	@Test
	public void testAddingAndGettingPair() {
		Dictionary<String, String> d = new Dictionary<String, String>();
		
		d.put("ponedjeljak", "radni dan");
		d.put("utorak", "radni dan");
		d.put("srijeda", "radni dan");
		d.put("utorak", "praznik");
		
		assertEquals(3, d.size());
		assertEquals("praznik", d.get("utorak"));
	}
	
	@Test
	public void testRemovingPair() {
		Dictionary<String, String> d = new Dictionary<String, String>();
		
		d.put("ponedjeljak", "radni dan");
		d.put("utorak", "radni dan");
		d.put("srijeda", "radni dan");
		
		d.remove("utorak");
		d.remove("cetvrtak");
		
		assertEquals(2, d.size());
		assertEquals(null , d.get("utorak"));
	}
	
	@Test
	public void testClear() {
		Dictionary<String, String> d = new Dictionary<String, String>();
		
		d.put("ponedjeljak", "radni dan");
		d.put("utorak", "radni dan");
		d.put("srijeda", "radni dan");
		
		d.clear();
		
		assertEquals(null, d.get("srijeda"));
		assertEquals(null, d.remove("srijeda"));
		
		assertEquals(0, d.size());
		assertEquals(true, d.isEmpty());
	}
	
	@Test
	public void testAddingWrongDataType() {
		Dictionary<Integer, String> d = new Dictionary<Integer, String>();
		
		d.put(1, "jedan");
		d.put(2, "dva");
		
		assertEquals(null, d.get("jedan"));
	}
}
