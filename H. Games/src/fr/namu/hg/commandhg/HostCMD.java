package fr.namu.hg.commandhg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import fr.namu.hg.MainHG;

public class HostCMD implements TabExecutor {

	private MainHG main;
	
	public HostCMD(MainHG main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  Player player = (Player)sender;
		    if (args.length == 0)
		      return false; 
		    if (!player.hasPermission("host.use")) {
		    	sender.sendMessage("§eVous n'avez pas les permissions nécessaires pour réaliser cette commande !");
		    	return true;
		    }	    	
		    switch (args[0]) {
		    case "start":
		    	this.main.StartUtils.startGame();
		    default:
		    return true;
		    }
	}
	
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
	    String[] tabe = { "start" };
	    List<String> tab = new ArrayList<>(Arrays.asList(tabe));
	    if (args.length == 0)
	      return tab; 
	    if (args.length == 1) {
	      for (int i = 0; i < tab.size(); i++) {
	        for (int j = 0; j < ((String)tab.get(i)).length() && j < args[0].length(); j++) {
	          if (((String)tab.get(i)).charAt(j) != args[0].charAt(j)) {
	            tab.remove(i);
	            i--;
	            break;
	          } 
	        } 
	      } 
	      return tab;
	    } 
	    return null;
	  }
}
