package fr.namu.hg;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.namu.hg.commandhg.HostCMD;
import fr.namu.hg.enumhg.ConfigurationHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.enumhg.StateHG;
import fr.namu.hg.games.pacman.PacMan;
import fr.namu.hg.listeners.*;
import fr.namu.hg.runnable.IntergameRunnable;
import fr.namu.hg.runnable.LobbyRunnable;
import fr.namu.hg.runnable.PacmanRunnable;
import fr.namu.hg.scoreboard.*;
import fr.namu.hg.utils.*;
import fr.namu.hg.games.cts.CTSheepUtils;
import fr.namu.hg.games.pacman.*;
import fr.namu.hg.games.pigrun.*;

public class MainHG extends JavaPlugin {

	public final Map<UUID, FastBoard> boards = new HashMap<>();
	public final Map<UUID, PlayerHG> playerhg = new HashMap<>();
	
	public final GeneralUtils GeneralUtils = new GeneralUtils(this);
	public final LobbyUtils LobbyUtils = new LobbyUtils(this);
	public final ItemStackUtils ItemUtils = new ItemStackUtils(this);
	public final StartUtils StartUtils = new StartUtils(this);
	public final ScoreUtils ScoreUtils = new ScoreUtils(this);
	
	public final ScoreBoardHG score = new ScoreBoardHG(this);
	
	public final PacMan pacman = new PacMan(this);
	public final PacManUtils PacManUtils = new PacManUtils(this);
	public final PacmanRunnable PacManRunnable = new PacmanRunnable(this);
	
	public final PigRun PigRun = new PigRun(this);
	public final PigRunUtils PigRunUtils = new PigRunUtils(this);
	
	public final CTSheepUtils CTSUtils = new CTSheepUtils(this);
	
	public final IntergameRunnable Intergame = new IntergameRunnable(this);
	
	private StateHG state;
	private GamesHG game;
	public ConfigurationHG config;
	public UUID hostName = null;
	
	@Override
	public void onEnable() {
		System.out.println("[H.GAMES] H.Games plugin has been enabled.");
		
		setState(StateHG.LOBBY);
		setGame(GamesHG.NULL);
		this.GeneralUtils.setBaseConfig();
		listenerEnabler();
		gameruleEnabler();
		commandEnabler();
		
		
		LobbyRunnable startLobby = new LobbyRunnable(this);
	    startLobby.runTaskTimer((Plugin)this, 0L, 20L);
	}
	
	@Override
	public void onDisable() {
		System.out.println("[H.GAMES] H.Games plugin has been disabled");
	}
	
	private void listenerEnabler() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents((Listener)new JoinLeaveEvent(this), (Plugin)this);
		pm.registerEvents((Listener)new PlayerDamageEvent(this), (Plugin)this);
		pm.registerEvents((Listener)new PacManListener(this), (Plugin)this);
		pm.registerEvents((Listener)new PigRunListener(this), (Plugin)this);
	}  // pm.registerEvents((Listener)new PlayerListener(this), (Plugin)this);
	
	private void gameruleEnabler() {
		World world = Bukkit.getWorld("world");
		world.setGameRuleValue("reducedDebugInfo", "false");
	    world.setGameRuleValue("keepInventory", "true");
	    world.setGameRuleValue("naturalRegeneration", "false");
	    world.setGameRuleValue("randomTickSpeed", "20");
	    world.setGameRuleValue("announceAdvancements", "false");
	    world.setGameRuleValue("doDaylightCycle", "false");
	    world.setGameRuleValue("doMobSpawning", "false");
	    world.setGameRuleValue("commandBlockOutput", "false");
	    world.setGameRuleValue("logAdminCommands", "false");
	}
	
	private void commandEnabler() {
		getCommand("hg").setExecutor((CommandExecutor)new HostCMD(this));
	}
	
	public void setState(StateHG state) {
		this.state = state;
	}
	
	public boolean isState(StateHG state) {
	    return (this.state == state);
	  }
	
	public void setGame(GamesHG game) {
		this.game = game;
	}
	
	public boolean isGame(GamesHG game) {
		return (this.game == game);
	}
	
	public boolean isConfig(ConfigurationHG config) {
		return (this.config == config);
	}
	
}
