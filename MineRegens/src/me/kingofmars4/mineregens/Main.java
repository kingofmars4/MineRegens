package me.kingofmars4.mineregens;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.kingofmars4.mineregens.commands.ResetMines;
import me.kingofmars4.mineregens.commands.SaveBlocks;
import me.kingofmars4.mineregens.commands.SetPos;
import me.kingofmars4.mineregens.files.FileMines;
import me.kingofmars4.mineregens.handlers.MineBlocks;
import me.kingofmars4.mineregens.handlers.MineManager;
import me.kingofmars4.mineregens.listeners.BreakBlock;
import me.kingofmars4.mineregens.utils.Messages;
import me.kingofmars4.mineregens.utils.U;

public class Main extends JavaPlugin implements CommandExecutor {
	
	public static Plugin plugin; public static Plugin getPlugin(){return plugin;}
	public static HashMap<Player, Location> loc1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> loc2 = new HashMap<Player, Location>();
	
	public static String mineName;
	
    public void onEnable() {
    	plugin = this;
    	loadConfigs();
        loadCommands();
        loadListeners();
        MineManager.get().loadMines();
        MineManager.get().saveAllBlocks();
        resetMinesTimer();
    }
    
    @Override
    public void onDisable() {
    	MineManager.get().resetAllMines();
    }
    
    public void loadCommands() {
    	getCommand("createmine").setExecutor(this);
    	getCommand("deletemine").setExecutor(this);
    	getCommand("minesetpos").setExecutor(new SetPos());
    	getCommand("mineblocks").setExecutor(new MineBlocks());
    	getCommand("saveallmines").setExecutor(new SaveBlocks());
    	getCommand("savemine").setExecutor(new SaveBlocks());
    	getCommand("resetallmines").setExecutor(new ResetMines());
    	getCommand("resetmine").setExecutor(new ResetMines());
    }
    
    public void loadListeners() {
    	PluginManager pm = getServer().getPluginManager();
    	pm.registerEvents(new BreakBlock(), this);
    	pm.registerEvents(new MineBlocks(), this);
    }
    
    public void loadConfigs() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		FileMines.setup();
		FileMines.get().options().copyDefaults(true);
		FileMines.save();
		
		getLogger().info("Configuration files succefully loaded.");
	}
    
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		if (!(sender instanceof Player)) { sender.sendMessage(Messages.mustBePlayer); return true;}
		Player p = (Player) sender;
		if (!p.hasPermission("mineregens.staff")) { p.sendMessage(Messages.noPermission); return true;}
		
		if (cmd.getName().equalsIgnoreCase("createmine")) {
			if (args.length!= 1) { p.sendMessage(Messages.pluginPrefix+U.color("&cCorrect usage: &e/createmine (name)")); return true;}
			if (loc1.get(p) == null || loc2.get(p) == null) { p.sendMessage(Messages.pluginPrefix+U.color("&cYou must use &e'/minesetpos (1/2)' &cto set the mine locations!")); return true;}
			
			MineManager.get().createMine(args[0], loc1.get(p), loc2.get(p));
			
			p.sendMessage(Messages.pluginPrefix+U.color("&aSuccefully created a new mine &e'%n'&a!".replaceAll("%n", args[0])));
		}
		
		if (cmd.getName().equalsIgnoreCase("deletemine")) {
			if (args.length!= 1) { p.sendMessage(Messages.pluginPrefix+U.color("&cCorrect usage: &e/deletemine (name)")); return true;}
			
			if (MineManager.get().deleteMine(args[0])) {
				p.sendMessage(Messages.pluginPrefix+U.color("&aSuccefully deleted mine &e'%n'&a!".replaceAll("%n", args[0])));
			} else {
				p.sendMessage(Messages.pluginPrefix+U.color("&e'%n' &cis not a valid mine!".replaceAll("%n", args[0])));
			}
		}
		return true;
	}
    
    
    int delay = getConfig().getInt("Options.resetBlocksDelay");
    int timer = delay;
    @SuppressWarnings("deprecation")
	public void resetMinesTimer() {
    	getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
        {
            public void run()
            {
            	if (timer == 0) {
            		MineManager.get().resetAllMines();
    				timer = delay;
            	}
				timer--;
            }
        }
        , 20, 20);
    }
}