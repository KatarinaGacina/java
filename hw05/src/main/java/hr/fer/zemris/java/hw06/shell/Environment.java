package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface that represents shell environment.
 * @author Katarina Gacina
 *
 */
public interface Environment {
	
	/**
	 * Reads one line from user.
	 * @return String line
	 * @throws ShellIOException if reading fails
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes given text to user.
	 * @param text wanted output
	 * @throws ShellIOException if writing fails
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes given text plus newline character to user.
	 * @param text wanted output
	 * @throws ShellIOException if writing fails
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Function returns a map containing all provided commands, pairs of command names and command instances
	 * @return SortedMap<String, ShellCommand> commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Function returns a multiline symbol.
	 * @return Character multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Function sets a multiline symbol
	 * @param symbol wanted multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Function returns a prompt symbol.
	 * @return Character prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Function sets a prompt symbol
	 * @param symbol wanted prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Function returns a morelines symbol.
	 * @return Character morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Function sets a morelines symbol
	 * @param symbol wanted morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
