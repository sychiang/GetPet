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

import common.CDictionary;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ActAdoptPairDetail extends AppCompatActivity {
    Intent intent;
    LayoutInflater inflater;
    Context mContext = ActAdoptPairDetail.this;
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    String animalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopt_pair_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        setTitle("找一個家的"+intent.getExtras().getString(CDictionary.BK_animalName));
        animalid = intent.getExtras().getString(CDictionary.BK_animalID);
        initComponent();
        //取得使用者基本資料
        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER TOKEN："+access_token);

        if(intent.getExtras() != null && intent.getExtras().containsKey(CDictionary.BK_animalPicURL1)){
            Glide.with(ActAdoptPairDetail.this).load(intent.getExtras().getString(CDictionary.BK_animalPicURL1)).into(ivPhotoOne);
        }
        if(intent.getExtras() != null && intent.getExtras().containsKey(CDictionary.BK_animalPicURL2)){
            Glide.with(ActAdoptPairDetail.this).load(intent.getExtras().getString(CDictionary.BK_animalPicURL2)).into(ivPhotoTwo);
        }
        if(intent.getExtras() != null && intent.getExtras().containsKey(CDictionary.BK_animalPicURL3)){
            Glide.with(ActAdoptPairDetail.this).load(intent.getExtras().getString(CDictionary.BK_animalPicURL3)).into(ivPhotoThree);
        }

        tvName.setText(intent.getExtras().getString(CDictionary.BK_animalName));
        tvType.setText(intent.getExtras().getString(CDictionary.BK_animalType));
        tvSex.setText(intent.getExtras().getString(CDictionary.BK_animalGender));
        tvColor.setText(intent.getExtras().getString(CDictionary.BK_animalColor));
        tvAge.setText(intent.getExtras().getString(CDictionary.BK_animalAge));
        tvArea.setText(intent.getExtras().getString(CDictionary.BK_animalAddress));
        tvIfBirth.setText(intent.getExtras().getString(CDictionary.BK_animalBirth));
        tvIfChip.setText(intent.getExtras().getString(CDictionary.BK_animalChip));
        tvHealthy.setText(intent.getExtras().getString(CDictionary.BK_animalHealthy));
        tvDisease.setText(intent.getExtras().getString(CDictionary.BK_animalDisease_Other));
        tvReason.setText(intent.getExtras().getString(CDictionary.BK_animalReason));
        tvNote.setText(intent.getExtras().getString(CDictionary.BK_animalNote));

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

    //查看認養條件
    View.OnClickListener btnCheckCond_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.showcondition_alertdialog, null);
            TextView tv_condAge = (TextView) view.findViewById(R.id.tv_condAge);
            TextView tv_condEconomy = (TextView) view.findViewById(R.id.tv_condEconomy);
            TextView tv_condHome = (TextView) view.findViewById(R.id.tv_condHome);
            TextView tv_condFamily = (TextView) view.findViewById(R.id.tv_condFamily);
            TextView tv_condReply = (TextView) view.findViewById(R.id.tv_condReply);
            TextView tv_condPaper = (TextView) view.findViewById(R.id.tv_condPaper);
            TextView tv_condFee = (TextView) view.findViewById(R.id.tv_condFee);
            TextView tv_condOther = (TextView) view.findViewById(R.id.tv_condOther);

            if(intent.getExtras().containsKey(CDictionary.BK_conditionAge))
                tv_condAge.setText(intent.getExtras().getString(CDictionary.BK_conditionAge));
            if(intent.getExtras().containsKey(CDictionary.BK_conditionEconomy))
                tv_condEconomy.setText(intent.getExtras().getString(CDictionary.BK_conditionEconomy));
            if(intent.getExtras().containsKey(CDictionary.BK_conditionHome))
                tv_condHome.setText(intent.getExtras().getString(CDictionary.BK_conditionHome));
            if(intent.getExtras().containsKey(CDictionary.BK_conditionFamily))
                tv_condFamily.setText(intent.getExtras().getString(CDictionary.BK_conditionFamily));
            if(intent.getExtras().containsKey(CDictionary.BK_conditionReply))
                tv_condReply.setText(intent.getExtras().getString(CDictionary.BK_conditionReply));
            if(intent.getExtras().containsKey(CDictionary.BK_conditionPaper))
                tv_condPaper.setText(intent.getExtras().getString(CDictionary.BK_conditionPaper));
            if(intent.getExtras().containsKey(CDictionary.BK_conditionFee))
                tv_condFee.setText(intent.getExtras().getString(CDictionary.BK_conditionFee));
            if(intent.getExtras().containsKey(CDictionary.BK_conditionOther))
                tv_condOther.setText(intent.getExtras().getString(CDictionary.BK_conditionOther));

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
    //留言板
    View.OnClickListener btnLeaveMsg_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActAdoptPairDetail.this,ActBoardList.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_animalID,animalid);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    //我要追蹤
    View.OnClickListener btnTrack_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //我要追蹤
            OkHttpClient Iv_OkHttp_client = new OkHttpClient();
            final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

            JSONObject jsonObject = new JSONObject();
            Log.d(CDictionary.Debug_TAG,"Create JSONObj: "+jsonObject.toString());
            try {
                jsonObject.put("followID", 0);
                Log.d(CDictionary.Debug_TAG,"GET followID: "+jsonObject.optString("followID"));
                jsonObject.put("follow_userId",UserId);
                Log.d(CDictionary.Debug_TAG,"GET follow_userId: "+jsonObject.optString("follow_userId"));
                jsonObject.put("follow_animalID", intent.getExtras().getString(CDictionary.BK_animalID));
                Log.d(CDictionary.Debug_TAG,"GET follow_animalID: "+jsonObject.optString("follow_animalID"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody requestBody =  RequestBody.create(Iv_MTyp_JSON,jsonObject.toString());
            Log.d(CDictionary.Debug_TAG,"GET JSON STRING: "+jsonObject.toString());
            Request postRequest = new Request.Builder()
                    .url("http://twpetanimal.ddns.net:9487/api/v1/followAnis")
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
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptPairDetail.this);
                            dialog.setTitle("已追蹤");
                            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //goMsgBox();
                                }
                            });
                            dialog.create().show();
                        }
                    });
                }
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(CDictionary.Debug_TAG,"POST FAIL......");
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptPairDetail.this);
//                    dialog.setTitle("訊息傳送失敗");
//                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    dialog.create().show();
                }
            });
        }
    };
    //我要認養
    View.OnClickListener btnAdopt_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //我要認養
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
                jsonObject.put("msgTo_userID", intent.getExtras().getString(CDictionary.BK_animalOwner_userID));
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
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptPairDetail.this);
                            dialog.setMessage("系統已為您送出認養通知給送養人");
                            dialog.setTitle("認養通知已送出");
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
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActAdoptPairDetail.this);
                    dialog.setTitle("認養通知傳送失敗");
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.create().show();
                }
            });
        }
    };

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

        btnTrack = (Button)findViewById(R.id.btnTrack);
        btnTrack.setOnClickListener(btnTrack_Click);

        btnAdopt = (Button)findViewById(R.id.btnAdopt);
        btnAdopt.setOnClickListener(btnAdopt_Click);

    }
    Button btnCheckCond,btnLeaveMsg,btnTrack,btnAdopt;
    ImageView ivPhotoOne,ivPhotoTwo,ivPhotoThree;
    TextView tvName, tvType, tvSex, tvColor, tvAge, tvArea,tvIfBirth,tvIfChip,tvHealthy,tvDisease,tvReason,tvNote;
    ViewFlipper viewFlipper;
    Animation fade_in,fade_out;
    //content_line1,content_line2,content_line3,content_line4,content_line5,
    //content_line6,content_line7,content_line8,content_line9,content_line10;
}
