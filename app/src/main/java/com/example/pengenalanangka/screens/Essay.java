package com.example.pengenalanangka.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.adapter.EssayAdapter;
import com.example.pengenalanangka.adapter.MultipleChoiceAdapter;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.auth.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Essay extends AppCompatActivity {
    private ViewPager2 viewPager;
    private EssayAdapter adapter;
    TextView scoreText;
    ImageButton backToHomeBtn;
    int score;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);

        viewPager = findViewById(R.id.quizEssayViewPager);
        scoreText = findViewById(R.id.scoreTextView);
        adapter = new EssayAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);

        score = 0;

        scoreText.setText(String.valueOf(score));

        mAuth = FirebaseAuth.getInstance();

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