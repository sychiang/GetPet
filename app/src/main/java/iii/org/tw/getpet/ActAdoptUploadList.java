package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import adapter.AdoptUploadListAdapter;
import adapter.MsgListAdapter;
import common.CDictionary;
import model.AdoptPair;
import model.UserMsg;
import model.object_petDataForSelfDB;

public class ActAdoptUploadList extends AppCompatActivity {
    //ArrayList<object_petDataForSelfDB> myDataset = new ArrayList<object_petDataForSelfDB>();
    ArrayList<AdoptPair> myDataset = new ArrayList<AdoptPair>();
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    AdoptUploadListAdapter adapter;
    RecyclerView recyclerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopt_upload_list);
        setTitle("送養清單");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //初始化元件
        recyclerList = (RecyclerView) findViewById(R.id.adoptUploadList_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);

        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER TOKEN："+access_token);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActAdoptUploadList.this, ActAdoptUpload.class);
                startActivity(intent);

            }
        });
        getDatafromServer();
    }

    public void getDatafromServer(){
        String url = "http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas";
        url += "?$filter=animalOwner_userID eq '"+UserId+"'";
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
                                String size = String.format("%d", response.size());
                                Log.d(CDictionary.Debug_TAG, size);
                                if (response.size() > 0) {
                                    for (AdoptPair rs : response) {
                                        myDataset.add(rs);
                                        Log.d(CDictionary.Debug_TAG, "GET PET: "+rs.getAnimalID());
                                    }
                                    adapter = new AdoptUploadListAdapter(myDataset);
                                    recyclerList.setAdapter(adapter);
                                } else {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptUploadList.this);
                                    dialog.setTitle("您目前尚無上傳資料");
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent(ActMsgBox.this, ActHomePage.class);
//                                            startActivity(intent);
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
