package fr.twitmund.listeners;

import fr.twitmund.Main;
import fr.twitmund.managers.Report;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

public class ReportInteract implements Listener {

    private Map<Player, Long> reportCooldown = new HashMap<>();

    @EventHandler
    public void onInventoryInterract(InventoryClickEvent e){

        if (e.getCurrentItem() == null)return;

        Player player  = (Player) e.getWhoClicked();
        Player target = Bukkit.getPlayer(e.getInventory().getName().substring(13).replace("§f",""));
        String targetRaw = e.getInventory().getName().substring(13).replace("§f","");


        /**if (target == null ){
            e.setCancelled(true);
            player.sendMessage(Main.getInstance().elementa+"Vous ne pouvez pas signaler ce joueur");
        }**/
        if (!e.getInventory().getTitle().contains(ChatColor.GRAY + "Report :")) return;
        switch (e.getCurrentItem().getType()){

            case STAINED_GLASS_PANE:
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(" ")){
                e.setCancelled(true);
                player.closeInventory();
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED  +"A");
            }
                return;

            case ENCHANTED_BOOK:
               /** if (reportCooldown.containsKey(player)) {
                    long time =(System.currentTimeMillis() - reportCooldown.get(player)) /1000;

                    if (time <(60*1)){
                        e.setCancelled(true);
                        player.closeInventory();
                        player.sendMessage(Main.getInstance().elementa+ "Merci de patienter entre chaque reports " + time);

                    }else {
                        reportCooldown.remove(player);
                    }

                }**/

                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "ForceField")){
                    e.setCancelled(true);
                    player.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), target.getName(),player.getDisplayName());
                    player.sendMessage(Main.getInstance().elementa +ChatColor.GRAY +"Vous avez bien signalé ce joueur " +ChatColor.WHITE+ target.getName());
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(target));
                    Main.getInstance().getReports().add(new Report(target.getName(),target.getUniqueId().toString(), player.getDisplayName(), e.getCurrentItem().getItemMeta().getDisplayName().replace("§9", " ")));
                    reportCooldown.put(player, System.currentTimeMillis());
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Insultes")){
                    e.setCancelled(true);
                    player.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), target.getName(),player.getDisplayName());
                    player.sendMessage(Main.getInstance().elementa +ChatColor.GRAY +"Vous avez bien signalé ce joueur " +ChatColor.WHITE+ target.getName());
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(target));
                    Main.getInstance().getReports().add(new Report(target.getName(),target.getUniqueId().toString(), player.getDisplayName(), e.getCurrentItem().getItemMeta().getDisplayName().replace("§9", " ")));
                    reportCooldown.put(player, System.currentTimeMillis());
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Autre")){
                    System.out.println(target);
                    System.out.println(targetRaw);
                    e.setCancelled(true);
                    player.closeInventory();
                    sendToMods(e.getCurrentItem().getItemMeta().getDisplayName(), target.getName(),player.getDisplayName());
                    player.sendMessage(Main.getInstance().elementa +ChatColor.GRAY +"Vous avez bien signalé ce joueur " +ChatColor.WHITE+ target.getName());
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(target));
                    Main.getInstance().getReports().add(new Report(target.getName(),target.getUniqueId().toString(), player.getDisplayName(), e.getCurrentItem().getItemMeta().getDisplayName().replace("§9", " ")));
                    reportCooldown.put(player, System.currentTimeMillis());
                }
                return;

            default :
                break;
        }

    }



    public void sendToMods(String displayName, String targetName, String playerWhoReport){
        for (Player players : Bukkit.getOnlinePlayers()){
            if (players.hasPermission("report.recive")){
                players.playSound(players.getLocation(), Sound.BLOCK_ANVIL_PLACE , 60.0f, 4f);
                players.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Le joueur "+ChatColor.RED + targetName +ChatColor.GRAY + " a été report pour " + ChatColor.WHITE +displayName+ ChatColor.GRAY +" par " + ChatColor.WHITE +playerWhoReport);
            }else {
                return;
            }
        }

    }

}
