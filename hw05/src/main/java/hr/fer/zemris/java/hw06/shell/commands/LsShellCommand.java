package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents ls command.
 * @author Katarina Gacina
 *
 */
public class LsShellCommand implements ShellCommand {
	
	/**
	 * Function that represents ls command logic and executes it.
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
		
		Path pathDirectory = null;
		
		try {
			pathDirectory = Paths.get(argument.get(0));
			
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path!");
			
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(pathDirectory)) {
			env.writeln("Expected directory!");
				
			return ShellStatus.CONTINUE; 
		}
		
		StringBuilder fileLine = new StringBuilder();
		for (String fname : new File(argument.get(0)).list()) {
			Path path = Paths.get(argument.get(0) + "\\" + fname);
			
			BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes;
			
			try {
				long sizeInBytes;
				
				attributes = faView.readAttributes();
				if (attributes.isDirectory()) {
					fileLine.append("d");
					sizeInBytes = 4096;
				} else {
					fileLine.append("-");
					sizeInBytes = Files.size(path);
				}
				if (Files.isReadable(path)) fileLine.append("r");
				else fileLine.append("-");
				if (Files.isWritable(path)) fileLine.append("w");
				else fileLine.append("-");
				if (Files.isExecutable(path)) fileLine.append("x");
				else fileLine.append("-");
				
				fileLine.append(" ");
				
				long m = (long) (Math.floor(Math.log10(sizeInBytes)) + 1);
				for (int i = 0; i < 10 - m; i++) {
					fileLine.append(" ");
				}
				fileLine.append(sizeInBytes);
				
				fileLine.append(" ");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				fileLine.append(formattedDateTime);
				fileLine.append(" ");
				
				fileLine.append(fname);
				
				env.writeln(fileLine.toString());
				
				fileLine.setLength(0);
				
			} catch (IOException e) {
				throw new ShellIOException();
			}
			
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command ls takes a single argument – directory – and writes a directory listing.");
		description.add("The output consists of 4 columns.");
		description.add("First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
		description.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
		description.add("Follows file creation date/time and finally file name.");
		
		return description;
	}

}
