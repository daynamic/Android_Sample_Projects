package com.test.asharm93.messagingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.test.asharm93.messagingapp.R;
import com.test.asharm93.messagingapp.models.SMSModel;

import java.util.List;

/**
 * Created by asharm93 on 7/9/2016.
 */
public class RcvdMsdRecyclerAdapter extends RecyclerView.Adapter<RcvdMsdRecyclerAdapter.MyViewHolder>{
    private List<SMSModel> msmsList;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mShopName, textname;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            mDrawableBuilder = TextDrawable.builder()
                    .round();
            mShopName = (TextView) view.findViewById(R.id.shop_name_apparel_shops);
            textname = (TextView) view.findViewById(R.id.maintext);
            image = (ImageView) view.findViewById(R.id.imageViewapparel_shops);
        }
    }


    public RcvdMsdRecyclerAdapter(List<SMSModel> smsList) {
        msmsList = smsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_msgs, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SMSModel sms = msmsList.get(position);
        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(sms.getNumber().charAt(0)), mColorGenerator.getColor(sms.getNumber()));
        holder.image.setImageDrawable(drawable);
        holder.mShopName.setText(sms.getNumber());
        if(sms.getBody().length()>47){
            String ac=sms.getBody();
            ac=ac.substring(0,46);
            ac=ac+"...";
            holder.textname.setText(ac);
        }
        else{
            holder.textname.setText(sms.getBody());
        }
    }

    @Override
    public int getItemCount() {
        return msmsList.size();
    }
}
