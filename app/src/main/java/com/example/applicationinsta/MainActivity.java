package com.example.applicationinsta;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private BottomNavigationView bottom_navigation;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment action_home = new fragment_Home();
        final Fragment action_profile = new fragment_Account();
        final Fragment action_box = new Fragment_Plus();

        bottom_navigation = findViewById(R.id.bottom_navigation);

        // handle navigation selection
        bottom_navigation.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragment = action_home;
                                break;
                            case R.id.action_box:
                                fragment = action_box;
                                break;
                            case R.id.action_profile:
                            default:
                                fragment = action_profile;
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.Frame, fragment).commit();
                        return true;
                    }
                });
        // Set default selection
        bottom_navigation.setSelectedItemId(R.id.action_home);
    }
}


