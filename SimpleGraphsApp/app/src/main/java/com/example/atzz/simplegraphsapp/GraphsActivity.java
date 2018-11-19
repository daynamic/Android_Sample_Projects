package com.example.atzz.simplegraphsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.atzz.simplegraphsapp.Graph.LineGraph;


public class GraphsActivity extends AppCompatActivity {
    Toolbar toolbar;
    LineGraph line;
    float series[]= new float[]{0f, 1f, 2f, 8f, 5f, 13f, 4f, 3f, 0f};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
       // toolbar = (Toolbar) findViewById(R.id.toolbar);

      //  setSupportActionBar(toolbar);
           line=(LineGraph)findViewById(R.id.lineGraph);
        /*for (float value : series) {
            line.addSeries(value);
        }
        line.setMSeries(series);
        line.addSeries(series[]);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        case R.id.action_settings:

        br


        return true;
    }*/
}
