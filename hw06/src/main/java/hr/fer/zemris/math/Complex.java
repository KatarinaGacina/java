package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents complex number.
 * @author Katarina Gacina
 *
 */
public class Complex {
	
	/**
	 * real part of complex number
	 */
	private final double re;
	
	/**
	 * imaginary part of complex number
	 */
	private final double im;
	
	/**
	 * zero
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * one
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * negative one
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * imaginary unit
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * negative imaginary unit
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Default constructor.
	 * Creates instance of zero.
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}
	
	/**
	 * Constructor for complex number.
	 * @param re real part of complex number
	 * @param im imaginary part of complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * @return module of complex number
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * Function mulplies this complex number with the given complex number.
	 * @param c complex number
	 * @return this*c
	 */
	public Complex multiply(Complex c) {
		double reNew = re * c.re - im * c.im;
		double imNew = re * c.im + c.re * im;

		return new Complex(reNew, imNew);
	}
	
	/**
	 * Function divides this complex number with the given complex number.
	 * @param c complex number
	 * @return this/c
	 */
	public Complex divide(Complex c) {
		double pom = Math.pow(c.re, 2) + Math.pow(c.im, 2); 
		
		double reNew = (re * c.re + im * c.im) / pom;
		double imNew = (im * c.re - re * c.im) / pom;

		return new Complex(reNew, imNew);
	}
	
	/**
	 * Function adds this complex number with the given complex number.
	 * @param c complex number
	 * @return this+c
	 */
	public Complex add(Complex c) {
		double reNew = re + c.re;
		double imNew = im + c.im;

		return new Complex(reNew, imNew);
	}
	
	/**
	 * Function subtracts this complex number with the given complex number.
	 * @param c complex number
	 * @return this-c
	 */
	public Complex sub(Complex c) {
		double reNew = re - c.re;
		double imNew = im - c.im;

		return new Complex(reNew, imNew);
	}
	
	/**
	 * Function returns negative representation of this complex number.	 * @param c complex number
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Function returns power of this complex number.
	 * @param n non-negative integer, power
	 * @return this^n
	 */
	public Complex power(int n) {		
		return new Complex(Math.pow(this.module(), n) * Math.cos(n*this.kut()), 
				Math.pow(this.module(), n) * Math.sin(n*this.kut()));
	}
	
	/**
	 * Function returns roots of this complex number.
	 * @param n positive integer, n-th root
	 * @return List<Complex> n-th root of this
	 */
	public List<Complex> root(int n) {
		List<Complex> lista = new ArrayList<>();
		
		double pom = Math.sqrt(this.module());
		
		for (int k = 0; k < n; k++) {
			double kut = (this.kut() / n) + ((2 * k * Math.PI) / n); 
			
			lista.add(new Complex(pom * Math.cos(kut), pom * Math.sin(kut)));
		}
		
		return lista;
	}
	
	@Override
	public String toString() {
		return re + " + " + im + "i";
	}
	
	/**
	 * Function returns the angle which the number forms with the positive Real axis.
	 * @return angle 
	 */
	private double kut() {
        return Math.atan2(im, re);
    }

	/**
	 * Function is a getter for real part of complex number.
	 * @return real part of complex number
	 */
	public double getRe() {
		return this.re;
	}
	
	/**
	 * Function is a getter for imaginary part of complex number.
	 * @return imaginary part of complex number
	 */
	public double getIm() {
		return this.im;
	}
	
}
