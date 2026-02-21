package me.japherwocky.portals.addons;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.japherwocky.portals.customportal.CustomPortal;

public abstract class PortalsAddonPlayerGUIAction {

	public abstract ItemStack getItemStack();
	
	public abstract boolean execute(Player player, CustomPortal selectedPortal);

}
