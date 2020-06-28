package fr.namu.hg.enumhg;

public enum ConfigurationHG {
	CLASSIC("Classique"),
	HOST("Configuration Host"),
	
	;
	
	private String configName;
	
	ConfigurationHG (String configName) {
		this.configName = configName;
	}
	
	public String getName() {
		return this.configName;
	}
	
}
