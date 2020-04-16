package com.example.someandroidfunc;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class NotesListActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener{

    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    ArrayList<Note> noteList = new ArrayList<>();

    String userName;
    ImageButton addNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);

        if(getIntent().hasExtra("USERNAME")){
            userName = getIntent().getExtras().getString("USERNAME");
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        FilenameFilter filter = new TextFileFilter();
        File notesDir = new File(getFilesDir(), "notes" + userName);
        if(!notesDir.exists()){
            notesDir.mkdir();
        }

        File[] noteFiles = notesDir.listFiles(filter);



        for(int i =0;i<noteFiles.length;i++){
                Note note = new Note();
                String title = noteFiles[i].getName().substring(0,noteFiles[i].getName().lastIndexOf('.'));
                note.setTitle(title);
                StringBuilder content = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(noteFiles[i]));
                    String line;

                    while ((line = br.readLine()) != null) {
                        content.append(line);
                        content.append('\n');
                    }
                    br.close();
                }
                catch (IOException e) {

                }
                note.setContent(content.toString());
                Date lastModified = new Date(noteFiles[i].lastModified());
                note.setDate(dateFormat.format(lastModified));
                noteList.add(note);

        }


        NoteAdapter noteAdapter = new NoteAdapter(this,noteList,this);
        notesRecyclerView.setAdapter(noteAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notesRecyclerView.setLayoutManager(linearLayoutManager);


        addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(getApplicationContext(),NoteActivity.class);
                noteIntent.putExtra("USERNAME",userName);
                startActivity(noteIntent);
                finish();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Intent noteIntent = new Intent(this,NoteActivity.class);
        noteIntent.putExtra("NOTETITLE",noteList.get(position).getTitle());
        noteIntent.putExtra("NOTECONTENT",noteList.get(position).getContent());
        noteIntent.putExtra("USERNAME",userName);
        startActivity(noteIntent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
