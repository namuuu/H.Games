package fr.namu.hg.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class PigRunListener implements Listener {

	private MainHG main;
	
	public PigRunListener(MainHG main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		
			if (entity instanceof Player /*entity.getPassenger() instanceof Player*/) {
				if (this.main.isGame(GamesHG.PIGRUN)) {
					event.setCancelled(true);
				}
			}
		
		
	}
	
	@EventHandler
	public void onDismount(VehicleExitEvent event) {
		if (this.main.isGame(GamesHG.PIGRUN)) {
			event.setCancelled(true);
		}
	}
	
}
