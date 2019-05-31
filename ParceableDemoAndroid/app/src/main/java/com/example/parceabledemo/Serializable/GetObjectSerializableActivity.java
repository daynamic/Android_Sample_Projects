package com.example.parceabledemo.Serializable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.parceabledemo.MainActivity;
import com.example.parceabledemo.R;

public class GetObjectSerializableActivity extends AppCompatActivity {
    private TextView txt_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_object_serializable);
        txt_Data=findViewById(R.id.txt_Data);
        Person mPerson = (Person)getIntent().getSerializableExtra(MainActivity.SER_KEY);
        String a ="Your name is: " + mPerson.getName() + "\n"+
                "Your age is: " + mPerson.getAge();
        txt_Data.setText(a);
    }
}
