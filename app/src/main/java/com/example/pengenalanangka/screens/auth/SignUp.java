package com.example.pengenalanangka.screens.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.MainMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {
    private static final String TAG = ".SignUp";
    private FirebaseAuth mAuth;
    TextInputEditText emailInput, passwordInput, usernameInput;
    Button submitBtn;
    TextView signInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        usernameInput = findViewById(R.id.usernameInput);
        submitBtn = findViewById(R.id.submitBtn);
        signInLink = findViewById(R.id.signInLink);

        signInLink.setOnClickListener(l -> openSignIn());
        submitBtn.setOnClickListener(l -> signUp());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            openMainMenu();
        }
    }

    void openMainMenu() {
        Helper.navigateToActivity(this, MainMenu.class);
        finish();
    }

    void openSignIn() {
        Helper.navigateToActivity(this, SignUp.class);
        finish();
    }

    void signUp() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();

                            user.updateProfile(profileChangeRequest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Helper.showToast(SignUp.this, "Berhasil Register");
                                            openMainMenu();
                                        }
                                    });
                        } else {
                            Exception exception = task.getException();
                            Helper.showToast(SignUp.this, "Register Gagal");

                            Log.d(TAG, "Error\t: " + exception.getMessage());
                        }
                    }
                });
    }
}