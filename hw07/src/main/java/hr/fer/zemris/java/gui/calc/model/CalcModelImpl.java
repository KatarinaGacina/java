package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.function.DoubleBinaryOperator;

/**
 * Class represents simple implementation for CalcModel.
 * @author Katarina Gacina
 *
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * calculator editable status
	 */
	private boolean isEditable;
	
	/**
	 * number negative status
	 */
	private boolean isNegative;
	
	/**
	 * string number representation
	 */
	private String znamenke;
	
	/**
	 * number
	 */
	private double broj;
	
	/**
	 * display freeze value
	 */
	private String freezeValue;
	
	/**
	 * active operand
	 */
	private Double activeOperand;
	
	/**
	 * pending double binary operation
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * list of CalcValueListeners
	 */
	private ArrayList<CalcValueListener> lista = new ArrayList<>();

	/**
	 * Constructor. Initializes the calculator model.
	 */
	public CalcModelImpl() {
		this.isEditable = true;
		this.isNegative = false;
		this.znamenke = "";
		this.broj = 0;
		this.freezeValue = null;
		
		activeOperand = null;
		pendingOperation = null;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (l == null) throw new NullPointerException();
		
		this.lista.add(l);
		
		for (CalcValueListener listener : lista) {
			listener.valueChanged(this);
		}
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l == null) throw new NullPointerException();
		
		if (this.lista.contains(l)) this.lista.remove(l);
	}

	@Override
	public double getValue() {
		
		if (isNegative) {
			return -broj;
		} else {
			return broj;
		}
		
	}

	@Override
	public void setValue(double value) {
		if (value < 0) {
			broj = -value;
			isNegative = true;
		} else {
			broj = value;
			isNegative = false;
		}
		
		if (Double.valueOf(value).isNaN()) {
			znamenke = "NaN";
		} else if ((Double.valueOf(value)).equals(Double.NEGATIVE_INFINITY)) {
			znamenke = "-Infinity";
		} else if ((Double.valueOf(value)).equals(Double.POSITIVE_INFINITY)) {
			znamenke = "Infinity";
		} else {
			znamenke = Double.valueOf(broj).toString();
		}
		
		isEditable = false;
		freezeValue = null;
		
		for (CalcValueListener listener : lista) {
			listener.valueChanged(this);
		}
		
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		broj = 0;
		znamenke = "";
		
		this.isEditable = true;
		this.isNegative = false;
		
		freezeValue = null;
		
		for (CalcValueListener listener : lista) {
			listener.valueChanged(this);
		}
	}

	@Override
	public void clearAll() {
		this.isEditable = true;
		this.isNegative = false;
		this.znamenke = "";
		this.broj = 0;
		this.freezeValue = null;
		
		activeOperand = null;
		pendingOperation = null;
		
		for (CalcValueListener listener : lista) {
			listener.valueChanged(this);
		}
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) throw new CalculatorInputException();

		isNegative = !isNegative;
		
		freezeValue = null;
		
		for (CalcValueListener listener : lista) {
			listener.valueChanged(this);
		}
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable || znamenke.length() == 0 || znamenke.indexOf('.') != -1) throw new CalculatorInputException();
		
		znamenke = znamenke + ".";
		
		freezeValue = null;
		
		for (CalcValueListener listener : lista) {
			listener.valueChanged(this);
		}
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) throw new CalculatorInputException();
		
		if (digit == 0 && znamenke.equals("0")) {
			return;
		}
		
		try {
			String noviPrikaz;
			if (znamenke.equals("0")) {
				noviPrikaz = Integer.valueOf(digit).toString();
			} else {
				noviPrikaz = znamenke + Integer.valueOf(digit).toString();
			}
			double noviBroj = Double.parseDouble(noviPrikaz);
			
			if (Double.isInfinite(noviBroj)) throw new CalculatorInputException();
			
			znamenke = noviPrikaz;
			broj = noviBroj;
			
		} catch (NumberFormatException e) {
			throw new CalculatorInputException();
		}
		
		freezeValue = null;
		
		for (CalcValueListener listener : lista) {
			listener.valueChanged(this);
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		if (activeOperand == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand == null) throw new IllegalStateException();
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		
		/*if (isNegative) {
			freezeValue = "-" + znamenke;
		} else {
			freezeValue = znamenke;
		}*/
		freezeValue = null;
		
		broj = 0;
		znamenke = "";
		
		isEditable = true;
		isNegative = false;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		if (freezeValue != null) throw new CalculatorInputException();
		
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if (freezeValue != null) throw new CalculatorInputException();
		
		pendingOperation = op;
		
	}
	
	@Override
	public String toString() {
		if (freezeValue != null) {
			return freezeValue;
			
		} else if (znamenke.equals("")) {
			if (isNegative) {
				return "-0";
			} else {
				return "0";
			}
			
		} else if (znamenke.contains("n")) {
			return znamenke;
			
		} else {
			if (isNegative) {
				return "-" + znamenke;
			} else {
				return znamenke;
			}
		}
	}

}
