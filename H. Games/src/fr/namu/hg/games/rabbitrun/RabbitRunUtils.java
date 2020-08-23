package fr.namu.hg.games.rabbitrun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;
import fr.namu.hg.utils.ItemStackUtils;

public class RabbitRunUtils {

	private MainHG main;
	
	public int quitTime = -1;
	
	private List<Rabbit> rabbits = new ArrayList<>();
	public List<UUID> runners = new ArrayList<>();
	
	public RabbitRunUtils(MainHG main) {
		this.main = main;
	}
	
	public void setupPlayers() {
		World world = Bukkit.getWorld("world");
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		Collections.shuffle(players);
		 int LineZ = -27;//2986;		
		 int PlayersY = 40;//26;
		 int startLineX = -20;//3000;	
		 int gap = -3; 
		 
		for (Integer ind = 0; ind < players.size(); ind++) {		
			Player player = Bukkit.getPlayer(players.get(ind));
			PlayerHG phg = this.main.playerhg.get(players.get(ind));
			player.getInventory().clear();
			player.teleport(new Location(world, startLineX + 0.5D, PlayersY, LineZ + 0.5D, 90F, 90F));
			phg.setStoredValue1(0);
								
			LineZ = LineZ + gap;
		}
	}
	
	public void setupRabbits() {
		World world = Bukkit.getWorld("world");
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		 int LineZ = -27;//2986;		
		 int RabbitsY = 32;//18;	
		 int startLineX = -20;//3000;
		 int gap = -3; 
		
		 for (Integer ind = 0; ind < players.size(); ind++) {	
			Player player = Bukkit.getPlayer(players.get(ind));
			Location rabbitLoc = new Location(world, startLineX + 0.5D, RabbitsY, LineZ + 0.5D, 0F, 0F);
			Rabbit rabbit = (Rabbit)world.spawnEntity(rabbitLoc, EntityType.RABBIT);
			rabbits.add(rabbit);
			rabbit.setCustomName(player.getName());
			rabbit.setMaxHealth(100D);
			rabbit.setHealth(rabbit.getMaxHealth());
			ItemStackUtils.removeAI(rabbit);
			
			LineZ = LineZ + gap;
		}
	}
	
	public void emeraldReroll(Player player) {
		for (Integer ind = 0; ind < 9; ind++) {
			player.getInventory().setItem(ind, this.main.ItemUtils.metaExtra(Material.PAPER, "§aClique sur l'Émeraude !", 1, null));
		}
		Random random = new Random();
		player.getInventory().setItem(random.nextInt(9), this.main.ItemUtils.metaExtra(Material.EMERALD, "§l§aCLIQUE SUR MOI !", 1, null));		
	}
	
	public void advanceRabbit(Player player) {	
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		int finishLineX = -34;//3021;
		
		for (Integer ind = 0; ind < rabbits.size(); ind++) {
			Rabbit rabbit = rabbits.get(ind);
			if(rabbit.getName() == player.getName() && rabbit.isDead() == false) {
				Location rabbitLoc = rabbit.getLocation();
				rabbitLoc.setX(rabbitLoc.getX() - 0.5D);
				rabbit.teleport(rabbitLoc);
				ItemStackUtils.removeAI(rabbit);
				phg.setStoredValue1(phg.getStoredValue1() + 1);
				this.emeraldReroll(player);
				this.main.score.updateBoard();
				
				if(rabbitLoc.getBlockX() == finishLineX) {
					playerFinish(player);
				}
			}
		}
	}
	
	public int getPlace(Player player) {
		PlayerHG PlayerHG = this.main.playerhg.get(player.getUniqueId());
		List<Integer>clics = new ArrayList<Integer>();
		
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			PlayerHG phg = this.main.playerhg.get(players.get(ind));
			clics.add(phg.getStoredValue1());
		}
		
		Collections.sort(clics);
		Collections.reverse(clics);
		
		for (int place = 0; place < clics.size(); place++) {
			if(clics.get(place) == PlayerHG.getStoredValue1()) {
				return (place + 1);
			}
		}
		return 0;
	}
	
	public void playerFinish(Player player) {
		this.runners.add(player.getUniqueId());
		player.getInventory().clear();
		
		Bukkit.broadcastMessage("§2" + player.getName() + "§a a mené son lévrier jusqu'au bout de la course en §2" + this.main.GeneralUtils.conversion(this.main.score.getTimer())  + "§a !");
		
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		if(runners.size() == players.size()) {
			Bukkit.broadcastMessage("§9Félicitations ! §aTout le monde a fini la course. Recevez ces §epoussières d'étoiles §b!");
			this.quitTime = 3;
		}
	}
	
	public void deleteRabbits() {
		while (!rabbits.isEmpty()) {
			System.out.println("Les lapins ont été supprimés.");
			Rabbit rabbit = rabbits.get(0);
			rabbit.remove();
			rabbits.remove(0);
		}
	}
}
