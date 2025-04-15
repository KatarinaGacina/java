package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

public class TestSharedFuncionalityCollections {
	
    protected void testingSize(Collection c) { 
    	c.add(1);
    	c.add(2);
    	
    	assertEquals(2, c.size());
    }
    
    protected void testingAddNull(Collection c) {
    	assertThrows(NullPointerException.class, () -> c.add(null));
    }
    
    protected void testingContains(Collection c) {
    	c.add(1);
    	c.add(2);
    	
    	assertEquals(true, c.contains(1));
    }
    
    protected void testingNotContains(Collection c) {
    	c.add(1);
    	c.add(2);

    	assertEquals(false, c.contains(3));
    }
    
    protected void testingNullContains(Collection c) {
    	c.add(1);
    	c.add(2);

    	assertEquals(false, c.contains(null));
    }
    
    protected void testingRemoveObjectNull(Collection c) {
    	c.add(1);

    	assertEquals(false, c.remove(null));
    }
    
    protected void testingUnsupportedToArray(Collection c) {
    	assertThrows(UnsupportedOperationException.class, () -> c.toArray());
    }
     
    protected void testingClear(Collection c) {
    	c.add(1);
    	c.add(2);
    	
    	c.clear();
    	
    	assertEquals(0, c.size());
    }
}
