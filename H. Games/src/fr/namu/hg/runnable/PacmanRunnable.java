package fr.namu.hg.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class PacmanRunnable extends BukkitRunnable {
	
	private MainHG main;
	
	public PacmanRunnable(MainHG main) {
		this.main = main;
	}
	
	public void run() {
		this.main.score.updateBoard();				
		
		
		if(this.main.score.getTimer() == 10) {
			this.main.pacman.startGame();
		}
		
		if(this.main.score.getTimer() == this.main.pacman.FirstCherryTimer) {
			this.main.PacManUtils.spawnCherry();
		}
		
		if(this.main.score.getTimer() == this.main.PacManUtils.desacCherry) {
			this.main.PacManUtils.cherryDeactivate();
		}
		
		if(this.main.score.getTimer() == this.main.PacManUtils.endGame) {
			this.main.StartUtils.endGame();
		}
		
		if(!this.main.isGame(GamesHG.PACMAN)) {
			cancel();
		}
		
		
		this.main.score.addTimer();
	}

}
