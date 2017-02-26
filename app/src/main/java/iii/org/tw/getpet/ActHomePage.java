package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;



import org.json.JSONObject;

import java.util.ArrayList;

import common.CDictionary;
import model.AnimalPic;

public class ActHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    String fbEmail, fbUsername, fbID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();
        //每次進來就先檢查登入資訊
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"SHAREDPREF TOKEN: "+access_token);
        if( access_token != ""){
            UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
            UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"訪客");
            //header_username.setText("Hi, "+UserName);
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        //輪播功能
//        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
//        fade_out = AnimationUtils.loadAnimation(this,android.R.anim.fade_out);
//        viewFlipper.setAnimation(fade_in);
//        viewFlipper.setAnimation(fade_out);
        //sets auto flipping
//        viewFlipper.setAutoStart(true);
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.startFlipping();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        Bundle bundle = new Bundle();

        switch (id){
            case R.id.messagebox:
                if(access_token == ""){
                    Log.d(CDictionary.Debug_TAG,"not log in");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActHomePage.this);
                    dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>尚未登入</font>"));
                    dialog.setMessage(Html.fromHtml("<font color='#2d4b44'>請先登入會員</font>"));
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goLoginScreen();
                        }
                    });
                    dialog.create().show();
                } else {
                    Log.d(CDictionary.Debug_TAG,"GET TOKEN: "+access_token);
                    intent = new Intent(ActHomePage.this,ActMsgBox.class);
                    Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
                    startActivity(intent);
                }
                break;
            case R.id.folowinglist:
                if(access_token == ""){
                    Log.d(CDictionary.Debug_TAG,"not log in");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActHomePage.this);
                    dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>尚未登入</font>"));
                    dialog.setMessage(Html.fromHtml("<font color='#2d4b44'>請先登入會員</font>"));
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goLoginScreen();
                        }
                    });
                    dialog.create().show();
                } else {
                    intent = new Intent(ActHomePage.this,ActFollowingList.class);
                    startActivity(intent);
                }
                break;
            case R.id.login:
                intent = new Intent(ActHomePage.this, ActLogin.class);
                startActivityForResult(intent, CDictionary.REQUEST_LOGIN);
                break;
            default:
                break;
        }

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
            //前往認養專區
            Intent intent = new Intent(ActHomePage.this, ActSearchAdopt.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoMember_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往會員設定
                Intent intent = new Intent(ActHomePage.this, ActMember.class);
                startActivity(intent);
        }
    };
    View.OnClickListener btnGoPetHelper_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往寵物小幫手
            Intent intent = new Intent(ActHomePage.this, ActPetHelper.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoMapSearch_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往地圖搜尋
            Intent intent = new Intent(ActHomePage.this, ActMapCategory.class);
            startActivity(intent);
        }
    };
    View.OnClickListener btnGoUpload_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往送養專區
            if(access_token == ""){
                Log.d(CDictionary.Debug_TAG,"not log in");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActHomePage.this);
                dialog.setTitle(Html.fromHtml("<font color='#2d4b44'>尚未登入</font>"));
                dialog.setMessage(Html.fromHtml("<font color='#2d4b44'>請先登入會員</font>"));
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goLoginScreen();
                    }
                });
                dialog.create().show();
            } else {
                Intent intent = new Intent(ActHomePage.this, ActAdoptUpload.class);
                startActivity(intent);
            }
        }
    };
    View.OnClickListener btnGoShelter_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //前往收容所資訊
                Intent intent = new Intent(ActHomePage.this, ActSearchShelter.class);
                startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aboutus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_goaboutus) {
            Intent intent = new Intent(this, ActAboutUs.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void initComponent(){
        btnGoAdoptSearch = (ImageButton)findViewById(R.id.btnGoAdoptSearch);
        btnGoAdoptSearch.setOnClickListener(btnGoAdoptSearch_Click);
        btnGoMember = (ImageButton)findViewById(R.id.btnGoMember);
        btnGoMember.setOnClickListener(btnGoMember_Click);
        btnGoPetHelper = (ImageButton)findViewById(R.id.btnGoPetHelper);
        btnGoPetHelper.setOnClickListener(btnGoPetHelper_Click);
        btnGoMapSearch = (ImageButton)findViewById(R.id.btnGoMapSearch);
        btnGoMapSearch.setOnClickListener(btnGoMapSearch_Click);
        btnGoUpload = (ImageButton)findViewById(R.id.btnGoUpload);
        btnGoUpload.setOnClickListener(btnGoUpload_Click);

        btnGoShelter = (ImageButton)findViewById(R.id.btnGoShelter);
        btnGoShelter.setOnClickListener(btnGoShelter_Click);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        header_username = (TextView)header.findViewById(R.id.header_username);

        ivPhoto1 = (ImageView)findViewById(R.id.ivPhoto1);
//        ivPhoto2 = (ImageView)findViewById(R.id.ivPhoto2);
//        ivPhoto3 = (ImageView)findViewById(R.id.ivPhoto3);
//        ivPhoto4 = (ImageView)findViewById(R.id.ivPhoto4);
//        ivPhoto5 = (ImageView)findViewById(R.id.ivPhoto5);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewflipper);
    }

    //Button btnGoAdoptSearch,btnGoMember,btnGoPetHelper,btnGoMapSearch,btnGoUpload,btnGoAboutUs;
    TextView header_username;
    NavigationView navigationView;
    ImageView ivPhoto1,ivPhoto2,ivPhoto3,ivPhoto4,ivPhoto5;
    ViewFlipper viewFlipper;
    Animation fade_in,fade_out;

    ImageButton btnGoAdoptSearch,btnGoMember,btnGoPetHelper,btnGoMapSearch,btnGoUpload,btnGoShelter;

}
