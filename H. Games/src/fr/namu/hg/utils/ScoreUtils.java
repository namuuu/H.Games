package fr.namu.hg.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;
import fr.namu.hg.scoreboard.Title;

public class ScoreUtils {
	
	private MainHG main;
	
	

	public ScoreUtils(MainHG main) {
		this.main = main;
	}
	
	public void sendActionBarAll(String message) {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			Title.sendActionBar(player, message);
		}
	}

	public int getPlace(Player player) {
		PlayerHG PlayerHG = this.main.playerhg.get(player.getUniqueId());
		List<Integer>stardusts = new ArrayList<Integer>();
		
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			PlayerHG phg = this.main.playerhg.get(players.get(ind));
			stardusts.add(phg.getStarDust());
		}
		
		Collections.sort(stardusts);
		Collections.reverse(stardusts);
		
		for (int place = 0; place < stardusts.size(); place++) {
			if(stardusts.get(place) == PlayerHG.getStarDust()) {
				return (place + 1);
			}
		}
		return 0;
	}
	
	public void resetStoredValue(Player player) {
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		phg.setStoredValue1(0);
		phg.setStoredValue2(0);
		phg.setStoredValue3(0);
		phg.setStoredValue4(0);
	}
	
	public String getDecimal(int value) {
		DecimalFormat df = new DecimalFormat("#.000");
		String output = df.format(value);
		return output;
	}
	
	public void earnSD(Player player, int value, String reason) {
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		phg.addStarDust(value);
		player.sendMessage("§eVous avez gagné §b" + value + " poussières d'étoiles ! §7(" + reason + ")");
	}
}
