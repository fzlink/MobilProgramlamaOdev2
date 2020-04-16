package com.example.someandroidfunc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EmailActivity extends AppCompatActivity {

    EditText toEmailEditText;
    EditText subjectEditText;
    EditText contentEditText;
    ImageButton attachmentButton;
    Button sendButton;

    Uri attachmentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        toEmailEditText = findViewById(R.id.emailToEmailEditText);
        subjectEditText = findViewById(R.id.emailSubjectEditText);
        contentEditText = findViewById(R.id.emailContentEditText);
        attachmentButton = findViewById(R.id.emailAttachmentButton);
        sendButton = findViewById(R.id.emailSendButton);

        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail();

            }
        });
    }
    static final int REQUEST_FILE_GET = 1;
    private void selectFile(){
        Intent getFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getFileIntent.setType("*/*");
        if(getFileIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(getFileIntent,REQUEST_FILE_GET);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_FILE_GET && resultCode == RESULT_OK){
            Toast toast = Toast.makeText(getApplicationContext(),"Attachment received successfully!",Toast.LENGTH_SHORT);
            toast.show();
            attachmentData = data.getData();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),"Attachment couldn't received",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void composeEmail(){

        String toEmail = toEmailEditText.getText().toString();
        String[] toEmails = {toEmail};
        String subject = subjectEditText.getText().toString();
        String content = contentEditText.getText().toString();
        Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
        sendEmailIntent.setType("message/rfc822");
        sendEmailIntent.putExtra(Intent.EXTRA_EMAIL,toEmails);
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        sendEmailIntent.putExtra(Intent.EXTRA_TEXT,content);
        if(attachmentData != null){
            sendEmailIntent.putExtra(Intent.EXTRA_STREAM,attachmentData);
        }
        if(sendEmailIntent.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(sendEmailIntent,"Select an email client"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
