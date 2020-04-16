package com.example.someandroidfunc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {

    EditText userNameEditText;
    EditText passwordEditText;
    Button loginButton;
    int loginTries = 3;
    private Toast toast;
    Person activePerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if( getIntent().getBooleanExtra("Exit", false)){
            CloseApp();
        }

        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int errorNo = checkCredential(userNameEditText.getText().toString(),passwordEditText.getText().toString());
                if(errorNo == 0 && activePerson != null){ //Correct
                    toast = Toast.makeText(getApplicationContext(),"Welcome to the Application!",Toast.LENGTH_LONG);
                    toast.show();

                    Intent menuIntent = new Intent(getApplicationContext(),MenuActivity.class);
                    menuIntent.putExtra("USERNAME", activePerson.getUserName());
                    menuIntent.putExtra("USERICON",activePerson.getImageID());
                    startActivity(menuIntent);

                }else{
                    String errorString = "";
                    if(errorNo == 1){
                        errorString = "Wrong Password.";
                    }
                    else if(errorNo == 2){
                        errorString = "Not existing Username.";
                    }

                    loginTries--;
                    if(loginTries <= 0){
                        toast.cancel();
                        toast = Toast.makeText(getApplicationContext(),"No Tries Remaining.\nApplication will shut down",Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                CloseApp();
                            }
                        }, 1000);
                    }
                    else{
                        toast.cancel();
                        toast = Toast.makeText(getApplicationContext(),errorString + "\n Remaining Tries = " + loginTries,Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });
    }

    private int checkCredential(String userName, String password){
        ArrayList<Person> persons = Person.getData();
        boolean personFound = false;
        int i=0;
        while(!personFound && i < persons.size()){
            if(persons.get(i).getUserName().compareTo(userName) == 0){
                personFound = true;
                if(persons.get(i).getPassword().compareTo(password) == 0){
                    activePerson = persons.get(i);
                    return 0; //Correct
                }
            }
            i++;
        }
        if(personFound) return 1; //Wrong Password
        else return 2; //Non existant Username
    }

    private void CloseApp(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
