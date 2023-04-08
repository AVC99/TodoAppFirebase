package com.example.todoappfirebase.model;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoappfirebase.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private RadioButton radioButton;
    private TextView taskName;
    private TextView taskDescription;
    private TextView taskDate;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        this.radioButton = itemView.findViewById(R.id.task_button);
        this.taskName = itemView.findViewById(R.id.task_name);
        this.taskDescription = itemView.findViewById(R.id.task_description);
        this.taskDate = itemView.findViewById(R.id.task_date);
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

    public TextView getTaskName() {
        return taskName;
    }

    public void setTaskName(TextView taskName) {
        this.taskName = taskName;
    }

    public TextView getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(TextView taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TextView getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(TextView taskDate) {
        this.taskDate = taskDate;
    }
}
