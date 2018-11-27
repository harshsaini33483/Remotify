package com.example.harshsaini.remotify.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.harshsaini.remotify.MainActivity;
import com.example.harshsaini.remotify.R;

import java.io.DataOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mouse extends Fragment {

    private Button leftClickButton, rightClickButton;
    private TextView touchPadTextView;
    private int initX, initY, disX, disY;
    boolean mouseMoved = false, moultiTouch = false;
    private DataOutputStream dataOutputStream;

    public Mouse() {
        // Required empty public constructor
        dataOutputStream=MainActivity.getDataOutputStream();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getResources().getString(R.string.controlMouse));

        View rootView = inflater.inflate(R.layout.fragment_mouse, container, false);
        leftClickButton = (Button) rootView.findViewById(R.id.leftClickButton);
        rightClickButton = (Button) rootView.findViewById(R.id.rightClickButton);
        touchPadTextView = (TextView) rootView.findViewById(R.id.touchPadTextView);
        leftClickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simulateLeftClick();
            }
        });
        rightClickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simulateRightClick();
            }
        });

        touchPadTextView.setOnTouchListener(viewTouch);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("Request ", "Send Request for Mouse");
                    dataOutputStream.writeUTF("Mouse");
                    Log.d("Request ", "Done");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return rootView;
    }


    public void sendMessageToServer(final String message)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    dataOutputStream.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }



    private void simulateLeftClick() {
        String message = "LEFT_CLICK";
        sendMessageToServer(message);
    }

    private void simulateRightClick() {
        String message = "RIGHT_CLICK";
        sendMessageToServer(message);
    }



     View.OnTouchListener viewTouch=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    //save X and Y positions when user touches the TextView
                    initX = (int) event.getX();
                    initY = (int) event.getY();
                    mouseMoved = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(moultiTouch == false) {
                        disX = (int) event.getX()- initX; //Mouse movement in x direction
                        disY = (int) event.getY()- initY; //Mouse movement in y direction
                                /*set init to new position so that continuous mouse movement
                                is captured*/
                        initX = (int) event.getX();
                        initY = (int) event.getY();
                        if (disX != 0 || disY != 0) {

                            // send mouse movement to server

                            sendMessageToServer("MOUSE_MOVE,"+disX+","+disY);
                            mouseMoved=true;
                        }
                    }
                    else {
                        disY = (int) event.getY()- initY; //Mouse movement in y direction
                        disY = (int) disY / 2;//to scroll by less amount
                        initY = (int) event.getY();
                        if(disY != 0) {

                            sendMessageToServer("MOUSE_WHEEL,+"+disY);
                            mouseMoved=true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    //consider a tap only if user did not move mouse after ACTION_DOWN
                    if(!mouseMoved){
                        sendMessageToServer("LEFT_CLICK");
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    initY = (int) event.getY();
                    mouseMoved = false;
                    moultiTouch = true;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    if(!mouseMoved) {
                        sendMessageToServer("LEFT_CLICK");
                    }
                    moultiTouch = false;
                    break;
            }
            return true;

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sendMessageToServer("Change");
        Log.w("MOUSE","onDestroyView");
    }
}
