package me.japherwocky.portals.completePortal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.block.data.BlockData;

/**
 * The PortalEntity that spawns actual falling block entities for portal visuals
 * Uses modern Bukkit API instead of raw packets
 */
public class PortalEntitySand extends PortalEntity {

	private FallingBlock fallingBlock;
	
	/**
	 * Construct the PortalEntity to display a falling block with the given block data
	 * @param location the location to summon the entity
	 * @param blockData the block data to display
	 */
	public PortalEntitySand(Location location, BlockData blockData) {
		super(location);
		
		// Center the falling block in the block position (add 0.5 to X and Z)
		Location spawnLocation = location.clone().add(0.5, 0, 0.5);
		
		// Spawn an actual falling block entity using Bukkit API
		// This handles all modern block state IDs automatically
		fallingBlock = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, blockData);
		
		if (fallingBlock != null) {
			fallingBlock.setDropItem(false);
			fallingBlock.setCancelDrop(true);
			fallingBlock.setGravity(false);
			fallingBlock.setInvulnerable(true);
		}
	}
	
	/**
	 * Check if the falling block is still valid, respawn if not
	 */
	public void ensureValid() {
		if (fallingBlock == null || fallingBlock.isDead()) {
			// Respawn the falling block
			Location spawnLocation = getLocation().clone().add(0.5, 0, 0.5);
			BlockData blockData = getLocation().getBlock().getBlockData();
			fallingBlock = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, blockData);
			if (fallingBlock != null) {
				fallingBlock.setDropItem(false);
				fallingBlock.setCancelDrop(true);
				fallingBlock.setGravity(false);
				fallingBlock.setInvulnerable(true);
			}
		}
	}
	
	/**
	 * Send nothing - the entity spawns automatically for all players
	 */
	public void summon(Player p) {
		// The falling block spawns naturally for all players when created
		// No packet sending needed
	}
	
	/**
	 * Remove the entity for this player
	 */
	public void destroy(Player p) {
		// Send block change to restore the actual block
		p.sendBlockChange(getLocation(), getLocation().getBlock().getBlockData());
	}

	/**
	 * Remove the entity for all players and broadcast block change
	 */
	public void destroyBroadcast() {
		if (fallingBlock != null && !fallingBlock.isDead()) {
			fallingBlock.remove();
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendBlockChange(getLocation(), getLocation().getBlock().getBlockData());
		}
	}
	
}
