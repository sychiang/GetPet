package iii.org.tw.getpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

import common.CDictionary;
import model.AnimalPic;

public class ActHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //ArrayList<AnimalPic> picList = new ArrayList<AnimalPic>();
    //ArrayList<ImageView> imgViewList = new ArrayList<ImageView>();
    String userID = "";
    String userName = "未登入";
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();
        header_username.setText(userName);

        //每次進來就先檢查登入資訊
        if(AccessToken.getCurrentAccessToken() != null){
            Log.d(CDictionary.Debug_TAG,"HAVE TOKEN："+ AccessToken.getCurrentAccessToken().getToken());
            accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            //讀出姓名 ID FB個人頁面連結
                            Log.d(CDictionary.Debug_TAG,"FB get"+response);
                            Log.d(CDictionary.Debug_TAG,"FB get"+object);
                            Log.d(CDictionary.Debug_TAG,object.optString("name"));
                            Log.d(CDictionary.Debug_TAG,object.optString("link"));
                            Log.d(CDictionary.Debug_TAG,object.optString("id"));
                            Log.d(CDictionary.Debug_TAG,object.optString("email"));
                            userName = object.optString("name");
                            Log.d(CDictionary.Debug_TAG,"Set userName："+userName);
                            header_username.setText(userName);

                            userID = object.optString("id");
                            Log.d(CDictionary.Debug_TAG,"Set userID："+userID);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        Bundle bundle = new Bundle();

        switch (id){
            case R.id.search_setting:
                if(AccessToken.getCurrentAccessToken() == null){
                    Log.d(CDictionary.Debug_TAG,"not log in");
                    goLoginScreen();
                } else {
                    Log.d(CDictionary.Debug_TAG,AccessToken.getCurrentAccessToken().getToken());
//                    intent = new Intent(ActHomePage.this,ActMsgBox.class);
//                    startActivity(intent);
                }
                break;
            case R.id.notify_setting:
                if(AccessToken.getCurrentAccessToken() == null){
                    Log.d(CDictionary.Debug_TAG,"not log in");
                    goLoginScreen();
                } else {
                    Log.d(CDictionary.Debug_TAG,AccessToken.getCurrentAccessToken().getToken());
//                    intent = new Intent(ActHomePage.this,ActMsgBox.class);
//                    startActivity(intent);
                }
                break;
            case R.id.messagebox:
//                intent = new Intent(ActHomePage.this,ActMsgBox.class);
//                startActivity(intent);
                if(AccessToken.getCurrentAccessToken() == null){
                    Log.d(CDictionary.Debug_TAG,"not log in");
                    goLoginScreen();
                } else {
                    Log.d(CDictionary.Debug_TAG,AccessToken.getCurrentAccessToken().getToken());
                    intent = new Intent(ActHomePage.this,ActMsgBox.class);
                    Log.d(CDictionary.Debug_TAG,"Get userName："+userName);
                    bundle.putString(CDictionary.BK_fb_name,userName);
                    bundle.putString(CDictionary.BK_fb_id,userID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.contact_us:
                break;
            case R.id.login:
                intent = new Intent(ActHomePage.this, ActLogin.class);
                startActivityForResult(intent, CDictionary.REQUEST_LOGIN);
                break;
            default:
                break;
        }

//        if (id == R.id.search_setting) {
//
//        } else if (id == R.id.notify_setting) {
//
//        } else if (id == R.id.messagebox) {
//            intent=new Intent(ActHomePage.this,ActMsgBox.class);
//            startActivity(intent);
//
//        } else if (id == R.id.other_setting) {
//
//        } else if (id == R.id.contact_us) {
//
//        } else if (id == R.id.login) {
//            intent = new Intent(ActHomePage.this, ActLogin.class);
//            startActivityForResult(intent, CDictionary.REQUEST_LOGIN);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goLoginScreen() {
        Intent intent = new Intent(ActHomePage.this, ActLogin.class);
        startActivity(intent);
    }

    View.OnClickListener btnGoAdoptSearch_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往認養搜尋
            Intent intent = new Intent(ActHomePage.this, ActCategory.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoPairSetting_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往配對設定
            Intent intent = new Intent(ActHomePage.this, ActCategory.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoPetHelper_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往寵物小幫手
            Intent intent = new Intent(ActHomePage.this, ActCategory.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoMapSearch_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往地圖搜尋
            Intent intent = new Intent(ActHomePage.this, ActCategory.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoUpload_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往送養管理
            Intent intent = new Intent(ActHomePage.this, ActCategory.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoSetting_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往系統設定
            Intent intent = new Intent(ActHomePage.this, ActCategory.class);
            startActivity(intent);
        }
    };

    public void initComponent(){
        btnGoAdoptSearch = (Button)findViewById(R.id.btnGoAdoptSearch);
        btnGoAdoptSearch.setOnClickListener(btnGoAdoptSearch_Click);
        btnGoPairSetting = (Button)findViewById(R.id.btnGoPairSetting);
        btnGoPairSetting.setOnClickListener(btnGoPairSetting_Click);
        btnGoPetHelper = (Button)findViewById(R.id.btnGoPetHelper);
        btnGoPetHelper.setOnClickListener(btnGoPetHelper_Click);
        btnGoMapSearch = (Button)findViewById(R.id.btnGoMapSearch);
        btnGoMapSearch.setOnClickListener(btnGoMapSearch_Click);
        btnGoUpload = (Button)findViewById(R.id.btnGoUpload);
        btnGoUpload.setOnClickListener(btnGoUpload_Click);
        btnGoSetting = (Button)findViewById(R.id.btnGoSetting);
        btnGoSetting.setOnClickListener(btnGoSetting_Click);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        header_username = (TextView)header.findViewById(R.id.header_username);

        ivPhoto1 = (ImageView)findViewById(R.id.ivPhoto1);
        ivPhoto2 = (ImageView)findViewById(R.id.ivPhoto2);
        ivPhoto3 = (ImageView)findViewById(R.id.ivPhoto3);
        ivPhoto4 = (ImageView)findViewById(R.id.ivPhoto4);
        ivPhoto5 = (ImageView)findViewById(R.id.ivPhoto5);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewflipper);
    }

    Button btnGoAdoptSearch,btnGoPairSetting,btnGoPetHelper,btnGoMapSearch,btnGoUpload,btnGoSetting;
    TextView header_username;
    NavigationView navigationView;
    ImageView ivPhoto1,ivPhoto2,ivPhoto3,ivPhoto4,ivPhoto5;
    ViewFlipper viewFlipper;
    Animation fade_in,fade_out;
}
