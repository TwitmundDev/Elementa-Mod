/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.listeners;

import fr.twitmund.Main;
import fr.twitmund.managers.Warn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.List;

public class CensureEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();


        if (Main.getInstance().isPlayerMuted(player)){
            return;

        }

        final List<String> bannedWord = Arrays.asList(
                "Connard",
                "fdp",
                "FDP",
                "Fils de pute",
                "DDOS",
                "DD0S",
                "Hack",
                "tg",
                "Tg",
                "Ta geule");


        for (String msg : e.getMessage().split(" ")) {
            if (bannedWord.contains(msg)) {
                e.setCancelled(true);
                Main.getInstance().getWarns().add(new Warn(playerName,
                        player.getUniqueId().toString(),
                        "Console",
                        "Propos innapropriée :" + msg));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 60.0f, 4f);
                player.sendMessage(Main.getInstance().elementa + "Vous avez été warn pour Propos innapropriée \n " +
                        "Au bout de plusieur warn une sanction peut être appliquée");


                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (players.hasPermission("elementa.Wanrings")) {
                        players.sendMessage( Main.getInstance().elementa + ChatColor.GRAY + "Le joueur " + player.getDisplayName() +
                                " à été warn pour Propos innapropriée mot utilisée " + msg);
                    }
                }
            }
        }


    }
}




