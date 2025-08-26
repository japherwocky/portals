package me.japherwocky.portals.commands;

import org.bukkit.command.CommandSender;

import me.japherwocky.portals.Portals;

public class ReloadCommand extends PortalsCommand {
	
	public ReloadCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand) {
		super(command, args, aliases, description, permission, adminCommand);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Portals main = Portals.getInstance();
		
		main.reload();
		
		sender.sendMessage("§7[§cPortals§7] §aPlugin reloaded!");
	}
}

