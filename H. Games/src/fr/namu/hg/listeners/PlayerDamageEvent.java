package fr.namu.hg.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class PlayerDamageEvent implements Listener {

	private final MainHG main;
	
	public PlayerDamageEvent(MainHG main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		if (this.main.isGame(GamesHG.NULL)) {
			if(event.getRightClicked().getType() == EntityType.ARMOR_STAND || event.getRightClicked().getType() == EntityType.ITEM_FRAME) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		
		if (entity instanceof Player) {
			if (this.main.isGame(GamesHG.NULL)) {
				event.setCancelled(true);
			}
		} else if (entity instanceof ArmorStand) {
			if (this.main.isGame(GamesHG.NULL)) {
				event.setCancelled(true);
			}
		} else if (entity instanceof ItemFrame) {
			if (this.main.isGame(GamesHG.NULL)) {
				event.setCancelled(true);
			}
		}
	}
	
}
