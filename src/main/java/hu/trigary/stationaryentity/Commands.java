package hu.trigary.stationaryentity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	private final Main main;
	
	public Commands(Main main) {
		this.main = main;
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("stationaryentity")) {
			Utils.sendError(sender, "You do not have permission to use this command!");
			return true;
		}
		
		if (args.length == 0) {
			return false;
		}
		
		if (!(sender instanceof Player)) {
			Utils.sendError(sender, "Only players can use these commands!");
			return true;
		}
		
		Player player = (Player) sender;
		switch (args[0].toLowerCase()) {
			case "spawn":
				if (args.length > 1) {
					commandSpawn(player, args);
					return true;
				}
				return false;
			case "name":
				if (args.length > 1) {
					commandName(player, args);
					return true;
				}
				return false;
			case "delete":
				commandDelete(player);
				return true;
			case "clear":
				commandClear(player, args);
				return true;
			case "stop":
				commandStop(player);
				return true;
			default:
				return false;
		}
	}
	
	
	
	private void commandSpawn(Player player, String[] args) {
		if (args.length > 3) {
			Utils.sendError(player, "Incorrect NBT tag format! You must separate the NBT tags using commas, not spaces!");
			return;
		}
		
		Location location = player.getLocation();
		String rotation = "Rotation:[" + location.getYaw() + "f," + location.getPitch() + "f]";
		String NBT = "{PersistenceRequired:1,NoAI:1," + rotation + "," + (args.length == 2 ? "Invulnerable:1,Silent:1" : args[2]) + "}";
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon " + args[1].toLowerCase() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + NBT);
		Utils.sendInfo(player, "You have successfully spawned the entity!");
	}
	
	private void commandDelete(Player player) {
		main.setPlayerSelection(player, "");
		Utils.sendInfo(player, "Right click on an entity to safely delete it or type \"/ste stop\" to exit the selecting mode.");
	}
	
	private void commandClear(Player player, String[] args) {
		int radius;
		if (args.length > 1) {
			if (args[1].matches("[1-9][0-9]*")) {
				radius = Integer.valueOf(args[1]);
			} else {
				Utils.sendError(player, "You have specified an incorrect radius!");
				return;
			}
		} else {
			radius = 1;
		}
		
		int counter = 0;
		for (Entity entity : player.getWorld().getEntities()) {
			if (entity.getType() == EntityType.ARMOR_STAND) {
				if (entity.isCustomNameVisible() && entity.getLocation().distance(player.getLocation()) <= radius) {
					counter++;
					entity.remove();
				}
			}
		}
		
		Utils.sendInfo(player, counter + " named armor stands have been removed.");
	}
	
	private void commandName(Player player, String[] args) {
		main.setPlayerSelection(player, ChatColor.translateAlternateColorCodes('&', Utils.argsToString(args, 1)));
		Utils.sendInfo(player, "Right click on an entity to rename it or type \"/ste stop\" to exit the selecting mode.");
	}
	
	private void commandStop(Player player) {
		if (main.takePlayerSelection(player) != null) {
			Utils.sendInfo(player, "You successfully exited from the selecting mode.");
		} else {
			Utils.sendError(player, "You are not in any kind of selecting mode!");
		}
	}
}
