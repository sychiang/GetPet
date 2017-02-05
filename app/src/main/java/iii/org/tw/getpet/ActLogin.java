package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.AccessToken;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.accountkit.AccountKit;
import org.json.JSONObject;

import java.util.Arrays;

import common.CDictionary;


public class ActLogin extends AppCompatActivity {

    CallbackManager callbackManager;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        //宣告callback Manager
        callbackManager = CallbackManager.Factory.create();

        //找到login button (facebook套件裡的登入按鈕元件)
        LoginButton btn_FBlogin = (LoginButton) findViewById(R.id.btn_FBlogin);

        btn_FBlogin.setReadPermissions(Arrays.asList("email"));

        //LoginButton增加callback function
        btn_FBlogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {//成功登入
                //accessToken之後或許還會用到 先存起來
                accessToken = loginResult.getAccessToken();
                Log.d(CDictionary.Debug_TAG,"FB access token got.");
                Log.d(CDictionary.Debug_TAG,"Access token: "+accessToken);

                //send request and call graph api
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            //當RESPONSE回來的時候會取得JSON物件
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //讀出姓名 ID FB個人頁面連結
                                Log.d(CDictionary.Debug_TAG,"FB get"+response);
                                Log.d(CDictionary.Debug_TAG,object.optString("name"));
                                Log.d(CDictionary.Debug_TAG,object.optString("link"));
                                Log.d(CDictionary.Debug_TAG,object.optString("id"));
                                Log.d(CDictionary.Debug_TAG,object.optString("email"));
                            }
                        });
                //包入想要得到的資料 送出request
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
                goMainScreen();
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
            btn_FBlogin.setVisibility(View.INVISIBLE);
        }
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
}
