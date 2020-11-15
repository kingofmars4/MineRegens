package me.kingofmars4.mineregens.handlers;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.kingofmars4.mineregens.Main;
import me.kingofmars4.mineregens.utils.GUIs;
import me.kingofmars4.mineregens.utils.Messages;
import me.kingofmars4.mineregens.utils.U;
@SuppressWarnings("deprecation")
public class MineBlocks implements CommandExecutor, Listener {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Messages.mustBePlayer); return true;}
		Player p = (Player) sender;
		if (!p.hasPermission("mineregens.staff")) { p.sendMessage(Messages.noPermission); return true;}
		if (args.length!= 1) { p.sendMessage(Messages.pluginPrefix+U.color("&cCorrect usage: &e/mineblocks (mine)")); return true;}
		if (!MineManager.get().isMine(args[0])) { p.sendMessage(Messages.mineDoesNotExist.replaceAll("%m", args[0])); return true;}
		
		Main.mineName = args[0];
		loadGui();
		p.openInventory(gui);
		
		
		return true;
	}
	
	
	
	private List<Player> waitingForID = new ArrayList<Player>();
	private static Inventory gui = Bukkit.createInventory(null, 27, U.color("&aAdd blocks to the mine!"));
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem(); 
		Inventory inventory = e.getInventory(); 
		
		if (!inventory.equals(gui)) { return; }
		if (clicked == null || clicked.getType().equals(Material.AIR)) { return; }
		e.setCancelled(true);
		
		if (clicked.getItemMeta().getDisplayName().equals( U.color("&aAdd new block"))) {
			p.closeInventory();
			p.sendMessage(Messages.pluginPrefix+U.color("&aDigit in the chat the ID of the block you wish to add!"));
			waitingForID.add(p);
		}
		
		if (clicked.getItemMeta().getLore().get(0).equalsIgnoreCase(U.color("&cLeft-click to remove the block!"))) {
			MineManager.get().removeBlock(Main.mineName, clicked.getTypeId());
			p.sendMessage(Messages.pluginPrefix+U.color("&cRemoved &e'%b' &cfrom the list of blocks!".replace("%b", clicked.getType().toString())));
			loadGui();
		}
	}
	
	public void loadGui() {
		Mine m = MineManager.get().getMine(Main.mineName);
		gui.clear();
		
		GUIs.createDisplay(new ItemStack(Material.EMERALD_BLOCK), gui, 22, U.color("&aAdd new block"), null);
		if (m.getIDs().isEmpty() ) { return; }
		for (int i = 0; i<m.getIDs().size(); i++) {
			GUIs.createDisplay(new ItemStack(Material.getMaterial(m.getIDs().get(i))), gui, i, U.color("&a")+Material.getMaterial(m.getIDs().get(i)).name(), U.color("&cLeft-click to remove the block!"));
		}
	}
	
	public Material newBlock;
	public int blockID;
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (waitingForID.contains(e.getPlayer())) {
			e.setCancelled(true);
			Player p = e.getPlayer();
			if (!U.isInt(e.getMessage())) { p.sendMessage(Messages.pluginPrefix+U.color("&cYou must enter a valid block ID!")); return;}
			blockID = Integer.parseInt(e.getMessage());
			newBlock = Material.getMaterial(blockID);
			if (!newBlock.isBlock()) { p.sendMessage(Messages.pluginPrefix+U.color("&cYou must enter a valid block ID!")); return;}
			
			loadGui();
			
			MineManager.get().addBlock(Main.mineName, blockID);
			
			waitingForID.remove(p);
			p.sendMessage(Messages.pluginPrefix+U.color("&aAdded &e'%b' &ato the list of blocks!".replace("%b", newBlock.name())));
			loadGui();
			p.openInventory(gui);
		}
	}

}
