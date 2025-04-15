package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for retrieving arguments. 
 * @author Katarina Gacina 
 *
 */
public class ReadArg {
	
	/**
	 * Function returns list of arguments from the given argument string.
	 * It parses through string and splits arguments. 
	 * It supports strings in double-quote notation. Inside of these strings escaping mechanism for " and \ is allowed.
	 * Furthermore, they can contain white spaces while other arguments are split by them.
	 * @param arguments 
	 * @return List<String> list of arguments
	 * @throws IllegalArgumentException if there is more characters and not even one space character is present after the ending double-quote,
	 * which indicates invalid path
	 */
	public static List<String> getArguments(String arguments) {
		List<String> argument = new ArrayList<>();
		
		boolean readString = false;
		StringBuilder arg = new StringBuilder();
		
		int currentChar = 0;
		char[] argumentsData = arguments.toCharArray();
		
		while (currentChar < argumentsData.length) {
			if (argumentsData[currentChar] == '"') {
				if (readString) {
					if (Character.isWhitespace(argumentsData[currentChar++]) && currentChar != argumentsData.length) {
						throw new IllegalArgumentException("Invalid path!");
					}
					
					currentChar--;
					
					readString = false;
					argument.add(arg.toString());
					arg.setLength(0);
					
				} else {
					readString = true;
					
				}
				
			} else if (readString && argumentsData[currentChar] == ' ') {
					arg.append(' ');
					
			} else if (readString && argumentsData[currentChar] == '\\') {
				if (argumentsData[currentChar++] == '"') {
					arg.append('"');
					
				} else if (argumentsData[currentChar] == '\\') {
					arg.append('\\');
					
				} else {
					currentChar--;
					arg.append(argumentsData[currentChar]);
					
				}
				
			} else if (argumentsData[currentChar] == ' ') {
				if (arg.length() != 0) {
					argument.add(arg.toString());
					arg.setLength(0);
				}
					
			} else {
				arg.append(argumentsData[currentChar]);
			}
			
			currentChar += 1;
		}
		
		if (arg.length() != 0) {
			argument.add(arg.toString());
			arg.setLength(0);
		}
		
		return argument;
	}
}
