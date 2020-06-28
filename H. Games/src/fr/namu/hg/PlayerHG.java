package fr.namu.hg;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import fr.namu.hg.games.cts.CTSEnum;

public class PlayerHG {
	
	Scoreboard board;
	
	private int starDust = 0; 
	
	private int storedValue1 = 0;
	private int storedValue2 = 0;
	private int storedValue3 = 0;
	private int storedValue4 = 0;
	
	private CTSEnum CTSTeam;
	
	public PlayerHG() {
		this.board = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	public int getStarDust() {
		return this.starDust;
	}
	
	public void addStarDust(int starDust) {
		this.starDust = this.starDust + starDust;
	}
	
	public void removeStardust(int StarDust) {
		this.starDust = this.starDust - starDust;
	}
	
	public void setStarDust(int starDust) {
		this.starDust = starDust;
	}
	
	public void setScoreBoard(Scoreboard board) {
	    this.board = board;
	  }
	
	public Scoreboard getScoreBoard() {
	    return this.board;
	  }
	
	public void setStoredValue1(int value) {
		this.storedValue1 = value;
	}
	public void setStoredValue2(int value) {
		this.storedValue2 = value;
	}
	public void setStoredValue3(int value) {
		this.storedValue3 = value;
	}
	public void setStoredValue4(int value) {
		this.storedValue4 = value;
	}
	
	public int getStoredValue1() {
		return this.storedValue1;
	}
	public int getStoredValue2() {
		return this.storedValue2;
	}
	public int getStoredValue3() {
		return this.storedValue3;
	}
	public int getStoredValue4() {
		return this.storedValue4;
	}
	
	public CTSEnum getTeam() {
		return this.CTSTeam;
	}
	public void setTeam(CTSEnum team) {
		this.CTSTeam = team;
	}

}
