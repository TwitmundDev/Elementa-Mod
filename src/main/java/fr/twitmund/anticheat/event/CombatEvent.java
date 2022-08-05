/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.anticheat.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatEvent {
    public Player player;

    public Entity attacked;

    public EntityDamageByEntityEvent event;

    public boolean cancel;

    public CombatEvent(EntityDamageByEntityEvent event) {
        this.player = (Player)event.getDamager();
        this.attacked = event.getEntity();
        this.event = event;
    }
}
