package com.example.todoappfirebase;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signInButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button createAccountButton;
    private Button forgotPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.login_login_button);
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        createAccountButton = findViewById(R.id.login_create_account_button);
        forgotPasswordButton = findViewById(R.id.login_forgot_password);

        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });

        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });
        forgotPasswordButton.setOnClickListener(v ->{
            Toast.makeText(SignInActivity.this, "Sending a recovery Email", Toast.LENGTH_SHORT).show();
            //TODO: Implement forgot password
        });
    }



    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FIREBASE-LOGIN", "signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(SignInActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this, HomeTodo.class);
                startActivity(intent);
            } else {
                Log.w("FIREBASE-LOGIN", "signInWithEmail:failure", task.getException());
                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}