package iii.org.tw.getpet;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import common.CDictionary;
import cz.msebera.android.httpclient.Header;
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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class ActAdoptEdit extends AppCompatActivity {
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    Bitmap iv_bitmap_getFromUrl;
    boolean iv_Boolean_判斷是否該觸發發品種列表刷新事件 = false;
    object_petDataForSelfDB iv_object_petDataForSelfDB;

    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalType;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalChip;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalArea;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalGender;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalBirth;
    ArrayAdapter<String> iv_ArrayAdapter_spinner_animalKind;
    //*
    object_ConditionOfAdoptPet iv_object_conditionOfAdoptPet_a;
    //**
    public static ActAdoptEdit page_editPetData;
    public static ActAdoptEdit iv_page_editPetData;
    //***
    OkHttpClient Iv_OkHttp_client = new OkHttpClient();
    public static final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");
    //**
    private static final int REQUEST_READ_STORAGE = 3;
    //*
    static final int requestCodeImgBtn1 = 1001;
    static final int requestCodeImgBtn2 = 1002;
    static final int requestCodeImgBtn3 = 1003;
    static final int requestCodeImgBtn4 = 1004;
    static final int requestCodeImgBtn5 = 1005;
    //*
    static final int iv_requestCodeOfImgBtn1ForCamera = 1006;
    static final int iv_requestCodeOfImgBtn2ForCamera = 1007;
    static final int iv_requestCodeOfImgBtn3ForCamera = 1008;
    static final int iv_requestCodeOfImgBtn4ForCamera = 1009;
    static final int iv_requestCodeOfImgBtn5ForCamera = 10010;
    //*
    int iv_forCountIn塞圖片到imageButton;
    //**
    boolean selectedImgForUpload1 = false;
    boolean selectedImgForUpload2 = false;
    boolean selectedImgForUpload3 = false;
    boolean selectedImgForUpload4 = false;
    boolean selectedImgForUpload5 = false;
    //**
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    Bitmap bitmap4;
    Bitmap bitmap5;
    //***
    object_ConditionOfAdoptPet object_conditionOfAdoptPet;
    //**
    Gson iv_gson;
    //**
    AlertDialog iv_ADialog_a;
    AlertDialog iv_ADialog_b;
    private AlertDialog iv_AlertDialog_Builder;
    //**
    ImageButton[] iv_ImageButtonArray;
    Bitmap[] bitmapArray = {bitmap1, bitmap2, bitmap3, bitmap4, bitmap5};
    boolean[] iv_booleanArray_ImgHasChange = {false, false, false, false, false};
    boolean[] selectedImgForUploadArray = {selectedImgForUpload1, selectedImgForUpload2, selectedImgForUpload3, selectedImgForUpload4, selectedImgForUpload5};
    ArrayList<object_OfPictureImgurSite> iv_ArrayList_object_OfPictureImgurSite;
    ArrayList<object_ConditionOfAdoptPet> iv_ArrayList_object_ConditionOfAdoptPet;
    final String[] area = {"臺北市", "新北市", "基隆市", "宜蘭縣",
            "桃園縣", "新竹縣", "新竹市", "苗栗縣", "臺中市", "彰化縣",
            "南投縣", "雲林縣", "嘉義縣", "嘉義市", "臺南市", "高雄市",
            "屏東縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣"};
    final String[] iv_array_animalGender = {"公", "母"};
    final String[] iv_array_YesOrNO = {"否", "是"};
    private ArrayList<String>[] iv_Array_動物品種清單;
    private ArrayList<String> iv_ArrayList_動物類別清單;
    private ArrayList<Bitmap> iv_ArrayList_Bitmap;

    private ProgressDialog progressDialog = null;
    static ActAdoptEdit iv_ActAdoptEdit;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopt_edit);
        iv_ActAdoptEdit = this;

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    0);
        }

        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE);
        } else {
            //已有權限，可進行檔案存取
        }
        init();
        try {
            fill表單欄位();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //取得使用者基本資料
        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER TOKEN："+access_token);
    }

    private void fill表單欄位() throws ExecutionException, InterruptedException {
        Intent l_intent = getIntent();
        object_petDataForSelfDB l_object_petDataForSelfDB = (object_petDataForSelfDB) l_intent.getSerializableExtra("object_ConditionOfAdoptPet_objA");
        iv_object_petDataForSelfDB = (object_petDataForSelfDB) l_intent.getSerializableExtra("object_ConditionOfAdoptPet_objA");

        edTxt_animalName.setText(l_object_petDataForSelfDB.getAnimalName());
        edTxt_animalDate.setText(l_object_petDataForSelfDB.getAnimalDate());
        edTxt_animalAge.setText(l_object_petDataForSelfDB.getAnimalAge());
        edTxt_animalColor.setText(l_object_petDataForSelfDB.getAnimalColor());
        edTxt_animalHealthy.setText(l_object_petDataForSelfDB.getAnimalHealthy());
        edTxt_animalDisease_Other.setText(l_object_petDataForSelfDB.getAnimalDisease_Other());
        edTxt_animalReason.setText(l_object_petDataForSelfDB.getAnimalReason());
        edTxt_animalNote.setText(l_object_petDataForSelfDB.getAnimalNote());
        Log.d("*********************", l_object_petDataForSelfDB.getAnimalID() + "");

        if (!l_object_petDataForSelfDB.getAnimalAddress().equals(null)) {
            spinner_animalArea.setSelection(iv_ArrayAdapter_spinner_animalArea.getPosition(l_object_petDataForSelfDB.getAnimalAddress().toString()));
        }
        if (!l_object_petDataForSelfDB.getAnimalGender().equals(null)) {
            spinner_animalGender.setSelection(iv_ArrayAdapter_spinner_animalGender.getPosition(l_object_petDataForSelfDB.getAnimalGender().toString()));
        }
        if (!l_object_petDataForSelfDB.getAnimalChip().equals(null)) {
            spinner_animalChip.setSelection(iv_ArrayAdapter_spinner_animalChip.getPosition(l_object_petDataForSelfDB.getAnimalChip().toString()));
        }
        if (!l_object_petDataForSelfDB.getAnimalBirth().equals(null)) {
            spinner_animalBirth.setSelection(iv_ArrayAdapter_spinner_animalBirth.getPosition(l_object_petDataForSelfDB.getAnimalBirth().toString()));
        }
        //*******
        fill塞圖片到imageButton(iv_ImageButtonArray, l_object_petDataForSelfDB);
        //********
    }

    private void fill塞圖片到imageButton(final ImageButton[] p_ImageButtonArray, final object_petDataForSelfDB p_object_petDataForSelfDB) throws ExecutionException, InterruptedException {
        //***********
        iv_ArrayList_Bitmap = new ArrayList<>();
        for (int i = 0; i < bitmapArray.length; i++) {
            bitmapArray[i] = null;
        }
        iv_forCountIn塞圖片到imageButton = 0;
        //final CountDownLatch l_latch = new CountDownLatch(p_object_petDataForSelfDB.getAnimalData_Pic().size());
        //Log.d("++++ length", p_object_petDataForSelfDB.getAnimalData_Pic().size() + "");
        //*
        final CountDownLatch l_CountDownLatch =new CountDownLatch(p_object_petDataForSelfDB.getAnimalData_Pic().size());
        //**

        for (final object_OfPictureImgurSite obj : p_object_petDataForSelfDB.getAnimalData_Pic()) {
            if (obj.getAnimalPicAddress().length() != 0&& iv_forCountIn塞圖片到imageButton<5) {
                //Glide.with(this).load(obj.getAnimalPicAddress()).into(p_ImageButtonArray[iv_forCountIn塞圖片到imageButton]);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(obj.getAnimalPicAddress());
                            iv_bitmap_getFromUrl = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //bitmapArray[iv_forCountIn塞圖片到imageButton] = iv_bitmap_getFromUrl;
                                    iv_ArrayList_Bitmap.add(iv_bitmap_getFromUrl);
                                    Log.d("bitmap length", iv_ArrayList_Bitmap.size() + "");
                                    //iv_forCountIn塞圖片到imageButton+=1;
                                    //**
                                    l_CountDownLatch.countDown();
                                    //**

                                }
                            });
                        } catch (IOException e) {
                            System.out.println(e);
                        }

                    }
                });
                thread.start();
                // selectedImgForUploadArray[iv_forCountIn塞圖片到imageButton] = true;
                iv_forCountIn塞圖片到imageButton += 1;
            }
        }
