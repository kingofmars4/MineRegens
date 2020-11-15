package me.kingofmars4.mineregens.files;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.kingofmars4.mineregens.Main;
import me.kingofmars4.mineregens.utils.U;

public class FileMines {
	private static File file;
	private static FileConfiguration fileMines;
	
	public static void setup () {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("MineRegens").getDataFolder(), "mines.yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				
			}
		}
		
		fileMines = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration get() {
		return fileMines;
	}
	
	public static void save() {
		try {
			fileMines.save(file);
		} catch (IOException e) {
			Main.getPlugin().getServer().getConsoleSender().sendMessage(U.color("&cIt was not possible to save mines.yml"));
		}
	}
	
	public static void reload() {
		fileMines = YamlConfiguration.loadConfiguration(file);
	}
}
