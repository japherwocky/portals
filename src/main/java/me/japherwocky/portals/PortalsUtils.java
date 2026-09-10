package me.japherwocky.portals;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
/**
 * Contains methods that are commonly used
 *
 */
public class PortalsUtils {

	/**
	 * Apply every gamerule declared in the given section to the world.
	 * Keys may use the vanilla namespaced form (minecraft:keep_inventory)
	 * or the bare name. Unknown rules and invalid values are logged and
	 * skipped.
	 *
	 * @param section the configuration section holding the gamerules
	 * @param world the world to apply them to
	 */
	public static void applyGamerules(ConfigurationSection section, World world) {
		if (section == null) return;

		for (String key : section.getKeys(false)) {
			String ruleName = key.startsWith("minecraft:") ? key.substring("minecraft:".length()) : key;
			GameRule<?> rule = GameRule.getByName(ruleName);
			if (rule == null) {
				PortalsDebbuger.MEDIUM.print("Unknown gamerule: " + key);
				continue;
			}

			String value = String.valueOf(section.get(key));
			try {
				if (Boolean.class.equals(rule.getType())) {
					setGameRuleValue(world, rule, Boolean.parseBoolean(value));
				} else if (Integer.class.equals(rule.getType())) {
					setGameRuleValue(world, rule, Integer.parseInt(value));
				} else if (Double.class.equals(rule.getType())) {
					setGameRuleValue(world, rule, Double.parseDouble(value));
				} else {
					PortalsDebbuger.MEDIUM.print("Unsupported gamerule type for " + key + ": " + rule.getType());
				}
			} catch (NumberFormatException e) {
				PortalsDebbuger.MEDIUM.print("Invalid value for gamerule " + key + ": " + value);
			}
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void setGameRuleValue(World world, GameRule rule, Object value) {
		world.setGameRule(rule, value);
	}

	/** 
	 * Check if the block is air
	 * 
	 * @param block block that is being checked
	 * @return true if the type of the block is AIR or CAVE_AIR
	 */
	public static boolean isAir(Block block) {
		return block.getType()==Material.AIR ||block.getType()==Material.CAVE_AIR;
	}
	
	/**
	 * Return a random integer in the given range
	 * 
	 * @param min inclusive
	 * @param max inclusive
	 * @return a random integer
	 */
	public static int getRandom(int min, int max) {
		return (int)(Math.random()*((max-min)+1))+min;
	}
	
	private static final Orientable netherPortalEffect = (Orientable) Material.NETHER_PORTAL.createBlockData();
	
	/**
	 * Get the nether portal BlockData for the axis
	 * @param zAxis if the BlockData must have zAxis
	 * @return NETHER_PORTAL BlockData facing the set Axis
	 */
	public static BlockData getNetherPortalEffect(boolean zAxis) {
		netherPortalEffect.setAxis(zAxis?Axis.Z:Axis.X);
		return netherPortalEffect;
	}
	
	/**
	 * Parse a location from a string
	 * @param str the string containing the location
	 * @param delim delimiter seperating the data
	 * @return the parsed location
	 */
	public static Location parseLocationFromString(String str, String delim) {
		String[] spl = str.split(delim);
		return new Location(Bukkit.getWorld(spl[0]), Double.parseDouble(spl[1]), Double.parseDouble(spl[2]), Double.parseDouble(spl[3]));
	}

	/**
	 * Convert a location to string
	 * @param loc the location to be stringified
	 * @param delim the delimeter to seperate the data
	 * @return the stringified location
	 */
	public static String locationToString(Location loc, String delim) {
		return loc.getWorld().getName()+delim+loc.getX()+delim+loc.getY()+delim+loc.getZ();
	}
	
	/**
	 * Check if string can be parsed as integer
	 * @param string string to check
	 * @return true if string is integer
	 */
	public static boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}
	
	 private static BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

	 /**
	  * Get blockface from location yaw
	  * @param yaw the yaw of the location
	  * @return BlockFace from yaw
	  */
	 public static BlockFace yawToFace(float yaw) {
		 return radial[Math.round(yaw / 45f) & 0x7];
	 }
	 
	 /**
	  * Check if the given BlockFace is placed along the Z Axis
	  * @param face BlockFace to check
	  * @return true if the BlockFace is WEST or EAST
	  */
	public static boolean isBlockFacezAxis(BlockFace face) {
		return face==BlockFace.WEST || face==BlockFace.EAST;
	}

	@SuppressWarnings({"deprecation", "removal"})
	public static void cloneEntity(Entity en, Entity newEn) {
		newEn.setCustomName(en.getCustomName());
		newEn.setCustomNameVisible(en.isCustomNameVisible());
		newEn.setFallDistance(en.getFallDistance());
		newEn.setFireTicks(en.getFireTicks());
		newEn.setFreezeTicks(en.getFreezeTicks());
		newEn.setGlowing(en.isGlowing());
		newEn.setGravity(en.hasGravity());
		newEn.setInvulnerable(en.isInvulnerable());
		var damage = en.getLastDamageCause();
		newEn.setLastDamageCause(damage);
		newEn.setOp(en.isOp());
		newEn.setPersistent(en.isPersistent());
		newEn.setPortalCooldown(en.getPortalCooldown());
		newEn.setSilent(en.isSilent());
		newEn.setTicksLived(en.getTicksLived());
		newEn.setVelocity(en.getVelocity());
		newEn.setVisualFire(en.isVisualFire());
		en.getPassengers().forEach(passenger -> newEn.addPassenger(passenger));
		en.getScoreboardTags().forEach(tag -> newEn.addScoreboardTag(tag));
		if (en.getVehicle()!=null) en.getVehicle().addPassenger(newEn);
		
		
		if (en instanceof LivingEntity && newEn instanceof LivingEntity) {
			LivingEntity newEn2 = (LivingEntity) newEn;
			LivingEntity en2 = (LivingEntity) en;

			en2.getActivePotionEffects().forEach(ef -> newEn2.addPotionEffect(ef));
			newEn2.setAbsorptionAmount(en2.getAbsorptionAmount());
			newEn2.setAI(en2.hasAI());
			newEn2.setArrowCooldown(en2.getArrowCooldown());
			newEn2.setArrowsInBody(en2.getArrowsInBody());
			newEn2.setCanPickupItems(en2.getCanPickupItems());
			newEn2.setCollidable(en2.isCollidable());
			newEn2.setGliding(en2.isGliding());
			Attribute maxHealthAttribute = Attribute.valueOf("MAX_HEALTH") == null ? Attribute.valueOf("GENERIC_MAX_HEALTH") : Attribute.valueOf("MAX_HEALTH");
			newEn2.setHealth(newEn2.getAttribute(maxHealthAttribute).getValue()/(en2.getAttribute(maxHealthAttribute).getValue()/en2.getHealth()));

			for (Attribute at : Attribute.values()) {
				AttributeInstance enAt = en2.getAttribute(at);
				AttributeInstance newEnAt = newEn2.getAttribute(at);
				if (enAt==null || newEnAt==null) continue;
				
				newEnAt.setBaseValue(enAt.getBaseValue());
				enAt.getModifiers().forEach(m -> newEnAt.addModifier(m));
			}

		}
		
	}
	
}