///**
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    l_CountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //**
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < iv_ArrayList_Bitmap.size(); i++) {
                            p_ImageButtonArray[i].setImageBitmap(iv_ArrayList_Bitmap.get(i));
                            Log.d("進入設定圖片",i+"");
                        }
                    }
                });
                //*
            }
        });
        thread2.start();
        //**
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        ///**************
        if ((requestCode == iv_requestCodeOfImgBtn1ForCamera ||
                requestCode == iv_requestCodeOfImgBtn2ForCamera ||
                requestCode == iv_requestCodeOfImgBtn3ForCamera ||
                requestCode == iv_requestCodeOfImgBtn4ForCamera ||
                requestCode == iv_requestCodeOfImgBtn5ForCamera) && resultCode == RESULT_OK) {
            ImageButton l_partTimeImgBtn = (ImageButton) findViewById(R.id.imgBtn1);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //img.setImageBitmap(imageBitmap);
            float mScale = 1;

            //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
            if (imageBitmap.getWidth() >= 480) {
                //判斷縮放比例
                mScale = (float) 480 / imageBitmap.getWidth();
            }

            Matrix mMat = new Matrix();
            mMat.setScale(mScale, mScale);

            imageBitmap = Bitmap.createBitmap(imageBitmap,
                    0,
                    0,
                    imageBitmap.getWidth(),
                    imageBitmap.getHeight(),
                    mMat,
                    false);

            //**check requestCode to decide show image on which button
            switch (requestCode) {
                case iv_requestCodeOfImgBtn1ForCamera:
                    //((ImageButton) findViewById(R.id.imgBtn1)).setImageBitmap(imageBitmap);
                    //selectedImgForUpload1 = true;
                    iv_ImageButtonArray[0].setImageBitmap(imageBitmap);
                    selectedImgForUploadArray[0] = true;
                    bitmapArray[0] = imageBitmap;
                    iv_booleanArray_ImgHasChange[0] = true;
                    Log.d("bitmapArray[0]", "已換成相機圖片");
                    //Toast.makeText(ScrollingActivity.this, selectedImgForUpload1==true? "TrueY":"FalseY", Toast.LENGTH_SHORT).show();
                    break;
                case iv_requestCodeOfImgBtn2ForCamera:
                    iv_ImageButtonArray[1].setImageBitmap(imageBitmap);
                    selectedImgForUploadArray[1] = true;
                    bitmapArray[1] = imageBitmap;
                    iv_booleanArray_ImgHasChange[1] = true;

                    //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn2)", Toast.LENGTH_SHORT).show();
                    break;
                case iv_requestCodeOfImgBtn3ForCamera:
                    iv_ImageButtonArray[2].setImageBitmap(imageBitmap);
                    selectedImgForUploadArray[2] = true;
                    bitmapArray[2] = imageBitmap;
                    iv_booleanArray_ImgHasChange[2] = true;

                    //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn3)", Toast.LENGTH_SHORT).show();
                    break;
                case iv_requestCodeOfImgBtn4ForCamera:
                    iv_ImageButtonArray[3].setImageBitmap(imageBitmap);
                    selectedImgForUploadArray[3] = true;
                    bitmapArray[3] = imageBitmap;
                    iv_booleanArray_ImgHasChange[3] = true;

                    //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn4)", Toast.LENGTH_SHORT).show();
                    break;
                case iv_requestCodeOfImgBtn5ForCamera:
                    iv_ImageButtonArray[4].setImageBitmap(imageBitmap);
                    selectedImgForUploadArray[4] = true;
                    bitmapArray[4] = imageBitmap;
                    iv_booleanArray_ImgHasChange[4] = true;
                    //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn5)", Toast.LENGTH_SHORT).show();
                    break;
            }
            //**
        }
        //******如果是彈跳視窗的回應********************************
        if (resultCode == CDictionary.IntentRqCodeOfPetAdoptCondition) {
            iv_object_conditionOfAdoptPet_a =
                    (object_ConditionOfAdoptPet) data.getSerializableExtra("l_object_ConditionOfAdoptPet_objA");
            //**
            Log.d("test", iv_object_conditionOfAdoptPet_a.toString());
            //*
        }
        //***********如果是圖片按鈕的回應************************
        if ((requestCode == requestCodeImgBtn1 ||
                requestCode == requestCodeImgBtn2 ||
                requestCode == requestCodeImgBtn3 ||
                requestCode == requestCodeImgBtn4 ||
                requestCode == requestCodeImgBtn5) && resultCode == RESULT_OK) {
            //****
            Bitmap mScaleBitmap = null;
            ///*************
            Log.d("OK", "OK");
            //取得圖檔的路徑位置
            Uri uri = data.getData();
            //寫log
            // Log.e("uri", uri.toString());
            //抽象資料的接口
            //Toast.makeText(ScrollingActivity.this,"11",Toast.LENGTH_SHORT).show();
            ContentResolver cr = this.getContentResolver();
            try {
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //取得圖片控制項ImageView
                //ImageButton imageView = (ImageButton) findViewById(R.id.imgBtn1);
                // 將Bitmap設定到ImageView
                //*****************
                float mScale = 1;
                //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
                if (bitmap.getWidth() >= 480) {
                    //判斷縮放比例
                    mScale = (float) 480 / bitmap.getWidth();
                }
                Matrix mMat = new Matrix();
                mMat.setScale(mScale, mScale);
                mScaleBitmap = Bitmap.createBitmap(bitmap,
                        0,
                        0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        mMat,
                        false);
                //***************
                ImageButton imgBtn = (ImageButton) findViewById(R.id.imgBtn1);
                //**check requestCode to decide show image on which button
                switch (requestCode) {
                    case requestCodeImgBtn1:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn1);
                        //selectedImgForUpload1 = true;
                        selectedImgForUploadArray[0] = true;
                        bitmapArray[0] = mScaleBitmap;
                        iv_booleanArray_ImgHasChange[0] = true;
                        //Toast.makeText(ScrollingActivity.this, selectedImgForUpload1==true? "TrueY":"FalseY", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn2:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn2);
                        selectedImgForUploadArray[1] = true;
                        bitmapArray[1] = mScaleBitmap;
                        iv_booleanArray_ImgHasChange[1] = true;
                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn2)", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn3:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn3);
                        selectedImgForUploadArray[2] = true;
                        bitmapArray[2] = mScaleBitmap;
                        iv_booleanArray_ImgHasChange[2] = true;
                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn3)", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn4:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn4);
                        selectedImgForUploadArray[3] = true;
                        bitmapArray[3] = mScaleBitmap;
                        iv_booleanArray_ImgHasChange[3] = true;
                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn4)", Toast.LENGTH_SHORT).show();
                        break;
                    case requestCodeImgBtn5:
                        imgBtn = (ImageButton) findViewById(R.id.imgBtn5);
                        selectedImgForUploadArray[4] = true;
                        bitmapArray[4] = mScaleBitmap;
                        iv_booleanArray_ImgHasChange[4] = true;
                        //Toast.makeText(ScrollingActivity.this, "String.valueOf(R.id.imgBtn5)", Toast.LENGTH_SHORT).show();
                        break;
                }
                //**
                imgBtn.setImageBitmap(mScaleBitmap);
                Log.d("mScaleBitmapSize", "" + mScaleBitmap.getWidth() + "  and " + mScaleBitmap.getHeight());
                Log.d("bitmapSize", "" + bitmap.getWidth() + "  and " + bitmap.getHeight());
                //**
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
    }

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
                                iv_Array_動物品種清單[j - 1].add(l_JSONObject.getString("animalType"));
                            }
                        }
                    }
                    for (int i = 1; i <= iv_ArrayList_動物類別清單.size(); i += 1) {
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
                        iv_ArrayAdapter_spinner_animalKind = new ArrayAdapter<String>(ActAdoptEdit.this, R.layout.spinnercontent_upload, iv_ArrayList_動物類別清單); //selected item will look like a spinner set from XML
                        Log.d("+++++++222+++", iv_ArrayAdapter_spinner_animalKind.toString());
                        iv_ArrayAdapter_spinner_animalKind.setDropDownViewResource(R.layout.spinnercontent_upload);
                        spinner_animalKind.setAdapter(iv_ArrayAdapter_spinner_animalKind);
                        //**
                        if (!iv_object_petDataForSelfDB.getAnimalKind().equals(null)) {
                            Log.d("++++++++", iv_object_petDataForSelfDB.getAnimalKind().toString());
                            // Log.d("++++++++",iv_ArrayAdapter_spinner_animalKind.toString());
                            spinner_animalKind.setSelection(iv_ArrayAdapter_spinner_animalKind.getPosition(iv_object_petDataForSelfDB.getAnimalKind().toString()));
                        }
                        //*
                        spinner_animalKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (iv_Boolean_判斷是否該觸發發品種列表刷新事件) {
                                    iv_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActAdoptEdit.this, R.layout.spinnercontent_upload, iv_Array_動物品種清單[position]);
                                    spinner_animalType.setAdapter(iv_ArrayAdapter_spinner_animalType);
                                }
                                iv_Boolean_判斷是否該觸發發品種列表刷新事件 = true;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        spinner_animalType = (Spinner) findViewById(R.id.spinner_animalType);
                        ArrayAdapter<String> l_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActAdoptEdit.this, R.layout.spinnercontent_upload, iv_Array_動物品種清單[0]); //selected item will look like a spinner set from XML
                        l_ArrayAdapter_spinner_animalType.setDropDownViewResource(R.layout.spinnercontent_upload);
                        spinner_animalType.setAdapter(l_ArrayAdapter_spinner_animalType);


                        if (!iv_object_petDataForSelfDB.getAnimalType().equals(null)) {
                            //*
                            Log.d("顯示品種'", iv_object_petDataForSelfDB.getAnimalType().toString() + "");
                            if(iv_ArrayAdapter_spinner_animalKind.getPosition(iv_object_petDataForSelfDB.getAnimalKind().toString())>0){
                                iv_ArrayAdapter_spinner_animalType = new ArrayAdapter<String>(ActAdoptEdit.this, R.layout.spinnercontent_upload, iv_Array_動物品種清單[iv_ArrayAdapter_spinner_animalKind.getPosition(iv_object_petDataForSelfDB.getAnimalKind().toString())]);
                                spinner_animalType.setAdapter(iv_ArrayAdapter_spinner_animalType);
                                //*
                                Log.d("222222", iv_ArrayAdapter_spinner_animalType.getPosition(iv_object_petDataForSelfDB.getAnimalType().toString()) + "");
                                spinner_animalType.setSelection(iv_ArrayAdapter_spinner_animalType.getPosition(iv_object_petDataForSelfDB.getAnimalType().toString()));
                            }

                        }

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

    private String switch英文的動物類別轉換為中文forString(String p_animalKind) {
        switch (p_animalKind) {
            case "cat":
                p_animalKind = "貓";
                break;
            case "dog":
                p_animalKind = "狗";
                break;
            case "bird":
                p_animalKind = "鳥";
                break;
            case "OTHER":
                p_animalKind = "其他";
                break;
            case "rabbit":
                p_animalKind = "兔子";
                break;
            case "mice":
                p_animalKind = "老鼠";
                break;
        }
        return p_animalKind;
    }

    private void init() {
        Factory_DynamicAnimalTypeListCreator("");
        iv_int_countHowManyPicNeedUpload = 0;
        iv_ArrayList_object_ConditionOfAdoptPet = new ArrayList<>();
        iv_ArrayList_object_OfPictureImgurSite = new ArrayList<>();
        iv_gson = new Gson();
        setViewComponent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setViewComponent() {
        //**********
        spinner_animalArea = (Spinner) findViewById(R.id.spinner_animalArea);
        iv_ArrayAdapter_spinner_animalArea = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, area); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalArea.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalArea.setAdapter(iv_ArrayAdapter_spinner_animalArea);
        //**
        spinner_animalGender = (Spinner) findViewById(R.id.spinner_animalGender);
        iv_ArrayAdapter_spinner_animalGender = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, iv_array_animalGender); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalGender.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalGender.setAdapter(iv_ArrayAdapter_spinner_animalGender);
        //**
        spinner_animalBirth = (Spinner) findViewById(R.id.spinner_animalBirth);
        iv_ArrayAdapter_spinner_animalBirth = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, iv_array_YesOrNO); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalBirth.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalBirth.setAdapter(iv_ArrayAdapter_spinner_animalBirth);
        //**
        spinner_animalChip = (Spinner) findViewById(R.id.spinner_animalChip);
        iv_ArrayAdapter_spinner_animalChip = new ArrayAdapter<String>(this, R.layout.spinnercontent_upload, iv_array_YesOrNO); //selected item will look like a spinner set from XML
        iv_ArrayAdapter_spinner_animalChip.setDropDownViewResource(R.layout.spinnercontent_upload);
        spinner_animalChip.setAdapter(iv_ArrayAdapter_spinner_animalChip);
        //**********
        edTxt_animalAge = (EditText) findViewById(R.id.edTxt_animalAge);
        edTxt_animalColor = (EditText) findViewById(R.id.edTxt_animalColor);
        edTxt_animalDate = (EditText) findViewById(R.id.edTxt_animalDate);
        edTxt_animalDisease_Other = (EditText) findViewById(R.id.edTxt_animalDisease_Other);
        edTxt_animalHealthy = (EditText) findViewById(R.id.edTxt_animalHealthy);
        edTxt_animalName = (EditText) findViewById(R.id.edTxt_animalName);
        edTxt_animalNote = (EditText) findViewById(R.id.edTxt_animalNote);
        edTxt_animalReason = (EditText) findViewById(R.id.edTxt_animalReason);
        //*隱藏區****
        edTxt_animalOwner_userID = (EditText) findViewById(R.id.edTxt_animalOwner_userID);
        edTxt_animalOwner_userID.setVisibility(View.GONE);
        //
        edTxt_animalID = (EditText) findViewById(R.id.edTxt_animalID);
        edTxt_animalID.setVisibility(View.GONE);
        //
        edTxt_animalDate = (EditText) findViewById(R.id.edTxt_animalDate);
        edTxt_animalDate.setVisibility(View.GONE);
        //***
        page_editPetData = this;
        //**************
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(btn_click);
        //**************
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(btn_click);
        //**************
        btnConfirmAdopted = (Button) findViewById(R.id.btnConfirmAdopted);
        btnConfirmAdopted.setOnClickListener(btnConfirmAdopted_click);
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
        iv_ImageButtonArray = new ImageButton[]{imgBtn1, imgBtn2, imgBtn3, imgBtn4, imgBtn5};
    }

    View.OnClickListener btnConfirmAdopted_click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActAdoptEdit.this, ActUpdateStatus.class);
            intent.putExtra("object_ConditionOfAdoptPet_objA",iv_object_petDataForSelfDB);
            startActivity(intent);
        }
    };

    public void updateStatus(){

    }

    public String check確認是否欄位都有填寫() {
        //************
        String p_string_未填寫的欄位有哪些 = "尚未填寫以下欄位:\n";
        Log.d("原始長度", p_string_未填寫的欄位有哪些.length() + "");
        p_string_未填寫的欄位有哪些 += spinner_animalKind.getSelectedItem().toString().equals("請選擇") ? "未選種類\n" : "";
        p_string_未填寫的欄位有哪些 += spinner_animalType.getSelectedItem().toString().equals("請選擇") ? "未選品種\n" : "";
        p_string_未填寫的欄位有哪些 += spinner_animalArea.getSelectedItem().toString().equals("請選擇") ? "未選縣市\n" : "";
        p_string_未填寫的欄位有哪些 += spinner_animalGender.getSelectedItem().toString().equals("請選擇") ? "未選性別\n" : "";
        p_string_未填寫的欄位有哪些 += spinner_animalBirth.getSelectedItem().toString().equals("請選擇") ? "未選是否已節育\n" : "";
        p_string_未填寫的欄位有哪些 += spinner_animalChip.getSelectedItem().toString().equals("請選擇") ? "未選是否有晶片\n" : "";

        p_string_未填寫的欄位有哪些 += edTxt_animalName.getText().toString().isEmpty() ? "動物姓名\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalAge.getText().toString().isEmpty() ? "動物年齡\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalColor.getText().toString().isEmpty() ? "動物毛色\n" : "";
        p_string_未填寫的欄位有哪些 += edTxt_animalHealthy.getText().toString().isEmpty() ? "健康狀態\n" : "";

        //*********
//        p_string_未填寫的欄位有哪些 += edTxt_animalName.getText().toString().isEmpty() ? "寵物姓名\n" : "";
//        p_string_未填寫的欄位有哪些 += edTxt_animalAge.getText().toString().isEmpty() ? "寵物年齡\n" : "";
//        //p_string_未填寫的欄位有哪些 += edTxt_animalChip.getText().toString().isEmpty() ? "是否植入晶片\n" : "";
//        p_string_未填寫的欄位有哪些 += edTxt_animalHealthy.getText().toString().isEmpty() ? "健康狀態\n" : "";
//        p_string_未填寫的欄位有哪些 += spinner_animalArea.getSelectedItem().toString().equals("全部") ? "未選縣市\n" : "";
//        p_string_未填寫的欄位有哪些 += edTxt_animalColor.getText().toString().isEmpty() ? "毛色\n" : "";
//        //p_string_未填寫的欄位有哪些 += edTxt_animalDate.getText().toString().isEmpty() ? "送養日期\n" : "";
//        p_string_未填寫的欄位有哪些 += edTxt_animalReason.getText().toString().isEmpty() ? "送養理由\n" : "";
        return p_string_未填寫的欄位有哪些;
    }

    private void delete資料(String p_animalId) {
        Request request = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/animalDatas/" + p_animalId)
                .addHeader("Content-Type", "raw")
                .addHeader("Authorization","Bearer "+access_token)
                .delete()
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
                //parseJson(json);

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
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
        l_PetData_PetObj.setAnimalDate(edTxt_animalDate.getText().toString());
        l_PetData_PetObj.setAnimalDisease_Other(edTxt_animalDisease_Other.getText().toString());
        l_PetData_PetObj.setAnimalGender(spinner_animalGender.getSelectedItem().toString());
        l_PetData_PetObj.setAnimalHealthy(edTxt_animalHealthy.getText().toString());
        l_PetData_PetObj.setAnimalName(edTxt_animalName.getText().toString());
        l_PetData_PetObj.setAnimalNote(edTxt_animalNote.getText().toString());
        l_PetData_PetObj.setAnimalReason(edTxt_animalReason.getText().toString());
        l_PetData_PetObj.setAnimalData_Condition(iv_ArrayList_object_ConditionOfAdoptPet);
        l_PetData_PetObj.setAnimalData_Pic(iv_ArrayList_object_OfPictureImgurSite);
        l_PetData_PetObj.setAnimalOwner_userID(iv_object_petDataForSelfDB.getAnimalOwner_userID());
        //****************
        Gson l_gsn_gson = new Gson();
        String l_strPetDataObjToJSONString = l_gsn_gson.toJson(l_PetData_PetObj);
        //***********
        RequestBody requestBody = RequestBody.create(Iv_MTyp_JSON, l_strPetDataObjToJSONString);
        //***************
        Request request = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas")
                .addHeader("Content-Type", "raw")
                .addHeader("Authorization","Bearer "+access_token)
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
                progressDialog.dismiss();
                final String json = response.body().string();
                Log.d("http", json);
                //textView.setText(json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jObj = new JSONObject(json);
                            String id = jObj.getString("animalID");
                            //Toast.makeText(page_editPetData.this, "此筆資料的id: " + id + ")", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        //*******************

    }

    private void uploadImageAndGetSiteBack() throws Exception {
        iv_int_countHowManyPicNeedUpload = 0;
        iv_ADialog_a.dismiss();
        {
            for (int i = 0; i < selectedImgForUploadArray.length; i++) {
                if (selectedImgForUploadArray[i] == true) {
                    iv_int_countHowManyPicNeedUpload += 1;
                }
            }
        }
        Log.d("需要上船的圖片數量",iv_int_countHowManyPicNeedUpload+"");
        //********
        CountDownLatch latch = new CountDownLatch(iv_int_countHowManyPicNeedUpload);//N个工人的协作
        Log.d("", "進入uploadImageAndGetSiteBack");
        for (int i = 0; i < selectedImgForUploadArray.length; i++) {
            if (selectedImgForUploadArray[i] == true) {
                // Toast.makeText(ScrollingActivity.this, selectedImgForUploadArray[i]==true? "True: "+i:"sFalse : "+i, Toast.LENGTH_SHORT).show();
                //Drawable drawable = imgBtnArray[i].getBackground();
                //bitmapArray[i] = ((BitmapDrawable) imgBtnArray[i].getDrawable()).getBitmap();
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

    public String create取得現在時間字串() {
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

            //String urlString = "https://imgur-apiv3.p.mashape.com/3/image/";
            String urlString = "https://api.imgur.com/3/image/";
            //String mashapeKey = ""; //設定自己的 Mashape Key
            String clientId = "d8371f0a27e5085"; //設定自己的 Clinet ID
            String titleString = "GetPet" + strDate; //設定圖片的標題
            SyncHttpClient client0 = new SyncHttpClient();
            //client0.addHeader("X-Mashape-Key", mashapeKey);
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
                }
            });
        }
    }

    private View.OnClickListener btn_click = new View.OnClickListener() {
        int IntentRCodeOfOpenAlbum = 0;
        int l_IntentRCodeOfOpenCamera = 0;

        @Override
        public void onClick(final View v) {

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
                case R.id.btnEdit:
                    iv_ADialog_a = new AlertDialog.Builder(ActAdoptEdit.this)
                            .setMessage(Html.fromHtml("<font color='#2d4b44'>是否確定送出資料</font>"))
                            .setTitle(Html.fromHtml("<font color='#2d4b44'>送出確認</font>"))
                            .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //**
                                    for (Boolean b:iv_booleanArray_ImgHasChange
                                            ) {
                                        Log.d("真假顯示",b.toString());
                                    }
                                    for (int i = 0; i < iv_ArrayList_Bitmap.size(); i += 1) {

                                        if (iv_booleanArray_ImgHasChange[i] == false) {
                                            Log.d("回圈內真假顯示",iv_booleanArray_ImgHasChange[i]+"");
                                            Log.d("I的值",i+"");
                                            bitmapArray[i] = iv_ArrayList_Bitmap.get(i);
                                            selectedImgForUploadArray[i] = true;
                                        }
                                    }
                                    //**
                                    //**
                                    String l_string_未填寫的欄位有哪些 = check確認是否欄位都有填寫();
                                    if (l_string_未填寫的欄位有哪些.length() > 10) {
                                        new AlertDialog.Builder(ActAdoptEdit.this)
                                                .setMessage(Html.fromHtml("<font color='#2d4b44'>"+l_string_未填寫的欄位有哪些+"</font>"))
                                                .setTitle(Html.fromHtml("<font color='#2d4b44'>欄位未填</font>"))
                                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .show();
                                    } else {
                                        try {
                                            progressDialog = ProgressDialog.show(ActAdoptEdit.this, Html.fromHtml("<font color='#2d4b44'>資料傳送中, 請稍後...</font>"), "", true);
                                            delete資料(String.valueOf(iv_object_petDataForSelfDB.getAnimalID()));
                                            uploadImageAndGetSiteBack();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        addAllDataToDBServer();
                                        Intent l_intent = new Intent(ActAdoptEdit.this, ActAdoptUploadList.class);
                                        ActAdoptUploadList.iv_ActAdoptUploadList.finish();
                                        startActivity(l_intent);
                                        finish();
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                case R.id.btnDelete:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptEdit.this);
                    dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>確定要刪除此筆資料?</font>"));
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog = ProgressDialog.show(ActAdoptEdit.this, Html.fromHtml("<font color='#2d4b44'>資料刪除中, 請稍後...</font>"), "", true);
                            delete資料(String.valueOf(iv_object_petDataForSelfDB.getAnimalID()));
                            progressDialog.dismiss();
                            Intent l_intent = new Intent(ActAdoptEdit.this, ActAdoptUploadList.class);
                            ActAdoptUploadList.iv_ActAdoptUploadList.finish();
                            startActivity(l_intent);
                            finish();
                        }
                    });
                    dialog.setNegativeButton("取消", null);
                    dialog.create().show();
                    break;
