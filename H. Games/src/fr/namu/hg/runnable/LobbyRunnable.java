package fr.namu.hg.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.StateHG;

public class LobbyRunnable extends BukkitRunnable {

	private final MainHG main;
	
	public LobbyRunnable(MainHG main) {
		this.main = main;
	}
	
	public void run() {
		
		this.main.score.updateBoard();
		
		if (this.main.LobbyUtils.getRequiredLeft() == 0) {
			this.main.StartUtils.startGame();
		}
		
		if(!(this.main.isState(StateHG.LOBBY))) {
			cancel();
		}
		
	}
}
