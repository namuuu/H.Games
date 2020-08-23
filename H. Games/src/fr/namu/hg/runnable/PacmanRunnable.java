package fr.namu.hg.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
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
		
		World world = Bukkit.getWorld("world");
		if(this.main.score.getTimer() >= 10) {
			world.playSound(this.main.PacManUtils.getPacMan().getLocation(), Sound.CREEPER_HISS, 1.0F, 1.0F);
		}
		
		if(this.main.score.getTimer() == 10) {
			this.main.pacman.startGame();
		}
		
		if(this.main.score.getTimer() == this.main.pacman.FirstCherryTimer) {
			this.main.PacManUtils.spawnCherry();
			this.main.pacman.FirstCherryTimer = this.main.score.getTimer() + 15;
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
