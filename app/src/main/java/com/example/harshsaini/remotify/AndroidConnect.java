package com.example.harshsaini.remotify;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class AndroidConnect extends AppCompatActivity {
    public TextInputEditText ipaddress;
    public TextInputEditText portNo;
    public  Button connectButton;
    public static Socket socket;
    final static int MESSAGE_CONNECT=1;
    final static int MESSAGE_NOTCONNCECT=2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        ipaddress=(TextInputEditText)findViewById(R.id.ipaddress);
        portNo=(TextInputEditText)findViewById(R.id.portNo);
        connectButton=(Button)findViewById(R.id.connectButton);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip=ipaddress.getText().toString();
                String port=portNo.getText().toString();
                ipaddress.setEnabled(false);
                portNo.setEnabled(false);
                new ClientThread(ip,Integer.parseInt(port)).start();
            }
        });

    }



    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.w("Handler","HANDLER CLASS"+ msg.what+" "+msg.obj);
            switch (msg.what)
            {
                case MESSAGE_CONNECT:

                    connectButton.setText("Connected");
                    connectButton.setEnabled(false);
                    socket=(Socket)msg.obj;
                    new Receive().start();
                    break;
                case MESSAGE_NOTCONNCECT:
                    connectButton.setText("Reconnect");
                    ipaddress.setEnabled(true);
                    portNo.setEnabled(true);

            }
            return false;
        }
    });

    class ClientThread extends Thread{
        Socket socket;
        String ipaddress;
        int port;
        public ClientThread() {
            super();
        }

        public ClientThread(String ipaddress,int port)
        {
            this.ipaddress=ipaddress;
            this.port=port;
        }


        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(ipaddress,port);
                Log.e("MAIN","Connection Successfull");
                handler.obtainMessage(MESSAGE_CONNECT,socket).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                Log.w("Client Thread","Socket is Having Problem");
            }

        }
    }

    class Receive extends Thread{
        InputStream inputStream;
        ObjectInputStream objectInputStream;
        public Receive() {
            super();

        }

        @Override
        public void run() {
            super.run();
            try {
                objectInputStream=new ObjectInputStream(socket.getInputStream());
                while(true)
                {

                    String str=(String)objectInputStream.readObject();
                    Log.w("SEND Thread",str);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }



        }
    }
}
