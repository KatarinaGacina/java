package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Class represents calculator layout.
 * @author Katarina Gacina
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * constraint - component map
	 */
	private Map<Component, RCPosition> ogranicenja;
	
	/**
	 * number of rows
	 */
	private final int rows_num = 5;
	
	/**
	 * number of columns
	 */
	private final int column_num = 7;
	
	/**
	 * distance between two neighbour components
	 */
	private int razmak;
	
	public CalcLayout() {
		this.razmak = 0;
		this.ogranicenja = new HashMap<>();
	}
	
	/**
	 * Constructor.
	 * @param razmak distance between two neighbour components
	 */
	public CalcLayout(int razmak) { //razmak = razmak izmedu redaka = razmak izmedu stupaca
		this.razmak = razmak;
		this.ogranicenja = new HashMap<>();
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
		ogranicenja.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		
        int n = parent.getComponentCount();

        double w = 0;
        double h = 0;
        
        for (int i = 0 ; i < n; i++) {
            Dimension d = parent.getComponent(i).getPreferredSize();
            
            if (d != null) {
            	double d_w = (double) d.width;
            	
            	RCPosition constraint = ogranicenja.get(parent.getComponent(i));
            	if (constraint.getColumn() == 1 && constraint.getRow() == 1) {
            		d_w = (d_w - ((column_num - 1) * razmak)) / 5.;
            	}
            	
            	if (w < d_w) {
                    w = d_w;
                }
                if (h < d.height) {
                    h = d.height;
                }
            }
        }
     
        
        return new Dimension(insets.left + column_num * (int) Math.ceil(w) + (column_num - 1) * razmak + insets.right,
        		insets.top + rows_num * (int) Math.ceil(h) + (rows_num - 1) * razmak + insets.bottom);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {  
		Insets insets = parent.getInsets();
		
        int n = parent.getComponentCount();

        int w = 0;
        int h = 0;
        
        for (int i = 0 ; i < n; i++) {
            Dimension d = parent.getComponent(i).getMinimumSize();
            
            if (d != null) {
            	if (w < d.width) {
                    w = d.width;
                }
                if (h < d.height) {
                    h = d.height;
                }
            }
        }
        
        return new Dimension(insets.left + column_num * (int) Math.ceil(w) + (column_num - 1) * razmak + insets.right,
        		insets.top + rows_num * (int) Math.ceil(h) + (rows_num - 1) * razmak + insets.bottom);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		
        int n = parent.getComponentCount();

        int w = 0;
        int h = 0;
        
        for (int i = 0 ; i < n; i++) {
            Dimension d = parent.getComponent(i).getMaximumSize();
            
            if (d != null) {
            	if (w < d.width) {
                    w = d.width;
                }
                if (h < d.height) {
                    h = d.height;
                }
            }
        }
        
        return new Dimension(insets.left + column_num * (int) Math.ceil(w) + (column_num - 1) * razmak + insets.right,
        		insets.top + rows_num * (int) Math.ceil(h) + (rows_num - 1) * razmak + insets.bottom);
	}
	
	/*private int distribution(int baseComponent, int alternateNum, int number, int i) {
		if (alternateNum == 1 && i == (int) (number / 2)) {
			return baseComponent + 1;
		} else if (alternateNum == 2 && (i == 2 || i == number - 1)) {
			return baseComponent + 1;
		} else if (alternateNum == 3 && ((i % 2 == 0) || (i == 3 && number == 5))) {
			return baseComponent + 1;
		} else if (alternateNum == 4 && (i % 2 == 0 || i == 7 || ((i == 3 || i == 5) && number == 5))) {
			return baseComponent + 1;
		} else if (alternateNum == 5 && (i >= 2 && i <= 6)) {
			return baseComponent + 1;
		} else if (alternateNum == 6 && (i >= 1 && i <= 6)) {
			return baseComponent + 1;
		} else {
			return baseComponent;
		}
		
	}*/

	@Override
	public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        Dimension parentDimension = parent.getSize();
        
        int n = parent.getComponentCount();
        
        double width = parentDimension.getWidth() - insets.left - insets.right - ((column_num - 1) * razmak);
        double height = parentDimension.getHeight() - insets.bottom - insets.top - ((rows_num - 1) * razmak);
        
        double componentWidth = width / column_num;

        int baseComponentWidth = (int) Math.round(componentWidth);
        //int alternateNumWidth = 0;
        if (baseComponentWidth * column_num > width) {
        	baseComponentWidth = (int) componentWidth;
        	//alternateNumWidth = (int) (baseComponentWidth * column_num - width); 
        }
        
        double componentHeight = height / rows_num;
        
        int baseComponentHeight = (int) Math.round(componentHeight);
        //int alternateNumHeight = 0;
        if (baseComponentHeight * rows_num > height) {
        	baseComponentHeight = (int) componentHeight;
        	//alternateNumHeight = (int) (baseComponentHeight * rows_num - width); 
        }
        
        double x = 0;
        double y = 0;

        for (int i = 0; i < n; i++) {
        	RCPosition position = ogranicenja.get(parent.getComponent(i));

        	x = (double) insets.left + (position.getColumn() - 1)*razmak + (position.getColumn() - 1)*baseComponentWidth;
        	y = (double) insets.top + (position.getRow() - 1)*razmak + (position.getRow() - 1)*baseComponentHeight;
        	
        	if (position.getColumn() == 1 && position.getRow() == 1) {
        		parent.getComponent(i).setBounds((int) x, (int) y, baseComponentWidth * 5 + razmak * 4, baseComponentHeight);
        	} else {
        		parent.getComponent(i).setBounds((int) x, (int) y, baseComponentWidth, baseComponentHeight);
        	}
        	
        }
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) throw new NullPointerException();
		
		RCPosition constraint = null;

		if (!(constraints instanceof RCPosition) && 
				!(constraints instanceof String)) {
			throw new IllegalArgumentException();
			
		} else if (constraints instanceof RCPosition) {
			constraint = (RCPosition) constraints;
			
		} else if (constraints instanceof RCPosition) {
			constraint = RCPosition.parse((String) constraints);
		}
		
		if (ogranicenja.containsValue(constraint) ||
				(constraint.getColumn() > 7 || constraint.getColumn() < 1) ||
				(constraint.getRow() < 1 || constraint.getRow() > 5) ||
				(constraint.getRow() == 1 && (constraint.getColumn() >= 2 && constraint.getColumn() <= 5))) {
			
			throw new CalcLayoutException();
		} else {
			ogranicenja.put(comp, constraint);
		}
		
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

}
