package fr.namu.hg.games.cts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;

public class CTSheepUtils {

	private MainHG main;
	
	/////////////////// POSITIONS POUR 4 JOUEURS
	private Location redLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -5);
	private Location yelLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -9);
	private Location greLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -13);
	private Location cyaLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -16);
	
	/////////////////// POSITIONS POUR 8 JOUEURS
	private Location redLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -5);
	private Location yelLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -9);
	private Location greLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -13);
	private Location cyaLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -16);
	private Location whiLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -2);
	private Location purLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, 2);
	private Location bluLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, 6);
	private Location oraLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, 9);
	
	
	
	
	public CTSheepUtils(MainHG main) {
		this.main = main;
	}

	public void setTeams() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		Collections.shuffle(players);
		for (Integer ind = 0; ind < players.size(); ind++) {
			PlayerHG phg = this.main.playerhg.get(players.get(ind));
			Player player = Bukkit.getPlayer(players.get(ind));
			
			if(ind == 0) {
				phg.setTeam(CTSEnum.RED);
				tpFromTeam(player);
				setOutfit(player);
			} else if(ind == 1) {
				phg.setTeam(CTSEnum.YELLOW);
				tpFromTeam(player);
				setOutfit(player);
			} else if(ind == 2) {
				phg.setTeam(CTSEnum.GREEN);
				tpFromTeam(player);
				setOutfit(player);
			} else if(ind == 3) {
				phg.setTeam(CTSEnum.CYAN);
				tpFromTeam(player);
				setOutfit(player);
			} else if(ind == 4) {
				phg.setTeam(CTSEnum.WHITE);
				tpFromTeam(player);
				setOutfit(player);
			} else if(ind == 5) {
				phg.setTeam(CTSEnum.PURPLE);
				tpFromTeam(player);
				setOutfit(player);
			} else if(ind == 6) {
				phg.setTeam(CTSEnum.BLUE);
				tpFromTeam(player);
				setOutfit(player);
			} else if(ind == 7) {
				phg.setTeam(CTSEnum.ORANGE);
				tpFromTeam(player);
				setOutfit(player);
			}
		}
	}
	
	public void tpFromTeam(Player player) {
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		CTSEnum team = phg.getTeam();
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		
		if(players.size() <= 4) {			
			if(team == CTSEnum.RED) {
				player.teleport(redLocAt4);
			} else if(team == CTSEnum.YELLOW) {
				player.teleport(yelLocAt4);
			} else if (team == CTSEnum.GREEN) {
				player.teleport(greLocAt4);
			} else if (team == CTSEnum.CYAN) {
				player.teleport(cyaLocAt4);
			}			
		} else {
			if(team == CTSEnum.RED) {
				player.teleport(redLocAt8);
			} else if(team == CTSEnum.YELLOW) {
				player.teleport(yelLocAt8);
			} else if (team == CTSEnum.GREEN) {
				player.teleport(greLocAt8);
			} else if (team == CTSEnum.CYAN) {
				player.teleport(cyaLocAt8);
			} else if (team == CTSEnum.WHITE) {
				player.teleport(whiLocAt8);
			} else if (team == CTSEnum.PURPLE) {
				player.teleport(purLocAt8);
			} else if (team == CTSEnum.BLUE) {
				player.teleport(bluLocAt8);
			} else if (team == CTSEnum.ORANGE) {
				player.teleport(oraLocAt8);
			}						
		}
	}
	
	public void setOutfit(Player player) {
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		CTSEnum team = phg.getTeam();
		this.main.ItemUtils.setLeatherColored(player, team.getColor());
	}
}
