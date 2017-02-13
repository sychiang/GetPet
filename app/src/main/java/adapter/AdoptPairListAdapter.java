package adapter;

import android.content.Context;
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
import model.ShelterPet;

/**
 * Created by user on 2017/2/1.
 */

public class AdoptPairListAdapter extends BaseAdapter{
    private List<AdoptPair> petList;
    private Context context;
    private LayoutInflater inflater;

    public AdoptPairListAdapter(Context context, List<AdoptPair> petList) {
        this.petList = petList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return petList.size();
    }

    @Override
    public Object getItem(int position) {
        return petList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.pairlist_eachitem, parent, false);

        AdoptPair item = (AdoptPair) getItem(position);

        ImageView ivImage = (ImageView) rowView.findViewById(R.id.pair_image);
        TextView tvName = (TextView) rowView.findViewById(R.id.pair_name);
        TextView tvAge = (TextView) rowView.findViewById(R.id.pair_age);
        TextView tvLocation = (TextView) rowView.findViewById(R.id.pair_location);
        TextView tvDate = (TextView) rowView.findViewById(R.id.pair_date);

        if(item.getAnimalData_Pic().size()>0){
            String imgURL = item.getAnimalData_Pic().get(0).getAnimalPicAddress();
            if(imgURL != null){
                if(imgURL.toLowerCase().endsWith(".jpg") || imgURL.toLowerCase().endsWith(".png")){
                    Glide.with(context).load(imgURL).into(ivImage);
                }
            }
        }

        tvName.setText(item.getAnimalName());
        tvAge.setText(String.format("%d",item.getAnimalAge()));
        tvLocation.setText(item.getAnimalAddress());
        tvDate.setText(item.getAnimalDate());
        return rowView;
    }
}
