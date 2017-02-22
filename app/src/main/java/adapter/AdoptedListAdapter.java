package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import iii.org.tw.getpet.R;
import model.AdoptPair;

/**
 * Created by user on 2017/2/22.
 */

public class AdoptedListAdapter extends RecyclerView.Adapter<AdoptedListAdapter.ViewHolder>{
    private List<AdoptPair> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public AdoptedListAdapter(List<AdoptPair> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adoptedlist_eachitem, parent, false);
        final AdoptedListAdapter.ViewHolder vholder = new AdoptedListAdapter.ViewHolder(view);
        return vholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AdoptPair item = mData.get(position);
        holder.adopted_name.setText(item.getAnimalName());
        holder.adopted_age.setText(String.format("%d",item.getAnimalAge()));
        holder.adopted_gender.setText(item.getAnimalGender());
        holder.adopted_location.setText(item.getAnimalAddress());
        holder.adopted_date.setText(item.getAnimalDate());

        if(item.getAnimalData_Pic().size()>0){
            String imgURL = item.getAnimalData_Pic().get(0).getAnimalPicAddress();
            if(imgURL != null){
                if(imgURL.toLowerCase().endsWith(".jpg") || imgURL.toLowerCase().endsWith(".png")){
                    Glide.with(mContext).load(imgURL).into(holder.adopted_image);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView adopted_name,adopted_age ,adopted_gender,adopted_location,adopted_date;
        public CardView cardView;
        public ImageView adopted_image;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            adopted_name = (TextView) view.findViewById(R.id.adopted_name);
            adopted_age = (TextView) view.findViewById(R.id.adopted_age);
            adopted_gender = (TextView) view.findViewById(R.id.adopted_gender);
            adopted_location = (TextView) view.findViewById(R.id.adopted_location);
            adopted_date = (TextView) view.findViewById(R.id.adopted_date);
            adopted_image = (ImageView) view.findViewById(R.id.adopted_image);
        }
    }

}
