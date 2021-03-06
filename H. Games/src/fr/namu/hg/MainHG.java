package fr.namu.hg;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
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
import fr.namu.hg.runnable.DuelRunnable;
import fr.namu.hg.runnable.IntergameRunnable;
import fr.namu.hg.runnable.LobbyRunnable;
import fr.namu.hg.runnable.PacmanRunnable;
import fr.namu.hg.runnable.RabbitRunnable;
import fr.namu.hg.scoreboard.*;
import fr.namu.hg.utils.*;
import fr.redline.serverclient.minijeux.MiniJeux;
import fr.namu.hg.games.cts.CTSheep;
import fr.namu.hg.games.cts.CTSheepUtils;
import fr.namu.hg.games.duel.WDuel;
import fr.namu.hg.games.duel.WDuelUtils;
import fr.namu.hg.games.pacman.*;
import fr.namu.hg.games.pigrun.*;
import fr.namu.hg.games.rabbitrun.RabbitRun;
import fr.namu.hg.games.rabbitrun.RabbitRunUtils;

public class MainHG extends JavaPlugin {

	public final Map<UUID, FastBoard> boards = new HashMap<>();
	public final Map<UUID, PlayerHG> playerhg = new HashMap<>();
	
	public final GeneralUtils GeneralUtils = new GeneralUtils(this);
	public final LobbyUtils LobbyUtils = new LobbyUtils(this);
	public final ItemStackUtils ItemUtils = new ItemStackUtils(this);
	public final StartUtils StartUtils = new StartUtils(this);
	public final ScoreUtils ScoreUtils = new ScoreUtils(this);
	public final MenuUtils MenuUtils = new MenuUtils(this);
	
	public MiniJeux mjc = null;
	
	public final ScoreBoardHG score = new ScoreBoardHG(this);
	
	public final PacMan pacman = new PacMan(this);
	public final PacManUtils PacManUtils = new PacManUtils(this);
	public final PacmanRunnable PacManRunnable = new PacmanRunnable(this);
	
	public final PigRun PigRun = new PigRun(this);
	public final PigRunUtils PigRunUtils = new PigRunUtils(this);
	
	public final CTSheep CTS = new CTSheep(this);
	public final CTSheepUtils CTSUtils = new CTSheepUtils(this);
	
	public final RabbitRun RR = new RabbitRun(this);
	public final RabbitRunUtils RRUtils =  new RabbitRunUtils(this);
	public final RabbitRunnable RabbitRunnable = new RabbitRunnable(this);
	
	public final WDuel Duel = new WDuel(this);
	public final WDuelUtils DuelUtils = new WDuelUtils(this);
	public final DuelRunnable DuelRun = new DuelRunnable(this);
	
	public final IntergameRunnable Intergame = new IntergameRunnable(this);
	
	private StateHG state;
	private GamesHG game;
	public ConfigurationHG config;
	public UUID hostName = null;
	
	@Override
	public void onEnable() {
		System.out.println("[HGames] H.Games plugin has been enabled.");
		/*mjc = StartSC.createMiniJeux("H.Games", "Party", "Default", 8, Boolean.valueOf(true), Boolean.valueOf(false));*/
		
		setState(StateHG.LOBBY);
		setGame(GamesHG.NULL);
		this.GeneralUtils.setBaseConfig();
		listenerEnabler();
		gameruleEnabler();
		commandEnabler();
		/*mjc.registerToServer();*/
		
		LobbyRunnable startLobby = new LobbyRunnable(this);
	    startLobby.runTaskTimer((Plugin)this, 0L, 20L);
	}
	
	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("�eReload du serveur ! �fVous pouvez vous reconnecter dans quelques secondes...");
		}		
		/*this.mjc.unregisterToServer();*/
		System.out.println("[HGames] H.Games plugin has been disabled");
	}
	
	private void listenerEnabler() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents((Listener)new JoinLeaveEvent(this), (Plugin)this);
		pm.registerEvents((Listener)new PlayerDamageEvent(this), (Plugin)this);
		pm.registerEvents((Listener)new HostMenuEvent(this), (Plugin)this);
		pm.registerEvents((Listener)new playerChatListener(this), (Plugin)this);
		pm.registerEvents((Listener)new PacManListener(this), (Plugin)this);
		pm.registerEvents((Listener)new PigRunListener(this), (Plugin)this);
		pm.registerEvents((Listener)new CTSListener(this), (Plugin)this);
		pm.registerEvents((Listener)new RabbitRunListener(this), (Plugin)this);
		pm.registerEvents((Listener)new DuelListener(this), (Plugin)this);
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
	    world.setSpawnLocation(0, 90, 0);
	    if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("player") == null) {
	    	Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("player");
	    }	    
	}
	
	private void commandEnabler() {
		getCommand("hg").setExecutor((CommandExecutor)new HostCMD(this));
		getCommand("hgames").setExecutor((CommandExecutor)new HostCMD(this));
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
