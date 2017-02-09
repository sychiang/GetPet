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

import adapter.ShelterPetListAdapter;
import common.CDictionary;
import model.ShelterPet;

public class ActShelterPetList extends AppCompatActivity implements AbsListView.OnScrollListener {
    ArrayList<ShelterPet> petlist = new ArrayList<ShelterPet>();
    ArrayList<ShelterPet> showlist = new ArrayList<ShelterPet>();
    ListView listview;
    ShelterPetListAdapter adapter;

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
        setContentView(R.layout.act_shelter_pet_list);
        Intent intent = getIntent();
        String url = "http://data.coa.gov.tw/Service/OpenData/AnimalOpenData.aspx?$top=50";
        String condArea = intent.getExtras().getString(CDictionary.BK_Area);
        Log.d(CDictionary.Debug_TAG, "get cond1" + condArea);
        String condType = intent.getExtras().getString(CDictionary.BK_Type);
        Log.d(CDictionary.Debug_TAG, "get cond2" + condType);
        switch (condArea) {
            case "全部":
                break;
            case "臺北市":
                url += "&$filter=shelter_name+like+臺北市";
                break;
            case "新北市":
                url += "&$filter=shelter_name+like+新北市";
                break;
            case "基隆市":
                url += "&$filter=shelter_name+like+基隆市";
                break;
            case "宜蘭縣":
                url += "&$filter=shelter_name+like+宜蘭縣";
                break;
            case "桃園縣":
                url += "&$filter=shelter_name+like+桃園縣";
                break;
            case "新竹縣":
                url += "&$filter=shelter_name+like+新竹縣";
                break;
            case "新竹市":
                url += "&$filter=shelter_name+like+新竹市";
                break;
            case "苗栗縣":
                url += "&$filter=shelter_name+like+苗栗縣";
                break;
            case "臺中市":
                url += "&$filter=shelter_name+like+臺中市";
                break;
            case "彰化縣":
                url += "&$filter=shelter_name+like+彰化縣";
                break;
            case "南投縣":
                url += "&$filter=shelter_name+like+南投縣";
                break;
            case "雲林縣":
                url += "&$filter=shelter_name+like+雲林縣";
                break;
            case "嘉義縣":
                url += "&$filter=shelter_name+like+嘉義縣";
                break;
            case "嘉義市":
                url += "&$filter=shelter_name+like+嘉義市";
                break;
            case "臺南市":
                url += "&$filter=shelter_name+like+臺南市";
                break;
            case "高雄市":
                url += "&$filter=shelter_name+like+高雄市";
                break;
            case "屏東縣":
                url += "&$filter=shelter_name+like+屏東縣";
                break;
            case "花蓮縣":
                url += "&$filter=shelter_name+like+花蓮縣";
                break;
            case "臺東縣":
                url += "&$filter=shelter_name+like+臺東縣";
                break;
            case "澎湖縣":
                url += "&$filter=shelter_name+like+澎湖縣";
                break;
            case "金門縣":
                url += "&$filter=shelter_name+like+金門縣";
                break;
            case "連江縣":
                url += "&$filter=shelter_name+like+連江縣";
                break;
            default:
                break;
        }
        if (condArea.equals("全部")) {
            switch (condType) {
                case "全部":
                    break;
                case "狗":
                    url += "&$filter=animal_kind+like+狗";
                    break;
                case "貓":
                    url += "&$filter=animal_kind+like+貓";
                    break;
                default:
                    break;
            }
        } else {
            switch (condType) {
                case "全部":
                    break;
                case "狗":
                    url += "+and+animal_kind+like+狗";
                    break;
                case "貓":
                    url += "+and+animal_kind+like+貓";
                    break;
                default:
                    break;
            }
        }
        Log.d(CDictionary.Debug_TAG, url);

        listview = (ListView) findViewById(R.id.shel_petlist);
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
                .getAsParsed(new TypeToken<ArrayList<ShelterPet>>() {
                             },
                        new ParsedRequestListener<ArrayList<ShelterPet>>() {
                            @Override
                            public void onResponse(ArrayList<ShelterPet> response) {
                                String size = String.format("%d", response.size());
                                Log.d("Debug", size);
                                if (response.size() > 0) {
                                    for (ShelterPet rs : response) {
                                        petlist.add(rs);
                                        Log.d("Debug", rs.getAnimal_id());
                                    }
                                    MaxDataNum = petlist.size(); // 設置最大數據條數
                                    size = String.format("%d", petlist.size());
                                    Log.d("Debug", size);
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
                                    Log.d("Debug", size);

                                    // 加上底部View，注意要放在setAdapter方法前
                                    listview.addFooterView(moreView);
                                    adapter = new ShelterPetListAdapter(ActShelterPetList.this, showlist);
                                    listview.setAdapter(adapter);
                                    // 绑定監聽器
                                    listview.setOnScrollListener(ActShelterPetList.this);

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
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActShelterPetList.this);
                                    dialog.setView(R.layout.nodata_alertdialog);
                                    dialog.setTitle("查無資料");
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ActShelterPetList.this, ActSearchShelter.class);
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
                bundle.putString(CDictionary.BK_animal_id, petlist.get(position).getAnimal_id());
                bundle.putString(CDictionary.BK_animal_kind, petlist.get(position).getAnimal_kind());
                bundle.putString(CDictionary.BK_animal_sex, petlist.get(position).getAnimal_sex());
                bundle.putString(CDictionary.BK_animal_bodytype, petlist.get(position).getAnimal_bodytype());
                bundle.putString(CDictionary.BK_animal_colour, petlist.get(position).getAnimal_colour());
                bundle.putString(CDictionary.BK_animal_age, petlist.get(position).getAnimal_age());
                if(petlist.get(position).getAlbum_file().toLowerCase().endsWith(".jpg") ||
                        petlist.get(position).getAlbum_file().toLowerCase().endsWith(".png")){
                    bundle.putString(CDictionary.BK_album_file, petlist.get(position).getAlbum_file());
                }
                bundle.putString(CDictionary.BK_shelter_name, petlist.get(position).getShelter_name());
                bundle.putString(CDictionary.BK_shelter_address, petlist.get(position).getShelter_address());
                bundle.putString(CDictionary.BK_shelter_tel, petlist.get(position).getShelter_tel());
                bundle.putString(CDictionary.BK_animal_remark, petlist.get(position).getAnimal_remark());
                Intent intent = new Intent(ActShelterPetList.this, ActShelterPetDetail.class);
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
