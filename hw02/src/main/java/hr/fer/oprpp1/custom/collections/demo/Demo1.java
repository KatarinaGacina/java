package hr.fer.oprpp1.custom.collections.demo;

import java.util.NoSuchElementException;

//import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class Demo1 {
	public static void main(String[] args) {
		Collection col = new LinkedListIndexedCollection(); //stvoriPraznuKolekciju(); npr. new ArrayIndexedCollection();
		
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (NoSuchElementException e) {
			System.out.println("Nema više elemenata!");
		}
	}

}
