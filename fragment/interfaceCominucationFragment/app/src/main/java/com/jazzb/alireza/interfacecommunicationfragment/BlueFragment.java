package com.jazzb.alireza.interfacecommunicationfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlueFragment extends Fragment {

    private TextView mTextView, mTextView2;

    public BlueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blue, container, false);
        mTextView = v.findViewById(R.id.textview);
        mTextView2= v.findViewById(R.id.textview2);
        return v;
    }

    // This is a public method that the Activity can use to communicate
    // directly with this Fragment
    public void youveGotMail(String message, Boolean b) {
        if (b) {
            mTextView.setText(message);
        } else {
            mTextView2.setText("false: "+message);

        }
    }

}
