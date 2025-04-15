package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents symbol command.
 * @author Katarina Gacina
 *
 */
public class SymbolShellCommand implements ShellCommand {
	
	/**
	 * Function that represents symbol command logic and executes it.
	 * @param env shell environment
	 * @param arguments arguments that appear after command name, represented as one String
	 * @return ShellStatus CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments == null) {
			env.writeln("Missing arguments!");
			
			return ShellStatus.CONTINUE;
		}
		
		String[] symbolData = arguments.trim().split("\\s+");	
		
		if (symbolData.length > 2) {
			env.writeln("Invalid number of arguments!");
			
			return ShellStatus.CONTINUE;
			
		} else if (symbolData.length == 2) {
			
			if (symbolData[1].length() > 1) {
				env.writeln("Expected one character!");
				
				return ShellStatus.CONTINUE;
				
			}
			
			char oldSymbol;
			if (symbolData[0].equals("PROMPT")) {
				oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(symbolData[1].charAt(0));
				
			} else if (symbolData[0].equals("MORELINES")) {
				oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(symbolData[1].charAt(0));
				
			} else if (symbolData[0].equals("MULTILINE")) {
				oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(symbolData[1].charAt(0));;
				
			} else {
				env.writeln("Undefined symbol!");
				
				return ShellStatus.CONTINUE;
			}
			
			env.writeln("Symbol for " + symbolData[0] + " changed from '" + oldSymbol + "' to '" + symbolData[1] + "'");
			
		} else {
			
			char symbol;
			if (symbolData[0].equals("PROMPT")) {
				symbol = env.getPromptSymbol();
				
			} else if (symbolData[0].equals("MORELINES")) {
				symbol = env.getMorelinesSymbol();
				
			} else if (symbolData[0].equals("MULTILINE")) {
				symbol = env.getMultilineSymbol();
				
			} else {
				env.writeln("Undefined symbol!");
				
				return ShellStatus.CONTINUE;
			}
			
			env.writeln("Symbol for " + symbolData[0] + " is '" + symbol + "'");
			
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("Command symbol takes one or two arguments.");
		desc.add("The first argument is name of the shell symbol and is mandatory.");
		desc.add("Second argument is one character, which user wants to assign to symbol.");
		desc.add("If not provided, user gets message which character is used for wanted symbol.");
		desc.add("If provided, symbol character is changed for wanted symbol.");
		
		return desc;
	}

}
