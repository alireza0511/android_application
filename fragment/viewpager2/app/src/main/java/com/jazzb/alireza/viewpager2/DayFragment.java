package com.jazzb.alireza.viewpager2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DayFragment extends Fragment {
    private String day;
    private TextView tv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_day,container,false);
        tv = view.findViewById(R.id.tv);
        if (bundle != null) {
            day = bundle.getString("day","today");
        }

        tv.setText(day);

        return view;    }
}
