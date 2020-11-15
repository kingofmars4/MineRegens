package me.kingofmars4.mineregens.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.kingofmars4.mineregens.Main;
import me.kingofmars4.mineregens.utils.Messages;
import me.kingofmars4.mineregens.utils.U;

public class SetPos implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		if (!(sender instanceof Player)) { sender.sendMessage(Messages.mustBePlayer); return true;}
		Player p = (Player) sender;
		if (!p.hasPermission("mineregens.staff")) { p.sendMessage(Messages.noPermission); return true;}
		if (args.length!= 1 || !U.isInt(args[0])) { p.sendMessage(Messages.pluginPrefix+U.color("&cCorrect usage: &e/minesetpos (1/2)")); return true;}
		
		switch (args[0]) {
		case "1":
			Main.loc1.put(p, p.getLocation());
			p.sendMessage(Messages.pluginPrefix+U.color("&aYour current position has been selected as location &en1&a!"));
			break;
		case "2":
			Main.loc2.put(p, p.getLocation());
			p.sendMessage(Messages.pluginPrefix+U.color("&aYour current position has been selected as location &en2&a!"));
			break;
		default:
			p.sendMessage(Messages.pluginPrefix+U.color("&cNumber can only be 1 or 2!"));
		}
		return true;
	}

}
