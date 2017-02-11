package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import common.CDictionary;
import iii.org.tw.getpet.ActHomePage;
import iii.org.tw.getpet.R;
import model.AdoptPair;
import model.object_petDataForSelfDB;

/**
 * Created by user on 2017/2/11.
 */

public class AdoptUploadListAdapter extends RecyclerView.Adapter<AdoptUploadListAdapter.ViewHolder> {
    private List<object_petDataForSelfDB> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public AdoptUploadListAdapter(List<object_petDataForSelfDB> data) {
        this.mData = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_upload_date, tv_upload_name;
        public CardView cardView;
        public ImageView iv_upload_image;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            tv_upload_date = (TextView) view.findViewById(R.id.tv_upload_date);
            tv_upload_name = (TextView) view.findViewById(R.id.tv_upload_name);
            iv_upload_image = (ImageView) view.findViewById(R.id.iv_upload_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_act_adopt_upload_list, parent, false);
        final ViewHolder vholder = new ViewHolder(view);

        vholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vholder.getAdapterPosition();

                Intent intent = new Intent(mContext, ActHomePage.class);
                intent.putExtra("object_ConditionOfAdoptPet_objA",mData.get(position));
                mContext.startActivity(intent);
            }
        });

        return vholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        object_petDataForSelfDB item = mData.get(position);
        holder.tv_upload_name.setText(item.getAnimalName());
        holder.tv_upload_date.setText(item.getAnimalDate());
        if(item.getAnimalData_Pic().size()>0){
            String imgURL = item.getAnimalData_Pic().get(0).getAnimalPicAddress();
            if(imgURL.toLowerCase().endsWith(".jpg") || imgURL.toLowerCase().endsWith(".png")){
                Glide.with(mContext).load(imgURL).into(holder.iv_upload_image);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
