package com.example.barbersync;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

public class DashBoard extends AppCompatActivity {

    private RecyclerView recyclerViewNouveautes;
    private NouveauteAdapter adapter;
    private List<Nouveaute> nouveautes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // Pour afficher les nouveautes
        recyclerViewNouveautes = findViewById(R.id.recyclerViewNouveautes);
        recyclerViewNouveautes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        nouveautes = new ArrayList<>();
        nouveautes.add(new Nouveaute("Nouveau coiffeur en ville!", "Découvrez notre nouveau talent.", new Date(), new Date(), true, "rabais"));
        nouveautes.add(new Nouveaute("Offre spéciale", "20% de réduction ce mois-ci.", new Date(), new Date(), true, "promo"));
        nouveautes.add(new Nouveaute("Offre spéciale", "20% de réduction ce mois-ci.", new Date(), new Date(), true, "promo"));

        adapter = new NouveauteAdapter(this, nouveautes);
        recyclerViewNouveautes.setAdapter(adapter);

        // Pour afficher les coiffeurs
        RecyclerView recyclerViewCoiffeurs = findViewById(R.id.recyclerViewCoiffeurs);
        recyclerViewCoiffeurs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<Coiffeur> coiffeurs = new ArrayList<>();
        coiffeurs.add(new Coiffeur(1, "Jean Dupont", "Expert en coupes modernes."));
        coiffeurs.add(new Coiffeur(2, "Marie Curie", "Spécialiste des coiffures classiques."));

        CoiffeurAdapter coiffeurAdapter = new CoiffeurAdapter(this, coiffeurs);
        recyclerViewCoiffeurs.setAdapter(coiffeurAdapter);



        // Initialisation des boutons de navigation
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            Intent intent = new Intent(DashBoard.this, SettingsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_scissors).setOnClickListener(v -> {
            Intent intent = new Intent(DashBoard.this, ListeCoiffeursActivity.class);
            startActivity(intent);
        });
    }
}