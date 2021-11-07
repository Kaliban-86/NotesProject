package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

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

public class NewNote extends AppCompatActivity {

    EditText editTextNoteTitle;
    EditText editTextNoteDiscription;
    Spinner spinnerDaysOfWeek;
    RadioGroup radioGroupPriority;
    Button buttonSaveNewNote;
    NotesDBHelper dbHelper;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteDiscription = findViewById(R.id.editTextNoteDiscription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupePriority);
        buttonSaveNewNote = findViewById(R.id.buttonSaveNewNote);

        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();

        buttonSaveNewNote.setOnClickListener(view -> {

            String newNoteTitle = editTextNoteTitle.getText().toString().trim();
            String newNoteDescription = editTextNoteDiscription.getText().toString();
            int newNoteDayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
            int radioButtonID = radioGroupPriority.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioButtonID);
            int newNotePriority = Integer.parseInt(radioButton.getText().toString());

            if (isFeeld(newNoteTitle, newNoteDescription)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, newNoteTitle);
                contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, newNoteDescription);
                contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, newNoteDayOfWeek + 1);
                contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, newNotePriority);

                database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);

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