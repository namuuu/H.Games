package fr.namu.hg.games.pigrun;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.namu.hg.MainHG;
import fr.namu.hg.runnable.PigrunRunnable;

public class PigRun {
	
	private MainHG main;

	public Location StartLine = new Location(Bukkit.getWorld("world"), -26, 37, 35);
	

	public PigRun(MainHG main) {
		this.main = main;
	}
	
	public void initGame() {
		this.main.score.setTimer(-800);
		Location StartLine = this.StartLine;
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			player.teleport(StartLine);
			this.main.ScoreUtils.resetStoredValue(player);
		}
		this.main.PigRunUtils.setupBarrier();
		this.startGame();
	}
	
	public void startGame() {
		PigrunRunnable startPig = new PigrunRunnable(this.main);
	    startPig.runTaskTimer((Plugin)this.main, 0L, 1L);
	    
	}

	
	
}
