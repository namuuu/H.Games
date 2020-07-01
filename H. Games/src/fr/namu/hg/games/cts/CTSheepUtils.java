package fr.namu.hg.games.cts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;

public class CTSheepUtils {

	private MainHG main;

	private int playersSizeAtStart = 0;
	
	/////////////////// POSITIONS POUR 4 JOUEURS
	private Location redLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -5);
	private Location yelLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -9);
	private Location greLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -13);
	private Location cyaLocAt4 = new Location(Bukkit.getWorld("world"), 23, 32, -16);
	
	private Location SheepSpawnAt4 = new Location(Bukkit.getWorld("world"), 0, 32, 0);
	
	/////////////////// POSITIONS POUR 8 JOUEURS
	private Location redLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -5);
	private Location yelLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -9);
	private Location greLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -13);
	private Location cyaLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -16);
	private Location whiLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, -2);
	private Location purLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, 2);
	private Location bluLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, 6);
	private Location oraLocAt8 = new Location(Bukkit.getWorld("world"), 23, 32, 9);
	
	private Location SheepSpawnAt8 = new Location(Bukkit.getWorld("world"), 0, 32, 0);
	
	private List<Sheep> SheepList = new ArrayList<>();
	
	
	public CTSheepUtils(MainHG main) {
		this.main = main;
	}

	public void setTeams() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		Collections.shuffle(players);
		playersSizeAtStart = players.size();
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

	public void spawnSheep() {
		if(this.playersSizeAtStart <= 4) {
			Sheep sheep = (Sheep)Bukkit.getWorld("world").spawnEntity(this.SheepSpawnAt4, EntityType.SHEEP);
			sheep.setColor(DyeColor.GRAY);
			sheep.setMaxHealth(1000D);
			sheep.setHealth(1000D);
			SheepList.add(sheep);
		} else {
			Sheep sheep = (Sheep)Bukkit.getWorld("world").spawnEntity(this.SheepSpawnAt8, EntityType.SHEEP);
			sheep.setColor(DyeColor.GRAY);
			sheep.setMaxHealth(1000D);
			sheep.setHealth(1000D);
			SheepList.add(sheep);
		}				
	}
	
	@SuppressWarnings("deprecation")
	public void refreshSheeps() {
		for (Integer ind = 0; ind < SheepList.size(); ind++) {
			Sheep sheep = SheepList.get(ind);
			Location sheepLoc = sheep.getLocation();
			int sheepLocX = sheepLoc.getBlockX();
			int sheepLocY = sheepLoc.getBlockY();
			int sheepLocZ = sheepLoc.getBlockZ();
			Block sheepMat = sheep.getWorld().getBlockAt(sheepLocX, sheepLocY - 2, sheepLocZ);
			if(sheepMat.getType() == Material.WOOL) {
				
				if(sheepMat.getData() == DyeColor.LIGHT_BLUE.getData()) {
					sheep.setColor(DyeColor.LIGHT_BLUE);
				} else if (sheepMat.getData() == DyeColor.LIME.getData()) {
					sheep.setColor(DyeColor.LIME);
				} else if (sheepMat.getData() == DyeColor.YELLOW.getData()) {
					sheep.setColor(DyeColor.YELLOW);
				} else if (sheepMat.getData() == DyeColor.RED.getData()) {
					sheep.setColor(DyeColor.RED);
				} else if (sheepMat.getData() == DyeColor.WHITE.getData()) {
					sheep.setColor(DyeColor.WHITE);
				} else if (sheepMat.getData() == DyeColor.PURPLE.getData()) {
					sheep.setColor(DyeColor.PURPLE);
				} else if (sheepMat.getData() == DyeColor.BLUE.getData()) {
					sheep.setColor(DyeColor.BLUE);
				} else if (sheepMat.getData() == DyeColor.ORANGE.getData()) {
					sheep.setColor(DyeColor.ORANGE);
				}
			}
			
		}
	}
	
	public void refeshPoints() {
		CTSEnum.CYAN.setShNumber(0);
		CTSEnum.GREEN.setShNumber(0);
		CTSEnum.YELLOW.setShNumber(0);
		CTSEnum.RED.setShNumber(0);
		CTSEnum.WHITE.setShNumber(0);
		CTSEnum.PURPLE.setShNumber(0);
		CTSEnum.BLUE.setShNumber(0);
		CTSEnum.ORANGE.setShNumber(0);
		
		for (Integer ind = 0; ind < SheepList.size(); ind++) {
			Sheep sheep = SheepList.get(ind);

			if(sheep.getColor() == DyeColor.LIGHT_BLUE) {
				CTSEnum.CYAN.addSheep(1);
			} else if (sheep.getColor() == DyeColor.LIME) {
				CTSEnum.GREEN.addSheep(1);
			} else if (sheep.getColor() == DyeColor.YELLOW) {
				CTSEnum.YELLOW.addSheep(1);
			} else if (sheep.getColor() == DyeColor.RED) {
				CTSEnum.RED.addSheep(1);
			} else if (sheep.getColor() == DyeColor.WHITE) {
				CTSEnum.WHITE.addSheep(1);
			} else if (sheep.getColor() == DyeColor.PURPLE) {
				CTSEnum.PURPLE.addSheep(1);
			} else if (sheep.getColor() == DyeColor.BLUE) {
				CTSEnum.BLUE.addSheep(1);
			} else if (sheep.getColor() == DyeColor.ORANGE) {
				CTSEnum.ORANGE.addSheep(1);
			}
		}
	}
	
	public int teamPlacement(CTSEnum team) {
		List<CTSEnum> teamList = Arrays.asList(CTSEnum.values());
		List<CTSEnum> teamOrdered = new ArrayList<>();
		
		for (Integer score = 50; score >= 0; score--) {
			for (Integer ind = 0; ind < teamList.size(); ind++) {
				CTSEnum teamPick = teamList.get(ind);
				if (teamPick.getShNumber() == score) {
					teamOrdered.add(teamPick);
				}
			}
		}		
		for(Integer ind = 0; ind < teamOrdered.size(); ind++) {
			if (team == teamOrdered.get(ind)) {
				return ind + 1;
			}
		}
		return 0;
	}
	
	public void setPoints() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			PlayerHG phg = this.main.playerhg.get(players.get(ind));
			this.main.ScoreUtils.earnSD(player, phg.getTeam().getShNumber()*7, "Moutons capturés");
			if(this.main.CTSUtils.teamPlacement(phg.getTeam()) == 1) {
				this.main.ScoreUtils.earnSD(player, 10, "Première place");
			}
			if(this.main.CTSUtils.teamPlacement(phg.getTeam()) == 2) {
				this.main.ScoreUtils.earnSD(player, 5, "Seconde place");
			}
		}
	}
	
	public void removeSheeps() {
		while (!SheepList.isEmpty()) {
			Sheep sheep = SheepList.get(0);
			sheep.remove();
			SheepList.remove(0);
		}
	}
}
