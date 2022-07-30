package fr.twitmund.listeners;

import fr.twitmund.Main;
import fr.twitmund.managers.PlayerManager;
import fr.twitmund.managers.Report;
import fr.twitmund.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ModItemInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();

        if (!(PlayerManager.isInMod(player))) return;
        if (!(e.getRightClicked()instanceof Player)) return;
        Player target =  (Player) e.getRightClicked();


        switch (player.getInventory().getItemInMainHand().getType()){


            /**
             * InvSee
             */
            case CHEST:
                Inventory inv  = Bukkit.createInventory(null, 5*9, ChatColor.GRAY +"Inventaire de "+ChatColor.RED+target.getDisplayName());


                for (int i = 0; i < 36; i++) {
                    if (target.getInventory().getItem(i) != null){
                        inv.setItem(i, target.getInventory().getItem(i));

                    }

                }
                inv.setItem(36, target.getInventory().getItemInOffHand());
                inv.setItem(37, target.getInventory().getHelmet());
                inv.setItem(38, target.getInventory().getChestplate());
                inv.setItem(39, target.getInventory().getLeggings());
                inv.setItem(40, target.getInventory().getBoots());

                player.openInventory(inv);
                break;


            /**
             * Instant kill
             */
            case STICK:
                target.damage(target.getMaxHealth());
                break;

            /**
             * @TODO Freeze
              */
            case SLIME_BALL:
                   /** if (Main.getInstance().getFreezedplayers().containsKey(target.getUniqueId())){
                        Main.getInstance().getFreezedplayers().remove(target.getUniqueId());
                        target.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 60.0f, 4f);
                        target.sendTitle(ChatColor.RED +"VOUS AVEZ ÉTÉ UNFREEZE","",1,10,20);
                        player.sendMessage(Main.getInstance().elementa + "Vous Avez unfreeze " +ChatColor.BLUE+ target.getName());
                    }else{
                        Main.getInstance().getFreezedplayers().put(target.getUniqueId(), target.getLocation());
                        player.sendMessage(Main.getInstance().elementa + "Vous avez freeze " +ChatColor.BLUE+ target.getName());
                    }**/


               /** if (Main.getInstance().isFreezed(target)){
                    player.sendMessage(Main.getInstance().elementa+"Unfreeze " + target.getName());
                    target.sendTitle(ChatColor.RED +"VOUS AVEZ ÉTÉ UNFREEZE","",1,10,20);
                    target.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 60.0f, 4f);
                    Main.getInstance().freeze.remove(target.getUniqueId());
                }else{
                    target.playSound(player.getLocation(), Sound.ENTITY_WOLF_HURT , 60.0f, 4f);
                    target.sendTitle(ChatColor.RED +"VOUS AVEZ ÉTÉ FREEZE A","",1,10,20);
                    player.sendMessage(Main.getInstance().elementa+"Freeze " + target.getName());
                    Main.getInstance().freeze.put(target.getUniqueId(), target.getLocation());
                }**/
               player.performCommand("freeze " +target.getName());


                /**
                 * @TODO FAIRE LE FREEZE SANS LE BUG QUI EXECUTE LE CODE 2 FOIS
                 */
                break;

            case BOOK:
                List<Report> reports =Main.getInstance().getReports().getReports(target.getUniqueId().toString());

                Inventory invReports  = Bukkit.createInventory(null, 5*9, ChatColor.GRAY +"Reports de "+ChatColor.RED+target.getDisplayName());
                AtomicInteger i = new AtomicInteger();
                reports.forEach(r -> {
                    ItemBuilder report = new ItemBuilder(Material.ENCHANTED_BOOK).setName(ChatColor.WHITE.BOLD + r.getReason()).setLore(ChatColor.DARK_PURPLE +"Report par : "+ChatColor.BLUE + r.getAuthor(),ChatColor.DARK_PURPLE+ "Le : "+ ChatColor.BLUE + r.getDate());
                    invReports.setItem(i.get(), report.toItemStack());
                    i.getAndIncrement();

                });


                player.openInventory(invReports);


                break;


            default:break;
        }


    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if (!(PlayerManager.isInMod(player))) return;
        if (e.getAction()!= Action.RIGHT_CLICK_BLOCK &&e.getAction()!=Action.RIGHT_CLICK_AIR)return;


        switch (player.getInventory().getItemInMainHand().getType()){

            case EYE_OF_ENDER:
                List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                list.remove(player);

                if (list.size() == 0){
                    player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY + "Il n'y a aucun autre joueur sur le serveur");
                    return;
                }
                Player target = list.get(new Random().nextInt(list.size()));
                player.teleport(target.getLocation());
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY + "Vous avez été téléporté à "+ChatColor.WHITE + target.getDisplayName());

                break;



            case BLAZE_POWDER:
                PlayerManager mod = PlayerManager.getFromPlayer(player);
                mod.setVanished(!mod.isVanished());
                player.sendMessage(mod.isVanished() ?Main.getInstance().elementa + ChatColor.GRAY + "Vanish : "+ ChatColor.GREEN + "ON":Main.getInstance().elementa + ChatColor.GRAY + "Vanish : "+ ChatColor.RED + "OFF");
                break;
            default:break;
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        for (Player players : Bukkit.getOnlinePlayers()){
            if (PlayerManager.isInMod(player)){
                PlayerManager pm = PlayerManager.getFromPlayer(players);
                if (pm.isVanished()){
                    player.hidePlayer(players);
                }

            }
        }


    }

    public void FreezePlayer(Player player){
        Main.getInstance().freeze.put(player.getUniqueId(), player.getLocation());
    }

    public void UnFreezePlayer(Player player){
        Main.getInstance().freeze.remove(player.getUniqueId(),player.getLocation());
    }



}
