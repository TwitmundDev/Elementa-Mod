package fr.twitmund;

import fr.twitmund.commands.Commands;
import fr.twitmund.db.Mysql;
import fr.twitmund.db.Reports;
import fr.twitmund.db.Warns;
import fr.twitmund.managers.EventManager;
import fr.twitmund.managers.PlayerManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class Main extends JavaPlugin {


    private Warns warns;
    private Reports reports;
    private BasicDataSource connectionPool;
    private Mysql mysql;
    public String elementatxt = "Elementa";
    public String elementa = chatColoredInCrochet(elementatxt, ChatColor.DARK_BLUE);
    public String StaffChat = chatColoredInCrochet("StaffChat", ChatColor.DARK_BLUE);
    public String discordLink = "https://discord.gg/SYDw4GeQpp";


    public ArrayList<UUID> moderateurs  =  new ArrayList<>();
    public HashMap<UUID, PlayerManager> players  = new HashMap<>();
    public HashMap<UUID, Long> mutedPlayers  = new HashMap<UUID, Long>();
    public Map<UUID, Location> freeze = new HashMap<>();


    private static Main instance;




    @Override
    public void onEnable() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        instance = this ;
        getCommand("mod").setExecutor(new Commands());
        getCommand("report").setExecutor(new Commands());
        getCommand("freeze").setExecutor(new Commands());
        getCommand("nick").setExecutor(new Commands());
        getCommand("sreport").setExecutor(new Commands());
        getCommand("warn").setExecutor(new Commands());
        getCommand("swarn").setExecutor(new Commands());
        getCommand("push").setExecutor(new Commands());
        /*
        Commandes de mute
         */
        getCommand("mute").setExecutor(new Commands());
        getCommand("showmuted").setExecutor(new Commands());
        getCommand("ismuted").setExecutor(new Commands());
        getCommand("unmute").setExecutor(new Commands());
        getCommand("timelast").setExecutor(new Commands());


        //Commandes Utilitaires
        getCommand("heal").setExecutor(new Commands());
        getCommand("s").setExecutor(new Commands());


        new EventManager().registers();
        initConnection();
        reports = new Reports();
        warns = new Warns();
        //reports.add(new Report(UUID.fromString("327bb8a3-7ff4-48f8-a2fb-912bcfc3626e"), "Moderateur", "Triche 2"));
        //reports.getFromUUID(UUID.fromString("327bb8a3-7ff4-48f8-a2fb-912bcfc3626e"));

        //new FreezeRunnable().runTaskTimer(this, 0, 20);
    }

    private void initConnection(){
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionPool.setUsername("root");
        connectionPool.setPassword("root");
        connectionPool.setUrl("jdbc:mysql://127.0.0.1:3306/mod?autoReconnect=true");
        connectionPool.setInitialSize(1);
        connectionPool.setMaxTotal(10);
        mysql = new Mysql(connectionPool);
        mysql.createTables();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().stream().filter(PlayerManager::isInMod).forEach(p -> {
            if (PlayerManager.isInMod(p)){
                PlayerManager.getFromPlayer(p).destroy();
            }
        });

    }
    public String chatColoredInCrochet(String n , ChatColor couleur){
        String SChrochet =ChatColor.GRAY + "[" + couleur+ n +ChatColor.GRAY + "] " + ChatColor.RESET;
        return SChrochet;

    }




    public void notification(@NotNull String notification,@NotNull Player player , double warningLvl, String reachlvl){
        instance = this;
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("elementa.Wanrings")) {

                switch (notification){
                    case "reach":
                        players.sendMessage( Main.getInstance().elementa + ChatColor.GRAY + "Le joueur : "
                                + ChatColor.RED + ChatColor.BOLD + player.getDisplayName()
                                +ChatColor.RESET + ChatColor.GRAY + " est suspecter de " + notification + " " + warningLvl+
                                "\n Reach de " + reachlvl);
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 60.0f, 4f);
                            break;
                    default:
                        players.sendMessage( Main.getInstance().elementa + ChatColor.GRAY + "Le joueur : " + ChatColor.RED + ChatColor.BOLD + player.getDisplayName()
                                +ChatColor.RESET + ChatColor.GRAY + "est suspecter de " + notification + " " + warningLvl);
                        break;
                }


            }
        }


    }




    /**
     *
     * @GETTER
     */

    public Mysql getMysql() {
        return mysql;
    }

    public static Main getInstance(){
        return instance;
    }

    public ArrayList<UUID> getModerateurs() {
        return moderateurs;
    }

    public void setWarns(Warns warns) {
        this.warns = warns;
    }

    public Warns getWarns() {
        return warns;
    }

    public HashMap<UUID, PlayerManager> getPlayers() {
        return players;
    }

    public Map<UUID, Location> getFreezedplayers() {
        return freeze;
    }
    
    public boolean isFreezed(Player player){
        return getFreezedplayers().containsKey(player.getUniqueId());
    }

    public Mysql getMySQL() {return mysql;}

    public Reports getReports() {
        return reports;
    }



    public HashMap<UUID, Long> getMutedPlayers() {
        return mutedPlayers;
    }

    public long getTimeLeft(@NotNull Player player){
        UUID playerUUID = player.getUniqueId();


        long timeLeftParse =  mutedPlayers.get(playerUUID);
        long timeleft = timeLeftParse - System.currentTimeMillis();



        return timeleft;
    }
    public boolean isPlayerMuted(@NotNull Player player){
        boolean isMuted = false;
        if (getMutedPlayers().containsKey(player.getUniqueId())){
            isMuted = true;
        }else {
            isMuted =false;
        }

        return isMuted;
    }


}




