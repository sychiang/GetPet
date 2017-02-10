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
import android.view.Menu;
import android.view.MenuItem;
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

import adapter.MsgListAdapter;
import common.CDictionary;
import model.Category;
import model.CMessage;
import model.UserMsg;

public class ActMsgBox extends AppCompatActivity {
    ArrayList<UserMsg> myDataset = new ArrayList<UserMsg>();
    String name, id;
    MsgListAdapter adapter;
    RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_box);
        String url = "http://twpetanimal.ddns.net:9487/api/v1/MsgUsers";

        //初始化元件
        recyclerList = (RecyclerView) findViewById(R.id.msgboxlist_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);

        name = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+name);
        id = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+id);
        //url += "/"+id;
        Log.d(CDictionary.Debug_TAG,"GET URL："+url);

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
                                    adapter = new MsgListAdapter(myDataset);
                                    recyclerList.setAdapter(adapter);
                                } else {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgBox.this);
                                    dialog.setTitle("目前尚無訊息");
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ActMsgBox.this, ActHomePage.class);
                                            startActivity(intent);
                                        }
                                    });
                                    dialog.create().show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_backtohome) {
            Intent intent = new Intent(this, ActHomePage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
