package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import common.CDictionary;
import iii.org.tw.getpet.ActMsgShow;
import iii.org.tw.getpet.R;
import model.Board;
import model.UserMsg;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by iii on 2017/2/13.
 */

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder>{
    private List<Board> mData;
    private Context mContext;
    private LayoutInflater inflater;
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;

    public BoardListAdapter(List<Board> mData) {
        this.mData = mData;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_username, tv_content;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_username = (TextView) view.findViewById(R.id.tv_username);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.boardlist, parent, false);
        final ViewHolder vholder = new ViewHolder(view);

        vholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vholder.getAdapterPosition();
           }
        });
        return vholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Board item = mData.get(position);
        holder.tv_date.setText(item.getBoardTime());
        holder.tv_username.setText(item.getUserName());
        holder.tv_content.setText(item.getBoardContent());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
