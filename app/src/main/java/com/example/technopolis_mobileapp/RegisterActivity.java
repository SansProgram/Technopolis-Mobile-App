package com.example.technopolis_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.technopolis_mobileapp.ui.home.HomeFragment;
import com.example.technopolis_mobileapp.ui.home.HomeViewModel;

public class RegisterActivity extends AppCompatActivity {

    EditText username, emailUser, password , confirm_password;
    Button register, signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Set app to  Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        emailUser = (EditText) findViewById(R.id.email);
        confirm_password = (EditText) findViewById(R.id.confirm_password);

        register = (Button) findViewById(R.id.btnregister);
        signin = (Button) findViewById(R.id.btnsignin);

        DB = new DBHelper(this);

        //Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String email = emailUser.getText().toString();
                String repass = confirm_password.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("") || email.equals("")){
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser == false){
                            Boolean insert = DB.insertDataABoolean(user, pass, email);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this,"Register completed", Toast.LENGTH_SHORT).show();

                                // Create UserSessionManager instance and set current user
                                UserSessionManager sessionManager = new UserSessionManager(RegisterActivity.this);
                                sessionManager.setCurrentUser(user);

                                Intent intent_home = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent_home);
                            }else{
                                Toast.makeText(RegisterActivity.this,"Register Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //Sign In
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_login);
            }
        });


    }
}