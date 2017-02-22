package iii.org.tw.getpet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import adapter.AdoptPairListAdapter;
import adapter.AdoptUploadListAdapter;
import adapter.AdoptedListAdapter;
import common.CDictionary;
import model.AdoptPair;

public class ActAdoptedList extends AppCompatActivity implements AbsListView.OnScrollListener{
    ArrayList<AdoptPair> myDataset = new ArrayList<AdoptPair>();
    RecyclerView recyclerList;
    AdoptedListAdapter adapter;
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopted_list);
        setTitle("已認養清單");

        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER TOKEN："+access_token);

        //初始化元件
        recyclerList = (RecyclerView) findViewById(R.id.adoptedList_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);

        progressDialog = ProgressDialog.show(ActAdoptedList.this, Html.fromHtml("<font color='#2d4b44'>資料讀取中, 請稍後...</font>"), "", true);
        getDatafromServer();

    }

    public void getDatafromServer(){
        String url = "http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas?$orderby=animalDate desc";
        url += "&$filter=animalGetter_userID eq '"+UserName+"'";
        Log.d(CDictionary.Debug_TAG,"GET URL："+url);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<ArrayList<AdoptPair>>() {
                             },
                        new ParsedRequestListener<ArrayList<AdoptPair>>() {
                            @Override
                            public void onResponse(ArrayList<AdoptPair> response) {
                                progressDialog.dismiss();
                                String size = String.format("%d", response.size());
                                Log.d(CDictionary.Debug_TAG, size);
                                if (response.size() > 0) {
                                    for (AdoptPair rs : response) {
                                        myDataset.add(rs);
                                        Log.d(CDictionary.Debug_TAG, "GET PET: "+rs.getAnimalID());
                                    }
                                    adapter = new AdoptedListAdapter(myDataset);
                                    recyclerList.setAdapter(adapter);
                                } else {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptedList.this);
                                    dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>尚無資料</font>"));
                                    dialog.setMessage(Html.fromHtml("<font color='#2d4b44'>您目前尚無已認養資料</font>"));
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ActAdoptedList.this, ActMember.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    dialog.create().show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                progressDialog.dismiss();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptedList.this);
                                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>連線錯誤, 請稍後再試</font>"));
                                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ActAdoptedList.this, ActMember.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                dialog.create().show();
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
