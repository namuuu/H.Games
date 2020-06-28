package fr.namu.hg.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.scoreboard.Title;

public class PigrunRunnable extends BukkitRunnable {
	
	private MainHG main;
	
	public PigrunRunnable(MainHG main) {
		this.main = main;
	}
	
	public void run() {
		this.main.score.updateBoard();
		
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			this.main.PigRunUtils.checkCheckPoint(player);
			
			if(this.main.score.getTimer() == -500) {
				Title.sendTitle(player, 5, 40, 5, "§eSois-le plus rapide !", "§7Atteins la ligne d'arrivée !");
				this.main.PigRunUtils.setupPiggy(player);
			}
			if(this.main.score.getTimer() == -400) {
				player.getInventory().setItem(1, this.main.ItemUtils.metaExtraUnbreakable(Material.CARROT_STICK, "§dArme Légendaire de Niveau 200", 1, null));
				player.getInventory().setItem(2, this.main.ItemUtils.metaExtraUnbreakable(Material.FISHING_ROD, "§ddFouet à cochons", 1, null));

			}
			if(this.main.score.getTimer() == -300 || this.main.score.getTimer() == -200 || this.main.score.getTimer() == -100) {
				Title.sendTitle(player, 5, 40, 5, "", "§d" + this.main.score.getTimer()/-100);
			}
			if(this.main.score.getTimer() == 0) {
				Title.sendTitle(player, 5, 40, 5, "", "§cGO !!!");
				player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 20.0F);
			}
			if(this.main.score.getTimer() == this.main.PigRunUtils.stopTime && this.main.PigRunUtils.quitTime == -1) {
				if(player.getVehicle() != null)
					player.getVehicle().remove();
				player.setGameMode(GameMode.SPECTATOR);
				this.main.PigRunUtils.quitTime = this.main.score.getTimer() + 1500;
			}
		}
		if(this.main.score.getTimer() == 0) {
			this.main.PigRunUtils.breakBarrier();
		}
		
		if(this.main.score.getTimer() == this.main.PigRunUtils.quitTime) {
			this.main.StartUtils.endGame();
		}
		
		
		if(!(this.main.isGame(GamesHG.PIGRUN))){
			cancel();
		}
		
		this.main.score.setTimer(this.main.score.getTimer() + 005);
	}

}
