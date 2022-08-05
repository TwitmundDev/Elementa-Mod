/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.utils;

import java.util.HashMap;

public enum TimeUnit {
    SECONDES("Secondes","s",  1),
    MINUTES("Minutes", "min", 60),
    HEURES("Heures", "h",60*60),
    JOUR("Jour","j", 60*60*24),
    MOIS("Mois", "m", 60*60*24*30);


    private String name;
    private String shortcut;
    private long toSecond;

    private static HashMap<String, TimeUnit> ID_SHORTCUT = new HashMap<>();

    private TimeUnit(String name, String shortcut, long toSecond){
        this.name =name;
        this.shortcut = shortcut;
        this.toSecond = toSecond;
    }

    static{
        for (TimeUnit units : values()){
            ID_SHORTCUT.put(units.shortcut , units);
        }
    }

    /**
     * Récupérer le TimeUnit a parire du shortcut
     * @param shortcut
     * @return
     */
    public static TimeUnit getFromShortcut(String shortcut){
        return ID_SHORTCUT.get(shortcut);
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public long getToSecond() {
        return toSecond;
    }
}
