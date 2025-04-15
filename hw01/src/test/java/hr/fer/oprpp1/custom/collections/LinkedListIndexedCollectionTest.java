package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest extends TestSharedFuncionalityCollections {
	
	@Test
	public void testCollectionConstructor() {
		LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
		b.add(1);
		b.add(2);
		
		LinkedListIndexedCollection b2 = new LinkedListIndexedCollection(b);
		
		assertArrayEquals(new Object[] {1, 2}, b2.toArray());
	}
	
	@Test
    public void testNullCollectionConstructor() {
        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
    }
    
    @Test
    public void testSize( ) {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingSize(b);
    }
    
    @Test
    public void testAddNull() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingAddNull(b);
    }
    
    @Test
    public void testContains() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingContains(b);
    }
    
    @Test
    public void testNotContains() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingNotContains(b);
    }
    
    @Test
    public void testNullContains() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingNullContains(b);
    }
    
    @Test 
    public void testRemoveObjectTrue() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	
    	assertEquals(true, b.remove((Object)1));
    }
    
    @Test 
    public void testRemoveObjectFalse() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	b.add(1);
    
    	assertEquals(false, b.remove((Object)3));
    }
    
    @Test 
    public void testRemoveObjectNull() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingRemoveObjectNull(b);
    }
    
    @Test
    public void testRemoveObjectExpected1() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	b.add(3);
    	
    	b.remove((Object)1);

    	assertArrayEquals(new Object[] {2, 3}, b.toArray());
    }
    
    @Test
    public void testRemoveObjectExpected2() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	b.add(3);
    	
    	b.remove((Object)3);

    	assertArrayEquals(new Object[] {1, 2}, b.toArray());
    }
    
    @Test
    public void testRemoveObjectExpected3() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	b.add(3);
    	
    	b.remove((Object)2);

    	assertArrayEquals(new Object[] {1, 3}, b.toArray());
    }
    
    @Test
    public void testUnsupportedToArray() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingUnsupportedToArray(b);
    }
     
    @Test
    public void testClear() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	testingClear(b);
    }
    
    @Test
    public void testGetTooSmall() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> b.get(-1));
    }
    
    @Test
    public void testGetTooBig() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> b.get(17));
    }
    
    @Test
    public void testGetExpected() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	b.add(3);
    	b.add(4);
    	
    	assertEquals((Object) 3, b.get(2));
    }
    
    @Test
    public void testInsertPositionTooSmall() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> b.insert(null, -1));
    }
    
    @Test
    public void testInsertPositionTooBig() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> b.insert(null, 1));
    }
    
    @Test
    public void testInsertExpected1() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.insert(1, 0);
    	
    	assertArrayEquals(new Object[] {1}, b.toArray());
    }
    
    @Test
    public void testInsertExpected2() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	
    	b.insert(2, 1);
    	
    	assertArrayEquals(new Object[] {1, 2}, b.toArray());
    }
    
    @Test
    public void testInsertExpected3() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	
    	b.insert(2, 0);
    	
    	assertArrayEquals(new Object[] {2, 1}, b.toArray());
    }
    
    @Test
    public void testInsertExpected4() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	
    	b.insert(3, 1);
    	
    	assertArrayEquals(new Object[] {1, 3, 2}, b.toArray());
    }
    
    @Test
    public void testNullIndexOf() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	assertEquals(-1, b.indexOf(null));
    }
    
    @Test
    public void testExpectedIndexOf() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	
    	assertEquals(1, b.indexOf((Object) 2));
    }
    
    @Test
    public void testNonExistingIndexOf() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(3);
    	
    	assertEquals(-1, b.indexOf((Object) 2));
    }
    
    @Test
    public void testRemoveIndexTooSmall() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> b.remove(-1));
    }
    
    @Test
    public void testRemoveIndexTooBig() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> b.remove(1));
    }
    
    @Test
    public void testRemoveIndexExpected1() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	
    	b.remove(0);
    	
    	assertArrayEquals(new Object[] {2}, b.toArray());
    }
    
    @Test
    public void testRemoveIndexExpected2() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
    	b.add(1);
    	b.add(2);
    	
    	b.remove(1);
    	
    	assertArrayEquals(new Object[] {1}, b.toArray());
    }
    
    @Test
    public void testRemoveIndexExpected3() {
    	LinkedListIndexedCollection b = new LinkedListIndexedCollection(); 
    	
		b.add(1);
    	b.add(2);
    	b.add(3);
    	b.add(4);
    	b.add(5);
    	
    	b.remove(1);
    	
    	assertArrayEquals(new Object[] {1, 3, 4, 5}, b.toArray());
    }
}