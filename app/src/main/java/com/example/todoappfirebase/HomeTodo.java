package com.example.todoappfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.todoappfirebase.model.Task;
import com.example.todoappfirebase.model.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class HomeTodo extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private FloatingActionButton addTaskButton;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_todo);
        tasks = new ArrayList<>();

        tasks.add(new Task("1", "Task 1", "Description 1", new Date(), false));
        tasks.add(new Task("1", "Task 1", "Description 1", new Date(), false));

        addTaskButton = findViewById(R.id.addTaskFab);
        recyclerView = findViewById(R.id.todo_list_recycler_view);
        TaskAdapter taskAdapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(taskAdapter);


        addTaskButton.setOnClickListener(v -> {

        });
    }
}