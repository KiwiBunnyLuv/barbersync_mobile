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

        recyclerViewNouveautes = findViewById(R.id.recyclerViewNouveautes);
        recyclerViewNouveautes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        nouveautes = new ArrayList<>();
        nouveautes.add(new Nouveaute("Nouveau coiffeur en ville!", "Découvrez notre nouveau talent.", new Date(), new Date(), true, "rabais"));
        nouveautes.add(new Nouveaute("Offre spéciale", "20% de réduction ce mois-ci.", new Date(), new Date(), true, "promo"));
        nouveautes.add(new Nouveaute("Offre spéciale", "20% de réduction ce mois-ci.", new Date(), new Date(), true, "promo"));

        adapter = new NouveauteAdapter(this, nouveautes);
        recyclerViewNouveautes.setAdapter(adapter);
    }
}