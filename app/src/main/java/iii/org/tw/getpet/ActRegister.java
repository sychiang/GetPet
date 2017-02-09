package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.AccessToken;
import com.google.gson.Gson;

import org.json.JSONArray;
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

public class ActRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        initComponent();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(ActRegister.this)
                        .setMessage("是否確定送出資料")
                        .setTitle("送出確認")
                        .setPositiveButton("送出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String emptyInputField = checkInput();

                                if (emptyInputField.length() > 10) {
                                    new AlertDialog.Builder(ActRegister.this)
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
        });

    }

    public void sendRequestToServer(){

        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        Log.d(CDictionary.Debug_TAG,"Create JSONObj: "+jsonObject.toString());
        try {
            jsonObject.put("Email", input_email.getText().toString());
            Log.d(CDictionary.Debug_TAG,"GET EMAIL: "+jsonObject.optString("Email"));
            jsonObject.put("Password", input_pswd.getText().toString());
            Log.d(CDictionary.Debug_TAG,"GET PSWD : "+jsonObject.optString("Password"));
            jsonObject.put("ConfirmPassword", input_pswdcfm.getText().toString());
            Log.d(CDictionary.Debug_TAG,"GET PSWDCFM : "+jsonObject.optString("ConfirmPassword"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody =  RequestBody.create(Iv_MTyp_JSON,jsonObject.toString());
                Request postRrequest = new Request.Builder()
                        .url("http://twpetanimal.ddns.net:9487/api/v1/Account/Register")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .post(requestBody)
                        .build();

                Call call = Iv_OkHttp_client.newCall(postRrequest);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String json = response.body().string();
                        Log.d(CDictionary.Debug_TAG,"GET RESPONSE: "+json);
                        //註冊成功後回傳資料 給 "http://twpetanimal.ddns.net:9487/token"要token
                        requestForToken();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    JSONObject jObj = new JSONObject(json);
//                                    String id = jObj.getString("animalID");
//                                    Toast.makeText(ScrollingActivity.this,"上傳成功!(測試用_此次新增資料的id: "+id+")",Toast.LENGTH_SHORT).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(CDictionary.Debug_TAG,"POST FAIL......");
                    }
                });
    }

    public void requestForToken(){

        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("txt; charset=utf-8");

        String requestStr = "";

        requestStr += "username="+input_email.getText().toString();
        requestStr += "&password="+input_pswd.getText().toString();
        requestStr += "&grant_type=password";
        Log.d(CDictionary.Debug_TAG,"GET POST BODY : "+requestStr);

        RequestBody requestBody =  RequestBody.create(Iv_MTyp_JSON,requestStr);
        Request postRrequest = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/token")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .post(requestBody)
                .build();

        Call call = Iv_OkHttp_client.newCall(postRrequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(CDictionary.Debug_TAG,"GET RESPONSE: "+response.body().string());
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    JSONObject jObj = new JSONObject(json);
//                                    String id = jObj.getString("animalID");
//                                    Toast.makeText(ScrollingActivity.this,"上傳成功!(測試用_此次新增資料的id: "+id+")",Toast.LENGTH_SHORT).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"POST FAIL......");
            }
        });
    }

    public String checkInput() {
        String emptyInputField = "尚未填寫以下欄位:\n";
        emptyInputField += input_email.getText().toString().isEmpty() ? "電子郵件\n" : "";
        emptyInputField += input_pswd.getText().toString().isEmpty() ? "密碼\n" : "";
        emptyInputField += input_pswdcfm.getText().toString().isEmpty() ? "確認密碼\n" : "";
        return emptyInputField;
    }

    public void initComponent(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        input_email = (EditText)findViewById(R.id.input_email);
        input_pswd = (EditText)findViewById(R.id.input_pswd);
        input_pswdcfm = (EditText)findViewById(R.id.input_pswdcfm);
    }

    EditText input_email,input_pswd,input_pswdcfm;
    Button btnSubmit;
}
