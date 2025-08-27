package me.japherwocky.portals.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.completePortal.CompletePortal;
import me.japherwocky.portals.customportal.CustomPortalDestroyCause;

public class ClearCommand extends PortalsCommand {
	
	public ClearCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand) {
		super(command,args,aliases,description, permission, adminCommand);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (args.length==2) {
			Iterator<CompletePortal> iterator = Portals.getCompletePortalManager().getCompletePortals().iterator();
			while (iterator.hasNext()) {
				CompletePortal complete = iterator.next();
				if (args[1].equalsIgnoreCase("all") || (args[1].equalsIgnoreCase(complete.getCenter().getWorld().getName())) || (args[1].equalsIgnoreCase(complete.getCustomPortal().getPortalId()))) {
					iterator.remove();
					Portals.getCompletePortalManager().removePortal(complete, CustomPortalDestroyCause.PLUGIN, null);
				}
			}
			sender.sendMessage("§7[§cPortals§7] §aRemoved §c"+args[1]+"§a portals");
		} else {
			sender.sendMessage("§7[§cPortals§7] Missing argument. Please use /dim "+this.getCommand()+" "+this.getArgs());
		}
		
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		ArrayList<String> res = new ArrayList<String>();
		
		if (args.length != 2) return res;

		res.add("all");
		Bukkit.getWorlds().forEach(w -> res.add(w.getName()));
		Portals.getCustomPortalManager().getCustomPortals().forEach(w -> res.add(w.getPortalId()));
		
		return res;
	}
	
}
