package com.example.technopolis_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BiologyApplicantActivity extends AppCompatActivity {
    private ListView usernamesListView;

    private List<Applicant> applicantsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biology_applicant);
        setTitle("Applicants for Biology");

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        applicantsList = new ArrayList<>();
        DBHelper dBhelper = new DBHelper(this);
        SQLiteDatabase db = dBhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM biologyTA", null);

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex("name");
            do {
                String name = cursor.getString(nameColumnIndex);
                applicantsList.add(new BiologyApplicantActivity.Applicant(name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        usernamesListView = findViewById(R.id.usernames_listview);
        usernamesListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, applicantsList));
        usernamesListView.setOnItemClickListener((parent, view, position, id) -> {
            // Handle item click
            String name = applicantsList.get(position).toString();
            Intent intent = new Intent(BiologyApplicantActivity.this, BiologyApplicantDetailsActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });
    }
    private static class Applicant {
        private String name;

        Applicant(String name) {
            this.name = name;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
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