package com.test.asharm93.smartprix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by asharm93 on 5/8/2016.
 */
public class SearchProduct extends Activity {

    EditText searchinput;
    InputMethodManager imm=null;
    String tosend="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_product);
        searchinput=(EditText)findViewById(R.id.home_search_bar);

    }
    public void search(View v)
    {
        if(imm==null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else{
            imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchinput.getWindowToken(), 0);
            imm=null;
        }
    }
    public void searchnow(View v)
    {
        tosend=searchinput.getText().toString();
        if(!tosend.equals("")) {
            Intent i = new Intent(SearchProduct.this, ProductListing.class);
            i.putExtra("name", tosend);
            startActivity(i);
        }
        else{
            Toast.makeText(SearchProduct.this, "Please enter in the search bar", Toast.LENGTH_SHORT).show();
        }
    }
}
