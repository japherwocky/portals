package me.japherwocky.portals.gui.player;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.japherwocky.portals.Portals;

import me.japherwocky.portals.builder.CreatePortalInstance;
import me.japherwocky.portals.gui.CreatePortalGUI;
import me.japherwocky.portals.gui.PortalsGUIType;
import me.japherwocky.portals.gui.PortalsGUIUtils;

public class DimensionsPlayerPortalGUI extends CreatePortalGUI {
	
	private final float MAX_ITEMS_PER_PAGE = 20f;
	
	int page = 0;
	int maxPage = 0;
	
	public DimensionsPlayerPortalGUI(CreatePortalInstance instance) {
		super(instance, PortalsGUIType.PLAYER_PORTAL);
	}
	
	@Override
	public Inventory createInventory() {
		Inventory inv = Bukkit.createInventory(p, 54, "{{portal_displayName}}");

		for (int i=0;i<6;i++) {
			inv.setItem(4+(9*i), PortalsGUIUtils.BLACK_GLASS);
		}
		inv.setItem(50, PortalsGUIUtils.BLACK_GLASS);
		inv.setItem(53, PortalsGUIUtils.BLACK_GLASS);
		
		//Navigation
		inv.setItem(51, PortalsGUIUtils.createItem(Material.BIRCH_BOAT, "§7Previous page"));
		inv.setItem(52, PortalsGUIUtils.createItem(Material.BIRCH_BOAT, "§7Next page"));
		
		ItemStack closeItemStack = PortalsGUIUtils.createItem(Material.GREEN_STAINED_GLASS_PANE, "§aClose");
		inv.setItem(45, closeItemStack);
		inv.setItem(46, closeItemStack);
		inv.setItem(47, closeItemStack);
		inv.setItem(48, closeItemStack);
		
		return inv;
	}
	
	@Override
	public void open() {
		
		
		maxPage = 0;
		ItemStack[] contents = inventory.getContents();
		inventory = Bukkit.createInventory(p, inventory.getSize(), "§f"+instance.selectedPortal.getDisplayName());
		inventory.setContents(contents);
		
		for (int i=0;i<MAX_ITEMS_PER_PAGE;i++) {
			int itemIndex = (int) (MAX_ITEMS_PER_PAGE*page+i);
			int posIndex = (int) (5+((i/4)*4)+Math.floor(i/4)*5);
			if (true) {
				inventory.clear(posIndex);
			} else {
				
			}
		}
		
		ItemStack portalBlock = PortalsGUIUtils.createItem(instance.selectedPortal.getOutsideMaterial(), "§f"+instance.selectedPortal.getOutsideMaterial().name(), new String[] {"§7Build using this block."});
		for (int i=0;i<4;i++) {
			inventory.setItem(i, portalBlock);
			inventory.setItem(36+i, portalBlock);
		}
		for (int i=9;i<=27;i+=9) {
			inventory.setItem(i, portalBlock);
			inventory.setItem(i+3, portalBlock);
		}
		
		if (instance.selectedPortal.getLighterMaterial()==null) {
			inventory.setItem(29, PortalsGUIUtils.createItem(Material.BARRIER, "§f{{customItem}}", new String[] {"§7This should have been replaced by the responsible addon","§7Please make sure everything is setup correctly"}));
		} else {
			inventory.setItem(29, PortalsGUIUtils.createItem(instance.selectedPortal.getLighterMaterial(), "§f"+instance.selectedPortal.getLighterMaterial().name(), new String[] {"§7Ignite using this item."}));
		}
		
		inventory.getItem(51).setType(page==0?Material.MINECART:Material.CHEST_MINECART);
		inventory.getItem(52).setType(page==maxPage?Material.MINECART:Material.CHEST_MINECART);
		
		super.open();
	}

	@Override
	public void handleClick(int index, boolean rightClick, boolean shiftClick) {

		
		
		if (index>=5 && (index - 5) % 9 < 4 && index<=44) {
			int itemIndex = (index-18)+27*page;
			

			if (false) {
				p.closeInventory();
				Portals.getCreatePortalManager().clear(p);
			}
			
			return;
		}
		
		switch (index) {
		//Navigation
		case 51:
			page = Math.max(0, page-1);
			open();
			break;
		case 52:
			page = (int) Math.min(maxPage, page+1);
			open();
			break;

		case 45:
		case 46:
		case 47:
		case 48:
			instance.guiMap.get(PortalsGUIType.PLAYER_MAIN).open();
			break;
		}
	}

	@Override
	public boolean handleChat(String string) {
		return true;
	}

}

