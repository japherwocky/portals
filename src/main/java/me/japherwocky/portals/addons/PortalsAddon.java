package me.japherwocky.portals.addons;

import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.completePortal.CompletePortal;
import me.japherwocky.portals.customportal.CustomPortal;

/**
 * The abstract class that is parent to all the addons that are being loaded by Portals
 *
 */

public abstract class PortalsAddon {
	
	private String addonName;
	private String addonVersion;
	private String addonDescription;
	private DimensionsAddonPriority addonPriority = DimensionsAddonPriority.NORMAL;

	private static HashMap<CompletePortal, HashMap<String, Object>> addonOptionsOverride = new HashMap<CompletePortal, HashMap<String, Object>>();
	private static HashMap<CustomPortal, HashMap<String, Object>> addonOptions = new HashMap<CustomPortal, HashMap<String, Object>>();
	
	/**
	 * Constructor of PortalsAddon
	 * 
	 * @param addonName The name of the addon
	 * @param addonVersion The version of the addon
	 */
	public PortalsAddon(String addonName, String addonVersion) {
		this.addonName = addonName;
		this.addonVersion = addonVersion;
	}
	
	/**
	 * Constructor of PortalsAddon
	 * 
	 * @param addonName The name of the addon
	 * @param addonVersion The version of the addon
	 * @param addonDescription The description of the addon
	 */
	public PortalsAddon(String addonName, String addonVersion, String addonDescription) {
		this.addonName = addonName;
		this.addonVersion = addonVersion;
		this.addonDescription = addonDescription;
	}
	
	/**
	 * Constructor of PortalsAddon
	 * 
	 * @param addonName The name of the addon
	 * @param addonVersion The version of the addon
	 * @param addonDescription The description of the addon
	 * @param addonPriority The priority of the addon
	 */
	public PortalsAddon(String addonName, String addonVersion, String addonDescription, DimensionsAddonPriority addonPriority) {
		this.addonName = addonName;
		this.addonVersion = addonVersion;
		this.addonDescription = addonDescription;
		this.addonPriority = addonPriority;
	}
	
	/**
	 * Called when the addon is being enabled
	 */
	public abstract void onEnable();
	
	/**
	 * Called when the addon is being disabled
	 */
	public abstract void onDisable();
	
	/**
	 * Called when a portal is being created
	 * 
	 * @param portal The portal that is being created
	 */
	public void onPortalCreate(CompletePortal portal) {}
	
	/**
	 * Called when a portal is being removed
	 * 
	 * @param portal The portal that is being removed
	 */
	public void onPortalRemove(CompletePortal portal) {}
	
	/**
	 * Called when a portal is being teleported
	 * 
	 * @param portal The portal that is being teleported
	 */
	public void onPortalTeleport(CompletePortal portal) {}
	
	/**
	 * Called when a portal is being loaded
	 * 
	 * @param portal The portal that is being loaded
	 */
	public void onPortalLoad(CompletePortal portal) {}
	
	/**
	 * Called when a portal is being saved
	 * 
	 * @param portal The portal that is being saved
	 * @param config The config that is being saved
	 */
	public void onPortalSave(CompletePortal portal, YamlConfiguration config) {}
	
	/**
	 * Get the name of the addon
	 * 
	 * @return The name of the addon
	 */
	public String getName() {
		return addonName;
	}
	
	/**
	 * Get the version of the addon
	 * 
	 * @return The version of the addon
	 */
	public String getVersion() {
		return addonVersion;
	}
	
	/**
	 * Get the description of the addon
	 * 
	 * @return The description of the addon
	 */
	public String getDescription() {
		return addonDescription;
	}
	
	/**
	 * Get the priority of the addon
	 * 
	 * @return The priority of the addon
	 */
	public DimensionsAddonPriority getPriority() {
		return addonPriority;
	}
	
	/**
	 * Get the main class of the plugin
	 * 
	 * @return The main class of the plugin
	 */
	public Portals getPlugin() {
		return Portals.getInstance();
	}
	
	/**
	 * Get the addon options for a portal
	 * 
	 * @param portal The portal to get the options for
	 * @return The options for the portal
	 */
	public static HashMap<String, Object> getAddonOptions(CustomPortal portal) {
		if (!addonOptions.containsKey(portal))
			addonOptions.put(portal, new HashMap<String, Object>());
		return addonOptions.get(portal);
	}
	
	/**
	 * Get the addon options for a portal
	 * 
	 * @param portal The portal to get the options for
	 * @return The options for the portal
	 */
	public static HashMap<String, Object> getAddonOptions(CompletePortal portal) {
		if (!addonOptionsOverride.containsKey(portal))
			addonOptionsOverride.put(portal, new HashMap<String, Object>());
		return addonOptionsOverride.get(portal);
	}
	
