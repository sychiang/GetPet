package iii.org.tw.getpet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import common.CDictionary;

public class ActMapCategory extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map_category);
        initComponent();
        //**
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 102);
        }


        //**

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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener btn_AnimalHospital_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActMapCategory.this, ActMapSearch.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_mapType,"動物醫院");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    View.OnClickListener btn_PetShop_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActMapCategory.this, ActMapSearch.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_mapType,"合格寵物業者");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    View.OnClickListener btn_Injection_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActMapCategory.this, ActMapSearch.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_mapType,"狂犬病注射站");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    public void initComponent(){
        btn_AnimalHospital = (ImageButton)findViewById(R.id.btn_AnimalHospital);
        btn_AnimalHospital.setOnClickListener(btn_AnimalHospital_Click);

        btn_PetShop = (ImageButton)findViewById(R.id.btn_PetShop);
        btn_PetShop.setOnClickListener(btn_PetShop_Click);

        btn_Injection = (ImageButton)findViewById(R.id.btn_Injection);
        btn_Injection.setOnClickListener(btn_Injection_Click);
    }
    //Button btn_AnimalHospital,btn_PetShop,btn_Injection;
    ImageButton btn_AnimalHospital,btn_PetShop,btn_Injection;
}
