package com.example.todoappfirebase.model;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoappfirebase.R;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private FirebaseFirestore firebaseDB;
    private ArrayList<Task> tasks;

    public TaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        firebaseDB = FirebaseFirestore.getInstance();
        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        firebaseDB = FirebaseFirestore.getInstance();
        holder.getTaskName().setText(task.getTitle());
        holder.getTaskDescription().setText(task.getDescription());
        holder.getTaskDate().setText(task.getDueDate().toString());

        holder.getRadioButton().setChecked(task.isCompleted());

        if (task.isCompleted()) {
            holder.getTaskName().setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.getTaskDescription().setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.getTaskDate().setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.getTaskName().setPaintFlags(holder.getTaskName().getPaintFlags() &
                    (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.getTaskDescription().setPaintFlags(holder.getTaskDescription().getPaintFlags() &
                    (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.getTaskDate().setPaintFlags(holder.getTaskDate().getPaintFlags() &
                    (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.getRadioButton().setOnClickListener(v -> {

            if (holder.getRadioButton().isChecked()) {
                holder.getTaskName().setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.getTaskDescription().setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.getTaskDate().setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tasks.get(position).setIsCompleted(true);

                firebaseDB.collection("tasks").whereEqualTo("id", task.getId()).get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                task1.getResult().getDocuments().get(0).getReference().update("isCompleted", true);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.d("UPDATE-ERROR", "onFailure: " + e.getMessage());
                        });

            } else {
                holder.getTaskName().setPaintFlags(holder.getTaskName().getPaintFlags() &
                        (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.getTaskDescription().setPaintFlags(holder.getTaskDescription().getPaintFlags() &
                        (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.getTaskDate().setPaintFlags(holder.getTaskDate().getPaintFlags() &
                        (~Paint.STRIKE_THRU_TEXT_FLAG));

                tasks.get(position).setIsCompleted(true);
                firebaseDB.collection("tasks").whereEqualTo("id", task.getId()).get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                task1.getResult().getDocuments().get(0).getReference().update("isCompleted", false);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.d("UPDATE-ERROR", "onFailure: " + e.getMessage());
                        });
            }

        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


}
