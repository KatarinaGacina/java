package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
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

public class HexdumpShellCommand implements ShellCommand {
	
	/**
	 * buffer size
	 */
	private static final int BUFFLEN=16;
	
	/**
	 * Function that represents hexdump command logic and executes it.
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
		
		Path path = null;
		
		try {
			path = Paths.get(argument.get(0));
			
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path!");
			
			return ShellStatus.CONTINUE;
		}
		
		if (Files.isDirectory(path)) {
			env.writeln("Argument must be file not a directory!");
			
			return ShellStatus.CONTINUE;
		}
		
		StringBuilder sb = new StringBuilder();
		
		try (InputStream input = Files.newInputStream(path)) {
			
			byte[] buff = new byte[BUFFLEN];
			
			int offset = 0;
			int num = 0;
			
			while ((num = input.read(buff)) > 0) {
				sb.append(String.format("%08X: ", offset));
				
				for (int i = 0; i < BUFFLEN; i++) {
					if (i == 8) {
						sb.deleteCharAt(sb.length() - 1);
						sb.append("|");
					}
					
					if (i < num) {
						sb.append(String.format("%02X ", buff[i]));
					} else {
						sb.append(String.format("   ", buff[i]));
					}
					
				}
				
				sb.append("| ");
				
				String text = new String(buff).substring(0, num).replaceAll("[^\\x00-\\x7F]", ".");
				text = text.replaceAll("[\\x00-\\x1F]", ".");
				
				sb.append(text);
				
				sb.append("\n");
				
				offset += BUFFLEN;
			}
			
		} catch (IOException e) {
			throw new ShellIOException();
		}
		
		env.write(sb.toString());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command hexdump expects a single argument: file name, and produces hex-output.");
		description.add("The first row is offset.");
		description.add("Most right a standard subset of characters is shown; all characters, all bytes whose value is less than 32 or greater than 127 are replaced with '.'");
		
		return description;
	}
}
