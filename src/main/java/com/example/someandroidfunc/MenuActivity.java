package com.example.someandroidfunc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuActivity extends AppCompatActivity {

    CardView emailCard;
    CardView peopleCard;
    CardView customizeCard;
    CardView notesCard;
    CardView sensorsCard;
    CardView broadcastReceiverCard;
    CardView asyncCard;
    CardView locationCard;
    CardView alarmCard;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        emailCard = findViewById(R.id.emailCard);
        peopleCard = findViewById(R.id.peopleCard);
        customizeCard = findViewById(R.id.customizeCard);
        notesCard = findViewById(R.id.notesCard);
        sensorsCard = findViewById(R.id.sensorsCard);
        broadcastReceiverCard = findViewById(R.id.broadcastReceiverCard);
        asyncCard = findViewById(R.id.asyncCard);
        locationCard = findViewById(R.id.locationCard);
        alarmCard = findViewById(R.id.alarmCard);


        if(getIntent().hasExtra("USERNAME")){
            TextView userNameTextView = findViewById(R.id.menuUserNameText);
            userName = getIntent().getExtras().getString("USERNAME");
            userNameTextView.setText(userName);
        }
        if(getIntent().hasExtra("USERICON")){
            ImageView userIconImageView = findViewById(R.id.menuUserImage);
            int userIconID = getIntent().getExtras().getInt("USERICON");
            userIconImageView.setImageResource(userIconID);
        }

        emailCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(getApplicationContext(),EmailActivity.class);
                startActivity(emailIntent);
            }
        });
        peopleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent peopleIntent = new Intent(getApplicationContext(),PeopleListActivity.class);
                startActivity(peopleIntent);
            }
        });
        customizeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customizeIntent = new Intent(getApplicationContext(),CustomizeActivity.class);
                customizeIntent.putExtra("USERNAME", userName);
                startActivity(customizeIntent);
            }
        });
        notesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notesIntent = new Intent(getApplicationContext(), NotesListActivity.class);
                notesIntent.putExtra("USERNAME", userName);
                startActivity(notesIntent);
            }
        });
        sensorsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sensorsIntent = new Intent(getApplicationContext(),SensorsActivity.class);
                startActivity(sensorsIntent);
            }
        });
        broadcastReceiverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcastIntent = new Intent(getApplicationContext(),BroadcastReceiverActivity.class);
                startActivity(broadcastIntent);
            }
        });
        asyncCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asyncTaskIntent = new Intent(getApplicationContext(),AsyncTaskActivity.class);
                startActivity(asyncTaskIntent);
            }
        });
        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationIntent = new Intent(getApplicationContext(),LocationActivity.class);
                startActivity(locationIntent);
            }
        });
        alarmCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(alarmIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
