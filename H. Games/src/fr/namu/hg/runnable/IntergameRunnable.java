package fr.namu.hg.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.StateHG;

public class IntergameRunnable extends BukkitRunnable {

	private MainHG main;
	
	public int waitingQueue = -1;
	
	public IntergameRunnable(MainHG main) {
		this.main = main;
	}
	
	public void run() {
		
		if(this.waitingQueue == -1) {
			this.waitingQueue = 20;
		}
		
		this.main.score.updateBoard();
		
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));	
			if(waitingQueue >= 0) 
				player.setLevel(waitingQueue);
		}
		
		if(waitingQueue == 0) {
			this.main.StartUtils.startGame();
		}		
		if(!this.main.isState(StateHG.INTERGAME)) {
			cancel();
		}
		this.waitingQueue--;
	}
}
