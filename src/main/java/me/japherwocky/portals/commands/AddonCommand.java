package me.japherwocky.portals.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.PortalsDebbuger;
import me.japherwocky.portals.addons.PortalsAddon;
import me.japherwocky.portals.addons.PortalsAddonManager;

/**
 * Command to manage addons
 *
 */

public class AddonCommand implements CommandExecutor, TabCompleter {
	
	private Portals plugin;
	
	public AddonCommand(Portals plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length == 0) {
			sender.sendMessage("§cUsage: /portalsaddon <list|info|reload|unload> [addon]");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list")) {
			
			PortalsAddonManager addonManager = plugin.getAddonManager();
			
			if (addonManager.getAddons().size() == 0) {
				sender.sendMessage("§cNo addons loaded");
				return true;
			}
			
			sender.sendMessage("§aLoaded addons:");
			
			for (PortalsAddon addon : addonManager.getAddons()) {
				sender.sendMessage("§a- §e" + addon.getName() + " §7v" + addon.getVersion() + " §7by §e" + addon.getAuthor());
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("info")) {
			
			if (args.length < 2) {
				sender.sendMessage("§cUsage: /portalsaddon info <addon>");
				return true;
			}
			
			PortalsAddonManager addonManager = plugin.getAddonManager();
			
			PortalsAddon addon = addonManager.getAddonByName(args[1]);
			
			if (addon == null) {
				sender.sendMessage("§cAddon not found");
				return true;
			}
			
			sender.sendMessage("§aAddon info:");
			sender.sendMessage("§a- §eName: §7" + addon.getName());
			sender.sendMessage("§a- §eVersion: §7" + addon.getVersion());
			sender.sendMessage("§a- §eAuthor: §7" + addon.getAuthor());
			sender.sendMessage("§a- §eDescription: §7" + addon.getDescription());
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			
			if (args.length < 2) {
				sender.sendMessage("§cUsage: /portalsaddon reload <addon>");
				return true;
			}
			
			PortalsAddonManager addonManager = plugin.getAddonManager();
			
			PortalsAddon addon = addonManager.getAddonByName(args[1]);
			
			if (addon == null) {
				sender.sendMessage("§cAddon not found");
				return true;
			}
			
			addonManager.unload(addon);
			addonManager.loadAddon(addon.getFile());
			
			sender.sendMessage("§aAddon reloaded");
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("unload")) {
			
			if (args.length < 2) {
				sender.sendMessage("§cUsage: /portalsaddon unload <addon>");
				return true;
			}
			
			PortalsAddonManager addonManager = plugin.getAddonManager();
			
			PortalsAddon addon = addonManager.getAddonByName(args[1]);
			
			if (addon == null) {
				sender.sendMessage("§cAddon not found");
				return true;
			}
			
			addonManager.unload(addon);
			
			sender.sendMessage("§aAddon unloaded");
			
			return true;
		}
		
		sender.sendMessage("§cUsage: /portalsaddon <list|info|reload|unload> [addon]");
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		List<String> completions = new ArrayList<>();
		
		if (args.length == 1) {
			completions.add("list");
			completions.add("info");
			completions.add("reload");
			completions.add("unload");
		}
		
		if (args.length == 2) {
			
			if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("unload")) {
				
				PortalsAddonManager addonManager = plugin.getAddonManager();
				
				for (PortalsAddon addon : addonManager.getAddons()) {
					completions.add(addon.getName());
				}
			}
		}
		
		return completions;
	}
}
