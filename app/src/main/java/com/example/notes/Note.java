package com.example.notes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;
    private String date;
    private int yearOfCompletion;
    private int monthOfCompletion;
    private int dayOfCompletion;

    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority, String date) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
        this.date = date;
    }

    @Ignore
    public Note(int id, String title, String description, int dayOfWeek, int priority, String date, int yearOfCompletion, int monthOfCompletion, int dayOfCompletion) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
        this.date = date;
        this.yearOfCompletion = yearOfCompletion;
        this.monthOfCompletion = monthOfCompletion;
        this.dayOfCompletion = dayOfCompletion;
    }

    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority, String date, int yearOfCompletion, int monthOfCompletion, int dayOfCompletion) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
        this.date = date;
        this.yearOfCompletion = yearOfCompletion;
        this.monthOfCompletion = monthOfCompletion;
        this.dayOfCompletion = dayOfCompletion;
    }

    public int getYearOfCompletion() {
        return yearOfCompletion;
    }

    public void setYearOfCompletion(int yearOfCompletion) {
        this.yearOfCompletion = yearOfCompletion;
    }

    public int getMonthOfCompletion() {
        return monthOfCompletion;
    }

    public void setMonthOfCompletion(int monthOfCompletion) {
        this.monthOfCompletion = monthOfCompletion;
    }

    public int getDayOfCompletion() {
        return dayOfCompletion;
    }

    public void setDayOfCompletion(int dayOfCompletion) {
        this.dayOfCompletion = dayOfCompletion;
    }




    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    public String getDate() {
        return date;
    }
    public String getDateOfCompletion() {

        return dayOfCompletion + "-" + monthOfCompletion + "-" + yearOfCompletion;
    }
}
