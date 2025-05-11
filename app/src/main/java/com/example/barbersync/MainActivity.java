package com.example.barbersync;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private TextView textResult;
    private Api api = new Api(); // You already defined the API class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ensure this is correct layout file

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        textResult = findViewById(R.id.textResult);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> attemptLogin(v));
    }

    public void attemptLogin(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            List<Client> clients = api.getClients();
            Log.d("LoginDebug", "Number of clients: " + clients.size());
            for (Client client : clients) {
                Log.d("LoginDebug", "Client: " + client.getEmail() + " | Phone: " + client.getPhone());
            }

            boolean loginSuccess = false;
            Client matchedClient = null;

            for (Client client : clients) {
                if (client.getEmail().equalsIgnoreCase(email) && client.getPhone().equals(password)) {
                    loginSuccess = true;
                    matchedClient = client;
                    break;
                }
            }

            boolean finalLoginSuccess = loginSuccess;
            Client finalClient = matchedClient;
            runOnUiThread(() -> {
                if (finalLoginSuccess) {
                    textResult.setText("Welcome, " + finalClient.getName());
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, DashBoard.class);

                    // Pass client data if needed
                    intent.putExtra("client_name", finalClient.getName());
                    intent.putExtra("client_email", finalClient.getEmail());

                    startActivity(intent);
                } else {
                    textResult.setText("Login failed");
                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
