package fr.namu.hg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.games.pacman.PacEnum;

public class PacManListener implements Listener {

	private MainHG main;
	
	public PacManListener(MainHG main) {
		this.main = main;
	}
	
	@EventHandler
	public void onMenuClick(InventoryClickEvent event) {
	    event.getWhoClicked();
	    ItemStack current = event.getCurrentItem();
	    if(event.getInventory().getType().equals(InventoryType.PLAYER) && this.main.isGame(GamesHG.PACMAN) && current != null){
	    	event.setCancelled(true);	    	
	    }
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		if(this.main.PacManUtils.isPacState(PacEnum.START) || this.main.PacManUtils.isPacState(PacEnum.END)) {
			event.setCancelled(true);
		}
		
		if(event.getCause() == DamageCause.ENTITY_ATTACK)
			return;
		
		if (entity instanceof Player) {
			if (this.main.isGame(GamesHG.PACMAN)) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getClickedBlock()!= null && event.getClickedBlock().getType() == Material.SKULL && this.main.pacman.isPacMan(event.getPlayer()) && this.main.PacManUtils.isPacState(PacEnum.CHERRYLESS)) {
			this.main.PacManUtils.cherryActivate();
			event.getClickedBlock().setType(Material.AIR);
		}
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent event) {
		Entity entityHit = event.getEntity();
		Entity entityDamager = event.getDamager();
		
		if(!(entityHit instanceof Player) || !(entityDamager instanceof Player))
			return;
		
		Player hit = (Player)entityHit;
		Player damager = (Player)entityDamager;
		if(this.main.score.getTimer() >= 10) {
			event.setCancelled(true);
		}
		if(this.main.PacManUtils.isPacState(PacEnum.CHERRYLESS)) {
			if(this.main.pacman.isGhost(hit)) {
				event.setCancelled(true);
			} else {
				event.setDamage(1.0D);
				if(hit.getHealth() <= event.getFinalDamage()) {
					this.main.PacManUtils.pacDeath();
					this.main.ScoreUtils.earnSD(damager, 10, "Vie de Pac-Man enlevée");
					event.setCancelled(true);
				}
			}
		}
		if(this.main.PacManUtils.isPacState(PacEnum.CHERRYFULL)) {
			if(this.main.pacman.isGhost(hit) && !this.main.pacman.isGhost(damager)) {
				this.main.PacManUtils.ghostDeath(hit);
				event.setCancelled(true);
			} else {
				event.setCancelled(true);
			}
		}
	}
	
}
