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

public class SignIn extends AppCompatActivity {
    private static final String TAG = ".SignIn";
    private FirebaseAuth mAuth;
    TextInputEditText emailInput, passwordInput;
    Button submitBtn;
    TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        submitBtn = findViewById(R.id.submitBtn);
        signUpLink = findViewById(R.id.signUpLink);

        signUpLink.setOnClickListener(l -> openSignUp());
        submitBtn.setOnClickListener(l -> signIn());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            openMainMenu();
        }
    }

    void openMainMenu() {
        Helper.navigateToActivity(this, MainMenu.class);
        finish();
    }

    void openSignUp() {
        Helper.navigateToActivity(this, SignUp.class);
        finish();
    }

    void signIn() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Helper.showToast(SignIn.this, "Berhasil Login");
                            openMainMenu();
                        } else {
                            Exception exception = task.getException();
                            Helper.showToast(SignIn.this, "Login Gagal");

                            Log.d(TAG, "Error\t: " + exception.getMessage());
                        }
                    }
                });
    }
}