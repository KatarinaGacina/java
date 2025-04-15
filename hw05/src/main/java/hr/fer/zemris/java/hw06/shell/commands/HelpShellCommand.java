package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {
	
	/**
	 * Function that represents help command logic and executes it.
	 * @param env shell environment
	 * @param arguments arguments that appear after command name, represented as one String
	 * @return ShellStatus CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments == null || arguments.split("\\s+").length != 1) {
			env.writeln("Invalid number of arguments!");
			
			return ShellStatus.CONTINUE;
		}
		
		ShellCommand command = env.commands().get(arguments);
		
		if (command == null) {
			env.writeln("Unknown command!");
			
			return ShellStatus.CONTINUE;
		} 
		
		List<String> commandDescription = command.getCommandDescription();
		
		if (commandDescription == null || commandDescription.size() == 0) {
			env.writeln("No description available!");
			
			return ShellStatus.CONTINUE;
		}
		
		for (String line : commandDescription) {
			env.writeln(line);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		
		desc.add("Command help takes one argument.");
		desc.add("The argument is command name.");
		desc.add("It returns description for the given command name.");
		
		return desc;
	}

}
