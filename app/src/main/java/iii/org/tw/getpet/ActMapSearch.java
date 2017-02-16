package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        setContentView(R.layout.act_map_search);

        l_CountDownLatch = new CountDownLatch(1);

        //getDataFromServer();
        final MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map); //取得地圖

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //getDataFromServer();
        //final GoogleMap mGoogleMap = googleMap;

//        final Thread l_thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    l_CountDownLatch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                for (final object_MapMarkData obj : l_arrayList_object_MapMarkData) {
//                    Log.d("Debug1", "" + obj.getMaplatitude());
//                    if (obj.getMaplatitude() != null) {
//                        final LatLng gps = new LatLng(Double.valueOf(obj.getMaplatitude()), Double.valueOf(obj.getMaplongitude()));
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(gps)
//                                        .title(obj.getMapName())
//                                        .snippet(obj.getMapContent()));
//                                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.dog_icon))); //設地標
//                            }
//                        });
//                    }
//                }
//            }
//        });
//
//        l_thread.start();
        LatLng gps = new LatLng(22.628228, 120.2908483);    //設定經緯度 預設南區資策會
        Marker marker = googleMap.addMarker(new MarkerOptions().position(gps).title("南區資策會").snippet("")); //設地標
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gps, 16));      //設定顯示大小
    }

    public void getDataFromServer() {

        url += "?$filter=mapType eq '"+mapType+"'";
        Log.d(CDictionary.Debug_TAG,"GET URL: "+url);
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
}
