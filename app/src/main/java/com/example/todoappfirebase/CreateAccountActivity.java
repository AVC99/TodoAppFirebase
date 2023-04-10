package com.example.todoappfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button createAccountButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.create_account_email);
        passwordEditText = findViewById(R.id.create_account_password);
        confirmPasswordEditText = findViewById(R.id.create_account_confirm_password);
        createAccountButton = findViewById(R.id.create_account_create_button);
        loginButton = findViewById(R.id.create_account_login_button);


        createAccountButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(CreateAccountActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
            }else{
                if (password.equals(confirmPassword)){
                    createAccount(email, password);
                }else{
                    Toast.makeText(CreateAccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this, SignInActivity.class);
            startActivity(intent);
        });

    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateAccountActivity.this, HomeTodo.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(CreateAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}