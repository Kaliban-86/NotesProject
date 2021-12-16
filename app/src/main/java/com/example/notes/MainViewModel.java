package com.example.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

import java.util.List;
import java.util.Objects;

public class MainViewModel extends AndroidViewModel {

    private static NoteDatabase database;
    private LiveData<List<Note>> notes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NoteDatabase.getInstance(getApplication());
        notes = database.notesDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public Note getNote(int id){
        return Objects.requireNonNull(notes.getValue()).get(id);
    }

    public void insertNote (Note note){
        new  InsertTask().execute(note);
    }

    public void deleteNote (Note note){
       new DeleteTask().execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllTask().execute();
    }

    public  static  class InsertTask extends AsyncTask<Note,Void,Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if(notes != null && notes.length > 0){
                database.notesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    public  static  class DeleteTask extends AsyncTask<Note,Void,Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if(notes != null && notes.length > 0){
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }
    public  static  class DeleteAllTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... notes) {
            database.notesDao().deleteAllNotes();
            return null;
        }
    }
}
