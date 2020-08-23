package fr.namu.hg.games.duel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import fr.namu.hg.MainHG;
import fr.namu.hg.runnable.DuelRunnable;

public class WDuel {
	
	private MainHG main;
	
	private int gunTimer = 0;
	private int gunCoolDown = 5;
	
	private int round = 0;
	private int roundMax = 3;
	
	private WDuelState state;
	
	public WDuel(MainHG main) {
		this.main = main;
	}
	
	public void initGame() {
		this.round = 0;
		setState(WDuelState.BLIND);
		resetGunTimer();
		addRound();
		
		this.main.DuelUtils.setLocationList();
		this.main.DuelUtils.teleportAllPlayers();
		
		DuelRunnable startduel = new DuelRunnable(this.main);
	    startduel.runTaskTimer((Plugin)this.main, 0L, 20L);
	}
	
	
	public int getRound() {
		return this.round;
	}
	public void addRound() {
		this.round = this.round + 1;
	}
	public int getMaxRound() {
		return this.roundMax;
	}
	public void setMaxRound(int value) {
		this.setMaxRound(value);
	}
	public boolean isState(WDuelState state) {
		return (state == this.state);
	}
	public void setState(WDuelState state) {
		this.state = state;
	}
	public void resetGunTimer() {
		this.gunTimer = gunCoolDown;
	}
	public int getGunTimer() {
		return this.gunTimer;
	}
	public boolean isFightEnabled() {
		if(gunTimer == 0) {
			return true;
		} else {
			return false;
		}
	}
	public void decGunTimer() {
		if(this.gunTimer == 0) {
			this.setState(WDuelState.GUNFIGHT);
			List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
			for(Integer ind = 0; ind < players.size(); ind++) {
				Player player = Bukkit.getPlayer(players.get(ind));
				for (PotionEffect po : player.getActivePotionEffects())
				      player.removePotionEffect(po.getType());
			}
		}
		if(gunTimer != 0) {		
			this.gunTimer--;
		}
		
	}
}

