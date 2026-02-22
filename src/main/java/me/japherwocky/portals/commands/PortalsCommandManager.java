package me.japherwocky.portals.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.japherwocky.portals.Portals;

/**
 * Class to register and manage the Portals commands
 *
 */

public class PortalsCommandManager {

    private ArrayList<PortalsCommand> commands = new ArrayList<PortalsCommand>();
    
    /**
     * Construct the manager and register all subcommands
     * @param main the instance of the Portals plugin
     */
    public PortalsCommandManager(Portals main) {

        // Register subcommands
        registerCommand(main, new HelpCommand("help", "", new String[] {"h"}, "List all commands", "none", false));
        registerCommand(main, new InfoCommand("info", "", new String[0], "Info about the plugin", "none", false));
        registerCommand(main, new AdminHelpCommand("adminHelp", "", new String[] {"ah"}, "List all admin commands", "", false));
        registerCommand(main, new PermissionsCommand("permissions", "", new String[] {"perms"}, "List all commands with their permissions", "", true));
        registerCommand(main, new ReloadCommand("reload", "", new String[0], "Reload all configurations and addons", "", true));
        registerCommand(main, new WorldsCommand("worlds", "", new String[0], "List world names to be used in config", "", true));
        registerCommand(main, new AdminPermissionsCommand("adminPermissions", "", new String[] {"aperms"}, "List all admin commands with their permissions", "", true));
        registerCommand(main, new ClearCommand("clear", "<all/world/portal>", new String[] {"clr"}, "Delete all saved portals.", "", true));
        registerCommand(main, new PortalCommand("portal", "[portal]", new String[0], "Show info of specified portal or look at a portal", "", true));

    }

    /**
     * Register a subcommand with the server
     */
    private void registerCommand(Portals main, PortalsCommand cmd) {
        commands.add(cmd);
        String name = "portals-" + cmd.getCommand();
        
        org.bukkit.command.Command command = new org.bukkit.command.Command(
            name,
            cmd.getDescription(),
            "/" + name,
            java.util.Arrays.asList(cmd.getAliases())
        ) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                return handleCommand(sender, args);
            }
            @Override
            public List<String> tabComplete(CommandSender sender, String label, String[] args) {
                return handleTabComplete(sender, args);
            }
        };
        main.getServer().getCommandMap().register("portals", command);
    }

    /**
     * Handle command execution - delegates to the appropriate PortalsCommand
     */
    private boolean handleCommand(CommandSender sender, String[] args) {
        String subcommand = args.length > 0 ? args[0] : "help";
        
        for (PortalsCommand command : commands) {
            if (!command.isThisCommand(subcommand)) continue;
            if (!command.getPermission().contentEquals("none") && !sender.hasPermission(command.getPermission())) {
                sender.sendMessage("§7[§cPortals§7] §4You do not have permission to execute this command");
                return true;
            }
            try {
                command.execute(sender, args);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Handle tab completion - delegates to the appropriate PortalsCommand
     */
    private List<String> handleTabComplete(CommandSender sender, String[] args) {
        String subcommand = args.length > 0 ? args[0] : "";
        
        for (PortalsCommand command : commands) {
            if (!command.isThisCommand(subcommand)) continue;
            if (!command.getPermission().contentEquals("none") && !sender.hasPermission(command.getPermission())) {
                return new ArrayList<String>();
            }
            try {
                return command.onTabComplete(sender, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Return list of available subcommands for first argument
        if (args.length == 1) {
            ArrayList<String> res = new ArrayList<String>();
            commands.stream()
                .filter(c -> c.getPermission().contentEquals("none") || sender.hasPermission(c.getPermission()))
                .forEach(c -> res.add(c.getCommand()));
            return res;
        }
        
        return new ArrayList<String>();
    }
    
    /**
	 * Get the list of the non-admin commands
	 * @return the list of the non-admin commands
	 */
	public ArrayList<PortalsCommand> getCommands() {
		ArrayList<PortalsCommand> res = new ArrayList<PortalsCommand>();
		for (PortalsCommand cmd : commands) {
			if (cmd.isAdminCommand()) continue;
			res.add(cmd);
		}
		return res;
	}
	
	/**
	 * Get the list of the admin commands
	 * @return the list of the admin commands
	 */
	public ArrayList<PortalsCommand> getAdminCommands() {
		ArrayList<PortalsCommand> res = new ArrayList<PortalsCommand>();
		for (PortalsCommand cmd : commands) {
			if (!cmd.isAdminCommand()) continue;
			res.add(cmd);
		}
		return res;
	}
	
	/**
	 * Register a new PortalsCommand
	 * @param cmd the command instance
	 */
	public void registerCommand(PortalsCommand cmd) {
		commands.add(cmd);
	}
	
	/**
	 * Unregister a command
	 * @param cmd the instance of the command
	 */
	public void unregisterCommand(PortalsCommand cmd) {
		commands.remove(cmd);
	}
	
	
	
}
