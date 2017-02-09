package iii.org.tw.getpet;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import common.CDictionary;
import model.object_ConditionOfAdoptPet;
import model.object_OfPictureImgurSite;
import model.object_petDataForSelfDB;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class ActAdoptUpload extends AppCompatActivity {
    object_ConditionOfAdoptPet iv_object_conditionOfAdoptPet_a;
    public static ActAdoptUpload scrollingActivity;

    OkHttpClient Iv_OkHttp_client = new OkHttpClient();
    public static final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

    private static final int REQUEST_READ_STORAGE = 3;

    static final int requestCodeImgBtn1 = 1001;
    static final int requestCodeImgBtn2 = 1002;
    static final int requestCodeImgBtn3 = 1003;
    static final int requestCodeImgBtn4 = 1004;
    static final int requestCodeImgBtn5 = 1005;

    boolean selectedImgForUpload1 = false;
    boolean selectedImgForUpload2 = false;
    boolean selectedImgForUpload3 = false;
    boolean selectedImgForUpload4 = false;
    boolean selectedImgForUpload5 = false;

    Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5;
    //***
    object_ConditionOfAdoptPet object_conditionOfAdoptPet;
    //**
    Gson iv_gson;
    //**
    AlertDialog iv_ADialog_a;
    AlertDialog iv_ADialog_b;
    //**
    Bitmap[] bitmapArray = {bitmap1, bitmap2, bitmap3, bitmap4, bitmap5};
    boolean[] selectedImgForUploadArray = {selectedImgForUpload1, selectedImgForUpload2, selectedImgForUpload3, selectedImgForUpload4, selectedImgForUpload5};
    ArrayList<object_OfPictureImgurSite> iv_ArrayList_object_OfPictureImgurSite;
    ArrayList<object_ConditionOfAdoptPet> iv_ArrayList_object_ConditionOfAdoptPet;
    final String[] area = {"臺北市", "新北市", "基隆市", "宜蘭縣",
            "桃園縣", "新竹縣", "新竹市", "苗栗縣", "臺中市", "彰化縣",
            "南投縣", "雲林縣", "嘉義縣", "嘉義市", "臺南市", "高雄市",
            "屏東縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣"};
    final String[] iv_array_animalGender = {"公","母","未知"};
    final String[] iv_array_YesOrNO = {"否","是"};
    private ArrayList<String>[] iv_Array_動物品種清單;
    private ArrayList<String> iv_ArrayList_動物類別清單;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopt_upload);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE);
        } else {
            //已有權限，可進行檔案存取
        }
        init();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private View.OnClickListener btn_click = new View.OnClickListener() {
        int IntentRCodeOfOpenAlbum = 0;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgBtn1:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn1;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn1)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn2:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn2;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn2)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn3:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn3;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn3)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn4:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn4;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn4)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgBtn5:
                    IntentRCodeOfOpenAlbum = requestCodeImgBtn5;
                    //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn5)",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnAdoptCondition:
                    Log.d("123", "btnAdoptCondition");
                    Intent intent = new Intent(ActAdoptUpload.this, ActConditionInput.class);
                    intent.putExtra("l_object_ConditionOfAdoptPet_objA", iv_object_conditionOfAdoptPet_a);
                    startActivityForResult(intent, CDictionary.IntentRqCodeOfPetAdoptCondition);
                    break;
            }

            if (v.getId() != R.id.btnAdoptCondition) {
                //Toast.makeText(ScrollingActivity.this,String.valueOf(IntentRCodeOfOpenAlbum),Toast.LENGTH_SHORT).show();
                //**
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action會開啟選取圖檔視窗讓您選取手機內圖檔
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得相片後返回本畫面
                startActivityForResult(intent, IntentRCodeOfOpenAlbum);
                //**
            }

        }
    };
    private int iv_int_countHowManyPicNeedUpload;

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
                    @Override
                    public void run() {
                        //****************
                        spinner_animalKind = (Spinner) findViewById(R.id.spinner_animalKind);
                        ArrayAdapter<String> l_ArrayAdapter_spinner_animalKind = new ArrayAdapter<String>(ActAdoptUpload.this, R.layout.spinnercontent_upload, iv_ArrayList_動物類別清單); //selected item will look like a spinner set from XML
                        l_ArrayAdapter_spinner_animalKind.setDropDownViewResource(R.layout.spinnercontent_upload);
                        spinner_animalKind.setAdapter(l_ArrayAdapter_spinner_animalKind);
                        spinner_animalKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ArrayAdapter<String> l_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActAdoptUpload.this, R.layout.spinnercontent_upload, iv_Array_動物品種清單[position]);
                                spinner_animalType.setAdapter(l_ArrayAdapter_spinner_animalType);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        //****************
                        spinner_animalType = (Spinner) findViewById(R.id.spinner_animalType);
                        ArrayAdapter<String> l_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActAdoptUpload.this, R.layout.spinnercontent_upload, iv_Array_動物品種清單[0]); //selected item will look like a spinner set from XML
                        l_ArrayAdapter_spinner_animalType.setDropDownViewResource(R.layout.spinnercontent_upload);
                        spinner_animalType.setAdapter(l_ArrayAdapter_spinner_animalType);
                    }
                });
            }
        });
    }

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
                case "reptile":
                    p_ArrayList_動物類別清單.set(i, "蛇");
                    break;
                case "rabbit":
                    p_ArrayList_動物類別清單.set(i, "兔子");
                    break;
                case "mice":
                    p_ArrayList_動物類別清單.set(i, "老鼠");
                    break;
            }
        }
        Log.d(CDictionary.Debug_TAG, "轉換後類別清單: "+iv_ArrayList_動物類別清單.toString());
    }

    private void init() {
        Factory_DynamicAnimalTypeListCreator("");
        iv_int_countHowManyPicNeedUpload = 0;
        iv_ArrayList_object_ConditionOfAdoptPet = new ArrayList<>();
        iv_ArrayList_object_OfPictureImgurSite = new ArrayList<>();
        iv_gson = new Gson();
        setViewComponent();
    }

    View.OnClickListener btn_sendout_click=new View.OnClickListener(){
        public void onClick(View arg0) {
            iv_ADialog_a = new AlertDialog.Builder(ActAdoptUpload.this)
                    .setMessage("是否確定送出資料")
                    .setTitle("送出確認")
                    .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String l_string_未填寫的欄位有哪些= check確認是否欄位都有填寫();

                            if (l_string_未填寫的欄位有哪些.length() > 10) {
                                new AlertDialog.Builder(ActAdoptUpload.this)
                                        .setMessage(l_string_未填寫的欄位有哪些)
                                        .setTitle("欄位未填")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }else {
                                try {
                                    uploadImageAndGetSiteBack();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                addAllDataToDBServer();
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    };

    private void setViewComponent() {
        //**********
        spinner_animalArea = (Spinner) findViewById(R.id.spinner_animalArea);
        ArrayAdapter<String> l_ArrayAdapter_spinner_animalArea = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, area); //selected item will look like a spinner set from XML
        l_ArrayAdapter_spinner_animalArea.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalArea.setAdapter(l_ArrayAdapter_spinner_animalArea);
        //**
        spinner_animalGender = (Spinner) findViewById(R.id.spinner_animalGender);
        ArrayAdapter<String> l_ArrayAdapter_spinner_animalGender = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, iv_array_animalGender); //selected item will look like a spinner set from XML
        l_ArrayAdapter_spinner_animalGender.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalGender.setAdapter(l_ArrayAdapter_spinner_animalGender);
        //**
        spinner_animalBirth = (Spinner) findViewById(R.id.spinner_animalBirth);
        ArrayAdapter<String> l_ArrayAdapter_spinner_animalBirth = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, iv_array_YesOrNO); //selected item will look like a spinner set from XML
        l_ArrayAdapter_spinner_animalBirth.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalBirth.setAdapter(l_ArrayAdapter_spinner_animalBirth);
        //**
        spinner_animalChip = (Spinner) findViewById(R.id.spinner_animalChip);
        ArrayAdapter<String> l_ArrayAdapter_spinner_animalChip = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, iv_array_YesOrNO); //selected item will look like a spinner set from XML
        l_ArrayAdapter_spinner_animalChip.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalChip.setAdapter(l_ArrayAdapter_spinner_animalChip);
        //**********
        edTxt_animalAge = (EditText) findViewById(R.id.edTxt_animalAge);
        edTxt_animalColor = (EditText) findViewById(R.id.edTxt_animalColor);
        //
