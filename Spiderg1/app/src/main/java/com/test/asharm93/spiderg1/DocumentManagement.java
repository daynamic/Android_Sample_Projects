package com.test.asharm93.spiderg1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by asharm93 on 5/4/2016.
 */
public class DocumentManagement extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    CharSequence Titles[]={"Received","Sent","Pending"};
    int Numboftabs =3;
    TabLayout tabLayout;
    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documentmanagement);
        initToolbar();
        initInstances();
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tool_bar_for_new_landing);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initInstances(){
        pager = (ViewPager) findViewById(R.id.pager_for_documentmangement);
        pager.setAdapter(new DocumentPagersAdapter(getSupportFragmentManager(), Titles, Numboftabs));
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs_for_new_landing);
        tabLayout.setupWithViewPager(pager);
        pager.setOffscreenPageLimit(3);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     switch (item.getItemId()) {

            case R.id.search:{
                Toast.makeText(this, "You have pressed search option", Toast.LENGTH_LONG).show();

            }
            return true;
            case R.id.bell:
            {
                Toast.makeText(this, "You have pressed notify option", Toast.LENGTH_LONG).show();
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
