package com.example.todoappfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
    private TaskAdapter taskAdapter;
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


        if (extras != null) {
            userID = extras.getString("userID");
            getTaskFromFirebase(userID);
        }

        addTaskButton.setOnClickListener(v -> {
            Bundle extras1 = getIntent().getExtras();
            extras1.putString("userID", userID);
            extras1.putSerializable("taskList", tasks);
            Intent intent = new Intent(HomeTodo.this, NewTaskActivity.class);
            intent.putExtras(extras1);
            startActivity(intent);
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onResume");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            ArrayList<Task> taskList = (ArrayList<Task>) extras.getSerializable("taskList");
            getTaskFromFirebase(userID);
        }
    }



    private void getTaskFromFirebase(String userID) {
        tasks = new ArrayList<>();
        // get all documents from firebase
        db.collection("tasks").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (int i = 0; i < task.getResult().size(); i++) {
                    Task task1 = task.getResult().getDocuments().get(i).toObject(Task.class);

                    if (task1.getUserID().equals(userID)) {
                        Log.d("COLLECTION", "getTaskFromFirebase: " + task1.getTitle());

                        tasks.add(task1);
                        taskAdapter = new TaskAdapter(tasks);
                        recyclerView.setAdapter(taskAdapter);
                    }
                }
            }
        }).addOnFailureListener(e -> {
            Log.d("COLLECTION-ERROR", "getTaskFromFirebase: " + e.getMessage());
        });


    }
}