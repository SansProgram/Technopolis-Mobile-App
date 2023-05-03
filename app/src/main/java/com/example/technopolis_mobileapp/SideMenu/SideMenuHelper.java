package com.example.technopolis_mobileapp.SideMenu;

import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.technopolis_mobileapp.R;
import com.google.android.material.navigation.NavigationView;

public class SideMenuHelper {
    private final AppCompatActivity mActivity;
    private final DrawerLayout mDrawerLayout;
    private final NavigationView mNavigationView;

    public SideMenuHelper(AppCompatActivity activity, DrawerLayout drawerLayout, NavigationView navigationView) {
        mActivity = activity;
        mDrawerLayout = drawerLayout;
        mNavigationView = navigationView;
    }

    public void init() {
        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(mNavigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mActivity,
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Handle navigation view item clicks here.
                        int id = menuItem.getItemId();

                        if (id == R.id.nav_home) {
                            // Handle the home action
                        } else if (id == R.id.nav_profile) {
                            // Handle the profile action
                        } else if (id == R.id.nav_apply) {
                            // Handle the apply action
                        } else if (id == R.id.nav_calendar) {
                            // Handle the calendar action
                        } else if (id == R.id.nav_contact) {
                            // Handle the contact action
                        }

                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }


                }
        );
    }
}
