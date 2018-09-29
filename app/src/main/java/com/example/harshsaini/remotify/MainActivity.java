package com.example.harshsaini.remotify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.harshsaini.remotify.fragments.Keyboard;
import com.example.harshsaini.remotify.fragments.LiveScreen;
import com.example.harshsaini.remotify.fragments.Mouse;
import com.example.harshsaini.remotify.fragments.about;
import com.example.harshsaini.remotify.fragments.cmd;
import com.example.harshsaini.remotify.fragments.cortana;
import com.example.harshsaini.remotify.fragments.livescreenkey;
import com.example.harshsaini.remotify.fragments.musicFragment;
import com.example.harshsaini.remotify.fragments.power;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationbar_open,
                R.string.navigationbar_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LiveScreen()).commit();
        navigationView.setCheckedItem(R.id.liveScreenNav);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.liveScreenNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LiveScreen()).commit();
                break;
            case R.id.cmdNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new cmd()).commit();
                break;
            case R.id.keyBoardNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Keyboard()).commit();
                break;
            case R.id.controlMouseNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Mouse()).commit();
                break;
            case R.id.musicNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new musicFragment()).commit();
                break;
            case R.id.cortanaNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new cortana()).commit();
                break;
            case R.id.aboutNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new about()).commit();
                break;
            case R.id.liveScreenMouseNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new livescreenkey()).commit();
            case R.id.powerControlNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new power()).commit();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}

