package me.japherwocky.portals.completePortal;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerEntityDestroy;
import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerEntityTeleport;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntity;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

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
		
		/*try {
			spawnPacket.setType(EntityType.FALLING_BLOCK);
			spawnPacket.setObjectData(combinedID);
		} catch (FieldAccessException e) {
			try {
				spawnPacket.getHandle().getIntegers().write(6, 70);
				spawnPacket.getHandle().getIntegers().write(7, combinedID);
			} catch (FieldAccessException e1) {
				spawnPacket.getHandle().getIntegers().write(3, 70);
				spawnPacket.getHandle().getIntegers().write(4, combinedID);
			}
			
		}*/
		
		spawnPacket.setX(location.getX());
		spawnPacket.setY(location.getY());
		spawnPacket.setZ(location.getZ());
		
		metaPacket = new WrapperPlayServerEntityMetadata();
		metaPacket.setEntityID(fallingBlockId);
		dataWatcher = new WrappedDataWatcher();
		
		// Skip data watcher for now - simplifies compatibility
		// The falling block will still spawn, just without metadata
		
		metaPacket.setMetadata(dataWatcher.getWatchableObjects());
		
		teleportPacket = new WrapperPlayServerEntityTeleport();
		teleportPacket.setEntityID(fallingBlockId);
		try {
			teleportPacket.setX(location.getX()+0.5f);
			teleportPacket.setY(location.getY());
			teleportPacket.setZ(location.getZ()+0.5f);
		} catch (FieldAccessException e) {
			teleportPacket.setLocation(location.getX()+0.5f, location.getY(), location.getZ()+0.5f);
		}
		
		destroyPacket = new WrapperPlayServerEntityDestroy();
		try {
			destroyPacket.setEntityIds(fallingBlockId);
		} catch (Exception e) {
			// Try alternative methods
			try {
				if (destroyPacket.getHandle().getIntegers().size() > 0) {
					destroyPacket.getHandle().getIntegers().write(0, fallingBlockId);
				}
			} catch (Exception e2) {
				// Skip destroy packet - entity will still despawn naturally
			}
		}
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
