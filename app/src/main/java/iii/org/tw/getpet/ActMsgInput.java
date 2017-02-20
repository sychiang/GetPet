package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class ActMsgInput extends AppCompatActivity {
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    private String msgID, msgTime, msgFrom_userID, msgFrom_userName, msgTo_userID, msgType, msgFromURL, msgContent, msgRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_input);
        setTitle(Html.fromHtml("<font color='#2d4b44'>訊息回覆</font>"));
        initComponent();

        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+access_token);

        Intent intent = getIntent();
        if(intent != null){
            msgFrom_userID = intent.getExtras().getString(CDictionary.BK_msg_fromuserid);
            msgFrom_userName = intent.getExtras().getString(CDictionary.BK_msg_fromusername);
            tv_msgTo_userName.setText(msgFrom_userName);
        }

    }

    View.OnClickListener btnSubmit_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            AlertDialog dialog = new AlertDialog.Builder(ActMsgInput.this)
                    .setMessage(Html.fromHtml("<font color='#2d4b44'>是否確定送出資料</font>"))
                    .setTitle(Html.fromHtml("<font color='#2d4b44'>送出確認</font>"))
                    .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String emptyInputField = checkInput();
                            if (emptyInputField.length() > 10) {
                                new AlertDialog.Builder(ActMsgInput.this)
                                        .setMessage(Html.fromHtml("<font color='#2d4b44'>"+emptyInputField+"</font>"))
                                        .setTitle(Html.fromHtml("<font color='#2d4b44'>欄位未填</font>"))
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }else {
                                sendRequestToServer();
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    };

    public void sendRequestToServer(){
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
            jsonObject.put("msgTo_userID", msgFrom_userID);
            Log.d(CDictionary.Debug_TAG,"GET msgTo_userID: "+jsonObject.optString("msgTo_userID"));
            jsonObject.put("msgType", "站內信");
            Log.d(CDictionary.Debug_TAG,"GET msgType: "+jsonObject.optString("msgType"));
            jsonObject.put("msgFromURL", "nil");
            Log.d(CDictionary.Debug_TAG,"GET msgFromURL: "+jsonObject.optString("msgFromURL"));
            jsonObject.put("msgContent", edTxt_msgContent.getText().toString());
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
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgInput.this);
                        dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>訊息已送出</font>"));
                        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goMsgBox();
                            }
                        });
                        dialog.create().show();
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"POST FAIL......");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgInput.this);
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

    private void goMsgBox() {
        Intent intent = new Intent(ActMsgInput.this, ActMsgBox.class);
        startActivity(intent);
        ActMsgBox.iv_ActMsgBox.finish();
        ActMsgShow.iv_ActMsgShow.finish();
        finish();
    }

    public String checkInput() {
        String emptyInputField = "尚未填寫以下欄位:\n";
        emptyInputField += edTxt_msgContent.getText().toString().isEmpty() ? "內文\n" : "";
        return emptyInputField;
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void initComponent(){
        btnSubmit = (ImageButton)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(btnSubmit_Click);

        edTxt_msgContent=(EditText)findViewById(R.id.edTxt_msgContent);

        tv_msgTo_userName=(TextView)findViewById(R.id.tv_msgTo_userName);
    }
    //Button btnSubmit;
    EditText edTxt_msgTo_userID, edTxt_msgContent;
    TextView tv_msgTo_userName;

    ImageButton btnSubmit;
}
