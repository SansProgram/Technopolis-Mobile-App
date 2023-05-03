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

public class EconomicsActivity extends AppCompatActivity {
    EditText economics_name, economics_email, economics_phone, economics_description;
    CheckBox cb_experience, cb_reference;
    Button economics_btn_submit;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economics);
        setTitle("Apply for Economics TA");

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        economics_name = (EditText) findViewById(R.id.economics_name);
        economics_email = (EditText) findViewById(R.id.economics_email);
        economics_phone = (EditText) findViewById(R.id.economics_phone);

        economics_description = (EditText) findViewById(R.id.economics_description);

        cb_experience = (CheckBox) findViewById(R.id.cb_experience);
        cb_reference = (CheckBox) findViewById(R.id.cb_reference);

        economics_btn_submit = (Button) findViewById(R.id.economics_btn_submit);

        DB = new DBHelper(this);

        economics_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = DB.getUsername();
                if (username != null) {
                    // insert the data into the ScienceTA table
                    // rest of the code here
                    String name = economics_name.getText().toString();
                    String email = economics_email.getText().toString();
                    String phone = economics_phone.getText().toString();
                    String description = economics_description.getText().toString();
                    boolean hasExperience = cb_experience.isChecked();
                    boolean hasReference = cb_reference.isChecked();

                    // Insert the data into the EconomicsTA table
                    DBHelper DB = new DBHelper(EconomicsActivity.this);
                    Boolean isInserted = DB.insertEconomicsTA(username, name, phone, email, description, hasExperience, hasReference);

                    if (isInserted) {
                        Toast.makeText(EconomicsActivity.this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EconomicsActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EconomicsActivity.this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EconomicsActivity.this, "Username is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}