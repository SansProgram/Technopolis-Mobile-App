package com.example.technopolis_mobileapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technopolis_mobileapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private UserSessionManager sessionManager;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Set app to  Fullscreen
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



//        binding.appBarMain.toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_apply, R.id.nav_calendar, R.id.nav_contact,R.id.action_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set click listener for logout button
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(item -> {
            // Logout user and send to RegisterActivity
            logout();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            return true;
        });
        updateMenuItems();
    }
    private void updateMenuItems() {
        NavigationView navigationView = binding.navView;
        MenuItem navApplyItem = navigationView.getMenu().findItem(R.id.nav_apply);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("onCreateOptionsMenu", "True");
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        DBHelper dbHelper = new DBHelper(this);
        String currentUser = dbHelper.getUsername();

        Log.d("CurrentUser", currentUser);

        setNavigationGraph(currentUser);

        return true;
    }

    private void setNavigationGraph(String currentUser) {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();

        NavigationView navView = findViewById(R.id.nav_view);
        Menu navMenu = navView.getMenu();

        if (currentUser != null && currentUser.equals("Staff")) {
            navMenu.setGroupVisible(R.id.staff_menu_group, true);
        } else {
            navMenu.setGroupVisible(R.id.staff_menu_group, false);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void logout() {
        UserSessionManager sessionManager = new UserSessionManager(MainActivity.this);
        sessionManager.clearSession();
        updateMenuItems();
    }
}