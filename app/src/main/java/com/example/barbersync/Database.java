package com.example.barbersync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "barbersync.db";
    private static final int DB_VERSION = 2;

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

        // Table nouveautes
        db.execSQL("CREATE TABLE nouveautes (" +
                "id INTEGER PRIMARY KEY, " +
                "nom TEXT, " +
                "description TEXT, " +
                "date_debut TEXT, " +
                "date_fin TEXT, " +
                "is_active BOOLEAN, " +
                "type TEXT)");

        db.execSQL("CREATE TABLE photos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "coiffeur_id INTEGER, " +
                "nomFichierImage TEXT, " +
                "description TEXT, " +
                "FOREIGN KEY(coiffeur_id) REFERENCES coiffeurs(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS coiffeur_creneau");
        db.execSQL("DROP TABLE IF EXISTS coiffeur_coupe");
        db.execSQL("DROP TABLE IF EXISTS photos");
        db.execSQL("DROP TABLE IF EXISTS creneaux");
        db.execSQL("DROP TABLE IF EXISTS coupes");
        db.execSQL("DROP TABLE IF EXISTS coiffeurs");
        db.execSQL("DROP TABLE IF EXISTS nouveautes");
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

    public boolean insertPhoto(Photo c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("coiffeur_id", c.getUser_id());
        values.put("nomFichierImage", c.getNomFichierImage());
        values.put("description", c.getDescription());
        long result = db.insert("photos", null, values);
        return result != -1;
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

    /**
     * recupere toutes les coiffeurs de la database avec cursor
     *
     * @return toutes les coiffeurs de la db dans une list
     */
    public List<Coiffeur> getAllCoiffeurs() {
        List<Coiffeur> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM coiffeurs", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nom = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String biographie = cursor.getString(cursor.getColumnIndexOrThrow("biographie"));

                Coiffeur c = new Coiffeur(id, nom, biographie);
                liste.add(c);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return liste;
    }

    /**
     * return une liste de photos avec un coiffeur id qu'on choisit
     * @param idCoiffeur coiffeur qu'on veut récupérer les photos
     * @return une liste de photos
     */
    public List<Photo> getPhotosByCoiffeur(int idCoiffeur)
    {
        List<Photo> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM photos WHERE coiffeur_id = ?",
                new String[]{String.valueOf(idCoiffeur)}
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int coiffeurId = cursor.getInt(cursor.getColumnIndexOrThrow("coiffeur_id"));
                String nomFichierImage = cursor.getString(cursor.getColumnIndexOrThrow("nomFichierImage"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                Photo photo = new Photo(id, coiffeurId, nomFichierImage, description);
                liste.add(photo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return liste;
    }

    public List<CoupeCoiffeur> getCoupesByCoiffeur(int idCoiffeur)
    {
        List<CoupeCoiffeur> coupe = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT c.coupe, cc.prix FROM coupes c JOIN coiffeur_coupe cc ON c.id = cc.coupe_id WHERE cc.coiffeur_id = ?;",
                new String[]{String.valueOf(idCoiffeur)}
        );

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("coupe"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("prix"));
                coupe.add(new CoupeCoiffeur(price, name));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return coupe;
    }
    public void insertNouveaute(Nouveaute n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", n.getId());
        values.put("nom", n.getNom());
        values.put("description", n.getDescription());
        values.put("date_debut", n.getDateDebut().toString());
        values.put("date_fin", n.getDateFin().toString());
        values.put("is_active", n.getIsActive());
        values.put("type", n.getType());
        db.insertWithOnConflict("nouveautes", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<Nouveaute> getAllNouveautes() {
        List<Nouveaute> nouveautes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM nouveautes", null);

        if (cursor.moveToFirst()) {
            do {
                Nouveaute nouveaute = new Nouveaute(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date_debut")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date_fin")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("is_active")) == 1,
                        cursor.getString(cursor.getColumnIndexOrThrow("type"))
                );
                nouveautes.add(nouveaute);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nouveautes;
    }

}
