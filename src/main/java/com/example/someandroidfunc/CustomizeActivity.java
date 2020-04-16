package com.example.someandroidfunc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomizeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String USERPREFERENCES = "UserPrefs" ;
    private static final String USERNAME = "userName";
    private static final String AGE = "age";
    private static final String WEIGHT = "weight";
    private static final String HEIGHT = "height";
    private static final String LANGUAGE = "language";
    private static final String GENDER = "gender";
    private static final String LIGHTDARKMODE = "mode";

    private String activePersonUserName;

    EditText userNameEditText;
    EditText ageEditText;
    EditText weightEditText;
    EditText heightEditText;
    Spinner genderSpinner;
    Spinner languageSpinner;
    Switch modeSwitch;
    Button saveButton;

    String[] genderArraySpinner = new String[]{
            "Male", "Female"
    };

    String[] languageArraySpinner = new String[]{
            "English", "Turkish", "Japanese"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        userNameEditText = findViewById(R.id.customizeUserNameEditText);
        ageEditText = findViewById(R.id.customizeAgeEditText);
        weightEditText = findViewById(R.id.customizeWeightEditText);
        heightEditText = findViewById(R.id.customizeHeightEditText);
        genderSpinner = findViewById(R.id.customizeGenderSpinner);
        languageSpinner = findViewById(R.id.customizeLanguageSpinner);
        modeSwitch = findViewById(R.id.customizeModeSwitch);
        saveButton = findViewById(R.id.customizeSaveButton);

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, languageArraySpinner);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genderArraySpinner);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        if(getIntent().hasExtra("USERNAME")){
            activePersonUserName = getIntent().getExtras().getString("USERNAME");
        }

        sharedPreferences = getSharedPreferences(USERPREFERENCES + activePersonUserName, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(USERNAME)){
            userNameEditText.setText(sharedPreferences.getString(USERNAME,""));
            userNameEditText.setEnabled(false);
        }

        ageEditText.setText(sharedPreferences.getString(AGE,"0"));
        weightEditText.setText(sharedPreferences.getString(WEIGHT,"0"));
        heightEditText.setText(sharedPreferences.getString(HEIGHT,"0"));
        genderSpinner.setSelection(sharedPreferences.getInt(GENDER,0));
        languageSpinner.setSelection(sharedPreferences.getInt(LANGUAGE,0));
        modeSwitch.setChecked(sharedPreferences.getBoolean(LIGHTDARKMODE,false));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = userNameEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String weight = weightEditText.getText().toString();
                String height = heightEditText.getText().toString();

                int language = languageSpinner.getSelectedItemPosition();
                int gender = genderSpinner.getSelectedItemPosition();

                boolean lightDarkMode = modeSwitch.isChecked();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(USERNAME,userName);
                editor.putString(AGE,age);
                editor.putString(WEIGHT,weight);
                editor.putString(HEIGHT,height);
                editor.putInt(GENDER,gender);
                editor.putInt(LANGUAGE,language);
                editor.putBoolean(LIGHTDARKMODE,lightDarkMode);

                editor.commit();
                Toast.makeText(getApplicationContext(),"Preferences Saved!",Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
