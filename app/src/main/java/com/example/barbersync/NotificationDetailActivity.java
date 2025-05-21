package com.example.barbersync;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        TextView title = findViewById(R.id.detailTitle);
        TextView message = findViewById(R.id.detailMessage);
        TextView typeInfo = findViewById(R.id.detailType);
        TextView dateInfo = findViewById(R.id.detailDates);

        title.setText(getIntent().getStringExtra("title"));
        message.setText(getIntent().getStringExtra("message"));

        // Pour les informations supplémentaires des nouveautés
        String type = getIntent().getStringExtra("type");
        String dates = getIntent().getStringExtra("dates");

        if (type != null && !type.isEmpty()) {
            typeInfo.setVisibility(View.VISIBLE);
            typeInfo.setText("Type: " + type);
        } else {
            typeInfo.setVisibility(View.GONE);
        }

        if (dates != null && !dates.isEmpty()) {
            dateInfo.setVisibility(View.VISIBLE);
            dateInfo.setText(dates);
        } else {
            dateInfo.setVisibility(View.GONE);
        }

        // Configuration du bouton retour
        ImageButton btnRetour = findViewById(R.id.btnRetour);
        if (btnRetour != null) {
            btnRetour.setOnClickListener(v -> finish());
        }
    }
}