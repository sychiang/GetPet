package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import adapter.AdoptPairListAdapter;
import adapter.ShelterPetListAdapter;
import common.CDictionary;
import model.AdoptPair;
import model.ShelterPet;

public class ActAdoptPairList extends AppCompatActivity implements AbsListView.OnScrollListener{
    ArrayList<AdoptPair> petlist = new ArrayList<AdoptPair>();
    ArrayList<AdoptPair> showlist = new ArrayList<AdoptPair>();
    ListView listview;
    AdoptPairListAdapter adapter;

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
        setContentView(R.layout.act_adopt_pair_list);
        Intent intent = getIntent();
        String url = "http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas";

        listview = (ListView) findViewById(R.id.pair_petlist);
        // 實例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.loadmoredata, null);
        btn_load = (Button) moreView.findViewById(R.id.btn_load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        handler = new Handler();

        //取回JSON資料存入集合
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
                                        petlist.add(rs);
                                        Log.d(CDictionary.Debug_TAG, ""+rs.getAnimalID());
                                    }
                                    MaxDataNum = petlist.size(); // 設置最大數據條數
                                    size = String.format("%d", petlist.size());
                                    Log.d(CDictionary.Debug_TAG, size);
                                    if(MaxDataNum<10){
                                        for (int i = 0; i < MaxDataNum; i++) {
                                            showlist.add(petlist.get(i));
                                        }
                                    }else{
                                        for (int i = 0; i < 10; i++) {
                                            showlist.add(petlist.get(i));
                                        }
                                    }
                                    size = String.format("%d", showlist.size());
                                    Log.d(CDictionary.Debug_TAG, size);

                                    // 加上底部View，注意要放在setAdapter方法前
                                    listview.addFooterView(moreView);
                                    adapter = new AdoptPairListAdapter(ActAdoptPairList.this, showlist);
                                    listview.setAdapter(adapter);
                                    // 绑定監聽器
                                    listview.setOnScrollListener(ActAdoptPairList.this);

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
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptPairList.this);
                                    dialog.setTitle("查無資料");
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ActAdoptPairList.this, ActSearchShelter.class);
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
                bundle.putString(CDictionary.BK_animal_name, petlist.get(position).getAnimalName());
//                bundle.putString(CDictionary.BK_animal_kind, petlist.get(position).getAnimal_kind());
//                bundle.putString(CDictionary.BK_animal_sex, petlist.get(position).getAnimal_sex());
//                bundle.putString(CDictionary.BK_animal_bodytype, petlist.get(position).getAnimal_bodytype());
//                bundle.putString(CDictionary.BK_animal_colour, petlist.get(position).getAnimal_colour());
//                bundle.putString(CDictionary.BK_animal_age, petlist.get(position).getAnimal_age());
//                bundle.putString(CDictionary.BK_album_file, petlist.get(position).getAlbum_file());
//                bundle.putString(CDictionary.BK_shelter_name, petlist.get(position).getShelter_name());
//                bundle.putString(CDictionary.BK_shelter_address, petlist.get(position).getShelter_address());
//                bundle.putString(CDictionary.BK_shelter_tel, petlist.get(position).getShelter_tel());
//                bundle.putString(CDictionary.BK_animal_remark, petlist.get(position).getAnimal_remark());
                Intent intent = new Intent(ActAdoptPairList.this, ActAdoptPairDetail.class);
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadMoreData() {
        int count = adapter.getCount();
        if (count + 5 < MaxDataNum) {
            // 每次加載5條
            for (int i = count; i < count + 5; i++) {
                showlist.add(petlist.get(i));
            }
        } else {
            // 數據已經不足5條
            for (int i = count; i < MaxDataNum; i++) {
                showlist.add(petlist.get(i));
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
