package me.japherwocky.portals;

import org.bukkit.Bukkit;

/**
 * Utility class for debugging
 *
 */

public enum PortalsDebbuger {
	
	VERY_LOW(0),
	LOW(1),
	MEDIUM(2),
	HIGH(3),
	VERY_HIGH(4);
	
	private int level;
	
	private PortalsDebbuger(int level) {
		this.level = level;
	}
	
	/**
	 * Print a message to the console if the debug level is high enough
	 * 
	 * @param message The message to print
	 */
	public void print(String message) {
		if (level >= PortalsSettings.getDebugLevel()) {
			Bukkit.getConsoleSender().sendMessage("[Portals] "+message);
		}
	}
	
	/**
	 * Print a message to the console if the debug level is high enough
	 * 
	 * @param message The message to print
	 * @param debugLevel The debug level to print at
	 */
	public static void print(String message, int debugLevel) {
		if (debugLevel >= PortalsSettings.getDebugLevel()) {
			Bukkit.getConsoleSender().sendMessage("[Portals] "+message);
		}
	}
}

