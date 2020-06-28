package fr.namu.hg.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.ConfigurationHG;

public class GeneralUtils {

	private final MainHG main;
	
	private int NbPlayer = 0;
	
	private int NbGame = 0;
	private int NbMaxGame = 10;
	
	private ConfigurationHG baseConfig = ConfigurationHG.CLASSIC;
	
	public GeneralUtils (MainHG main) {
		this.main = main;
	}
	
	public void addPlayer() {
		this.NbPlayer++;
	}
	
	public void removePlayer() {
		this.NbPlayer--;
	}
	
	public int GetNbPlayer() {
		return this.NbPlayer;
	}
	
	public void addGame() {
		this.NbGame++;
	}
	
	public int GetNbGame() {
		return this.NbGame;
	}
	
	public int GetNbMaxGame() {
		return this.NbMaxGame;
	}
	
	public void setHost(UUID host) {
		this.main.hostName = host;
	}
	
	public UUID setHost() {
		return this.main.hostName;
	}
	
	public void setConfig(ConfigurationHG config) {
		this.main.config = config;
	}
	
	public void setBaseConfig() {
		this.main.config = baseConfig;
	}
	
	public void PlayersSound(Sound sound) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), sound, 20.0F, 1.0F);
		}
	}
	
	public String conversion(int timer) {
	    String valeur;
	    if (timer % 60 > 9) {
	      valeur = String.valueOf(timer % 60) + "s";
	    } else {
	      valeur = "0" + (timer % 60) + "s";
	    } 
	    if (timer / 3600 > 0) {
	      if (timer % 3600 / 60 > 9) {
	        valeur = String.valueOf(timer / 3600) + "h" + (timer % 3600 / 60) + "m" + valeur;
	      } else {
	        valeur = String.valueOf(timer / 3600) + "h0" + (timer % 3600 / 60) + "m" + valeur;
	      } 
	    } else if (timer / 60 > 0) {
	      valeur = String.valueOf(timer / 60) + "m" + valeur;
	    } 
	    return valeur;
	  }
	
}
