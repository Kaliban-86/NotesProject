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
    Button buttonChangeNote;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);

        // скрываем actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // инициализируем элементы:
        // - поле для вывода названия заметки, поле для вывода описания заметки, кнопку "изменить", кнопку "удалить", кнопку "отмена"
        // - ViewModel
        textViewReadNoteTitle = findViewById(R.id.textViewReadNoteTitle);
        textViewReadNoteDescription = findViewById(R.id.textViewReadNoteDescription);
        buttonDeleteNote = findViewById(R.id.buttonDeleteNote);
        buttonCancel = findViewById(R.id.buttonToMain);
        buttonChangeNote = findViewById(R.id.buttonChangeNote);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // получаем интент из MainActivity содержащий note.id
        Bundle noteFields = getIntent().getExtras();

        // создаем интент для перехода в MainActivity
        Intent intentToMain = new Intent(readNote.this, MainActivity.class);

        // если пришедший интен не пустой, то через метод getOldNoteFields получаем объект Note и
        // с помощью метода setNoteFieldsToOldNote  устанавиливаем заголовок (note.title)  и описание заметки (note.description)
        //  в textViewReadNoteTitle и textViewReadNoteDescription соответственно
        if (noteFields != null) {
            setNoteFieldsToOldNote(getOldNoteFields(noteFields));
        }

        // декларируем переменную для хранения ID цвета для установки цвета тексту заголовка заметки
        int colorID;

        // получаем значение приоритета из пришедшего интента через метод getOldNoteFields
        assert noteFields != null;
        int priority = (getOldNoteFields(noteFields)).getPriority();

        // инициализируем переменную colorID в соответствии с принятой системой цветов
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

        // устанавливаем цвет для тектса заголовка заметки
        textViewReadNoteTitle.setTextColor(colorID);

        // реализация кнопки "удалить" (удаляем заметку, полученную с помощью меода getOldNoteFields)
        buttonDeleteNote.setOnClickListener(view -> {
            viewModel.deleteNote(getOldNoteFields(noteFields));
            startActivity(intentToMain);
        });

        // реализация кнопки "отмена" (просто переход на MainActivity)
        buttonCancel.setOnClickListener(view -> {
            startActivity(intentToMain);
        });

        // реализация кнопки "изменить" (создаем интен для перехода в NewNote и передаем с ним note.id заметки которая пришла из MainActivity)
        buttonChangeNote.setOnClickListener(view -> {
            Intent intentToNewNote = new Intent(readNote.this, NewNote.class);
            intentToNewNote.putExtra("noteId", viewModel.getByID(noteFields.getInt("noteId")).getId());
            startActivity(intentToNewNote);
        });
    }

    // вспомогательный метод для получения из пришедшего интента объекта Note по его ID
        private Note getOldNoteFields(Bundle bundle) {
        return viewModel.getByID(bundle.getInt("noteId"));
    }

    // вспомогательный метод для установки note.title и  note.description в поля textViewReadNoteTitle и textViewReadNoteDescription
    private void setNoteFieldsToOldNote(Note note) {
        textViewReadNoteTitle.setText(note.getTitle());
        textViewReadNoteDescription.setText(note.getDescription());
    }
}