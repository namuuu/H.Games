package fr.namu.hg.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class CTSListener implements Listener {
	
	private MainHG main;
	
	public CTSListener(MainHG main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(this.main.isGame(GamesHG.CTS) && event.getRightClicked().getType() == EntityType.SHEEP) {
			Sheep sheep = (Sheep) event.getRightClicked();
			if(sheep.getVehicle() == player) {
				launchSheep(player);
			} 
			if(sheep.getVehicle() != null || sheep.getPassenger() != null) {
				return;
			}
			if (player.getPassenger() == null) {
				player.setPassenger(sheep);
			} else if (player.getPassenger().getPassenger() == null) {
				player.getPassenger().setPassenger(sheep);
			} else if (player.getPassenger().getPassenger().getPassenger() == null) {
				player.getPassenger().getPassenger().setPassenger(sheep);
			} else {
				player.sendMessage("§cTu ne peux porter que trois moutons à la fois !");
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(this.main.isGame(GamesHG.CTS)) {
			if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if  (player.getPassenger() != null){
					Entity Passenger1 = player.getPassenger();
					if (player.getPassenger().getPassenger() != null) {
						Entity Passenger2 = player.getPassenger().getPassenger();
						if (player.getPassenger().getPassenger().getPassenger() != null) {
							Entity Passenger3 = player.getPassenger().getPassenger().getPassenger();
							Vector v = player.getLocation().getDirection();
							Passenger2.eject();
							Passenger3.setVelocity(v.multiply(1.5));
						}			
						Vector v = player.getLocation().getDirection();
						Passenger1.eject();
						Passenger2.setVelocity(v.multiply(1.5));
					}		
					Vector v = player.getLocation().getDirection();
					player.eject();
					Passenger1.setVelocity(v.multiply(1.5));
				}
					
			}
		}
	}
	
	@EventHandler
	public void EntityDamageEntity(EntityDamageByEntityEvent event) {
		if (!this.main.isGame(GamesHG.CTS)) {
			return;
		}
		Entity hit = event.getEntity();
		Entity damager = event.getDamager();
		
		if(hit instanceof Sheep && damager instanceof Player) {
			Player player = (Player)damager;
			
			if(hit.getPassenger() == player) {
				launchSheep(player);
			}
		}
	}
	
	public void launchSheep(Player player) {
		if  (player.getPassenger() != null){
			Entity Passenger1 = player.getPassenger();
			if (player.getPassenger().getPassenger() != null) {
				Entity Passenger2 = player.getPassenger().getPassenger();
				if (player.getPassenger().getPassenger().getPassenger() != null) {
					Entity Passenger3 = player.getPassenger().getPassenger().getPassenger();
					Vector v = player.getLocation().getDirection();
					Passenger2.eject();
					Passenger3.setVelocity(v.multiply(1.5));
				}			
				Vector v = player.getLocation().getDirection();
				Passenger1.eject();
				Passenger2.setVelocity(v.multiply(1.5));
			}		
			Vector v = player.getLocation().getDirection();
			player.eject();
			Passenger1.setVelocity(v.multiply(1.5));
		}
	}

}

