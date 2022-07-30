/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package fr.twitmund.db;

import fr.twitmund.Main;
import fr.twitmund.managers.Warn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Warns {
    private static final String TABLE = "warns";

    public void add(Warn warn){
        Main.getInstance().getMySQL().update("INSERT INTO " + TABLE + " (username,uuid, date, auteur, raison) VALUES (" +
                "'" + warn.getUsername() + "', " +
                "'" + warn.getUUID().toString() + "', " +
                "'" + warn.getDate() + "', " +
                "'" + warn.getAuthor() + "', " +
                "'" + warn.getReason() + "')");
    }

    public void remove(int id){
        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE id='" + id + "'", rs -> {
            try {
                if(rs.next()){
                    rs.deleteRow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Warn getWarn(int id){
        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE id='" + id + "'", rs -> {
            try {
                if(rs.next()){
                    return new Warn(rs.getString("username"),rs.getString("uuid"), rs.getString("date"), rs.getString("auteur"), rs.getString("raison"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        return null;
    }

    // public UUID test = UUID.fromString("672d7d1e3ba44aa7a98c10284cbf2b2f");

    public List<Integer> getIDS(String uuid){
        List<Integer> ids = new ArrayList<>();

        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'", rs -> {
            try {
                while(rs.next()){
                    ids.add(rs.getInt("id"));
                    //Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + Integer.toString(rs.getInt("id")));
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        });

        return ids;
    }

    public List<Warn> getWarns(String uuid){
        List<Warn> warns = new ArrayList<>();

        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "' ORDER BY id ASC", rs -> {
            try {
                while(rs.next()){
                    warns.add(new Warn(rs.getString("username"),rs.getString("uuid"), rs.getString("date"), rs.getString("auteur"), rs.getString("raison")));
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        });

        return warns;
    }
}
