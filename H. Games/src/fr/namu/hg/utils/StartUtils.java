package fr.namu.hg.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.ConfigurationHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.enumhg.StateHG;
import fr.namu.hg.runnable.IntergameRunnable;

public class StartUtils {

	private MainHG main;
	
	public List<GamesHG> gameConfigList = new ArrayList<>();

	public StartUtils (MainHG main) {
		this.main = main;
	}
	
	public void initiateConfigList() {	
		if(this.main.isConfig(ConfigurationHG.HOST)) {
			List<GamesHG> hostConfigList = Arrays.asList(GamesHG.values());
			for (Integer ind = 0; ind < hostConfigList.size(); ind++) {
				GamesHG game = hostConfigList.get(ind);
				if(game.getActive() == true) {
					gameConfigList.add(game);
				}
			}
		}
		if(this.main.isConfig(ConfigurationHG.CLASSIC)) {
			gameConfigList.add(GamesHG.PACMAN);
			gameConfigList.add(GamesHG.PIGRUN);
			gameConfigList.add(GamesHG.CTS);
			gameConfigList.add(GamesHG.RABBIT_RACE);
			gameConfigList.add(GamesHG.DUEL);
		}
	}
	
	public void startGame() {				
		this.initiateConfigList();
		Collections.shuffle(gameConfigList);
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < gameConfigList.size(); ind++) {
			GamesHG game = gameConfigList.get(ind);
			if(this.main.isGame(GamesHG.NULL)) {
				if(this.checkGame(game, GamesHG.PACMAN)) {
					this.main.pacman.initGame();
				}
				if(this.checkGame(game, GamesHG.PIGRUN)) {
					this.main.PigRun.initGame();			
				}
				if(this.checkGame(game, GamesHG.CTS) && players.size() <= 8) {
					this.main.CTS.initGame();
				}
				if(this.checkGame(game, GamesHG.RABBIT_RACE) && players.size() <= 8) {
					this.main.RR.initGame();
				}
				if(this.checkGame(game, GamesHG.DUEL)) {
					this.main.Duel.initGame();
				}
			}
		}
		
		if(!(this.main.isGame(GamesHG.NULL))) {
			this.main.setState(StateHG.GAME);
		} else {
			this.main.ScoreUtils.sendActionBarAll("§cErreur : Aucun jeu n'a été trouvé !");
		}
		
		
		
	}
	
	public void endGame() {
		/*String top1 = null;
		String top2 = null;
		String top3 = null;*/
		
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		/*for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			if(this.main.ScoreUtils.getPlace(player) == 1 && top1 == null) {
				player.getLocation().setX(-1.5);
				player.getLocation().setY(25);
				player.getLocation().setZ(-13.5);
			} else {
				if(this.main.ScoreUtils.getPlace(player) == 1 && top2 == null) {
					player.getLocation().setX(-4.5);
					player.getLocation().setY(24);
					player.getLocation().setZ(-13.5);
				} else {
					if(this.main.ScoreUtils.getPlace(player) == 1 && top3 == null) {
						player.getLocation().setX(1.5);
						player.getLocation().setY(23);
						player.getLocation().setZ(-13.5);
					}
				}
			}
			if(this.main.ScoreUtils.getPlace(player) == 2 && top2 == null) {
				player.getLocation().setX(-4.5);
				player.getLocation().setY(24);
				player.getLocation().setZ(-13.5);
			} else {
				if(this.main.ScoreUtils.getPlace(player) == 2 && top3 == null) {
					player.getLocation().setX(1.5);
					player.getLocation().setY(23);
					player.getLocation().setZ(-13.5);
				}
			}
			if(this.main.ScoreUtils.getPlace(player) == 3 && top3 == null) {
				player.getLocation().setX(1.5);
				player.getLocation().setY(23);
				player.getLocation().setZ(-13.5);
			}										
		}*/
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			this.main.LobbyUtils.getPlayerToLobby(player);
			player.setMaxHealth(20.0D);
		    player.setHealth(20.0D);
		    player.setExp(0.0F);
		    player.setLevel(0);
		    player.getInventory().clear();
		    player.getInventory().setHelmet(null);
		    player.getInventory().setChestplate(null);
		    player.getInventory().setLeggings(null);
		    player.getInventory().setBoots(null);
		    player.setCustomNameVisible(true);
		    player.setGameMode(GameMode.ADVENTURE);
		    for (PotionEffect po : player.getActivePotionEffects())
			    player.removePotionEffect(po.getType()); 
			player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2147483647, 0, false, false));
		}
		this.main.GeneralUtils.addGame();
		this.main.setGame(GamesHG.NULL);
		if(this.main.GeneralUtils.GetNbGame() != this.main.GeneralUtils.GetNbMaxGame()) {
			this.main.setState(StateHG.INTERGAME);
			IntergameRunnable startInter = new IntergameRunnable(this.main);
		    startInter.runTaskTimer((Plugin)this.main, 0L, 20L);
		} else {
			this.main.setState(StateHG.FIN);
			this.main.mjc.setLeaveRestricted(false);
			Bukkit.broadcastMessage("La Game est finie ! (Esthétique encore à développer)");
		}	
	}
	
	public Boolean checkGame(GamesHG indGame, GamesHG game) {
		if(indGame == game && game.getActive()) {
			this.main.setGame(game);
			game.setActive(Boolean.valueOf(false));
			return true;
		} else {
			return false;
		}
	}
	
}
