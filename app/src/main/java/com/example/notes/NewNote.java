package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewNote extends AppCompatActivity {

    EditText editTextNoteTitle;
    EditText editTextNoteDiscription;
    RadioGroup radioGroupPriority;
    Button buttonSaveNewNote;
    private MainViewModel viewModel;
    CalendarView calendarView;
    String dateOf;
    Dialog dialogSetData;

    private int yearOfCompletion;
    private int monthOfCompletion;
    private int dayOfCompletion;
    TextInputEditText textInputEditText;



    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteDiscription = findViewById(R.id.editTextNoteDiscription);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSaveNewNote = findViewById(R.id.buttonSaveNewNote);
        textInputEditText = findViewById(R.id.textInputEditText);
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

            if (isFeeld(newNoteTitle, newNoteDescription, yearOfCompletion, monthOfCompletion, dayOfCompletion)) {

                Note note = new Note(newNoteTitle, newNoteDescription, newNoteDayOfWeek, newNotePriority, simpleDateFormat.format(new Date()), yearOfCompletion, monthOfCompletion, dayOfCompletion);
                viewModel.insertNote(note);
                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
            } else {
                Toast.makeText(this, "Необходимо заполнить все поля!", Toast.LENGTH_SHORT).show();
            }
        });

        textInputEditText.setOnTouchListener((view, motionEvent) -> {
            dialogSetData.show();
            calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
                yearOfCompletion = i;
                monthOfCompletion = i1 + 1;
                dayOfCompletion = i2;
                dateOf = dayOfCompletion + "-" + monthOfCompletion + "-" + yearOfCompletion;
            });
            return true;
        });
    }

    private boolean isFeeld(String title, String description, int Y, int M, int D) {
        return !title.isEmpty() && !description.isEmpty() && Y != 0 && M != 0 && D != 0;
    }

    public void saveDate(View view) {
        Date currentDate = new Date();
        Calendar dateOfCompletion = new GregorianCalendar(yearOfCompletion, monthOfCompletion - 1, dayOfCompletion + 1);
        if (currentDate.before(dateOfCompletion.getTime())) {
            textInputEditText.setText(dateOf);
            dialogSetData.cancel();
        } else {
            Toast.makeText(this, "Выберите дату не позже текущей!", Toast.LENGTH_SHORT).show();
        }
    }
}