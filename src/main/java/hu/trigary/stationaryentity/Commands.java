package hu.trigary.stationaryentity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Commands implements CommandExecutor {
	Commands (Main main) {
		this.main = main;
	}
	
	private final Main main;
	
	@Override
	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) { //TODO slimes are random
		if (sender.hasPermission ("stationaryentity")) {
			if (args.length != 0) {
				if (sender instanceof Player) {
					Player player = (Player)sender;
					switch (args[0]) {
						case "spawn":
							return commandSpawn (player, args);
						case "delete":
							return commandDelete (player, args);
						case "clear":
							return commandClear (player, args);
						case "rename":
							return commandRename (player, args);
						case "stop":
							return commandStop (player, args);
						default:
							return false;
					}
				} else {
					sendError (sender, "Only players can use these commands!");
					return true;
				}
			} else {
				return false;
			}
		} else {
			sendError (sender, "You do not have permission to use this command!");
			return true;
		}
	}
	
	
	
	private boolean commandSpawn (Player player, String[] args) {
		if (args.length == 1) {
			return false;
		}
		
		String[] split = args[1].split (";");
		EntityType type = main.entityTypes.get (split[0].toLowerCase ());
		if (type == null) {
			sendError (player, "You have specified an incorrect or unsupported entity type! Please choose from these: " + getEntityTypes ());
			return true;
		}
		if (split.length > 2) {
			sendError (player, "Incorrect NBT format! Only use one \";\" and only to separate the entity type from the NBT tags!");
			return true;
		}
		
		Location location = player.getLocation ();
		String rotation = "Rotation:" + getRotation (location);
		String NBT;
		if (split.length == 2) {
			NBT = split[1];
		} else {
			NBT = "Invulnerable:1,Silent:1";
		}
		
		Main.executeCommand ("summon " + split[0].toLowerCase () + " " + Main.getLocation (location) + " {NoAI:1," + rotation + "," + NBT + "}");
		List<Entity> entities = player.getNearbyEntities (0, 0, 0);
		for (Entity entity : entities) {
			if (entity.getType () == type) {
				if (entity instanceof LivingEntity) {
					((LivingEntity)entity).setRemoveWhenFarAway (false);
					break;
				}
			}
		}
		
		if (args.length > 2) {
			String name = args[2];
			for (int i = 3; i < args.length; i++) {
				name += " " + args[i];
			}
			name = ChatColor.translateAlternateColorCodes ('&', name);
			Main.spawnNamedArmorStand (player.getLocation (), Main.getEntityHeight (type), name);
		}
		
		sendInfo (player, "You have successfully created an entity!");
		return true;
	}
	
	private boolean commandDelete (Player player, String[] args) {
		switch (args.length) {
			case 1:
				main.selectingPlayers.put (player.getUniqueId (), "");
				sendInfo (player, "Right click on an entity to safely delete it or type \"/ste stop\" to exit the selecting mode.");
				return true;
			default:
				return false;
		}
	}
	
	private boolean commandClear (Player player, String[] args) {
		int radius = 1;
		switch (args.length) {
			case 2:
				if (args[1].matches ("[1-9][0-9]*")) {
					radius = Integer.valueOf (args[1]);
				} else {
					sendError (player, "You have specified an incorrect radius!");
					return true;
				}
			case 1:
				int counter = 0;
				for (Entity entity : player.getWorld ().getEntities ()) {
					if (entity.getType () == EntityType.ARMOR_STAND) {
						if (entity.isCustomNameVisible () && entity.getLocation ().distance (player.getLocation ()) <= radius) {
							counter++;
							entity.remove ();
						}
					}
				}
				sendInfo (player, counter + " named armor stands have been removed.");
				return true;
			default:
				return false;
		}
	}
	
	private boolean commandRename (Player player, String[] args) {
		if (args.length == 1) {
			return false;
		}
		
		String name = args[1];
		for (int i = 2; i < args.length; i++) {
			name += " " + args[i];
		}
		name = ChatColor.translateAlternateColorCodes ('&', name);
		
		main.selectingPlayers.put (player.getUniqueId (), name);
		sendInfo (player, "Right click on an entity to rename it or type \"/ste stop\" to exit the selecting mode.");
		return true;
	}
	
	private boolean commandStop (Player player, String[] args) {
		if (args.length == 1) {
			if (main.selectingPlayers.remove (player.getUniqueId ()) != null) {
				sendInfo (player, "You successfully exited from the selecting mode.");
			} else {
				sendError (player, "You are not in any kind of selecting mode!");
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	
	private String getEntityTypes () {
		String out = "";
		for (String key : main.entityTypes.keySet ()) {
			out += key + ", ";
		}
		return out.substring (0, out.length () - 2);
	}
	
	private String getRotation (Location location) {
		return "[" + location.getYaw () + "f," + location.getPitch () + "f]";
	}
	
	static void sendError (CommandSender player, String message) {
		player.sendMessage (Main.PREFIX + ChatColor.RED + message);
	}
	
	static void sendInfo (CommandSender player, String message) {
		player.sendMessage (Main.PREFIX + ChatColor.WHITE + message);
	}
}
