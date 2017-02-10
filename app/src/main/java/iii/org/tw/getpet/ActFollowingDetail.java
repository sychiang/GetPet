package iii.org.tw.getpet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import common.CDictionary;
import model.AdoptPair;

public class ActFollowingDetail extends AppCompatActivity {
    ArrayList<AdoptPair> petlist = new ArrayList<AdoptPair>();
    String url = "http://twpetanimal.ddns.net:9487/api/v1/AnimalDatas";
    Intent intent;
    String animalid = "";
    LayoutInflater inflater;
    Context mContext = ActFollowingDetail.this;
    AdoptPair item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_following_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //取回JSON資料
        intent = getIntent();
        if(intent.getExtras().containsKey(CDictionary.BK_animal_id)){
            animalid = intent.getExtras().getString(CDictionary.BK_animal_id);
            Log.d(CDictionary.Debug_TAG, "GET ANIMAL ID: " + animalid);
            url += "/"+animalid;
            Log.d(CDictionary.Debug_TAG, "GET URL: " + url);
        }

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<ArrayList<AdoptPair>>() {
                             },
                        new ParsedRequestListener<ArrayList<AdoptPair>>() {
                            @Override
                            public void onResponse(ArrayList<AdoptPair> response) {
                                String size = String.format("%d", response.size());
                                Log.d(CDictionary.Debug_TAG, size);
                                if (response.size() > 0) {
                                    item = response.get(0);
                                    //圖片處理
                                    if(item.getAnimalData_Pic().size()>0){
                                        if(item.getAnimalData_Pic().get(0).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
                                                item.getAnimalData_Pic().get(0).getAnimalPicAddress().toLowerCase().endsWith(".png"))
                                            Glide.with(mContext).load(item.getAnimalData_Pic().get(0).getAnimalPicAddress()).into(ivPhotoOne);
                                        if(item.getAnimalData_Pic().size() >= 2){
                                            if(item.getAnimalData_Pic().get(1).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
                                                    item.getAnimalData_Pic().get(1).getAnimalPicAddress().toLowerCase().endsWith(".png")){
                                                Glide.with(mContext).load(item.getAnimalData_Pic().get(1).getAnimalPicAddress()).into(ivPhotoTwo);
                                            }
                                        }
                                        if(item.getAnimalData_Pic().size() >= 3){
                                            if(item.getAnimalData_Pic().get(2).getAnimalPicAddress().toLowerCase().endsWith(".jpg") ||
                                                    item.getAnimalData_Pic().get(2).getAnimalPicAddress().toLowerCase().endsWith(".png")){
                                                Glide.with(mContext).load(item.getAnimalData_Pic().get(2).getAnimalPicAddress()).into(ivPhotoThree);
                                            }
                                        }
                                    }

                                    tvName.setText(item.getAnimalName());
                                    tvType.setText(item.getAnimalType());
                                    tvSex.setText(item.getAnimalGender());
                                    tvColor.setText(item.getAnimalColor());
                                    tvAge.setText(String.format("%d",item.getAnimalAge()));
                                    tvArea.setText(item.getAnimalAddress());
                                    tvIfBirth.setText(item.getAnimalBirth());
                                    tvIfChip.setText(item.getAnimalChip());
                                    tvHealthy.setText(item.getAnimalHealthy());
                                    tvDisease.setText(item.getAnimalDisease_Other());
                                    tvReason.setText(item.getAnimalReason());
                                    tvNote.setText(item.getAnimalNote());

                                    tv_condAge.setText(item.getAnimalData_Condition().get(0).getConditionAge());
                                    tv_condEconomy.setText(item.getAnimalData_Condition().get(0).getConditionEconomy());
                                    tv_condHome.setText(item.getAnimalData_Condition().get(0).getConditionHome());
                                    tv_condFamily.setText(item.getAnimalData_Condition().get(0).getConditionFamily());
                                    tv_condReply.setText(item.getAnimalData_Condition().get(0).getConditionReply());
                                    tv_condPaper.setText(item.getAnimalData_Condition().get(0).getConditionPaper());
                                    tv_condFee.setText(item.getAnimalData_Condition().get(0).getConditionFee());
                                    tv_condOther.setText(item.getAnimalData_Condition().get(0).getConditionOther());

//                                    for (AdoptPair rs : response) {
//                                        petlist.add(rs);
//                                        Log.d(CDictionary.Debug_TAG, ""+rs.getAnimalID());
//                                    }

                                } else {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActFollowingDetail.this);
                                    dialog.setView(R.layout.nodata_alertdialog);
                                    dialog.setTitle("查無資料");
                                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ActFollowingDetail.this, ActFollowingList.class);
                                            startActivity(intent);
                                        }
                                    });
                                    dialog.create().show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }

                        });



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
        }
        return super.onOptionsItemSelected(item);
    }

    //查看認養條件
    View.OnClickListener btnCheckCond_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.showcondition_alertdialog, null);

