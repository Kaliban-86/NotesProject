package com.example.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class},version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase database;
    private static final String DB_NAME = "notes3.db";
    private static final Object LOCK = new Object();

    public static NoteDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }
    public abstract NotesDao notesDao();
}
