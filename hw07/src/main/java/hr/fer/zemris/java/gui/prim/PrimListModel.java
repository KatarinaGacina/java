package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that represents prim list model.
 * @author Katarina Gacina
 *
 */
public class PrimListModel implements ListModel<Integer>{
	
	/**
	 * list of ListDataListeners
	 */
	private List<ListDataListener> promatraci = new ArrayList<>();
	
	/**
	 * list of prim numbers
	 */
	private List<Integer> primBrojevi = new ArrayList<>();
	{
		primBrojevi.add(1);
	}
	
	/**
	 * Function that calculates next prim number and saves it into prim number list.
	 */
	public void next() {
		int number;
		
		if (primBrojevi.size() == 1) {
			number = 2;
	        
	    } else {
	    	boolean pogodak = true;
	    	
	    	number = primBrojevi.get(primBrojevi.size() - 1) + 1;
	    			
	    	while (true) {
	    		for (int i = 2; i*i <= number && pogodak; i++) {
	    	        if (number % i == 0) {
	    	             pogodak = false;
	    	        }
	    	    }
	    	
	    		if (pogodak) {
	    			break;
	    		}
	    		
	    		number++;
	    		pogodak = true;
	       	}
	    }
		
		primBrojevi.add(number);
		
		int pos = primBrojevi.size() - 1;
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : promatraci) {
			l.intervalAdded(event);
		}
	}

	@Override
	public int getSize() {
		return primBrojevi.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primBrojevi.get(index);
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);
	}
	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}
	
}
