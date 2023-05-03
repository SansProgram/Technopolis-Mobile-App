package com.example.technopolis_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.technopolis_mobileapp.R.id;

public class ScienceActivity extends AppCompatActivity {

    EditText sci_name, sci_email, sci_phone, sci_description;
    CheckBox cb_experience, cb_reference;
    Button sci_btn_submit;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science);
        setTitle("Apply for Science TA");

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

      Toolbar toolbar = findViewById(id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sci_name = (EditText) findViewById(id.sci_name);
        sci_email = (EditText) findViewById(id.sci_email);
        sci_phone = (EditText) findViewById(id.sci_phone);

        cb_experience = (CheckBox) findViewById(id.cb_experience);
        cb_reference = (CheckBox) findViewById(id.cb_reference);

        sci_description = (EditText) findViewById(id.sci_description);

        sci_btn_submit = (Button) findViewById(id.sci_btn_submit);

        DB = new DBHelper(this);

        //Save Data into ScienceTA
        sci_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = DB.getUsername();
                if (username != null) {
                    // insert the data into the ScienceTA table
                    // rest of the code here
                    String name = sci_name.getText().toString();
                    String email = sci_email.getText().toString();
                    String phone = sci_phone.getText().toString();
                    String description = sci_description.getText().toString();
                    boolean hasExperience = cb_experience.isChecked();
                    boolean hasReference = cb_reference.isChecked();

                    // Insert the data into the ScienceTA table
                    DBHelper DB = new DBHelper(ScienceActivity.this);
                    Boolean isInserted = DB.insertScienceTA(username, name, phone, email, description, hasExperience, hasReference);

                    if (isInserted) {
                        Toast.makeText(ScienceActivity.this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScienceActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ScienceActivity.this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ScienceActivity.this, "Username is null", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}