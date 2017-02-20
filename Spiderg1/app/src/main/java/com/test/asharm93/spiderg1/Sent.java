package com.test.asharm93.spiderg1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by asharm93 on 5/4/2016.
 */
public class Sent extends Fragment implements OnClickListener{
    View v;
    private ListView mListView;
    ArrayList<SentDocumentModel> modelsArrayList = new ArrayList<SentDocumentModel>();
    private MyTask task;
    private RelativeLayout footer;
    private MyAdapter adapter;
    JSONArray jarray;
    String json = null;
    String ImageName,rfqsntname,rfqsntsender,Gladimage,Gladtechname,gladfourthline,docnumb,rfq,timstamp,image1,image2,image3;
    Context context;
    JSONObject mainobject,jobject;
    Button btn1,btn2,btn3,btn4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.sent_layout, container, false);
        context=getActivity();
        btn1=(Button)v.findViewById(R.id.buttonOnline);
        btn1.setOnClickListener(this);
        btn2=(Button)v.findViewById(R.id.button3);
        btn2.setOnClickListener(this);
        btn3=(Button)v.findViewById(R.id.button4);
        btn3.setOnClickListener(this);
        btn4=(Button)v.findViewById(R.id.buttonCash);
        btn4.setOnClickListener(this);
        mListView = (ListView) v.findViewById(R.id.LV_all_sent_items);

        footer = (RelativeLayout) inflater.inflate(R.layout.footer_progressbar, null);
        mListView.addFooterView(footer);
        adapter = new MyAdapter(context, R.layout.document_management_list_item);
        mListView.setAdapter(adapter);
        task = new MyTask();
        task.execute();
        return v;
    }

    @Override
    public void onClick(View v) {
        // implements your things
        if(v.getId()==R.id.buttonOnline){
            btn1.setBackgroundColor(Color.parseColor("#0B54DD"));
            btn1.setTextColor(Color.parseColor("#ffffff"));
            btn2.setBackgroundColor(Color.parseColor("#ffffff"));
            btn2.setTextColor(Color.parseColor("#000000"));
            btn3.setBackgroundColor(Color.parseColor("#ffffff"));
            btn3.setTextColor(Color.parseColor("#000000"));
            btn4.setBackgroundColor(Color.parseColor("#ffffff"));
            btn4.setTextColor(Color.parseColor("#000000"));
        }
        if(v.getId()==R.id.button3){
            btn2.setBackgroundColor(Color.parseColor("#0B54DD"));
            btn2.setTextColor(Color.parseColor("#ffffff"));
            btn1.setBackgroundColor(Color.parseColor("#ffffff"));
            btn1.setTextColor(Color.parseColor("#000000"));
            btn3.setBackgroundColor(Color.parseColor("#ffffff"));
            btn3.setTextColor(Color.parseColor("#000000"));
            btn4.setBackgroundColor(Color.parseColor("#ffffff"));
            btn4.setTextColor(Color.parseColor("#000000"));
        }
        if(v.getId()==R.id.button4){
            btn3.setBackgroundColor(Color.parseColor("#0B54DD"));
            btn3.setTextColor(Color.parseColor("#ffffff"));
            btn1.setBackgroundColor(Color.parseColor("#ffffff"));
            btn1.setTextColor(Color.parseColor("#000000"));
            btn2.setBackgroundColor(Color.parseColor("#ffffff"));
            btn2.setTextColor(Color.parseColor("#000000"));
            btn4.setBackgroundColor(Color.parseColor("#ffffff"));
            btn4.setTextColor(Color.parseColor("#000000"));
        }
        if(v.getId()==R.id.buttonCash){
            btn4.setBackgroundColor(Color.parseColor("#0B54DD"));
            btn4.setTextColor(Color.parseColor("#ffffff"));
            btn1.setBackgroundColor(Color.parseColor("#ffffff"));
            btn1.setTextColor(Color.parseColor("#000000"));
            btn2.setBackgroundColor(Color.parseColor("#ffffff"));
            btn2.setTextColor(Color.parseColor("#000000"));
            btn3.setBackgroundColor(Color.parseColor("#ffffff"));
            btn3.setTextColor(Color.parseColor("#000000"));
        }
    }
    class MyTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                InputStream is = getActivity().getAssets().open("SentDocMngmnt.json");

                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
                mainobject = new JSONObject(json);
                jarray = mainobject.getJSONArray("sent");
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                for (int i = 0; i < jarray.length(); i++) {
                    jobject = jarray.getJSONObject(i);
                    ImageName = jobject.getString("image_name_rqf");
                    rfqsntname = jobject.getString("rfqsnt_name");
                    rfqsntsender = jobject.getString("rfqsnt_sender");
                    Gladimage = jobject.getString("glad_image");
                    Gladtechname = jobject.getString("glad_tech_name");
                    gladfourthline = jobject.getString("glad_tech_more_details");
                    docnumb = jobject.getString("doc_numb");
                    rfq = jobject.getString("rfq");
                    timstamp = jobject.getString("time_stamp");
                    image1 = jobject.getString("image_1");
                    image2 = jobject.getString("image_2");
                    image3 = jobject.getString("image_3");
                    modelsArrayList.add(new SentDocumentModel(ImageName, rfqsntname, rfqsntsender, Gladimage,Gladtechname,gladfourthline,docnumb,rfq,timstamp,image1,image2,image3));
                    System.out.println("The data with position "+i+" is "+ImageName+rfqsntname+rfqsntsender+Gladimage+Gladtechname+gladfourthline+docnumb+rfq+timstamp+image1+image2+image3);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            mListView.removeFooterView(footer);

        }
    }

    class MyAdapter extends ArrayAdapter<SentDocumentModel> {
        LayoutInflater inflater;
        /*private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
        private TextDrawable.IBuilder mDrawableBuilder;*/
        public MyAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

            return modelsArrayList.size();
        }
        @Override
        public SentDocumentModel getItem(int position) {

            return modelsArrayList.get(position);
        }

        @SuppressLint("NewApi") @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AdapterHolder holder;
            if(convertView==null)
            {
                convertView = inflater.inflate(R.layout.document_management_list_item, null);
                holder= new AdapterHolder();
                holder.tvrfqsnt_name=(TextView)convertView.findViewById(R.id.textViewrfqsnt_name);
                holder.tvrfqsnt_sender=(TextView)convertView.findViewById(R.id.textViewrfqsnt_sender);
                holder.tvglad_tech_name=(TextView)convertView.findViewById(R.id.TextViewglad_tech_name);

                convertView.setTag(holder);
            }
            else{
                holder=(AdapterHolder)convertView.getTag();
            }
            holder.model = modelsArrayList.get(position);
            holder.tvrfqsnt_name.setText(modelsArrayList.get(position).getrfqsntname());
            holder.tvrfqsnt_sender.setText(modelsArrayList.get(position).getrfqsntsender());
            holder.tvglad_tech_name.setText(modelsArrayList.get(position).getGladtechname());



            return convertView;
        }
    }

  private static class AdapterHolder
    {
        SentDocumentModel model;
        TextView  tvrfqsnt_name, tvrfqsnt_sender,tvglad_tech_name;

    }
}
