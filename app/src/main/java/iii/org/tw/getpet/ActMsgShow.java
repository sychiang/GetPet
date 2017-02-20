package iii.org.tw.getpet;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import common.CDictionary;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActMsgShow extends AppCompatActivity {
    static ActMsgShow iv_ActMsgShow;
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    private String msgID, msgTime, msgFrom_userID, msgFrom_userName, msgTo_userID, msgType, msgFromURL, msgContent, msgRead;
    private  int msg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_show);
        setTitle("訊息內容");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        initComponent();
        Intent intent = getIntent();
        if(intent != null){
            msgFrom_userID = intent.getExtras().getString(CDictionary.BK_msg_fromuserid);
            msgFrom_userName = intent.getExtras().getString(CDictionary.BK_msg_fromusername);
            msgType = intent.getExtras().getString(CDictionary.BK_msg_subject);
            msgContent = intent.getExtras().getString(CDictionary.BK_msg_content);
            msg_id = intent.getExtras().getInt(CDictionary.BK_msg_id);
            Log.d(CDictionary.Debug_TAG,"MSG ID: "+msg_id);

            msg_subject.setText(msgType);
            msg_sender.setText(msgFrom_userName);
            msg_content.setText(msgContent);
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //取得使用者基本資料
        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER TOKEN："+access_token);
    }
    @Override
    public void finish() {
        super.finish();
    }

    View.OnClickListener btnOK_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //finish();
            Intent intent = new Intent(ActMsgShow.this, ActMsgBox.class);
            startActivity(intent);
            ActMsgBox.iv_ActMsgBox.finish();
            finish();
        }
    };

    View.OnClickListener btnReply_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActMsgShow.this,ActMsgInput.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_msg_fromuserid,msgFrom_userID);
            bundle.putString(CDictionary.BK_msg_fromusername,msgFrom_userName);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    View.OnClickListener btnDelete_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Log.d(CDictionary.Debug_TAG,"DEL BTN CLICK");
            AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgShow.this);
            dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>確定要刪除此訊息?</font>"));
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteMsg();
                }
            });
            dialog.setNegativeButton("取消", null);
            dialog.create().show();
        }
    };

    public  void deleteMsg(){
        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

        Request postRequest = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/MsgUsers/"+msg_id)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+access_token)
                .delete()
                .build();

        Call call = Iv_OkHttp_client.newCall(postRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d(CDictionary.Debug_TAG,"DELETE RESPONSE BODY: "+json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgShow.this);
                        dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>訊息已刪除</font>"));
                        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ActMsgShow.this,ActMsgBox.class);
                                startActivity(intent);
                                ActMsgBox.iv_ActMsgBox.finish();
                                finish();
                            }
                        });
                        dialog.create().show();
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"POST FAIL......");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgShow.this);
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



    public void initComponent(){
        //**
        iv_ActMsgShow = this;
        //**
        btnOK = (ImageButton)findViewById(R.id.btnOK);
        btnOK.setOnClickListener(btnOK_Click);

        btnReply = (ImageButton)findViewById(R.id.btnReply);
        btnReply.setOnClickListener(btnReply_Click);

        btnDelete = (ImageButton)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(btnDelete_Click);

        msg_subject = (TextView) findViewById(R.id.msg_subject);
        msg_sender = (TextView) findViewById(R.id.msg_sender);
        msg_content = (TextView) findViewById(R.id.msg_content);
    }
    //Button btnOK, btnReply, btnDelete;
    TextView msg_subject, msg_sender, msg_content;

    ImageButton btnOK, btnReply, btnDelete;
}
