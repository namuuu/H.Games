package fr.namu.hg.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class RabbitRunnable extends BukkitRunnable {

	private MainHG main;
	
	public RabbitRunnable(MainHG main) {
		this.main = main;
	}
	
	public void run() {
		this.main.score.updateBoard();
		
		if(this.main.score.getTimer() == -4) {
			this.main.RRUtils.setupRabbits();
		}
		
		if(this.main.score.getTimer() == 0) {
			List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
			for(Integer ind = 0; ind < players.size(); ind++) {
				Player player = Bukkit.getPlayer(players.get(ind));
				this.main.RRUtils.emeraldReroll(player);
			}
		}	
		
		if(this.main.RRUtils.quitTime > 0) {
			this.main.RRUtils.quitTime = this.main.RRUtils.quitTime - 1;
			if(this.main.RRUtils.quitTime == 0) {
				this.main.RRUtils.deleteRabbits();
				this.main.StartUtils.endGame();
			}
		}
		
		if(!this.main.isGame(GamesHG.RABBIT_RACE)) {
			cancel();
		}
		
		this.main.score.addTimer();
	}
}
