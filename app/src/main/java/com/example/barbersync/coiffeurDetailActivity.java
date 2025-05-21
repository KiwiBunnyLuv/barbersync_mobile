/****************************************
 Fichier : coiffeurDetailActivity.java
 Auteur : Samit Sabah Adelyar
 Fonctionnalité : MOBSER2 (détail d'un coiffeur)
 Date : 2025-05-17


 Vérification :
 2025-05-20     Nicolas Beaudoin        Approuvé
 =========================================================
 Historique de modifications :
 2025-05-15     Ramin Amiri           ajout de ma partie dans les intent
 2025-05-20     Samit Adelyar           ajout de commentaires
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


        // initialiser id des vues
        imageViewCoiffeurProfile = findViewById(R.id.imageViewCoiffeurProfile);
        textViewCoiffeurName = findViewById(R.id.textViewCoiffeurName);
        textViewRating = findViewById(R.id.textViewRating);
        textViewConsultReviews = findViewById(R.id.textViewConsultReviews);
        buttonTakeAppointment = findViewById(R.id.buttonTakeAppointment);
        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        biographie = findViewById(R.id.biographie);
        btnRetour = findViewById(R.id.btnRetour);

        //navbar et topbar
        configurerBoutonRetour();
        configurerNavBar();

        Intent intent = getIntent();



        // recupérer le coiffeur
        Coiffeur coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");;

        //set la vues avec les informations du coiffeur
        if (coiffeur != null) {
            textViewCoiffeurName.setText(coiffeur.getName());
            biographie.setText(coiffeur.getBiographie());


            //textViewRating.setText(coiffeur.getRating() + " ★★★★☆ (" + coiffeur.getNumberOfReviews() + ")");

            //photo de profil
            String baseUrl = "http://192.168.11.212:5000/galeries/";
            String encodedName = android.net.Uri.encode(coiffeur.getName());
            String encodedFileName = android.net.Uri.encode(coiffeur.getPhotos().get(0).getNomFichierImage());
            String fullUrl = baseUrl + encodedName + "/" + encodedFileName;

            Log.d(TAG, "URL encodée de l'image: " + fullUrl);


            Glide.with(this)
                    .load(fullUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.scissors)
                    .into(imageViewCoiffeurProfile);



            // appeler gallery Adapter
            GalleryAdapter galleryAdapter = new GalleryAdapter(coiffeur, this);
            recyclerViewGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewGallery.setAdapter(galleryAdapter);

            // appeler services adapter
            ServicesAdapter servicesAdapter = new ServicesAdapter(coiffeur); // Assuming Coiffeur has a method for services list
            recyclerViewServices.setLayoutManager(new LinearLayoutManager(this)); // Vertical by default
            recyclerViewServices.setAdapter(servicesAdapter);

            // Set click listeners (implement the actions in these methods)
            textViewConsultReviews.setOnClickListener(v -> consultReviews(coiffeur, this)); // Assuming Coiffeur has an ID
            buttonTakeAppointment.setOnClickListener(v -> takeAppointment(coiffeur, this));
        }
    }

    // appelle les reviews
    private void consultReviews(Coiffeur coiffeur, Context context) {
        Intent intent = new Intent(context, coiffeurDetailActivity.class);
        intent.putExtra("coiffeur", coiffeur);
        context.startActivity(intent);
    }

    // appelle l'activity qui met les rendez-vous
    private void takeAppointment(Coiffeur coiffeur, Context context) {
        Intent intent = new Intent(context, coiffeurDetailActivity.class);
        intent.putExtra("coiffeur", coiffeur);
        context.startActivity(intent);
    }
    //methode pour la barre de navigaiton
    private void configurerNavBar() {
        // Navigation vers l'accueil
        ImageButton homeButton = findViewById(R.id.nav_home);
        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                try {
                    Log.d(TAG, "skibidi");
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
    //bouton retour en haut de l'écran
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