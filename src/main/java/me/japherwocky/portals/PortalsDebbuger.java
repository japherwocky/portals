package me.japherwocky.portals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import me.japherwocky.portals.settings.PortalsSettings;

/**
 * Use static instances of the class to print debug messages to the appropriate debug level set in the config
 */
public class PortalsDebbuger {
	
	/**Show messages for debug level 5 */
	public static final PortalsDebbuger DEBUG = new PortalsDebbuger(5);
	/**Show messages for debug level 4 */
	public static final PortalsDebbuger VERY_LOW = new PortalsDebbuger(4);
	/**Show messages for debug level 3 */
	public static final PortalsDebbuger LOW = new PortalsDebbuger(3);
	/**Show messages for debug level 2 */
	public static final PortalsDebbuger MEDIUM = new PortalsDebbuger(2);
	/**Show messages for debug level 1 */
	public static final PortalsDebbuger HIGH = new PortalsDebbuger(1);
	/**Show messages for debug level 0 */
	public static final PortalsDebbuger VERY_HIGH = new PortalsDebbuger(0);
	
	
	private int level = 0;
	
	private PortalsDebbuger(int i) {
		this.level = i;
	}

	/**
	 * Use the PortalsDebbuger instance to print a message in the console for the appropriate debugLevels set in the config
	 * 
	 * @param str The message to print
	 */
	public void print(Object... str) {
		if (PortalsSettings.debugLevel>=level)
			Bukkit.getConsoleSender().sendMessage("§7[§cPortals§7] §r"+String.join(", ",Arrays.asList(str).stream()
					.map((s) -> s==null?"null":s.toString())
                    .collect(Collectors.toList())));
	}

			
}
