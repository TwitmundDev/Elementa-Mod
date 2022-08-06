/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.anticheat.module.combat;

import fr.twitmund.Main;
import fr.twitmund.anticheat.Check;
import fr.twitmund.anticheat.event.CombatEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.DecimalFormat;

public class ReachA implements Listener {
    public Location lastPos;





    @EventHandler
    public void onCombatEvent(EntityDamageByEntityEvent e) {

        //Bukkit.getConsoleSender().sendMessage("A");

        if (this.lastPos != null) {
            double delta = this.lastPos.distance(e.getDamager().getLocation());
            double reach = e.getEntity().getLocation().distance(e.getDamager().getLocation());
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            String reachNotification = numberFormat.format(  reach );
            double distanceBetween = e.getEntity().getLocation().getZ() - e.getDamager().getLocation().getZ();
            if (reach > 3.20D + delta) {
                Bukkit.broadcastMessage("" + distanceBetween);
                Main.getInstance().notification("reach" ,(Player) e.getDamager() , 2 , reachNotification);
                e.setCancelled(true);
            }
        }
        this.lastPos = e.getDamager().getLocation();
    }
}
