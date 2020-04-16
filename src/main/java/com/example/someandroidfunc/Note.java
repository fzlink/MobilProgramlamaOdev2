package com.example.someandroidfunc;

import java.util.ArrayList;

public class Note {

    private String title;
    private String content;
    private String date;

    public Note(String title, String content, String date){
        this.title = title;
        this.content = content;
        this.date = date;
    }
    public Note(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public static ArrayList<Note> getData(){
        ArrayList<Note> noteList = new ArrayList<>();
        noteList.add(new Note("first note","This is the content of the first note", "1.1.2020"));
        noteList.add(new Note("second note","This is the content of the second note", "2.1.2020"));
        return noteList;
    }



}