//                tv_condAge.setText(intent.getExtras().getString(CDictionary.BK_conditionAge));
//
//                tv_condEconomy.setText(intent.getExtras().getString(CDictionary.BK_conditionEconomy));
//
//                tv_condHome.setText(intent.getExtras().getString(CDictionary.BK_conditionHome));
//
//                tv_condFamily.setText(intent.getExtras().getString(CDictionary.BK_conditionFamily));
//
//                tv_condReply.setText(intent.getExtras().getString(CDictionary.BK_conditionReply));
//
//                tv_condPaper.setText(intent.getExtras().getString(CDictionary.BK_conditionPaper));
//
//                tv_condFee.setText(intent.getExtras().getString(CDictionary.BK_conditionFee));
//
//                tv_condOther.setText(intent.getExtras().getString(CDictionary.BK_conditionOther));

            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setView(view);
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.create().show();
        }
    };

    View.OnClickListener btnLeaveMsg_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //開啟留言板

        }
    };

    View.OnClickListener btnTrack_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //我要追蹤

        }
    };

    View.OnClickListener btnAdopt_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //我要認養

        }
    };

    private void initComponent(){
        tvName = (TextView)findViewById(R.id.tvName);
        tvType = (TextView)findViewById(R.id.tvType);
        tvSex = (TextView)findViewById(R.id.tvSex);
        tvColor = (TextView)findViewById(R.id.tvColor);
        tvAge = (TextView)findViewById(R.id.tvAge);
        tvArea = (TextView)findViewById(R.id.tvArea);
        tvIfBirth = (TextView)findViewById(R.id.tvIfBirth);
        tvIfChip = (TextView)findViewById(R.id.tvIfChip);
        tvHealthy = (TextView)findViewById(R.id.tvHealthy);
        tvDisease = (TextView)findViewById(R.id.tvDisease);
        tvReason = (TextView)findViewById(R.id.tvReason);
        tvNote = (TextView)findViewById(R.id.tvNote);
        ivPhotoOne = (ImageView)findViewById(R.id.ivPhotoOne);
        ivPhotoTwo = (ImageView)findViewById(R.id.ivPhotoTwo);
        ivPhotoThree = (ImageView)findViewById(R.id.ivPhotoThree);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewflipper);

        btnCheckCond = (Button)findViewById(R.id.btnCheckCond);
        btnCheckCond.setOnClickListener(btnCheckCond_Click);

        btnLeaveMsg = (Button)findViewById(R.id.btnLeaveMsg);
        btnLeaveMsg.setOnClickListener(btnLeaveMsg_Click);

        btnAdopt = (Button)findViewById(R.id.btnAdopt);
        btnAdopt.setOnClickListener(btnAdopt_Click);

        tv_condAge = (TextView) findViewById(R.id.tv_condAge);
        tv_condEconomy = (TextView) findViewById(R.id.tv_condEconomy);
        tv_condHome = (TextView) findViewById(R.id.tv_condHome);
        tv_condFamily = (TextView) findViewById(R.id.tv_condFamily);
        tv_condReply = (TextView) findViewById(R.id.tv_condReply);
        tv_condPaper = (TextView) findViewById(R.id.tv_condPaper);
        tv_condFee = (TextView) findViewById(R.id.tv_condFee);
        tv_condOther = (TextView) findViewById(R.id.tv_condOther);

    }
    Button btnCheckCond,btnLeaveMsg,btnAdopt;
    ImageView ivPhotoOne,ivPhotoTwo,ivPhotoThree;
    TextView tvName, tvType, tvSex, tvColor, tvAge, tvArea,tvIfBirth,tvIfChip,tvHealthy,tvDisease,tvReason,tvNote;
    TextView tv_condAge, tv_condEconomy, tv_condHome, tv_condFamily, tv_condReply, tv_condPaper, tv_condFee, tv_condOther;
    ViewFlipper viewFlipper;
    Animation fade_in,fade_out;

}
