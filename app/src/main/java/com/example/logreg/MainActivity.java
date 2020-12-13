package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName, etPassword;
    private Button btnLogin, btnRegistration;

    static String userInput;

    DBhelper adatbazis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registration);
                finish();
            }
        });
    }


    private void Login() {
        //String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        userInput = etUserName.getText().toString().trim(); // Mivel felhasználónév vagy email cím is lehet megadva

        if (userInput.isEmpty()) {
            Toast.makeText(this, "A felhasználónév vagy E-mail cím megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "A jelszó megadása kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!adatbazis.loginQuery(userInput, password)) {
            Toast.makeText(this, "Hibás felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
        }

        else {
            Intent login = new Intent(MainActivity.this, LoggedInActivity.class);
            startActivity(login);
            finish();
        }
    }


    private void init() {
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistration = findViewById(R.id.btnRegistration);

        adatbazis = new DBhelper(MainActivity.this);
    }
}