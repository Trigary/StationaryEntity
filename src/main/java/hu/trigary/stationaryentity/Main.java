package hu.trigary.stationaryentity;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin {
	private final Map<UUID, String> selectingPlayers = new HashMap<>();
	
	@Override
	public void onEnable() {
		getCommand("stationaryentity").setExecutor(new Commands(this));
		getServer().getPluginManager().registerEvents(new Events(this), this);
	}
	
	
	
	public void setPlayerSelection(Player player, String command) {
		selectingPlayers.put(player.getUniqueId(), command);
	}
	
	public String takePlayerSelection(Player player) {
		return selectingPlayers.remove(player.getUniqueId());
	}
}
