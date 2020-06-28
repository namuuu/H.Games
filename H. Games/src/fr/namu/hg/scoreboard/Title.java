package fr.namu.hg.scoreboard;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
    PlayerConnection connection = (((CraftPlayer)player).getHandle()).playerConnection;
    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    connection.sendPacket((Packet<?>)packetPlayOutTimes);
    if (subtitle != null) {
      subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
      connection.sendPacket((Packet<?>)packetPlayOutSubTitle);
    } 
    if (title != null) {
      title = title.replaceAll("%player%", player.getDisplayName());
      title = ChatColor.translateAlternateColorCodes('&', title);
      IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
      PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
      connection.sendPacket((Packet<?>)packetPlayOutTitle);
    } 
  }
  
  public static void sendTabTitle(Player player, String header1, String header2, String header3, String footer, String headerSpace, String footerSpace) {
    if (header1 == null)
      header1 = ""; 
    header1 = ChatColor.translateAlternateColorCodes('&', header1);
    if (header2 == null)
      header2 = ""; 
    header2 = ChatColor.translateAlternateColorCodes('&', header2);
    if (header3 == null)
      header3 = ""; 
    header3 = ChatColor.translateAlternateColorCodes('&', header3);
    if (headerSpace == null)
      headerSpace = ""; 
    headerSpace = ChatColor.translateAlternateColorCodes('&', headerSpace);
    if (footer == null)
      footer = ""; 
    footer = ChatColor.translateAlternateColorCodes('&', footer);
    if (footerSpace == null)
      footerSpace = ""; 
    footerSpace = ChatColor.translateAlternateColorCodes('&', footerSpace);
    header1 = header1.replaceAll("%player%", player.getDisplayName());
    header2 = header2.replaceAll("%player%", player.getDisplayName());
    header3 = header3.replaceAll("%player%", player.getDisplayName());
    headerSpace = headerSpace.replaceAll("%player%", player.getDisplayName());
    footer = footer.replaceAll("%player%", player.getDisplayName());
    footerSpace = footerSpace.replaceAll("%player%", player.getDisplayName());
    PlayerConnection connection = (((CraftPlayer)player).getHandle()).playerConnection;
    IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header1 + "\n" + header2 + "\n" + header3 + "\n" + headerSpace + "\"}");
    IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footerSpace + "\n" + footer + "\"}");
    PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
    try {
      Field field = headerPacket.getClass().getDeclaredField("b");
      field.setAccessible(true);
      field.set(headerPacket, tabFoot);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      connection.sendPacket((Packet<?>)headerPacket);
    } 
  }
  
  public static void sendActionBar(Player player, String message) {
    CraftPlayer p = (CraftPlayer)player;
    IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
    (p.getHandle()).playerConnection.sendPacket((Packet<?>)ppoc);
  }
}
