package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import common.CDictionary;

public class ActMapCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map_category);
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
        btn_AnimalHospital = (Button)findViewById(R.id.btn_AnimalHospital);
        btn_AnimalHospital.setOnClickListener(btn_AnimalHospital_Click);

        btn_PetShop = (Button)findViewById(R.id.btn_PetShop);
        btn_PetShop.setOnClickListener(btn_PetShop_Click);

        btn_Injection = (Button)findViewById(R.id.btn_Injection);
        btn_Injection.setOnClickListener(btn_Injection_Click);
    }
    Button btn_AnimalHospital,btn_PetShop,btn_Injection;
}
