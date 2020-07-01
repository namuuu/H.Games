package fr.namu.hg.games.cts;

import org.bukkit.plugin.Plugin;

import fr.namu.hg.MainHG;
import fr.namu.hg.runnable.CTSRunnable;

public class CTSheep {

	private MainHG main;
	
	
	public CTSheep(MainHG main) {
		this.main = main;
	}


	public void initGame() {
		this.main.score.setTimer(160);
		this.main.CTSUtils.setTeams();
		
		CTSRunnable startCTS = new CTSRunnable(this.main);
	    startCTS.runTaskTimer((Plugin)this.main, 0L, 20L);
	}
}
