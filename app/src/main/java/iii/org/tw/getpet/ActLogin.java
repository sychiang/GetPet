package iii.org.tw.getpet;

import android.app.ProgressDialog;
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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import common.CDictionary;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ActLogin extends AppCompatActivity {

    SharedPreferences userInfo;
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;

    OkHttpClient Iv_OkHttp_client = new OkHttpClient();
    public static final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initComponent();
        userInfo = getSharedPreferences("userInfo",MODE_PRIVATE);
        if(userInfo.getString(CDictionary.SK_token,"") != ""){
            input_account.setVisibility(View.GONE);
            input_password.setVisibility(View.GONE);
            btn_login.setVisibility(View.GONE);

            tv_username.setVisibility(View.VISIBLE);
            tv_username.setText("Hi, "+userInfo.getString(CDictionary.SK_username,"")+"\n您已登入本系統");
            btn_logout.setVisibility(View.VISIBLE);
        }

    }

    public String checkInput() {
        String emptyInputField = "以下欄位不可為空:\n";
        emptyInputField += input_account.getText().toString().isEmpty() ? "帳號\n" : "";
        emptyInputField += input_password.getText().toString().isEmpty() ? "密碼\n" : "";
        return emptyInputField;
    }

    private void goMainScreen() {
        Intent intent = new Intent(ActLogin.this, ActHomePage.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    View.OnClickListener btn_register_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActLogin.this, ActRegister.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener btn_logout_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            userInfo.edit().clear().commit();
            AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
            dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>您已登出系統</font>"));
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goMainScreen();
                }
            });
            dialog.create().show();
        }
    };

    View.OnClickListener btn_login_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            String emptyInputField = checkInput();
            if (emptyInputField.length() > 10) {
                new AlertDialog.Builder(ActLogin.this)
                        .setMessage(Html.fromHtml("<font color='#2d4b44'>"+emptyInputField+"</font>"))
                        .setTitle(Html.fromHtml("<font color='#2d4b44'>欄位未填</font>"))
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }else {
                progressDialog = ProgressDialog.show(ActLogin.this, Html.fromHtml("<font color='#2d4b44'>資料傳送中, 請稍後...</font>"), "", true);
                requestForToken();
            }
        }
    };

    public void requestForToken(){
        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("txt; charset=utf-8");

        String requestStr = "";
        requestStr += "username="+input_account.getText().toString();
        requestStr += "&password="+input_password.getText().toString();
        requestStr += "&grant_type=password";

        RequestBody requestBody =  RequestBody.create(Iv_MTyp_JSON,requestStr);
        Log.d(CDictionary.Debug_TAG,"POST BODY : "+requestStr);
        Request postRequest = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/token")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .post(requestBody)
                .build();

        Call call = Iv_OkHttp_client.newCall(postRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String reponseMsg = response.body().string();
                Log.d(CDictionary.Debug_TAG,"(REQUEST TOKEN)RESPONSE CODE: "+response.code());
                if(response.code() == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jObj = new JSONObject(reponseMsg);
                                access_token = jObj.getString("access_token");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(access_token != ""){
                                requestForUserInfo();
                            } else{
                                progressDialog.dismiss();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>登入失敗</font>"));
                                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                dialog.create().show();
                            }
                        }
                    });
                } else {

                }

            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"REQUEST FOR TOKEN FAIL......");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                        dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>連線錯誤, 請稍後再試</font>"));
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

    public void requestForUserInfo(){
        OkHttpClient Iv_OkHttp_client = new OkHttpClient();

        Request getRequest = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/Account/UserInfo")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+access_token)
                .get()
                .build();
        Log.d(CDictionary.Debug_TAG,"BEARER TOKEN: "+access_token);

        Call call = Iv_OkHttp_client.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String reponseMsg = response.body().string();
                Log.d(CDictionary.Debug_TAG,"(USERINFO)RESPONSE CODE: "+response.code());
                if(response.code() == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jObj = new JSONObject(reponseMsg);
                                Email = jObj.getString("Email");
                                UserName = jObj.getString("UserName");
                                UserId = jObj.getString("UserId");
                                HasRegistered = jObj.getString("HasRegistered");
                                LoginProvider = jObj.getString("LoginProvider");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                            userInfo.edit().putString(CDictionary.SK_username,input_account.getText().toString())
                                    .putString(CDictionary.SK_token,access_token)
                                    .putString(CDictionary.SK_userid,UserId)
                                    .putString(CDictionary.SK_useremail,Email)
                                    .commit();
                            Log.d(CDictionary.Debug_TAG,"測試暫存 USERNAME: "+getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,""));
                            Log.d(CDictionary.Debug_TAG,"測試暫存 TOKEN: "+getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,""));
                            Log.d(CDictionary.Debug_TAG,"測試暫存 USERID: "+getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,""));
                            Log.d(CDictionary.Debug_TAG,"測試暫存 EMAIL: "+getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_useremail,""));

                            progressDialog.dismiss();

                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                            dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>登入成功</font>"));
                            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    goMainScreen();
                                }
                            });
                            dialog.create().show();

                        }
                    });
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                    dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>登入失敗</font>"));
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.create().show();
                }

            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"GET USERINFO FAIL......");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>登入失敗</font>"));
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
            }
        });

    }

    View.OnClickListener btn_test_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Log.d(CDictionary.Debug_TAG,"TEST BTN CLICK.......");
            Request request = new Request.Builder()
                    .url("http://localhost:59840/api/Account/ExternalLogins?returnUrl=%2f&generateState=true")
                    .addHeader("Content-Type", "text")
                    .get()
                    .build();
            Log.d(CDictionary.Debug_TAG,"REQUEST BUILD.......");

            Call call = Iv_OkHttp_client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(CDictionary.Debug_TAG,"GET RESPONSE FOM OUR SERVER: "+response.body().toString());
                }
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(CDictionary.Debug_TAG,"NO RESPONSE......");
                }
            });
        }
    };

    public void initComponent(){
//        btn_register = (Button)findViewById(R.id.btn_register);
//        btn_register.setOnClickListener(btn_register_Click);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(btn_login_Click);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(btn_logout_Click);

        input_account = (EditText)findViewById(R.id.input_account);
        input_password = (EditText)findViewById(R.id.input_password);

        tv_username = (TextView)findViewById(R.id.tv_username);

//        btn_test = (Button)findViewById(R.id.btn_test);
//        btn_test.setOnClickListener(btn_test_Click);
    }

    Button btn_login, btn_logout, btn_register, btn_test;
    EditText input_account, input_password;
    TextView tv_username;

}
