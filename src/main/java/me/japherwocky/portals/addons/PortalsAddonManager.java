package me.japherwocky.portals.addons;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.bukkit.event.HandlerList;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.PortalsDebbuger;

/**
 * Loads and enables all the Portals addons
 *
 */

public class PortalsAddonManager {

	private final String ADDONS_PATH = "./plugins/Portals/Addons/";
	
	private Portals pl;

	private ServiceLoader<PortalsAddon> loader;
	private ArrayList<PortalsAddon> addons = new ArrayList<PortalsAddon>();
	
	/**
	 * Constructor of PortalsAddonManager
	 * 
	 * @param pl The main class of the plugin
	 */
	public PortalsAddonManager(Portals pl) {
		this.pl = pl;
		
		File addonsFolder = new File(ADDONS_PATH);
		if (!addonsFolder.exists())
			addonsFolder.mkdirs();
		
		File[] addonFiles = addonsFolder.listFiles();
		
		if (addonFiles.length == 0)
			return;
		
		ArrayList<URL> urls = new ArrayList<URL>();
		
		for (File file : addonFiles) {
			if (file.getName().endsWith(".jar")) {
				try {
					urls.add(file.toURI().toURL());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (urls.isEmpty())
			return;
		
		URLClassLoader ucl = new URLClassLoader(urls.toArray(new URL[0]), this.getClass().getClassLoader());
		
		loader = ServiceLoader.load(PortalsAddon.class, ucl);
		
		Iterator<PortalsAddon> iterator = loader.iterator();
		
		while (true) {
			try {
				if (!iterator.hasNext())
					break;
				
				PortalsAddon addon = iterator.next();
				
				addons.add(addon);
				
				PortalsDebbuger.VERY_LOW.print("Found addon: "+addon.getName()+" v"+addon.getVersion());
				
			} catch (ServiceConfigurationError e) {
				e.printStackTrace();
			}
		}
		
		addons.sort((a1, a2) -> {
			return a2.getPriority().getPriority() - a1.getPriority().getPriority();
		});
	}
	
	/**
	 * Enable all the addons
	 */
	public void enableAddons() {
		for (PortalsAddon addon : addons) {
			try {
				addon.onEnable();
				PortalsDebbuger.VERY_LOW.print("Enabled addon: "+addon.getName()+" v"+addon.getVersion());
			} catch (Exception e) {
				PortalsDebbuger.VERY_LOW.print("Failed to enable addon: "+addon.getName()+" v"+addon.getVersion());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Disable all the addons
	 */
	public void onDisable() {
		for (PortalsAddon addon : addons) {
			try {
				addon.onDisable();
				PortalsDebbuger.VERY_LOW.print("Disabled addon: "+addon.getName()+" v"+addon.getVersion());
			} catch (Exception e) {
				PortalsDebbuger.VERY_LOW.print("Failed to disable addon: "+addon.getName()+" v"+addon.getVersion());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Unload all the addons
	 */
	public void unloadAll() {
		for (PortalsAddon addon : addons) {
			try {
				addon.onDisable();
				PortalsDebbuger.VERY_LOW.print("Disabled addon: "+addon.getName()+" v"+addon.getVersion());
			} catch (Exception e) {
				PortalsDebbuger.VERY_LOW.print("Failed to disable addon: "+addon.getName()+" v"+addon.getVersion());
				e.printStackTrace();
			}
		}
		
		HandlerList.unregisterAll(pl);
		
		addons.clear();
	}
	
	/**
	 * Get all the addons
	 * 
	 * @return All the addons
	 */
	public ArrayList<PortalsAddon> getAddons() {
		return addons;
	}
	
	/**
	 * Get an addon by its name
	 * 
	 * @param name The name of the addon
	 * @return The addon, or null if not found
	 */
	public PortalsAddon getAddonByName(String name) {
		for (PortalsAddon addon : addons) {
			if (addon.getName().equalsIgnoreCase(name)) {
				return addon;
			}
		}
		return null;
	}
	
	/**
	 * Unload an addon
	 * 
	 * @param addon The addon to unload
	 */
	public void unload(PortalsAddon addon) {
		try {
			addon.onDisable();
			PortalsDebbuger.VERY_LOW.print("Disabled addon: " + addon.getName() + " v" + addon.getVersion());
		} catch (Exception e) {
			PortalsDebbuger.VERY_LOW.print("Failed to disable addon: " + addon.getName() + " v" + addon.getVersion());
			e.printStackTrace();
		}
		
		addons.remove(addon);
	}
	
	/**
	 * Load an addon from a file
	 * 
	 * @param file The file to load the addon from
	 */
	public void loadAddon(File file) {
		try {
			URL url = file.toURI().toURL();
			URLClassLoader ucl = new URLClassLoader(new URL[] { url }, this.getClass().getClassLoader());
			
			ServiceLoader<PortalsAddon> newLoader = ServiceLoader.load(PortalsAddon.class, ucl);
			Iterator<PortalsAddon> iterator = newLoader.iterator();
			
			while (iterator.hasNext()) {
				PortalsAddon addon = iterator.next();
				addons.add(addon);
				addon.onEnable();
				PortalsDebbuger.VERY_LOW.print("Loaded and enabled addon: " + addon.getName() + " v" + addon.getVersion());
			}
			
			addons.sort((a1, a2) -> {
				return a2.getPriority().getPriority() - a1.getPriority().getPriority();
			});
		} catch (Exception e) {
			PortalsDebbuger.VERY_LOW.print("Failed to load addon from file: " + file.getName());
			e.printStackTrace();
		}
	}
}
