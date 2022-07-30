package fr.twitmund.managers;

import fr.twitmund.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class FreezeRunnable extends BukkitRunnable {

    private int i = 0;
    @Override
    public void run() {
        for (UUID uuid: Main.getInstance().getFreezedplayers().keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if ( player!= null){
                Location loc = Main.getInstance().getFreezedplayers().get(uuid);
                player.teleport(loc);

                if (i== 5 ){
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE , 60.0f, 4f);
                    player.sendTitle(ChatColor.RED +"VOUS AVEZ ÉTÉ FREEZE","Ne déconnectez pas",1,50,100);
                    player.sendMessage(Main.getInstance().elementa+"Veuillez rejoindre le discord :" + ChatColor.STRIKETHROUGH+Main.getInstance().discordLink);
                }
            }

        }

        if(i==5) i=0;

        i++;
    }
}
