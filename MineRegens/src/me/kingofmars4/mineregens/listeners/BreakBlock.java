package me.kingofmars4.mineregens.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import me.kingofmars4.mineregens.handlers.Mine;
import me.kingofmars4.mineregens.handlers.MineManager;
import me.kingofmars4.mineregens.utils.Messages;

public class BreakBlock implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (MineManager.get().isInMine(p.getLocation())) {
			Mine m = MineManager.get().getPlayerMine(p);
			if (!p.hasPermission("mineregens.breakblock")) {
				p.sendMessage(Messages.noPermToBreak.replaceAll("%m", m.getName()));
				e.setCancelled(true);
			}
		}
		
		if (MineManager.get().isInMine(e.getBlock().getLocation())) {
			Block b = e.getBlock();
			Mine m = MineManager.get().getMineFromLoc(b.getLocation());
			
			for (Location l : m.getLocations()) {
				if (l.equals(b.getLocation())) {
					e.setCancelled(true);
					b.setType(Material.BEDROCK);
					b.getState().update();
				}
			}
		}
	}

}
