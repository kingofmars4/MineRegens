package me.kingofmars4.mineregens.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.kingofmars4.mineregens.handlers.MineManager;
import me.kingofmars4.mineregens.utils.Messages;
import me.kingofmars4.mineregens.utils.U;

public class SaveBlocks implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		if (!(sender instanceof Player)) { sender.sendMessage(Messages.mustBePlayer); return true;}
		Player p = (Player) sender;
		if (!p.hasPermission("mineregens.staff")) { p.sendMessage(Messages.noPermission); return true;}
		
		if (cmd.getName().equalsIgnoreCase("saveallmines")) {
			MineManager.get().saveAllBlocks();
			p.sendMessage(Messages.pluginPrefix+U.color("&aSaved current blocks from all mines!"));
		}
		
		if (cmd.getName().equalsIgnoreCase("savemine")) {
			if (args.length!= 1) { p.sendMessage(Messages.pluginPrefix+U.color("&cCorrect usage: &e/savemine (name)")); return true;}
			if (!MineManager.get().isMine(args[0])) { p.sendMessage(Messages.mineDoesNotExist); return true; } else {
				MineManager.get().saveBlocks(MineManager.get().getMine(args[0]));
				p.sendMessage(Messages.pluginPrefix+U.color("&aSaved current blocks from mine &e%m&a!".replace("%m", args[0])));
			}
		}
		
		return true;
	}

}
