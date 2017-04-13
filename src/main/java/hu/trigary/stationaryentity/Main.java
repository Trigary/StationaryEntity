package hu.trigary.stationaryentity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin { //TODO: better resouce icon (for both) (1.8: bat name: 1.8)
	@Override
	public void onEnable () {
		entityTypes = new HashMap<> ();
		entityTypes.put ("villager", EntityType.VILLAGER);
		entityTypes.put ("zombie", EntityType.ZOMBIE);
		entityTypes.put ("zombie_pigman", EntityType.PIG_ZOMBIE);
		entityTypes.put ("blaze", EntityType.BLAZE);
		entityTypes.put ("cave_spider", EntityType.CAVE_SPIDER);
		entityTypes.put ("chicken", EntityType.CHICKEN);
		entityTypes.put ("cow", EntityType.COW);
		entityTypes.put ("creeper", EntityType.CREEPER);
		entityTypes.put ("horse", EntityType.HORSE);
		entityTypes.put ("enderman", EntityType.ENDERMAN);
		entityTypes.put ("endermite", EntityType.ENDERMITE);
		entityTypes.put ("giant", EntityType.GIANT);
		entityTypes.put ("ghast", EntityType.GHAST);
		entityTypes.put ("guardian", EntityType.GUARDIAN);
		entityTypes.put ("villager_golem", EntityType.IRON_GOLEM);
		entityTypes.put ("snowman", EntityType.SNOWMAN);
		entityTypes.put ("magma_cuba", EntityType.MAGMA_CUBE);
		entityTypes.put ("slime", EntityType.SLIME);
		entityTypes.put ("mooshroom", EntityType.MUSHROOM_COW);
		entityTypes.put ("ocelot", EntityType.OCELOT);
		entityTypes.put ("wolf", EntityType.WOLF);
		entityTypes.put ("witch", EntityType.WITCH);
		entityTypes.put ("pig", EntityType.PIG);
		entityTypes.put ("rabbit", EntityType.RABBIT);
		entityTypes.put ("sheep", EntityType.SHEEP);
		entityTypes.put ("silverfish", EntityType.SILVERFISH);
		entityTypes.put ("skeleton", EntityType.SKELETON);
		entityTypes.put ("spider", EntityType.SPIDER);
		entityTypes.put ("squid", EntityType.SQUID);
		entityTypes.put ("bat", EntityType.BAT);
		entityTypes.put ("elder_guardian", EntityType.ELDER_GUARDIAN);
		entityTypes.put ("wither_skeleton", EntityType.WITHER_SKELETON);
		entityTypes.put ("stray", EntityType.STRAY);
		entityTypes.put ("husk", EntityType.HUSK);
		entityTypes.put ("zombie_villager", EntityType.ZOMBIE_VILLAGER);
		entityTypes.put ("evocation_illager", EntityType.EVOKER);
		entityTypes.put ("vex", EntityType.VEX);
		entityTypes.put ("vindication_illager", EntityType.VINDICATOR);
		entityTypes.put ("shulker", EntityType.SHULKER);
		entityTypes.put ("wither", EntityType.WITHER);
		entityTypes.put ("skeleton_horse", EntityType.SKELETON_HORSE);
		entityTypes.put ("zombie_horse", EntityType.ZOMBIE_HORSE);
		entityTypes.put ("llama", EntityType.LLAMA);
		entityTypes.put ("donkey", EntityType.DONKEY);
		entityTypes.put ("mule", EntityType.MULE);
		entityTypes.put ("polar_bear", EntityType.POLAR_BEAR);
		
		selectingPlayers = new HashMap<> ();
		
		getCommand ("stationaryentity").setExecutor (new Commands (this));
		getServer ().getPluginManager ().registerEvents (new Events (this), this);
	}
	
	Map<String, EntityType> entityTypes;
	Map<UUID, String> selectingPlayers;
	
	final static String PREFIX = ChatColor.GOLD + "[StationaryEntity] ";
	
	static boolean executeCommand (String command) {
		return Bukkit.dispatchCommand (Bukkit.getConsoleSender (), command);
	}
	
	static double getEntityHeight (Entity entity) {
		return entity.getHeight ();
	}
	
	static double getEntityHeight (EntityType type) { //TODO better way
		World world = Bukkit.getWorlds ().get (0);
		Entity entity = world.spawnEntity (new Location (world, 0, 0, 0), type);
		entity.remove ();
		return getEntityHeight (entity);
	}
	
	static void spawnNamedArmorStand (Location location, double height, String name) {
		executeCommand ("summon armor_stand " + getLocation (location.add (0, height, 0)) + " {Marker:1,Invisible:1,NoGravity:1,CustomNameVisible:1,CustomName:\"" + name + "\"}");
	}
	
	static String getLocation (Location location) {
		return location.getX () + " " + location.getY () + " " + location.getZ ();
	}
	
	//TODO
	//shulkers are buggy, same as slimes
}