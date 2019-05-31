package com.example.parceabledemo;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.parceler.Parcels;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
        Bundle b = i.getBundleExtra("List_Extra");

        List<Items> movies = Parcels.unwrap(b.getParcelable("List_Parcel"));
        Log.d("TAG", "Movies: " + movies);
    }
}
