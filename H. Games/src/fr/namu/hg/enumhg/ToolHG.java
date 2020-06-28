package fr.namu.hg.enumhg;

public enum ToolHG {

	MULTIPLICATOR_CHANCE("Probabilités d'un boost", 0),
	;
	
	private final String ToolName;
	
	private int value;
	
	ToolHG(String ToolName, int value) {
		this.ToolName = ToolName;
		this.value = value;
	}
	
	public String getName() {
		return this.ToolName;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void switchBoolean() {
		if(this.value == 0) {
			this.value = 1;
		} else if (this.value == 1) {
			this.value = 0;
		}
	}
	
	public void setBoolean(Boolean value) {
		if(value == Boolean.valueOf(true)) {
			this.value = 1;
		} else if (value == Boolean.valueOf(false)) {
			this.value = 0;
		}
	}
	
	public Boolean getBoolean() {
		if (this.value == 1) {
			return Boolean.valueOf(true);
		} else if (this.value == 0) {
			return Boolean.valueOf(false);
		} else {
			return Boolean.valueOf(false);
		}
	}
}
