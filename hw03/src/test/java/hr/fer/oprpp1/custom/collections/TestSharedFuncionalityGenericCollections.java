package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

public class TestSharedFuncionalityGenericCollections {
	
    protected void testingSize(Collection<Integer> c) { 
    	c.add(1);
    	c.add(2);
    	
    	assertEquals(2, c.size());
    }
    
    protected void testingAddNull(Collection<Integer> c) {
    	assertThrows(NullPointerException.class, () -> c.add(null));
    }
    
    protected void testingContains(Collection<Integer> c) {
    	c.add(1);
    	c.add(2);
    	
    	assertEquals(true, c.contains(1));
    }
    
    protected void testingNotContains(Collection<Integer> c) {
    	c.add(1);
    	c.add(2);

    	assertEquals(false, c.contains("jabuka"));
    }
    
    protected void testingNullContains(Collection<Integer> c) {
    	c.add(1);
    	c.add(2);

    	assertEquals(false, c.contains(null));
    }
    
    protected void testingRemoveObjectNull(Collection<Integer> c) {
    	c.add(1);

    	assertEquals(false, c.remove(null));
    }
    
    protected void testingUnsupportedToArray(Collection<Integer> c) {
    	assertThrows(UnsupportedOperationException.class, () -> c.toArray());
    }
     
    protected void testingClear(Collection<Integer> c) {
    	c.add(1);
    	c.add(2);
    	
    	c.clear();
    	
    	assertEquals(0, c.size());
    }
}
