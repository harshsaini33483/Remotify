package com.example.harshsaini.remotify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.harshsaini.remotify.MainActivity;
import com.example.harshsaini.remotify.R;

import java.io.DataOutputStream;
import java.io.IOException;


public class Keyboard extends Fragment {

    private Button ctrlButton, altButton, shiftButton, enterButton, tabButton, escButton, backspaceButton;
    private Button deleteButton, clearTextButton,ctrlAltDelButton;
    private Button nButton, tButton, wButton, rButton, fButton, zButton;
    private Button cButton, xButton, vButton, aButton, oButton, sButton;
    private Button qButton, eButton, yButton, uButton, iButton, pButton;
    private Button dButton, gButton, hButton, jButton, kButton, lButton;
    private Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton;
    private Button sevenButton, eightButton, nineButton, zeroButton, addButton, minusButton;
    private Button mulButton, divButton, upButton, downButton, leftButton, rightButton;
    private Button spaceButton, bButton, mButton;

    private Button ctrlAltTButton, ctrlShiftZButton, altF4Button;
    private DataOutputStream dataOutputStream;

    public Keyboard() {
        // Required empty public constructor
        dataOutputStream=MainActivity.getDataOutputStream();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for  fragment
        View v= inflater.inflate(R.layout.fragment_keyboard, container, false);
        initializing(v);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("Request ", "Send Request for KeyBoard");
                    dataOutputStream.writeUTF("KeyBoard");
                    Log.d("Request ", "Done");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return v;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopKeyBoardServices();
        Log.w("KEYBOARD","onDestroyView");
    }



    public void stopKeyBoardServices()
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


