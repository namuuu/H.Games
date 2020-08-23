package fr.namu.hg.games.pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import fr.namu.hg.MainHG;
import fr.namu.hg.scoreboard.Title;

public class PacManUtils {
	
	private MainHG main;
	
	private Random random = new Random();
	private int TPNb;
	
	public Scoreboard scoreboard;
	
	public int desacCherry = -1;
	public int endGame = -1;
	
	private Location cherry = null;
	private int minX = 955;
	private int minZ = 968;
	private int maxX = 1056;
	private int maxZ = 1070;
	private int y = 20;
	
	private PacEnum PacState;

	public PacManUtils(MainHG main) {
		this.main = main;
		this.TPNb = 0;
	}
	
	public UUID pickPacMan() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		Collections.shuffle(players);
		
		return players.get(0);
	}
	
	public void randomTeleport(Player player) {
		 Location pmLoc1 = new Location(Bukkit.getWorld("world"), 1057, y, 1064);
		 Location pmLoc2 = new Location(Bukkit.getWorld("world"), 952, y, 971);
		 Location pmLoc3 = new Location(Bukkit.getWorld("world"), 970, y, 1068);
		 Location pmLoc4 = new Location(Bukkit.getWorld("world"), 1057, y, 1013);
		 Location pmLoc5 = new Location(Bukkit.getWorld("world"), 995, y, 1067);
		 Location pmLoc6 = new Location(Bukkit.getWorld("world"), 1051, y, 1018);
		 Location pmLoc7 = new Location(Bukkit.getWorld("world"), 968, y, 993);
		 Location pmLoc8 = new Location(Bukkit.getWorld("world"), 1054, y, 1036);
		
		switch (TPNb) {
		case 0:
			player.teleport(pmLoc1);
		case 1:
			player.teleport(pmLoc2);
		case 2:
			player.teleport(pmLoc3);
		case 3:
			player.teleport(pmLoc4);
		case 4:
			player.teleport(pmLoc5);
		case 5:
			player.teleport(pmLoc6);
		case 6:
			player.teleport(pmLoc7);
		case 7:
			player.teleport(pmLoc8);
		}
		
		this.TPNb = this.TPNb + 1;
		if(TPNb == 8) {
			this.TPNb = 0;
		}
	}
	
	public void setPacState(PacEnum pacstate) {
		this.PacState = pacstate;
	}
	
	public boolean isPacState(PacEnum pacstate) {
		return (this.PacState == pacstate);
	}
	
	public Player getPacMan() {
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			Player player = Bukkit.getPlayer(players.get(ind));
			if(this.main.pacman.isPacMan(player)) {
				return player;
			}
		}
		return null;
	}
	
	public void revealPacMan(Player player) {
		player.setMaxHealth(this.main.pacman.PacManHearths);
		player.getInventory().setBoots(this.main.ItemUtils.metaLeather(Material.LEATHER_BOOTS, "�eVous �tes Pac-Man !", 1, Color.YELLOW, null));
		player.getInventory().setLeggings(this.main.ItemUtils.metaLeather(Material.LEATHER_LEGGINGS, "�eVous �tes Pac-Man !", 1, Color.YELLOW, null));
		player.getInventory().setChestplate(this.main.ItemUtils.metaLeather(Material.LEATHER_CHESTPLATE, "�eVous �tes Pac-Man !", 1, Color.YELLOW, null));
		player.getInventory().setHelmet(this.main.ItemUtils.skullMeta("�eVous �tes Pac-Man !", "MageGivre"));
		Title.sendTitle(player, 20, 60, 20, "�bVous �tes �ePac-Man !", "�7Ramassez une �cCerise �7pour �liminer les Fant�mes !");
		player.sendMessage("�6�lVous �tes Pac-Man !");
		player.sendMessage("�eEn tant que Pac-Man, vous vous devez de chercher des �ccerises �epour vous rendre plus puissant ! "
				+ "�eCette puissance vous donnera le pouvoir, pendant un temps limit�, d'�liminer des �9fant�mes �e!"
				+ "�eAttention, les �9fant�mes �epeuvent vous attaquer si vous ne poss�dez pas les pouvoirs de la �ccerise �e!");
	}
	
	public void revealGhost(Player player) {
		player.setMaxHealth(2.0D);
		player.getInventory().setBoots(this.main.ItemUtils.metaLeather(Material.LEATHER_BOOTS, "�eVous �tes Pac-Man !", 1, Color.RED, null));
		player.getInventory().setLeggings(this.main.ItemUtils.metaLeather(Material.LEATHER_LEGGINGS, "�eVous �tes Pac-Man !", 1, Color.RED, null));
		player.getInventory().setChestplate(this.main.ItemUtils.metaLeather(Material.LEATHER_CHESTPLATE, "�eVous �tes Pac-Man !", 1, Color.RED, null));
		player.getInventory().setHelmet(this.main.ItemUtils.skullMeta("�eVous �tes Pac-Man !", "PacManEater"));
		Title.sendTitle(player, 20, 60, 20, "�bVous �tes �dun Fant�me !", "�7�liminer Pac-Man avant qu'il ne ramasse une �ccerise �7!");
		player.sendMessage("�9�lVous �tes un Fant�me !");
		player.sendMessage("�eEn tant que fant�me, vous vous devez d'attaquer �6Pac-Man �ejusqu'� ce qu'il meure ! Son �limination vous rapportera des points. "
				+ "En revanche, si ce dernier obtient une �ccerise�e, ce sera � votre tour de courir, si vous tenez � votre vie !");
	}
	
	@SuppressWarnings("deprecation")
	public void spawnCherry() {
		cherry = null;
		
		while (cherry == null) {
			int x = random.nextInt(this.maxX - this.minX) + this.minX;
			int z = random.nextInt(this.maxZ - this.minZ) + this.minZ;
			
			Block cherryBloc = Bukkit.getWorld("world").getBlockAt(x, y, z);
			if(cherryBloc.getType() == Material.AIR) {
				cherryBloc.setTypeIdAndData(Material.SKULL.getId(), (byte)1, true);
				Skull cherrySkull = (Skull) cherryBloc.getState();
				cherrySkull.setOwner("MHF_Apple");
				cherrySkull.setRotation(BlockFace.NORTH);
				cherrySkull.update(true);
				cherry = cherryBloc.getLocation();
				Bukkit.broadcastMessage("�7Une �cCerise �7est apparue sur la Map !");
			}
		}
		
		
	}
	
	public void ghostDeath(Player player) {
		this.main.pacman.ghosts.remove(player.getUniqueId());
		Bukkit.broadcastMessage("�b" + player.getName() + " �es'est fait �liminer ! Il reste �b" + this.main.pacman.ghosts.size() + " �efant�mes en vie.");
		player.setGameMode(GameMode.SPECTATOR);
		
		this.main.ScoreUtils.earnSD(this.getPacMan(), 10, "Fant�me �limin�");
		
		if(this.main.pacman.ghosts.size() == 0) {
			this.victoryPac();
		}
	}
	
	public void pacDeath() {
		this.main.pacman.PacManLives = this.main.pacman.PacManLives - 1;
		int PCL = this.main.pacman.PacManLives;	
		Player pacman = this.getPacMan();
		pacman.setHealth(pacman.getMaxHealth());
		if(PCL == 0) {
			pacman.setGameMode(GameMode.SPECTATOR);
			this.victoryGhost();
		} else {
			Bukkit.broadcastMessage("�eLe joueur �6Pac-Man �es'est fait tuer ! Il lui reste �b" + PCL + " �evies !");
			randomTeleport(getPacMan());
		}
	}
	
	public void cherryActivate() {
		setPacState(PacEnum.CHERRYFULL);
		Player pacman = this.getPacMan();
		this.desacCherry = this.main.score.getTimer() + 10;
		this.main.pacman.FirstCherryTimer = -1;
		pacman.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
		pacman.getInventory().setBoots(this.main.ItemUtils.metaLeatherFakeEnchanted(Material.LEATHER_BOOTS, "�eVous �tes Pac-Man !", 1, Color.ORANGE, null));
		pacman.getInventory().setLeggings(this.main.ItemUtils.metaLeatherFakeEnchanted(Material.LEATHER_LEGGINGS, "�eVous �tes Pac-Man !", 1, Color.ORANGE, null));
		pacman.getInventory().setChestplate(this.main.ItemUtils.metaLeatherFakeEnchanted(Material.LEATHER_CHESTPLATE, "�eVous �tes Pac-Man !", 1, Color.ORANGE, null));
		Title.sendTitle(pacman, 20, 60, 20, "�cCerise !", "�7�liminez les Fant�mes !");
		for (Integer ind = 0; ind < 9; ind++) {
			pacman.getInventory().setItem(ind, this.main.ItemUtils.metaExtraFakeEnchanted(Material.BLAZE_ROD, "�4�liminez tout les fant�mes !", 1, null));
		}
		for (Integer ind = 0; ind < this.main.pacman.ghosts.size(); ind++) {
			Player ghost = Bukkit.getPlayer(this.main.pacman.ghosts.get(ind));
			Title.sendTitle(ghost, 20, 60, 20, "�eFuyez !", "�7Pac-Man a ramass� une cerise, fuyez pour votre vie !");
		}	
	}
	
	public void cherryDeactivate() {
		setPacState(PacEnum.CHERRYLESS);
		Player pacman = this.getPacMan();
		this.main.pacman.FirstCherryTimer = this.main.score.getTimer() + 15;
		for (PotionEffect po : pacman.getActivePotionEffects())
		      pacman.removePotionEffect(po.getType());
		pacman.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, Boolean.valueOf(false)));
		pacman.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2147483647, 0, false, false));
		pacman.getInventory().setBoots(this.main.ItemUtils.metaLeather(Material.LEATHER_BOOTS, "�eVous �tes Pac-Man !", 1, Color.YELLOW, null));
		pacman.getInventory().setLeggings(this.main.ItemUtils.metaLeather(Material.LEATHER_LEGGINGS, "�eVous �tes Pac-Man !", 1, Color.YELLOW, null));
		pacman.getInventory().setChestplate(this.main.ItemUtils.metaLeather(Material.LEATHER_CHESTPLATE, "�eVous �tes Pac-Man !", 1, Color.YELLOW, null));
		Bukkit.broadcastMessage("�7Les effets de la �cCerise �7ont disparu !");
		this.spawnCherry();
		Title.sendTitle(pacman, 20, 60, 20, " ", "�7Une nouvelle cerise va appara�tre !");
		for (Integer ind = 0; ind < 9; ind++) {
			pacman.getInventory().setItem(ind, this.main.ItemUtils.metaVoid());
		}
	}
	
	public void victoryPac() {
		Player pacman = this.getPacMan();
		Bukkit.broadcastMessage("�eLe joueur pacman �b" + pacman.getName() + " �eremporte cette partie !");
		this.setPacState(PacEnum.END);
		this.endGame = this.main.score.getTimer() + 10;
		this.main.ScoreUtils.earnSD(pacman, this.main.pacman.PacManLives*10, "Vie restante");
		this.main.ScoreUtils.earnSD(pacman, 30, "Victoire");
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			if(!(this.main.pacman.isPacMan(Bukkit.getPlayer(players.get(ind))))) {
				Player ghost = Bukkit.getPlayer(players.get(ind));
				this.main.ScoreUtils.earnSD(ghost, Math.round(this.main.score.getTimer()/3), "Temps surv�cu");
			}
			
		}
	}
	
	public void victoryGhost() {
		Bukkit.broadcastMessage("�eLes �bFant�mes �eremporte cette partie !");
		this.setPacState(PacEnum.END);
		this.endGame = this.main.score.getTimer() + 10;
		List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
		for (Integer ind = 0; ind < players.size(); ind++) {
			if(!(this.main.pacman.isPacMan(Bukkit.getPlayer(players.get(ind))))) {
				Player ghost = Bukkit.getPlayer(players.get(ind));
				this.main.ScoreUtils.earnSD(ghost, 30, "Victoire");
			}
			if((this.main.pacman.isPacMan(Bukkit.getPlayer(players.get(ind))))) {
				Player pac = Bukkit.getPlayer(players.get(ind));
				this.main.ScoreUtils.earnSD(pac, Math.round(this.main.score.getTimer()/3), "Temps surv�cu");
			}
			
		}
	}
	
	public void disconnectionHandle(Player player) {
		
		if(this.main.playerhg.containsKey(player.getUniqueId())) {
			if(this.main.pacman.isGhost(player)) {
				ghostDeath(player);
			} else {
				Bukkit.broadcastMessage("�eLe joueur Pac-Man s'est d�connect� ! Par cons�quent...");
				victoryGhost();
			}
		}		
	}
}


