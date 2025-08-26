package me.japherwocky.portals;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import me.japherwocky.portals.addons.DimensionsAddon;
import me.japherwocky.portals.addons.DimensionsAddonManager;
import me.japherwocky.portals.addons.patreoncosmetics.DimensionsPatreonCosmetics;
import me.japherwocky.portals.builder.CreatePortalManager;
import me.japherwocky.portals.commands.DimensionsCommandManager;
import me.japherwocky.portals.completePortal.CompletePortalManager;
import me.japherwocky.portals.customportal.CustomPortal;
import me.japherwocky.portals.customportal.CustomPortalManager;
import me.japherwocky.portals.listener.PortalListener;
import me.japherwocky.portals.settings.PortalsSettings;
import me.japherwocky.portals.PortalsDebbuger;
import me.japherwocky.portals.PortalsUtils;

/**
 * Main class of the plugin
 */
public class Portals extends JavaPlugin {
	
	private static Portals instance;
	private static DimensionsCommandManager commandManager;
	private static DimensionsAddonManager addonsManager;
	private static CompletePortalManager completePortalManager;
	private static CustomPortalManager customPortalManager;
	private static CreatePortalManager createPortalManager;
	
	private static DimensionsPatreonCosmetics patreonCosmetics;
	
	public void onLoad() {
		
		instance = this;
		
		PortalsDebbuger.VERY_LOW.print("Loading Portals settings...");
		new PortalsSettings(this);
 
		PortalsDebbuger.VERY_LOW.print("Loading addons...");
		addonsManager = new DimensionsAddonManager(this);
		PortalsDebbuger.VERY_LOW.print("Loaded "+addonsManager.getAddons().size()+" addons.");
		
	}
	
	public void onEnable() {

		PortalsDebbuger.DEBUG.print("Registering commands...");
		commandManager = new DimensionsCommandManager(this);
		
		if (PortalsSettings.enablePatreonCosmetics)
			patreonCosmetics = new DimensionsPatreonCosmetics(this);
		
		PortalsDebbuger.VERY_LOW.print("Enabling addons...");
		addonsManager.enableAddons();
		
		PortalsDebbuger.VERY_LOW.print("Loading portals...");
		customPortalManager = new CustomPortalManager(this);
		PortalsDebbuger.MEDIUM.print("Found "+customPortalManager.getCustomPortals().size()+" portals.");
		completePortalManager = new CompletePortalManager(this);
		
		PortalsDebbuger.VERY_LOW.print("Instatiating GUIs...");
		createPortalManager = new CreatePortalManager(this);

		PortalsDebbuger.DEBUG.print("Registering Listener class...");
		new PortalListener(this);
		
		//Use a task in order to load portals only after all plugins have loaded and have generated/loaded their worlds
		//Portals require a world instance in order to be loaded and we can only have that if the plugin has loaded the required worlds

		PortalsDebbuger.DEBUG.print("Portals has been loaded. Waiting for server to tick before loading saved portals...");
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			
			@Override
			public void run() {
				
				PortalsSettings.setDefaultWorld();

				PortalsDebbuger.DEBUG.print("Loading saved portals...");
				completePortalManager.loadAll();
				PortalsDebbuger.DEBUG.print("Loading complete...");
				
			}
		}, 1);
		
		int pluginId = 6978;
		 Metrics metrics = new Metrics(this, pluginId);
	        
	        metrics.addCustomChart(new Metrics.DrilldownPie("portal_blocks_frames", () -> {
	            Map<String, Map<String, Integer>> map = new HashMap<>();
	            for (CustomPortal portal : getCustomPortalManager().getCustomPortals()) {
	                Map<String, Integer> entry = new HashMap<>();
	                entry.put(portal.getInsideMaterial().toString(),1);
	                map.put(portal.getOutsideMaterial().toString(), entry);
	            }
	            return map;
	        }));
	        
	        metrics.addCustomChart(new Metrics.DrilldownPie("portal_blocks_lighters", () -> {
	            Map<String, Map<String, Integer>> map = new HashMap<>();
	            for (CustomPortal portal : getCustomPortalManager().getCustomPortals()) {
	                Map<String, Integer> entry = new HashMap<>();
	                entry.put(portal.getLighterMaterial().toString(),1);
	                map.put(portal.getOutsideMaterial().toString(), entry);
	            }
	            return map;
	        }));
	        
	        metrics.addCustomChart(new Metrics.DrilldownPie("used_addons", () -> {
	            Map<String, Map<String, Integer>> map = new HashMap<>();
	            for (DimensionsAddon addon : getAddonManager().getAddons()) {
	                Map<String, Integer> entry = new HashMap<>();
	                entry.put(addon.getVersion(),1);
	                map.put(addon.getName(), entry);
	            }
	            return map;
	        }));
	}
	
	public void reload() {
		if (patreonCosmetics!=null)
			patreonCosmetics.disable();
		addonsManager.unloadAll();
		completePortalManager.save();
		HandlerList.unregisterAll(this);
		
		new PortalsSettings(this);
		PortalsSettings.setDefaultWorld();

		commandManager = new DimensionsCommandManager(this);
		
		if (PortalsSettings.enablePatreonCosmetics)
			patreonCosmetics = new DimensionsPatreonCosmetics(this);

		addonsManager.enableAddons();

		customPortalManager = new CustomPortalManager(this);
		completePortalManager = new CompletePortalManager(this);
		createPortalManager = new CreatePortalManager(this);
		
		new PortalListener(this);
		
		completePortalManager.loadAll();
	}
	
	public void onDisable() {
		
		addonsManager.onDisable();
		completePortalManager.save();
	}
	
	public static Portals getInstance() {
		return instance;
	}
	
	public static CompletePortalManager getCompletePortalManager() {
		return completePortalManager;
	}

	public static CustomPortalManager getCustomPortalManager() {
		return customPortalManager;
	}
	
	public static DimensionsAddonManager getAddonManager() {
		return addonsManager;
	}
	
	public static DimensionsCommandManager getCommandManager() {
		return commandManager;
	}

	public static CreatePortalManager getCreatePortalManager() {
		return createPortalManager;
	}
	
}
