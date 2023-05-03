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

public class SocialActivity extends AppCompatActivity {
    EditText social_name, social_email, social_phone, social_description;
    CheckBox cb_experience, cb_reference;
    Button social_btn_submit;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        setTitle("Apply for Social TA");

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        social_name = (EditText) findViewById(R.id.social_name);
        social_email = (EditText) findViewById(R.id.social_email);
        social_phone = (EditText) findViewById(R.id.social_phone);

        social_description = (EditText) findViewById(R.id.social_description);

        cb_experience = (CheckBox) findViewById(R.id.cb_experience);
        cb_reference = (CheckBox) findViewById(R.id.cb_reference);

        social_btn_submit = (Button) findViewById(R.id.social_btn_submit);

        DB = new DBHelper(this);

        social_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = DB.getUsername();
                if (username != null) {
                    // insert the data into the ScienceTA table
                    // rest of the code here
                    String name = social_name.getText().toString();
                    String email = social_email.getText().toString();
                    String phone = social_phone.getText().toString();
                    String description = social_description.getText().toString();
                    boolean hasExperience = cb_experience.isChecked();
                    boolean hasReference = cb_reference.isChecked();

                    // Insert the data into the ScienceTA table
                    DBHelper DB = new DBHelper(SocialActivity.this);
                    Boolean isInserted = DB.insertSocialTA(username, name, phone, email, description, hasExperience, hasReference);

                    if (isInserted) {
                        Toast.makeText(SocialActivity.this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SocialActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SocialActivity.this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SocialActivity.this, "Username is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}