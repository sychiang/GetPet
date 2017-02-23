package iii.org.tw.getpet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopt_pair_list);
        //setTitle("iPet 動物資訊");
        Intent intent = getIntent();
        String url = "http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas?$orderby=animalDate desc";
        String condArea = intent.getExtras().getString(CDictionary.BK_Area);
        Log.d(CDictionary.Debug_TAG, "get cond1" + condArea);
        String condKind = intent.getExtras().getString(CDictionary.BK_Kind);
        Log.d(CDictionary.Debug_TAG, "get cond2" + condKind);
        String condType = intent.getExtras().getString(CDictionary.BK_Type);
        Log.d(CDictionary.Debug_TAG, "get cond3" + condType);
        String condSex = intent.getExtras().getString(CDictionary.BK_Sex);
        Log.d(CDictionary.Debug_TAG, "get cond4" + condSex);
        switch (condArea) {
            case "全部":
                break;
            case "臺北市":
                url += "&$filter=animalAddress eq '臺北市'";
                break;
            case "新北市":
                url += "&$filter=animalAddress eq '新北市'";
                break;
            case "基隆市":
                url += "&$filter=animalAddress eq '基隆市'";
                break;
            case "宜蘭縣":
                url += "&$filter=animalAddress eq '宜蘭縣'";
                break;
            case "桃園縣":
                url += "&$filter=animalAddress eq '桃園縣'";
                break;
            case "新竹縣":
                url += "&$filter=animalAddress eq '新竹縣'";
                break;
            case "新竹市":
                url += "&$filter=animalAddress eq '新竹市'";
                break;
            case "苗栗縣":
                url += "&$filter=animalAddress eq '苗栗縣'";
                break;
            case "臺中市":
                url += "&$filter=animalAddress eq '臺中市'";
                break;
            case "彰化縣":
                url += "&$filter=animalAddress eq '彰化縣'";
                break;
            case "南投縣":
                url += "&$filter=animalAddress eq '南投縣'";
                break;
            case "雲林縣":
                url += "&$filter=animalAddress eq '雲林縣'";
                break;
            case "嘉義縣":
                url += "&$filter=animalAddress eq '嘉義縣'";
                break;
            case "嘉義市":
                url += "&$filter=animalAddress eq '嘉義市'";
                break;
            case "臺南市":
                url += "&$filter=animalAddress eq '臺南市'";
                break;
            case "高雄市":
                url += "&$filter=animalAddress eq '高雄市'";
                break;
            case "屏東縣":
                url += "&$filter=animalAddress eq '屏東縣'";
                break;
            case "花蓮縣":
                url += "&$filter=animalAddress eq '花蓮縣'";
                break;
            case "臺東縣":
                url += "&$filter=animalAddress eq '臺東縣'";
                break;
            case "澎湖縣":
                url += "&$filter=animalAddress eq '澎湖縣'";
                break;
            case "金門縣":
                url += "&$filter=animalAddress eq '金門縣'";
                break;
            case "連江縣":
                url += "&$filter=animalAddress eq '連江縣'";
                break;
            default:
                break;
        }
        if (condArea.equals("全部")) {
            Log.d(CDictionary.Debug_TAG,"縣市全部");
            switch (condKind) {
                case "全部":
                    break;
                case "狗":
                    url += "&$filter=animalKind eq '狗'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "貓":
                    url += "&$filter=animalKind eq '貓'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "老鼠":
                    url += "&$filter=animalKind eq '老鼠'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "鳥":
                    url += "&$filter=animalKind eq '鳥'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "兔子":
                    url += "&$filter=animalKind eq '兔子'";
                    if(!condType.equals("全部")){
                        url += "and animalType eq '"+condType+"'";
                    }
                    break;
                case "其他":
                    url += "&$filter=animalKind eq '其他'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                default:
                    break;
            }
        } else {
            Log.d(CDictionary.Debug_TAG,"特定縣市: "+condArea);
            switch (condKind) {
                case "全部":
                    break;
                case "狗":
                    url += " and animalKind eq '狗'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "貓":
                    url += " and animalKind eq '貓'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "老鼠":
                    url += " and animalKind eq '老鼠'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "鳥":
                    url += " and animalKind eq '鳥'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "兔子":
                    url += " and animalKind eq '兔子'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                case "其他":
                    url += " and animalKind eq '其他'";
                    if(!condType.equals("全部")){
                        url += " and animalType eq '"+condType+"'";
                    }
                    break;
                default:
                    break;
            }
        }
        if(condArea.equals("全部") && condKind.equals("全部")){
            switch (condSex) {
                case "全部":
                    break;
                case "公":
                    url += "&$filter=animalGender eq '公'";
                    break;
                case "母":
                    url += "&$filter=animalGender eq '母'";
                    break;
                default:
                    break;
            }
        } else {
            switch (condSex) {
                case "全部":
                    break;
                case "公":
                    url += " and animalGender eq '公'";
                    break;
                case "母":
                    url += " and animalGender eq '母'";
                    break;
                default:
                    break;
            }
        }

        Log.d(CDictionary.Debug_TAG, url);

        progressDialog = ProgressDialog.show(ActAdoptPairList.this, Html.fromHtml("<font color='#2d4b44'>請稍後...</font>"), Html.fromHtml("<font color='#2d4b44'>資料讀取中...</font>"), true);

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

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    });
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
                                    progressDialog.dismiss();
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptPairList.this);
                                    //dialog.setView(R.layout.nodata_alertdialog);
                                    dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>查無資料</font>"));
                                    //dialog.setMessage("查無資料");
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ActAdoptPairList.this, ActSearchAdopt.class);
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
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptPairList.this);
                                //dialog.setView(R.layout.nodata_alertdialog);
                                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>連線錯誤, 請稍後再試</font>"));
                                //dialog.setMessage("查無資料");
                                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ActAdoptPairList.this, ActSearchAdopt.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                dialog.create().show();
                            }
                        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(CDictionary.BK_animalName, petlist.get(position).getAnimalName());
                bundle.putString(CDictionary.BK_animalType, petlist.get(position).getAnimalType());
                bundle.putString(CDictionary.BK_animalGender, petlist.get(position).getAnimalGender());
                bundle.putString(CDictionary.BK_animalColor, petlist.get(position).getAnimalColor());
                String age = String.format("%d",petlist.get(position).getAnimalAge());
                bundle.putString(CDictionary.BK_animalAge, age);
                bundle.putString(CDictionary.BK_animalAddress, petlist.get(position).getAnimalAddress());
                bundle.putString(CDictionary.BK_animalBirth, petlist.get(position).getAnimalBirth());
                bundle.putString(CDictionary.BK_animalChip, petlist.get(position).getAnimalChip());
                bundle.putString(CDictionary.BK_animalHealthy, petlist.get(position).getAnimalHealthy());
                bundle.putString(CDictionary.BK_animalDisease_Other, petlist.get(position).getAnimalDisease_Other());
                bundle.putString(CDictionary.BK_animalReason, petlist.get(position).getAnimalReason());
                bundle.putString(CDictionary.BK_animalNote, petlist.get(position).getAnimalNote());
                String animalId = String.format("%d",petlist.get(position).getAnimalID());
                bundle.putString(CDictionary.BK_animalID, animalId);
                bundle.putString(CDictionary.BK_animalOwner_userID, petlist.get(position).getAnimalOwner_userID());
                if(petlist.get(position).getAnimalData_Pic().size()>0){
                    if(petlist.get(position).getAnimalData_Pic().get(0).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
                            petlist.get(position).getAnimalData_Pic().get(0).getAnimalPicAddress().toLowerCase().endsWith(".png"))
                        bundle.putString(CDictionary.BK_animalPicURL1, petlist.get(position).getAnimalData_Pic().get(0).getAnimalPicAddress());
//                    if(petlist.get(position).getAnimalData_Pic().size() >= 2){
//                        if(petlist.get(position).getAnimalData_Pic().get(1).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
//                                petlist.get(position).getAnimalData_Pic().get(1).getAnimalPicAddress().toLowerCase().endsWith(".png")){
//                            bundle.putString(CDictionary.BK_animalPicURL2, petlist.get(position).getAnimalData_Pic().get(1).getAnimalPicAddress());
//                        }
//                    }
//                    if(petlist.get(position).getAnimalData_Pic().size() >= 3){
//                        if(petlist.get(position).getAnimalData_Pic().get(2).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
//                                petlist.get(position).getAnimalData_Pic().get(2).getAnimalPicAddress().toLowerCase().endsWith(".png")){
//                            bundle.putString(CDictionary.BK_animalPicURL3, petlist.get(position).getAnimalData_Pic().get(2).getAnimalPicAddress());
//                        }
//                    }
                }
                if(petlist.get(position).getAnimalData_Condition().size()>0){
                    bundle.putString(CDictionary.BK_conditionAge,petlist.get(position).getAnimalData_Condition().get(0).getConditionAge());
                    bundle.putString(CDictionary.BK_conditionEconomy,petlist.get(position).getAnimalData_Condition().get(0).getConditionEconomy());
                    bundle.putString(CDictionary.BK_conditionHome,petlist.get(position).getAnimalData_Condition().get(0).getConditionHome());
                    bundle.putString(CDictionary.BK_conditionFamily,petlist.get(position).getAnimalData_Condition().get(0).getConditionFamily());
                    bundle.putString(CDictionary.BK_conditionReply,petlist.get(position).getAnimalData_Condition().get(0).getConditionReply());
                    bundle.putString(CDictionary.BK_conditionPaper,petlist.get(position).getAnimalData_Condition().get(0).getConditionPaper());
                    bundle.putString(CDictionary.BK_conditionFee,petlist.get(position).getAnimalData_Condition().get(0).getConditionFee());
                    bundle.putString(CDictionary.BK_conditionOther,petlist.get(position).getAnimalData_Condition().get(0).getConditionOther());
                }

                Intent intent = new Intent(ActAdoptPairList.this, ActAdoptPairDetail.class);
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
            Toast.makeText(this, "數據全部加載完成，沒有更多數據！", Toast.LENGTH_SHORT).show();
        }
    }
}
