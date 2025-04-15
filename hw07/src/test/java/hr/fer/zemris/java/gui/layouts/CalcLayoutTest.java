package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
	
	@Test
	public void testPrefferedSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		
		JLabel l1 = new JLabel(""); 
		l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); 
		l2.setPreferredSize(new Dimension(20,15));
		
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		
		Dimension dim = p.getPreferredSize();
		
		assertEquals(new Dimension(152, 158), dim);
	}

	@Test
	public void testPrefferedSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		
		JLabel l1 = new JLabel(""); 
		l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); 
		l2.setPreferredSize(new Dimension(16,30));
		
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		
		Dimension dim = p.getPreferredSize();
		
		assertEquals(new Dimension(152, 158), dim);
	}
	
	@Test
	public void testCalcLayoutException1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); 
		
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(0,1)));
	}
	
	@Test
	public void testCalcLayoutException2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); 
		
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(6,1)));
	}
	
	@Test
	public void testCalcLayoutException3() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); 
		
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1,0)));
	}
	
	@Test
	public void testCalcLayoutException4() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); 
		
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1,8)));
	}
	
	@Test
	public void testCalcLayoutException5() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); 
		
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1,5)));
	}
	
	@Test
	public void testCalcLayoutException6() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); 
		p.add(l1, new RCPosition(1,1));
		JLabel l2 = new JLabel(""); 
		
		assertThrows(CalcLayoutException.class, () -> p.add(l2, new RCPosition(1,1)));
	}
}
