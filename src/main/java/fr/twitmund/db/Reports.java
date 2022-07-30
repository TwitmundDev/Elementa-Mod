package fr.twitmund.db;

import fr.twitmund.Main;
import fr.twitmund.managers.Report;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Reports {

    private static final String TABLE = "reports";

    public void add(Report report){
        Main.getInstance().getMySQL().update("INSERT INTO " + TABLE + " (username,uuid, date, auteur, raison) VALUES (" +
                "'" + report.getUsername() + "', " +
                "'" + report.getUUID().toString() + "', " +
                "'" + report.getDate() + "', " +
                "'" + report.getAuthor() + "', " +
                "'" + report.getReason() + "')");
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

    public Report getReport(int id){
        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE id='" + id + "'", rs -> {
            try {
                if(rs.next()){
                    return new Report(rs.getString("username"),rs.getString("uuid"), rs.getString("date"), rs.getString("auteur"), rs.getString("raison"));
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
                    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + Integer.toString(rs.getInt("id")));
                    //Bukkit.getConsoleSender().sendMessage(ChatColor.RED +Bukkit.getPlayer(test).getName());
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        });

        return ids;
    }

    public List<Report> getReports(String uuid){
        List<Report> reports = new ArrayList<>();

        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "' ORDER BY id ASC", rs -> {
            try {
                while(rs.next()){
                    reports.add(new Report(rs.getString("username"),rs.getString("uuid"), rs.getString("date"), rs.getString("auteur"), rs.getString("raison")));
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        });

        return reports;
    }
}
