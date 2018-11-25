package com.example.harshsaini.remotify.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.harshsaini.remotify.MainActivity;

import java.io.InputStream;

public class LiveSceenBackgroundTask extends AsyncTask<Void, Void, Void> {


    public volatile static boolean running = true;
    ImageView imageView;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            imageView.setImageBitmap((Bitmap) msg.obj);
            return false;
        }
    });
    private InputStream input;
    private int screenWidth, screenHeight;


    LiveSceenBackgroundTask() {
        input = MainActivity.getInputStream();
        imageView = LiveScreen.getImageView();
        screenHeight = MainActivity.getHeight();
        screenWidth = MainActivity.getWidth();
    }

    public static void changeRunningState() {
        running = false;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("Background Task", "TASK IMAGES SCAN   " + running);

        while (running) {
            try {
                byte[] bytes = new byte[1024 * 1024];
                int count = 0;
                do {
                    count += input.read(bytes, count, bytes.length - count);
                    Log.w("COunt", "Count" + count);

                   //NO IDEA WHY IT DOES GOES TO THIS VALUE AND LIVE SCREEN DOES NOT WORK THEN
                    if (count == 1048576) {
                        count = 0;
                        break;
                    }
                }
                while (!(count > 4 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39));

                Log.d("COUNT", Integer.toString(count));
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);

                Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, count);
                Bitmap rotated = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
                handler.obtainMessage(1, rotated).sendToTarget();

            } catch (Exception e) {
                Log.w("LOG FIRE ON", "PATA NHI KYA PROBLEM HAI");
                e.printStackTrace();
            }

        }
        Log.e("Background Task", "TASK COMPLETE SCAN   " + running);

        return null;

    }

}
