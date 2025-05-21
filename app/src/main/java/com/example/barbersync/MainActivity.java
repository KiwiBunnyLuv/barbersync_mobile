package com.example.barbersync;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private TextView textResult;
    private final SyncManager sync = new SyncManager();
    private final Api api = new Api();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sync.synchroniserDepuisApi(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        textResult = findViewById(R.id.textResult);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> attemptLogin(v));
        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    public void attemptLogin(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer votre email et mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        // Afficher une indication que la connexion est en cours
        textResult.setText("Connexion en cours...");

        new Thread(() -> {
            boolean loginSuccess = false;
            Client matchedClient = null;
            String errorMessage = "Erreur de connexion au serveur";

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .build();

                // Création de l'objet JSON pour la requête
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", email);
                jsonObject.put("password", password);

                // Log pour débogage
                Log.d("LoginAPI", "Envoi des identifiants: " + email);

                // Création du corps de la requête
                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json"),
                        jsonObject.toString()
                );

                // Création de la requête
                Request request = new Request.Builder()
                        .url("http://192.168.1.216:5000/login")
                        .post(body)
                        .build();

                // Exécution de la requête
                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    Log.d("LoginAPI", "Réponse reçue: " + responseBody);

                    if (response.isSuccessful()) {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        loginSuccess = jsonResponse.getBoolean("success");

                        if (loginSuccess) {
                            // Extraction des données du client depuis la réponse
                            JSONObject clientData = jsonResponse.getJSONObject("client");
                            matchedClient = new Client(
                                    clientData.getInt("id"),
                                    clientData.getString("name"),
                                    clientData.getString("email"),
                                    clientData.getString("address"),
                                    clientData.getString("city"),
                                    clientData.getString("province"),
                                    clientData.getString("postal_code"),
                                    clientData.getString("phone")
                            );
                        } else if (jsonResponse.has("message")) {
                            errorMessage = jsonResponse.getString("message");
                        }
                    } else {
                        Log.e("LoginAPI", "Erreur HTTP : " + response.code());
                        errorMessage = "Erreur serveur: " + response.code();
                    }
                }
            } catch (JSONException e) {
                Log.e("LoginAPI", "Erreur JSON : " + e.getMessage());
                errorMessage = "Format de réponse invalide";
            } catch (IOException e) {
                Log.e("LoginAPI", "Erreur réseau : " + e.getMessage());
                errorMessage = "Impossible de joindre le serveur";
            }

            // Variables finales pour utilisation dans runOnUiThread
            boolean finalLoginSuccess = loginSuccess;
            Client finalClient = matchedClient;
            String finalErrorMessage = errorMessage;

            runOnUiThread(() -> {
                if (finalLoginSuccess) {
                    Client.CLIENT_COURANT = finalClient;

                    textResult.setText("Bienvenue, " + finalClient.getName());
                    Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    try {
                        // Synchroniser les données depuis l'API
                        sync.synchroniserDepuisApi(MainActivity.this);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                    Intent intent = new Intent(MainActivity.this, DashBoard.class);
                    startActivity(intent);
                    finish();
                } else {
                    textResult.setText("Connexion échouée");
                    Toast.makeText(MainActivity.this, finalErrorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }
}