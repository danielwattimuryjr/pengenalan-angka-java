package com.example.pengenalanangka.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.auth.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {
    private Button openAngkaButton, openQuizButton;
    ImageButton exitBtn, logoutBtn, editUsernameButton;
    private FirebaseAuth mAuth;
    TextView usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        openAngkaButton = findViewById(R.id.openAngka);
        editUsernameButton = findViewById(R.id.editUsernameBtn);
        openQuizButton = findViewById(R.id.openQuiz);
        exitBtn = findViewById(R.id.exitApp);
        logoutBtn = findViewById(R.id.logout);
        usernameText = findViewById(R.id.usernameText);

        mAuth = FirebaseAuth.getInstance();

        openAngkaButton.setOnClickListener(l -> openBelajarAngkaActivity());
        openQuizButton.setOnClickListener(l -> openQuizActivity());
        logoutBtn.setOnClickListener(l -> signOut());
        editUsernameButton.setOnClickListener(l -> openUpdateProfile());

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog before exiting the app
                new AlertDialog.Builder(MainMenu.this)
                        .setTitle("Exit App")
                        .setMessage("Are you sure you want to exit the app?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Exit the app
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        setUsername();
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

    void setUsername() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        usernameText.setText(
                currentUser.getDisplayName()
        );
    }

    void openSignIn() {
        Helper.navigateToActivity(this, SignIn.class);
        finish();
    }

    void openBelajarAngkaActivity() {
        Helper.navigateToActivity(this, LearnNumbers.class);
        finish();
    }

    void openQuizActivity() {
        Helper.navigateToActivity(this, Quiz.class);
        finish();
    }

    void openUpdateProfile() {
        Helper.navigateToActivity(this, UpdateProfile.class);
    }

    public void signOut() {
        mAuth.signOut();
        openSignIn();
    }
}