package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class SimpleHashtableDemo { 
	
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		
		// fill data:
		examMarks.put("Ivana", 2); 
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		
		System.out.println(examMarks.remove("Ivana"));
		
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		System.out.println(examMarks.toString());
		
		System.out.println(Arrays.toString(examMarks.toArray()));
	}  
	
}
