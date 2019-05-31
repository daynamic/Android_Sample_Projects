package com.example.parceabledemo;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ParcelableSpan;
import android.view.View;
import android.widget.Button;

import com.example.parceabledemo.Parcelable.Book;
import com.example.parceabledemo.Parcelable.GetObjectParcelableActivity;
import com.example.parceabledemo.Serializable.GetObjectSerializableActivity;
import com.example.parceabledemo.Serializable.Person;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public  final static String SER_KEY = "com.example.parceabledemo.ser";
    public  final static String PAR_KEY = "com.example.parceabledemo.par";
    private Context context = MainActivity.this;
    List<Items> to_send;
    private Button b1, btn_Parcel,btn_Serial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button1);
        btn_Parcel=(Button)findViewById(R.id.button2);
        btn_Serial=(Button)findViewById(R.id.button3);
        gendataforlibrary();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainActivity2.class);
                Bundle b = new Bundle();
                b.putParcelable("List_Parcel", Parcels.wrap(to_send));
                i.putExtra("List_Extra", b);
                startActivity(i);
            }
        });
        btn_Parcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book mBook = new Book();
                mBook.setBookName("Android");
                mBook.setAuthor("Akshat");
                mBook.setPublishTime(2019);
                Intent mIntent = new Intent(context, GetObjectParcelableActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable(PAR_KEY, mBook);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

        btn_Serial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person mPerson = new Person();
                mPerson.setName("Akshat");
                mPerson.setAge(30);
                Intent mIntent = new Intent(context, GetObjectSerializableActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(SER_KEY,mPerson);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }

    private void gendataforlibrary(){
        Items one = new Items("Item 1", 2012);
        Items two = new Items("Item 2", 2014);
        Items three = new Items("Item 3", 2015);
        Items four = new Items("Item 4", 2018);
        Items five = new Items("Item 5", 2019);

        to_send = new ArrayList<>(Arrays.asList(one, two, three, four, five));
    }
}
