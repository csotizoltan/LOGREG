package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoggedInActivity extends AppCompatActivity {

    private TextView tvLoggedUserName;
    private Button btnLogoff;

    DBhelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        init();

        loggedIn();
        //showAllUsers(); // --- megjeleníti az összes felhasználó összes regisztrációs adatát ---

        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoff = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(logoff);
                finish();
            }
        });
    }


    private void loggedIn() {
        String userInput = MainActivity.userInput;

        Cursor adatok = adatbazis.dataQuery(userInput);

        StringBuilder builder = new StringBuilder();
        while (adatok.moveToNext()) {
            builder.append("Üdvözöllek ").append(adatok.getString(0)).append("\n\n");

            tvLoggedUserName.setText(builder.toString());
            Toast.makeText(this, "Sikeres bejelentkezés", Toast.LENGTH_SHORT).show();
        }
    }


    private void showAllUsers() {
        Cursor adatok = adatbazis.dataQueryAllUsers();

        StringBuilder builder = new StringBuilder();
        while (adatok.moveToNext()) {
            builder.append("ID: ").append(adatok.getInt(0)).append("\n");
            builder.append("E-mail: ").append(adatok.getString(1)).append("\n");
            builder.append("Felhasználónév: ").append(adatok.getString(2)).append("\n");
            builder.append("Jelszó: ").append(adatok.getString(3)).append("\n");
            builder.append("Teljes név: ").append(adatok.getString(4)).append("\n\n");

            tvLoggedUserName.setText(builder.toString());
            Toast.makeText(this, "Sikeres bejelentkezés", Toast.LENGTH_SHORT).show();
        }
        tvLoggedUserName.setMovementMethod(new ScrollingMovementMethod());
    }


    private void init() {
        tvLoggedUserName = findViewById(R.id.tvLoggedUserName);
        btnLogoff = findViewById(R.id.btnLogoff);

        adatbazis = new DBhelper(LoggedInActivity.this);
    }
}