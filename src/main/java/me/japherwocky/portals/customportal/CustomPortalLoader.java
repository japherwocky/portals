package me.japherwocky.portals.customportal;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Orientable;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import me.japherwocky.portals.AxisOrFace;
import me.japherwocky.portals.Portals;
import me.japherwocky.portals.PortalsDebbuger;
import me.japherwocky.portals.completePortal.PortalGeometry;

/**
 * Loads all the custom portals
 */

public class CustomPortalLoader {
	
	public static final String DIRECTORY_PATH = "./plugins/Portals/Portals";
	public static final File PORTALS_DIRECTORY = new File(DIRECTORY_PATH);
	public static final String CONFIG_VERSION = "3.0.1";
	
	// For modern Minecraft (1.21+), we use NMS reflection directly
	private static Class<?> nmsBlockClass;
	private static Class<?> craftBlockDataClass;
	private static Method getStateIdMethod;
	private static Method getStateMethod;
	private static boolean nmsAvailable = false;
	
	/**
	 * Constructor of the loader - sets up NMS reflection for block ID lookup
	 */
	public CustomPortalLoader() {
		initializeNMSReflection();
	}
	
	/**
	 * Initialize NMS reflection for block state ID lookup
	 */
	private void initializeNMSReflection() {
		try {
			String serverVersion = getServerVersion();
			nmsBlockClass = Class.forName("net.minecraft.server." + serverVersion + ".Block");
			craftBlockDataClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".block.data.CraftBlockData");
			
			getStateIdMethod = findGetStateIdMethod();
			getStateMethod = craftBlockDataClass.getMethod("getState");
			nmsAvailable = true;
			
		} catch (ClassNotFoundException e) {
			logNMSUnavailable("NMS class not found for version: " + e.getMessage());
		} catch (NoSuchMethodException e) {
			logNMSUnavailable("Required method not found: " + e.getMessage());
		} catch (SecurityException e) {
			logNMSUnavailable("Security restriction prevented NMS access: " + e.getMessage());
		}
	}
	
	/**
	 * Get the current server version string
	 */
	private String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}
	
	/**
	 * Find the appropriate method for getting block state ID
	 */
	private Method findGetStateIdMethod() throws NoSuchMethodException {
		// Try parameterless getStateId() first
		try {
			return nmsBlockClass.getMethod("getStateId");
		} catch (NoSuchMethodException e) {
			// Continue to next attempt
		}
		
		// Try the obfuscated method 'i' with CraftBlockData parameter
		try {
			return nmsBlockClass.getMethod("i", craftBlockDataClass);
		} catch (NoSuchMethodException e) {
			// Continue to next attempt
		}
		
		// Try with two int parameters
		try {
			return nmsBlockClass.getMethod("getStateId", int.class, int.class);
		} catch (NoSuchMethodException e) {
			// Continue to last attempt
		}
		
		// Last resort: try getting from Block.a(BlockState)
		try {
			Method getBlockMethod = nmsBlockClass.getMethod("getBlock");
			return nmsBlockClass.getMethod("getStateId", getBlockMethod.getReturnType());
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodException("No suitable getStateId method found in Block class");
		}
	}
	
	/**
	 * Log NMS unavailability with debug message
	 */
	private void logNMSUnavailable(String message) {
		nmsAvailable = false;
		PortalsDebbuger.MEDIUM.print("NMS reflection unavailable: " + message);
		PortalsDebbuger.MEDIUM.print("Falling block visual effect will be disabled");
	}
	
	/**
	 * Load all the custom portals
	 */
	public ArrayList<CustomPortal> loadAll() {
		ArrayList<CustomPortal> res = new ArrayList<CustomPortal>();
		
		File portalFolder = new File(DIRECTORY_PATH);
		if (!portalFolder.exists()) portalFolder.mkdir();
		
		PortalGeometry.instance = PortalGeometry.nullGeometry();
		
		for (File f : PORTALS_DIRECTORY.listFiles()) {
			String portalID = f.getName().replace(".yml", "");
			if (portalID.contains(" ")) continue;
			
			YamlConfiguration portalConfig = YamlConfiguration.loadConfiguration(f);
			
			String fVersion = portalConfig.getString("configVersion", "pre3");
			if (!fVersion.equals(CONFIG_VERSION)) {
				
				if (portalConfig.contains("Options.BuildExitPortal")) {
					portalConfig.set("Options.ExitPortal.Enable", portalConfig.getBoolean("Options.BuildExitPortal"));
				} else {
					portalConfig.set("Options.ExitPortal.Enable", true);
				}
				
				portalConfig.set("Options.BuildExitPortal", null);
				
				portalConfig.set("Options.ExitPortal.FixedWidth", -1);
				portalConfig.set("Options.ExitPortal.FixedHeight", -1);
				
				portalConfig.set("configVersion", CONFIG_VERSION);
				
				try {
					portalConfig.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			boolean enabled = portalConfig.getBoolean("Enable", false);
			String displayName = portalConfig.getString("DisplayName", "Unnamed");

			Material outsideMaterial = Material.matchMaterial(portalConfig.getString("Portal.Frame.Material", "COBBLESTONE"));
			AxisOrFace outsideBlockDir = new AxisOrFace(portalConfig.getString("Portal.Frame.Face", "all"));
			Material insideMaterial = Material.matchMaterial(portalConfig.getString("Portal.InsideMaterial", "NEHTER_PORTAL"));

//			BlockData[] insideBlockData = new BlockData[] {getInsideBlockData(false, tempBlockData),getInsideBlockData(true, tempBlockData)};
//			int[] combinedId = createCombinedID(insideBlockData, insideMaterial);
			
			String ligherMaterialString = portalConfig.getString("Portal.LighterMaterial", "FLINT_AND_STEEL");
			Material lighterMaterial = ligherMaterialString.equalsIgnoreCase("null")?null:Material.matchMaterial(ligherMaterialString);
			String[] particlesColorString = portalConfig.getString("Portal.ParticlesColor", "0;0;0").split(";");
			Color particlesColor = Color.fromBGR(Integer.parseInt(particlesColorString[2]), Integer.parseInt(particlesColorString[1]), Integer.parseInt(particlesColorString[0]));
			
			Sound breakEffect = getSound(portalConfig.getString("Portal.BreakEffect", "BLOCK_GLASS_BREAK"));
			

			int minimumHeight = portalConfig.getInt("Portal.MinimumHeight", 4);
			int maximumHeight = portalConfig.getInt("Portal.MaximumHeight", 15);
			

			int maximumWidth = portalConfig.getInt("Portal.MaximumWidth", 14);
			int minimumWidth = portalConfig.getInt("Portal.MinimumWidth", 3);
			
			String worldName = portalConfig.getString("World.Name", "world");
			/*if (PortalsSettings.generateNewWorlds && !Bukkit.getServer().getWorlds().contains(Bukkit.getWorld(worldName))) {
				Bukkit.getServer().createWorld(new WorldCreator(worldName));
			}*/
			
//			String[] ratioString = portalConfig.getString("World.Ratio", "1:1").split(":");
//			int ratio0 = Integer.parseInt(ratioString[0]);
//			int ratio1 = Integer.parseInt(ratioString[1]);
//			int ratio = ratio1/ratio0;
			
			List<String> allowedWorlds = portalConfig.getStringList("Options.AllowedWorlds");
			if (allowedWorlds.size()==0) allowedWorlds.add("all");
			

			boolean buildExitPortal = portalConfig.getBoolean("Options.ExitPortal.Enable", true);
			int fixedExitPortalWidth = portalConfig.getInt("Options.ExitPortal.FixedWidth", -1);
			int fixedExitPortalHeight = portalConfig.getInt("Options.ExitPortal.FixedHeight", -1);
			
			int teleportDelay = portalConfig.getInt("Options.TeleportDelay", 4);
			boolean enableParticles = portalConfig.getBoolean("Options.EnableParticles", true);
			
			HashMap<EntityType,EntityType> entityTransformation = new HashMap<EntityType,EntityType>();
			for (String entity : portalConfig.getStringList("Entities.Transformation")) {
				String[] spl = entity.toUpperCase().split("->");
				entityTransformation.put(EntityType.valueOf(spl[0]), EntityType.valueOf(spl[1]));
			}
			
			String s = portalConfig.getString("Entities.Spawning.Delay", "5000-10000");
			int[] spawningDelay = new int[2]; 
			if (s.contains("-")) {
				String[] spawningDelayString = s.split("-");
				spawningDelay = new int[] {Integer.parseInt(spawningDelayString[0]),Integer.parseInt(spawningDelayString[1])};
			} else {
				int delay = Integer.parseInt(s);
				spawningDelay = new int[] {delay, delay};
			}
			HashMap<EntityType,Integer> entitySpawning = new HashMap<EntityType,Integer>();
			for (String entity : portalConfig.getStringList("Entities.Spawning.List")) {
				String[] spl = entity.toUpperCase().split(";");
				entitySpawning.put(EntityType.valueOf(spl[0]), Integer.parseInt(spl[1]));
			}
			CustomPortal portal = new CustomPortal(portalID, displayName, enabled, outsideMaterial, outsideBlockDir, insideMaterial, lighterMaterial, particlesColor,breakEffect,minimumHeight,maximumHeight, maximumWidth, minimumWidth,
					worldName,buildExitPortal, fixedExitPortalWidth, fixedExitPortalHeight, allowedWorlds, teleportDelay, enableParticles, entityTransformation, spawningDelay[0], spawningDelay[1], entitySpawning);
			portal.setInsideBlockData(insideMaterial.createBlockData());
			// Removed addon registration
			res.add(portal);
		}
		
		return res;
	}

	/**
	 * Creates combinedID for the block data inside the portal
	 * @param insideBlockData
	 * @param insideMaterial
	 * @return
	 */
	public static int[] createCombinedID(BlockData[] insideBlockData, Material insideMaterial) {
		int[] combinedId = { 0, 0 };
		
		// Skip if NMS reflection isn't available
		if (!nmsAvailable || nmsBlockClass == null || getStateIdMethod == null || getStateMethod == null) {
			return combinedId;
		}
		
		if (!insideMaterial.isSolid() && insideMaterial != Material.NETHER_PORTAL && insideMaterial != Material.END_GATEWAY) {
			return combinedId;
		}
		
		int stateId = getBlockStateId(insideBlockData[0]);
		if (stateId >= 0) {
			combinedId[0] = stateId;
			combinedId[1] = stateId;
		}
		
		return combinedId;
	}
	
	/**
	 * Get the block state ID using reflection
	 */
	private static int getBlockStateId(BlockData blockData) {
		try {
			Object nmsBlockData = getStateMethod.invoke(blockData);
			return invokeGetStateIdMethod(nmsBlockData);
		} catch (IllegalAccessException e) {
			PortalsDebbuger.MEDIUM.print("Failed to access NMS method: " + e.getMessage());
		} catch (java.lang.reflect.InvocationTargetException e) {
			PortalsDebbuger.MEDIUM.print("Failed to invoke NMS method: " + e.getMessage());
		} catch (NoSuchMethodException e) {
			PortalsDebbuger.MEDIUM.print("NMS method not found: " + e.getMessage());
		}
		return -1;
	}
	
	/**
	 * Invoke the appropriate getStateId method based on parameter count
	 */
	private static int invokeGetStateIdMethod(Object nmsBlockData) 
			throws IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
		int paramCount = getStateIdMethod.getParameterCount();
		
		if (paramCount == 0) {
			return (int) getStateIdMethod.invoke(nmsBlockData);
		} else if (paramCount == 1) {
			return (int) getStateIdMethod.invoke(nmsBlockData, 0);
		} else {
			// Multi-parameter method - use fallback approach
			Method getBlockMethod = nmsBlockClass.getMethod("getBlock");
			Object block = getBlockMethod.invoke(null);
			return (int) getStateIdMethod.invoke(block, nmsBlockData);
		}
	}

	/**
	 * Creates BlockData in the correct Z Axis
	 * @param zAxis
	 * @param blockData
	 * @return
	 */
	public static BlockData getInsideBlockData(boolean zAxis, BlockData blockData) {
		if (zAxis) {
			if (blockData instanceof Orientable) {
				Orientable orientable = (Orientable) blockData;
				orientable.setAxis(Axis.Z);
				blockData = orientable;
			} else if (blockData instanceof Directional) {
				Directional directional = (Directional) blockData;
				directional.setFacing(BlockFace.NORTH);
				blockData = directional;
			} else if (blockData instanceof MultipleFacing) {
				MultipleFacing face = (MultipleFacing) blockData;
				face.setFace(BlockFace.NORTH, true);
				face.setFace(BlockFace.SOUTH, true);
				blockData = face;
			}
		} else {
			if (blockData instanceof MultipleFacing) {
				MultipleFacing face = (MultipleFacing) blockData;
				face.setFace(BlockFace.EAST, true);
				face.setFace(BlockFace.WEST, true);
				blockData = face;
			}
		}
		
		return blockData;
	}
	
	/**
	 * Get a Sound by name, with fallback to a default
	 */
	private static Sound getSound(String name) {
		try {
			return Sound.valueOf(name);
		} catch (IllegalArgumentException e) {
			PortalsDebbuger.MEDIUM.print("Unknown sound: " + name + ", using default");
			return Sound.BLOCK_GLASS_BREAK;
		}
	}
	
}
