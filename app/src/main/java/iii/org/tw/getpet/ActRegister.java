package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    View.OnClickListener btnSubmit_Click = new View.OnClickListener(){
        public void onClick(View arg0) {
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
    };

    public void sendRequestToServer(){
        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        Log.d(CDictionary.Debug_TAG,"Create JSONObj: "+jsonObject.toString());
        try {
            jsonObject.put("Email", input_email.getText().toString());
            Log.d(CDictionary.Debug_TAG,"GET EMAIL: "+jsonObject.optString("Email"));
            jsonObject.put("Password", input_password.getText().toString());
            Log.d(CDictionary.Debug_TAG,"GET PSWD : "+jsonObject.optString("Password"));
            jsonObject.put("ConfirmPassword", input_cfmpassword.getText().toString());
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
                        Log.d(CDictionary.Debug_TAG,"GET RESPONSE BODY: "+json);
                        requestForToken();
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
        requestStr += "&password="+input_password.getText().toString();
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
                final String reponseMsg = response.body().string();
                Log.d(CDictionary.Debug_TAG,"GET RESPONSE: "+reponseMsg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String access_token = "";
                        try {
                            JSONObject jObj = new JSONObject(reponseMsg);
                            access_token = jObj.getString("access_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(access_token != ""){
                            SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                            String[] strArray = input_email.getText().toString().split("@");
                            userInfo.edit().putString(CDictionary.SK_username,strArray[0])
                                    .putString(CDictionary.SK_token,access_token)
                                    .commit();
                            String name = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"訪客");
                            String access = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
                            Log.d(CDictionary.Debug_TAG,"GET SHAREDPREF: "+name);
                            Log.d(CDictionary.Debug_TAG,"GET SHAREDPREF: "+access);
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActRegister.this);
                            dialog.setTitle("註冊成功");
                            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ActRegister.this, ActHomePage.class);
                                    startActivity(intent);
                                }
                            });
                            dialog.create().show();
                        } else{
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActRegister.this);
                            dialog.setTitle("註冊失敗");
                            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.create().show();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"POST FAIL......");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActRegister.this);
                        dialog.setTitle("註冊失敗");
                        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                });
            }
        });
    }

    public String checkInput() {
        String emptyInputField = "尚未填寫以下欄位:\n";
        emptyInputField += input_email.getText().toString().isEmpty() ? "電子郵件\n" : "";
        emptyInputField += input_password.getText().toString().isEmpty() ? "密碼\n" : "";
        emptyInputField += input_cfmpassword.getText().toString().isEmpty() ? "確認密碼\n" : "";
        return emptyInputField;
    }

    public void initComponent(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(btnSubmit_Click);

        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);
        input_cfmpassword = (EditText)findViewById(R.id.input_cfmpassword);
    }

    EditText input_email,input_password,input_cfmpassword;
    Button btnSubmit;
}