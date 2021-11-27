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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    TextView textViewDateOfCompletion;
    private int yearOfCompletion;
    private int monthOfCompletion;
    private int dayOfCompletion;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteDiscription = findViewById(R.id.editTextNoteDiscription);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSaveNewNote = findViewById(R.id.buttonSaveNewNote);
        textViewDateOfCompletion = findViewById(R.id.textViewDataOfCompletion);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        dialogSetData = new Dialog(this);
        dialogSetData.setContentView(R.layout.dialog);

        calendarView = dialogSetData.findViewById(R.id.calendarViewSetData);
        buttonSaveNewNote.setOnClickListener(view -> {

            String newNoteTitle = editTextNoteTitle.getText().toString().trim();
            String newNoteDescription = editTextNoteDiscription.getText().toString();
            int newNoteDayOfWeek = 0;
            int radioButtonID = radioGroupPriority.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioButtonID);
            int newNotePriority = Integer.parseInt(radioButton.getText().toString());


            if (isFeeld(newNoteTitle, newNoteDescription, yearOfCompletion,monthOfCompletion,dayOfCompletion)) {
                Note note = new Note(newNoteTitle, newNoteDescription, newNoteDayOfWeek, newNotePriority, simpleDateFormat.format(new Date()), yearOfCompletion,monthOfCompletion,dayOfCompletion);
                viewModel.insertNote(note);

                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
            } else {
                Toast.makeText(this, "Необходимо заполнить все поля!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isFeeld(String title, String description, int Y, int M, int D) {
        return !title.isEmpty() && !description.isEmpty() && Y != 0 && M != 0 && D != 0;
    }

    public void chooseData(View view) {
        dialogSetData.show();
        calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            yearOfCompletion = i;
            monthOfCompletion = i1 + 1;
            dayOfCompletion = i2;
            dateOf = dayOfCompletion + "-" + monthOfCompletion + "-" + yearOfCompletion;
        });
    }

    public void saveDate(View view) {
        Toast.makeText(this, dateOf, Toast.LENGTH_SHORT).show();
        textViewDateOfCompletion.setText(dateOf);
        dialogSetData.cancel();
    }
}