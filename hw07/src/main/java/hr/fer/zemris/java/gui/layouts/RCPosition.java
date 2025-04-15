package hr.fer.zemris.java.gui.layouts;

/**
 * Class that represents row column component position
 * @author Katarina Gacina
 *
 */
public class RCPosition {
	
	/**
	 * row
	 */
	private int row;
	
	/**
	 * column
	 */
	private int column;
	
	/**
	 * Constructor
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Function returns row value.
	 * @return row
	 */
	int getRow() {
		return row;
	}
	
	/**
	 * Function returns column value.
	 * @return column
	 */
	int getColumn() {
		return column;
	}
	
	/**
	 * Function that parses text, takes row and column values and creates new RCPosition.
	 * @param text input as text
	 * @return RCPosition
	 */
	public static RCPosition parse(String text) {
		String[] vrijednosti = text.split(",");
		
		return new RCPosition(Integer.valueOf(vrijednosti[0].trim()), Integer.valueOf(vrijednosti[1].trim()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RCPosition)) return false;
		
		if (this.column == ((RCPosition) obj).column && this.row == ((RCPosition) obj).row) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
