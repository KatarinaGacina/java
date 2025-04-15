package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that asks user to enter roots, and then it starts fractal viewer and displays the fractal respectively.
 * @author Katarina Gacina
 *
 */
public class Newton {
	
	/**
	 * number of iterations
	 */
	private static final int ITER_NUM = 4096;
	
	/**
	 * treshold
	 */
	private static final double TRESHOLD = 0.001;
	
	/**
	 * list of roots
	 */
	private static List<Complex> complexNumbers;
	
	/**
	 * Main program that asks user to enter roots, and then it starts fractal viewer and displays the fractal.
	 * If roots are in illegal format, program stops with the appropriate message.
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Scanner scanner = new Scanner(System.in);
		
		NewtonParser parser = new NewtonParser();
		complexNumbers = new ArrayList<>();
		
		try {
			do {
				System.out.print("> ");
				
				String line = scanner.nextLine().trim();
				
				if (line.equals("done")) {
					break;
				}
				
				Complex number = parser.startParsing(line);

				complexNumbers.add(number);
				
			} while (true);
			
			FractalViewer.show(new NewtonProducer());
			
		} catch (IllegalStateException e) {
			System.out.println("Neispravne nultocke!");
		}
		
		scanner.close();
	}
	
	/**
	 * Implementation of the IFractalProducer.
	 * @author Katarina Gacina
	 *
	 */
	public static class NewtonProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			
			int offset = 0;
			short[] data = new short[width * height];
			
			Complex[] complexArr = new Complex[complexNumbers.size()];
			ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, complexNumbers.toArray(complexArr));			
			ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
			
			for(int y = 0; y < height; y++) {
				
				if(cancel.get()) break;
				
				for(int x = 0; x < width; x++) {
					
					double cRe = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cIm = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;  
					Complex c = new Complex(cRe, cIm);
					
					Complex zn = c;
					
					double module = 0;
					int iter = 0;
					
					do {
						Complex numerator = polynomial.apply(zn);
						ComplexPolynomial derived = polynomial.derive();
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						
						iter++;

					} while(iter < ITER_NUM && module > TRESHOLD);
					
					int index = rootedPolynomial.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short) (index + 1);
				}
			}
				
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
	}

}
