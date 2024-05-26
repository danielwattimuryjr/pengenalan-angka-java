package com.example.pengenalanangka.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.adapter.LearnAngkaAdapter;
import com.example.pengenalanangka.data.Data;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.auth.SignIn;
import com.example.pengenalanangka.view_pager.LearnViewPagerItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LearnNumbers extends AppCompatActivity {
    private ImageButton backToHome, next, prev;
    TextToSpeech textToSpeech;
    LearnAngkaAdapter dataAdapter;
    ViewPager2 viewPager;
    ArrayList<LearnViewPagerItem> viewPagerItemArrayList;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_numbers);

        backToHome = findViewById(R.id.backToHome);
        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);
        viewPager = findViewById(R.id.angkaViewPager);
        mAuth = FirebaseAuth.getInstance();

        List<String> numberList = Data.getAngkaList();
        viewPagerItemArrayList = new ArrayList<>();

        for (String number : numberList) {
            LearnViewPagerItem viewPagerItem = new LearnViewPagerItem(number);
            viewPagerItemArrayList.add(viewPagerItem);
        }

        dataAdapter = new LearnAngkaAdapter(viewPagerItemArrayList);
        viewPager.setAdapter(dataAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setUserInputEnabled(false);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        next.setOnClickListener(l -> updateNumber(1));
        prev.setOnClickListener(l -> updateNumber(-1));
        backToHome.setOnClickListener(l -> openMainMenu());

        onViewPageChangePage();
        setUpTts();

        dataAdapter.onTtsClickListener(new LearnAngkaAdapter.onTtsClickListener() {
            @Override
            public void onTtsClick(int position) {
                String text = numberList.get(position);
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
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

    void updateNumber(int pos) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + pos);
    }

    void openMainMenu() {
        Helper.navigateToActivity(this, MainMenu.class);
        finish();
    }

    private void onViewPageChangePage() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("ViewPager2", "Pos : " + position + ". Max : " + dataAdapter.getItemCount());
                if (position < 1) {
                    prev.setVisibility(View.GONE);
                } else if (position > 0) {
                    prev.setVisibility(View.VISIBLE);
                }

                if (position  >= dataAdapter.getItemCount() - 1) {
                    next.setVisibility(View.GONE);
                } else if(position < dataAdapter.getItemCount()) {
                    next.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setUpTts() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(new Locale("id", "ID"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Bahasa Indonesia tidak didukung");
                        Log.d("TTS", "Berganti ke Bahasa Inggris USA");
                        textToSpeech.setLanguage(Locale.US);
                    } else {
                        textToSpeech.setLanguage(new Locale("id", "ID"));
                        Log.d("TTS", "Bahasa Indonesia Berhasil Digunakan");
                    }
                } else {
                    Log.e("TTS", "Inisialisasi TextToSpeech gagal");
                }
            }
        });
    }
}