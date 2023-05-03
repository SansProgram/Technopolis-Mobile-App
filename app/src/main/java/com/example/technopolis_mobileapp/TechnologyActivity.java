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

public class TechnologyActivity extends AppCompatActivity {

    EditText tech_name, tech_email, tech_phone, tech_description;
    CheckBox cb_experience, cb_reference;
    Button tech_btn_submit;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology);
        setTitle("Apply for Technology TA");

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tech_name = (EditText) findViewById(R.id.tech_name);
        tech_email = (EditText) findViewById(R.id.tech_email);
        tech_phone = (EditText) findViewById(R.id.tech_phone);

        tech_description = (EditText) findViewById(R.id.tech_description);

        cb_experience = (CheckBox) findViewById(R.id.cb_experience);
        cb_reference = (CheckBox) findViewById(R.id.cb_reference);

        tech_btn_submit = (Button) findViewById(R.id.tech_btn_submit);

        DB = new DBHelper(this);

        tech_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = DB.getUsername();
                if (username != null) {
                    // insert the data into the ScienceTA table
                    // rest of the code here
                    String name = tech_name.getText().toString();
                    String email = tech_email.getText().toString();
                    String phone = tech_phone.getText().toString();
                    String description = tech_description.getText().toString();
                    boolean hasExperience = cb_experience.isChecked();
                    boolean hasReference = cb_reference.isChecked();

                    // Insert the data into the TechnologyTA table
                    DBHelper DB = new DBHelper(TechnologyActivity.this);
                    Boolean isInserted = DB.insertTechnologyTA(username, name, phone, email, description, hasExperience, hasReference);

                    if (isInserted) {
                        Toast.makeText(TechnologyActivity.this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TechnologyActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TechnologyActivity.this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TechnologyActivity.this, "Username is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}