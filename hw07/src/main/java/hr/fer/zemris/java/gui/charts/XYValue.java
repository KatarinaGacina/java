package hr.fer.zemris.java.gui.charts;

/**
 * Class represents bar chart value.
 * @author Katarina Gacina
 *
 */
public class XYValue {
	
	/**
	 * x value == x name
	 */
	private int x;
	
	/**
	 * y value
	 */
	private int y;
	
	/**
	 * Constructor
	 * @param i x value
	 * @param j y value
	 */
	public XYValue(int i, int j) {
		this.x = i;
		this.y = j;
	}
	
	/**
	 * Function returns x value.
	 * @return x value
	 */
	int getX() {
		return x;
	}
	
	/**
	 * Function returns y value.
	 * @return y value
	 */
	int getY() {
		return y;
	}
	
}
