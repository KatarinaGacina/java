package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * Class that represents copy command.
 * @author Katarina Gacina
 *
 */
public class CopyShellCommand implements ShellCommand {
	
	/**
	 * Function that represents copy command logic and executes it.
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
		
		if (argument.size() != 2) {
			env.writeln("Invalid number of arguments!");
			
			return ShellStatus.CONTINUE;
		}
		
		Path sourcePath = Paths.get(argument.get(0));
		Path destinationPath = Path.of(argument.get(1));
		
		try {
			sourcePath = Paths.get(argument.get(0));
			destinationPath = Path.of(argument.get(1));
			
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path!");
			
			return ShellStatus.CONTINUE;
		}
		
		if (Files.isDirectory(sourcePath)) {
			env.writeln("First parameter must not be a directory!");
			
			return ShellStatus.CONTINUE;
		}
		
		if (Files.isDirectory(destinationPath)) {
			destinationPath = destinationPath.resolve(sourcePath.getFileName().toString());
		}
		
		if (Files.exists(destinationPath)) {
			env.write(env.getPromptSymbol().toString());
			env.write(" ");
			env.writeln("Do you want to overwrite the existing file[yes\\no]?");
			env.write(env.getPromptSymbol().toString());
			env.write(" ");
			
			String response = env.readLine();
			
			if (!response.equals("yes") && !response.isEmpty()) {
				return ShellStatus.CONTINUE;
			}
		}
		
		try (InputStream input = new BufferedInputStream(Files.newInputStream(sourcePath)); OutputStream output = new BufferedOutputStream(Files.newOutputStream(destinationPath))) {
			int n;
			  
            while ((n = input.read()) != -1) {
                output.write(n);
            }
			
		} catch (IOException e) {
			throw new ShellIOException();
		}
				
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command copy expects two arguments: source file name and destination file name (i.e. paths and	names).");
		description.add("It copies file from source to destination.");
		description.add("If destination file exists, user is asked if shell is allowed to overwrite it.");
		description.add("If the second argument is directory, it is assumed that user wants to copy the original file into that directory using the original file name.");
		
		return description;
	}

}
