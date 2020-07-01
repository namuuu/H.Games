package fr.namu.hg.runnable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class CTSRunnable extends BukkitRunnable {
	
	private MainHG main;
	
	private int sheepSpawn = 999;
	private int rateSpawn = 10;
	
	private int quitTime = -1;
	
	public CTSRunnable(MainHG main) {
		this.main = main;
	}

	public void run() {
		this.main.score.updateBoard();
		this.main.CTSUtils.refreshSheeps();
		this.main.CTSUtils.refeshPoints();
		
		if(this.main.score.getTimer() == 150) {
			this.main.CTSUtils.spawnSheep();
			this.setNextSheepSpawn(rateSpawn);
		}
		
		if(this.main.score.getTimer() == this.sheepSpawn) {
			this.main.CTSUtils.spawnSheep();
			this.setNextSheepSpawn(rateSpawn);	
		}
		
		if(this.main.score.getTimer() == 30) {
			Bukkit.broadcastMessage("§cDernière ligne droite ! Le taux d'apparition des moutons augmente !");
			this.rateSpawn = 5;
		}
		
		if(this.main.score.getTimer() == 0) {
			this.main.score.updateBoard();
			this.main.CTSUtils.refreshSheeps();
			this.main.CTSUtils.refeshPoints();
			this.main.CTSUtils.setPoints();
			this.quitTime = 5;
			this.main.CTSUtils.removeSheeps();
		}
		
		if(this.quitTime == 0) {
			this.main.StartUtils.endGame();
		}
		
		
		if(this.main.score.getTimer() > -1) {
			this.main.score.setTimer(this.main.score.getTimer() - 1);
		} else if (this.quitTime > 0) {
			this.quitTime = this.quitTime - 1;
		}
		
		
		if(!this.main.isGame(GamesHG.CTS)) {
			cancel();
		}
	}
	
	public void setNextSheepSpawn(int time) {
		this.sheepSpawn = this.main.score.getTimer() - time;
	}

}
