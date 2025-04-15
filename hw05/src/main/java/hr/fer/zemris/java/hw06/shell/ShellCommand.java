package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface that represents one shell command.
 * @author Katarina Gacina
 *
 */
public interface ShellCommand {
	
	/**
	 * Function that represents command logic and executes it.
	 * @param env shell environment
	 * @param arguments arguments that appear after command name, represented as one String
	 * @return ShellStatus CONTINUE if shell should continue or TERMINATE if shell should terminate work
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Function returns command name.
	 * @return String command name
	 */
	String getCommandName();
	
	/**
	 * Function returns command description.
	 * @return List<String> command description
	 */
	List<String> getCommandDescription();
}
