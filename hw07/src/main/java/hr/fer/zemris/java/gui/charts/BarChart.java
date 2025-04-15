package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class that represents bar chart.
 * @author Katarina Gacina
 *
 */
public class BarChart {
	
	/**
	 * list of chart values
	 */
	private List<XYValue> lista;
	
	/**
	 * name of x axis
	 */
	private String osX;
	
	/**
	 * name of y axis
	 */
	private String osY;
	
	/**
	 * minimum y value
	 */
	private int minY;
	
	/**
	 * maximum y value
	 */
	private int maxY;
	
	/**
	 * distance between two y values labeled on y axis
	 */
	private int razmakY;
	
	/**
	 * Constructor.
	 * @param lista list of XYValues
	 * @param osX name of x axis
	 * @param osY name of y axis
	 * @param minY minimum y value
	 * @param maxY maximum y value 
	 * @param razmakY distance between two y values labeled on y axis
	 */
	public BarChart(List<XYValue> lista, String osX, String osY,
			int minY, int maxY, int razmakY) {
		if (minY < 0) throw new IllegalArgumentException();
		
		for (XYValue v : lista) {
			if (v.getY() < minY) {
				throw new IllegalArgumentException();
			}
		}
		
		this.lista = lista;
		this.osX = osX;
		this.osY = osY;
		this.minY = minY;
		this.maxY = maxY;
		this.razmakY = razmakY;
	}

	/**
	 * Function returns list of XYValues.
	 * @return lista List<XYValue>
	 */
	List<XYValue> getLista() {
		return lista;
	}

	/**
	 * Function returns name of x axis.
	 * @return name of x axis
	 */
	String getOsX() {
		return osX;
	}
	
	/**
	 * Function returns name of y axis.
	 * @return name of y axis
	 */
	String getOsY() {
		return osY;
	}
	
	/**
	 * Function returns the minimum y value.
	 * @return minimum y value
	 */
	int getMinY() {
		return minY;
	}

	/**
	 * Function returns the maximum y value.
	 * @return maximum y value
	 */
	int getMaxY() {
		return maxY;
	}

	/**
	 * Function returns the distance between two y values labeled on y axis.
	 * @return distance between two y values labeled on y axis
	 */
	int getRazmakY() {
		return razmakY;
	}
	
}
