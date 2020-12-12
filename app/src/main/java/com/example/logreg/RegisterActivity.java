package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String username = etRegistrationUserName.getText().toString().trim();
        String password = etRegistrationPassword.getText().toString().trim();
        String name = etRegistrationFullName.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Az E-mail cím megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.isEmpty()) {
            Toast.makeText(this, "A felhasználónév megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "A jelszó megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "A teljes neved megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adatbazis.dataInsert(email, username, password, name)) {
            Toast.makeText(this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
        }
    }


    private void init() {
        etRegistrationEmail = findViewById(R.id.etRegistrationEmail);
        etRegistrationUserName = findViewById(R.id.etRegistrationUserName);
        etRegistrationPassword = findViewById(R.id.etRegistrationPassword);
        etRegistrationFullName = findViewById(R.id.etRegistrationFullName);
        btnRegistration = findViewById(R.id.btnRegistration);
        btnBack = findViewById(R.id.btnBack);

        adatbazis = new DBhelper(RegisterActivity.this);
    }
}