	/**
	 * Set the addon options for a portal
	 * 
	 * @param portal The portal to set the options for
	 * @param options The options to set
	 */
	public static void setAddonOptions(CustomPortal portal, HashMap<String, Object> options) {
		addonOptions.put(portal, options);
	}
	
	/**
	 * Set the addon options for a portal
	 * 
	 * @param portal The portal to set the options for
	 * @param options The options to set
	 */
	public static void setAddonOptions(CompletePortal portal, HashMap<String, Object> options) {
		addonOptionsOverride.put(portal, options);
	}
	
	/**
	 * Set an addon option for a portal
	 * 
	 * @param portal The portal to set the option for
	 * @param key The key of the option
	 * @param value The value of the option
	 */
	public static void setAddonOption(CustomPortal portal, String key, Object value) {
		if (!addonOptions.containsKey(portal))
			addonOptions.put(portal, new HashMap<String, Object>());
		addonOptions.get(portal).put(key, value);
	}
	
	/**
	 * Set an addon option for a portal
	 * 
	 * @param portal The portal to set the option for
	 * @param key The key of the option
	 * @param value The value of the option
	 */
	public static void setAddonOption(CompletePortal portal, String key, Object value) {
		if (!addonOptionsOverride.containsKey(portal))
			addonOptionsOverride.put(portal, new HashMap<String, Object>());
		addonOptionsOverride.get(portal).put(key, value);
	}
	
	/**
	 * Get an addon option for a portal
	 * 
	 * @param portal The portal to get the option for
	 * @param key The key of the option
	 * @return The value of the option
	 */
	public static Object getAddonOption(CustomPortal portal, String key) {
		if (!addonOptions.containsKey(portal))
			addonOptions.put(portal, new HashMap<String, Object>());
		return addonOptions.get(portal).get(key);
	}
	
	/**
	 * Get an addon option for a portal
	 * 
	 * @param portal The portal to get the option for
	 * @param key The key of the option
	 * @return The value of the option
	 */
	public static Object getAddonOption(CompletePortal portal, String key) {
		if (!addonOptionsOverride.containsKey(portal))
			addonOptionsOverride.put(portal, new HashMap<String, Object>());
		return addonOptionsOverride.get(portal).get(key);
	}
	
	/**
	 * Check if a portal has an addon option
	 * 
	 * @param portal The portal to check
	 * @param key The key of the option
	 * @return True if the portal has the option
	 */
	public static boolean hasAddonOption(CustomPortal portal, String key) {
		if (!addonOptions.containsKey(portal))
			addonOptions.put(portal, new HashMap<String, Object>());
		return addonOptions.get(portal).containsKey(key);
	}
	
	/**
	 * Check if a portal has an addon option
	 * 
	 * @param portal The portal to check
	 * @param key The key of the option
	 * @return True if the portal has the option
	 */
	public static boolean hasAddonOption(CompletePortal portal, String key) {
		if (!addonOptionsOverride.containsKey(portal))
			addonOptionsOverride.put(portal, new HashMap<String, Object>());
		return addonOptionsOverride.get(portal).containsKey(key);
	}
	
	/**
	 * Remove an addon option from a portal
	 * 
	 * @param portal The portal to remove the option from
	 * @param key The key of the option
	 */
	public static void removeAddonOption(CustomPortal portal, String key) {
		if (!addonOptions.containsKey(portal))
			addonOptions.put(portal, new HashMap<String, Object>());
		addonOptions.get(portal).remove(key);
	}
	
	/**
	 * Remove an addon option from a portal
	 * 
	 * @param portal The portal to remove the option from
	 * @param key The key of the option
	 */
	public static void removeAddonOption(CompletePortal portal, String key) {
		if (!addonOptionsOverride.containsKey(portal))
			addonOptionsOverride.put(portal, new HashMap<String, Object>());
		addonOptionsOverride.get(portal).remove(key);
	}
	
	/**
	 * Clear all addon options for a portal
	 * 
	 * @param portal The portal to clear the options for
	 */
	public static void clearAddonOptions(CustomPortal portal) {
		addonOptions.remove(portal);
	}
	
	/**
	 * Clear all addon options for a portal
	 * 
	 * @param portal The portal to clear the options for
	 */
	public static void clearAddonOptions(CompletePortal portal) {
		addonOptionsOverride.remove(portal);
	}
	
	/**
	 * Clear all addon options
	 */
	public static void clearAllAddonOptions() {
		addonOptions.clear();
		addonOptionsOverride.clear();
	}
}

