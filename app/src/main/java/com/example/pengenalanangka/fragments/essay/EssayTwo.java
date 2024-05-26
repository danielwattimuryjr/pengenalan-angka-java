package com.example.pengenalanangka.fragments.essay;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.screens.Essay;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EssayTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EssayTwo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputEditText jawabanInput;
    Button submitBtn;    Animation animation;

    MediaPlayer correctSound, wrongSound;
    Activity activity;
    private static final String ANSWER = "5";

    public EssayTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EssayTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static EssayTwo newInstance(String param1, String param2) {
        EssayTwo fragment = new EssayTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_essay_two, container, false);
        activity = getActivity();

        submitBtn = v.findViewById(R.id.submitBtn);
        jawabanInput = v.findViewById(R.id.jawabanInput);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.horizontal_shake);

        correctSound = MediaPlayer.create(getContext(), R.raw.quiz_correct);
        wrongSound = MediaPlayer.create(getContext(), R.raw.quiz_wrong);

        submitBtn.setOnClickListener(l -> checkAnswer());
        return v;
    }

    void correct() {
        correctSound.start();
        if (activity instanceof Essay) {
            ((Essay) activity).addScore();
            ((Essay) activity).nextQuestion();
        }
    }

    void checkAnswer() {
        String jawaban = jawabanInput.getText().toString().trim();

        if (jawaban.isEmpty()) {
            jawabanInput.startAnimation(animation);
        } else {
            if (jawaban.equals(ANSWER)) {
                correct();
            } else {
                wrong();
            }
        }
    }

    void wrong() {
        wrongSound.start();
        if (activity instanceof Essay) {
            ((Essay) activity).nextQuestion();
        }
    }
}