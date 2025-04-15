package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
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
 * Class represents cat command. 
 * @author Katarina Gacina
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Function that represents cat command logic and executes it.
	 * @param env shell environment
	 * @param arguments arguments that appear after command name, represented as one String
	 * @return ShellStatus CONTINUE
	 * @throws ShellIOException when problem with reading or writing files occurs
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments == null) {
			env.writeln("Missing arguments!");
			
			return ShellStatus.CONTINUE;
		}
		
		List<String> argument = null;
		try {
			argument = ReadArg.getArguments(arguments);
			
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			
			return ShellStatus.CONTINUE;
		}
		
		Path path = null;
		Charset charset = null;
		
		try {
			if (argument.size() == 2) {
				
				path = Paths.get(argument.get(0));

				charset = Charset.forName(argument.get(1));
				
			} else if (argument.size() == 1) {
				
				path = Paths.get(argument.get(0));
				
				charset = Charset.defaultCharset();
				
			} else {
				env.writeln("Invalid number of arguments!");
				
				return ShellStatus.CONTINUE;
			}

		} catch (InvalidPathException ex1) {
			env.writeln("Invalid path!");
			
			return ShellStatus.CONTINUE;
			
		} catch (IllegalCharsetNameException ex2) {
			env.writeln("Given charset name " + argument.get(1) + " does not exist!");
			
			return ShellStatus.CONTINUE;
			
		}
		
		if (Files.isDirectory(path)) {
			env.writeln("Expected file, not a directory!");
				
			return ShellStatus.CONTINUE; 
		}
				
		try (BufferedReader buff = new BufferedReader(
				new InputStreamReader(
						new BufferedInputStream(
						Files.newInputStream(path)), charset))) {
			
			String line = buff.readLine();
			
			while(line != null) {
			  env.writeln(line);
			  line = buff.readLine();
			}
			
		} catch (IOException e) {
			throw new ShellIOException();
		} 
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command cat takes one or two arguments.");
		description.add("The first argument is path to some file and is mandatory.");
		description.add("Second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset is used.");
		description.add("This command opens given file and writes its content to console.");
		
		return description;
	}

}
