package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import common.CDictionary;

public class ActShelterPetDetail extends AppCompatActivity implements OnMapReadyCallback {
    Intent intent;
    Double gv_dblLat, gv_dblLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shelter_pet_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("收容所毛孩");
        setSupportActionBar(toolbar);
        initComponent();
        intent = getIntent();
        tvID.setText(intent.getExtras().getString(CDictionary.BK_animal_id));
        tvKind.setText(intent.getExtras().getString(CDictionary.BK_animal_kind));
        switch (intent.getExtras().getString(CDictionary.BK_animal_sex)){
            case "M":
                tvSex.setText("公");
                break;
            case "F":
                tvSex.setText("母");
                break;
            default:
                tvSex.setText("");
                break;
        }
        switch (intent.getExtras().getString(CDictionary.BK_animal_bodytype)){
            case "MINI":
                tvBodyType.setText("迷你");
                break;
            case "SMALL":
                tvBodyType.setText("小型");
                break;
            case "MEDIUM":
                tvBodyType.setText("中型");
                break;
            case "BIG":
                tvBodyType.setText("大型");
                break;
            default:
                tvBodyType.setText("一般");
                break;
        }

        tvColor.setText(intent.getExtras().getString(CDictionary.BK_animal_colour));
        switch (intent.getExtras().getString(CDictionary.BK_animal_age)){
            case "ADULT":
                tvAge.setText("成年");
                break;
            case "CHILD":
                tvAge.setText("幼年");
                break;
            default:
                tvAge.setText("");
                break;
        }

