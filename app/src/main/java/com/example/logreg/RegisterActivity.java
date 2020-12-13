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

        focusChangeListeners();


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


    private void focusChangeListeners() {

        etRegistrationUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String username = etRegistrationUserName.getText().toString().trim();

                if (!hasFocus) {
                    if (!adatbazis.usernameExists(username)) {
                        etRegistrationUserName.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.usernameExists));
                    }
                    else {
                        etRegistrationUserName.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.usernameNotExists));
                    }
                }
            }
        });

        etRegistrationEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = etRegistrationEmail.getText().toString().trim();

                if (!hasFocus) {
                    if (!adatbazis.emailExists(email)) {
                        etRegistrationEmail.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.usernameExists));
                    }
                    else {
                        etRegistrationEmail.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.usernameNotExists));
                    }
                }
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

        if (!emailValidator(email) && !isValidEmail(email)) {
            Toast.makeText(this, "Nem E-mail cím formátum!", Toast.LENGTH_SHORT).show();
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

        if (!namelValidator(name)) {
            Toast.makeText(this, "A neved nem teljes!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adatbazis.dataInsert(email, username, password, name)) {
            Toast.makeText(this, "Sikeres regisztráció", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public boolean namelValidator(String name) {
        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "^[A-ZÁÉÍÓÚÖÜŐŰ][a-zA-Záéíóúöüőű]{3,30}(?: [A-ZÁÉÍÓÚÖÜŐŰ][a-zA-Záéíóúöüőű]*){1,3}$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
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