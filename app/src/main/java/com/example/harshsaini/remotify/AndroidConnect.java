package com.example.harshsaini.remotify;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class AndroidConnect extends AppCompatActivity {
    public TextInputEditText ipaddress;
    public TextInputEditText portNo;
    public Button connectButton;
    public static Socket socket;
    public ProgressBar progressBar;
    final static int MESSAGE_CONNECT = 1;
    final static int MESSAGE_NOTCONNECT = 2;
    final static int MESSAGE_TIMEOUT = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        ipaddress = (TextInputEditText) findViewById(R.id.ipaddress);
        portNo = (TextInputEditText) findViewById(R.id.portNo);
        connectButton = (Button) findViewById(R.id.connectButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        connectButton.setOnClickListener(new connectingButton());
        if(!isNetworkAvailable())
        {
            dialogBox(R.string.noNetwork);
        }
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.w("Handler", "HANDLER CLASS" + msg.what + " " + msg.obj);
            switch (msg.what) {
                case MESSAGE_CONNECT:
                    connectButton.setText("Connected");
                    connectButton.setEnabled(false);
                    socket = (Socket) msg.obj;
                    new CountDownTimer(1000, 500) {
                        public void onFinish() {
                            progressBar.setVisibility(View.INVISIBLE);
                            new Receive().start();
                        }

                        public void onTick(long millisUntilFinished) {

                        }
                    }.start();
                    break;
                case MESSAGE_NOTCONNECT:
                    connectButton.setText(R.string.reconnect);
                    connectButton.setEnabled(true);
                    ipaddress.setEnabled(true);
                    portNo.setEnabled(true);
                    progressBar.setVisibility(View.INVISIBLE);
                    dialogBox(R.string.probleminsocket);
                case MESSAGE_TIMEOUT:
                    connectButton.setText(R.string.reconnect);
                    connectButton.setEnabled(true);
                    ipaddress.setEnabled(true);
                    portNo.setEnabled(true);
                    progressBar.setVisibility(View.INVISIBLE);
                    dialogBox(R.string.timeOut);
            }
            return false;
        }
    });


    public void dialogBox(int msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                        }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    class ClientThread extends Thread {
        Socket socket;
        String ipaddress;
        int port;
        private ClientThread(String ipaddress, int port) {
            this.ipaddress = ipaddress;
            this.port = port;
        }


        @Override
        public void run() {
            super.run();

            try {
                Log.e("MAIN", "Connection ");

                socket = new Socket();
                socket.connect(new InetSocketAddress(ipaddress, port),7000);
                Log.e("MAIN", "Connection Successfull");
                handler.obtainMessage(MESSAGE_CONNECT, socket).sendToTarget();
            }
            catch (SocketTimeoutException e) {
                e.printStackTrace();
                Log.w("Client Thread", "Connection TimeOut");
                handler.obtainMessage(MESSAGE_TIMEOUT).sendToTarget();
            }
                catch (IOException e) {
                e.printStackTrace();
                handler.obtainMessage(MESSAGE_NOTCONNECT).sendToTarget();
                Log.w("Client Thread", "Socket is Having Problem");
            }


        }
    }
    class Receive extends Thread {
        ObjectInputStream objectInputStream;
        @Override
        public void run() {
            super.run();
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                    String str = (String) objectInputStream.readObject();
                    Log.w("SEND Thread", str);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    class connectingButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String ip = ipaddress.getText().toString();
            String port = portNo.getText().toString();
            Log.w("MAINTHREAD",ip+"ip"+port+"port");

            if((ip==null || ip.equals(" ") || ip.equals("")) )
            {
                Log.w("MAINTHREAD",ip+"ip"+port+"port");
                dialogBox(R.string.emptyField);

            }
            else if( (port==null || port.equals("") || port.equals(" ")))
            {
                Log.w("MAINTHREAD",ip+"ip"+port+"port");
                dialogBox(R.string.emptyField);

            }
            else if(!isNetworkAvailable())
            {
                dialogBox(R.string.noConnection);
            }
            else
                {
                    ipaddress.setEnabled(false);
                    portNo.setEnabled(false);
                    connectButton.setText(R.string.connecting);
                    connectButton.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    new ClientThread(ip, Integer.parseInt(port)).start();

                }
        }
    }

}