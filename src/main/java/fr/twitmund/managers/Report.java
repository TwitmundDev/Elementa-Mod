package fr.twitmund.managers;

import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Report {
    private String username;
    private String uuid;
    private String date;
    private String author;
    private String reason;

    public Report(String username, String uuid, String date, String author, String reason) {
        this.username = username;
        this.uuid = uuid;
        this.date = date;
        this.author = author;
        this.reason = reason;
    }

    public Report(String username,String uuid, String author, String reason) {
        this(username,uuid, new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date()), author, reason);
    }

    public String getUsername() {
        return username;
    }

    public String getUUID() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getReason() {
        return reason;
    }
}
