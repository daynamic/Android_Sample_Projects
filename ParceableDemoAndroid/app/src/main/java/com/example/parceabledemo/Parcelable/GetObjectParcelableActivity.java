package com.example.parceabledemo.Parcelable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.parceabledemo.MainActivity;
import com.example.parceabledemo.R;

public class GetObjectParcelableActivity extends AppCompatActivity {

    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_object_parcelable);
        txt=(TextView)findViewById(R.id.txt_Data) ;

        Book mBook = (Book)getIntent().getParcelableExtra(MainActivity.PAR_KEY);
        String a ="Book name is: " + mBook.getBookName()+"\n"+
                "Author is: " + mBook.getAuthor() + "\n" +
                "Publish Time is: " + mBook.getPublishTime();
        txt.setText(a);
    }
}
