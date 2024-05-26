package com.example.pengenalanangka.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.auth.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Score extends AppCompatActivity {
    Button mainMenuButton;
    TextView scoreTextView;
    int scoreValue;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mainMenuButton = findViewById(R.id.mainMenuBtn);
        scoreTextView = findViewById(R.id.scoreText);
        scoreValue = getIntent().getIntExtra("SCORE_KEY", 0);
        scoreTextView.setText("Score : " + scoreValue);
        mAuth = FirebaseAuth.getInstance();

        mainMenuButton.setOnClickListener(l -> openMainMenu());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            openSignIn();
        }
    }

    void openSignIn() {
        Helper.navigateToActivity(this, SignIn.class);
        finish();
    }

    void openMainMenu() {
        Helper.navigateToActivity(this, MainMenu.class);
        finish();
    }
}