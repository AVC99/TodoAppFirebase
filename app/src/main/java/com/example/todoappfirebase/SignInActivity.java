package com.example.todoappfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.BreakIterator;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signInButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button createAccountButton;
    private Button forgotPasswordButton;

    private Button resetPasswordButton;


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
        forgotPasswordButton.setOnClickListener(this::onButtonShowPopUp);
    }

    private void sendResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FIREBASE-RESET", "Email sent.");
                Toast.makeText(SignInActivity.this, "Sending a recovery Email", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("FIREBASE-RESET", e.getMessage());
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FIREBASE-LOGIN", "signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(SignInActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this, HomeTodo.class);

                Bundle b = new Bundle();
                b.putString("email", email);
                b.putString("userID", user.getUid());

                intent.putExtras(b);
                startActivity(intent);
                finish();
            } else {
                Log.w("FIREBASE-LOGIN", "signInWithEmail:failure", task.getException());
                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void onButtonShowPopUp(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_forgot_password, null);
        resetPasswordButton = popupView.findViewById(R.id.reset_password_button);

        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setElevation(30);
        // show the popup window in the middle of the screen

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        resetPasswordButton.setOnClickListener(v -> {
            EditText popUpEmailTextEdit = popupView.findViewById(R.id.pop_up_email);
            String email = popUpEmailTextEdit.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                sendResetEmail(email);
                Toast.makeText(SignInActivity.this, "Sending a recovery Email", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

    }
}