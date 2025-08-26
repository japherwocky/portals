package me.japherwocky.portals;

/**
 * Settings for the Portals plugin
 *
 */

public class PortalsSettings {
	
	private static int debugLevel = 0;
	
	/**
	 * Get the debug level
	 * 
	 * @return The debug level
	 */
	public static int getDebugLevel() {
		return debugLevel;
	}
	
	/**
	 * Set the debug level
	 * 
	 * @param level The debug level
	 */
	public static void setDebugLevel(int level) {
		debugLevel = level;
	}
}

