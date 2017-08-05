package hu.trigary.stationaryentity;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		selectingPlayers = new HashMap<>();
		getCommand("stationaryentity").setExecutor(new Commands(this));
		getServer().getPluginManager().registerEvents(new Events(this), this);
	}
	
	private Map<UUID, String> selectingPlayers;
	
	
	
	public void setPlayerSelection(Player player, String command) {
		selectingPlayers.put(player.getUniqueId(), command);
	}
	
	public String takePlayerSelection(Player player) {
		return selectingPlayers.remove(player.getUniqueId());
	}
}
