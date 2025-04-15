package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.UnaryOperator;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class that represents simple calculator.
 * @author Katarina Gacina
 *
 */
public class Calculator extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * calculator model
	 */
	private CalcModelImpl model;
	
	/**
	 * calculator stack
	 */
	private Stack<Double> stack;
	
	/**
	 * calculator display
	 */
	private JLabel display;
	
	/**
	 * indicates if certain calculator operators should be inverted
	 */
	private boolean inv;

	/**
	 * Constructor. Initializes Calculator window.
	 */
	public Calculator() {
		model = new CalcModelImpl();
		stack = new Stack<Double>();
		
		inv = false;
		
		setTitle("Java Calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Function for calculator initialization.
	 */
	private void initGUI() {
		UnaryOperatorStrategy unaryOperator = new UnaryOperatorStrategy() {
			@Override
			public void operation(UnaryOperator<Double> operator, UnaryOperator<Double> invOperator, boolean inv) {
				UnaryOperator<Double> result; 
				
				if (inv) {
					result = invOperator;
				} else {
					result = operator;
				}
					
				model.setValue(result.apply(model.getValue()));
			}
			
		};
		
		BinaryStrategy binaryOperator = new BinaryStrategy() {

			@Override
			public DoubleBinaryOperator operator(DoubleBinaryOperator operator, DoubleBinaryOperator invOperator,
					boolean inv) {
				if (inv) {
					return invOperator;
				} else {
					return operator;
				}
			}
			
		};
		
		Container cp = getContentPane();
		
		cp.setLayout(new CalcLayout(4));
		
		display();
		cp.add(display, new RCPosition(1,1));
		
		JButton is = new JButton("=");
		is.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.clearActiveOperand();
			}
		});
		cp.add(svojstva(is), new RCPosition(1,6));
		
		JButton clr = new JButton("clr");
		clr.addActionListener(e -> {
			model.clear();
		});
		cp.add(svojstva(clr), new RCPosition(1,7));
		
		JButton x_inv = new JButton("1/x");
		x_inv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setValue(1.0 / model.getValue());
			}
		});
		cp.add(svojstva(x_inv), new RCPosition(2,1));
		
		JButton sin = new JButton("sin");
		sin.addActionListener(e -> {
			unaryOperator.operation(a -> Math.sin(a), a -> Math.asin(a), inv);
		});
		cp.add(svojstva(sin), new RCPosition(2,2));
		
		cp.add(znamenke("7"), new RCPosition(2,3));
		cp.add(znamenke("8"), new RCPosition(2,4));
		cp.add(znamenke("9"), new RCPosition(2,5));
		
		cp.add(operatori("/", (l, r) -> l / r), new RCPosition(2,6));
		
		JButton reset = new JButton("reset");
		reset.addActionListener(e -> {
			model.clearAll();
		});
		cp.add(svojstva(reset), new RCPosition(2,7));
		
		JButton log = new JButton("log");
		log.addActionListener(e -> {
			unaryOperator.operation(a -> Math.log10(a), a -> Math.pow(10, a), inv);
		});
		cp.add(svojstva(log), new RCPosition(3,1));
		
		JButton cos = new JButton("cos");
		cos.addActionListener(e -> {
			unaryOperator.operation(a -> Math.cos(a), a -> Math.acos(a), inv);
		});
		cp.add(svojstva(cos), new RCPosition(3,2));
		
		cp.add(znamenke("4"), new RCPosition(3,3));
		cp.add(znamenke("5"), new RCPosition(3,4));
		cp.add(znamenke("6"), new RCPosition(3,5));
		
		JButton puta = new JButton("*");
		puta.addActionListener(e -> {
			
		});
		cp.add(operatori("*", (l, r) -> l * r), new RCPosition(3,6));
		
		JButton push = new JButton("push");
		push.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stack.push(model.getValue());
			}
		});
		cp.add(svojstva(push), new RCPosition(3,7));
		
		JButton ln = new JButton("ln");
		ln.addActionListener(e -> {
			unaryOperator.operation(a -> Math.log(a), a -> Math.pow(Math.E, a), inv);
		});
		cp.add(svojstva(ln), new RCPosition(4,1));
		
		JButton tan = new JButton("tan");
		tan.addActionListener(e -> {
			unaryOperator.operation(a -> Math.tan(a), a -> Math.atan(a), inv);
		});
		cp.add(svojstva(tan), new RCPosition(4,2));
		
		cp.add(znamenke("1"), new RCPosition(4,3));
		cp.add(znamenke("2"), new RCPosition(4,4));
		cp.add(znamenke("3"), new RCPosition(4,5));
		
		cp.add(operatori("-", (l, r) -> l - r), new RCPosition(4,6));
		
		JButton pop = new JButton("pop");
		pop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stack.isEmpty()) {
					JOptionPane.showMessageDialog(pop, "No data on stack.", "WARNING", JOptionPane.WARNING_MESSAGE);
				} else {
					model.setValue(stack.pop());
				}
           
			}
		});
		cp.add(svojstva(pop), new RCPosition(4,7));
		
		DoubleBinaryOperator x_nOperator = binaryOperator.operator((l, r) -> Math.pow(l, r), (l, r) -> Math.pow(l, 1.0 / r), inv);
		JButton x_n = operatori("x^n", x_nOperator);
		cp.add(x_n, new RCPosition(5,1));
		
		JButton ctg = new JButton("ctg");
		ctg.addActionListener(e -> {
			unaryOperator.operation(a -> 1.0 / Math.tan(a), a -> (Math.PI / 2.0) - Math.atan(a), inv);
		});
		cp.add(svojstva(ctg), new RCPosition(5,2));
		
		cp.add(znamenke("0"), new RCPosition(5,3));
		
		JButton swap = new JButton("+/-");
		swap.addActionListener(e -> {
			model.swapSign();
		});
		cp.add(svojstva(swap), new RCPosition(5,4));
		
		JButton dot = new JButton(".");
		dot.addActionListener(e -> {
			model.insertDecimalPoint();
		});
		cp.add(svojstva(dot), new RCPosition(5,5));
		
		cp.add(operatori("+", (l, r) -> l + r), new RCPosition(5,6));
		
		JCheckBox invBox = new JCheckBox("Inv");
		invBox.addActionListener(e -> {
			inv = !inv;
			
			if (inv) {
				sin.setText("arcsin");
				cos.setText("arccos");
				tan.setText("arctan");
				ctg.setText("arcctg");
				log.setText("10^x");
				ln.setText("e^x");
				x_n.setText("x^(1/n)");
			} else {
				sin.setText("sin");
				cos.setText("cos");
				tan.setText("tan");
				ctg.setText("ctg");
				log.setText("log");
				ln.setText("ln");
				x_n.setText("x^n");
			}
		});
		cp.add(invBox, new RCPosition(5,7));
		
		model.addCalcValueListener(new CalcValueListener() {

			@Override
			public void valueChanged(CalcModel model) {
				if (!model.toString().equals(display.getText())) {
					display.setText(model.toString());
				}
			}
			
		});
	}

	/**
	 * Function for display initialization.
	 */
	private void display() {
		display = new JLabel("0");
		display.setBackground(Color.YELLOW);
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setFont(display.getFont().deriveFont(30f));
		display.setBorder(new LineBorder(Color.BLACK));
		display.setOpaque(true);
	}
	
	/**
	 * Function for creating buttons with digits.
	 * @param text digit
	 * @return JButton
	 */
	private JButton znamenke(String text) {
		JButton l = new JButton(text);
		
		l.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				model.insertDigit(Integer.valueOf(text));
			} 
		});
		
		l.setBackground(Color.LIGHT_GRAY);
		l.setFont(l.getFont().deriveFont(30f));
		l.setOpaque(true);

		return l;
	}

	/**
	 * Function for creating buttons with double binary operators.
	 * @param text symbol for double binary operator
	 * @param operator DoubleBinaryOperator
	 * @return JButton
	 */
	private JButton operatori(String text, DoubleBinaryOperator operator) {
		JButton l = new JButton(text);
		
		l = svojstva(l);
		
		l.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (model.isActiveOperandSet()) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				}
				model.setPendingBinaryOperation(operator);
				model.setActiveOperand(model.getValue());
			} 
			
		});
		
		return l;
	} 
	
	/**
	 * Function that sets bacis visual properties on button.
	 * @param l JButton
	 * @return visually changed JButton
	 */
	private JButton svojstva(JButton l) {
		l.setBackground(Color.LIGHT_GRAY);
		l.setOpaque(true);

		return l;
	}

	/**
	 * Start the calculator.
	 * @param args does not use them
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}

}
