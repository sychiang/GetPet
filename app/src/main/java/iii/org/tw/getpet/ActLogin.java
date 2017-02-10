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
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.AccessToken;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.accountkit.AccountKit;
import com.google.gson.Gson;

import org.json.JSONArray;
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

    CallbackManager callbackManager;
    AccessToken accessToken;
    SharedPreferences userInfo;
    String fbEmail, fbUsername, fbID;

    OkHttpClient Iv_OkHttp_client = new OkHttpClient();
    public static final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

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
            btn_register.setVisibility(View.GONE);

            tv_username.setVisibility(View.VISIBLE);
            tv_username.setText("Hi, "+userInfo.getString(CDictionary.SK_username,""));
            btn_logout.setVisibility(View.VISIBLE);
        }

        //宣告FB SDK callback Manager
        callbackManager = CallbackManager.Factory.create();

        //找到login button (facebook套件裡的登入按鈕元件)
        LoginButton btn_FBlogin = (LoginButton) findViewById(R.id.btn_FBlogin);
//        btn_FBlogin.setReadPermissions(Arrays.asList(
//                "public_profile", "email", "user_birthday", "user_friends"));    要求存取使用者的各項資料

        btn_FBlogin.setReadPermissions(Arrays.asList("public_profile", "email"));  //要求存取使用者的基本資料&Email

        //LoginButton增加callback function
        btn_FBlogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //成功登入, accessToken之後或許還會用到 先存起來
                accessToken = loginResult.getAccessToken();
                Log.d(CDictionary.Debug_TAG,"FB ACCESS TOKEN  GET: "+accessToken.toString());

                //send request and call graph api
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            //當RESPONSE回來的時候會取得JSON物件
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //讀出姓名 ID FB個人頁面連結
                                Log.d(CDictionary.Debug_TAG,"FB GET RESPONSE"+response);
                                Log.d(CDictionary.Debug_TAG,object.optString("name"));
                                Log.d(CDictionary.Debug_TAG,object.optString("link"));
                                Log.d(CDictionary.Debug_TAG,object.optString("id"));
                                Log.d(CDictionary.Debug_TAG,object.optString("email"));

                                fbID = object.optString("id");
                                fbUsername = object.optString("name");
                                fbEmail = object.optString("email");
                                doRegisterExternal();
                            }
                        });
                //包入想要得到的資料 送出request
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {//取消登入
                Log.d("FB","CANCEL");
            }

            @Override
            public void onError(FacebookException exception) {//登入失敗
                Log.d("FB",exception.toString());
            }
        });

        //Check if user is currently logged in
        if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null){
            //登入狀態下不給登出
            //btn_FBlogin.setVisibility(View.INVISIBLE);
        }
    }

    public void doRegisterExternal(){
        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        Log.d(CDictionary.Debug_TAG,"Create JSONObj: "+jsonObject.toString());
        try {
            jsonObject.put("Email", fbEmail);
            Log.d(CDictionary.Debug_TAG,"GET EMAIL: "+jsonObject.optString("Email"));
            jsonObject.put("UserName", fbUsername);
            Log.d(CDictionary.Debug_TAG,"GET USERNAME: "+jsonObject.optString("UserName"));
            jsonObject.put("Provider", "Facebook");
            Log.d(CDictionary.Debug_TAG,"GET PROVIDER: "+jsonObject.optString("Provider"));
            jsonObject.put("ExternalAccessToken", accessToken.getToken());
            Log.d(CDictionary.Debug_TAG,"GET TOKEN: "+jsonObject.optString("ExternalAccessToken"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody =  RequestBody.create(Iv_MTyp_JSON,jsonObject.toString());
        Request postRrequest = new Request.Builder()
                .url("http://twpetanimal.ddns.net:9487/api/v1/Account/RegisterExternal")
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
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(CDictionary.Debug_TAG,"POST FAIL......");
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(ActLogin.this, ActHomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    View.OnClickListener btn_register_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActLogin.this, ActRegister.class);
            startActivity(intent);
        }
    };

    View.OnClickListener btn_logout_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            userInfo.edit().clear().commit();
            goMainScreen();
        }
    };

    View.OnClickListener btn_login_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            requestForToken();
        }
    };

    public void requestForToken(){
        OkHttpClient Iv_OkHttp_client = new OkHttpClient();
        final MediaType Iv_MTyp_JSON = MediaType.parse("txt; charset=utf-8");

        String requestStr = "";
        requestStr += "username="+input_account.getText().toString();
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
                            userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                            String[] strArray = input_account.getText().toString().split("@");
                            userInfo.edit().putString(CDictionary.SK_username,strArray[0])
                                    .putString(CDictionary.SK_token,access_token)
                                    .commit();
                            String name = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"訪客");
                            String access = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
                            Log.d(CDictionary.Debug_TAG,"GET SHAREDPREF: "+name);
                            Log.d(CDictionary.Debug_TAG,"GET SHAREDPREF: "+access);
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                            dialog.setTitle("登入成功");
                            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ActLogin.this, ActHomePage.class);
                                    startActivity(intent);
                                }
                            });
                            dialog.create().show();
                        } else{
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                            dialog.setTitle("登入失敗");
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
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActLogin.this);
                        dialog.setTitle("登入失敗");
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
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(btn_register_Click);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(btn_login_Click);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(btn_logout_Click);

        input_account = (EditText)findViewById(R.id.input_account);
        input_password = (EditText)findViewById(R.id.input_password);

        tv_username = (TextView)findViewById(R.id.tv_username);

        btn_test = (Button)findViewById(R.id.btn_test);
        btn_test.setOnClickListener(btn_test_Click);
    }

    Button btn_login, btn_register,btn_logout, btn_test;
    EditText input_account, input_password;
    TextView tv_username;

}
