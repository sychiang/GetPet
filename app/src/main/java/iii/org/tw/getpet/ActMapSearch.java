package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

    CountDownLatch l_CountDownLatch;
    ArrayList<object_MapMarkData> l_arrayList_object_MapMarkData;
    Gson gson = new Gson();
    String url = "http://twpetanimal.ddns.net:9487/api/v1/maps";
    AsyncHttpClient loopjClient = new AsyncHttpClient();
    Double gv_dblLat, gv_dblLon;
    Intent intent;
    String mapType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        mapType = intent.getExtras().getString(CDictionary.BK_mapType);
        url += "?$filter=mapType eq '"+mapType+"'";
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

    @Override
    public void onMapReady(final GoogleMap googleMap) {
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

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(22.628395, 120.292995));
            markerOptions.title("Im title"/*intent.getExtras().getString(CDictionary.BK_shelter_name)*/);
            markerOptions.draggable(false);
            googleMap.addMarker(markerOptions);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(markerOptions.getPosition())
                    .zoom(17)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            //**

            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.position(new LatLng(22.628228, 120.2908483));
            markerOptions2.title("南區資策會"/*intent.getExtras().getString(CDictionary.BK_shelter_name)*/);
            markerOptions2.draggable(false);
            googleMap.addMarker(markerOptions2);

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
