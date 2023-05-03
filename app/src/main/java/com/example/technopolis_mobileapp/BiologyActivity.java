package com.example.technopolis_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class BiologyActivity extends AppCompatActivity {
    EditText biology_name, biology_email, biology_phone, biology_description;
    CheckBox cb_experience, cb_reference;
    Button biology_btn_submit;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biology);
        setTitle("Apply for Biology TA");

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        biology_name = (EditText) findViewById(R.id.biology_name);
        biology_email = (EditText) findViewById(R.id.biology_email);
        biology_phone = (EditText) findViewById(R.id.biology_phone);

        biology_description = (EditText) findViewById(R.id.biology_description);

        cb_experience = (CheckBox) findViewById(R.id.cb_experience);
        cb_reference = (CheckBox) findViewById(R.id.cb_reference);

        biology_btn_submit = (Button) findViewById(R.id.biology_btn_submit);

        DB = new DBHelper(this);

        biology_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = DB.getUsername();
                if (username != null) {
                    // insert the data into the ScienceTA table
                    // rest of the code here
                    String name = biology_name.getText().toString();
                    String email = biology_email.getText().toString();
                    String phone = biology_phone.getText().toString();
                    String description = biology_description.getText().toString();
                    boolean hasExperience = cb_experience.isChecked();
                    boolean hasReference = cb_reference.isChecked();

                    // Insert the data into the BiologyTA table
                    DBHelper DB = new DBHelper(BiologyActivity.this);
                    Boolean isInserted = DB.insertBiologyTA(username, name, phone, email, description, hasExperience, hasReference);

                    if (isInserted) {
                        Toast.makeText(BiologyActivity.this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BiologyActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BiologyActivity.this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BiologyActivity.this, "Username is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}