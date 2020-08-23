package fr.namu.hg.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.NameTagVisibility;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.enumhg.StateHG;
import fr.namu.hg.scoreboard.FastBoard;
import fr.namu.hg.scoreboard.Title;
import fr.redline.serverclient.event.AuthorisePlayerConnectedEvent;
import fr.redline.serverclient.event.AuthorisePlayerDisconnectedEvent;

public class JoinLeaveEvent implements Listener {

	private final MainHG main;
	
	public JoinLeaveEvent(MainHG main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerJoin(AuthorisePlayerConnectedEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		UUID puid = player.getUniqueId();
		World world = player.getWorld();	
		
		FastBoard fastboard = new FastBoard(player);
	    fastboard.updateTitle("§l§3H.GAMES");
	    this.main.boards.put(player.getUniqueId(), fastboard);
	    Title.sendTabTitle(player, "§cH's §6Par§ety", " ", "§eVous jouez actuellement sur §aplay.h-party.fr", "§eNore site: §ahttps://www.h-party.fr/", "§eCe plugin a été créé par §bNamu", "§eDiscord: §ahttps://discord.gg/SHsq8gb");
	    this.main.score.updateBoard();
	    
	    if(this.main.isState(StateHG.LOBBY) && !this.main.mjc.isSpectator(player.getUniqueId())) {
	    	this.main.GeneralUtils.addPlayer();
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
		      System.out.println(playername + " a bien été téléporté à " + world.getSpawnLocation().getBlockX() + " " + world.getSpawnLocation().getBlockY() + " " + world.getSpawnLocation().getBlockZ());
		      player.setGameMode(GameMode.ADVENTURE);
		      this.main.playerhg.put(puid, new PlayerHG());
		      player.setScoreboard(((PlayerHG)this.main.playerhg.get(player.getUniqueId())).getScoreBoard());
		      if(!Bukkit.getScoreboardManager().getMainScoreboard().getTeam("player").hasEntry(player.getName())) {
		    	  Bukkit.getScoreboardManager().getMainScoreboard().getTeam("player").addEntry(player.getName());
		      }	      
		      Bukkit.getScoreboardManager().getMainScoreboard().getTeam("player").setNameTagVisibility(NameTagVisibility.NEVER);

			  for (PotionEffect po : player.getActivePotionEffects())
			      player.removePotionEffect(po.getType()); 
			  player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2147483647, 0, false, false));
	    } 
	    if(!(this.main.isState(StateHG.LOBBY)) || this.main.mjc.isSpectator(player.getUniqueId())) {
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
		      this.main.mjc.setSpectator(player, Boolean.valueOf(true));
		      this.main.mjc.setLeaveRestrictedPlayer(player, Boolean.valueOf(false));
		      for (PotionEffect po : player.getActivePotionEffects())
			      player.removePotionEffect(po.getType()); 
			  player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2147483647, 0, false, false));
	    }
	    
	    this.main.LobbyUtils.handleAutoStart();
	    event.setJoinMessage("§a+ §7» §e"+ playername);
	}
	    
	
	
	@EventHandler
	public void onPlayerQuit(AuthorisePlayerDisconnectedEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		UUID puid = player.getUniqueId();
		
		if(this.main.GeneralUtils.GetNbPlayer() != 0) {
			this.main.GeneralUtils.removePlayer();
		}	
		if(this.main.isGame(GamesHG.PACMAN)) {
			this.main.PacManUtils.disconnectionHandle(player);
		} else if (this.main.isGame(GamesHG.PIGRUN)) {
			this.main.PigRunUtils.disconnectionHandle(player);
		} else if (this.main.isGame(GamesHG.CTS)) {
			
		}
		
		if(this.main.playerhg.containsKey(puid)) {
			this.main.playerhg.remove(puid);
		}
		
		
		event.setQuitMessage("§c- §7» §e"+ playername);
	}
}
