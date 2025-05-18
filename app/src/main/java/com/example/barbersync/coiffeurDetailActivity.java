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


        // Find views
        imageViewCoiffeurProfile = findViewById(R.id.imageViewCoiffeurProfile);
        textViewCoiffeurName = findViewById(R.id.textViewCoiffeurName);
        textViewRating = findViewById(R.id.textViewRating);
        textViewConsultReviews = findViewById(R.id.textViewConsultReviews);
        buttonTakeAppointment = findViewById(R.id.buttonTakeAppointment);
        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        biographie = findViewById(R.id.biographie);
        btnRetour = findViewById(R.id.btnRetour);

        configurerBoutonRetour();
        configurerNavBar();

        Intent intent = getIntent();



        // Get Coiffeur object (example - you might pass it via Intent)
        Coiffeur coiffeur = (Coiffeur) getIntent().getSerializableExtra("coiffeur");;

        if (coiffeur != null) {
            // Populate views
            textViewCoiffeurName.setText(coiffeur.getName());
            biographie.setText(coiffeur.getBiographie());

            // Format rating and reviews text based on your Coiffeur object structure
            //textViewRating.setText(coiffeur.getRating() + " ★★★★☆ (" + coiffeur.getNumberOfReviews() + ")");

            String baseUrl = "http://192.168.2.160:5000/galeries/";
            String encodedName = android.net.Uri.encode(coiffeur.getName());
            String encodedFileName = android.net.Uri.encode(coiffeur.getPhotos().get(0).getNomFichierImage());
            String fullUrl = baseUrl + encodedName + "/" + encodedFileName;

            Log.d(TAG, "URL encodée de l'image: " + fullUrl);

            // Configuration plus stricte de Glide
            Glide.with(this) // Utiliser le contexte de l'application pour éviter les fuites
                    .load(fullUrl)// Dimensions spécifiques
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .centerCrop()        // Mode d'échelle
                    .diskCacheStrategy(DiskCacheStrategy.NONE)  // Désactiver le cache
                    .skipMemoryCache(true)                      // Désactiver le cache mémoire
                    .placeholder(R.drawable.scissors)
                    .into(imageViewCoiffeurProfile);



            // Set up Gallery RecyclerView
            GalleryAdapter galleryAdapter = new GalleryAdapter(coiffeur, this); // Assuming Coiffeur has a method for photo URLs
            recyclerViewGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewGallery.setAdapter(galleryAdapter);

            // Set up Services RecyclerView
            ServicesAdapter servicesAdapter = new ServicesAdapter(coiffeur); // Assuming Coiffeur has a method for services list
            recyclerViewServices.setLayoutManager(new LinearLayoutManager(this)); // Vertical by default
            recyclerViewServices.setAdapter(servicesAdapter);

            // Set click listeners (implement the actions in these methods)
            textViewConsultReviews.setOnClickListener(v -> consultReviews(coiffeur, this)); // Assuming Coiffeur has an ID
            buttonTakeAppointment.setOnClickListener(v -> takeAppointment(coiffeur, this));
        }
    }

    // Dummy method to get Coiffeur data (replace with your actual data retrieval)

    // Implement these methods to handle button clicks
    private void consultReviews(Coiffeur coiffeur, Context context) {
        Intent intent = new Intent(context, coiffeurDetailActivity.class);
        intent.putExtra("coiffeur", coiffeur);
        context.startActivity(intent);
    }

    private void takeAppointment(Coiffeur coiffeur, Context context) {
        Intent intent = new Intent(context, coiffeurDetailActivity.class);
        intent.putExtra("coiffeur", coiffeur);
        context.startActivity(intent);
    }
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