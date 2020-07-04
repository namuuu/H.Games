package fr.namu.hg.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.namu.hg.MainHG;
import fr.namu.hg.enumhg.GamesHG;

public class MenuUtils {
	
	private MainHG main;

	public MenuUtils(MainHG main) {
		this.main = main;
	}
	
	public void gameList(Player player) {
		Inventory inv = Bukkit.createInventory(null, 6*9, "§7Liste des jeux");
		
		inv.setItem(10, this.main.ItemUtils.metaExtra(Material.GOLD_BLOCK, "§6Pac-§eMan !", 1, isGameActive(GamesHG.PACMAN)));
		inv.setItem(11, this.main.ItemUtils.metaExtra(Material.SADDLE, "§dRun, Piggy, Run !", 1, isGameActive(GamesHG.PIGRUN)));
		inv.setItem(12, this.main.ItemUtils.metaExtra(Material.WOOL, "§fCapture The Sheep !", 1, isGameActive(GamesHG.CTS)));
		inv.setItem(13, this.main.ItemUtils.metaExtra(Material.RABBIT_FOOT, "§eRabbit Race", 1, isGameActive(GamesHG.RABBIT_RACE)));
		
		player.openInventory(inv);
	}
	
	
	
	
	
	
	
	
	
	
	
	public String[] isGameActive(GamesHG game) {
		if(game.getActive() == true) {
			  return new String[] {"§aActivé", "§7Clique-moi dessus pour me désactiver !"};
		} else {
			  return new String[] {"§cDésactivé", "§7Clique-moi dessus pour m'activer !"};
		}
	}

}
