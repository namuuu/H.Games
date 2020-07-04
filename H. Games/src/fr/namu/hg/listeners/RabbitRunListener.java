package fr.namu.hg.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.scoreboard.Title;

public class RabbitRunListener implements Listener {

	private MainHG main;
	
	public RabbitRunListener(MainHG main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(!this.main.isGame(GamesHG.RABBIT_RACE)) {
			return;
		}
		
		Player player = event.getPlayer();
		Location ploc = player.getLocation();
		ItemStack current = player.getItemInHand();
		Material currentType = current.getType();
		
		if(currentType == Material.EMERALD) {
			this.main.RRUtils.advanceRabbit(player);
		} else if(currentType == Material.PAPER) {
			player.playSound(ploc, Sound.DIG_STONE, 20.0F, 1.0F);
			Title.sendActionBar(player, "§cTu dois cliquer sur l'émeraude !");
		} else {
			if(!this.main.RRUtils.runners.contains(player.getUniqueId()) && this.main.score.getTimer() >= 0) {
				this.main.RRUtils.emeraldReroll(player);
			}			
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		if(this.main.isGame(GamesHG.RABBIT_RACE)) {
			if(entity instanceof Player) {
				event.setCancelled(true);
			}
			if(entity instanceof Rabbit && event.getCause() == DamageCause.ENTITY_ATTACK) {
				event.setCancelled(true);
			}
		}
	}
}
