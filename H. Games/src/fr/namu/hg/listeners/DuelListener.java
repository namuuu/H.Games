package fr.namu.hg.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.games.duel.WDuelState;

public class DuelListener implements Listener {

private MainHG main;
	
	public DuelListener(MainHG main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		if(!this.main.isGame(GamesHG.DUEL)) {
			return;
		}
		if(player.getItemInHand() == null) {
			return;
		}
		if(!this.main.Duel.isState(WDuelState.GUNFIGHT)) {
			return;
		}
		
		for(Integer ind = 0; ind < players.size(); ind++) {
			Player playerLookedAt = Bukkit.getPlayer(players.get(ind));
			
			if(this.getLookingAt(player, playerLookedAt) && playerLookedAt.getGameMode() != GameMode.SPECTATOR) {
				Bukkit.broadcastMessage("§c" + playerLookedAt.getName() + " §es'est fait tirer dessus par §a" + player.getName());
				this.main.DuelUtils.playerWin(player);
				this.main.DuelUtils.playerLose(playerLookedAt);
			}
			
		}
	}
	
	@EventHandler
	public void onDropEvent(PlayerDropItemEvent event) {
		if(!this.main.isGame(GamesHG.DUEL)) {
			return;
		}
		event.setCancelled(true);
	}
	
	private boolean getLookingAt(Player player, Player player1) {
	    Location eye = player.getEyeLocation();
	    Vector toEntity = player1.getEyeLocation().toVector().subtract(eye.toVector());
	    double dot = toEntity.normalize().dot(eye.getDirection());
	   
	    return dot > 0.99D;
	  }
	
}