//        edTxt_animalDate = (EditText) findViewById(R.id.edTxt_animalDate);
//        edTxt_animalDate.setText(create取得現在時間字串());
        //
        edTxt_animalDisease_Other = (EditText) findViewById(R.id.edTxt_animalDisease_Other);
        edTxt_animalHealthy = (EditText) findViewById(R.id.edTxt_animalHealthy);
        edTxt_animalName = (EditText) findViewById(R.id.edTxt_animalName);
        edTxt_animalNote = (EditText) findViewById(R.id.edTxt_animalNote);
        edTxt_animalReason = (EditText) findViewById(R.id.edTxt_animalReason);
        //***
        scrollingActivity = this;
        //**************
        imgBtn1 = (ImageButton) findViewById(R.id.imgBtn1);
        imgBtn1.setOnClickListener(btn_click);
        //**************
        imgBtn2 = (ImageButton) findViewById(R.id.imgBtn2);
        imgBtn2.setOnClickListener(btn_click);
        //**************
        imgBtn3 = (ImageButton) findViewById(R.id.imgBtn3);
        imgBtn3.setOnClickListener(btn_click);
        //**************
        imgBtn4 = (ImageButton) findViewById(R.id.imgBtn4);
        imgBtn4.setOnClickListener(btn_click);
        //**************
        imgBtn5 = (ImageButton) findViewById(R.id.imgBtn5);
        imgBtn5.setOnClickListener(btn_click);
        //**************
        btnAdoptCondition = (Button) findViewById(R.id.btnAdoptCondition);
        btnAdoptCondition.setOnClickListener(btn_click);

        btn_sendout = (Button) findViewById(R.id.btn_sendout);
        btn_sendout.setOnClickListener(btn_sendout_click);
    }

    public String check確認是否欄位都有填寫() {
        String p_string_未填寫的欄位有哪些 = "尚未填寫以下欄位:\n";
        Log.d("原始長度", p_string_未填寫的欄位有哪些.length() + "");
        p_string_未填寫的欄位有哪些 += edTxt_animalName.getText().toString().isEmpty() ? "寵物姓名\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalAge.getText().toString().isEmpty() ? "寵物年齡\n" : "";
        //p_string_未填寫的欄位有哪些 += edTxt_animalChip.getText().toString().isEmpty() ? "是否植入晶片\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalHealthy.getText().toString().isEmpty() ? "健康狀態\n" : "";
        p_string_未填寫的欄位有哪些 += spinner_animalArea.getSelectedItem().toString().equals("全部") ? "未選縣市\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalColor.getText().toString().isEmpty() ? "毛色\n" : "";
        //p_string_未填寫的欄位有哪些 += edTxt_animalDate.getText().toString().isEmpty() ? "送養日期\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalReason.getText().toString().isEmpty() ? "送養理由\n" : "";
        return p_string_未填寫的欄位有哪些;
    }

    private void addAllDataToDBServer() {
        //******判斷使用者是否填寫領養條件
        if (iv_object_conditionOfAdoptPet_a == null) {
            iv_object_conditionOfAdoptPet_a = new object_ConditionOfAdoptPet();
            iv_object_conditionOfAdoptPet_a.createAdefault_object_ConditionOfAdoptPet();
        }

        iv_ArrayList_object_ConditionOfAdoptPet.add(iv_object_conditionOfAdoptPet_a);
        //*********
        object_petDataForSelfDB l_PetData_PetObj = new object_petDataForSelfDB();
        l_PetData_PetObj.setAnimalAddress(spinner_animalArea.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalAge(edTxt_animalAge.getText().toString());
        l_PetData_PetObj.setAnimalKind(spinner_animalKind.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalType(spinner_animalType.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalBirth(spinner_animalBirth.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalChip(spinner_animalChip.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalColor(edTxt_animalColor.getText().toString());
//        l_PetData_PetObj.setAnimalDate(edTxt_animalDate.getText().toString());
        l_PetData_PetObj.setAnimalDisease_Other(edTxt_animalDisease_Other.getText().toString());
        l_PetData_PetObj.setAnimalGender(spinner_animalGender.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalHealthy(edTxt_animalHealthy.getText().toString());
        l_PetData_PetObj.setAnimalName(edTxt_animalName.getText().toString());
        l_PetData_PetObj.setAnimalNote(edTxt_animalNote.getText().toString());
        l_PetData_PetObj.setAnimalReason(edTxt_animalReason.getText().toString());
        l_PetData_PetObj.setAnimalData_Condition(iv_ArrayList_object_ConditionOfAdoptPet);
        l_PetData_PetObj.setAnimalData_Pic(iv_ArrayList_object_OfPictureImgurSite);
        //****************
        Gson l_gsn_gson = new Gson();
        String l_strPetDataObjToJSONString = l_gsn_gson.toJson(l_PetData_PetObj);
        //***********
        RequestBody requestBody = RequestBody.create(Iv_MTyp_JSON, l_strPetDataObjToJSONString);

        //***************
        Request request = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas")
                .addHeader("Content-Type", "raw")
                .post(requestBody)
                .build();

        Call call = Iv_OkHttp_client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("http", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d("http", json);
                //textView.setText(json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jObj = new JSONObject(json);
                            String id = jObj.getString("animalID");
                            Toast.makeText(ActAdoptUpload.this, "上傳成功!(測試用_此次新增資料的id: " + id + ")", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //parseJson(json);
            }
        });
    }

    private void uploadImageAndGetSiteBack() throws Exception {
        iv_int_countHowManyPicNeedUpload = 0;
        iv_ADialog_a.dismiss();
        {
            for (int i = 0; i < selectedImgForUploadArray.length; i++) {

                if (selectedImgForUploadArray[i] == true) {
                    iv_int_countHowManyPicNeedUpload += 1;
                    Toast.makeText(ActAdoptUpload.this, "資料上傳中 請稍後....", Toast.LENGTH_LONG).show();
                }
            }
        }
        CountDownLatch latch = new CountDownLatch(iv_int_countHowManyPicNeedUpload);//N个工人的协作
        Log.d("", "進入uploadImageAndGetSiteBack");
        for (int i = 0; i < selectedImgForUploadArray.length; i++) {
            if (selectedImgForUploadArray[i] == true) {
                // Toast.makeText(ScrollingActivity.this, selectedImgForUploadArray[i]==true? "True: "+i:"sFalse : "+i, Toast.LENGTH_SHORT).show();
                String bitmapStream = transBitmapToStream(bitmapArray[i]);
                //imgurUpload(bitmapStream);
                Log.d(" 進入迴圈", String.valueOf(selectedImgForUploadArray.length));
                uploadImgByCallable l_uploadImgByCallable = new uploadImgByCallable(bitmapStream, latch);
                l_uploadImgByCallable.start();
            }
        }
        latch.await();
        Log.d(" await完畢", " ");
    }

    public String create取得現在時間字串(){

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        return strDate;
    }

    private String transBitmapToStream(Bitmap myBitmap) {
        Bitmap bitmap = myBitmap; //程式寫在後面

        //將 Bitmap 轉為 base64 字串
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        String imageBase64 = Base64.encodeToString(bitmapData, Base64.DEFAULT);
        return imageBase64;
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


    class uploadImgByCallable extends Thread {
        String image;
        CountDownLatch latch;

        public uploadImgByCallable(String p_image, CountDownLatch p_latch) {
            this.image = p_image;
            this.latch = p_latch;
        }

        @Override
        public void run() {
            Log.d(" 進入線程", " 進入線程");
            imgurUploadInClass(image);

        }

        private void imgurUploadInClass(final String image) { //插入圖片
            Log.d(" 進入imgurUpload", " 進入imgurUpload");
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            Date date = new Date();
            String strDate = sdFormat.format(date);
            Log.d(" 1進入imgurUpload", " 進入imgurUpload");

            String urlString = "https://imgur-apiv3.p.mashape.com/3/image";
            String mashapeKey = ""; //設定自己的 Mashape Key
            String clientId = ""; //設定自己的 Clinet ID
            String titleString = "GetPet" + strDate; //設定圖片的標題

            SyncHttpClient client0 = new SyncHttpClient();
            client0.addHeader("X-Mashape-Key", mashapeKey);
            client0.addHeader("Authorization", "Client-ID " + clientId);
            client0.addHeader("Content-Type", "application/x-www-form-urlencoded");

            RequestParams params = new RequestParams();
            params.put("title", titleString);
            params.put("image", image);
            Log.d(" 準備POST", " ");

            client0.post(urlString, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    if (!response.optBoolean("success") || !response.has("data")) {
                        Log.d("editor", "response: " + response.toString());
                        return;
                    }

                    JSONObject data = response.optJSONObject("data");
                    String link = data.optString("link", "");
                    int width = data.optInt("width", 0);
                    int height = data.optInt("height", 0);
                    Log.d("imgSite", link);
                    //**
                    object_OfPictureImgurSite l_object_OfPictureImgurSite = new object_OfPictureImgurSite(data.optString("link"));
                    iv_ArrayList_object_OfPictureImgurSite.add(l_object_OfPictureImgurSite);
                    latch.countDown();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject error) {
                    Log.d("上傳圖片失敗", "");
                    Log.d("上傳圖片失敗", "");
                }
            });
        }
    }

    ImageButton imgBtn1, imgBtn2, imgBtn3, imgBtn4, imgBtn5;
    Button btnAdoptCondition, btn_sendout;
    ImageButton[] imgBtnArray = {imgBtn1, imgBtn2, imgBtn3, imgBtn4, imgBtn5};
    //*********************
    EditText edTxt_animalID, edTxt_animalName, edTxt_animalAddress, edTxt_animalDate, edTxt_animalGender,
            edTxt_animalAge, edTxt_animalColor, edTxt_animalBirth, edTxt_animalChip, edTxt_animalHealthy,
            edTxt_animalDisease_Other, edTxt_animalOwner_userID, edTxt_animalReason, edTxt_animalGetter_userID,
            edTxt_animalAdopted, edTxt_animalAdoptedDate, edTxt_animalNote;
    //***********************
    Spinner spinner_animalArea, spinner_animalKind, spinner_animalType, spinner_animalGender, spinner_animalChip, spinner_animalBirth;
    //*******
}
