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
	
	private ArrayList<PortalsAddon> loadedAddons = new ArrayList<PortalsAddon>();
	
	
	private URL[] urls;
	
	/**
	 * Constructor of the Addon manager. Creates the directory containing the addons and loads all the addons using ServiceLoader
	 * @param pl The instance of the plugin
	 */
	public PortalsAddonManager(Portals pl) {
		this.pl = pl;
		
		
		File dir = new File(ADDONS_PATH);
	    if(!dir.exists()) dir.mkdirs();
	    
	    ArrayList<URL> urls = new ArrayList<URL>();
	    for(File file : dir.listFiles((file, name) -> name.endsWith(".jar"))) {
	    	if (file.getName().equals("PatreonCosmeticsAddon.jar")) continue; //Prevent error after embedding the addon into main Portals plugin
	    	try {
				urls.add(file.toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	    	
	    }
	    this.urls = urls.toArray(new URL[0]);
		loader = ServiceLoader.load(PortalsAddon.class, URLClassLoader.newInstance(this.urls, Portals.class.getClassLoader()));
		
		Iterator<PortalsAddon> iter = loader.iterator();
		while (iter.hasNext()) {
			try {
				PortalsAddon addon = iter.next();
				if (addon.onLoad(pl)) {
					PortalsDebbuger.MEDIUM.print("Loaded addon: "+addon.getName()+" v"+addon.getVersion());
					loadedAddons.add(addon);
				} else {
					PortalsDebbuger.MEDIUM.print("Failed to load addon: "+addon.getName()+" v"+addon.getVersion());
				}
			} catch (ServiceConfigurationError e) {
				String addonName = e.getMessage().substring(e.getMessage().lastIndexOf('.')+1);
				addonName = addonName.substring(0,addonName.indexOf(' ')-1);
				PortalsDebbuger.MEDIUM.print("Failed to load addon: "+addonName);
			}
		}
	}
	
	/**
	 * Enable all the addons
	 */
	public void enableAddons() {
		for (PortalsAddon addon : loadedAddons) {
			PortalsDebbuger.MEDIUM.print("Enabling addon: "+addon.getName()+" v"+addon.getVersion());
			addon.onEnable(pl);
		}
	}

	/*public PortalsAddon downloadAndExportAddon(String jarName, String updateJarURL) {
	    try {
	    	URLConnection urlConn = new URL(updateJarURL).openConnection();
	    	urlConn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
	    	
	    	File file = new File(ADDONS_PATH+jarName);
	    	
	    	if (file.exists()) {
	    		FileOutputStream fos = new FileOutputStream(file);
		    	fos.flush();
		    	fos.write(urlConn.getInputStream().readAllBytes());
		    	fos.close();
	    	} else {
	    		Files.copy(urlConn.getInputStream(), Paths.get(ADDONS_PATH+jarName), StandardCopyOption.REPLACE_EXISTING);
	    	}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	    
	    return null;
		//return loader.load(new File(ADDONS_PATH+jarName), PortalsAddon.class, urls);
		
	}*/

	/**
	 * Get the instance of the addon from the addon's name if loaded
	 * @param addonName the name of the addon
	 * @return the instance of the addon or null
	 */
	public PortalsAddon getAddonByName(String addonName) {
		for (PortalsAddon addon : loadedAddons) {
			if (addon.getName().contentEquals(addonName)) return addon;
		}
		return null;
	}

	/**
	 * Get the list of the loaded addons
	 * @return List of PortalsAddon
	 */
	public ArrayList<PortalsAddon> getAddons() {
		return loadedAddons;
	}
	
	/**
	 * Disable all the addons
	 */
	public void onDisable() {
		for (PortalsAddon addon : loadedAddons) {
			addon.onDisable();
		}

		PortalsAddon.resetOptions();
	}
	
	/**
	 * Disable all addons and cancel their running tasks
	 */
	public void unloadAll() {

		for (PortalsAddon addon : loadedAddons) {
			unload(addon);
		}
		
		PortalsAddon.resetOptions();
	}
	
	/**
	 * Unload the given addon
	 * @param plugin the addon to unload
	 * @return true
	 */
	public boolean unload(PortalsAddon plugin) {
		plugin.onDisable();
		
		HandlerList.getRegisteredListeners(pl).forEach(r -> HandlerList.unregisterAll(r.getListener()));
		return true;
    }

	/*public boolean update(PortalsAddon addon) {
		try {
			if (!addon.needsUpdate()) return false;
			PortalsDebbuger.debug("Found new version for "+addon.getName()+". Updating addon...", PortalsDebbuger.MEDIUM);
		
			addon = downloadAndExportAddon(jarFiles.get(addon).getName(), addon.getUpdateJarURL());
			PortalsDebbuger.debug("Update complete for "+addon.getName()+".", PortalsDebbuger.MEDIUM);
		} catch (Exception e) {
			PortalsDebbuger.debug("Could not update", PortalsDebbuger.MEDIUM);
			e.printStackTrace();
			return false;
		}
		
		return true;
	}*/
}
