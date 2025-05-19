package com.example.barbersync;

public class Notification {
    private int id;
    private String title;
    private String message;
    private boolean isRead;

    public Notification(int id, String title, String message, boolean isRead) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
    }

    // Getters et setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
