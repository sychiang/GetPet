package iii.org.tw.getpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class ActHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String userName = "未登入";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();
        //每次進來就先檢查登入資訊


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

//        switch (id){
//            case R.id.search_setting:
//                break;
//            case R.id.notify_setting:
//                break;
//            case R.id.messagebox:
//                Intent intent=new Intent(ActHomePage.this,ActMsgBox.class);
//                startActivity(intent);
//                break;
//            case R.id.contact_us:
//                break;
//            case R.id.login:
//                break;
//            default:
//                break;
//        }

        if (id == R.id.search_setting) {

        } else if (id == R.id.notify_setting) {

        } else if (id == R.id.messagebox) {
            Intent intent=new Intent(ActHomePage.this,ActMsgBox.class);
            startActivity(intent);

        } else if (id == R.id.other_setting) {

        } else if (id == R.id.contact_us) {

        } else if (id == R.id.login) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void checkIfLogin(){

    }

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
        header_username = (TextView)findViewById(R.id.header_username);

    }

    Button btnGoAdoptSearch,btnGoPairSetting,btnGoPetHelper,btnGoMapSearch,btnGoUpload,btnGoSetting;
    TextView header_username;

}
