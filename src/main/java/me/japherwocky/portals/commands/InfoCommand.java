package me.japherwocky.portals.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.settings.PortalsSettings;

public class InfoCommand extends PortalsCommand {
	
	public InfoCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand) {
		super(command,args,aliases,description, permission, adminCommand);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (PortalsSettings.showPortalsToPlayers) {
			Portals.getCreatePortalManager().handle((Player) sender);
		} else {
			sender.sendMessage("§7[§cPortals§7] Version "+ Portals.getInstance().getDescription().getVersion());
		}
	}
	
	
	
	
}
