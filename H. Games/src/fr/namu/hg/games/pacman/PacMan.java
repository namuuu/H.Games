package fr.namu.hg.games.pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.namu.hg.MainHG;
import fr.namu.hg.runnable.PacmanRunnable;

public class PacMan {
	
	private MainHG main;
	private UUID PacManUUID = null;
	public List<UUID> ghosts = new ArrayList<>();
	
	public int PacManLives = 3;
	public double PacManHearths = 6.0D;
	
	public int FirstCherryTimer = 15;
	
	public PacMan(MainHG main) {
		this.main = main;
	}

	public void initGame() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		PacManUUID = this.main.PacManUtils.pickPacMan();
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			this.main.PacManUtils.randomTeleport(player);
			if(PacManUUID != player.getUniqueId()) {
				ghosts.add(player.getUniqueId());
			}
		}
		this.main.PacManUtils.isPacState(PacEnum.START);
		this.main.score.setTimer(0);
		PacmanRunnable startLobby = new PacmanRunnable(this.main);
	    startLobby.runTaskTimer((Plugin)this.main, 0L, 20L);
	}
	
	public void startGame() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		this.main.PacManUtils.setPacState(PacEnum.CHERRYLESS);
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			if(isPacMan(player)) {
				this.main.PacManUtils.revealPacMan(player);
			}
			if(isGhost(player)) {
				this.main.PacManUtils.revealGhost(player);
			}
		}
	}
	
	public boolean isPacMan(Player player) {
		return (player.getUniqueId() == PacManUUID);
	}
	
	public boolean isGhost(Player player) {
		return (ghosts.contains(player.getUniqueId()));
	}
	
	
	
}
