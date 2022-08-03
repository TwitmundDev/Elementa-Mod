package fr.twitmund.managers;

import fr.twitmund.Main;
import fr.twitmund.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    @EventHandler
    public void registers(){
        PluginManager pm  = Bukkit.getPluginManager();
        pm.registerEvents(new ReportInteract(), Main.getInstance());
        pm.registerEvents(new ModCancels(), Main.getInstance());
        pm.registerEvents(new ModItemInteract(), Main.getInstance());
        pm.registerEvents(new ModChat(), Main.getInstance());
        pm.registerEvents(new OnModQuit(), Main.getInstance());
        pm.registerEvents(new SreportInteract(),Main.getInstance());
        pm.registerEvents(new SwarnInteract(),Main.getInstance());
        pm.registerEvents(new CensureEvent(),Main.getInstance());
        pm.registerEvents(new AntiKbEvent(),Main.getInstance());
        //pm.registerEvents(new PlayerPush(),Main.getInstance());
    }
}
