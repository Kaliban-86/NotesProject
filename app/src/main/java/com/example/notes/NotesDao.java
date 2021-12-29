package com.example.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY priority")
    LiveData <List<Note>> getAllNotes();

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM notes WHERE id = :noteId")
    Note getById(int noteId);

    @Delete
    void  deleteNote (Note note);

    @Query("DELETE FROM notes")
    void  deleteAllNotes();
}
