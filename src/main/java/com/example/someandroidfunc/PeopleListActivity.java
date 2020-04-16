package com.example.someandroidfunc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PeopleListActivity extends AppCompatActivity implements PersonAdapter.OnPersonListener{

    ArrayList<Person> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        RecyclerView personRecyclerView = findViewById(R.id.personListRecyclerView);

        personList = Person.getData();
        PersonAdapter personAdapter = new PersonAdapter(this,personList, this);
        personRecyclerView.setAdapter(personAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        personRecyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onPersonClick(int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
