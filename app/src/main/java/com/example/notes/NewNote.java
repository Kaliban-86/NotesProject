package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNote extends AppCompatActivity {

    EditText editTextNoteTitle;
    EditText editTextNoteDiscription;
    Spinner spinnerDaysOfWeek;
    RadioGroup radioGroupPriority;
    Button buttonSaveNewNote;
    private  MainViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteDiscription = findViewById(R.id.editTextNoteDiscription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupePriority);
        buttonSaveNewNote = findViewById(R.id.buttonSaveNewNote);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


        buttonSaveNewNote.setOnClickListener(view -> {

            String newNoteTitle = editTextNoteTitle.getText().toString().trim();
            String newNoteDescription = editTextNoteDiscription.getText().toString();
            int newNoteDayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
            int radioButtonID = radioGroupPriority.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioButtonID);
            int newNotePriority = Integer.parseInt(radioButton.getText().toString());

            if (isFeeld(newNoteTitle, newNoteDescription)) {
                Note note = new Note(newNoteTitle,newNoteDescription,newNoteDayOfWeek,newNotePriority,simpleDateFormat.format(new Date()));
                viewModel.insertNote(note);
                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
            } else {
                Toast.makeText(this,"Необходимо заполнить все поля!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isFeeld(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}