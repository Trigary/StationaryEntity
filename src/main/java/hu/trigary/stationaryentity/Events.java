package hu.trigary.stationaryentity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
	Events (Main main) {
		this.main = main;
	}
	
	private final Main main;
	
	@SuppressWarnings ("unused")
	@EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteractEntity (PlayerInteractEntityEvent event) {
		String command = main.selectingPlayers.remove (event.getPlayer ().getUniqueId ());
		if (command != null) {
			Entity entity = event.getRightClicked ();
			if (main.entityTypes.values ().contains (entity.getType ())) {
				if (command.length () == 0) {
					entity.remove ();
					for (Entity found : entity.getNearbyEntities (0, Main.getEntityHeight (entity), 0)) {
						if (found.getType () == EntityType.ARMOR_STAND && found.isCustomNameVisible ()) {
							found.remove ();
							break;
						}
					}
					Commands.sendInfo (event.getPlayer (), "You have successfully deleted an entity.");
				} else {
					for (Entity found : entity.getNearbyEntities (0, Main.getEntityHeight (entity), 0)) {
						if (found.getType () == EntityType.ARMOR_STAND && found.isCustomNameVisible ()) {
							found.remove ();
							break;
						}
					}
					Main.spawnNamedArmorStand (entity.getLocation (), Main.getEntityHeight (entity), command);
					Commands.sendInfo (event.getPlayer (), "You have successfully renamed an entity.");
				}
				event.setCancelled (true);
			} else {
				Commands.sendError (event.getPlayer (), "That entity type is not supported!");
			}
		}
	}
	
	@SuppressWarnings ("unused")
	@EventHandler
	public void onPlayerQuit (PlayerQuitEvent event) {
		main.selectingPlayers.remove (event.getPlayer ().getUniqueId ());
	}
}
