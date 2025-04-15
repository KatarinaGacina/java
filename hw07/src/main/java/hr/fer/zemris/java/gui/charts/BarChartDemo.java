package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Class that represents BarChart window.
 * @author Katarina Gacina
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public BarChartDemo() {
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocation(0, 0);
		setSize(350, 300);
	}
	
	/**
	 * Function starts the application. Takes argument as file path, reads first 6 lines of file and according to them
	 * creates and displays file path and bar chart.
	 * @param args[0] file path
	 */
	public static void main(String[] args) {
		
		if (args.length == 0) throw new IllegalStateException();
		
		Path graph = Paths.get(args[0]).toAbsolutePath();

		if (!Files.exists(graph) || Files.isDirectory(graph)) {
			System.out.println("File does not exist or is a directory.");
			return;
		}
		
		String[] graph_prop = new String[6];
		
		try (BufferedReader input = new BufferedReader(new FileReader(args[0]))) {
			
			for (int i = 0; i < 6; i++) {
				String line = input.readLine();
				
				if (line == null) {
					System.out.println("Wrong file content.");
					return;
				}
				
				graph_prop[i] = line;
			}
			
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return;
		}
		
		String[] points = graph_prop[2].split("\\s+");
		List<XYValue> listValues = new ArrayList<>();
		for (String v : points) {
			String[] xy = v.split(",");
			
			listValues.add(new XYValue(Integer.valueOf(xy[0]), Integer.valueOf(xy[1])));
		}
		
		/*BarChart model = new BarChart(Arrays.asList(
				new XYValue(1,8), new XYValue(2,20), new XYValue(3,22),
				new XYValue(4,11), new XYValue(5,4)
				),
				"Number of people in the car",
				"Frequency",
				0, // y-os kreće od 0
				22, // y-os ide do 22
				2
			);*/
		
		BarChart model = new BarChart(listValues,
				graph_prop[0],
				graph_prop[1],
				Integer.valueOf(graph_prop[3]), // y-os kreće od 0
				Integer.valueOf(graph_prop[4]), // y-os ide do 22
				Integer.valueOf(graph_prop[5])
			);
		
		BarChartComponent component = new BarChartComponent(model);
		
		BarChartDemo demo = new BarChartDemo();
		demo.setLayout(new BorderLayout());
		
		JLabel title = new JLabel(args[0]);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		demo.add(BorderLayout.PAGE_START, title);
		demo.add(BorderLayout.CENTER, component);
		
		demo.setVisible(true);
		
	}
	
}
