package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents tree command.
 * @author Katarina Gacina
 *
 */
public class TreeShellCommand implements ShellCommand{
	
	/**
	 * Function that represents tree command logic and executes it.
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
		
		Path path= null;
		
		try {
			path = Paths.get(argument.get(0));
			
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path!");
			
			return ShellStatus.CONTINUE;
		}
		 
		if (!Files.isDirectory(path)) {
			env.writeln("Expected directory!");
			
			return ShellStatus.CONTINUE;
		}
		
		StringBuilder treeSb = new StringBuilder();
		
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				int n = 0;
				
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			        for (int i = 0; i < n; i++) {
			        	treeSb.append("  ");
			        }
					
					treeSb.append(file.getFileName());
			        treeSb.append("\n");

			        return FileVisitResult.CONTINUE;
			    }
				
				@Override
				public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) throws IOException {
					for (int i = 0; i < n; i++) {
			        	treeSb.append("  ");
			        }
					
					treeSb.append(file.getFileName());
			        treeSb.append("\n");
			        
			        n += 1;
			        
			        return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					n -= 1;
					
					return super.postVisitDirectory(dir, exc);
				}
				
				
			});
		} catch (IOException e) {
			throw new ShellIOException();
		}
		
		env.write(treeSb.toString());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Command tree command expects a single argument: directory name.");
		description.add("It prints a tree (each directory level shifts output two charatcers to the right).");
		
		return description;
	}

}
