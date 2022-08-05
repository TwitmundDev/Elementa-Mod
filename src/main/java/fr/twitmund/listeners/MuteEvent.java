/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.listeners;

import fr.twitmund.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MuteEvent implements Listener {

    @EventHandler
    public void playerTalkEvent(AsyncPlayerChatEvent e){

        Player player = e.getPlayer();
        Date currentDate = new Date(Main.getInstance().getTimeLeft(player)-System.currentTimeMillis());

        if (Main.getInstance().isPlayerMuted(player)){

            e.setCancelled(true);
            player.sendMessage(Main.getInstance().elementa + "Vous êtes mute par conséquent vous ne pouvez pas parler dans le chat" +
                    "\n Temps restant " + currentDate);

        }

    }

}