    private void initializing(View rootView) {



        ctrlButton = (Button) rootView.findViewById(R.id.ctrlButton);
        altButton = (Button) rootView.findViewById(R.id.altButton);
        shiftButton = (Button) rootView.findViewById(R.id.shiftButton);
        enterButton = (Button) rootView.findViewById(R.id.enterButton);
        tabButton = (Button) rootView.findViewById(R.id.tabButton);
        escButton = (Button) rootView.findViewById(R.id.escButton);
        backspaceButton = (Button) rootView.findViewById(R.id.backspaceButton);
        deleteButton = (Button) rootView.findViewById(R.id.deleteButton);
        clearTextButton = (Button) rootView.findViewById(R.id.clearTextButton);


        qButton = (Button) rootView.findViewById(R.id.qButton);
        wButton = (Button) rootView.findViewById(R.id.wButton);
        eButton = (Button) rootView.findViewById(R.id.eButton);
        rButton = (Button) rootView.findViewById(R.id.rButton);
        tButton = (Button) rootView.findViewById(R.id.tButton);
        yButton = (Button) rootView.findViewById(R.id.yButton);
        uButton = (Button) rootView.findViewById(R.id.uButton);
        iButton = (Button) rootView.findViewById(R.id.iButton);
        oButton = (Button) rootView.findViewById(R.id.oButton);
        pButton = (Button) rootView.findViewById(R.id.pButton);
        aButton = (Button) rootView.findViewById(R.id.aButton);
        sButton = (Button) rootView.findViewById(R.id.sButton);

        dButton = (Button) rootView.findViewById(R.id.dButton);
        fButton = (Button) rootView.findViewById(R.id.fButton);
        gButton = (Button) rootView.findViewById(R.id.gButton);
        hButton = (Button) rootView.findViewById(R.id.hButton);
        jButton = (Button) rootView.findViewById(R.id.jButton);
        kButton = (Button) rootView.findViewById(R.id.kButton);
        lButton = (Button) rootView.findViewById(R.id.lButton);
        zButton = (Button) rootView.findViewById(R.id.zButton);
        xButton = (Button) rootView.findViewById(R.id.xButton);
        cButton = (Button) rootView.findViewById(R.id.cButton);
        vButton = (Button) rootView.findViewById(R.id.vButton);
        bButton = (Button) rootView.findViewById(R.id.bButton);
        nButton = (Button) rootView.findViewById(R.id.nButton);
        mButton = (Button) rootView.findViewById(R.id.mButton);
        spaceButton = (Button) rootView.findViewById(R.id.spaceButton);



        oneButton = (Button) rootView.findViewById(R.id.oneButton);
        twoButton = (Button) rootView.findViewById(R.id.twoButton);
        threeButton = (Button) rootView.findViewById(R.id.threeButton);
        fourButton = (Button) rootView.findViewById(R.id.fourButton);
        fiveButton = (Button) rootView.findViewById(R.id.fiveButton);
        sixButton = (Button) rootView.findViewById(R.id.sixButton);
        sevenButton = (Button) rootView.findViewById(R.id.sevenButton);
        eightButton = (Button) rootView.findViewById(R.id.eightButton);
        nineButton = (Button) rootView.findViewById(R.id.nineButton);
        zeroButton = (Button) rootView.findViewById(R.id.zeroButton);
        upButton = (Button) rootView.findViewById(R.id.upButton);
        downButton = (Button) rootView.findViewById(R.id.downButton);
        leftButton = (Button) rootView.findViewById(R.id.leftButton);
        rightButton = (Button) rootView.findViewById(R.id.rightButton);

        mulButton = (Button) rootView.findViewById(R.id.mulButton);
        addButton = (Button) rootView.findViewById(R.id.addButton);
        minusButton = (Button) rootView.findViewById(R.id.minusButton);
        divButton = (Button) rootView.findViewById(R.id.divButton);


        mulButton = (Button) rootView.findViewById(R.id.mulButton);
        addButton = (Button) rootView.findViewById(R.id.addButton);
        minusButton = (Button) rootView.findViewById(R.id.minusButton);
        divButton = (Button) rootView.findViewById(R.id.divButton);



        ctrlAltTButton = (Button) rootView.findViewById(R.id.ctrlAltTButton);
        ctrlAltDelButton = (Button) rootView.findViewById(R.id.ctrlaltdelButton);

        ctrlShiftZButton = (Button) rootView.findViewById(R.id.ctrlShiftZButton);
        altF4Button = (Button) rootView.findViewById(R.id.altF4Button);



        backspaceButton.setOnClickListener(clickListener);
        enterButton.setOnClickListener(clickListener);
        tabButton.setOnClickListener(clickListener);
        escButton.setOnClickListener(clickListener);
        deleteButton.setOnClickListener(clickListener);
        clearTextButton.setOnClickListener(clickListener);
        qButton.setOnClickListener(clickListener);
        wButton.setOnClickListener(clickListener);
        eButton.setOnClickListener(clickListener);
        rButton.setOnClickListener(clickListener);
        tButton.setOnClickListener(clickListener);
        yButton.setOnClickListener(clickListener);
        uButton.setOnClickListener(clickListener);
        iButton.setOnClickListener(clickListener);
        oButton.setOnClickListener(clickListener);
        pButton.setOnClickListener(clickListener);
        aButton.setOnClickListener(clickListener);
        sButton.setOnClickListener(clickListener);
        dButton.setOnClickListener(clickListener);
        fButton.setOnClickListener(clickListener);
        gButton.setOnClickListener(clickListener);
        hButton.setOnClickListener(clickListener);
        jButton.setOnClickListener(clickListener);
        kButton.setOnClickListener(clickListener);
        lButton.setOnClickListener(clickListener);
        zButton.setOnClickListener(clickListener);
        xButton.setOnClickListener(clickListener);
        cButton.setOnClickListener(clickListener);
        vButton.setOnClickListener(clickListener);
        bButton.setOnClickListener(clickListener);
        nButton.setOnClickListener(clickListener);
        mButton.setOnClickListener(clickListener);

        oneButton.setOnClickListener(clickListener);
        twoButton.setOnClickListener(clickListener);
        threeButton.setOnClickListener(clickListener);
        fourButton.setOnClickListener(clickListener);
        fiveButton.setOnClickListener(clickListener);
        sixButton.setOnClickListener(clickListener);
        sevenButton.setOnClickListener(clickListener);
        eightButton.setOnClickListener(clickListener);
        nineButton.setOnClickListener(clickListener);
        zeroButton.setOnClickListener(clickListener);
        addButton.setOnClickListener(clickListener);
        minusButton.setOnClickListener(clickListener);
        divButton.setOnClickListener(clickListener);
        mulButton.setOnClickListener(clickListener);


        altButton.setOnClickListener(clickListener);
        shiftButton.setOnClickListener(clickListener);

        ctrlButton.setOnClickListener(clickListener);
        ctrlAltTButton.setOnClickListener(clickListener);
        ctrlAltDelButton.setOnClickListener(clickListener);
        ctrlShiftZButton.setOnClickListener(clickListener);
        altF4Button.setOnClickListener(clickListener);


    }


