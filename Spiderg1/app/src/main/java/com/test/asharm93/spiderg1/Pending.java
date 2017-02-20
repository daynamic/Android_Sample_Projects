package com.test.asharm93.spiderg1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by asharm93 on 5/4/2016.
 */
public class Pending extends Fragment {
View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.pendinglayout,container,false);

        return v;
    }
}
