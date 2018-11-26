package com.example.harshsaini.remotify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.harshsaini.remotify.fragments.Keyboard;
import com.example.harshsaini.remotify.fragments.LiveSceenBackgroundTask;
import com.example.harshsaini.remotify.fragments.LiveScreen;
import com.example.harshsaini.remotify.fragments.Mouse;
import com.example.harshsaini.remotify.fragments.about;
import com.example.harshsaini.remotify.fragments.cmd;
import com.example.harshsaini.remotify.fragments.cortana;
import com.example.harshsaini.remotify.fragments.livescreenkey;
import com.example.harshsaini.remotify.fragments.music;
import com.example.harshsaini.remotify.fragments.power;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    static Socket socket;
    static InputStream inputStream;
    static OutputStream outputStream;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    static int height;
    static int width;
    DisplayMetrics displayMetrics;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheme(android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        socketConnect();

        //custom toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        //navigation bar
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);


        //option menu custom toolbar
        setSupportActionBar(toolbar);

        //navigation bar toogle button
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationbar_open,
                R.string.navigationbar_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        //animation for navigation bar
        actionBarDrawerToggle.syncState();

        //default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LiveScreen()).commit();
        navigationView.setCheckedItem(R.id.liveScreenNav);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (LiveSceenBackgroundTask.running) {
            LiveSceenBackgroundTask.changeRunningState();
        }

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new music()).commit();
                break;
            case R.id.cortanaNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new cortana()).commit();
                break;
            case R.id.aboutNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new about()).commit();
                break;
            case R.id.liveScreenMouseNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new livescreenkey()).commit();
                break;
            case R.id.powerControlNav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new power()).commit();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LiveScreen()).commit();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Toast.makeText(MainActivity.this, "Option is not Available", Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.disconnect:
                Toast.makeText(MainActivity.this, "Disconnect is not Available", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void socketConnect() {

        socket = AndroidConnect.socket;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataInputStream = new DataInputStream(inputStream);
        } catch (IOException e) {
            new AndroidConnect().dialogBox(R.string.streamDisconnected);
        }

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    public static OutputStream getOutputStream() {
        return outputStream;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public static DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public static InputStream getInputStream() {
        return inputStream;
    }


}

