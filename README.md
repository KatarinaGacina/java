# Selected Topics in Software Development 1

These laboratory assigements were completed as part of the Selected Topics in Software Development 1 course at college.

The laboratory assigements are implemented in **Java**.
Code is commented and has Junit tests and demo classes.


## First laboratory assigement: 
Implementation of resizable array-backed collection of objects - ArrayIndexedCollection  
Implementation of linked list-backed collection of objects - LinkedListIndexedCollection

Stack implementation as instance of an ArrayIndexedCollection - ObjectStack  
Demonstration by running: 
`java -cp . hr.fer.oprpp1.custom.collections.demo.StackDemo "8 -2 / -1 *"`

## Second laboratory assigement: 
Improvements of previously implemented collections. 

Development of a simple language processor.

## Third laboratory assigement: 
Parametrization of previously implemented collections.  

Dictionary implementation - Dictionary  
Implementation of a hash table with open addressing that allows storage of key-value pairs - SimpleHashtable

## Fourth laboratory assigement: 
A simple database emulator implementation.  
Example of usage:  
`java hr.fer.oprpp1.hwo4.db.StudentDB`  
`> query jmbag = "0000000003"`

## Fifth laboratory assigement: 
Implementation of functionality to encrypt and decrypt files using the AES algorithm with a 128-bit key, as well as to compute and verify SHA-256 file digests.  
Example of usage:  
`java hr.fer.oprpp1.hw05.crypto.Crypto checksha file.bin`  
`java hr.fer.oprpp1.hw05.crypto.Crypto encrypt file.pdf file.crypted.pdf`  
`java hr.fer.oprpp1.hw05.crypto.Crypto decrypt file.crypted.pdf fileorig.pdf`  

Implementation of command-line program.  
Example of usage:  
`java hr.fer.zemris.java.hw06.shell.MyShell`

## Sixth laboratory assigement: 
Implemented a lightweight mathematical library with support for operations on complex numbers and polynomials.  

Generation of fractal images using the Newton-Raphson iteration applied to complex polynomials.  
Example of usage:  
`java hr.fer.zemris.java.fractals.Newton`

## Seventh laboratory assigement: 
Implementation of layout managers. 
Bar chart implementation.  

Calculator implementation.  

## Eighth laboratory assigement: 
Implementation of text file editor.  
Example of usage:  
`java hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP`
