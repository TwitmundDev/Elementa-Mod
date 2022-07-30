/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fr.twitmund.managers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Warn {
    private String username;
    private String uuid;
    private String date;
    private String author;
    private String reason;

    public Warn(String username, String uuid, String date, String author, String reason) {
        this.username = username;
        this.uuid = uuid;
        this.date = date;
        this.author = author;
        this.reason = reason;
    }

    public Warn(String username,String uuid, String author, String reason) {
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
