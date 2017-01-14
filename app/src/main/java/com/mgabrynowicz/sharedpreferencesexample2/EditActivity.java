package com.mgabrynowicz.sharedpreferencesexample2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCES = "shared preferences";
    public static final String NAME_KEY = "name key";
    public static final String SURNAME_KEY = "surname key";
    public static final String LANGUAGE_KEY = "language key";
    public static final String COUNTRY_KEY = "country key";
    public static final int RESULT_SAVED = 1;
    public static final int RESULT_CANCELED = 0;
    static final int PICK_DATA_REQUEST = 1;

    EditText editTextName;
    EditText editTextSurname;
    Spinner spinnerLanguage;
    Spinner spinnerCountry;
    Button buttonSave, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editTextName = (EditText) findViewById(R.id.edit_text_name);
        editTextSurname = (EditText) findViewById(R.id.edit_text_surname);
        spinnerCountry = (Spinner) findViewById(R.id.spinner_country);
        spinnerLanguage = (Spinner) findViewById(R.id.spinner_language);
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        spinnerCountry.setAdapter(new ArrayAdapter<Country>(this, android.R.layout.simple_spinner_dropdown_item, Country.values()));
        spinnerLanguage.setAdapter(new ArrayAdapter<Language>(this, android.R.layout.simple_spinner_dropdown_item, Language.values()));
        checkIfFieldsEmpty();



        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveToSharedPrefenreces(NAME_KEY, editTextName.getText().toString());
                saveToSharedPrefenreces(SURNAME_KEY, editTextSurname.getText().toString());
                saveToSharedPrefenreces(COUNTRY_KEY, spinnerCountry.getSelectedItem().toString());
                saveToSharedPrefenreces(LANGUAGE_KEY, spinnerLanguage.getSelectedItem().toString());
                returnToMainActivity(RESULT_SAVED);
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                returnToMainActivity(RESULT_CANCELED);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkIfFieldsEmpty();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editTextName.addTextChangedListener(textWatcher);
        editTextSurname.addTextChangedListener(textWatcher);









    }

    private void checkIfFieldsEmpty() {
        if(editTextName.getText().toString().isEmpty() && editTextSurname.getText().toString().isEmpty()) {
            buttonSave.setEnabled(false);
        } else {
            buttonSave.setEnabled(true);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadFormData();
    }

    private void saveToSharedPrefenreces(String sharedPreferencesKey, String textToSave) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putString(sharedPreferencesKey, textToSave);
        sharedPreferencesEditor.commit();


    }

    public void loadFormData() {
        Intent intent = getIntent();
        editTextName.setText(intent.getStringExtra(NAME_KEY));
        editTextSurname.setText(intent.getStringExtra(SURNAME_KEY));
        Enum<Country> country = Country.valueOf(intent.getStringExtra(COUNTRY_KEY));
        Enum<Language> language = Language.valueOf(intent.getStringExtra(LANGUAGE_KEY));
        ArrayAdapter countryAdapter = (ArrayAdapter) spinnerCountry.getAdapter();
        ArrayAdapter languageAdapter = (ArrayAdapter) spinnerLanguage.getAdapter();
        spinnerCountry.setSelection(setSpinner(country, countryAdapter));
        spinnerLanguage.setSelection(setSpinner(language, languageAdapter));


    }


    public int setSpinner(Enum spinnerValue, ArrayAdapter spinnerAdapter) {
        int spinnerPosition = 0;

        if (spinnerValue != null) {
            spinnerPosition = spinnerAdapter.getPosition(spinnerValue);
            return spinnerPosition;

        }
        return spinnerPosition;
    }

    private void returnToMainActivity(int result) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        setResult(result, intent);
        finish();


    }

}