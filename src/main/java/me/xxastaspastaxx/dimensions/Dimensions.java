package me.xxastaspastaxx.dimensions;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.addons.PortalsAddonManager;
import me.japherwocky.portals.builder.CreatePortalManager;
import me.japherwocky.portals.commands.PortalsCommandManager;
import me.japherwocky.portals.completePortal.CompletePortalManager;
import me.japherwocky.portals.customportal.CustomPortalManager;

/**
 * Compatibility class for the old Dimensions plugin
 * Delegates all calls to the new Portals class
 */
public class Dimensions {
    
    public static Portals getInstance() {
        return Portals.getInstance();
    }
    
    public static CompletePortalManager getCompletePortalManager() {
        return Portals.getCompletePortalManager();
    }

    public static CustomPortalManager getCustomPortalManager() {
        return Portals.getCustomPortalManager();
    }
    
    public static PortalsAddonManager getAddonManager() {
        return Portals.getAddonManager();
    }
    
    public static PortalsCommandManager getCommandManager() {
        return Portals.getCommandManager();
    }

    public static CreatePortalManager getCreatePortalManager() {
        return Portals.getCreatePortalManager();
    }
}

