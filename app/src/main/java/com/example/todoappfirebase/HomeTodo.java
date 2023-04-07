package com.example.todoappfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeTodo extends AppCompatActivity {

    private FloatingActionButton addTaskButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_todo);

        addTaskButton = findViewById(R.id.addTaskFab);

        addTaskButton.setOnClickListener(v -> {

        });
    }
}