    public View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            
                String message = "";
                switch (id) {

                    case R.id.ctrlShiftZButton:
                        message = "CTRL+SHIFT+Z";
                        break;

                    case R.id.enterButton:
                        message = "VK_ENTER";
                        break;
                    case R.id.tabButton:
                        message = "VK_TAB";
                        break;
                    case R.id.escButton:
                        message = "VK_ESC";
                        break;

                    case R.id.deleteButton:
                        message = "VK_DEL";
                        break;
                    case R.id.qButton:
                        message = "VK_Q";
                        break;
                    case R.id.wButton:
                        message = "VK_W";
                        break;
                    case R.id.eButton:
                        message = "VK_E";
                        break;
                    case R.id.rButton:
                        message = "VK_R";
                        break;
                    case R.id.tButton:
                        message = "VK_T";
                        break;
                    case R.id.yButton:
                        message = "VK_Y";
                        break;
                    case R.id.uButton:
                        message = "VK_U";
                        break;
                    case R.id.iButton:
                        message = "VK_I";
                        break;
                    case R.id.oButton:
                        message = "VK_O";
                        break;
                    case R.id.pButton:
                        message = "VK_P";
                        break;
                    case R.id.aButton:
                        message = "VK_A";
                        break;
                    case R.id.sButton:
                        message = "VK_S";
                        break;
                    case R.id.dButton:
                        message = "VK_D";
                        break;
                    case R.id.fButton:
                        message = "VK_F";
                        break;
                    case R.id.gButton:
                        message = "VK_G";
                        break;
                    case R.id.hButton:
                        message = "VK_H";
                        break;
                    case R.id.jButton:
                        message = "VK_J";
                        break;
                    case R.id.kButton:
                        message = "VK_K";
                        break;
                    case R.id.lButton:
                        message = "VK_L";
                        break;
                    case R.id.zButton:
                        message = "VK_Z";
                        break;
                    case R.id.xButton:
                        message = "VK_X";
                        break;
                    case R.id.cButton:
                        message = "VK_C";
                        break;
                    case R.id.vButton:
                        message = "VK_V";
                        break;
                    case R.id.bButton:
                        message = "VK_B";
                        break;
                    case R.id.nButton:
                        message = "VK_N";
                        break;
                    case R.id.mButton:
                        message = "VK_M";
                        break;
                    case R.id.spaceButton:
                        message = "VK_SPACE";
                        break;


                    case R.id.oneButton:
                        message = "VK_1";
                        break;
                    case R.id.twoButton:
                        message = "VK_2";
                        break;
                    case R.id.threeButton:
                        message = "VK_3";
                        break;
                    case R.id.fourButton:
                        message = "VK_4";
                        break;
                    case R.id.fiveButton:
                        message = "VK_5";
                        break;
                    case R.id.sixButton:
                        message = "VK_6";
                        break;
                    case R.id.sevenButton:
                        message = "VK_7";
                        break;
                    case R.id.eightButton:
                        message = "VK_8";
                        break;
                    case R.id.nineButton:
                        message = "VK_9";
                        break;
                    case R.id.zeroButton:
                        message = "VK_0";
                        break;


                    case R.id.addButton:
                        message = "VK_+";
                        break;
                    case R.id.minusButton:
                        message = "VK_-";
                        break;
                    case R.id.divButton:
                        message = "VK_/";
                        break;
                    case R.id.mulButton:
                        message = "VK_*";
                        break;


                    case R.id.ctrlButton:
                        message = "VK_CTRL";
                        break;

                    case R.id.shiftButton:
                        message = "VK_SHIFT";
                        break;
                    case R.id.altButton:
                        message = "VK_ALT";
                        break;


                    case R.id.altF4Button:
                        message = "ALT+F4";
                        break;

                    case R.id.ctrlaltdelButton:
                        message = "CTRL+ALT+DEL";
                        break;

                    case R.id.ctrlAltTButton:
                        message = "CTRL+ALT+T";
                        break;



                }


            sendMessageToServer(message);


            Log.d("Key Pressed",message+" ");

        }
    } ;


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

}
