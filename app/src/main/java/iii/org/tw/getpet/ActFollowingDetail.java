package iii.org.tw.getpet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import common.CDictionary;
import cz.msebera.android.httpclient.Header;
import model.AdoptPair;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActFollowingDetail extends AppCompatActivity {
    ArrayList<AdoptPair> petlist = new ArrayList<AdoptPair>();
    String url = "http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas";
    String animalid = "";
    LayoutInflater inflater;
    Context mContext = ActFollowingDetail.this;
    AdoptPair adoptpair;

    Gson gson = new Gson();
    AsyncHttpClient httpclient = new AsyncHttpClient();
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_following_detail);
        setTitle("iPet 幸福轉運站");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();

        //取得使用者基本資料
        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER TOKEN："+access_token);

        //取回JSON資料
        Intent intent = getIntent();
        if(intent.getExtras().containsKey(CDictionary.BK_animalID_following)){
            animalid = intent.getExtras().getString(CDictionary.BK_animalID_following);
            Log.d(CDictionary.Debug_TAG, "GET ANIMAL ID: " + animalid);
            url += "/"+animalid;
            Log.d(CDictionary.Debug_TAG, "GET URL: " + url);
        }

        Log.d(CDictionary.Debug_TAG, "GET URL: " + url);
        httpclient.get(ActFollowingDetail.this, url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String responsestr = new String(responseBody);
                        Log.d(CDictionary.Debug_TAG,"GET RESPONSE BODY: "+responsestr);
                        adoptpair = gson.fromJson(responsestr,AdoptPair.class);
                        Log.d(CDictionary.Debug_TAG,"GET PET ID: "+adoptpair.getAnimalID());
                        if (adoptpair != null ) {
                            //圖片處理
                            if(adoptpair.getAnimalData_Pic().size()>0){
                                if(adoptpair.getAnimalData_Pic().get(0).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
                                        adoptpair.getAnimalData_Pic().get(0).getAnimalPicAddress().toLowerCase().endsWith(".png")){
                                    Glide.with(mContext).load(adoptpair.getAnimalData_Pic().get(0).getAnimalPicAddress()).into(ivPhotoOne);
                                } else {
                                    ivPhotoOne.setImageResource(R.drawable.default_photo);
                                }
                                if(adoptpair.getAnimalData_Pic().size() >= 2){
                                    if(adoptpair.getAnimalData_Pic().get(1).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
                                            adoptpair.getAnimalData_Pic().get(1).getAnimalPicAddress().toLowerCase().endsWith(".png")){
                                        Glide.with(mContext).load(adoptpair.getAnimalData_Pic().get(1).getAnimalPicAddress()).into(ivPhotoTwo);
                                    } else {
                                    }
                                } else {
                                    ivPhotoTwo.setImageResource(R.drawable.default_photo);
                                }
                                if(adoptpair.getAnimalData_Pic().size() >= 3){
                                    if(adoptpair.getAnimalData_Pic().get(2).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
                                            adoptpair.getAnimalData_Pic().get(2).getAnimalPicAddress().toLowerCase().endsWith(".png")){
                                        Glide.with(mContext).load(adoptpair.getAnimalData_Pic().get(2).getAnimalPicAddress()).into(ivPhotoThree);
                                    } else {
                                    }
                                } else {
                                    ivPhotoThree.setImageResource(R.drawable.default_photo);
                                }
                            }

                            tvName.setText(adoptpair.getAnimalName());
                            tvType.setText(adoptpair.getAnimalType());
                            tvSex.setText(adoptpair.getAnimalGender());
                            tvColor.setText(adoptpair.getAnimalColor());
                            tvAge.setText(String.format("%d",adoptpair.getAnimalAge()));
                            tvArea.setText(adoptpair.getAnimalAddress());
                            tvIfBirth.setText(adoptpair.getAnimalBirth());
                            tvIfChip.setText(adoptpair.getAnimalChip());
                            tvHealthy.setText(adoptpair.getAnimalHealthy());
                            tvDisease.setText(adoptpair.getAnimalDisease_Other());
                            tvReason.setText(adoptpair.getAnimalReason());
                            tvNote.setText(adoptpair.getAnimalNote());

                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActFollowingDetail.this);
                            //dialog.setView(R.layout.nodata_alertdialog);
                            dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>查無資料</font>"));
                            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ActFollowingDetail.this, ActFollowingList.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            dialog.create().show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActFollowingDetail.this);
                        dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>連線錯誤, 請稍後再試</font>"));
                        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ActFollowingDetail.this, ActFollowingList.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialog.create().show();
                    }
                });

        //輪播功能
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,android.R.anim.fade_out);
        viewFlipper.setAnimation(fade_in);
        viewFlipper.setAnimation(fade_out);
        //sets auto flipping
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
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

    public View showConditionView(){
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.showcondition_alertdialog, null);

        tv_condAge = (TextView) view.findViewById(R.id.tv_condAge);
        tv_condEconomy = (TextView) view.findViewById(R.id.tv_condEconomy);
        tv_condHome = (TextView) view.findViewById(R.id.tv_condHome);
        tv_condFamily = (TextView) view.findViewById(R.id.tv_condFamily);
        tv_condReply = (TextView) view.findViewById(R.id.tv_condReply);
        tv_condPaper = (TextView) view.findViewById(R.id.tv_condPaper);
        tv_condFee = (TextView) view.findViewById(R.id.tv_condFee);
        tv_condOther = (TextView) view.findViewById(R.id.tv_condOther);

        if(adoptpair.getAnimalData_Condition().size()>0){
            Log.d(CDictionary.Debug_TAG,"GET COND: "+adoptpair.getAnimalData_Condition().get(0).getConditionID());
            tv_condAge.setText(adoptpair.getAnimalData_Condition().get(0).getConditionAge());
            tv_condEconomy.setText(adoptpair.getAnimalData_Condition().get(0).getConditionEconomy());
            tv_condHome.setText(adoptpair.getAnimalData_Condition().get(0).getConditionHome());
            tv_condFamily.setText(adoptpair.getAnimalData_Condition().get(0).getConditionFamily());
            tv_condReply.setText(adoptpair.getAnimalData_Condition().get(0).getConditionReply());
            tv_condPaper.setText(adoptpair.getAnimalData_Condition().get(0).getConditionPaper());
            tv_condFee.setText(adoptpair.getAnimalData_Condition().get(0).getConditionFee());
            tv_condOther.setText(adoptpair.getAnimalData_Condition().get(0).getConditionOther());
        }
        return view;
    }

    //查看認養條件
    View.OnClickListener btnCheckCond_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.showcondition_alertdialog, null);

            tv_condAge = (TextView) view.findViewById(R.id.tv_condAge);
            tv_condEconomy = (TextView) view.findViewById(R.id.tv_condEconomy);
            tv_condHome = (TextView) view.findViewById(R.id.tv_condHome);
            tv_condFamily = (TextView) view.findViewById(R.id.tv_condFamily);
            tv_condReply = (TextView) view.findViewById(R.id.tv_condReply);
            tv_condPaper = (TextView) view.findViewById(R.id.tv_condPaper);
            tv_condFee = (TextView) view.findViewById(R.id.tv_condFee);
            tv_condOther = (TextView) view.findViewById(R.id.tv_condOther);

            if(adoptpair.getAnimalData_Condition().size()>0){
                Log.d(CDictionary.Debug_TAG,"GET COND: "+adoptpair.getAnimalData_Condition().get(0).getConditionID());
                tv_condAge.setText(adoptpair.getAnimalData_Condition().get(0).getConditionAge());
                tv_condEconomy.setText(adoptpair.getAnimalData_Condition().get(0).getConditionEconomy());
                tv_condHome.setText(adoptpair.getAnimalData_Condition().get(0).getConditionHome());
                tv_condFamily.setText(adoptpair.getAnimalData_Condition().get(0).getConditionFamily());
                tv_condReply.setText(adoptpair.getAnimalData_Condition().get(0).getConditionReply());
                tv_condPaper.setText(adoptpair.getAnimalData_Condition().get(0).getConditionPaper());
                tv_condFee.setText(adoptpair.getAnimalData_Condition().get(0).getConditionFee());
                tv_condOther.setText(adoptpair.getAnimalData_Condition().get(0).getConditionOther());
            }

            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setView(view);
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.create().show();
        }
    };

    View.OnClickListener btnLeaveMsg_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //開啟留言板
            Intent intent = new Intent(ActFollowingDetail.this,ActBoardList.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_animalID,animalid);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    View.OnClickListener btnAdopt_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //我要認養
            if(access_token == ""){
                Log.d(CDictionary.Debug_TAG,"not log in");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActFollowingDetail.this);
                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>尚未登入</font>"));
                dialog.setMessage(Html.fromHtml("<font color='#2d4b44'>請先登入會員</font>"));
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goLoginScreen();
                    }
                });
                dialog.create().show();
            } else {
                View view = showConditionView();

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setView(view);
                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>以下認養條件是否已詳閱完畢?</font>"));
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendRequestForAdopt();
                    }
                });
                dialog.create().show();
            }
        }
    };

    public void sendRequestForAdopt(){
        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        Log.d(CDictionary.Debug_TAG,"Create JSONObj: "+jsonObject.toString());
        try {
            jsonObject.put("msgID", 0);
            Log.d(CDictionary.Debug_TAG,"GET msgID: "+jsonObject.optString("msgID"));
            jsonObject.put("msgTime","");
            Log.d(CDictionary.Debug_TAG,"GET msgTime: "+jsonObject.optString("msgTime"));
            jsonObject.put("msgFrom_userID", UserId);
            Log.d(CDictionary.Debug_TAG,"GET msgFrom_userID: "+jsonObject.optString("msgFrom_userID"));
            jsonObject.put("msgTo_userID", adoptpair.getAnimalOwner_userID());
            //jsonObject.put("msgTo_userID", "86644d36-0c69-4117-bb75-c500486eea71");
            Log.d(CDictionary.Debug_TAG,"GET msgTo_userID: "+jsonObject.optString("msgTo_userID"));
            jsonObject.put("msgType", "認養通知");
            Log.d(CDictionary.Debug_TAG,"GET msgType: "+jsonObject.optString("msgType"));
            jsonObject.put("msgFromURL", "nil");
            Log.d(CDictionary.Debug_TAG,"GET msgFromURL: "+jsonObject.optString("msgFromURL"));
            jsonObject.put("msgContent", "您好 我想認養你的寵物");
            Log.d(CDictionary.Debug_TAG,"GET msgContent: "+jsonObject.optString("msgContent"));
            jsonObject.put("msgRead", "未讀");
            Log.d(CDictionary.Debug_TAG,"GET msgRead: "+jsonObject.optString("msgRead"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody =  RequestBody.create(Iv_MTyp_JSON,jsonObject.toString());
        Log.d(CDictionary.Debug_TAG,"GET JSON STRING: "+jsonObject.toString());
        Request postRequest = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/MsgUsers")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+access_token)
                .post(requestBody)
                .build();

        Call call = Iv_OkHttp_client.newCall(postRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d(CDictionary.Debug_TAG,"GET RESPONSE BODY: "+json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActFollowingDetail.this);
                        dialog.setMessage(Html.fromHtml("<font color='#2d4b44'>系統已為您送出認養通知給送養人</font>"));
                        dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>認養通知已送出</font>"));
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
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"POST FAIL......");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActFollowingDetail.this);
                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>連線錯誤, 請稍後再試</font>"));
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create().show();
            }
        });
    }

    private void goLoginScreen() {
        Intent intent = new Intent(ActFollowingDetail.this, ActLogin.class);
        startActivity(intent);
        finish();
    }

    private void initComponent(){
        tvName = (TextView)findViewById(R.id.tvName);
        tvType = (TextView)findViewById(R.id.tvType);
        tvSex = (TextView)findViewById(R.id.tvSex);
        tvColor = (TextView)findViewById(R.id.tvColor);
        tvAge = (TextView)findViewById(R.id.tvAge);
        tvArea = (TextView)findViewById(R.id.tvArea);
        tvIfBirth = (TextView)findViewById(R.id.tvIfBirth);
        tvIfChip = (TextView)findViewById(R.id.tvIfChip);
        tvHealthy = (TextView)findViewById(R.id.tvHealthy);
        tvDisease = (TextView)findViewById(R.id.tvDisease);
        tvReason = (TextView)findViewById(R.id.tvReason);
        tvNote = (TextView)findViewById(R.id.tvNote);
        ivPhotoOne = (ImageView)findViewById(R.id.ivPhotoOne);
        ivPhotoTwo = (ImageView)findViewById(R.id.ivPhotoTwo);
        ivPhotoThree = (ImageView)findViewById(R.id.ivPhotoThree);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewflipper);

        btnCheckCond = (Button)findViewById(R.id.btnCheckCond);
        btnCheckCond.setOnClickListener(btnCheckCond_Click);

        btnLeaveMsg = (Button)findViewById(R.id.btnLeaveMsg);
        btnLeaveMsg.setOnClickListener(btnLeaveMsg_Click);

        btnAdopt = (Button)findViewById(R.id.btnAdopt);
        btnAdopt.setOnClickListener(btnAdopt_Click);

    }
    Button btnCheckCond,btnLeaveMsg,btnAdopt;
    ImageView ivPhotoOne,ivPhotoTwo,ivPhotoThree;
    TextView tvName, tvType, tvSex, tvColor, tvAge, tvArea,tvIfBirth,tvIfChip,tvHealthy,tvDisease,tvReason,tvNote;
    TextView tv_condAge, tv_condEconomy, tv_condHome, tv_condFamily, tv_condReply, tv_condPaper, tv_condFee, tv_condOther;
    ViewFlipper viewFlipper;
    Animation fade_in,fade_out;

}
