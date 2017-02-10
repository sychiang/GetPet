package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import adapter.FollowingListAdapter;
import common.CDictionary;
import model.Following;

public class ActFollowingList extends AppCompatActivity implements AbsListView.OnScrollListener{
    ArrayList<Following> followList = new ArrayList<Following>();
    ArrayList<Following> showlist = new ArrayList<Following>();
    ListView listview;
    FollowingListAdapter adapter;
    String name, id;

    //listView 分頁加載
    private Button btn_load;
    private ProgressBar pg;
    // ListView底部View
    private View moreView;
    private Handler handler;
    // 設置一個最大的數據條數，超過即不再加載
    private int MaxDataNum;
    // 最後可見條目的索引
    private int lastVisibleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_following_list);
        //Intent intent = getIntent();
        String url = "http://twpetanimal.ddns.net:9487/api/v1/followAnis";   //改成追蹤清單的API

        listview = (ListView) findViewById(R.id.followlist);
        // 實例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.loadmoredata, null);
        btn_load = (Button) moreView.findViewById(R.id.btn_load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        handler = new Handler();

        name = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+name);
        id = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+id);
        //url += "/"+id;
        Log.d(CDictionary.Debug_TAG,"GET URL："+url);

        //取回JSON資料存入集合
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<ArrayList<Following>>() {
                             },
                        new ParsedRequestListener<ArrayList<Following>>() {
                            @Override
                            public void onResponse(ArrayList<Following> response) {
                                String size = String.format("%d", response.size());
                                Log.d(CDictionary.Debug_TAG, size);
                                if (response.size() > 0) {
                                    for (Following rs : response) {
                                        followList.add(rs);
                                        Log.d(CDictionary.Debug_TAG, "Get "+rs.getFollowID());
                                    }
                                    if(followList.size()<50){
                                        adapter = new FollowingListAdapter(ActFollowingList.this, followList);
                                        listview.setAdapter(adapter);
                                    }else {
                                        MaxDataNum = followList.size(); // 設置最大數據條數
                                        //size = String.format("%d", followList.size());
                                        Log.d(CDictionary.Debug_TAG, "Set MaxNum: "+MaxDataNum);
                                        if(MaxDataNum<10){
                                            for (int i = 0; i < MaxDataNum; i++) {
                                                showlist.add(followList.get(i));
                                                Log.d(CDictionary.Debug_TAG, "showlist add: "+showlist.get(i).getFollowID());
                                            }
                                        }else{
                                            for (int i = 0; i < 10; i++) {
                                                showlist.add(followList.get(i));
                                            }
                                        }
                                        size = String.format("%d", showlist.size());
                                        Log.d(CDictionary.Debug_TAG, size);

                                        // 加上底部View，注意要放在setAdapter方法前
                                        listview.addFooterView(moreView);
                                        adapter = new FollowingListAdapter(ActFollowingList.this, showlist);
                                        listview.setAdapter(adapter);
                                        Log.d(CDictionary.Debug_TAG,"Set Adapter");
                                    }

                                    // 绑定監聽器
                                    listview.setOnScrollListener(ActFollowingList.this);
                                    btn_load.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pg.setVisibility(View.VISIBLE);// 將進度條可見
                                            btn_load.setVisibility(View.GONE);// 按鈕不可見
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    loadMoreData();// 加載更多數據
                                                    btn_load.setVisibility(View.VISIBLE);
                                                    pg.setVisibility(View.GONE);
                                                    adapter.notifyDataSetChanged();// 通知listView刷新數據
                                                }
                                            }, 1000);
                                        }
                                    });
                                } else {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActFollowingList.this);
                                    dialog.setTitle("尚未有追蹤項目");
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ActFollowingList.this, ActHomePage.class);
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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(CDictionary.BK_animalID_following, String.format("%d",followList.get(position).getAnimalID()));
                Log.d(CDictionary.Debug_TAG,"SET ANIMAL ID: "+String.format("%d",followList.get(position).getAnimalID()));
                Intent intent = new Intent(ActFollowingList.this, ActFollowingDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
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

    private void loadMoreData() {
        int count = adapter.getCount();
        if (count + 5 < MaxDataNum) {
            // 每次加載5條
            for (int i = count; i < count + 5; i++) {
                showlist.add(followList.get(i));
            }
        } else {
            // 數據已經不足5條
            for (int i = count; i < MaxDataNum; i++) {
                showlist.add(followList.get(i));
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 滑到底部後自動加載，判斷listview已經停止滾動並且最後可視的條目等於adapter的條目
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == adapter.getCount()) {
            //當滑到底部時自動加載
            pg.setVisibility(View.VISIBLE);
            btn_load.setVisibility(View.GONE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadMoreData();
                    btn_load.setVisibility(View.VISIBLE);
                    pg.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }, 1000);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 計算最後可見條目的索引
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;

        // 所有的條目已經和最大條數相等，則移除底部的View
        if (totalItemCount == MaxDataNum + 1) {
            listview.removeFooterView(moreView);
            Toast.makeText(this, "數據全部加載完成，沒有更多數據！", Toast.LENGTH_LONG).show();
        }
    }
}
