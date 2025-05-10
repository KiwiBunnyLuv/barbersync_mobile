package com.example.barbersync;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "barbersync.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table coiffeurs
        db.execSQL("CREATE TABLE coiffeurs (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "biographie TEXT)");

        // Table coupes
        db.execSQL("CREATE TABLE coupes (" +
                "id INTEGER PRIMARY KEY, " +
                "coupe TEXT)");

        // Table photos
        db.execSQL("CREATE TABLE photos (" +
                "id INTEGER PRIMARY KEY, " +
                "url TEXT, " +
                "coiffeur_id INTEGER, " +
                "FOREIGN KEY(coiffeur_id) REFERENCES coiffeurs(id))");

        // Table creneaux
        db.execSQL("CREATE TABLE creneaux (" +
                "id INTEGER PRIMARY KEY, " +
                "date TEXT, " +
                "heure_debut TEXT," +
                "heure_fin TEXT)");

        // Table de liaison coiffeur_coupe
        db.execSQL("CREATE TABLE coiffeur_coupe (" +
                "coiffeur_id INTEGER, " +
                "coupe_id INTEGER, " +
                "prix FLOAT," +
                "PRIMARY KEY(coiffeur_id, coupe_id), " +
                "FOREIGN KEY(coiffeur_id) REFERENCES coiffeurs(id), " +
                "FOREIGN KEY(coupe_id) REFERENCES coupes(id))");

        // Table de liaison coiffeur_creneau
        db.execSQL("CREATE TABLE coiffeur_creneau (" +
                "coiffeur_id INTEGER, " +
                "creneau_id INTEGER, " +
                "dispo BOOLEAN," +
                "reserve BOOLEAN," +
                "PRIMARY KEY(coiffeur_id, creneau_id), " +
                "FOREIGN KEY(coiffeur_id) REFERENCES coiffeurs(id), " +
                "FOREIGN KEY(creneau_id) REFERENCES creneaux(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Pour recréer les tables en cas de mise à jour
        db.execSQL("DROP TABLE IF EXISTS coiffeur_creneau");
        db.execSQL("DROP TABLE IF EXISTS coiffeur_coupe");
        db.execSQL("DROP TABLE IF EXISTS photos");
        db.execSQL("DROP TABLE IF EXISTS creneaux");
        db.execSQL("DROP TABLE IF EXISTS coupes");
        db.execSQL("DROP TABLE IF EXISTS coiffeurs");
        onCreate(db);
    }
    public void insertCoiffeur(Coiffeur c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", c.getId());
        values.put("name", c.getName());
        values.put("biographie", c.getBiographie());
        db.insertWithOnConflict("coiffeurs", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insertCoupe(Coupes c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", c.getId());
        values.put("coupe", c.getNom());
        db.insertWithOnConflict("coupes", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insertCreneau(Creneau c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", c.getId());
        values.put("date", c.getDate());
        values.put("heure_debut", c.getHeureDebut());
        values.put("heure_fin", c.getHeureFin());
        db.insertWithOnConflict("creneaux", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insertCoiffeurCoupe(CoupeCoiffeur c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("coiffeur_id", c.getCoiffeur());
        values.put("coupe_id", c.getCoupe());
        values.put("prix", c.getPrix());
        db.insertWithOnConflict("coiffeur_coupe", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insertCoiffeurCreneau(CreneauCoiffeur c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("coiffeur_id", c.getCoiffeurs());
        values.put("creneau_id", c.getCreneau());
        values.put("dispo", c.getDispo());
        values.put("reserve", c.getReserve());
        db.insertWithOnConflict("coiffeur_creneau", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

}
