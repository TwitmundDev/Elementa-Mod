/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.anticheat;

import fr.twitmund.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Check {

    private static Check instance;




    public void notification(@NotNull String notification,@NotNull Player player , String warningLvl, String reachlvl){
        instance = this;
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("elementa.Wanrings")) {

                switch (notification){
                    case "reach":
                        players.sendMessage( Main.getInstance().elementa + ChatColor.GRAY + "Le joueur : "
                                + ChatColor.RED + ChatColor.BOLD + player.getDisplayName()
                                +ChatColor.RESET + ChatColor.GRAY + "est suspecter de " + notification + " " + warningLvl+
                                "\n Reach de " + reachlvl);
                }




                players.sendMessage( Main.getInstance().elementa + ChatColor.GRAY + "Le joueur : " + ChatColor.RED + ChatColor.BOLD + player.getDisplayName()
                        +ChatColor.RESET + ChatColor.GRAY + "est suspecter de " + notification + " " + warningLvl);
            }
        }
    }


    public static Check getInstance(){
        return instance;
    }

}
