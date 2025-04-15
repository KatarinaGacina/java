package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

/**
 * Class represents BarChart component.
 * @author Katarina Gacina
 *
 */
public class BarChartComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * BarChart reference
	 */
	BarChart barChart;
	
	/**
	 * Constructor.
	 * @param barChart BarChart reference
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
	
	/**
	 * Function paint wanted bar chart component.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int numX = barChart.getLista().size();
		
		double maxValue = barChart.getMaxY();
		double minValue = barChart.getMinY(); 

		Dimension dim = getSize();
	    int widthP = dim.width;
	    int heightP = dim.height;
	    
	    Font labelFont = new Font("Book Antiqua", Font.PLAIN, 14);
	    g.setFont(labelFont);
		
	    FontMetrics metrics = g.getFontMetrics(labelFont);
	    
	    int pomakX = metrics.getHeight() + metrics.stringWidth(Integer.valueOf(barChart.getMaxY()).toString()) + 6; // 5 je velicina crtice
	    int pomakY = metrics.getHeight() * 2; //bottom
	    
	    Graphics2D g2d = (Graphics2D) g; // obtain the graphics context
	    AffineTransform defaultAt = g2d.getTransform();
	    
	    AffineTransform at = new AffineTransform();
	    at.rotate(-Math.PI / 2);
	     
	    g2d.setTransform(at);
	    g2d.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
	    g2d.drawString(barChart.getOsY(), -(heightP) + (heightP- metrics.stringWidth(barChart.getOsY()) - 1)/2  - pomakY, 1 + metrics.getAscent());
	    
	    g2d.setTransform(defaultAt);
	    
	    g.setFont(labelFont);
	    g.drawString(barChart.getOsX(), (pomakX + (widthP - metrics.stringWidth(barChart.getOsX()) - pomakX)/2), heightP - 2);
		
		g.setColor(Color.black);
        g.drawLine(pomakX, heightP - pomakY, widthP, heightP - pomakY);
        g.drawLine(pomakX, heightP - pomakY, pomakX, 0);
        
        g.drawLine(pomakX, heightP - pomakY, pomakX, heightP - pomakY + 5);
        
	    int sirinaX = (widthP - pomakX - 10) / numX;
		double scale = (heightP - pomakY - 10) / (maxValue - minValue);
	    
	    for (int j = 0; j < numX; j++) {	    	 
	    	 int valueP = j * sirinaX + pomakX;
	         int valueQ = 10;
	         
	         int height = (int)Math.ceil((double)(barChart.getLista().get(j).getY()) * scale);
	         
	         if (barChart.getLista().get(j).getY() >= 0) {
	           valueQ += (int)((maxValue - (double)(barChart.getLista().get(j).getY())) * scale);
	         } else {
	           valueQ += (int)(maxValue * scale);
	           height = -height;
	         }
	    
	         g.setColor(Color.ORANGE);
	         g.fillRect(valueP, valueQ, sirinaX, height);
	         g.setColor(Color.black);
	         g.drawRect(valueP, valueQ, sirinaX, height);
	         
	         int labelWidth = metrics.stringWidth(Integer.valueOf(barChart.getLista().get(j).getX()).toString());
		     int stringWidth = ((int)(j * sirinaX + (sirinaX - labelWidth) / 2) + pomakX);
		     g.drawString(Integer.valueOf(barChart.getLista().get(j).getX()).toString(), stringWidth, heightP - pomakY + metrics.getHeight());
		     
		     g.drawLine((j + 1) * sirinaX + pomakX, heightP - pomakY, (j + 1) * sirinaX + pomakX, heightP - pomakY + 5);
	    }
	    
	    for (int i = 0; i <= (maxValue - minValue) / barChart.getRazmakY(); i++) {
	    	g.drawLine(pomakX - 5, (int)(heightP - pomakY - (i) * barChart.getRazmakY() * scale), pomakX, (int)(heightP - pomakY - (i) * barChart.getRazmakY() * scale));
	    	g.drawString((Integer.valueOf((int) (minValue + (barChart.getRazmakY() * (i))))).toString(), 
	    			pomakX - 6 - metrics.stringWidth((Integer.valueOf((int) (minValue + (barChart.getRazmakY() * (i))))).toString())
	    			, (int)(heightP - pomakY - (i) * barChart.getRazmakY() * scale + metrics.getHeight() / 4));
	    }
	    
	    //double angle = Math.atan2(heightP - pomakY, 5);
	    AffineTransform tx = g2d.getTransform();
	    tx.translate(widthP - 5, heightP - pomakY);
	    tx.rotate(-Math.PI / 2);
	    g2d.setTransform(tx);

	    Polygon arrowHead = new Polygon();
	    arrowHead.addPoint(0, 5);
	    arrowHead.addPoint(-5, -5);
	    arrowHead.addPoint(5, -5);
	    g2d.fill(arrowHead);
	    
	    g2d.setTransform(defaultAt);
	    
	    AffineTransform ty = g2d.getTransform();
	    ty.translate(pomakX, heightP - pomakY - (maxValue * scale) - 6);
	    ty.rotate(-Math.PI);
	    g2d.setTransform(ty);
	    
	    Polygon arrowHead2 = new Polygon();
	    arrowHead2.addPoint(0, 5);
	    arrowHead2.addPoint(-5, -5);
	    arrowHead2.addPoint(5, -5);
	    g2d.fill(arrowHead2);
		
	}
	
}
