package com.mgabrynowicz.sharedpreferencesexample2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textViewName;
    TextView textViewSurname;
    TextView textViewLanguage;
    TextView textViewCountry;
    Button editButton;
    public static final String SHARED_PREFERENCES = "shared preferences";
    public static final String NAME_KEY = "name key";
    public static final String SURNAME_KEY = "surname key";
    public static final String LANGUAGE_KEY = "language key";
    public static final String COUNTRY_KEY = "country key";
    public static final int RESULT_SAVED = 1;
    public static final int RESULT_CANCELED = 0;
    static final int PICK_DATA_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName = (TextView) findViewById(R.id.text_view_name);
        textViewSurname = (TextView) findViewById(R.id.text_view_surname);
        textViewLanguage = (TextView) findViewById(R.id.text_view_language);
        textViewCountry = (TextView) findViewById(R.id.text_view_country);
        editButton = (Button) findViewById(R.id.button_edit);

        loadFormData();


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent editActivityIntent = new Intent(getApplicationContext(), EditActivity.class);
                editActivityIntent.putExtra(NAME_KEY, textViewName.getText().toString());
                editActivityIntent.putExtra(SURNAME_KEY, textViewSurname.getText().toString());
                editActivityIntent.putExtra(LANGUAGE_KEY, textViewLanguage.getText().toString());
                editActivityIntent.putExtra(COUNTRY_KEY, textViewCountry.getText().toString());

                startActivity(editActivityIntent);

            }
        });


    }

    @Override
    protected void onResume() {

        loadFormData();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_SAVED) {

            Toast.makeText(getApplicationContext(), "Results saved!", Toast.LENGTH_SHORT).show();

        }

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Edition canceled", Toast.LENGTH_SHORT).show();
        }


    }

    public String getStringFromSharedPreferences(String sharedPreferencesKey) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String textToSet = sharedPreferences.getString(sharedPreferencesKey, "Default");
        return textToSet;
    }

    public void loadFormData() {

        textViewName.setText(getStringFromSharedPreferences(NAME_KEY));
        textViewSurname.setText(getStringFromSharedPreferences(SURNAME_KEY));
        textViewLanguage.setText(getStringFromSharedPreferences(LANGUAGE_KEY));
        textViewCountry.setText(getStringFromSharedPreferences(COUNTRY_KEY));


    }

}
