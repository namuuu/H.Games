package fr.namu.hg.games.pigrun;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class PigRunUtils {
	
	private MainHG main;
	
	private int cp1MinX = -30;
	private int cp1MaxX = -21;
	private int cp1MinZ = 24;
	private int cp1MaxZ = 24;
	
	private int cp2MinX = -30;
	private int cp2MaxX = -21;
	private int cp2MinZ = 17;
	private int cp2MaxZ = 17;
	
	private int cp3MinX = -30;
	private int cp3MaxX = -21;
	private int cp3MinZ = 6;
	private int cp3MaxZ = 6;
	
	private int cpEndMinX = -30;
	private int cpEndMaxX = -21;
	private int cpEndMinZ = -3;
	private int cpEndMaxZ = -3;
	
	private int barMinX = -31;
	private int barMaxX = -21;
	private int barY = 33;
	private int barZ = 33;
	
	private int finishedPlayers = 0;
	public int quitTime = -1;
	public int stopTime = -1;
	
	public List<UUID> firstCheckPoint = new ArrayList<>();
	public List<UUID> secondCheckPoint = new ArrayList<>();
	public List<UUID> thirdCheckPoint = new ArrayList<>();
	public List<UUID> finished = new ArrayList<>();
	
	public PigRunUtils(MainHG main) {
		this.main = main;
	}

	public void setupPiggy(Player player) {
		Pig pig = (Pig)player.getWorld().spawnEntity(player.getLocation(), EntityType.PIG);
		pig.setSaddle(Boolean.valueOf(true));
		pig.setCustomName(player.getName());
		pig.setMaxHealth(500D);
		pig.setHealth(500D);
		pig.setPassenger(player);
		PigRunUtils.changeAI(pig);
	}
	
	public void passedCheckPoint(List<UUID> cp, Player player) {
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 20.0F, 1.0F);
		this.main.ScoreUtils.earnSD(player, 15, "Checkpoint passé");
		if(this.firstCheckPoint.contains(player.getUniqueId()))
			this.firstCheckPoint.remove(player.getUniqueId());
		
		if(this.secondCheckPoint.contains(player.getUniqueId()))
			this.secondCheckPoint.remove(player.getUniqueId());
		
		if(this.thirdCheckPoint.contains(player.getUniqueId()))
			this.thirdCheckPoint.remove(player.getUniqueId());
		
		cp.add(player.getUniqueId());
	}
	
	public void checkCheckPoint(Player player) {
		Location ploc = player.getLocation();
		UUID PUID = player.getUniqueId();
		PlayerHG phg = this.main.playerhg.get(PUID);
		
		if(ploc.getBlockX() >= cp1MinX && ploc.getBlockX() <= cp1MaxX && ploc.getBlockZ() >= cp1MinZ && ploc.getBlockZ() <= cp1MaxZ && !firstCheckPoint.contains(PUID) && player.getGameMode() != GameMode.SPECTATOR && player.getVehicle() != null) {
			this.passedCheckPoint(firstCheckPoint, player);
			phg.setStoredValue1(this.main.score.getTimer());
		}
		if(ploc.getBlockX() >= cp2MinX && ploc.getBlockX() <= cp2MaxX && ploc.getBlockZ() >= cp2MinZ && ploc.getBlockZ() <= cp2MaxZ && !secondCheckPoint.contains(PUID) && player.getGameMode() != GameMode.SPECTATOR && player.getVehicle() != null) {
			this.passedCheckPoint(secondCheckPoint, player);
			phg.setStoredValue2(this.main.score.getTimer());
		}
		if(ploc.getBlockX() >= cp3MinX && ploc.getBlockX() <= cp3MaxX && ploc.getBlockZ() >= cp3MinZ && ploc.getBlockZ() <= cp3MaxZ && !thirdCheckPoint.contains(PUID) && player.getGameMode() != GameMode.SPECTATOR && player.getVehicle() != null) {
			this.passedCheckPoint(thirdCheckPoint, player);
			phg.setStoredValue3(this.main.score.getTimer());
		}
		if(ploc.getBlockX() >= cpEndMinX && ploc.getBlockX() <= cpEndMaxX && ploc.getBlockZ() >= cpEndMinZ && ploc.getBlockZ() <= cpEndMaxZ && !finished.contains(PUID) && player.getGameMode() != GameMode.SPECTATOR && player.getVehicle() != null) {
			this.passedCheckPoint(finished, player);
			this.finishLine(player);
			phg.setStoredValue4(this.main.score.getTimer());
		}
	}
	
	public void setupBarrier() {
		for (int i = this.barMinX; i <=this.barMaxX; i++) {
			World world = Bukkit.getWorld("world");
			world.getBlockAt(i, barY, barZ).setType(Material.GLASS);
		}
	}
	
	public void breakBarrier() {
		for (int i = this.barMinX; i <=this.barMaxX; i++) {
			World world = Bukkit.getWorld("world");
			world.getBlockAt(i, barY, barZ).setType(Material.AIR);
		}
	}
	
	public void finishLine(Player player) {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		this.main.ScoreUtils.earnSD(player, (players.size() - finishedPlayers)/players.size(), "Ligne d'arrivée parcourue + Classement");
		this.finishedPlayers++;
		Bukkit.broadcastMessage("§e" + player.getName() + "a fini le parcours en position §b" + (this.finishedPlayers) + "§e, avec un temps de §b" + this.main.GeneralUtils.conversion(this.main.score.getTimer()/100));
		player.getVehicle().remove();
		player.setGameMode(GameMode.SPECTATOR);
		this.main.GeneralUtils.PlayersSound(Sound.PIG_IDLE);
		if(this.finishedPlayers == players.size()) {			
			this.quitTime = this.main.score.getTimer() + 1500;
		} else if (this.finishedPlayers == 1){
			this.stopTime = this.main.score.getTimer() + 3000;
			Bukkit.broadcastMessage("§7Il est le premier joueur à avoir atteint la ligne d'arrivée, vous avez 30 secondes avant que le chrono s'arrête !");
		}
	}
	
	public static void changeAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEnt = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEnt.getNBTTag();

        if (tag == null) {
            tag = new NBTTagCompound();
        }

        nmsEnt.c(tag);
        tag.setInt("setAI", 1);
        nmsEnt.f(tag);
    }
	
    
}
