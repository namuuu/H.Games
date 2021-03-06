package fr.namu.hg.utils;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.namu.hg.MainHG;
import net.minecraft.server.v1_8_R3.NBTTagCompound;


public class ItemStackUtils {

	  public ItemStackUtils(MainHG main) {
	}

	public ItemStack metaLeather(Material m, String ItemName, int Amount, Color color, String[] lore) {
		    ItemStack item = new ItemStack(m, Amount);
		    LeatherArmorMeta im = (LeatherArmorMeta)item.getItemMeta();
		    im.setDisplayName(ItemName);
		    im.setColor(color);
		    if(lore != null)
		    	im.setLore(Arrays.asList(lore));
		    item.setItemMeta((ItemMeta)im);
		    return item;
		  }
	
	public void setLeatherColored(Player p, Color color) {
		p.getInventory().setHelmet(metaLeather(Material.LEATHER_HELMET, "�eCasque en cuir", 1, color, null));
		p.getInventory().setChestplate(metaLeather(Material.LEATHER_CHESTPLATE, "�ePlastron en cuir", 1, color, null));
		p.getInventory().setLeggings(metaLeather(Material.LEATHER_LEGGINGS, "�eJambi�res en cuir", 1, color, null));
		p.getInventory().setBoots(metaLeather(Material.LEATHER_BOOTS, "�eBottes en cuir", 1, color, null));
	}
	
	public ItemStack metaLeatherFakeEnchanted(Material m, String ItemName, int Amount, Color color, String[] lore) {
	    ItemStack item = new ItemStack(m, Amount);
	    LeatherArmorMeta im = (LeatherArmorMeta)item.getItemMeta();
	    im.setDisplayName(ItemName);
	    im.addEnchant(Enchantment.DURABILITY, 1, Boolean.valueOf(true));
	    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    im.setColor(color);
	    if(lore != null)
	    	im.setLore(Arrays.asList(lore));
	    item.setItemMeta((ItemMeta)im);
	    return item;
	  }
	
	public ItemStack metaExtraUnbreakable(Material m, String ItemName, int Amount, String[] lore) {
		ItemStack item = new ItemStack(m, Amount);
	    ItemMeta im = item.getItemMeta();
	    im.setDisplayName(ItemName);
	    im.addEnchant(Enchantment.DURABILITY, 1, Boolean.valueOf(true));
	    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    im.spigot().setUnbreakable(true);
	    if(lore != null)
	    	im.setLore(Arrays.asList(lore));
	    item.setItemMeta(im);
	    return item;
	}
	  
	  public ItemStack metaExtra(Material m, String ItemName, int Amount, String[] lore) {
		    ItemStack item = new ItemStack(m, Amount);
		    ItemMeta im = item.getItemMeta();
		    im.setDisplayName(ItemName);
		    if(lore != null)
		    	im.setLore(Arrays.asList(lore));		        
		    item.setItemMeta(im);
		    return item;
		  }
	  
	  public ItemStack metaExtraFakeEnchanted(Material m, String ItemName, int Amount, String[] lore) {
		    ItemStack item = new ItemStack(m, Amount);
		    ItemMeta im = item.getItemMeta();
		    im.addEnchant(Enchantment.DURABILITY, 1, Boolean.valueOf(true));
		    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		    im.setDisplayName(ItemName);
		    if(lore != null)
		    	im.setLore(Arrays.asList(lore));		        
		    item.setItemMeta(im);
		    return item;
		  }
	  
	  public ItemStack skullMeta(String ItemName, String PlayerName) {
		  ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		  SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
		  skullMeta.setOwner(PlayerName);
		  skullMeta.setDisplayName(ItemName);
		  skull.setItemMeta((ItemMeta)skullMeta);
		  return skull;
	  } //skullMeta("", "")
	  
	  public ItemStack metaVoid() {
		  ItemStack voidItem = new ItemStack(Material.AIR, 1);
		  return voidItem;
	  }
	  
		public static void removeAI(Entity entity) {
	        net.minecraft.server.v1_8_R3.Entity nmsEnt = ((CraftEntity) entity).getHandle();
	        NBTTagCompound tag = nmsEnt.getNBTTag();

	        if (tag == null) {
	            tag = new NBTTagCompound();
	        }

	        nmsEnt.c(tag);
	        tag.setInt("NoAI", 1);
	        nmsEnt.f(tag);
	    }
	  
}
