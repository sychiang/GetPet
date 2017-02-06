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
import model.Following;

/**
 * Created by user on 2017/2/5.
 */

public class FollowingListAdapter extends BaseAdapter {
    private List<Following> followList;
    private Context context;
    private LayoutInflater inflater;

    public FollowingListAdapter(Context context, List<Following> followList) {
        this.followList = followList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return followList.size();
    }

    @Override
    public Object getItem(int position) {
        return followList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.followlist_eachitem, parent, false);

        Following item = (Following) getItem(position);     //之後要改成 追蹤清單取回的JSON 物件類別

        ImageView ivImage = (ImageView) rowView.findViewById(R.id.followpet_image);
        TextView tvName = (TextView) rowView.findViewById(R.id.followpet_name);
        TextView tvAge = (TextView) rowView.findViewById(R.id.followpet_age);
        TextView tvLocation = (TextView) rowView.findViewById(R.id.followpet_location);
        TextView tvDate = (TextView) rowView.findViewById(R.id.followpet_location);

        if(item.getAnimalPicAddress().toLowerCase().contains(".jpg") || item.getAnimalPicAddress().toLowerCase().contains(".png")){
            String imgURL = item.getAnimalPicAddress();
                Glide.with(context).load(imgURL).into(ivImage);
                //Picasso.with(context).load(imgURL).into(ivImage);
        }

        tvName.setText(item.getAnimalName());
        tvAge.setText(String.format("%d",item.getAnimalAge()));
        tvLocation.setText(item.getAnimalAddress());
        //tvDate.setText(item.get());
        return rowView;
    }
}
