package com.example.watercheck;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText ageEditText;
    private RadioGroup genderRadioGroup;
    private Button createProfileButton;
    private Button selectProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);

        nameEditText = findViewById(R.id.nameEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        ageEditText = findViewById(R.id.ageEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        createProfileButton = findViewById(R.id.createProfileButton);
        selectProfileButton = findViewById(R.id.selectProfileButton);

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });

        selectProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfile();
            }
        });
    }

    private void createProfile() {
        // Retrieve the values entered by the user
        String name = nameEditText.getText().toString();
        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = getSelectedGender();

        // Perform any necessary validation or processing of the input data

        // Save the profile or perform further actions
    }

    private void selectProfile() {
        // Perform the necessary actions when selecting a profile
    }

    private String getSelectedGender() {
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        }
        return null;
    }
}
