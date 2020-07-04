package fr.namu.hg.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.ConfigurationHG;

public class LobbyUtils {
	
	private MainHG main;
	
	private final int RequiredPlayersToStart = 4;
	
	private int MaxPlayersSlot = 8;
	
	private int autoStartTimer = -1;
	
	private String CurrentConfigName = "§cConfiguration Inconnue !";
	
	public LobbyUtils (MainHG main) {
		this.main = main;
	}
	
	public void getPlayerToLobby(Player player) {
		World world = player.getWorld();
		player.teleport(world.getSpawnLocation());
	}
	
	public int getRequiredLeft() {
		return (this.RequiredPlayersToStart - this.main.GeneralUtils.GetNbPlayer());
	}
	
	public int getMaxPlayers() {
		return (this.MaxPlayersSlot);
	}
	
	public int getAutoStartTimer() {
		return this.autoStartTimer;
	}
	
	public void decreaseAutoStartTimer() {
		this.autoStartTimer = autoStartTimer - 1;
	}
	
	public void handleAutoStart() {
		if(getRequiredLeft() == 0 && autoStartTimer == -1) {
			autoStartTimer = 30;
		} else if(MaxPlayersSlot == this.main.GeneralUtils.GetNbPlayer() && autoStartTimer <= 5) {
			autoStartTimer = 5;
		}
	}
	
	public String getConfigName() {
		List<ConfigurationHG> configList = Arrays.asList(ConfigurationHG.values());
		for (Integer ind = 0; ind < configList.size(); ind++) {
			ConfigurationHG currentConfig = configList.get(ind);
			if(this.main.config == currentConfig) {
				CurrentConfigName = currentConfig.getName();
			}
		}
		return CurrentConfigName;
	}
	
	

}