        tvShelterName.setText(intent.getExtras().getString(CDictionary.BK_shelter_name));
        tvShelterTel.setText(intent.getExtras().getString(CDictionary.BK_shelter_tel));
        tvShelterAddress.setText(intent.getExtras().getString(CDictionary.BK_shelter_address));
        tvRemark.setText(intent.getExtras().getString(CDictionary.BK_animal_remark));
        if(intent.getExtras().containsKey(CDictionary.BK_album_file)){
            Glide.with(ActShelterPetDetail.this).load(intent.getExtras().getString(CDictionary.BK_album_file)).into(ivPhoto);
        }

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        final MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map); //取得地圖
        map.getMapAsync(this);
        convertLanLat();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(gv_dblLat != null && gv_dblLon != null){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(gv_dblLat, gv_dblLon));
            markerOptions.title(intent.getExtras().getString(CDictionary.BK_shelter_name));
            markerOptions.draggable(false);
            googleMap.addMarker(markerOptions);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(markerOptions.getPosition())
                    .zoom(17)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public void convertLanLat(){
        String lv_strAddress = intent.getExtras().getString(CDictionary.BK_shelter_address);

        if ( lv_strAddress != null) {
            Geocoder geoCoder = new Geocoder(ActShelterPetDetail.this, Locale.TRADITIONAL_CHINESE);
            List<Address> addressLocation = null;
            try {
                addressLocation = geoCoder.getFromLocationName(lv_strAddress, 1);
//                if(addressLocation.size() == 0){
//                    addressLocation = geoCoder.getFromLocationName(lv_strAddress, 2);
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(CDictionary.Debug_TAG,"ADD SIZE: "+addressLocation.size());
            if(addressLocation.size() > 0){
                gv_dblLat = addressLocation.get(0).getLatitude();
                gv_dblLon = addressLocation.get(0).getLongitude();
                Log.d(CDictionary.Debug_TAG,"LAT: "+gv_dblLat.toString());
                Log.d(CDictionary.Debug_TAG,"LON: "+gv_dblLon.toString());
            }
        } else {
            Log.d(CDictionary.Debug_TAG,"LANLAT CONVERT FAIL");
            //Toast.makeText(ActMain.this, "無資料可顯示", Toast.LENGTH_LONG).show();
        }
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

    View.OnClickListener btnHowToAdopt_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            LayoutInflater inflater = LayoutInflater.from(ActShelterPetDetail.this);
            final View view = inflater.inflate(R.layout.howtoadopt_alertdialog, null);
            TextView content_line1,content_line2,content_line3,content_line4,content_line5,
                    content_line6,content_line7,content_line8,content_line9,content_line10;

            content_line1 = (TextView)view.findViewById(R.id.content_line1);
            content_line2 = (TextView)view.findViewById(R.id.content_line2);
            content_line3 = (TextView)view.findViewById(R.id.content_line3);
            content_line4 = (TextView)view.findViewById(R.id.content_line4);
            content_line5 = (TextView)view.findViewById(R.id.content_line5);
            content_line6 = (TextView)view.findViewById(R.id.content_line6);
            content_line7 = (TextView)view.findViewById(R.id.content_line7);
            content_line8 = (TextView)view.findViewById(R.id.content_line8);
            content_line9 = (TextView)view.findViewById(R.id.content_line9);
            content_line10 = (TextView)view.findViewById(R.id.content_line10);

            content_line1.setText("一、申請人：年滿20歲之民眾。未滿20歲者，以其法定代理人或法定監護人為飼主。");
            content_line2.setText("二、申請步驟：");
            content_line3.setText("（一）申請人應攜身分證明文件，填具申請書。");
            content_line4.setText("（二）承辦人員應就認養人核對身分證明文件，必要時得親自實地勘察。");
            content_line5.setText("（三）待認養動物條件：於本處留置已逾7日尚無飼主認領或無身分標識者，且經本處健康行為評估適於認養者。");
            content_line6.setText("（四）符合認養人資格者，得由管理人員協助，由可認養犬隻中，自行挑選合意犬隻。");
            content_line7.setText("（五）繳交相關規費：晶片植入手續費250元、狂犬病預防注射費200元。");
            content_line8.setText("（六）未實施晶片植入、狂犬病預防注射及寵物登記之動物，應於完成晶片植入、狂犬病預防注射及寵物登記後始得放行。唯8週齡以下幼犬暫免施打狂犬病疫苗。");
            content_line9.setText("（七）認養之犬隻自領出日起1個月內，若因任何原因無法續養，可將該犬交還本所，填寫「不擬續養動物申請切結書」放棄該犬之所有權，並繳回寵物登記證及狂犬病預防注射證明辦理註銷。認養時所繳之費用概不退還。");
            content_line10.setText("以上資訊供參考，詳細辦理方法依各收容單位為準。");

            AlertDialog.Builder dialog = new AlertDialog.Builder(ActShelterPetDetail.this);
            dialog.setTitle("認養須知");
            dialog.setView(view);
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.create().show();
        }
    };

    private void initComponent(){
        tvID = (TextView)findViewById(R.id.tvID);
        tvKind = (TextView)findViewById(R.id.tvKind);
        tvSex = (TextView)findViewById(R.id.tvSex);
        tvBodyType = (TextView)findViewById(R.id.tvBodyType);
        tvColor = (TextView)findViewById(R.id.tvColor);
        tvAge = (TextView)findViewById(R.id.tvAge);
        //tvPlace = (TextView)findViewById(R.id.tvPlace);
        tvShelterName = (TextView)findViewById(R.id.tvShelterName);
        tvShelterAddress = (TextView)findViewById(R.id.tvShelterAddress);
        tvShelterTel = (TextView)findViewById(R.id.tvShelterTel);
        tvRemark = (TextView)findViewById(R.id.tvRemark);
        ivPhoto = (ImageView)findViewById(R.id.imgDog);

        btnHowToAdopt = (Button)findViewById(R.id.btnHowToAdopt);
        btnHowToAdopt.setOnClickListener(btnHowToAdopt_Click);

    }
    Button btnHowToAdopt;
    ImageView ivPhoto;
    TextView tvID, tvKind, tvSex, tvBodyType, tvColor, tvAge, tvPlace,
            tvShelterName, tvShelterAddress, tvShelterTel, tvRemark;
            //content_line1,content_line2,content_line3,content_line4,content_line5,
            //content_line6,content_line7,content_line8,content_line9,content_line10;
}
