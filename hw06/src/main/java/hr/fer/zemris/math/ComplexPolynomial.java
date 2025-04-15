package hr.fer.zemris.math;

/**
 * Class represents complex polynomial.
 * @author Katarina Gacina
 *
 */
public class ComplexPolynomial {
	
	/**
	 * factors of complex polynomial
	 */
	private Complex[] faktori;
	
	/**
	 * Constructor.
	 * @param factors complex polynomial factors
	 */
	public ComplexPolynomial(Complex... factors) {
		faktori = new Complex[factors.length];
		
		int i = 0;
		
		for (Complex f : factors) {
			faktori[i] = f;
			i++;
		}
	}
	
	/**
	 * Function returns polynomial order.
	 * @return order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 */
	public short order() {
		return (short) (faktori.length);
	}
	
	/**
	 * Function computes a new polynomial this*p.
	 * @param p other ComplexPolynomial
	 * @return a new polynomial this*p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] prod = new Complex[faktori.length + p.faktori.length - 1];
		  
        for (int i = 0; i < faktori.length + p.faktori.length - 1; i++) {
            prod[i] = Complex.ZERO;
        }
  
        for (int i = 0; i < faktori.length; i++) {
            for (int j = 0; j < p.faktori.length; j++) {
                prod[i + j] = prod[i + j].add(faktori[i].multiply(p.faktori[j]));
            }
        }
        
        return new ComplexPolynomial(prod);
	}
	
	/**
	 * Function computes first derivative of this polynomial; 
	 * for example, for (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] der = new Complex[faktori.length - 1];
		
		for (int i = 1; i < faktori.length; i++) {
			der[i - 1] = faktori[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(der);
	}
	
	/**
	 * Function computes polynomial value at given point z.
	 * @param z given point
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex c = faktori[0];
		
		for (int i = 1; i < faktori.length; i++) {
			c = c.add(faktori[i].multiply(z.power(i)));
		}
		
		return c;
	}
	
	@Override
	public String toString() {
		StringBuilder representation = new StringBuilder();
		
		for (int i = faktori.length - 1; i >= 0; i--) {
			representation.append("(");
			representation.append(faktori[i].getRe());
			if (faktori[i].getIm() < 0) {
				representation.append("-i");
			} else {
				representation.append("+i");
			}
			representation.append(faktori[i].getIm());
			representation.append(")*z^");
			representation.append(i);
			representation.append("+");
		}

		representation.delete(representation.length() - 5, representation.length());
		
		return representation.toString();
	}
}
