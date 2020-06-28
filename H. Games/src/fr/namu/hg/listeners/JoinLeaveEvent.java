package fr.namu.hg.listeners;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;
import fr.namu.hg.enumhg.StateHG;
import fr.namu.hg.scoreboard.FastBoard;
import fr.namu.hg.scoreboard.Title;

public class JoinLeaveEvent implements Listener {

	private final MainHG main;
	
	public JoinLeaveEvent(MainHG main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		UUID puid = player.getUniqueId();
		
		this.main.GeneralUtils.addPlayer();
		
		FastBoard fastboard = new FastBoard(player);
	    fastboard.updateTitle("§l§3H.GAMES");
	    this.main.boards.put(player.getUniqueId(), fastboard);
	    Title.sendTabTitle(player, "§cH's §6Par§ety", " ", "§eVous jouez actuellement sur §aplay.h-party.fr", "§eNore site: §ahttps://www.h-party.fr/", "§eCe plugin a été créé par §bNamu", "§eDiscord: §ahttps://discord.gg/SHsq8gb");
	    this.main.score.updateBoard();
	    
	    if(this.main.isState(StateHG.LOBBY)) {
	    	  player.setMaxHealth(20.0D);
		      player.setHealth(20.0D);
		      player.setExp(0.0F);
		      player.setLevel(0);
		      player.getInventory().clear();
		      player.getInventory().setHelmet(null);
		      player.getInventory().setChestplate(null);
		      player.getInventory().setLeggings(null);
		      player.getInventory().setBoots(null);
		      this.main.LobbyUtils.getPlayerToLobby(player);
		      player.setGameMode(GameMode.ADVENTURE);
		      this.main.playerhg.put(puid, new PlayerHG());
		      player.setScoreboard(((PlayerHG)this.main.playerhg.get(player.getUniqueId())).getScoreBoard());
			  for (PotionEffect po : player.getActivePotionEffects())
			      player.removePotionEffect(po.getType()); 
			  player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2147483647, 0, false, false));
	    } 
	    if(!(this.main.isState(StateHG.LOBBY))) {
	    	  player.setMaxHealth(20.0D);
		      player.setHealth(20.0D);
		      player.setExp(0.0F);
		      player.setLevel(0);
		      player.getInventory().clear();
		      player.getInventory().setHelmet(null);
		      player.getInventory().setChestplate(null);
		      player.getInventory().setLeggings(null);
		      player.getInventory().setBoots(null);
		      player.teleport(player.getWorld().getSpawnLocation());
		      player.setGameMode(GameMode.SPECTATOR);
		      for (PotionEffect po : player.getActivePotionEffects())
			      player.removePotionEffect(po.getType()); 
			  player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2147483647, 0, false, false));
	    }
	    
	    event.setJoinMessage("§a+ §7» §e"+ playername);
	    //e.setJoinMessage("§a+ §7» §e"+ e.getPlayer().getName());
	}
	    
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		UUID puid = player.getUniqueId();
		
		if(this.main.GeneralUtils.GetNbPlayer() != 0) {
			this.main.GeneralUtils.removePlayer();
		}	
		if(this.main.playerhg.containsKey(puid)) {
			this.main.playerhg.remove(puid);
		}
		
		event.setQuitMessage("§c- §7» §e"+ playername);
	}
}
