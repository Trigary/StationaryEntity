package hu.trigary.stationaryentity;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {
	private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "StationaryEntity" + ChatColor.GRAY + "] ";
	
	public static String argsToString(String[] args, int start) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < args.length; ) {
			builder.append(args[i++]);
			if (i < args.length) {
				builder.append(' ');
			} else {
				break;
			}
		}
		return builder.toString();
	}
	
	public static void sendInfo(CommandSender recipient, String message) {
		recipient.sendMessage(PREFIX + ChatColor.WHITE + message);
	}
	
	public static void sendError(CommandSender recipient, String error) {
		recipient.sendMessage(PREFIX + ChatColor.RED + error);
	}
}
