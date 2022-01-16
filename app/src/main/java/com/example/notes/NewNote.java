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
import java.util.concurrent.ExecutionException;

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
        // инициализируем нужные элементы
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSaveNewNote = findViewById(R.id.buttonSaveNewNote);
        buttonCancel = findViewById(R.id.buttonCancel);
        textInputEditTextSetDate = findViewById(R.id.textInputEditText);
        textInputEditTextTitleOfNote = findViewById(R.id.textInputEditTextTitleOfNote);
        textInputEditTextDescriptionOfNote = findViewById(R.id.textInputEditTextDescriptionOfNote);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatCompleteDate = new SimpleDateFormat("dd-MM-yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatD = new SimpleDateFormat("dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatM = new SimpleDateFormat("MM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatY = new SimpleDateFormat("yyyy");

        //  это диалог для вызова календаря для установки даты - разобраться позже
        dialogSetData = new Dialog(this);
        dialogSetData.setContentView(R.layout.dialog);
        calendarView = dialogSetData.findViewById(R.id.calendarViewSetData);

        // получение объекта Bundle  из интента (содержит note.id  для получения  объекта  note из базы данных)
        Bundle noteFields = getIntent().getExtras();

        // проверка что интен пришел не пустой, и если не пустой, то получение объекта note из базы данных и установка из него информации
        // с помощью метода setNoteFieldsToOldNote
        if (noteFields != null) {
                setNoteFieldsToOldNote(viewModel.getByID(noteFields.getInt("noteId")));
        }

        buttonSaveNewNote.setOnClickListener(view -> {
            String newNoteTitle = textInputEditTextTitleOfNote.getText().toString().trim();
            String newNoteDescription = textInputEditTextDescriptionOfNote.getText().toString();
            int newNoteDayOfWeek = 0;
            int radioButtonID = radioGroupPriority.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioButtonID);
            int newNotePriority = Integer.parseInt(radioButton.getText().toString());

            if (isFeeld(newNoteTitle, newNoteDescription, yearOfCompletion, monthOfCompletion, dayOfCompletion)  && (noteFields != null)) {
                Note note = new Note(noteFields.getInt("noteId"), newNoteTitle, newNoteDescription, newNoteDayOfWeek, newNotePriority, simpleDateFormatCompleteDate.format(new Date()), yearOfCompletion, monthOfCompletion, dayOfCompletion);
                viewModel.updateNote(note);
                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);

            } else if (isFeeld(newNoteTitle, newNoteDescription, yearOfCompletion, monthOfCompletion, dayOfCompletion)) {
                Note note = new Note(newNoteTitle, newNoteDescription, newNoteDayOfWeek, newNotePriority, simpleDateFormatCompleteDate.format(new Date()), yearOfCompletion, monthOfCompletion, dayOfCompletion);
                viewModel.insertNote(note);
                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
            } else {
                Toast.makeText(this, "Необходимо заполнить все поля!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(view -> {
            Intent intentToMain = new Intent(this, MainActivity.class);
            startActivity(intentToMain);
        });

        textInputEditTextSetDate.setOnTouchListener((view, motionEvent) -> {
            dialogSetData.show();
            Date date = new Date();

            yearOfCompletion = viewModel.getByID(noteFields.getInt("noteId")).getYearOfCompletion();
            monthOfCompletion = viewModel.getByID(noteFields.getInt("noteId")).getMonthOfCompletion();
            dayOfCompletion = viewModel.getByID(noteFields.getInt("noteId")).getDayOfCompletion();
            dateOf = dayOfCompletion + "-" + monthOfCompletion + "-" + yearOfCompletion;

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

    // вспомогательный метод ля установки названия заметки и описания заметки в поля textInputEditTextTitleOfNote и textInputEditTextDescriptionOfNote
    private void setNoteFieldsToOldNote(Note note) {
        textInputEditTextTitleOfNote.setText(note.getTitle());
        textInputEditTextDescriptionOfNote.setText(note.getDescription());
        textInputEditTextSetDate.setText(note.getDateOfCompletion());
        yearOfCompletion = note.getYearOfCompletion();
        monthOfCompletion = note.getMonthOfCompletion();
        dayOfCompletion = note.getDayOfCompletion();
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearOfCompletion, monthOfCompletion - 1,dayOfCompletion);
        calendarView.setDate(calendar.getTimeInMillis());
    }

    public void saveDate(View view) {
        textInputEditTextSetDate.setText(dateOf);
        dialogSetData.cancel();

    }
}