package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class readNote extends AppCompatActivity {

    TextView textViewReadNoteTitle;
    TextView textViewReadNoteDescription;
    Button buttonDeleteNote;
    Button buttonCancel;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        textViewReadNoteTitle = findViewById(R.id.textViewReadNoteTitle);
        textViewReadNoteDescription = findViewById(R.id.textViewReadNoteDescription);
        buttonDeleteNote = findViewById(R.id.buttonDeleteNote);
        buttonCancel = findViewById(R.id.buttonToMain);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Bundle noteFields = getIntent().getExtras();
        Intent intentToMain = new Intent(readNote.this, MainActivity.class);

        if (noteFields != null) {
            setNoteFieldsToOldNote(getOldNoteFields(noteFields));
        }

        buttonDeleteNote.setOnClickListener(view -> {
            assert noteFields != null;
            viewModel.deleteNote(getOldNoteFields(noteFields));
            startActivity(intentToMain);
        });
        buttonCancel.setOnClickListener(view -> {
            startActivity(intentToMain);
        });
    }

    private Note getOldNoteFields(Bundle bundle) {
        return new Note(bundle.getInt("noteId"),bundle.getString("noteSTitle")
                , bundle.getString("noteSDescription"), bundle.getInt("noteSDayOfWeek")
                , bundle.getInt("noteSPriority"), bundle.getString("noteSDate")
                , bundle.getInt("noteSYearOfCompletion"), bundle.getInt("noteSMonthOfCompletion")
                , bundle.getInt("noteSDayOfCompletion"));
    }

    private void setNoteFieldsToOldNote(Note note) {
        textViewReadNoteTitle.setText(note.getTitle());
        textViewReadNoteDescription.setText(note.getDescription());
    }
}