package me.japherwocky.portals.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

/**
 * Parent class of commands that are being executed with <b>/portals [command]</b>
 *
 */

public abstract class PortalsCommand {
	
	private String command;
	private String args;
	private String[] aliases;
	private String description;
	private String permission;
	private boolean adminCommand;
	
	/**
	 * Constructor of PortalsCommand
	 * @param command the command
	 * @param args a string showing what arguments are required to execute the ocmmand
	 * @param aliases String array with aliases for the command
	 * @param description Short description of the command
	 * @param permission empty string to allow the default permission or the permission to run the command
	 * @param adminCommand true if this command should not be viewed by non-admin users
	 */
	public PortalsCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand) {
		this.command = command;
		this.args = args;
		this.aliases = aliases;
		this.description = description;
		this.permission = permission;
		this.adminCommand = adminCommand;
	}
	
	/**
	 * Execute the command
	 * 
	 * @param sender The sender of the command
	 * @param args The arguments of the command
	 * @return True if the command was executed successfully
	 */
	public abstract boolean execute(CommandSender sender, String[] args);
	
	/**
	 * Get the command
	 * 
	 * @return The command
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * Get the arguments
	 * 
	 * @return The arguments
	 */
	public String getArgs() {
		return args;
	}
	
	/**
	 * Get the aliases
	 * 
	 * @return The aliases
	 */
	public String[] getAliases() {
		return aliases;
	}
	
	/**
	 * Get the description
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Get the permission
	 * 
	 * @return The permission
	 */
	public String getPermission() {
		return permission;
	}
	
	/**
	 * Check if the command is an admin command
	 * 
	 * @return True if the command is an admin command
	 */
	public boolean isAdminCommand() {
		return adminCommand;
	}
	
	/**
	 * Get tab completions for the command
	 * 
	 * @param sender The sender of the command
	 * @param args The arguments of the command
	 * @return The tab completions
	 */
	public List<String> tabComplete(CommandSender sender, String[] args) {
		return new ArrayList<String>();
	}
}

