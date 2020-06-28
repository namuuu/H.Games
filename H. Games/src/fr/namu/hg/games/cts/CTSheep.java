package fr.namu.hg.games.cts;

import fr.namu.hg.MainHG;

public class CTSheep {

	private MainHG main;
	
	
	public void initGame() {
		this.main.score.setTimer(160);
		this.main.CTSUtils.setTeams();
	}
}
