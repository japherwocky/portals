package me.japherwocky.portals.addons.patreoncosmetics;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.japherwocky.portals.commands.PortalsCommand;

public class PatreonDisableCommand extends PortalsCommand {

	PortalsPatreonCosmetics main;

	public PatreonDisableCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand, PortalsPatreonCosmetics main) {
		super(command,args,aliases,description, permission, adminCommand);
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (!(sender instanceof Player)) return;

		main.getUsers().remove(((Player) sender).getUniqueId());
		sender.sendMessage("§7[§cPortals§7] §aSuccesfully disabled portal effects for the current session.");
	}
}
