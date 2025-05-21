package com.example.barbersync;

public abstract class BaseNotification {
    private boolean isRead = false;

    public abstract String getTitle();
    public abstract String getMessage();

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { this.isRead = read; }
}