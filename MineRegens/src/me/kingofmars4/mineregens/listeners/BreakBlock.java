package me.kingofmars4.mineregens.listeners;

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
		if (MineManager.get().isInMine(p)) {
			Mine m = MineManager.get().getPlayerMine(p);
			if (!p.hasPermission("mineregens.breakblock")) {
				p.sendMessage(Messages.noPermToBreak.replaceAll("%m", m.getName()));
				e.setCancelled(true);
			}
		}
	}

}
