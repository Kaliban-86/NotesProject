package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewNote extends AppCompatActivity {

    RadioGroup radioGroupPriority;
    Button buttonSaveNewNote;
    Button buttonCancel;
    private MainViewModel viewModel;
    CalendarView calendarView;
    String dateOf;
    Dialog dialogSetData;
    private int yearOfCompletion;
    private int monthOfCompletion;
    private int dayOfCompletion;
    TextInputEditText textInputEditTextSetDate;
    TextInputEditText textInputEditTextTitleOfNote;
    TextInputEditText textInputEditTextDescriptionOfNote;


    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSaveNewNote = findViewById(R.id.buttonSaveNewNote);
        buttonCancel = findViewById(R.id.buttonCancel);
        textInputEditTextSetDate = findViewById(R.id.textInputEditText);
        textInputEditTextTitleOfNote = findViewById(R.id.textInputEditTextTitleOfNote);
        textInputEditTextDescriptionOfNote = findViewById(R.id.textInputEditTextDescriptionOfNote);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        dialogSetData = new Dialog(this);
        dialogSetData.setContentView(R.layout.dialog);
        calendarView = dialogSetData.findViewById(R.id.calendarViewSetData);

        buttonCancel.setOnClickListener(view -> {
            Intent intentToMain = new Intent(this, MainActivity.class);
            Bundle noteFeelds = getIntent().getExtras();
            if (noteFeelds != null) {
                viewModel.insertNote(getOldNoteFields(noteFeelds));
                startActivity(intentToMain);
            } else {
                startActivity(intentToMain);
            }
        });

        buttonSaveNewNote.setOnClickListener(view -> {
            String newNoteTitle = textInputEditTextTitleOfNote.getText().toString().trim();
            String newNoteDescription = textInputEditTextDescriptionOfNote.getText().toString();
            int newNoteDayOfWeek = 0;
            int radioButtonID = radioGroupPriority.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioButtonID);
            int newNotePriority = Integer.parseInt(radioButton.getText().toString());

            if (isFeeld(newNoteTitle, newNoteDescription, yearOfCompletion, monthOfCompletion, dayOfCompletion)) {

                Note note = new Note(newNoteTitle, newNoteDescription, newNoteDayOfWeek, newNotePriority, simpleDateFormat.format(new Date()), yearOfCompletion, monthOfCompletion, dayOfCompletion);
                viewModel.insertNote(note);
                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
            } else {
                Toast.makeText(this, "Необходимо заполнить все поля!", Toast.LENGTH_SHORT).show();
            }
        });

        textInputEditTextSetDate.setOnTouchListener((view, motionEvent) -> {
            dialogSetData.show();
            calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
                yearOfCompletion = i;
                monthOfCompletion = i1 + 1;
                dayOfCompletion = i2;
                dateOf = dayOfCompletion + "-" + monthOfCompletion + "-" + yearOfCompletion;
            });
            return true;
        });

        Bundle noteFields = getIntent().getExtras();
        if (noteFields != null) {
            setNoteFieldsToOldNote(getOldNoteFields(noteFields));
        }
    }

    private boolean isFeeld(String title, String description, int Y, int M, int D) {
        return !title.isEmpty() && !description.isEmpty() && Y != 0 && M != 0 && D != 0;
    }

    private Note getOldNoteFields(Bundle bundle) {
        return new Note(bundle.getString("noteSTitle")
                , bundle.getString("noteSDescription"), bundle.getInt("noteSDayOfWeek")
                , bundle.getInt("noteSPriority"), bundle.getString("noteSDate")
                , bundle.getInt("noteSYearOfCompletion"), bundle.getInt("noteSMonthOfCompletion")
                , bundle.getInt("noteSDayOfCompletion"));
    }

    private void setNoteFieldsToOldNote(Note note) {
        textInputEditTextTitleOfNote.setText(note.getTitle());
        textInputEditTextDescriptionOfNote.setText(note.getDescription());
    }

    public void saveDate(View view) {
        Date currentDate = new Date();
        Calendar dateOfCompletion = new GregorianCalendar(yearOfCompletion, monthOfCompletion - 1, dayOfCompletion + 1);
        if (currentDate.before(dateOfCompletion.getTime())) {
            textInputEditTextSetDate.setText(dateOf);
            dialogSetData.cancel();
        } else {
            Toast.makeText(this, "Выберите дату не позже текущей!", Toast.LENGTH_SHORT).show();
        }
    }
}