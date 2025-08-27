package me.japherwocky.portals.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.PortalsUtils;
import me.japherwocky.portals.completePortal.CompletePortal;
import me.japherwocky.portals.customportal.CustomPortal;

public class PortalCommand extends PortalsCommand {
	
	public PortalCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand) {
		super(command,args,aliases,description, permission, adminCommand);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if (args.length==1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§7[§cPortals§7] This command without arguments can only be used from players.");
				return;
			}
			List<Block> los = ((Player) sender).getLineOfSight(null, 5);
			for (Block block : los) {
				if (!PortalsUtils.isAir(block)) break;
				CompletePortal compl = Portals.getCompletePortalManager().getCompletePortal(block.getLocation(), false, false);
				if (compl!=null) {
					CustomPortal portal = compl.getCustomPortal();
					sender.sendMessage("§7[§cPortals§7] "+portal.getDisplayName()+":§7 Is built from §c"+portal.getOutsideMaterial()+"§7, is ignited using §c"+portal.getLighterMaterial()+"§7 and this specific portal goes to §c"+(compl.getLinkedPortal()==null?portal.getWorld().getName():compl.getLinkedPortal().getWorld().getName())+"§7.");
					return;
				}
			}
			
			sender.sendMessage("§7[§cPortals§7] Could not find a portal where you look at.");
		} else if (args.length==2) {
			CustomPortal portal = Portals.getCustomPortalManager().getCustomPortal(args[1]);
			if (portal!=null) {
				sender.sendMessage("§7[§cPortals§7] "+portal.getDisplayName()+":§7 Is built from §c"+portal.getOutsideMaterial()+"§7, is ignited using §c"+portal.getLighterMaterial()+"§7 and goes to §c"+portal.getWorld().getName()+"§7.");
			} else {
				sender.sendMessage("§7[§cPortals§7] Could not find specified portal.");
			}
		} else {
			sender.sendMessage("§7[§cPortals§7] Missing argument. Please use /portal "+this.getCommand()+" "+this.getArgs());
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		ArrayList<String> res = new ArrayList<String>();

		if (args.length!=2) return res;
		
		Portals.getCustomPortalManager().getCustomPortals().forEach(p -> res.add(p.getPortalId()));
		
		return res;
	}
	
}
