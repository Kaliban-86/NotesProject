package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNote extends AppCompatActivity {

    EditText editTextNoteTitle;
    EditText editTextNoteDiscription;
    //Spinner spinnerDaysOfWeek;
    RadioGroup radioGroupPriority;
    Button buttonSaveNewNote;
    private MainViewModel viewModel;
    CalendarView calendarView;
    String dateOf;
    Dialog dialogSetData;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteDiscription = findViewById(R.id.editTextNoteDiscription);
        //spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSaveNewNote = findViewById(R.id.buttonSaveNewNote);
        //calendarView = findViewById(R.id.calendarViewSetData);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        dialogSetData = new Dialog(this);
        //dialogSetData.setTitle("Выберите дату");
        dialogSetData.setContentView(R.layout.dialog);

        calendarView = dialogSetData.findViewById(R.id.calendarViewSetData);
        buttonSaveNewNote.setOnClickListener(view -> {

            String newNoteTitle = editTextNoteTitle.getText().toString().trim();
            String newNoteDescription = editTextNoteDiscription.getText().toString();
            int newNoteDayOfWeek = 0;
            int radioButtonID = radioGroupPriority.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioButtonID);
            int newNotePriority = Integer.parseInt(radioButton.getText().toString());

           // Toast.makeText(this, dateOf, Toast.LENGTH_SHORT).show();


            if (isFeeld(newNoteTitle, newNoteDescription)) {
                Note note = new Note(newNoteTitle, newNoteDescription, newNoteDayOfWeek, newNotePriority, simpleDateFormat.format(new Date()));
                viewModel.insertNote(note);

                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
            } else {
                Toast.makeText(this, "Необходимо заполнить все поля!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isFeeld(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }

    public void chooseData(View view) {
        dialogSetData.show();
        calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            int Y = i;
            int M = i1 + 1;
            int D = i2;
            dateOf = D + "-" + M + "-" + Y;
        });
    }

    public void saveDate(View view) {
        Toast.makeText(this, dateOf, Toast.LENGTH_SHORT).show();
        dialogSetData.cancel();
    }
}