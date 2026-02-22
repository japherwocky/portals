package me.japherwocky.portals.completePortal;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerEntityDestroy;
import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerEntityTeleport;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntity;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import me.japherwocky.portals.PortalsDebbuger;

/**
 * The PortalEntity that sends players packets of spawning falling sand with textures of blocks
 *
 */

@SuppressWarnings({"deprecation", "removal"})
public class PortalEntitySand extends PortalEntity {

	private int fallingBlockId;
	
	private WrapperPlayServerSpawnEntity spawnPacket;
	private WrapperPlayServerEntityTeleport teleportPacket;
	private WrapperPlayServerEntityMetadata metaPacket;
	private WrappedDataWatcher dataWatcher;
	private WrapperPlayServerEntityDestroy destroyPacket;
	
	/**
	 * Construct the PortalEntity and create all the packets to summon, retexture, teleport and destroy the falling block
	 * @param location the location to summon the entity
	 * @param combinedID the combinedID of the texture
	 */
	public PortalEntitySand(Location location, int combinedID) {
		super(location);
		fallingBlockId =  (int) (Math.random() * Integer.MAX_VALUE);
		
	
		spawnPacket = new WrapperPlayServerSpawnEntity();
		
		spawnPacket.setEntityID(fallingBlockId);
		spawnPacket.setUniqueId(UUID.randomUUID());

		spawnPacket.setTypeFallingBlock(combinedID);
		
		spawnPacket.setX(location.getX());
		spawnPacket.setY(location.getY());
		spawnPacket.setZ(location.getZ());
		
		metaPacket = new WrapperPlayServerEntityMetadata();
		metaPacket.setEntityID(fallingBlockId);
		dataWatcher = new WrappedDataWatcher();
		
		// Skip data watcher for now - simplifies compatibility
		// The falling block will still spawn, just without metadata
		
		metaPacket.setMetadata(dataWatcher.getWatchableObjects());
		
		initializeTeleportPacket(location);
		initializeDestroyPacket();
	}
	
	/**
	 * Initialize teleport packet with fallback handling
	 */
	private void initializeTeleportPacket(Location location) {
		teleportPacket = new WrapperPlayServerEntityTeleport();
		teleportPacket.setEntityID(fallingBlockId);
		
		boolean success = setTeleportField("setX", location.getX() + 0.5f)
			&& setTeleportField("setY", location.getY())
			&& setTeleportField("setZ", location.getZ() + 0.5f);
		
		if (!success) {
			// Fallback to setLocation if setters aren't available
			teleportPacket.setLocation(location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f);
		}
	}
	
	/**
	 * Set a field on the teleport packet using reflection
	 */
	private boolean setTeleportField(String methodName, double value) {
		try {
			teleportPacket.getClass().getMethod(methodName, double.class).invoke(teleportPacket, value);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		} catch (Exception e) {
			PortalsDebbuger.MEDIUM.print("Failed to set teleport field: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Initialize destroy packet with fallback handling
	 */
	private void initializeDestroyPacket() {
		destroyPacket = new WrapperPlayServerEntityDestroy();
		
		boolean success = setDestroyEntityIds();
		
		if (!success) {
			// Entity will despawn naturally - that's fine
			PortalsDebbuger.MEDIUM.print("Destroy packet not fully supported - entity will despawn naturally");
		}
	}
	
	/**
	 * Set entity IDs on destroy packet
	 */
	private boolean setDestroyEntityIds() {
		try {
			destroyPacket.setEntityIds(fallingBlockId);
			return true;
		} catch (Exception e) {
			// Try direct field access as fallback
			return setDestroyFieldDirect();
		}
	}
	
	/**
	 * Try to set destroy field directly via handle
	 */
	private boolean setDestroyFieldDirect() {
		try {
			if (destroyPacket.getHandle().getIntegers().size() > 0) {
				destroyPacket.getHandle().getIntegers().write(0, fallingBlockId);
				return true;
			}
		} catch (Exception e) {
			// Ignore - entity will despawn naturally
		}
		return false;
	}

	/**
	 * Send the spawn packets to the player
	 */
	public void summon(Player p) {
		spawnPacket.sendPacket(p);
		teleportPacket.sendPacket(p);
		metaPacket.sendPacket(p);
	}
	
	/**
	 * Send the destroy packets to the player
	 */
	public void destroy(Player p) {
		destroyPacket.sendPacket(p);
		
		p.sendBlockChange(getLocation(),getLocation().getBlock().getBlockData());
	}

	/**
	 * Send the destroy packets to all players
	 */
	
	public void destroyBroadcast() {
		destroyPacket.broadcastPacket();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendBlockChange(getLocation(),getLocation().getBlock().getBlockData());
		}
	}
	
}
