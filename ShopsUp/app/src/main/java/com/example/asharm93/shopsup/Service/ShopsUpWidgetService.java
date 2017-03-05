package com.example.asharm93.shopsup.Service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.asharm93.shopsup.Model.ModelsShop;
import com.example.asharm93.shopsup.R;
import com.example.asharm93.shopsup.ShopsUpWidget;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asharm93 on 3/5/17.
 */

public class ShopsUpWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private String geturl="https://www.hackerearth.com/api/events/upcoming/?format=gson&status=ONGOING&college=false&unicoded=true";
    private JSONArray DATA;
    ArrayList<ModelsShop> ShopsArrayList = new ArrayList<ModelsShop>();
    private static int mCount=30;
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        RequestQueue rq = Volley.newRequestQueue(this.mContext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, geturl, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            DATA = (JSONArray) response.get("response");
                            if(DATA.length()>mCount){
                                mCount=DATA.length();
                            }
                            for (int i = 0; i < DATA.length(); i++) {
                                JSONObject jsonobject = DATA.getJSONObject(i);
                                String title = jsonobject.getString("title");
                                String desc = jsonobject.getString("description");
                                String thumb = jsonobject.optString("thumbnail");
                                String url = jsonobject.getString("url");
                             //    System.out.println("the response in "+i+" title "+title +"desc "+desc+" thum "+thumb+" url "+url);
                                ShopsArrayList.add(new ModelsShop(title,desc,thumb,url));
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    //    System.out.println("in the thirdline with "+error.getMessage());

                    }
                });

        rq.add(jsObjRequest);
        //  request();

       try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        ShopsArrayList.clear();
    }

    public int getCount() {
        return mCount;
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.shops_up_widget_item);

            if(ShopsArrayList.size()==0){
                rv.setTextViewText(R.id.textViewrfqsnt_name, "empty");
            }
            else {
                if(ShopsArrayList.get(position).getTitle().length()>32){
                    String a =ShopsArrayList.get(position).getTitle();
                    a=a.substring(0,29);
                    a=a+" ...";
                    rv.setTextViewText(R.id.textViewrfqsnt_name, a);
                }
                else{
                    if(ShopsArrayList.get(position).getTitle().equals(" ")){
                        rv.setTextViewText(R.id.textViewrfqsnt_name, "N/A");
                    }
                    else{
                        rv.setTextViewText(R.id.textViewrfqsnt_name, ShopsArrayList.get(position).getTitle());
                    }

                }
                if(ShopsArrayList.get(position).getDescription().length()>38){
                    String a =ShopsArrayList.get(position).getDescription();
                    a=a.substring(0,37);
                    a=a+" ...";
                    rv.setTextViewText(R.id.textViewrfqsnt_sender, a);
                }
                else{
                    if(ShopsArrayList.get(position).getDescription().equals(" ")){
                        rv.setTextViewText(R.id.textViewrfqsnt_sender, "N/A");
                    }
                    else{
                        rv.setTextViewText(R.id.textViewrfqsnt_sender, ShopsArrayList.get(position).getDescription());
                    }
                }

                rv.setTextViewText(R.id.TextViewglad_tech_name, ShopsArrayList.get(position).getUrl());

            }
        Bundle extras = new Bundle();
        extras.putInt(ShopsUpWidget.EXTRA_ITEM, position);
        if(ShopsArrayList.isEmpty()){
            extras.putString("url", "");
        }
        else{
            extras.putString("url", ShopsArrayList.get(position).getUrl());
        }
       // extras.putString("url", ShopsArrayList.get(position).getUrl());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.TextViewglad_tech_name, fillInIntent);

        try {
           // System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      //  }
        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }
}