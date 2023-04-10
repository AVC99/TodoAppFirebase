package com.example.todoappfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.todoappfirebase.model.Task;
import com.example.todoappfirebase.model.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class HomeTodo extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private FloatingActionButton addTaskButton;
    private String userID;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private Task newTask;
    private  TaskAdapter taskAdapter;
    private ArrayList<Task> firebaseTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_todo);
        tasks = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        addTaskButton = findViewById(R.id.addTaskFab);
        recyclerView = findViewById(R.id.todo_list_recycler_view);
        taskAdapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(taskAdapter);

        if (extras != null) {
            userID = extras.getString("userID");
            newTask = (Task) extras.getSerializable("newTask");
            getTaskFromFirebase(userID);
            Task randomTask = new Task("2","IMPORTANT", "Description 1", new Date(), false,"UID");

            tasks.add(randomTask);
            if (newTask != null) {

                tasks.add(newTask);
            }
        }

        addTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeTodo.this, NewTaskActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        });



    }

    private void getTaskFromFirebase(String userID) {
        firebaseTasks = new ArrayList<>();
        // get all documents from firebase
        db.collection("tasks").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (int i = 0; i < task.getResult().size(); i++) {
                    Task task1 = task.getResult().getDocuments().get(i).toObject(Task.class);

                    if(task1.getUserID().equals(userID)){
                        Log.d("COLLECTION", "getTaskFromFirebase: " + task1.getTitle());
                        tasks.add(task1);
                        firebaseTasks.add(task1);
                        taskAdapter.notifyDataSetChanged();
                    }
                }
            }
        }).addOnFailureListener(e -> {
            Log.d("COLLECTION-ERROR", "getTaskFromFirebase: " + e.getMessage());
        });


    }
}