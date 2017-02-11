package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_input);
        initComponent();

        name = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"");
        Log.d(CDictionary.Debug_TAG,"GET USER NAME："+name);
        id = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
        Log.d(CDictionary.Debug_TAG,"GET USER ID："+id);
    }

    View.OnClickListener btnSubmit_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            AlertDialog dialog = new AlertDialog.Builder(ActMsgInput.this)
                    .setMessage("是否確定送出資料")
                    .setTitle("送出確認")
                    .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String emptyInputField = checkInput();

                            if (emptyInputField.length() > 10) {
                                new AlertDialog.Builder(ActMsgInput.this)
                                        .setMessage(emptyInputField)
                                        .setTitle("欄位未填")
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
            jsonObject.put("msgFrom_userID", "86644d36-0c69-4117-bb75-c500486eea71");
            Log.d(CDictionary.Debug_TAG,"GET msgFrom_userID: "+jsonObject.optString("msgFrom_userID"));
            //jsonObject.put("msgTo_userID", edTxt_msgTo_userID.getText().toString());
            jsonObject.put("msgTo_userID", "86644d36-0c69-4117-bb75-c500486eea71");
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
                        dialog.setTitle("訊息已送出");
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
                dialog.setTitle("訊息傳送失敗");
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
    }

    public String checkInput() {
        String emptyInputField = "尚未填寫以下欄位:\n";
        emptyInputField += edTxt_msgTo_userID.getText().toString().isEmpty() ? "收件人\n" : "";
        emptyInputField += edTxt_msgContent.getText().toString().isEmpty() ? "內文\n" : "";
        return emptyInputField;
    }

    public void initComponent(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(btnSubmit_Click);

        edTxt_msgTo_userID=(EditText)findViewById(R.id.edTxt_msgTo_userID);
        edTxt_msgContent=(EditText)findViewById(R.id.edTxt_msgContent);
    }
    Button btnSubmit;
    EditText edTxt_msgTo_userID, edTxt_msgContent;
}
