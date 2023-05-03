package com.example.technopolis_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SocialApplicantDetailsActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView descTextView;
    private Button approveButton;
    private Button disapproveButton;
    private TextView hasExperience;
    private TextView hasReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_applicant_details);
        setTitle("Applicant Details");

        //Set app to Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameTextView = findViewById(R.id.name_textview);
        emailTextView = findViewById(R.id.email_textview);
        phoneTextView = findViewById(R.id.phone_textview);

        descTextView = findViewById(R.id.desc_textview);

        approveButton = findViewById(R.id.approve_button);
        disapproveButton = findViewById(R.id.disapprove_button);

        String name = getIntent().getStringExtra("name");

        DBHelper dBhelper = new DBHelper(this);
        SQLiteDatabase db = dBhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM socialTA WHERE name = ?", new String[]{name});

        if (cursor.moveToFirst()) {
            int emailColumnIndex = cursor.getColumnIndex("email");
            int phoneColumnIndex = cursor.getColumnIndex("phone");
            int descriptionColumnIndex = cursor.getColumnIndex("description");
            String email = cursor.getString(emailColumnIndex);
            String phone = cursor.getString(phoneColumnIndex);
            String description = cursor.getString(descriptionColumnIndex);

            TextView hasExperienceTextView = findViewById(R.id.hasExperience);
            // get the value of the hasExperience column from the cursor
            int hasExperienceColumnIndex = cursor.getColumnIndex("hasExperience");
            int hasExperience = cursor.getInt(hasExperienceColumnIndex);

            // set the text of the hasExperience TextView based on the value of hasExperience
            if (hasExperience == 1) {
                hasExperienceTextView.setText("Yes");
            } else {
                hasExperienceTextView.setText("No");
            }

            TextView hasReferenceTextView = findViewById(R.id.hasReference);
            // get the value of the hasReference column from the cursor
            int hasReferenceColumnIndex = cursor.getColumnIndex("hasReference");
            int hasReference = cursor.getInt(hasReferenceColumnIndex);

            // set the text of the hasReference TextView based on the value of hasReference
            if (hasReference == 1) {
                hasReferenceTextView.setText("Yes");
            } else {
                hasReferenceTextView.setText("No");
            }

            nameTextView.setText(name);
            emailTextView.setText(email);
            phoneTextView.setText(phone);
            descTextView.setText(description);
        }
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dBhelper = new DBHelper(SocialApplicantDetailsActivity.this);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM socialTA WHERE name = ?", new String[]{name});

                if (cursor.moveToFirst()) {
                    int emailColumnIndex = cursor.getColumnIndex("email");
                    String email = cursor.getString(emailColumnIndex);
                    String subject = "Your Social TA application has been Approved ";
                    String message = "Dear " + name + ",\n\nCongratulations your TA application has been Approved" + ".\n\nPlease contact Faculty for enquiries"
                            + ".\n\nBest regards,\nTechnopolis Team";
                    // EmailSender emailSender = new EmailSender();
                    EmailSender emailSender = new EmailSender(email, subject, message);
                    emailSender.execute();

                    db.delete("socialTA", "name=?", new String[]{name});
                    db.close();

                    Toast.makeText(SocialApplicantDetailsActivity.this, "Approved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                cursor.close();
            }
        });

        disapproveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dBhelper = new DBHelper(SocialApplicantDetailsActivity.this);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM socialTA WHERE name = ?", new String[]{name});

                if (cursor.moveToFirst()) {
                    int emailColumnIndex = cursor.getColumnIndex("email");
                    String email = cursor.getString(emailColumnIndex);
                    String subject = "Your Social TA application has been Rejected ";
                    String message = "Dear " + name + ",\n\nUnfortunately your TA application has been Rejected" + ".\n\nPlease contact Faculty for enquiries"
                            + ".\n\nBest regards,\nTechnopolis Team";
                    // EmailSender emailSender = new EmailSender();
                    EmailSender emailSender = new EmailSender(email, subject, message);
                    emailSender.execute();

                    db.delete("socialTA", "name=?", new String[]{name});
                    db.close();

                    Toast.makeText(SocialApplicantDetailsActivity.this, "Rejected", Toast.LENGTH_SHORT).show();
                    finish();
                }
                cursor.close();
            }
        });

        cursor.close();
        db.close();
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