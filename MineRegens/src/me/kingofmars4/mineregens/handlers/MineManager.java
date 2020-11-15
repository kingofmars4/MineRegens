package me.kingofmars4.mineregens.handlers;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import me.kingofmars4.mineregens.files.FileMines;
import me.kingofmars4.mineregens.utils.U;
@SuppressWarnings("deprecation")
public class MineManager {
	
	private static MineManager dm;
	private MineManager(){}
	public static final List<Mine> Mines = new ArrayList<Mine>();
	
	
	public void loadMines() {
		if (!FileMines.get().isConfigurationSection("Mines")) { return; }
		for (String key : FileMines.get().getConfigurationSection("Mines").getKeys(true)) {
			if (!key.contains(".")) {
				createMine(key, U.deserializeLoc(FileMines.get().getString("Mines."+key+".location1")), U.deserializeLoc(FileMines.get().getString("Mines."+key+".location2")));
				
				if (FileMines.get().get("Mines."+key+".Blocks") != null) {
					getMine(key).setIDs(FileMines.get().getIntegerList("Mines."+key+".Blocks"));
				}
			}
		}
	}
	
	public static MineManager get() {
		if (dm == null)
            dm = new MineManager();
        return dm;
	}
	
	public Mine getMine(String name) {
		for (Mine m: Mines) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	public void createMine(String name, Location l1, Location l2) {
		Mines.add(new Mine(name, l1, l2));
		FileMines.get().set("Mines."+name+".location1", U.serializeLoc(l1));
		FileMines.get().set("Mines."+name+".location2", U.serializeLoc(l2));
		FileMines.save();
	}
	
	public boolean deleteMine(String name) {
		for (Mine m: Mines) {
			if (m.getName().equalsIgnoreCase(name)) {
				FileMines.get().set("Mines."+name, null);
				FileMines.save();
				Mines.remove(m);
				return true;
			}
		}
		return false;
	}
	
	public Mine getPlayerMine (Player p) {
		for (Mine m : Mines) {
			if (m.isIn(p.getLocation())) {
				return m;
			}
		}
		return null;
	}
	
	public boolean isInMine(Player p) {
		for (Mine m : Mines) {
			if (m.isIn(p.getLocation())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMine (String name) {
		for (Mine m : Mines) {
			if (m.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void addBlock (String name, int material) {
		for (Mine m : Mines) {
			if (m.getName().equalsIgnoreCase(name)) {
				m.getIDs().add(material);
				
				FileMines.get().set("Mines."+name+".Blocks", m.getIDs());
				FileMines.save();
			}
		}
	}
	
	public void removeBlock (String name, int material) {
		for (Mine m : Mines) {
			if (m.getName().equalsIgnoreCase(name)) {
				for (int i = 0; i<m.getIDs().size(); i++) {
					if (m.getIDs().get(i) == material) {
						m.getIDs().remove(i);
					}
				}
				FileMines.get().set("Mines."+name+".Blocks", m.getIDs());
				FileMines.save();
			}
		}
	}
	
	public void saveAllBlocks() {
		Block b;
		for (Mine m : Mines) {
			m.getBlocks().clear();
			m.getLocations().clear();
			for (int i = 0; i<m.getIDs().size(); i++) {
				int x1, x2, z1, z2;
				if (m.getx1()>m.getx2()) { x1 = m.getx1(); x2 = m.getx2(); } else { x1 = m.getx2(); x2 = m.getx1(); }
				if (m.getz1()>m.getz2()) { z1 = m.getz1(); z2 = m.getz2(); } else { z1 = m.getz2(); z2 = m.getz1(); }
				
				for (int x=x2; x<=x1; x++) {
					for (int z=z2; z<=z1; z++) {
						for (int y = 0; y<260; y++) {
							b = m.getWorld().getBlockAt(x, y, z);
								if (b.getTypeId() == m.getIDs().get(i)) {
									m.getLocations().add(b.getLocation());
									m.getBlocks().put(b.getLocation(), b.getTypeId());
								}
						}
					}
				}
			}
		}
	}
	
	public void saveBlocks(Mine m) {
		Block b;
		m.getBlocks().clear();
		m.getLocations().clear();
			for (int i = 0; i<m.getIDs().size(); i++) {
				int x1, x2, z1, z2;
				if (m.getx1()>m.getx2()) { x1 = m.getx1(); x2 = m.getx2(); } else { x1 = m.getx2(); x2 = m.getx1(); }
				if (m.getz1()>m.getz2()) { z1 = m.getz1(); z2 = m.getz2(); } else { z1 = m.getz2(); z2 = m.getz1(); }
				
				for (int x=x2; x<=x1; x++) {
					for (int z=z2; z<=z1; z++) {
						for (int y = 0; y<260; y++) {
							b = m.getWorld().getBlockAt(x, y, z);
								if (b.getTypeId() == m.getIDs().get(i)) {
									m.getLocations().add(b.getLocation());
									m.getBlocks().put(b.getLocation(), b.getTypeId());
								}
						}
					}
				}
			}
	}
	
	public void resetAllMines() {
		Block b;
		for (Mine m : Mines) {
			for (Location l : m.getLocations()) {
				b = m.getWorld().getBlockAt(l);
				b.getState().update();
				b.setType(Material.getMaterial(m.getBlocks().get(l)));
				b.getState().update();
			}
		}
	}
	
	public void resetMines(Mine m) {
		Block b;
			for (Location l : m.getLocations()) {
				b = m.getWorld().getBlockAt(l);
				b.getState().update();
				b.setType(Material.getMaterial(m.getBlocks().get(l)));
				b.getState().update();
			}
	}
}
