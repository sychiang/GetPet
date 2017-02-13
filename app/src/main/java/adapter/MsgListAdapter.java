package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import iii.org.tw.getpet.ActMsgBox;
import iii.org.tw.getpet.ActMsgShow;
import iii.org.tw.getpet.R;
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
 * Created by user on 2017/2/5.
 */

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.ViewHolder> {
    private List<UserMsg> mData;
    private Context mContext;
    private LayoutInflater inflater;
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;

    public MsgListAdapter(List<UserMsg> data) {
        mData = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvSender, tvIfRead;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            tvTitle = (TextView) view.findViewById(R.id.msglist_tvTitle);
            tvSender = (TextView) view.findViewById(R.id.msglist_tvSender);
            tvIfRead = (TextView) view.findViewById(R.id.msglist_tvIfRead);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.msglist, parent, false);
        final ViewHolder vholder = new ViewHolder(view);

        vholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vholder.getAdapterPosition();

                UserName = mContext.getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
                Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
                UserId = mContext.getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
                Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
                access_token = mContext.getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
                Log.d(CDictionary.Debug_TAG,"GET USER ID："+access_token);

                Intent intent = new Intent(mContext, ActMsgShow.class);
                Bundle bundle = new Bundle();
                //bundle.putString(CDictionary.BK_msg_id, String.format("%d",mData.get(position).getMsgID()));
                bundle.putInt(CDictionary.BK_msg_id, mData.get(position).getMsgID());
                bundle.putString(CDictionary.BK_msg_time, mData.get(position).getMsgTime());
                bundle.putString(CDictionary.BK_msg_fromuserid, mData.get(position).getMsgFrom_userID());
                bundle.putString(CDictionary.BK_msg_fromusername, mData.get(position).getMsgFrom_userName());
                bundle.putString(CDictionary.BK_msg_touserid, mData.get(position).getMsgTo_userID());
                bundle.putString(CDictionary.BK_msg_subject, mData.get(position).getMsgType());
                bundle.putString(CDictionary.BK_msg_fromurl, mData.get(position).getMsgFromURL());
                bundle.putString(CDictionary.BK_msg_content, mData.get(position).getMsgContent());
                bundle.putString(CDictionary.BK_msg_read, mData.get(position).getMsgRead());
                intent.putExtras(bundle);
                mContext.startActivity(intent);

                if(mData.get(position).getMsgRead().equals("未讀")){
                    String strURL = "http://twpetanimal.ddns.net:9487/api/v1/MsgUsers";
                    strURL += "/"+String.format("%d",mData.get(position).getMsgID());
                    Log.d(CDictionary.Debug_TAG,"GET URL: "+strURL);
                    //傳送PUT修改為已讀
                    OkHttpClient okhttpclient = new OkHttpClient();
                    final MediaType mediatype_json = MediaType.parse("application/json; charset=utf-8");

                    JSONObject jsonObject = new JSONObject();
                    Log.d(CDictionary.Debug_TAG,"CREATE JSON OBJ: "+jsonObject.toString());
                    try {
                        jsonObject.put("msgID", mData.get(position).getMsgID());
                        jsonObject.put("msgTime", mData.get(position).getMsgTime());
                        jsonObject.put("msgFrom_userID", mData.get(position).getMsgFrom_userID());
                        jsonObject.put("msgTo_userID", mData.get(position).getMsgTo_userID());
                        jsonObject.put("msgType", mData.get(position).getMsgType());
                        jsonObject.put("msgFromURL", mData.get(position).getMsgFromURL());
                        jsonObject.put("msgContent", mData.get(position).getMsgContent());
                        jsonObject.put("msgRead","已讀");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestBody requestBody =  RequestBody.create(mediatype_json,jsonObject.toString());
                    Request postRequest = new Request.Builder()
                            .url(strURL)
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization","Bearer "+access_token)
                            .put(requestBody)
                            .build();

                    Call call = okhttpclient.newCall(postRequest);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String json = response.body().string();
                            Log.d(CDictionary.Debug_TAG,"RESPONSE BODY: "+json);
                        }
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(CDictionary.Debug_TAG,"PUT FAIL......");
                        }
                    });
                }
            }
        });
        return vholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserMsg message = mData.get(position);
        holder.tvTitle.setText(message.getMsgType());
        holder.tvSender.setText(message.getMsgFrom_userName());
        holder.tvIfRead.setText(message.getMsgRead());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}
