package com.example.harshsaini.remotify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.harshsaini.remotify.MainActivity;
import com.example.harshsaini.remotify.R;

import java.io.DataOutputStream;
import java.io.IOException;


public class LiveScreen extends Fragment {

    static public ImageView imageView;
    DataOutputStream dataOutputStream;
    LiveSceenBackgroundTask liveSceenBackgroundTask = null;

    public LiveScreen() {
        // Required empty public constructor
    }

    public static ImageView getImageView() {
        return imageView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getResources().getString(R.string.liveScreen));

        View view = inflater.inflate(R.layout.fragment_live_screen, container, false);
        imageView = view.findViewById(R.id.imageView);
        dataOutputStream = MainActivity.getDataOutputStream();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("Request ", "Send Request for Live ScreenShot");
                    dataOutputStream.writeUTF("LiveScreenShot");
                    Log.d("Request ", "Done");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        LiveSceenBackgroundTask.running = true;
        Log.d("Request ", "Start Task in Background");
        if (liveSceenBackgroundTask != null) {
            liveSceenBackgroundTask = null;
        } else {
            liveSceenBackgroundTask = new LiveSceenBackgroundTask();
        }
        liveSceenBackgroundTask.execute(null, null, null);
        return view;
    }



}

