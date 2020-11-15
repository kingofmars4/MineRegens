package me.kingofmars4.mineregens.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;

public class Mine {
	
	private int x1, x2, z1, z2;
	private World world;
	private final String name;
	private List<Integer> ids = new ArrayList<Integer>();
	private List<Location> locations = new ArrayList<Location>();
	private HashMap<Location, Integer> blocks = new HashMap<Location, Integer>();
	
	public Mine (String name, Location l1, Location l2) {
		this.name = name;
		this.x1 = l1.getBlockX();
		this.x2 = l2.getBlockX();
		this.z1 = l1.getBlockZ();
		this.z2 = l2.getBlockZ();
		this.world = l1.getWorld();
	}
	
	public boolean isIn (Location l) {
		if (l.getX() > x1 && l.getX() < x2) {
			if (l.getZ() > z1 && l.getZ() < z2) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Integer> getIDs() {
		return this.ids;
	}
	
	public void setIDs(List<Integer> ids) {
		this.ids = ids;
	}

	public HashMap<Location, Integer> getBlocks() {
		return blocks;
	}
	
	public int getx1 () {
		return this.x1;
	}
	
	public int getx2 () {
		return this.x2;
	}
	
	public int getz1 () {
		return this.z1;
	}
	
	public int getz2 () {
		return this.z2;
	}

	public World getWorld() {
		return world;
	}

	public List<Location> getLocations() {
		return locations;
	}

}
