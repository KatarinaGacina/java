package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents mkdir command.
 * @author Katarina Gacina
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Function that represents mkdir command logic and executes it.
	 * @param env shell environment
	 * @param arguments arguments that appear after command name, represented as one String
	 * @return ShellStatus CONTINUE
	 * @throws ShellIOException when problem with reading or writing files occurs
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments == null) {
			env.writeln("Missing arguments.");
			
			return ShellStatus.CONTINUE;
		}
		
		List<String> argument = null;
		try {
			argument = ReadArg.getArguments(arguments);
			
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			
			return ShellStatus.CONTINUE;
		}
		
		if (argument.size() != 1) {
			env.writeln("Invalid number of arguments!");
			
			return ShellStatus.CONTINUE;
		}
		
		try {
		    Path path = Paths.get(argument.get(0));

		    Files.createDirectories(path);

		    env.writeln("Directory is successfully created!");

		 } catch(InvalidPathException e1) {
			 env.writeln("Invalid path!");
			 
			 return ShellStatus.CONTINUE;
			 
		 } catch (IOException e2) {
			 
			 throw new ShellIOException();
		 }
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command mkdir takes a single argument: directory name.");
		description.add("It creates the appropriate directory structure.");
		
		return description;
	}

}
