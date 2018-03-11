package hu.trigary.stationaryentity;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
	private final Main main;
	
	public Events(Main main) {
		this.main = main;
	}
	
	
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		String command = main.takePlayerSelection(event.getPlayer());
		if (command == null) {
			return;
		}
		event.setCancelled(true);
		
		Entity entity = event.getRightClicked();
		for (Entity found : entity.getNearbyEntities(0, entity.getHeight(), 0)) {
			if (found.getType() == EntityType.ARMOR_STAND && found.isCustomNameVisible()) {
				found.remove();
				break;
			}
		}
		
		if (command.isEmpty()) {
			entity.remove();
			Utils.sendInfo(event.getPlayer(), "You have successfully deleted an entity.");
			return;
		}
		
		ArmorStand armorStand = (ArmorStand) entity.getWorld().spawnEntity(entity.getLocation().add(0, entity.getHeight(), 0), EntityType.ARMOR_STAND);
		armorStand.setInvulnerable(true);
		armorStand.setVisible(false);
		armorStand.setMarker(true);
		armorStand.setGravity(false);
		armorStand.setCustomNameVisible(true);
		armorStand.setCustomName(command);
		Utils.sendInfo(event.getPlayer(), "You have successfully named an entity.");
	}
	
	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent event) {
		main.takePlayerSelection(event.getPlayer());
	}
}
