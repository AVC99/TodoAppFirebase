package com.example.todoappfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoappfirebase.model.Task;


import java.util.Date;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText dateEditText;
    private Button createButton;
    private String userID;
    
    private SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
    private Date date; 
    private Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titleEditText = findViewById(R.id.new_task_title);
        descriptionEditText = findViewById(R.id.new_task_description);
        dateEditText = findViewById(R.id.new_task_date);
        createButton = findViewById(R.id.new_task_create_button);

        dateEditText.setFocusable(false);
        dateEditText.setClickable(true);

        createButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String dateLabelText = dateEditText.getText().toString();
            if (title.isEmpty() || description.isEmpty() || dateLabelText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }else{
                Task task = new Task("id",title, description, date, false, userID);
                finish();
            }
        });

        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        
        dateEditText.setOnClickListener(v -> {
            new DatePickerDialog(NewTaskActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

    }

    private void updateLabel() {
        date = myCalendar.getTime();
       dateEditText.setText(dateFormat.format(myCalendar.getTime()));
    }
}