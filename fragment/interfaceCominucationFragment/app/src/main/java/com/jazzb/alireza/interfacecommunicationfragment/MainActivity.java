package com.jazzb.alireza.interfacecommunicationfragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements GreenFragment.OnFragmentInteractionListener {

    private static final String BLUE_TAG = "blue";
    private static final String GREEN_TAG = "green";
    private static final String YELLOW_TAG = "yellow";
    BlueFragment mBlueFragment;
    GreenFragment mGreenFragment;
    YellowFragment mYellowFragment;
     FragmentManager fragmentManager = getSupportFragmentManager();
    Button btnFragment , btnyFragment;
    Bundle bundle;

    Integer tempNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle = new Bundle();


        btnFragment = findViewById(R.id.btn_fragment);
        btnyFragment = findViewById(R.id.btn_fragment2);
        // add fragments
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tempNum = 1;


                    mBlueFragment = (BlueFragment) fragmentManager.findFragmentByTag(BLUE_TAG);
                    if (mBlueFragment == null) {
                        mBlueFragment = new BlueFragment();
                        fragmentManager.beginTransaction().replace(R.id.blue_fragment_container, mBlueFragment, BLUE_TAG).commit();
                    }

            }
        });
        btnyFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tempNum = 2;
                mYellowFragment = (YellowFragment) fragmentManager.findFragmentByTag(YELLOW_TAG);
                if (mYellowFragment == null) {
                    mYellowFragment = new YellowFragment();
                    fragmentManager.beginTransaction().replace(R.id.blue_fragment_container, mYellowFragment, YELLOW_TAG).commit();
                }
            }
        });

        mGreenFragment = (GreenFragment) fragmentManager.findFragmentByTag(GREEN_TAG);
        if (mGreenFragment == null) {
            mGreenFragment = new GreenFragment();
            fragmentManager.beginTransaction().add(R.id.green_fragment_container, mGreenFragment, GREEN_TAG).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onFragmentInteraction(String text, Boolean b) {
       if (tempNum == 1) {
           mBlueFragment.youveGotMail(text, b);
       }
       else if (tempNum == 2) {
           mYellowFragment.youveGotMail1(text, b);
       }
    }
}
