/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class SreportInteract implements Listener {

    @EventHandler
    public void inInventoryInterract(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;



        if (!e.getInventory().getTitle().contains(ChatColor.GRAY + "Reports de")) return;
            if (e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
               // Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "A");
                e.setCancelled(true);
            }

    }

}
