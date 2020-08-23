package fr.namu.hg.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class DuelRunnable extends BukkitRunnable {

	private MainHG main;
	
	public DuelRunnable(MainHG main) {
		this.main = main;
	}

	public void run() {
		this.main.score.updateBoard();
		
		this.main.Duel.decGunTimer();
		this.main.DuelUtils.decFinishTimer();
		
		if(!this.main.isGame(GamesHG.DUEL)) {
			cancel();
		}
	}
}
