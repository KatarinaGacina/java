package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class represents charsets command.
 * @author Katarina Gacina
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	
	/**
	 * Function that represents charsets command logic and executes it.
	 * @param env shell environment
	 * @param arguments arguments that appear after command name, represented as one String
	 * @return ShellStatus CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments != null) {
			env.writeln("Command charsets accepts no arguments!");
			
			return ShellStatus.CONTINUE;
		}
		
		for (Charset c : Charset.availableCharsets().values()) {
			env.writeln(c.displayName());;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command charsets takes no arguments.");
		description.add("It lists names of supported charsets for Java platform.");
		description.add("A single charset name is written per line.");
		
		return description;
	}

}
