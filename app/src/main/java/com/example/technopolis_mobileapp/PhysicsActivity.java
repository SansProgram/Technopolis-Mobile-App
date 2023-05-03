package com.example.technopolis_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PhysicsActivity extends AppCompatActivity {
    EditText physics_name, physics_email, physics_phone, phys_description;
    CheckBox cb_experience, cb_reference;
    Button physics_btn_submit;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physics);
        setTitle("Apply for Physics TA");

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        physics_name = (EditText) findViewById(R.id.physics_name);
        physics_email = (EditText) findViewById(R.id.physics_email);
        physics_phone = (EditText) findViewById(R.id.physics_phone);

        phys_description = (EditText) findViewById(R.id.phys_description);

        cb_experience = (CheckBox) findViewById(R.id.cb_experience);
        cb_reference = (CheckBox) findViewById(R.id.cb_reference);

        physics_btn_submit = (Button) findViewById(R.id.physics_btn_submit);

        DB = new DBHelper(this);

        physics_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = DB.getUsername();
                if (username != null) {
                    // insert the data into the ScienceTA table
                    // rest of the code here
                    String name = physics_name.getText().toString();
                    String email = physics_email.getText().toString();
                    String phone = physics_phone.getText().toString();
                    String description = phys_description.getText().toString();
                    boolean hasExperience = cb_experience.isChecked();
                    boolean hasReference = cb_reference.isChecked();

                    // Insert the data into the ScienceTA table
                    DBHelper DB = new DBHelper(PhysicsActivity.this);
                    Boolean isInserted = DB.insertPhysicsTA(username, name, phone, email, description, hasExperience, hasReference);

                    if (isInserted) {
                        Toast.makeText(PhysicsActivity.this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PhysicsActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PhysicsActivity.this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PhysicsActivity.this, "Username is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
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