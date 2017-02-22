package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import common.CDictionary;
import model.object_petDataForSelfDB;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActSearchAdopt extends AppCompatActivity {
    String selectedArea = "";
    String selectedKind = "";
    String selectedType = "";
    String selectedSex = "";
    //**
    private ArrayList<String>[] iv_Array_動物品種清單;
    private ArrayList<String> iv_ArrayList_動物類別清單;
    private ArrayList<Bitmap> iv_ArrayList_Bitmap;
    ArrayList<String> arrayListType;
    //**
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalType;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalKind;
    //**
    object_petDataForSelfDB iv_object_petDataForSelfDB;
    boolean iv_Boolean_判斷是否該觸發發品種列表刷新事件 = false;
    //**

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_adopt);
        initComponent();
        Factory_DynamicAnimalTypeListCreator("");

        //縣市下拉選單
        final String[] area = {"全部","臺北市", "新北市", "基隆市", "宜蘭縣",
                "桃園縣", "新竹縣", "新竹市", "苗栗縣", "台中市", "彰化縣",
                "南投縣", "雲林縣", "嘉義縣", "嘉義市", "臺南市", "高雄市",
                "屏東縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣"};
        //adapter可以使用自訂layout
        ArrayAdapter<String> areaList = new ArrayAdapter<>(ActSearchAdopt.this,
                R.layout.spinnercontent_adopt,
                area);
        spinner_area.setDropDownWidth(500);
        spinner_area.setAdapter(areaList);
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = area[position];
                Log.d(CDictionary.Debug_TAG,"縣市: "+selectedArea);
                //Toast.makeText(MainActivity.this, "你選的是" + lunch[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActSearchAdopt.this);
                dialog.setTitle("請選擇欲查詢的縣市");
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create().show();
            }
        });

        //性別下拉選單
        final String[] sex = {"全部", "公", "母"};
        ArrayAdapter<String> sexList = new ArrayAdapter<>(ActSearchAdopt.this,
                R.layout.spinnercontent,
                sex);
        spinner_sex.setDropDownWidth(500);
        spinner_sex.setAdapter(sexList);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActSearchAdopt.this);
                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>請選擇欲查詢的性別</font>"));
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //搜尋按鈕點擊後事件
    View.OnClickListener btnSearch_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            selectedArea = spinner_area.getSelectedItem().toString();
            Log.d(CDictionary.Debug_TAG,"縣市: "+selectedArea);
            selectedKind = spinner_animalKind.getSelectedItem().toString();
            Log.d(CDictionary.Debug_TAG,"種類: "+selectedKind);
            selectedType = spinner_animalType.getSelectedItem().toString();
            Log.d(CDictionary.Debug_TAG,"品種: "+selectedType);
            selectedSex = spinner_sex.getSelectedItem().toString();
            Log.d(CDictionary.Debug_TAG,"性別: "+selectedSex);

            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_Area, selectedArea);
            bundle.putString(CDictionary.BK_Kind, selectedKind);
            bundle.putString(CDictionary.BK_Type, selectedType);
            bundle.putString(CDictionary.BK_Sex, selectedSex);
            Intent intent = new Intent(ActSearchAdopt.this, ActAdoptPairList.class);
            intent.putExtras(bundle);
            startActivity(intent);
            //finish();
        }
    };

    public void initComponent(){
        btnSearch = (Button)findViewById(R.id.btnSearch);
        //btnSearch = (ImageButton)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(btnSearch_Click);
        spinner_area = (Spinner)findViewById(R.id.spinner_area);
        spinner_type = (Spinner)findViewById(R.id.spinner_type);
        spinner_sex = (Spinner)findViewById(R.id.spinner_sex);
    }
    //******************
    public void Factory_DynamicAnimalTypeListCreator(String p_String_url) {
        OkHttpClient l_okHttpClient_client = new OkHttpClient();

        if ("".equals(p_String_url)) {
            p_String_url = "http://twpetanimal.ddns.net:9487/api/v1/animalData_Type";
        }

        Request request = new Request.Builder()
                .url(p_String_url)
                .addHeader("Content-Type", "raw")
                .get()
                .build();

        Call call = l_okHttpClient_client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONArray l_JSONArray_jObj = null;
                //**
                try {
                    l_JSONArray_jObj = new JSONArray(json);

                    iv_ArrayList_動物類別清單 = new ArrayList<String>();
                    //**
                    iv_ArrayList_動物類別清單.add("全部");
                    //**
                    for (int i = 0; i < l_JSONArray_jObj.length(); i += 1) {
                        JSONObject l_JSONObject = (JSONObject) l_JSONArray_jObj.get(i);
                        if (!iv_ArrayList_動物類別清單.contains(l_JSONObject.getString("animalKind"))) {
                            iv_ArrayList_動物類別清單.add(l_JSONObject.getString("animalKind"));
                            Log.d("l_JSString(animalType)", l_JSONObject.getString("animalType"));
                        }
                    }
                    Log.d("iv_ArrayList_動物類別清單", iv_ArrayList_動物類別清單.toString() + "共" + iv_ArrayList_動物類別清單.size() + "種");
                    iv_Array_動物品種清單 = new ArrayList[iv_ArrayList_動物類別清單.size()];
                    for (int j = 1; j <= iv_ArrayList_動物類別清單.size(); j += 1) {
                        iv_Array_動物品種清單[j - 1] = new ArrayList<String>();
                    }

                    for (int i = 0; i < l_JSONArray_jObj.length(); i += 1) {
                        JSONObject l_JSONObject = (JSONObject) l_JSONArray_jObj.get(i);
                        for (int j = 1; j <= iv_ArrayList_動物類別清單.size(); j += 1) {

                            if (l_JSONObject.getString("animalKind").equals(iv_ArrayList_動物類別清單.get(j - 1)) && !iv_Array_動物品種清單[j - 1].contains(l_JSONObject.getString("animalType"))) {
                                //iv_Array_動物品種清單[j-1].add(l_JSONObject.getString("animalType"));
                                iv_Array_動物品種清單[j - 1].add(l_JSONObject.getString("animalType"));
                            }
                        }
                    }

                    for (int i = 1; i <= iv_ArrayList_動物類別清單.size(); i += 1) {
                        //iv_Array_動物品種清單[i-1].toString();
                        Log.d("第" + i + "份動物品種清單", iv_Array_動物品種清單[i - 1].toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch英文的動物類別轉換為中文(iv_ArrayList_動物類別清單);
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        //****************
                        spinner_animalKind = (Spinner) findViewById(R.id.spinner_animalKind2);
                        ArrayAdapter<String> l_ArrayAdapter_spinner_animalKind = new ArrayAdapter<String>(ActSearchAdopt.this, R.layout.spinnercontent_adopt, iv_ArrayList_動物類別清單); //selected item will look like a spinner set from XML
                        l_ArrayAdapter_spinner_animalKind.setDropDownViewResource(R.layout.spinnercontent_adopt);
                        spinner_animalKind.setAdapter(l_ArrayAdapter_spinner_animalKind);
                        spinner_animalKind.setDropDownWidth(600);
                        spinner_animalKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(iv_ArrayList_動物類別清單.get(position).equals("全部")){
                                    String[] type = {"全部"};
                                    ArrayAdapter<String> l_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActSearchAdopt.this, R.layout.spinnercontent_adopt, type);
                                    spinner_animalType.setAdapter(l_ArrayAdapter_spinner_animalType);
                                } else {
                                    iv_Array_動物品種清單[position].add("全部");
                                    arrayListType = iv_Array_動物品種清單[position];
                                    ArrayAdapter<String> l_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActSearchAdopt.this, R.layout.spinnercontent_adopt, iv_Array_動物品種清單[position]);
                                    l_ArrayAdapter_spinner_animalType.setDropDownViewResource(R.layout.spinnercontent_upload);
                                    spinner_animalType.setAdapter(l_ArrayAdapter_spinner_animalType);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        //****************
                        spinner_animalType = (Spinner) findViewById(R.id.spinner_animalType2);
                        ArrayAdapter<String> l_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActSearchAdopt.this, R.layout.spinnercontent_adopt, iv_Array_動物品種清單[0]); //selected item will look like a spinner set from XML
                        l_ArrayAdapter_spinner_animalType.setDropDownViewResource(R.layout.spinnercontent_adopt);
                        spinner_animalType.setAdapter(l_ArrayAdapter_spinner_animalType);
                    }
                });
            }
        });
    }
    //************
    private void switch英文的動物類別轉換為中文(ArrayList<String> p_ArrayList_動物類別清單) {
        for (int i = 0; i < p_ArrayList_動物類別清單.size(); i += 1) {
            switch (p_ArrayList_動物類別清單.get(i).toLowerCase()) {
                case "cat":
                    p_ArrayList_動物類別清單.set(i, "貓");
                    break;
                case "dog":
                    p_ArrayList_動物類別清單.set(i, "狗");
                    break;
                case "bird":
                    p_ArrayList_動物類別清單.set(i, "鳥");
                    break;
                case "other":
                    p_ArrayList_動物類別清單.set(i, "其他");
                    break;
                case "rabbit":
                    p_ArrayList_動物類別清單.set(i, "兔子");
                    break;
                case "mice":
                    p_ArrayList_動物類別清單.set(i, "老鼠");
                    break;
            }
        }
        Log.d("轉換後類別清單", iv_ArrayList_動物類別清單.toString());
    }
    //**

    Button btnSearch;
    //ImageButton btnSearch;
    Spinner spinner_area, spinner_type, spinner_sex;
    //**
    Spinner spinner_animalArea, spinner_animalKind, spinner_animalType, spinner_animalGender, spinner_animalChip, spinner_animalBirth;
    //*
}
