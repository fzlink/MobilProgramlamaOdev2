package com.example.someandroidfunc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;

public class NoteActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText contentEditText;
    Button deleteButton;
    Button saveButton;

    boolean isEditNote = false;
    String originalTitle;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleEditText = findViewById(R.id.noteTitleEditText);
        contentEditText = findViewById(R.id.noteContentEditText);
        deleteButton = findViewById(R.id.noteDeleteButton);
        saveButton = findViewById(R.id.noteSaveButton);

        if(getIntent().hasExtra("USERNAME")){
            userName = getIntent().getExtras().getString("USERNAME");
        }

        if(getIntent().hasExtra("NOTETITLE")){
            originalTitle = getIntent().getExtras().getString("NOTETITLE");
            titleEditText.setText(originalTitle);
            isEditNote = true;
        }
        if(getIntent().hasExtra("NOTECONTENT")){
            contentEditText.setText(getIntent().getExtras().getString("NOTECONTENT"));
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditNote){
                    File file = new File(getFilesDir(),"notes" + userName);
                    File oldFile = new File(file,originalTitle + ".txt");
                    oldFile.delete();
                }
                Intent intent = new Intent(getApplicationContext(),NotesListActivity.class);
                intent.putExtra("USERNAME",userName);
                startActivity(intent);
                finish();

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    File file = new File(getFilesDir(), "notes" + userName);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    try {
                        if(isEditNote){
                            File oldfile = new File(file,originalTitle + ".txt");
                            oldfile.delete();
                        }

                        File newFile = new File(file, titleEditText.getText().toString() + ".txt");
                        FileWriter writer = new FileWriter(newFile);
                        writer.append(contentEditText.getText().toString());
                        writer.flush();
                        writer.close();
                        Toast.makeText(NoteActivity.this, "Note Saved as .txt file", Toast.LENGTH_LONG).show();
                    } catch (Exception e) { }

                    Intent intent = new Intent(getApplicationContext(),NotesListActivity.class);
                    intent.putExtra("USERNAME",userName);
                    startActivity(intent);
                    finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
