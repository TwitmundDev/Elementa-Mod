package fr.twitmund.listeners;

import fr.twitmund.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ModChat implements Listener {
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        if(e.getMessage().startsWith("$") && player.hasPermission("elementa.staffchat.send")){
            e.setCancelled(true);
            for (Player players : Bukkit.getOnlinePlayers()){
                if (players.hasPermission("elemente.staffchat.read")){

                    players.sendMessage(Main.getInstance().StaffChat + ChatColor.LIGHT_PURPLE +player.getName() +ChatColor.WHITE+ ": "+e.getMessage().substring(1 ));
                    /**
                     * @TODO send message
                     */
                }
            }


        }

    }
}
