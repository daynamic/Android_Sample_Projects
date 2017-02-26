package com.test.asharm93.smartprix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.test.asharm93.smartprix.Model.ProductListingModel;
import com.test.asharm93.smartprix.Protocol.ApiListStorage;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asharm93 on 5/8/2016.
 */
public class ProductListing extends AppCompatActivity  implements AbsListView.OnScrollListener {
Context context;
    ListView mlistview;
    Toolbar toolbar;
    private RelativeLayout footer;
    private boolean isloading = false;
    TextView tv1;
    ActionBar actionBar;
    JSONArray DATA;
    String dataresult;
    HttpResponse response,shopResponse;
    DefaultHttpClient client,shopClient;
    private MyTask task;
    MaterialDialog pDialog;
    String ListingURL= ApiListStorage.ProductsListing;
    String newUrl="",oldUrl="";
    int start=0;
    private final int MAX_ITEMS_PER_PAGE = 10;
    String category_name="";
     private MyAdapter adapter;
    private int TOTAL_ITEMS;
    ArrayList<ProductListingModel> modelsArrayList = new ArrayList<ProductListingModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_listing_layout);
        mlistview = (ListView)findViewById(R.id.LV_all_prduct);
        tv1=(TextView)findViewById(R.id.TvCate_name);
        context=ProductListing.this;
        getintentdata();
        initToolbar();
        footer = (RelativeLayout)findViewById(R.id.footer);
        adapter = new MyAdapter(context, R.layout.row_for_product_listing);
        mlistview.setAdapter(adapter);
        mlistview.setOnScrollListener(this);
        ListingURL=ListingURL+"&category="+category_name;
        newUrl=ListingURL;
        ListingURL=ListingURL+"&start="+start;
        task = new MyTask();
        task.execute();
    }

    class MyTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new MaterialDialog.Builder(ProductListing.this)
                        .cancelable(false)
                        .content("Please wait fecthing data...")
                        .progress(true, 0)
                        .show();
        }

        @Override
        protected Void doInBackground(Void... params) {
                try{
                    isloading = true;
                    client = new DefaultHttpClient();
                    HttpGet get = new HttpGet(ListingURL);
                    try {
                        response = client.execute(get);
                        if(response!=null){
                            String response_str = EntityUtils.toString(response.getEntity());
                            JSONObject jsonObject = new JSONObject(response_str);
                                                        dataresult =jsonObject.get("request_status").toString();
                            if(dataresult.equals("SUCCESS"))
                            {
                               String data=jsonObject.get("request_result").toString();
                               JSONObject a=new JSONObject(data);
                               DATA = (JSONArray) a.get("results");
                               String b=a.get("results_count_total").toString();
                               TOTAL_ITEMS=Integer.parseInt(b);
                               String c=a.get("results_count").toString();
                               if(DATA!=null){
                                   for (int i = 0; i < DATA.length(); i++)
                                   {
                                       try {
                                           JSONObject jsonArr = DATA.getJSONObject(i);
                                           String Id = jsonArr.getString("id");
                                           String Name=jsonArr.getString("name");
                                           String Category= jsonArr.getString("category");
                                           String Price=jsonArr.getString("price");
                                           String Brand=jsonArr.getString("brand");
                                           String image_url=jsonArr.getString("img_url");
                                           image_url=image_url.replaceAll("\\[", "").replaceAll("\\]", "");
                                           image_url=image_url.replace("\\/", "/");
                                           image_url=image_url.replace("\"","");
                                           modelsArrayList.add(new ProductListingModel(Id,Name,Category,Price,Brand,image_url));
                                       }
                                       catch(Exception e){
                                           e.printStackTrace();
                                       }
                                   }
                               }
                           }
                            else{

                            }
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(dataresult.equals("SUCCESS")) {
                try {
                    pDialog.hide();
                    adapter.notifyDataSetChanged();
                    isloading = false;
                    if (adapter.getCount() >= TOTAL_ITEMS) {
                        mlistview.setOnScrollListener(null);
                        mlistview.removeFooterView(footer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(ProductListing.this, "No valid category. Please try with other categories.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        int loadedItems = firstVisibleItem + visibleItemCount;
        if((loadedItems == totalItemCount) && !isloading){
            if(task != null && (task.getStatus() == AsyncTask.Status.FINISHED)){
               ChangeUrl(ListingURL);
            }

        }
    }
    public void ChangeUrl(String oldUrl)
    {

        start=  start+10;
        oldUrl="";
        oldUrl= newUrl+"&start="+start;
        Log.v("old URL now 1" , oldUrl);
        ListingURL= oldUrl;
        task = new MyTask();
        task.execute();
    }
    public void getintentdata(){
        category_name= getIntent().getStringExtra("name");
        if(!category_name.equals("")){
            tv1.setText(category_name);
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

                Toast.makeText(ProductListing.this, "This feature is disabled for this page", Toast.LENGTH_SHORT).show();

            }
            return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyAdapter extends ArrayAdapter<ProductListingModel> {
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
        public ProductListingModel getItem(int position) {

            return modelsArrayList.get(position);
        }

        @SuppressLint("NewApi") @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            AdapterHolder holder;
            if(convertView==null)
            {
                convertView = inflater.inflate(R.layout.row_for_product_listing, null);
                holder= new AdapterHolder();
                holder.image=(ImageView)convertView.findViewById(R.id.imageView2);
                holder.mProductName = (TextView) convertView.findViewById(R.id.textView);
                holder.mProductPrice = (TextView) convertView.findViewById(R.id.textView2);
                holder.r1=(RelativeLayout)convertView.findViewById(R.id.MainLayout);
                convertView.setTag(holder);
            }
            else{
                holder=(AdapterHolder)convertView.getTag();
            }
            holder.model = modelsArrayList.get(position);
            Picasso.with(context).load(modelsArrayList.get(position).getImg_Url()).into(holder.image);
            String c=modelsArrayList.get(position).getName();
            if(c.length()>23){
                c=c.substring(0,21);
                c=c+"..";
            }
            holder.mProductName.setText(c);
            String a=modelsArrayList.get(position).getPrice();
            a="Rs. "+a;
            holder.mProductPrice.setText(a);
            holder.r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProductDetailScreen.class);
                    i.putExtra("ID", modelsArrayList.get(position).getId());
                    i.putExtra("Prod_Name",modelsArrayList.get(position).getName());
                    i.putExtra("Prod_image", modelsArrayList.get(position).getImg_Url());
                    startActivity(i);

                             }
            });
         return convertView;
        }
    }

    private static class AdapterHolder
    {
        ProductListingModel model;
        TextView  mProductName,mProductPrice;
        ImageView image;
        RelativeLayout r1;
    }
}
