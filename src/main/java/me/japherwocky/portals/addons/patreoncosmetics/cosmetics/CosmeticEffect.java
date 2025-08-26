package me.japherwocky.portals.addons.patreoncosmetics.cosmetics;

import org.bukkit.entity.Player;

import me.japherwocky.portals.completePortal.CompletePortal;

public enum CosmeticEffect {
	
	NOTHING,
	FINAL_SPARK, //use, destroy, ignite
	FILLING_THE_VOID, //tick, use, destroy, ignite
	HEART_SEEKER, //tick, use, destroy, ignite
	HUNGRY_HOUNDS, //tick, use, destroy, ignite
	GLOWING_AURA, //tick
	ANGRY_LLAMA,  //tick
	EXPLOSIONS, //destroy, use
	LIL_RING; //tick
	
	public CosmeticEffectManager manager = new CosmeticEffectManager();
	
	public void play(CompletePortal completePortal, Player player) {
		manager.play(completePortal, player, this);
		
	}
}
