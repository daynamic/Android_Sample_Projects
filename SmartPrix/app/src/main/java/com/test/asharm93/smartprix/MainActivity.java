package com.test.asharm93.smartprix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.test.asharm93.smartprix.Model.CategoriesModel;
import com.test.asharm93.smartprix.Protocol.ApiListStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;

    MaterialDialog pDialog;
    JSONArray DATA;
    ListView mListView;
   private MyAdapter adapter;
    Context context;
    ArrayList<CategoriesModel> modelsArrayList = new ArrayList<CategoriesModel>();
    String Categoriesurl= ApiListStorage.CategoriesApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        initToolbar();
        mListView = (ListView)findViewById(R.id.LV_all_categoris);
        adapter = new MyAdapter(context, R.layout.row_all_categories);
        mListView.setAdapter(adapter);
        getcategorydata();
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

                Intent intent= new Intent(context,SearchProduct.class);
                startActivity(intent);

            }
            return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.tool_bar_for_new_landing);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    public void getcategorydata(){
      preexcecute();
        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, Categoriesurl, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String dataresult =response.get("request_status").toString();
                            if(dataresult.equals("SUCCESS"))
                            {
                                DATA = (JSONArray) response.get("request_result");
                            }
                            else{

                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                        handlerforstopdialog1.sendEmptyMessage(1);

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("in the thirdline with "+error.getMessage());

                    }
                });

        rq.add(jsObjRequest);

    //    postexecute();
    }
public void preexcecute(){
    pDialog = new MaterialDialog.Builder(MainActivity.this)
            .cancelable(false)
            .content("Getting data......")
            .progress(true, 0)
            .show();
}

    Handler handlerforstopdialog1 = new Handler() {
        public void handleMessage(Message msg) {
            pDialog.hide();
          if(DATA!=null){
              for(int i=0;i<DATA.length();i++){
                  try
                  {
                  modelsArrayList.add(new CategoriesModel(String.valueOf(DATA.get(i))));
                  }
                  catch (Exception e){
                      e.printStackTrace();
                      }
              }
          }
            adapter.notifyDataSetChanged();
        }

    };
    class MyAdapter extends ArrayAdapter<CategoriesModel> {
        LayoutInflater inflater;
        private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
        private TextDrawable.IBuilder mDrawableBuilder;
        public MyAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

            return modelsArrayList.size();
        }
        @Override
        public CategoriesModel getItem(int position) {

            return modelsArrayList.get(position);
        }

        @SuppressLint("NewApi") @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            AdapterHolder holder;
            if(convertView==null)
            {
                convertView = inflater.inflate(R.layout.row_all_categories, null);
                holder= new AdapterHolder();
                mDrawableBuilder = TextDrawable.builder()
                        .round();
                holder.image=(ImageView)convertView.findViewById(R.id.imageViewapparel_shops);
                holder.mShopName = (TextView) convertView.findViewById(R.id.shop_name_apparel_shops);
                holder.r1=(RelativeLayout)convertView.findViewById(R.id.shopData_apparel_shops);
                convertView.setTag(holder);
            }
            else{
                holder=(AdapterHolder)convertView.getTag();
            }
            holder.model = modelsArrayList.get(position);
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(modelsArrayList.get(position).getName().charAt(0)), mColorGenerator.getColor(modelsArrayList.get(position).getName()));
            holder.image.setImageDrawable(drawable);
            holder.mShopName.setText(modelsArrayList.get(position).getName().toString());
            holder.r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,ProductListing.class);
                    i.putExtra("name",modelsArrayList.get(position).getName());
                    startActivity(i);
                }
            });


            return convertView;
        }
    }

    private static class AdapterHolder
    {
        CategoriesModel model;
        TextView  mShopName;
        ImageView image;
        RelativeLayout r1;

    }




}
