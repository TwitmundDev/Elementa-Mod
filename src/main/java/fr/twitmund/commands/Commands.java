package fr.twitmund.commands;

import fr.twitmund.Main;
import fr.twitmund.managers.PlayerManager;
import fr.twitmund.managers.Report;
import fr.twitmund.managers.Warn;
import fr.twitmund.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Commands implements CommandExecutor {




    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (arg.equalsIgnoreCase("mod")){
            if (!(player.hasPermission("*"))){
                player.sendMessage(Main.getInstance().elementa +ChatColor.RED + "Vous n'avez pas la permission de faire cela");
                return false;
            }




            if (PlayerManager.isInMod(player)){
                PlayerManager pm = PlayerManager.getFromPlayer(player);
                Main.getInstance().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY + "Mode modération : "+ ChatColor.RED + "OFF");
                if (!(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)){
                    player.setAllowFlight(false);
                    player.setFlying(false);
                }

                pm.giveInventory();
                pm.destroy();

                return false;

            }


            PlayerManager pm = new PlayerManager(player);
            pm.init();
            Main.getInstance().moderateurs.add(player.getUniqueId());
            player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY + "Mode modération : "+ ChatColor.GREEN + "ON");
            player.setAllowFlight(true);
            player.setFlying(true);



            pm.saveInventory();

            ItemBuilder Invsee = new ItemBuilder(Material.CHEST).setName(ChatColor.AQUA + "Invsee").setLore(ChatColor.BLUE + "Permet de voir l'inventaire d'un joueur");
            ItemBuilder Reports = new ItemBuilder(Material.BOOK).setName(ChatColor.AQUA + "Reports").setLore(ChatColor.BLUE + "Voir les reports");
            ItemBuilder Freeze =  new ItemBuilder(Material.SLIME_BALL).setName(ChatColor.AQUA + "Freeze").setLore(ChatColor.BLUE + "Freeze un joueur");
            ItemBuilder kbTesterx5 =  new ItemBuilder(Material.BLAZE_ROD).setName(ChatColor.AQUA + "Kb x5").setLore(ChatColor.BLUE + "Kb un joueur").addUnsafeEnchantment(Enchantment.KNOCKBACK,5);
            ItemBuilder kbTesterx10 =  new ItemBuilder(Material.BLAZE_ROD).setName(ChatColor.AQUA + "Kb x10").setLore(ChatColor.BLUE + "Kb un joueur").addUnsafeEnchantment(Enchantment.KNOCKBACK,10);
            ItemBuilder Kill =  new ItemBuilder(Material.STICK).setName(ChatColor.AQUA + "Instant Killer").setLore(ChatColor.BLUE + "Tue instantanément un joueur");
            ItemBuilder randomTP =  new ItemBuilder(Material.EYE_OF_ENDER).setName(ChatColor.AQUA + "Random TP").setLore(ChatColor.BLUE + "Se téléporte aléatoirement sur un joueur");
            ItemBuilder Vanish =  new ItemBuilder(Material.BLAZE_POWDER).setName(ChatColor.AQUA + "Vanish").setLore(ChatColor.BLUE + "Active / désactive le mode vanish");


            player.getInventory().setItem(0, Invsee.toItemStack());
            player.getInventory().setItem(1, Reports.toItemStack());
            player.getInventory().setItem(2, Freeze.toItemStack());
            player.getInventory().setItem(3, kbTesterx5.toItemStack());
            player.getInventory().setItem(4, kbTesterx10.toItemStack());
            player.getInventory().setItem(5, Kill.toItemStack());
            player.getInventory().setItem(6, randomTP.toItemStack());
            player.getInventory().setItem(7, Vanish.toItemStack());

        }
        if (arg.equalsIgnoreCase("report")){
            if (args.length != 1 ){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Veuillez préciser le pseudo d'un joueur");
                return false;
            }

            String targetName = args[0];

            if (Bukkit.getPlayer(targetName) ==  null){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Le joueur n'est pas en ligne ");
                return false;
            }







            Player target = Bukkit.getPlayer(targetName);

            Inventory inv = Bukkit.createInventory(null, 3*9,  ChatColor.GRAY + "Report :" + ChatColor.RESET+" "+ target.getName());
            ItemStack glassFill =  new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta glassFillMeta = glassFill.getItemMeta();
            glassFillMeta.setDisplayName(" ");
            glassFill.setItemMeta(glassFillMeta);


            for (int i = 0; i < 27; i++) {
                inv.setItem(i,glassFill);
            }
            inv.setItem(12, new ItemBuilder(Material.ENCHANTED_BOOK).setName(ChatColor.BLUE + "ForceField").toItemStack());
            inv.setItem(13, new ItemBuilder(Material.ENCHANTED_BOOK).setName(ChatColor.BLUE + "Insultes").toItemStack());
            inv.setItem(14, new ItemBuilder(Material.ENCHANTED_BOOK).setName(ChatColor.BLUE + "Autre").toItemStack());


            player.openInventory(inv);
        }
        if (arg.equalsIgnoreCase("freeze")){
            if (args.length != 1 ){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Veuillez préciser le pseudo d'un joueur");
                return false;
            }


            String targetName = args[0];

            if (Bukkit.getPlayer(targetName) ==  null){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Le joueur n'est pas en ligne ");
                return false;
            }

            Player target = Bukkit.getPlayer(targetName);


            if (Main.getInstance().isFreezed(target)){
                player.sendMessage(Main.getInstance().elementa+"Unfreeze " + targetName);
                Main.getInstance().freeze.remove(target.getUniqueId());
            }else{
                player.sendMessage(Main.getInstance().elementa+"Freeze " + targetName);
                Main.getInstance().freeze.put(target.getUniqueId(), target.getLocation());
            }



        }
        if (arg.equalsIgnoreCase("nick")){
            player.setDisplayName("1" + "\n" +player.getDisplayName() );

        }
        if (arg.equalsIgnoreCase("sreport")){
            if (args.length != 1 ){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Veuillez préciser le pseudo d'un joueur");
                return false;
            }
            String targetName = args[0];

            if (Bukkit.getPlayer(targetName) ==  null){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Le joueur n'est pas en ligne ");
                return false;
            }
            Player target = Bukkit.getPlayer(targetName);

            List<Report> reports = Main.getInstance().getReports().getReports(target.getUniqueId().toString());

            Inventory invReports  = Bukkit.createInventory(null, 5*9, ChatColor.GRAY +"Reports de "+ChatColor.RED+target.getDisplayName());
            AtomicInteger i = new AtomicInteger();
            reports.forEach(r -> {
                ItemBuilder report = new ItemBuilder(Material.ENCHANTED_BOOK).setName(ChatColor.WHITE.BOLD + r.getReason()).setLore(ChatColor.DARK_PURPLE +"Report par : "+ChatColor.BLUE + r.getAuthor(),ChatColor.DARK_PURPLE+ "Le : "+ ChatColor.BLUE + r.getDate());
                invReports.setItem(i.get(), report.toItemStack());
                i.getAndIncrement();

            });


            player.openInventory(invReports);




        }


        if (arg.equalsIgnoreCase("warn")){

            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);
            String reason = "";

            for (int i = 1; i <args.length ; i++) {
                reason = reason +" "+ args[i];
            }

            if (args.length < 1 ){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Veuillez préciser le pseudo d'un joueur");
                return false;
            }

            if (Bukkit.getPlayer(targetName) ==  null){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Le joueur n'est pas en ligne ");
                return false;
            }

            Main.getInstance().getWarns().add(new Warn(targetName, target.getUniqueId().toString(), player.getName(), reason));


            target.playSound(player.getLocation(), Sound.AMBIENT_CAVE , 60.0f, 4f);
            target.sendTitle(ChatColor.RED +"VOUS AVEZ ÉTÉ WARN POUR",ChatColor.GRAY+reason,1,50,100);



        }

        if (arg.equalsIgnoreCase("swarn")){
            if (args.length != 1 ){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Veuillez préciser le pseudo d'un joueur");
                return false;
            }
            String targetName = args[0];

            if (Bukkit.getPlayer(targetName) ==  null){
                player.sendMessage(Main.getInstance().elementa + ChatColor.GRAY +"Le joueur n'est pas en ligne ");
                return false;
            }
            Player target = Bukkit.getPlayer(targetName);

            List<Warn> warns = Main.getInstance().getWarns().getWarns(target.getUniqueId().toString());


            Inventory invWarns  = Bukkit.createInventory(null, 5*9, ChatColor.GRAY +"Warns de "+ChatColor.RED+target.getDisplayName());
            AtomicInteger i = new AtomicInteger();
            warns.forEach(r -> {
                ItemBuilder report = new ItemBuilder(Material.ENCHANTED_BOOK).setName(ChatColor.WHITE.BOLD + r.getReason()).setLore(ChatColor.DARK_PURPLE +"Warn par : "+ChatColor.BLUE + r.getAuthor(),ChatColor.DARK_PURPLE+ "Le : "+ ChatColor.BLUE + r.getDate());
                invWarns.setItem(i.get(), report.toItemStack());
                i.getAndIncrement();

            });


            player.openInventory(invWarns);




        }


        if (arg.equalsIgnoreCase("push")){

        }



        return false;
    }
}
