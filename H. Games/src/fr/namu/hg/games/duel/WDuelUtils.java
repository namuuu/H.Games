package fr.namu.hg.games.duel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;

public class WDuelUtils {
	
	private MainHG main;
	private Random random = new Random();
	private int FinishTimer = -1;
	
	private List<Location> middle = new ArrayList<>();
	private List<UUID> finishedRound = new ArrayList<>();

	public WDuelUtils(MainHG main) {
		this.main = main;
	}
	
	public void setLocationList() {
		middle.add(new Location(Bukkit.getWorld("world"), 3, 32, 12));
	}
	
	public void teleportAllPlayers() {
		finishedRound.clear();
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		Collections.shuffle(players);
		Integer middleNb = 0;
		for(Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			PlayerHG phg = this.main.playerhg.get(players.get(ind));
			player.getInventory().clear();
			player.getInventory().setItem(0, this.main.ItemUtils.metaExtra(Material.WOOD_HOE, "§ePistolet", 1, null));
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));		
			player.setFoodLevel(20);
			player.setGameMode(GameMode.ADVENTURE);
			if(this.main.Duel.getRound() == 1) {
				phg.setStoredValue1(0);
				phg.setStoredValue2(0);
			}			
		}
		
		for(Integer ind = 0; ind < players.size(); ind++) {
			Location place = middle.get(middleNb);
			Player player1 = Bukkit.getPlayer(players.get(ind));
			player1.teleport(new Location(Bukkit.getWorld("world"), place.getBlockX() - 16 + this.random.nextInt(32), place.getBlockY(), place.getBlockZ() - 16 + this.random.nextInt(12), 0, 0));
			
			ind++;
			
			if(ind < players.size()) {
				Player player2 = Bukkit.getPlayer(players.get(ind));
				player2.teleport(new Location(Bukkit.getWorld("world"), place.getBlockX() - 16 + this.random.nextInt(32), place.getBlockY(), place.getBlockZ() + 16 - this.random.nextInt(12), 180, 0));
			} else {
				playerTie(player1);
			}
			
			middleNb++;
		}
	}
	
	public void playerWin(Player player) {
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		this.main.ScoreUtils.earnSD(player, 10, "Round Gagné");
		phg.setStoredValue1(phg.getStoredValue1() + 1);
		finishedRound.add(player.getUniqueId());
		this.checkRound();
	}
	
	public void playerLose(Player player) {
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		this.main.ScoreUtils.earnSD(player, 5, "Round Perdu");
		phg.setStoredValue2(phg.getStoredValue2() + 1);
		player.setGameMode(GameMode.SPECTATOR);
		finishedRound.add(player.getUniqueId());
		this.checkRound();
	}
	
	public void playerTie(Player player) {
		this.main.ScoreUtils.earnSD(player, 7, "Aucun adversaire");
		finishedRound.add(player.getUniqueId());
		this.checkRound();
	}
	
	public void checkRound() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		if (players.size() == finishedRound.size()) {
			System.out.println("[HGames] Round fini.");
			this.FinishTimer = 3;
		} else {
			System.out.println("[HGames] Joueurs ayant fini : [" + finishedRound.size() + "/" + players.size() + "]");
		}
	}
	
	public void decFinishTimer() {
		if(this.FinishTimer >= 0) {
			this.FinishTimer--;
		}
		if(this.FinishTimer == 0) {
			if(this.main.Duel.getRound() == this.main.Duel.getMaxRound()) {
				this.main.StartUtils.endGame();
			} else {
				teleportAllPlayers();
				this.main.Duel.addRound();
				this.main.Duel.resetGunTimer();
			}
			
		}
	}

}
