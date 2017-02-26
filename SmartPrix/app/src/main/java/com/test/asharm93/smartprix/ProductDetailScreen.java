package com.test.asharm93.smartprix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.squareup.picasso.Picasso;
import com.test.asharm93.smartprix.Model.CategoriesModel;
import com.test.asharm93.smartprix.Model.NearByShopModel;
import com.test.asharm93.smartprix.Protocol.ApiListStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asharm93 on 5/10/2016.
 */
public class ProductDetailScreen extends AppCompatActivity {
    private Context context;
    Toolbar toolbar;
    ActionBar actionBar;
    String productdetailAPI= ApiListStorage.ProductDetail;
    TextView Prod_name,Prod_price,Prod_nearby;
    ImageView Prod_Image;
    MaterialDialog pDialog;
    ListView mlist;
    JSONObject DATA;
    private MyAdapter adapter;
    ArrayList<NearByShopModel> modelsArrayList = new ArrayList<NearByShopModel>();
    JSONArray prices;
    String prod_id="",prod_name="",prod_image="",prod_price="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_screen);
       context=ProductDetailScreen.this;
        Prod_name=(TextView)findViewById(R.id.TvProd_name);
        Prod_Image=(ImageView)findViewById(R.id.imageView);
        Prod_price=(TextView)findViewById(R.id.TvProd_price);
        Prod_nearby=(TextView)findViewById(R.id.Nearby);
        getintentdata();
        initToolbar();
        productdetailAPI=productdetailAPI+"&id="+prod_id;
        mlist=(ListView)findViewById(R.id.LV_all_nearby);
        adapter = new MyAdapter(context, R.layout.row_for_nearby_store);
        mlist.setAdapter(adapter);
        getdata();

    }

    public void getintentdata(){
        prod_id= getIntent().getStringExtra("ID");
        prod_name =getIntent().getStringExtra("Prod_Name");
        prod_image=getIntent().getStringExtra("Prod_image");
        if(!prod_name.equals("")){
            Prod_name.setText(prod_name);
        }
        if(!prod_image.equals("")){
            Picasso.with(context).load(prod_image).into(Prod_Image);
        }
    }

    public void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.tool_bar_for_new_landing);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void getdata(){
        preexcecute();
        RequestQueue rq = Volley.newRequestQueue(ProductDetailScreen.this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, productdetailAPI, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String dataresult =response.get("request_status").toString();
                            if(dataresult.equals("SUCCESS"))
                            {
                                DATA = (JSONObject) response.get("request_result");
                                prod_price=DATA.getString("price");
                                prices=DATA.getJSONArray("prices");
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


    }

    public void preexcecute(){
        pDialog = new MaterialDialog.Builder(ProductDetailScreen.this)
                .cancelable(false)
                .content("Fetching data......")
                .progress(true, 0)
                .show();
    }

    Handler handlerforstopdialog1 = new Handler() {
        public void handleMessage(Message msg) {
            if(prices!=null){
                for(int i=0;i<prices.length();i++){
                    try
                    {
                        JSONObject bb=prices.getJSONObject(i);

                        String image_url=bb.getString("logo");
                        image_url=image_url.replaceAll("\\[", "").replaceAll("\\]", "");
                        image_url=image_url.replace("\\/", "/");
                        image_url=image_url.replace("\"","");
                        String price=bb.getString("price");
                        modelsArrayList.add(new NearByShopModel(image_url,price));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
            pDialog.hide();
            if(!prod_price.equals("")){
                prod_price="Best Price : Rs. "+prod_price;
                Prod_price.setText(prod_price);
            }
            if(prices!=null) {
                if (prices.length() == 0) {
                    Prod_nearby.setVisibility(View.INVISIBLE);
                    mlist.setVisibility(View.INVISIBLE);
                } else {
                    int a = prices.length();
                    String ss = String.valueOf(a);
                    ss = "Available at " + ss + " Stores";
                    Prod_nearby.setText(ss);
                }
            }
            else{
                Prod_nearby.setVisibility(View.INVISIBLE);
                mlist.setVisibility(View.INVISIBLE);
            }


        }

    };

    class MyAdapter extends ArrayAdapter<NearByShopModel> {
        LayoutInflater inflater;

        public MyAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

            return modelsArrayList.size();
        }
        @Override
        public NearByShopModel getItem(int position) {

            return modelsArrayList.get(position);
        }

        @SuppressLint("NewApi") @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            AdapterHolder holder;
            if(convertView==null)
            {
                convertView = inflater.inflate(R.layout.row_for_nearby_store, null);
                holder= new AdapterHolder();

                holder.image=(ImageView)convertView.findViewById(R.id.imageView2);
                holder.mShopPrice = (TextView) convertView.findViewById(R.id.textView);
                holder.b1 = (Button) convertView.findViewById(R.id.button);
                holder.r1=(RelativeLayout)convertView.findViewById(R.id.MainLayout);
                convertView.setTag(holder);
            }
            else{
                holder=(AdapterHolder)convertView.getTag();
            }
            holder.model = modelsArrayList.get(position);
            Picasso.with(context).load(modelsArrayList.get(position).getStore_logo()).into(holder.image);
            String a=modelsArrayList.get(position).getprice().toString();
            a="Rs. "+a;
            holder.mShopPrice.setText(a);
            holder.r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You have pressed search option", Toast.LENGTH_LONG).show();
                }
            });


            return convertView;
        }
    }

    private static class AdapterHolder
    {
        NearByShopModel model;
        TextView  mShopPrice;
        ImageView image;
        Button b1;
        RelativeLayout r1;

    }


}
