package fr.namu.hg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;

public class playerChatListener implements Listener {

	private MainHG main;
	
	public playerChatListener(MainHG main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		
		event.setCancelled(true);
		if(this.main.playerhg.containsKey(player.getUniqueId())) {
			Bukkit.broadcastMessage("§7[" + phg.getStarDust() + "§7] §e" + playername + " §7» §f" + event.getMessage());
		} else {
			Bukkit.broadcastMessage("§7" + playername + " » " + event.getMessage());
		}
	}
}
