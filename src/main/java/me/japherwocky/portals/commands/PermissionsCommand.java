package me.japherwocky.portals.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.japherwocky.portals.Portals;
import me.japherwocky.portals.PortalsUtils;

public class PermissionsCommand extends PortalsCommand {

	private float commandsPerPage = 5;
	
	public PermissionsCommand(String command, String args, String[] aliases, String description, String permission, boolean adminCommand) {
		super(command,args,aliases,description, permission, adminCommand);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		String head = "§7[§cPortals§7] Commands list:";
		int page = 0;
		if (args.length>1 && PortalsUtils.isInt(args[1]) && !args[1].equals("0")) page = Integer.parseInt(args[1])-1;
		ArrayList<PortalsCommand> commandList = Portals.getCommandManager().getCommands();
		for (int i =(int) Math.max(page*commandsPerPage, 0);i<Math.min(page*commandsPerPage+(commandList.size()-commandsPerPage*page), commandsPerPage*(1+page));i++) {
			PortalsCommand cmd = (PortalsCommand) commandList.toArray()[i];
        	head += "\n/portals "+cmd.getCommand()+" "+cmd.getArgs()+" §c-§7 "+cmd.getPermission();
    	}
		if (Math.min(commandList.size()-(1+page)*commandsPerPage, commandsPerPage*(2+page)) > 0 || page!=0)
			head += "\n\n**Page "+(page+1)+"/"+((int) Math.ceil(commandList.size()/commandsPerPage))+"**";
		
		sender.sendMessage(head);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		ArrayList<String> res = new ArrayList<String>();

		if (args.length!=2) return res;
		
		for (int i =1 ;i<=((int) Math.ceil(Portals.getCommandManager().getCommands().size()/commandsPerPage));i++)
			res.add(i+"");
		
		return res;
	}
	
}
