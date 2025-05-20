/****************************************
 Fichier : Notification.java
 Auteur : Nicolas Beaudoin
 Fonctionnalité : Représente une notification dans l'application
 Date : 2025-05-13

 Vérification :
 2025-05-15     Samit Adelyar        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-20     Nicolas Beaudoin           Ajout de commentaires
 =========================================================
 ****************************************/


package com.example.barbersync;
/**
 * Classe représentant une notification avec un titre, un message et un état de lecture.
 */
public class Notification {
    private int id;
    private String title;
    private String message;
    private boolean isRead;
    /**
     * Constructeur de la classe Notification.
     * @param id Identifiant de la notification.
     * @param title Titre de la notification.
     * @param message Message de la notification.
     * @param isRead État de lecture de la notification.
     */
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
