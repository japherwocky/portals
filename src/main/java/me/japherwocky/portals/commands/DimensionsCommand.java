package me.japherwocky.portals.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

/**
 * Parent class of commands that are being executed with <b>/portal [command]</b>
 * @deprecated Use PortalsCommand instead
 */

@Deprecated
public abstract class DimensionsCommand extends PortalsCommand {
	
	/**
	 * Constructor of DimensionsCommand
	 * @param command the command
	 * @param args a string showing what arguments are required to execute the ocmmand
	 * @param aliases String array with aliases for the command
	 * @param description Short description of the command
	 * @param permission empty string to allow the default permission or the permission to run the command
	 * @param adminCommand true if this command should not be viewed by non-admin users
	 */
	public DimensionsCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand) {
		super(command, args, aliases, description, permission, adminCommand);
	}
	
	// All methods are inherited from PortalsCommand

	/**
	 * Execute the command
	 * @param sender the sender of the command
	 * @param args the arguments used
	 */
	public abstract void execute(CommandSender sender, String[] args);
	
	/**
	 * Requests a list of possible completions for a command argument.
	 * @param sender the sender of the command
	 * @param args the arguments used
	 */
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return new ArrayList<String>();
	}
	
}
