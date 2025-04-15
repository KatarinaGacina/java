package hr.fer.zemris.math;

/**
 * Class represents complex rooted polynomial.
 * @author Katarina Gacina
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * coefficients of complex rooted polynomial
	 */
	private Complex[] koeficijenti;
	
	/**
	 * Constructor
	 * @param constant complex constant
	 * @param roots complex roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		
		koeficijenti = new Complex[roots.length + 1];
		
		koeficijenti[0] = constant;
		
		int i = 1;
		for (Complex r : roots) {
			koeficijenti[i] = r;
			i++;
		}
		
	}
	
	/**
	 * Function computes polynomial value at given point z.
	 * @param z given point
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		
		Complex c = koeficijenti[0];
		
		for (int i = 1; i < koeficijenti.length; i++) {
			c = c.multiply(koeficijenti[i]);
		}
		
		return c;
	}
	
	/**
	 * Function converts this representation to ComplexPolynomial type.
	 * @return ComplexPolynomial type representation of this
	 */
	public ComplexPolynomial toComplexPolynom() {
		
		ComplexPolynomial c = new ComplexPolynomial(koeficijenti[0]);
		
		for (int k = 1; k < koeficijenti.length; k++) {
			c = c.multiply(new ComplexPolynomial(koeficijenti[k].negate(), new Complex(1, 0)));
		}
		
		return c;
	}
	
	/**
	 * Function finds index of closest root for given complex number z that is within treshold;
	 * if there is no such root, returns -1.
	 * First root has index 0, second index 1, etc.
	 * @param z given complex number
	 * @param treshold given treshold
	 * @return index of the closest root for given complex number z that is within treshold or if there is no such root -1
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
	
		double distance = koeficijenti[0].sub(z).module();
		
		int index;
		if (distance <= treshold) {
			index = 0;
		} else {
			index = -1;
		}
		
		int i = 1;
		while (i < koeficijenti.length) {
			
			double d = koeficijenti[i].sub(z).module();
			
			if (d <= distance && d <= treshold) {
				distance = d;
				index = i;
			}
			
			i++;
		}
		
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder representation = new StringBuilder();
		
		representation.append("(");
		representation.append(koeficijenti[0].getRe());
		if (koeficijenti[0].getIm() < 0) {
			representation.append("-i");
		} else {
			representation.append("+i");
		}
		representation.append(koeficijenti[0].getIm());
		representation.append(")");
		
		for (int i = 1; i < koeficijenti.length; i++) {
			representation.append("*(z-");
			representation.append("(");
			representation.append(koeficijenti[i].getRe());
			if (koeficijenti[i].getIm() < 0) {
				representation.append("-i");
				representation.append(Math.abs(koeficijenti[i].getIm()));
			} else {
				representation.append("+i");
				representation.append(koeficijenti[i].getIm());
			}
			representation.append("))");
		}
		
		return representation.toString();
	}

}
