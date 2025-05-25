/****************************************
 * Fichier : coiffeurDetailActivity.java
 * Auteur : Samit Sabah Adelyar, Ramin Amiri
 * Fonctionnalité : MOBSER2 (détail d'un coiffeur) - Mise à jour pour la prise de rendez-vous
 * Date : 2025-05-24
 *
 * Vérification :
 * 2025-05-24     Nicolas Beaudoin        Approuvé
 =========================================================
 * Historique de modifications :
 * 2025-05-24     Ramin Amiri           Modification du bouton rendez-vous pour utiliser DisponibilitesActivity
 =========================================================
 ****************************************/

package com.example.barbersync;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Activité qui affiche les détails d'un coiffeur spécifique
 * avec ses services, sa galerie photo et la possibilité de prendre rendez-vous
 */
public class coiffeurDetailActivity extends AppCompatActivity {
    private ImageView imageViewCoiffeurProfile;
    private TextView textViewCoiffeurName;
    private TextView textViewRating;
    private TextView textViewConsultReviews;
    private TextView biographie;
    private Button buttonTakeAppointment;
    private RecyclerView recyclerViewGallery;
    private RecyclerView recyclerViewServices;
    private ImageButton btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coiffeur_detail);

        // Initialiser les vues
        initialiserVues();

        // Configuration de la navigation
        configurerBoutonRetour();
        configurerNavBar();

        // Récupérer le coiffeur depuis l'intent
        Coiffeur coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");

        // Configurer l'interface avec les informations du coiffeur
        if (coiffeur != null) {
            configurerInterfaceCoiffeur(coiffeur);
        } else {
            Toast.makeText(this, "Erreur: coiffeur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Initialise toutes les vues de l'activité
     */
    private void initialiserVues() {
        imageViewCoiffeurProfile = findViewById(R.id.imageViewCoiffeurProfile);
        textViewCoiffeurName = findViewById(R.id.textViewCoiffeurName);
        textViewRating = findViewById(R.id.textViewRating);
        textViewConsultReviews = findViewById(R.id.textViewConsultReviews);
        buttonTakeAppointment = findViewById(R.id.buttonTakeAppointment);
        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        biographie = findViewById(R.id.biographie);
        btnRetour = findViewById(R.id.btnRetour);
    }

    /**
     * Configure l'interface avec les informations du coiffeur
     * @param coiffeur Le coiffeur à afficher
     */
    private void configurerInterfaceCoiffeur(Coiffeur coiffeur) {
        // Afficher les informations de base
        textViewCoiffeurName.setText(coiffeur.getName());
        biographie.setText(coiffeur.getBiographie());

        // Afficher la photo de profil si disponible
        afficherPhotoProfile(coiffeur);

        // Configurer la galerie photo
        configurerGalerie(coiffeur);

        // Configurer la liste des services
        configurerServices(coiffeur);

        // Configurer les boutons d'action
        configurerBoutonsAction(coiffeur);
    }

    /**
     * Affiche la photo de profil du coiffeur
     * @param coiffeur Le coiffeur dont afficher la photo
     */
    private void afficherPhotoProfile(Coiffeur coiffeur) {
        if (coiffeur.getPhotos() != null && !coiffeur.getPhotos().isEmpty()) {
            // Construire l'URL de l'image
            String baseUrl = "http://192.168.2.21:5000/galeries/";
            String encodedName = android.net.Uri.encode(coiffeur.getName());
            String encodedFileName = android.net.Uri.encode(coiffeur.getPhotos().get(0).getNomFichierImage());
            String fullUrl = baseUrl + encodedName + "/" + encodedFileName;

            Log.d(TAG, "URL de l'image de profil: " + fullUrl);

            // Charger l'image avec Glide
            Glide.with(this)
                    .load(fullUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.scissors)
                    .error(R.drawable.scissors)
                    .into(imageViewCoiffeurProfile);
        } else {
            // Afficher l'image par défaut
            imageViewCoiffeurProfile.setImageResource(R.drawable.scissors);
        }
    }

    /**
     * Configure la galerie photo du coiffeur
     * @param coiffeur Le coiffeur dont afficher la galerie
     */
    private void configurerGalerie(Coiffeur coiffeur) {
        if (coiffeur.getPhotos() != null && !coiffeur.getPhotos().isEmpty()) {
            GalleryAdapter galleryAdapter = new GalleryAdapter(coiffeur, this);
            recyclerViewGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewGallery.setAdapter(galleryAdapter);
        }
    }

    /**
     * Configure la liste des services du coiffeur
     * @param coiffeur Le coiffeur dont afficher les services
     */
    private void configurerServices(Coiffeur coiffeur) {
        if (coiffeur.getCoupes() != null && !coiffeur.getCoupes().isEmpty()) {
            ServicesAdapter servicesAdapter = new ServicesAdapter(coiffeur);
            recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewServices.setAdapter(servicesAdapter);
        }
    }

    /**
     * Configure les boutons d'action (avis et rendez-vous)
     * @param coiffeur Le coiffeur pour lequel configurer les actions
     */
    private void configurerBoutonsAction(Coiffeur coiffeur) {
        // Bouton pour consulter les avis
        textViewConsultReviews.setOnClickListener(v -> consultReviews(coiffeur, this));

        // Bouton pour prendre rendez-vous - Utiliser DisponibilitesActivity
        buttonTakeAppointment.setOnClickListener(v -> takeAppointment(coiffeur, this));
    }

    /**
     * Ouvre l'activité pour consulter les avis du coiffeur
     * @param coiffeur Le coiffeur dont consulter les avis
     * @param context Le contexte de l'activité
     */
    private void consultReviews(Coiffeur coiffeur, Context context) {
        try {
            Intent intent = new Intent(context, AvisCoiffeurActivity.class);
            intent.putExtra("coiffeur", coiffeur);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'ouverture des avis: " + e.getMessage(), e);
            Toast.makeText(context, "Erreur lors de l'ouverture des avis", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Ouvre l'activité pour prendre rendez-vous avec le coiffeur
     * @param coiffeur Le coiffeur avec qui prendre rendez-vous
     * @param context Le contexte de l'activité
     */
    private void takeAppointment(Coiffeur coiffeur, Context context) {
        // Vérifier que le client est connecté
        if (Client.CLIENT_COURANT == null) {
            Toast.makeText(context, "Veuillez vous connecter pour prendre rendez-vous", Toast.LENGTH_LONG).show();
            return;
        }

        // Rediriger vers l'activité des disponibilités
        Intent intent = new Intent(context, DisponibilitesActivity.class);
        intent.putExtra("coiffeur", coiffeur);
        context.startActivity(intent);
    }

    /**
     * Configure la barre de navigation inférieure
     */
    private void configurerNavBar() {
        // Navigation vers l'accueil
        ImageButton homeButton = findViewById(R.id.nav_home);
        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, DashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Erreur de navigation: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Navigation vers le profil
        ImageButton profileButton = findViewById(R.id.nav_profile);
        if (profileButton != null) {
            profileButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Erreur de navigation: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Configure le bouton de retour en haut de l'écran
     */
    private void configurerBoutonRetour() {
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> {
                Intent intent = new Intent(this, ListeCoiffeursActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }
    }
}