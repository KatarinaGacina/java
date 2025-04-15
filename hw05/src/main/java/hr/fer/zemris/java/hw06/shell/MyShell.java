package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Class represents a command-line program. It implements interface Environment.
 * @author Katarina Gacina
 *
 */
public class MyShell implements Environment{
	
	/**
	 * multiline character
	 */
	public static Character MULTILINES='|';
	
	/**
	 * morelines character
	 */
	public static Character MORELINES='\\';
	
	/**
	 * prompt symbol
	 */
	public static Character PROMPTSYMBOL='>';
	
	/**
	 * map containing all provided commands, pairs of command names and command instances
	 */
	private Map<String, ShellCommand> commandsMap;
	
	/**
	 * scanner for reading from console
	 */
	private Scanner scanner;
	
	public MyShell() {
		SortedMap<String, ShellCommand> commandMap = new TreeMap<>();
		
		commandMap.put("exit", new ExitShellCommand());
		commandMap.put("ls", new LsShellCommand());
		commandMap.put("charsets", new CharsetsShellCommand());
		commandMap.put("cat", new CatShellCommand());
		commandMap.put("symbol", new SymbolShellCommand());
		commandMap.put("tree", new TreeShellCommand());
		commandMap.put("copy", new CopyShellCommand());
		commandMap.put("mkdir", new MkdirShellCommand());
		commandMap.put("hexdump", new HexdumpShellCommand());
		commandMap.put("help", new HelpShellCommand());
		
		commandsMap = Collections.unmodifiableSortedMap(commandMap);
		
		scanner = new Scanner(System.in);
	}

	@Override
	public String readLine() throws ShellIOException {
		if (scanner.hasNextLine()) return scanner.nextLine();
		
		throw new ShellIOException();
	}

	@Override
	public void write(String text) throws ShellIOException {
		if (text == null) throw new ShellIOException();
		
		System.out.print(text);
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		if (text == null) throw new ShellIOException();
		
		System.out.println(text);
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return (SortedMap<String, ShellCommand>) commandsMap;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINES;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINES=symbol;
		
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL=symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINES;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINES=symbol;
		
	}
	
	/**
	 * Represents a command line program. It reads line by line and executes known shell commands.
	 * Program terminates when command exit occurs.
	 * @param args it does not use this arguments
	 * @throws ShellIOException when problem with reading or writing files occurs
	 */
	public static void main(String[] args) throws ShellIOException{
		MyShell myshell = new MyShell();
		
		SortedMap<String, ShellCommand> commands = myshell.commands();
		
		System.out.println("Welcome to MyShell v 1.0");
		
		ShellStatus status = ShellStatus.CONTINUE;
		
		Scanner scanner = myshell.scanner;
		
		do {
			myshell.write(myshell.getPromptSymbol().toString());
			myshell.write(" ");
			
			String line = scanner.nextLine();
			
			String splitData[] = line.trim().split("\\s", 2);
			
			if (splitData.length != 0) {
				String commandName = splitData[0];
				
				StringBuilder arg = new StringBuilder();;
				if (splitData.length > 1) {
					arg.append(splitData[1]);
					
					while (arg.charAt(arg.length() - 1) == myshell.getMorelinesSymbol()) {
						arg.deleteCharAt(arg.length() - 1);
						
						myshell.write(myshell.getMultilineSymbol().toString());
						myshell.write(" ");
						
						arg.append(myshell.readLine());
					}
				} 
				
				String arguments = null;
				if (arg.length() != 0) {
					arguments = arg.toString();
				}
				
				ShellCommand command = commands.get(commandName);
				
				if (command != null) status = command.executeCommand(myshell, arguments);
			}
			
		} while (status != ShellStatus.TERMINATE);
		
		scanner.close();
		
	}
	
	
}
