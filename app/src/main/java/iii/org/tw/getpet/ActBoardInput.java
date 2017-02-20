package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ActBoardInput extends AppCompatActivity {
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    String animalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_board_input);
        setTitle(Html.fromHtml("<font color='#2d4b44'>新增留言</font>"));
        initComponent();

        UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+UserName);
        UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+access_token);

        Intent intent = getIntent();
        animalid = intent.getExtras().getString(CDictionary.BK_animalID);


    }

    View.OnClickListener btnSubmit_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            AlertDialog dialog = new AlertDialog.Builder(ActBoardInput.this)
                    .setTitle(Html.fromHtml("<font color='#2d4b44'>是否確定送出資料?</font>"))
                    .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String emptyInputField = checkInput();
                            if (emptyInputField.length() > 10) {
                                new AlertDialog.Builder(ActBoardInput.this)
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
            jsonObject.put("boardID", 0);
            Log.d(CDictionary.Debug_TAG,"GET boardID: "+jsonObject.optString("boardID"));
            jsonObject.put("boardTime","");
            Log.d(CDictionary.Debug_TAG,"GET boardTime: "+jsonObject.optString("boardTime"));
            jsonObject.put("board_userID", UserId);
            Log.d(CDictionary.Debug_TAG,"GET board_userID: "+jsonObject.optString("board_userID"));
            jsonObject.put("board_animalID", animalid);
            Log.d(CDictionary.Debug_TAG,"GET board_animalID: "+jsonObject.optString("board_animalID"));
            jsonObject.put("boardContent", edTxt_boardContent.getText().toString());
            Log.d(CDictionary.Debug_TAG,"GET boardContent: "+jsonObject.optString("boardContent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody =  RequestBody.create(Iv_MTyp_JSON,jsonObject.toString());
        Log.d(CDictionary.Debug_TAG,"GET JSON STRING: "+jsonObject.toString());
        Request postRequest = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/boards")
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
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActBoardInput.this);
                        dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>留言已送出</font>"));
                        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goBoardList();
                            }
                        });
                        dialog.create().show();
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"POST FAIL......");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActBoardInput.this);
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

    @Override
    public void finish() {
        super.finish();
    }

    private void goBoardList() {
        Intent intent = new Intent(ActBoardInput.this, ActBoardList.class);
        Bundle bundle = new Bundle();
        bundle.putString(CDictionary.BK_animalID,animalid);
        intent.putExtras(bundle);
        startActivity(intent);
        ActBoardList.iv_ActBoardList.finish();
        finish();
    }

    public String checkInput() {
        String emptyInputField = "尚未填寫以下欄位:\n";
        emptyInputField += edTxt_boardContent.getText().toString().isEmpty() ? "留言內容\n" : "";
        return emptyInputField;
    }

    public void initComponent(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(btnSubmit_Click);

        edTxt_boardContent=(EditText)findViewById(R.id.edTxt_boardContent);

    }
    Button btnSubmit;
    EditText edTxt_boardContent;
}
