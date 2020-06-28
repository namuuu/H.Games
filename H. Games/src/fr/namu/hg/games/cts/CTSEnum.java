package fr.namu.hg.games.cts;

import org.bukkit.Color;

public enum CTSEnum {
	CYAN("Cyan", Color.TEAL, 0),
	GREEN("Vert", Color.GREEN, 0),
	YELLOW("Jaune", Color.YELLOW, 0),
	RED("Rouge", Color.RED, 0),
	WHITE("Blanc", Color.WHITE, 0),
	PURPLE("Violet", Color.PURPLE, 0),
	BLUE("Bleu", Color.BLUE, 0),
	ORANGE("Orange", Color.ORANGE, 0),
	
	;
	
	private String teamName;
	
	private Color color;
	
	private int SheepNumber;
	
	CTSEnum(String teamName, Color color, int SheepNumber) {
		this.teamName = teamName;
		this.color = color;
		this.SheepNumber = SheepNumber;
	}
	
	public String getName() {
		return this.teamName;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getShNumber() {
		return this.SheepNumber;
	}
	
	public void addSheep(int value) {
		this.SheepNumber = this.SheepNumber + value;
	}
	
	public void setShNumber(int value) {
		this.SheepNumber = value;
	}
}
