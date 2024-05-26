package com.example.pengenalanangka.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.auth.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Quiz extends AppCompatActivity {
    private CardView cardPilihanGanda, cardEssay;
    private ImageButton backToHome;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        cardPilihanGanda = findViewById(R.id.cardPilihanGanda);
        cardEssay = findViewById(R.id.cardEssay);
        backToHome = findViewById(R.id.backToHome);
        mAuth = FirebaseAuth.getInstance();

        cardPilihanGanda.setOnClickListener(l -> openQuizPilihanGanda());

        cardEssay.setOnClickListener(l -> openEssay());

        backToHome.setOnClickListener(l -> openMainMenu());
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

    private void openQuizPilihanGanda() {
        Helper.navigateToActivity(this, MultipleChoice.class);
        finish();
    }

    private void openEssay() {
        Helper.navigateToActivity(this, Essay.class);
        finish();
    }

    private void openMainMenu() {
        Helper.navigateToActivity(this, MainMenu.class);
        finish();
    }
}