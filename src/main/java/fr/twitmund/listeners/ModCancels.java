package fr.twitmund.listeners;

import fr.twitmund.Main;
import fr.twitmund.managers.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ModCancels implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        e.setCancelled(PlayerManager.isInMod(e.getPlayer())||Main.getInstance().isFreezed(e.getPlayer()));
    }
    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e){
        e.setCancelled(PlayerManager.isInMod(e.getPlayer())||Main.getInstance().isFreezed(e.getPlayer()));
    }
    @EventHandler
    public void onBlockBroke(BlockBreakEvent e){
        e.setCancelled(PlayerManager.isInMod(e.getPlayer())||Main.getInstance().isFreezed(e.getPlayer()));
    }
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        e.setCancelled(PlayerManager.isInMod(e.getPlayer())||Main.getInstance().isFreezed(e.getPlayer()));
    }
    @EventHandler
    public void onItemDropped(PlayerDropItemEvent e){
        e.setCancelled(PlayerManager.isInMod(e.getPlayer())||Main.getInstance().isFreezed(e.getPlayer()));
    }





    @EventHandler
    public void onEntityDamaged(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        e.setCancelled(PlayerManager.isInMod((Player) e.getEntity())||Main.getInstance().isFreezed((Player) e.getEntity()));
    }
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;
        Player damager= (Player) e.getDamager();

        if (PlayerManager.isInMod(damager)){
            e.setCancelled(damager.getInventory().getItemInHand().getType()!= Material.STICK);
        }
    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        e.setCancelled(PlayerManager.isInMod( e.getPlayer())||Main.getInstance().isFreezed(e.getPlayer()));
    }
    @EventHandler
    public void onInventoryClickedEvent(InventoryClickEvent e){
        e.setCancelled(PlayerManager.isInMod((Player) e.getWhoClicked()));
    }


    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (Main.getInstance().getFreezedplayers().containsKey(e.getPlayer().getUniqueId())){
            Player player =e.getPlayer();
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE , 60.0f, 4f);
            player.sendTitle(ChatColor.RED +"VOUS AVEZ ÉTÉ FREEZE","Ne déconnectez pas",1,50,100);
            player.sendMessage(Main.getInstance().elementa+"Veuillez rejoindre le discord :" + ChatColor.BOLD +ChatColor.RED +Main.getInstance().discordLink);
            e.setTo(e.getFrom());

        }
    }
}
