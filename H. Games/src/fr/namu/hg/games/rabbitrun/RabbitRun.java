package fr.namu.hg.games.rabbitrun;

import org.bukkit.plugin.Plugin;

import fr.namu.hg.MainHG;
import fr.namu.hg.runnable.RabbitRunnable;

public class RabbitRun {
	
	private MainHG main;
	
	public RabbitRun(MainHG main) {
		this.main = main;
	}
	
	public void initGame() {
		this.main.score.setTimer(-5);
		this.main.RRUtils.setupPlayers();
		
		RabbitRunnable startRabbit = new RabbitRunnable(this.main);
	    startRabbit.runTaskTimer((Plugin)this.main, 0L, 20L); 
	}

}
