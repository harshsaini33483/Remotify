package com.example.harshsaini.remotify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.harshsaini.remotify.MainActivity;
import com.example.harshsaini.remotify.R;

import java.io.DataOutputStream;
import java.io.IOException;

public class power extends Fragment {

    DataOutputStream dataOutputStream=null;
    ImageButton sleep,restart,shutdown;

    public power() {

        // Required empty public constructor
        dataOutputStream=MainActivity.getDataOutputStream();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_power, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("Request ", "Send Request for Power");
                    dataOutputStream.writeUTF("Power");
                    Log.d("Request ", "Done");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        sleep=(ImageButton)v.findViewById(R.id.sleepButton);
        shutdown=(ImageButton)v.findViewById(R.id.powerButton);
        restart=(ImageButton)v.findViewById(R.id.restartButton);


        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Power","Sending the Sleep message");
                messageSender("Sleep");
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Power","Sending the Restart message");

                messageSender("Restart");
            }
        });


        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Power","Sending the Shutdown message");

                messageSender("Shutdown");
            }
        });
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopPowerServices();
        Log.w("KEYBOARD","onDestroyView");
    }

    public void stopPowerServices()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    dataOutputStream.writeUTF("Change");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void messageSender(final String message)
    {
        Log.w("Power","Received Message"+message);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    dataOutputStream.writeUTF(message);
                    Log.w("Power","Sending the message");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
