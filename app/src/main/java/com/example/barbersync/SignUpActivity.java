package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etAddress, etCity, etProvince, etPostalCode, etPassword;
    private static List<Client> hardcodedClients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();

        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> attemptSignUp());

        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);
        tvGoToLogin.setOnClickListener(v -> navigateToLogin());
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etProvince = findViewById(R.id.etProvince);
        etPostalCode = findViewById(R.id.etPostalCode);
        etPassword = findViewById(R.id.etPassword);
    }

    private void attemptSignUp() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String province = etProvince.getText().toString().trim();
        String postalCode = etPostalCode.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Name, email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }


        for (Client client : hardcodedClients) {
            if (client.getEmail().equalsIgnoreCase(email)) {
                Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create new client
        Client newClient = new Client();
        newClient.setId(hardcodedClients.size() + 1);
        newClient.setName(name);
        newClient.setEmail(email);
        newClient.setAddress(address);
        newClient.setCity(city);
        newClient.setProvince(province);
        newClient.setPostal_code(postalCode);
        newClient.setPhone(password);

        // Add to hardcoded list
        hardcodedClients.add(newClient);

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static List<Client> getHardcodedClients() {
        if (hardcodedClients.isEmpty()) {
            Client defaultClient = new Client();
            defaultClient.setId(1);
            defaultClient.setName("John Doe");
            defaultClient.setEmail("john@example.com");
            defaultClient.setPhone("password123");
            hardcodedClients.add(defaultClient);
        }
        return hardcodedClients;
    }
}