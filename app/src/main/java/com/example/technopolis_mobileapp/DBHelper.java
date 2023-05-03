package com.example.technopolis_mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Technopolis.db";
    private Context context;

    public DBHelper( Context context) {
        super(context, "Technopolis.db", null, 2);
        this.context = context.getApplicationContext();
    }

    //Create Table for Db
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //User Table
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, email TEXT)");

        //Science Applicant Table
        MyDB.execSQL("create Table scienceTA(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, name TEXT, phone TEXT," +
                " email TEXT, description TEXT, hasExperience INTEGER, hasReference INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username))");

        //Physics Applicant Table
        MyDB.execSQL("create Table physicsTA(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, name TEXT, phone TEXT," +
                " email TEXT, description TEXT,hasExperience INTEGER, hasReference INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username))");

        //Social Applicant Table
        MyDB.execSQL("create Table socialTA(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, name TEXT, phone TEXT," +
                " email TEXT, description TEXT,hasExperience INTEGER, hasReference INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username))");

        //Economics Applicant Table
        MyDB.execSQL("create Table economicsTA(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, name TEXT, phone TEXT," +
                " email TEXT, description TEXT,hasExperience INTEGER, hasReference INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username))");

        //Biology Applicant Table
        MyDB.execSQL("create Table biologyTA(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, name TEXT, phone TEXT," +
                " email TEXT, description TEXT,hasExperience INTEGER, hasReference INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username))");

        //Technology Applicant Table
        MyDB.execSQL("create Table technologyTA(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, name TEXT, phone TEXT," +
                " email TEXT, description TEXT, hasExperience INTEGER, hasReference INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username))");


        // Insert hard-coded user record
        ContentValues values = new ContentValues();
        values.put("username", "Staff");
        values.put("email", "staff@dut4life.ac.za");
        values.put("password", "staff@1234");
        MyDB.insert("users", null, values);

        Log.d("DBHelper", "Created tables: users, scienceTA, physicsTA");

    }


    //Update Table in db
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists scienceTA");
        MyDB.execSQL("drop Table if exists physicsTA");
        MyDB.execSQL("drop Table if exists socialTA");
        MyDB.execSQL("drop Table if exists economicsTA");
        MyDB.execSQL("drop Table if exists biologyTA");
        MyDB.execSQL("drop Table if exists technologyTA");
        onCreate(MyDB);

        MyDB.execSQL("PRAGMA foreign_keys=ON;");
    }

    //User data !!!
    //Add Data to Db
    public Boolean insertDataABoolean(String username, String password, String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);

        long results = MyDB.insert("users", null, contentValues);

        if (results == -1) {
            Log.d("Insertion failed", "Failed to insert record into users table");
            return false;
        }
        else {
            Log.d("Insertion succeeded", "Record inserted into users table with id: " + results);
            return true;
        }

    }
    //Check User name against data in Db
    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?",new String[]{username});

        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username,password});

        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    //GetUserName
    public String getUsername() {
        UserSessionManager sessionManager = new UserSessionManager(context);
        String currentUser = sessionManager.getCurrentUser();
        if (currentUser == null) {
            Log.e("CurrentUserStatus", "getCurrentUser() returned null");
            return null;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{currentUser});
        String username = null;
        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex("username");
            if (usernameIndex >= 0) {
                username = cursor.getString(usernameIndex);
            }
        }
        Log.d("CurrentUserStatus", "getCurrentUser() returned " + currentUser);
        cursor.close();
        return username;
    }
    //End User Data !!!

    //Science Applicants
    public boolean insertScienceTA(String username, String name, String phone, String email, String description, boolean hasExperience, boolean hasReference) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        // Check if the username value is null before inserting it
        if (username == null) {
            return false;
        }

        ContentValues contentValues = new ContentValues();

        // Check if the given username exists in the users table
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        if (cursor.getCount() <= 0) {
            // Username doesn't exist in users table
            cursor.close();
            return false;
        }

        // Username exists in users table, proceed with inserting into scienceTA
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("description", description);
        contentValues.put("hasExperience", hasExperience ? 1:0);
        contentValues.put("hasReference", hasReference ? 1:0);

        long result = MyDB.insert("scienceTA", null, contentValues);

        // close cursor and database to avoid memory leaks
        cursor.close();
        MyDB.close();

        return result != -1;
    }

    public Cursor getScienceTAData(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = null;
        try {
            res = myDB.rawQuery("SELECT * FROM scienceTA WHERE username=?", new String[]{username});
        } catch (Exception e) {
            Log.e("DBHelper", "Error getting scienceTA data: " + e.getMessage());
        }
        return res;
    }

    public boolean updateScienceTAStatus(String username, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        int rowsAffected = db.update("scienceTA", contentValues, "username=?", new String[]{username});
        db.close();
        return rowsAffected > 0;
    }
    //Science Applicants end!!!

    //Physics Applicants
    public boolean insertPhysicsTA(String username, String name, String phone, String email, String description, boolean hasExperience, boolean hasReference) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        // Check if the username value is null before inserting it
        if (username == null) {
            return false;
        }

        ContentValues contentValues = new ContentValues();

        // Check if the given username exists in the users table
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        if (cursor.getCount() <= 0) {
            // Username doesn't exist in users table
            cursor.close();
            return false;
        }

        // Username exists in users table, proceed with inserting into scienceTA
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("description", description);
        contentValues.put("hasExperience", hasExperience ? 1:0);
        contentValues.put("hasReference", hasReference ? 1:0);

        long result = MyDB.insert("physicsTA", null, contentValues);

        // close cursor and database to avoid memory leaks
        cursor.close();
        MyDB.close();

        return result != -1;
    }

    public Cursor getPhysicsTAData(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = null;
        try {
            res = myDB.rawQuery("SELECT * FROM physicsTA WHERE username=?", new String[]{username});
        } catch (Exception e) {
            Log.e("DBHelper", "Error getting physicsTA data: " + e.getMessage());
        }
        return res;
    }
    //Physics Applicants end!!!

    //Social Applicants
    public boolean insertSocialTA(String username, String name, String phone, String email, String description, boolean hasExperience, boolean hasReference) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        // Check if the username value is null before inserting it
        if (username == null) {
            return false;
        }

        ContentValues contentValues = new ContentValues();

        // Check if the given username exists in the users table
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        if (cursor.getCount() <= 0) {
            // Username doesn't exist in users table
            cursor.close();
            return false;
        }

        // Username exists in users table, proceed with inserting into scienceTA
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("description", description);
        contentValues.put("hasExperience", hasExperience ? 1:0);
        contentValues.put("hasReference", hasReference ? 1:0);

        long result = MyDB.insert("socialTA", null, contentValues);

        // close cursor and database to avoid memory leaks
        cursor.close();
        MyDB.close();

        return result != -1;
    }

    public Cursor getSocialTAData(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = null;
        try {
            res = myDB.rawQuery("SELECT * FROM socialTA WHERE username=?", new String[]{username});
        } catch (Exception e) {
            Log.e("DBHelper", "Error getting socialTA data: " + e.getMessage());
        }
        return res;
    }
    //Social Applicants end!!!

    //Economics Applicants
    public boolean insertEconomicsTA(String username, String name, String phone, String email, String description, boolean hasExperience, boolean hasReference) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        // Check if the username value is null before inserting it
        if (username == null) {
            return false;
        }

        ContentValues contentValues = new ContentValues();

        // Check if the given username exists in the users table
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        if (cursor.getCount() <= 0) {
            // Username doesn't exist in users table
            cursor.close();
            return false;
        }

        // Username exists in users table, proceed with inserting into scienceTA
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("description", description);
        contentValues.put("hasExperience", hasExperience ? 1:0);
        contentValues.put("hasReference", hasReference ? 1:0);

        long result = MyDB.insert("economicsTA", null, contentValues);

        // close cursor and database to avoid memory leaks
        cursor.close();
        MyDB.close();

        return result != -1;
    }

    public Cursor getEconomicsTAData(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = null;
        try {
            res = myDB.rawQuery("SELECT * FROM socialTA WHERE username=?", new String[]{username});
        } catch (Exception e) {
            Log.e("DBHelper", "Error getting economicsTA data: " + e.getMessage());
        }
        return res;
    }
    //Economics Applicants end!!!

    //Biology Applicants
    public boolean insertBiologyTA(String username, String name, String phone, String email, String description, boolean hasExperience, boolean hasReference) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        // Check if the username value is null before inserting it
        if (username == null) {
            return false;
        }

        ContentValues contentValues = new ContentValues();

        // Check if the given username exists in the users table
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        if (cursor.getCount() <= 0) {
            // Username doesn't exist in users table
            cursor.close();
            return false;
        }

        // Username exists in users table, proceed with inserting into scienceTA
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("description", description);
        contentValues.put("hasExperience", hasExperience ? 1:0);
        contentValues.put("hasReference", hasReference ? 1:0);

        long result = MyDB.insert("biologyTA", null, contentValues);

        // close cursor and database to avoid memory leaks
        cursor.close();
        MyDB.close();

        return result != -1;
    }

    public Cursor getBiologyTAData(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = null;
        try {
            res = myDB.rawQuery("SELECT * FROM socialTA WHERE username=?", new String[]{username});
        } catch (Exception e) {
            Log.e("DBHelper", "Error getting biologyTA data: " + e.getMessage());
        }
        return res;
    }
    //Biology Applicants end!!!

    //Technology Applicants
    public boolean insertTechnologyTA(String username, String name, String phone, String email, String description, boolean hasExperience, boolean hasReference) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        // Check if the username value is null before inserting it
        if (username == null) {
            return false;
        }

        ContentValues contentValues = new ContentValues();

        // Check if the given username exists in the users table
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        if (cursor.getCount() <= 0) {
            // Username doesn't exist in users table
            cursor.close();
            return false;
        }

        // Username exists in users table, proceed with inserting into scienceTA
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("description", description);
        contentValues.put("hasExperience", hasExperience ? 1:0);
        contentValues.put("hasReference", hasReference ? 1:0);

        long result = MyDB.insert("technologyTA", null, contentValues);

        // close cursor and database to avoid memory leaks
        cursor.close();
        MyDB.close();

        return result != -1;
    }

    public Cursor getTechnologyTAData(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor res = null;
        try {
            res = myDB.rawQuery("SELECT * FROM socialTA WHERE username=?", new String[]{username});
        } catch (Exception e) {
            Log.e("DBHelper", "Error getting technologyTA data: " + e.getMessage());
        }
        return res;
    }
    //Technology Applicants end!!!

}
