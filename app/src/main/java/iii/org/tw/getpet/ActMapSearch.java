package iii.org.tw.getpet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import common.CDictionary;
import cz.msebera.android.httpclient.Header;
import model.object_MapMarkData;

public class ActMapSearch extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MY_LOCATION_REQUEST_CODE = 2001;
    CountDownLatch l_CountDownLatch;
    ArrayList<object_MapMarkData> l_arrayList_object_MapMarkData;
    Gson gson = new Gson();
    String url = "http://twpetanimal.ddns.net:9487/api/v1/maps";
    AsyncHttpClient loopjClient = new AsyncHttpClient();
    Double gv_dblLat, gv_dblLon;
    Intent intent;
    String mapType;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //**
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        //**

        intent = getIntent();
        mapType = intent.getExtras().getString(CDictionary.BK_mapType);
        url += "?$filter=mapType eq '" + mapType + "'";
        setContentView(R.layout.act_map_search);
        setTitle(mapType);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map); //取得地圖
        mapFragment.getMapAsync(this);


        l_CountDownLatch = new CountDownLatch(1);

        loopjClient.get(ActMapSearch.this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responsestr = new String(responseBody);
                Log.d("Debug", "" + responsestr);
                l_arrayList_object_MapMarkData = gson.fromJson(responsestr, new TypeToken<ArrayList<object_MapMarkData>>() {
                }.getType());
                Log.d("Debug", "" + l_arrayList_object_MapMarkData.get(0).getMaplatitude());

                l_CountDownLatch.countDown();
                Log.d("Debug22", "" + l_arrayList_object_MapMarkData.get(0).getMaplatitude());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Debug", "" + error);
            }
        });


    }

    //*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }
    //*



    @Override
    public void onMapReady(final GoogleMap googleMap) {

        //**



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("地圖設置為真","123");
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }





        if (true/*gv_dblLat != null && gv_dblLon != null*/) {

            //**

            final Thread l_thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        l_CountDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (final object_MapMarkData obj : l_arrayList_object_MapMarkData) {
                        Log.d("Debug1", "" + obj.getMaplatitude());
                        if (obj.getMaplatitude() != null) {
                            final LatLng gps = new LatLng(Double.valueOf(obj.getMaplatitude()), Double.valueOf(obj.getMaplongitude()));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(gps);
                                    markerOptions.title(obj.getMapName());
                                    markerOptions.snippet(obj.getMapContent());
                                    markerOptions.draggable(false);
                                    googleMap.addMarker(markerOptions);
                                }
                            });

                        }

                    }
                }
            });

            l_thread.start();

            //**


//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(new LatLng(22.628395, 120.292995));
//            markerOptions.title("Im title"/*intent.getExtras().getString(CDictionary.BK_shelter_name)*/);
//            markerOptions.draggable(false);
//            googleMap.addMarker(markerOptions);


//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(markerOptions.getPosition())
//                    .zoom(17)
//                    .build();
//            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            //**
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            double latitude = 22.628228;
            double longitude = 120.2908483;


            Location locationNet = locationManager.getLastKnownLocation(locationManager
                    .NETWORK_PROVIDER);
            Location locationGps = locationManager.getLastKnownLocation(locationManager
                    .GPS_PROVIDER);
            if(locationGps!=null){
                latitude = locationGps.getLatitude();
                longitude = locationGps.getLongitude();

            }else if(locationNet != null){
                latitude = locationNet.getLatitude();
                longitude = locationNet.getLongitude();
            }




            //**
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置


            } else {
                Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//開啟設定頁面
            }
            //**

            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.position(new LatLng(latitude, longitude));
            //markerOptions2.title("我的位置"/*intent.getExtras().getString(CDictionary.BK_shelter_name)*/);
            //markerOptions2.draggable(false);
            //googleMap.addMarker(markerOptions2);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions2.getPosition()));
            CameraPosition cameraPosition2 = new CameraPosition.Builder()
                    .target(markerOptions2.getPosition())
                    .zoom(17)
                    .build();

            //***

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition2));
            //**

        }


    }

    public void getDataFromServer() {
        url += "?$filter=mapType eq '" + mapType + "'";
        Log.d(CDictionary.Debug_TAG, "GET URL: " + url);
        loopjClient.get(ActMapSearch.this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responsestr = new String(responseBody);
                Log.d("Debug", "" + responsestr);
                l_arrayList_object_MapMarkData = gson.fromJson(responsestr, new TypeToken<ArrayList<object_MapMarkData>>() {
                }.getType());
                Log.d("Debug", "" + l_arrayList_object_MapMarkData.get(0).getMaplatitude());
                l_CountDownLatch.countDown();
                Log.d("Debug22", "" + l_arrayList_object_MapMarkData.get(0).getMaplatitude());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Debug", "" + error);
            }
        });
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
}
