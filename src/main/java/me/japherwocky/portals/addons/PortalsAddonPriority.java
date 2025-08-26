package me.japherwocky.portals.addons;

public enum PortalsAddonPriority {
	
	HIGHEST(6),
	VERY_HIGH(5),
	HIGH(4),
	NORMAL(3),
	LOW(2),
	VERY_LOW(1),
	LOWEST(0);
	
	private final int priority;
	
	PortalsAddonPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}
}

