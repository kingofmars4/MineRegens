package me.kingofmars4.mineregens.utils;

import org.bukkit.configuration.file.FileConfiguration;

import me.kingofmars4.mineregens.Main;

public class Messages {
	
	private static FileConfiguration f = Main.getPlugin().getConfig();
	
	public static String pluginPrefix = U.color(f.getString("Options.pluginPrefix"));
	public static String noPermission = U.color(pluginPrefix + f.getString("Messages.noPermission"));
	public static String mustBePlayer = pluginPrefix + U.color(f.getString("Messages.mustBePlayer"));
	public static String noPermToBreak = pluginPrefix + U.color(f.getString("Messages.noPermToBreak"));
	public static String mineDoesNotExist = pluginPrefix + U.color(f.getString("Messages.mineDoesNotExist"));
	
}
