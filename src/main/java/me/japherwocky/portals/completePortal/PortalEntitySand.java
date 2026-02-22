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
		fallingBlockId = (int) (Math.random() * Integer.MAX_VALUE);
		
		// Spawn packet - set type as falling block with the block state ID
		spawnPacket = new WrapperPlayServerSpawnEntity();
		spawnPacket.setEntityID(fallingBlockId);
		spawnPacket.setUniqueId(UUID.randomUUID());
		spawnPacket.setTypeFallingBlock(combinedID);
		spawnPacket.setX(location.getX());
		spawnPacket.setY(location.getY());
		spawnPacket.setZ(location.getZ());
		
		// Metadata packet - empty data watcher is fine
		metaPacket = new WrapperPlayServerEntityMetadata();
		metaPacket.setEntityID(fallingBlockId);
		dataWatcher = new WrappedDataWatcher();
		metaPacket.setMetadata(dataWatcher.getWatchableObjects());
		
		// Teleport packet - move entity to center of block
		teleportPacket = new WrapperPlayServerEntityTeleport();
		teleportPacket.setEntityID(fallingBlockId);
		teleportPacket.setLocation(location.getX() + 0.5f, location.getY(), location.getZ() + 0.5f);
		
		// Destroy packet - to remove the entity when portal breaks
		destroyPacket = new WrapperPlayServerEntityDestroy();
		destroyPacket.setEntityIds(fallingBlockId);
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
