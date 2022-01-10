package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class readNote extends AppCompatActivity {

    TextView textViewReadNoteTitle;
    TextView textViewReadNoteDescription;
    Button buttonDeleteNote;
    Button buttonCancel;
    Button buttonCChangeNote;
    int noteID;
    ConstraintLayout constraintLayout;
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
        buttonCChangeNote = findViewById(R.id.buttonChangeNote);
        constraintLayout = findViewById(R.id.constraintlayout);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Bundle noteFields = getIntent().getExtras();
        Intent intentToMain = new Intent(readNote.this, MainActivity.class);

        //viewModel.getNote(noteFields.getInt("noteId")).getTitle();


        if (noteFields != null) {
            setNoteFieldsToOldNote(getOldNoteFields(noteFields));
        }

        int colorID;
        int priority = (getOldNoteFields(noteFields)).getPriority();
        switch (priority) {
            case 1:
                colorID = getResources().getColor(R.color.red);
                break;
            case 2:
                colorID = getResources().getColor(R.color.blue);
                break;
            default:
                colorID = getResources().getColor(R.color.green);
                break;
        }

        textViewReadNoteTitle.setTextColor(colorID);
        //constraintLayout.setBackgroundColor(colorID);

        buttonDeleteNote.setOnClickListener(view -> {
            assert noteFields != null;
            viewModel.deleteNote(getOldNoteFields(noteFields));
            startActivity(intentToMain);
        });
        buttonCancel.setOnClickListener(view -> {
            startActivity(intentToMain);
        });
        Note note;


        try {
           note =  viewModel.getByID(noteFields.getInt("noteId"));
            Toast.makeText(this, note.getDescription(), Toast.LENGTH_SHORT).show();
            noteID = note.getId();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        buttonCChangeNote.setOnClickListener(view -> {
            Intent intentToNewNote = new Intent(readNote.this, NewNote.class);
            intentToNewNote.putExtra("noteId",noteID);
            startActivity(intentToNewNote);
        });
    }

    private Note getOldNoteFields(Bundle bundle) {

        //Note note2 = viewModel.getByID(bundle.getInt("noteId"));

        return new Note(bundle.getInt("noteId"),bundle.getString("noteSTitle")
                , bundle.getString("noteSDescription"), bundle.getInt("noteSDayOfWeek")
                , bundle.getInt("noteSPriority"), bundle.getString("noteSDate")
                , bundle.getInt("noteSYearOfCompletion"), bundle.getInt("noteSMonthOfCompletion")
                , bundle.getInt("noteSDayOfCompletion"));
        //return note2;
    }

    private void setNoteFieldsToOldNote(Note note) {
        textViewReadNoteTitle.setText(note.getTitle());
        textViewReadNoteDescription.setText(note.getDescription());
    }
}