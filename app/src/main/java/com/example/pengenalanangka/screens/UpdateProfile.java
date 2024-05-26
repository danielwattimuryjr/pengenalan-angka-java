package com.example.pengenalanangka.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pengenalanangka.R;
import com.example.pengenalanangka.helper.Helper;
import com.example.pengenalanangka.screens.auth.SignIn;
import com.example.pengenalanangka.screens.auth.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UpdateProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText usernameInput;
    Button saveBtn;
    ImageButton cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        usernameInput = findViewById(R.id.usernameInput);
        saveBtn = findViewById(R.id.submitBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        mAuth = FirebaseAuth.getInstance();

        saveBtn.setOnClickListener(l -> updateUsername());
        cancelBtn.setOnClickListener(l -> openMainMenu());

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
        usernameInput.setText(
                currentUser.getDisplayName()
        );
    }

    void updateUsername() {
        String username = usernameInput.getText().toString().trim();

        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Helper.showToast(UpdateProfile.this, "Berhasil Update Profile");
                        openMainMenu();
                    }
                });
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