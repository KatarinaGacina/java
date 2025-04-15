package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayIndexedGenericCollectionTest extends TestSharedFuncionalityGenericCollections {

    @Test
    public void testNullSizeConstructor() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<String>(null));
    }
    
    @Test
    public void testNullSizeCollectionConstructor() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<String>(null, 5));
    }
    
    @Test
    public void testExpectedSizeCollectionConstructor() {
    	ArrayIndexedCollection<Integer> a2 = new ArrayIndexedCollection<Integer>(2);
    	a2.add(1);
    	a2.add(2);
    	
    	ArrayIndexedCollection<Integer> a1 = new ArrayIndexedCollection<Integer>(a2, 1);
    	
    	Object[] expected = {1, 2};
    	
    	assertArrayEquals(expected, a1.toArray());
    }
    
    @Test
    public void testExpectedCollectionConstructor() {
    	ArrayIndexedCollection<Integer> a2 = new ArrayIndexedCollection<Integer>(2);
    	a2.add(1);
    	a2.add(2);
    	
    	ArrayIndexedCollection<Integer> a1 = new ArrayIndexedCollection<Integer>(a2);

    	assertArrayEquals(new Object[] {1, 2}, a1.toArray());
    }
    
    @Test
    public void testSize( ) {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	testingSize(a);
    }
    
    @Test 
    public void testAddNull() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	testingAddNull(a);
    }
    
    @Test
    public void testAddRelocalize() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	a.add(1);
    	a.add(2);
    	a.add(3);
    	
    	assertEquals(3, a.size());
    }
    
    @Test
    public void testContains() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	testingContains(a);
    }
    
    @Test
    public void testNotContains() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	testingNotContains(a);
    }
    
    @Test
    public void testNullContains() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	testingNullContains(a);
    }
    
    @Test 
    public void testRemoveObjectTrue() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
		a.add(1);
    	a.add(2);
    	a.add(1);
    	
    	assertEquals(true, a.remove((Object)1));
    }
    
    @Test 
    public void testRemoveObjectFalse() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
		a.add(1);
    	a.add(2);
    	a.add(1);
    	
    	assertEquals(false, a.remove((Object)3));
    }
    
    @Test 
    public void testRemoveObjectNull() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
		testingRemoveObjectNull(a);
    }
    
    @Test
    public void testRemoveObjectExpected() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
		a.add(1);
    	a.add(2);
    	a.add(1);
    
    	a.remove((Object)1);
   
    	assertArrayEquals(new Object[] {2, 1}, a.toArray());
    }
    
    @Test
    public void testUnsupportedToArray() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	testingUnsupportedToArray(a);
    }
    
    @Test 
    public void testaddAll() {
    	ArrayIndexedCollection<Integer> a1 = new ArrayIndexedCollection<Integer>(3);
    	a1.add(1);
    	
    	LinkedListIndexedCollection<Integer> a2 = new LinkedListIndexedCollection<Integer>();
    	a2.add(2);
    	a2.add(3);
    	
    	a1.addAll(a2);
    	
    	assertArrayEquals(new Object[] {1, 2, 3}, a1.toArray());
    }
    
    @Test
    public void testClear() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	testingClear(a);
    }
    
    @Test
    public void testGetTooSmall() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
    }
    
    @Test
    public void testGetTooBig() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>();
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.get(17));
    }
    
    @Test
    public void testGetExpected() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	a.add(1);
    	
    	assertEquals((Object) 1, a.get(0));
    }
    
    @Test
    public void testInsertPositionTooSmall() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.insert(null, -1));
    }
    
    @Test
    public void testInsertPositionTooBig() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.insert(null, 2));
    }
    
    @Test
    public void testInsertExpected1() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	a.add(1);
    	a.add(2);
    	
    	a.insert(3, 2);
    	
    	assertArrayEquals(new Object[] {1, 2, 3}, a.toArray());
    }
    
    @Test
    public void testInsertExpected2() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	a.add(1);
    	a.add(2);
    	
    	a.insert(3, 0);
    	
    	assertArrayEquals(new Object[] {3, 1, 2}, a.toArray());
    }
    
    @Test
    public void testInsertExpected3() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	a.add(1);
    	a.add(2);
    	a.add(3);
    	
    	a.insert(4, 2);
    	
    	assertArrayEquals(new Object[] {1, 2, 4, 3}, a.toArray());
    }
    
    @Test
    public void testNullIndexOf() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	assertEquals(-1, a.indexOf(null));
    }
    
    @Test 
    public void testExpectedIndexOf() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	a.add(1);
    	a.add(2);
    	
    	assertEquals(1, a.indexOf((Object) 2));
    }
    
    @Test
    public void testNonExistingIndexOf() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	a.add(1);
    	a.add(3);
    	
    	assertEquals(-1, a.indexOf((Object) 2));
    }
    
    @Test
    public void testRemoveIndexTooSmall() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
    }
    
    @Test
    public void testRemoveIndexTooBig() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> a.remove(17));
    } 
    
    @Test
    public void testRemoveIndexExpected1() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
		a.add(1);
    	a.add(2);
    	
    	a.remove(0);
    	
    	assertArrayEquals(new Object[] {2}, a.toArray());
    }
    
    @Test
    public void testRemoveIndexExpected2() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
		a.add(1);
    	a.add(2);
    	
    	a.remove(1);
    	
    	assertArrayEquals(new Object[] {1}, a.toArray());
    }
    
    @Test
    public void testToArray2() {
    	ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<Integer>(3);
    	
		a.add(1);
    	a.add(2);
    	
    	Integer[] a2 = new Integer[4];
    	
    	assertArrayEquals(new Integer[] {1, 2}, a.toArray(a2));
    	
    }
}