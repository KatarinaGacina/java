package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class that represents window displaying list of prim numbers. On each button click new prim number is added.
 * @author Katarina Gacina
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Prim brojevi");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Function initializes window. 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JPanel bottomPanel = new JPanel(new GridLayout());

		JButton dodaj = new JButton("sljedeći");
		bottomPanel.add(dodaj);
		
		dodaj.addActionListener(e -> {
			model.next();
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Function displays PrimDemo window.
	 * @param args not used
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}

}
