package com.test.asharm93.messagingapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.test.asharm93.messagingapp.R;
import com.test.asharm93.messagingapp.adapters.RcvdMsdRecyclerAdapter;
import com.test.asharm93.messagingapp.models.SMSModel;
import com.test.asharm93.messagingapp.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MessagingMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout With_msg,without_msg;
    List<SMSModel> smsList = new ArrayList<>();
    Uri uri;
    private static MessagingMainActivity inst;
    private RecyclerView recyclerView;
    private RcvdMsdRecyclerAdapter adapter;
    ListView lv;
    Cursor c;
    Context context=MessagingMainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_main);
        initToolbar();
        initInstances();
    }

    protected void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tool_bar_for_new_landing);
        setSupportActionBar(toolbar);
    }

    protected void initInstances(){
        With_msg=(RelativeLayout)findViewById(R.id.msg);
        without_msg=(RelativeLayout)findViewById(R.id.No_msg);
        readinbox();
        if(smsList.size()>0){
            With_msg.setVisibility(View.VISIBLE);
            without_msg.setVisibility(View.GONE);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            adapter = new RcvdMsdRecyclerAdapter(smsList);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
        else{
            With_msg.setVisibility(View.GONE);
            without_msg.setVisibility(View.VISIBLE);
        }
    }

    public void readinbox(){
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            uri = Uri.parse("content://sms/inbox");
            c = getContentResolver().query(uri, null, null, null, null);
            startManagingCursor(c);

            // Read the sms data and store it in the list
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    SMSModel sms = new SMSModel();
                    sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                    sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                    smsList.add(sms);
                    c.moveToNext();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messaging_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_messaging_main, menu);

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_sync:{

                Intent intent= new Intent(context,BackUpContacts.class);
                startActivity(intent);

            }
            return true;

            case R.id.action_overFlow:{
             }
            return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void SendMessage(View v){
         Intent i=new Intent(context,NewMessageActivity.class);
         startActivity(i);
    }
    public static MessagingMainActivity instance() {
        return inst;
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

}
