package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that asks user to enter roots, and then it starts fractal viewer and displays the fractal.
 * It uses parallelization for drawing the fractal.
 * @author Katarina Gacina
 *
 */
public class NewtonPrallel {
	
	/**
	 * number of iterations
	 */
	private static final int ITER_NUM = 4096;
	
	/**
	 * treshold
	 */
	private static final double TRESHOLD = 0.001;
	
	/**
	 * default number of workers
	 */
	private static int workers = Runtime.getRuntime().availableProcessors();
	
	/**
	 * default number of tracks, i.e. jobs
	 */
	private static int tracks = 4 * workers;
	
	/**
	 * list of roots
	 */
	private static List<Complex> complexNumbers;
	
	/**
	 * Main program that asks user to enter roots, and then it starts fractal viewer and displays the fractal.
	 * If roots are in illegal format, program stops with the appropriate message.
	 * @param args --workers=N or -w N number of workers, --tracks=K or -t K number of tracks
	 * @throws IllegalArgumentException if args are in illegal format or multiple number of same arguments appear 
	 */
	public static void main(String[] args) {
		
		if (args.length != 0) {
			Integer w = null;
			Integer t = null;
			
			for (String arg : args) {
				if (arg.startsWith("-w") || arg.startsWith("--workers")) {
					if (w == null) {
						w = Integer.valueOf(arg.split("[=:\\s+]")[1]);
						
					} else {
						throw new IllegalArgumentException();
					}
					
				} else if (arg.startsWith("-t") || arg.startsWith("--tracks")) {
					if (t == null) {
						t = Integer.valueOf(arg.split("[=:\\s+]")[1]);
						
					} else {
						throw new IllegalArgumentException();
					}
					
				} else {
					throw new IllegalArgumentException();
				}
			}
			
			if (w != null && w > 0) {
				workers = w;
			} 
			
			if (t != null) {
				if (t < 1) throw new IllegalArgumentException();
				
				if (t <= 16) {
					tracks = t;
				}
			} 
		}
		
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
			
			FractalViewer.show(new MojProducer());
			
		} catch (IllegalStateException e) {
			System.out.println("Neispravne nultocke!");
		}
		
		scanner.close();
		
	}

	/**
	 * Class represents one job.
	 * @author Katarina Gacina
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			
			int offset = yMin * width;
			
			Complex[] complexArr = new Complex[complexNumbers.size()];
			ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, complexNumbers.toArray(complexArr));			
			ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
			
			for(int y = yMin; y <= yMax && !cancel.get(); y++) {
				
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
		}
	}
	
	/**
	 * Class implements IFractalProducer.
	 * @author Katarina Gacina
	 *
	 */
	public static class MojProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			
			int m = 16*16*16;
			short[] data = new short[width * height];
			
			final int brojTraka = tracks;  //number of tracks
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers]; //number of workers
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)m, requestNo);
		}
	}
	
}
