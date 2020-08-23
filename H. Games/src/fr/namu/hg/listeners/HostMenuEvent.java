package fr.namu.hg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class HostMenuEvent implements Listener {

	private MainHG main;
	
	public HostMenuEvent(MainHG main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(!player.isOp() || !player.getItemInHand().hasItemMeta()) {
			return;
		}
		
		ItemStack current = player.getItemInHand();
		String currentName = current.getItemMeta().getDisplayName();
		
		if(currentName.contains("§eMenu du Host")) {
			this.main.MenuUtils.gameList(player);
		}
	}
	
	@EventHandler
	public void onClickEvent(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
	    Player player = (Player)event.getWhoClicked();
	    ItemStack current = event.getCurrentItem();
		
	    if(current == null || !current.hasItemMeta() || current.getItemMeta().getDisplayName() == null) {
	    	return;
	    }
	    
	    String currentName = current.getItemMeta().getDisplayName();
	    
	    if(inv.getName().contains("§7Liste des jeux")) {
	    	event.setCancelled(true);
	    	
	    	if(currentName.contains("§6Pac-§eMan !")) {
	    		onSwitchActive(GamesHG.PACMAN);
	    	} else if(currentName.contains("§dRun, Piggy, Run !")) {
	    		onSwitchActive(GamesHG.PIGRUN);
	    	} else if (currentName.contains("§fCapture The Sheep !")) {
	    		onSwitchActive(GamesHG.CTS);
	    	} else if (currentName.contains("§eRabbit Race")) {
	    		onSwitchActive(GamesHG.RABBIT_RACE);
	    	} else if (currentName.contains("§cWestern Duels")) {
	    		onSwitchActive(GamesHG.DUEL);
	    	}
	    	
	    	
	    	
	    	this.main.MenuUtils.gameList(player);
	    }
	}
	
	
	private void onSwitchActive(GamesHG game) {
		game.switchActive();
	}
}