//                case R.id.btnCamera:
//                    Intent intentCamera = new Intent(
//                            MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
//
//                    startActivity(intentCamera);
//                    break;
                case R.id.btnAdoptCondition:
                    Log.d("123", "btnAdoptCondition");
                    Intent intent = new Intent(ActAdoptEdit.this, ActConditionInput.class);
                    if(iv_object_petDataForSelfDB.getAnimalData_Condition().size()>0)
                        intent.putExtra("l_object_ConditionOfAdoptPet_objA", iv_object_petDataForSelfDB.getAnimalData_Condition().get(0));
                    startActivityForResult(intent, CDictionary.IntentRqCodeOfPetAdoptCondition);
                    break;
            }
//            if (v.getId() == R.id.btnEdit || v.getId() == R.id.btnDelete || v.getId() == R.id.btnCamera) {
//                return;
//            }
            if (v.getId() != R.id.btnAdoptCondition && v.getId() != R.id.btnEdit && v.getId() != R.id.btnDelete && v.getId() != R.id.btnConfirmAdopted) {
                //Toast.makeText(ScrollingActivity.this,String.valueOf(IntentRCodeOfOpenAlbum),Toast.LENGTH_SHORT).show();
                //**
                //**
                iv_AlertDialog_Builder = new AlertDialog.Builder(ActAdoptEdit.this)
                        .setMessage(Html.fromHtml("<font color='#2d4b44'>如欲使用相簿內的相片 請點選相簿\n" +
                                "如欲使用相機直接拍攝 請點擊相機</font>"))
                        .setTitle(Html.fromHtml("<font color='#2d4b44'>請選擇使用相簿或相機</font>"))
                        .setPositiveButton("相簿", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                        })
                        .setNeutralButton("取消", null)
                        .setNegativeButton("相機", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (v.getId()) {
                                    case R.id.imgBtn1:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn1ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn1)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn2:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn2ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn2)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn3:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn3ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn3)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn4:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn4ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn4)",Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.imgBtn5:
                                        l_IntentRCodeOfOpenCamera = iv_requestCodeOfImgBtn5ForCamera;
                                        //Toast.makeText(ScrollingActivity.this,"String.valueOf(R.id.imgBtn5)",Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, l_IntentRCodeOfOpenCamera);
                                }
                            }
                        })
                        .show();
            }
        }
    };
    private int iv_int_countHowManyPicNeedUpload;

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

    ImageButton imgBtn1, imgBtn2, imgBtn3, imgBtn4, imgBtn5;
    Button btnAdoptCondition, btnEdit, btnDelete, btnCamera,btnConfirmAdopted;
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
