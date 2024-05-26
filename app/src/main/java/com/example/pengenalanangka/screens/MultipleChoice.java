package com.example.pengenalanangka.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.adapter.MultipleChoiceAdapter;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.auth.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MultipleChoice extends AppCompatActivity {
    private ViewPager2 viewPager;
    private MultipleChoiceAdapter adapter;
    TextView scoreText;
    int score;
    private FirebaseAuth mAuth;
    ImageButton backToHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        viewPager = findViewById(R.id.quizPilihanGandaViewPager);
        scoreText = findViewById(R.id.scoreTextView);

        adapter = new MultipleChoiceAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        mAuth = FirebaseAuth.getInstance();

        score = 0;

        scoreText.setText(String.valueOf(score));

        backToHomeBtn= findViewById(R.id.backToHome);
        backToHomeBtn.setOnClickListener(l -> openMainMenu());
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

    public void nextQuestion() {
        if (viewPager.getCurrentItem() == (adapter.getItemCount() - 1)) {
            // Jika saat ini berada di halaman terakhir, pindah ke activity lainnya
            Bundle extras = new Bundle();
            extras.putInt("SCORE_KEY", score);
            Helper.navigateToActivity(this, Score.class, extras);
            finish();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    }

    public void addScore() {
        score += 25;
        scoreText.setText(String.valueOf(score));
    }

    void openMainMenu() {
        Helper.navigateToActivity(this, MainMenu.class);
        finish();
    }
}