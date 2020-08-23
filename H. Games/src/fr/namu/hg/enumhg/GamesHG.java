package fr.namu.hg.enumhg;

public enum GamesHG {

	PACMAN("§6Pac-§eMan", Boolean.valueOf(false), 1),
	PIGRUN("§6Run, Piggy, Run !", Boolean.valueOf(false), 1),
	CTS("§6Capture The Sheep", Boolean.valueOf(false), 1),
	RABBIT_RACE("§6Rabbit Race", Boolean.valueOf(false), 1),
	DUEL("§6Western Duel", Boolean.valueOf(true), 1),
	
	NULL("§7Aucun Jeu actif", Boolean.valueOf(true), 1),
	
	;
	
	private final String GameName;
	
	private Boolean activated;
	
	private int multiplicator;
	
	GamesHG (String GameName, Boolean activated, int multiplicator) {
		this.GameName = GameName;
		this.activated = activated;
		this.multiplicator = multiplicator;
	}
	
	public String getName() {
		return this.GameName;
	}
	
	public void setActive(Boolean value) {
		this.activated = value;
	}
	
	public void switchActive() {
		if (this.activated == true) {
			this.activated = Boolean.valueOf(false);
		} else if (this.activated == false) {
			this.activated = Boolean.valueOf(true);
		}
	}
	
	public Boolean getActive() {
		return this.activated;
	}
	
	public void setMultiplicator(int multiplicator) {
		this.multiplicator = multiplicator;
	}
	
	public int getMultiplicator() {
		return this.multiplicator;
	}
}
