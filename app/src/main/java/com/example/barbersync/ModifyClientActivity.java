package com.example.barbersync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ModifyClientActivity extends AppCompatActivity {

    private EditText etName, etEmail, etAddress, etCity, etProvince, etPostalCode, etPhone;
    private Button btnSave;
    private Client currentClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_client);

        initViews();

        currentClient = Client.CLIENT_COURANT;

        if (currentClient == null) {
            currentClient = getHardcodedClient();
            Client.CLIENT_COURANT = currentClient;
        }

        populateFields();

        btnSave.setOnClickListener(v -> saveClientChanges());
    }

    private Client getHardcodedClient() {
        Client client = new Client();
        client.setId(1);
        client.setName("John Doe");
        client.setEmail("john@example.com");
        client.setAddress("123 Main St");
        client.setCity("Montreal");
        client.setProvince("Quebec");
        client.setPostal_code("H1H 1H1");
        client.setPhone("password123");
        return client;
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etProvince = findViewById(R.id.etProvince);
        etPostalCode = findViewById(R.id.etPostalCode);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
    }

    private void populateFields() {
        if (currentClient != null) {
            etName.setText(currentClient.getName());
            etEmail.setText(currentClient.getEmail());
            etAddress.setText(currentClient.getAddress());
            etCity.setText(currentClient.getCity());
            etProvince.setText(currentClient.getProvince());
            etPostalCode.setText(currentClient.getPostal_code());
            etPhone.setText(currentClient.getPhone());
        }
    }

    private void saveClientChanges() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String province = etProvince.getText().toString().trim();
        String postalCode = etPostalCode.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Name and email are required", Toast.LENGTH_SHORT).show();
            return;
        }

        currentClient.setName(name);
        currentClient.setEmail(email);
        currentClient.setAddress(address);
        currentClient.setCity(city);
        currentClient.setProvince(province);
        currentClient.setPostal_code(postalCode);
        currentClient.setPhone(phone);

        Client.CLIENT_COURANT = currentClient;

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}