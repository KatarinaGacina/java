package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest extends TestSharedFuncionalityCollections {

    @Test
    public void testNullSizeConstructor() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }
    
    @Test
    public void testNullSizeCollectionConstructor() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 5));
    }
    
    @Test
    public void testExpectedSizeCollectionConstructor() {
    	ArrayIndexedCollection a2 = new ArrayIndexedCollection(2);
    	a2.add(1);
    	a2.add(2);
    	
    	ArrayIndexedCollection a1 = new ArrayIndexedCollection(a2, 1);
    	
    	Object[] expected = {1, 2};
    	
    	assertArrayEquals(expected, a1.toArray());
    }
    
    @Test
    public void testExpectedCollectionConstructor() {
    	ArrayIndexedCollection a2 = new ArrayIndexedCollection(2);
    	a2.add(1);
    	a2.add(2);
    	
    	ArrayIndexedCollection a1 = new ArrayIndexedCollection(a2);

    	assertArrayEquals(new Object[] {1, 2}, a1.toArray());
    }
    
    @Test
    public void testSize( ) {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	testingSize(a);
    }
    
    @Test 
    public void testAddNull() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	testingAddNull(a);
    }
    
    @Test
    public void testAddRelocalize() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	a.add(1);
    	a.add(2);
    	a.add(3);
    	
    	assertEquals(3, a.size());
    }
    
    @Test
    public void testContains() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	testingContains(a);
    }
    
    @Test
    public void testNotContains() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	testingNotContains(a);
    }
    
    @Test
    public void testNullContains() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	testingNullContains(a);
    }
    
    @Test 
    public void testRemoveObjectTrue() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
		a.add(1);
    	a.add(2);
    	a.add(1);
    	
    	assertEquals(true, a.remove((Object)1));
    }
    
    @Test 
    public void testRemoveObjectFalse() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
		a.add(1);
    	a.add(2);
    	a.add(1);
    	
    	assertEquals(false, a.remove((Object)3));
    }
    
    @Test 
    public void testRemoveObjectNull() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
		testingRemoveObjectNull(a);
    }
    
    @Test
    public void testRemoveObjectExpected() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
		a.add(1);
    	a.add(2);
    	a.add(1);
    
    	a.remove((Object)1);
   
    	assertArrayEquals(new Object[] {2, 1}, a.toArray());
    }
    
    @Test
    public void testUnsupportedToArray() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	testingUnsupportedToArray(a);
    }
    
    @Test 
    public void testaddAll() {
    	ArrayIndexedCollection a1 = new ArrayIndexedCollection(3);
    	a1.add(1);
    	
    	ArrayIndexedCollection a2 = new ArrayIndexedCollection(3);
    	a2.add(2);
    	a2.add(3);
    	
    	a1.addAll(a2);
    	
    	assertArrayEquals(new Object[] {1, 2, 3}, a1.toArray());
    }
    
    @Test
    public void testClear() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	testingClear(a);
    }
    
    @Test
    public void testGetTooSmall() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
    }
    
    @Test
    public void testGetTooBig() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection();
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.get(17));
    }
    
    @Test
    public void testGetExpected() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	a.add(1);
    	
    	assertEquals((Object) 1, a.get(0));
    }
    
    @Test
    public void testInsertPositionTooSmall() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.insert(null, -1));
    }
    
    @Test
    public void testInsertPositionTooBig() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.insert(null, 2));
    }
    
    @Test
    public void testInsertExpected1() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	a.add(1);
    	a.add(2);
    	
    	a.insert(3, 2);
    	
    	assertArrayEquals(new Object[] {1, 2, 3}, a.toArray());
    }
    
    @Test
    public void testInsertExpected2() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	a.add(1);
    	a.add(2);
    	
    	a.insert(3, 0);
    	
    	assertArrayEquals(new Object[] {3, 1, 2}, a.toArray());
    }
    
    @Test
    public void testInsertExpected3() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	a.add(1);
    	a.add(2);
    	a.add(3);
    	
    	a.insert(4, 2);
    	
    	assertArrayEquals(new Object[] {1, 2, 4, 3}, a.toArray());
    }
    
    @Test
    public void testNullIndexOf() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	assertEquals(-1, a.indexOf(null));
    }
    
    @Test 
    public void testExpectedIndexOf() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	a.add(1);
    	a.add(2);
    	
    	assertEquals(1, a.indexOf((Object) 2));
    }
    
    @Test
    public void testNonExistingIndexOf() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	a.add(1);
    	a.add(3);
    	
    	assertEquals(-1, a.indexOf((Object) 2));
    }
    
    @Test
    public void testRemoveIndexTooSmall() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
    }
    
    @Test
    public void testRemoveIndexTooBig() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.remove(17));
    }
    
    @Test
    public void testRemoveIndexExpected1() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
		a.add(1);
    	a.add(2);
    	
    	a.remove(0);
    	
    	assertArrayEquals(new Object[] {2}, a.toArray());
    }
    
    @Test
    public void testRemoveIndexExpected2() {
    	ArrayIndexedCollection a = new ArrayIndexedCollection(3);
    	
		a.add(1);
    	a.add(2);
    	
    	a.remove(1);
    	
    	assertArrayEquals(new Object[] {1}, a.toArray());
    }
}