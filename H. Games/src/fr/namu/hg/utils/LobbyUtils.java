package fr.namu.hg.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.ConfigurationHG;

public class LobbyUtils {
	
	private MainHG main;
	
	Location LobbySpawn = null;
	
	private final int RequiredPlayersToStart = 8;
	
	private int MaxPlayersSlot = 8;
	
	private String CurrentConfigName = "§cConfiguration Inconnue !";
	
	public LobbyUtils (MainHG main) {
		this.main = main;
	}
	
	public void getPlayerToLobby(Player player) {
		World world = Bukkit.getWorld("world");
		if(LobbySpawn != null) {
			player.teleport(LobbySpawn);
		} else {
			player.teleport(new Location(world, 0, world.getHighestBlockYAt(0, 0) + 2, 0));
		}
	}
	
	public int getRequiredLeft() {
		return (this.RequiredPlayersToStart - this.main.GeneralUtils.GetNbPlayer());
	}
	
	public int getMaxPlayers() {
		return (this.MaxPlayersSlot);
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
