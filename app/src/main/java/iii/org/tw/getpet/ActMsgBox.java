package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import common.CDictionary;
import model.Category;
import model.CMessage;
import model.UserMsg;

public class ActMsgBox extends AppCompatActivity {
    ArrayList<UserMsg> myDataset = new ArrayList<UserMsg>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_box);
        String url = "http://twpetanimal.ddns.net:9487/api/v1/MsgUsers";
        //初始化元件
        final RecyclerView mList = (RecyclerView) findViewById(R.id.msgboxlist_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);

//        Intent intent = getIntent();
//        name = intent.getExtras().getString(CDictionary.BK_fb_name);
//        Log.d(CDictionary.Debug_TAG,"Get userName："+name);

//取回MSG資料存入集合
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<ArrayList<UserMsg>>() {
                             },
                        new ParsedRequestListener<ArrayList<UserMsg>>() {
                            @Override
                            public void onResponse(ArrayList<UserMsg> response) {
                                String size = String.format("%d", response.size());
                                Log.d(CDictionary.Debug_TAG, size);
                                if (response.size() > 0) {
                                    for (UserMsg rs : response) {
                                        myDataset.add(rs);
                                        Log.d(CDictionary.Debug_TAG, "Get Msg: "+rs.getMsgID());
                                    }
                                    ActMsgBox.MyAdapter myAdapter = new ActMsgBox.MyAdapter(myDataset);
                                    mList.setAdapter(myAdapter);
                                } else {
                                    //No Response 什麼都不做
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

    }

    public class MyAdapter extends RecyclerView.Adapter<ActMsgBox.MyAdapter.ViewHolder> {
        private List<UserMsg> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle, tvSender;
            public CardView cardView;

            public ViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
                tvTitle = (TextView) view.findViewById(R.id.msglist_title);
                tvSender = (TextView) view.findViewById(R.id.msglist_sender);
            }
        }

        public MyAdapter(List<UserMsg> data) {
            mData = data;
        }

        @Override
        public ActMsgBox.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msglist, parent, false);
            final ActMsgBox.MyAdapter.ViewHolder vholder = new ActMsgBox.MyAdapter.ViewHolder(view);

            vholder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = LayoutInflater.from(ActMsgBox.this);
                    final View view = inflater.inflate(R.layout.msgdetail_alertdialog, null);
                    TextView msg_subject = (TextView) view.findViewById(R.id.msg_subject);
                    TextView msg_sender = (TextView) view.findViewById(R.id.msg_sender);
                    TextView msg_content = (TextView) view.findViewById(R.id.msg_content);

                    int position = vholder.getAdapterPosition();
                    msg_subject.setText(myDataset.get(position).getMsgType());
                    msg_sender.setText(myDataset.get(position).getMsgFrom_userName());
                    msg_content.setText(myDataset.get(position).getMsgContent());

                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgBox.this);
                    dialog.setView(view);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.create().show();
                }
            });
            return vholder;
        }

        @Override
        public void onBindViewHolder(ActMsgBox.MyAdapter.ViewHolder holder, int position) {
            UserMsg message = mData.get(position);
            holder.tvTitle.setText(message.getMsgType());
            holder.tvSender.setText(message.getMsgFrom_userID());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
