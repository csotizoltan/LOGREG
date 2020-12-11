package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    public EditText etRegistrationEmail, etRegistrationUserName, etRegistrationPassword, etRegistrationFullName;
    public Button btnRegistration, btnBack;

    DBhelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });
    }


    private void Registration() {
        String email = etRegistrationEmail.getText().toString().trim();
        String felhnev = etRegistrationUserName.getText().toString().trim();
        String jelszo = etRegistrationUserName.getText().toString().trim();
        String teljesnev = etRegistrationUserName.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Az E-mail cím megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }

        if (felhnev.isEmpty()) {
            Toast.makeText(this, "A felhasználónév megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }

        if (jelszo.isEmpty()) {
            Toast.makeText(this, "A jelszó megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }

        if (teljesnev.isEmpty()) {
            Toast.makeText(this, "A teljes neved megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adatbazis.adatRogzites(email, felhnev, jelszo, teljesnev)) {
            Toast.makeText(this, "Sikeres rögzítés", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Siktelen rögzítés", Toast.LENGTH_SHORT).show();
        }

    }


    private void init() {
        etRegistrationEmail = findViewById(R.id.etRegistrationEmail);
        etRegistrationUserName = findViewById(R.id.etRegistrationUserName);
        etRegistrationUserName = findViewById(R.id.etRegistrationPassword);
        etRegistrationUserName = findViewById(R.id.etRegistrationFullName);
        btnRegistration = findViewById(R.id.btnRegistration);
        btnBack = findViewById(R.id.btnBack);

        adatbazis = new DBhelper(RegisterActivity.this);
    }
}