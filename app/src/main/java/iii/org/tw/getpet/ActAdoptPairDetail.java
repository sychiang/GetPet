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
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import common.CDictionary;


import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;


public class ActAdoptPairDetail extends AppCompatActivity {
    Intent intent;
    LayoutInflater inflater;
    Context mContext = ActAdoptPairDetail.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopt_pair_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("送養的毛孩子");
        setSupportActionBar(toolbar);
        initComponent();
        intent = getIntent();

        if(intent.getExtras() != null && intent.getExtras().containsKey(CDictionary.BK_animalPicURL1)){
            Glide.with(ActAdoptPairDetail.this).load(intent.getExtras().getString(CDictionary.BK_animalPicURL1)).into(ivPhotoOne);
        }
        if(intent.getExtras() != null && intent.getExtras().containsKey(CDictionary.BK_animalPicURL2)){
            Glide.with(ActAdoptPairDetail.this).load(intent.getExtras().getString(CDictionary.BK_animalPicURL2)).into(ivPhotoTwo);
        }
        if(intent.getExtras() != null && intent.getExtras().containsKey(CDictionary.BK_animalPicURL3)){
            Glide.with(ActAdoptPairDetail.this).load(intent.getExtras().getString(CDictionary.BK_animalPicURL3)).into(ivPhotoThree);
        }

        tvName.setText(intent.getExtras().getString(CDictionary.BK_animalName));
        tvType.setText(intent.getExtras().getString(CDictionary.BK_animalName));
        tvSex.setText(intent.getExtras().getString(CDictionary.BK_animalGender));
        tvColor.setText(intent.getExtras().getString(CDictionary.BK_animalColor));
        tvAge.setText(intent.getExtras().getString(CDictionary.BK_animalAge));
        tvArea.setText(intent.getExtras().getString(CDictionary.BK_animalAddress));
        tvIfBirth.setText(intent.getExtras().getString(CDictionary.BK_animalBirth));
        tvIfChip.setText(intent.getExtras().getString(CDictionary.BK_animalChip));
        tvHealthy.setText(intent.getExtras().getString(CDictionary.BK_animalHealthy));
        tvDisease.setText(intent.getExtras().getString(CDictionary.BK_animalDisease_Other));
        tvReason.setText(intent.getExtras().getString(CDictionary.BK_animalReason));
        tvNote.setText(intent.getExtras().getString(CDictionary.BK_animalNote));

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
    //查看認養條件
    View.OnClickListener btnCheckCond_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.showcondition_alertdialog, null);
            TextView tv_condAge = (TextView) view.findViewById(R.id.tv_condAge);
            TextView tv_condEconomy = (TextView) view.findViewById(R.id.tv_condEconomy);
            TextView tv_condHome = (TextView) view.findViewById(R.id.tv_condHome);
            TextView tv_condFamily = (TextView) view.findViewById(R.id.tv_condFamily);
            TextView tv_condReply = (TextView) view.findViewById(R.id.tv_condReply);
            TextView tv_condPaper = (TextView) view.findViewById(R.id.tv_condPaper);
            TextView tv_condFee = (TextView) view.findViewById(R.id.tv_condFee);
            TextView tv_condOther = (TextView) view.findViewById(R.id.tv_condOther);

            tv_condAge.setText(intent.getExtras().getString(CDictionary.BK_conditionAge));
            tv_condEconomy.setText(intent.getExtras().getString(CDictionary.BK_conditionEconomy));
            tv_condHome.setText(intent.getExtras().getString(CDictionary.BK_conditionHome));
            tv_condFamily.setText(intent.getExtras().getString(CDictionary.BK_conditionFamily));
            tv_condReply.setText(intent.getExtras().getString(CDictionary.BK_conditionReply));
            tv_condPaper.setText(intent.getExtras().getString(CDictionary.BK_conditionPaper));
            tv_condFee.setText(intent.getExtras().getString(CDictionary.BK_conditionFee));
            tv_condOther.setText(intent.getExtras().getString(CDictionary.BK_conditionOther));

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
    //我要留言
    View.OnClickListener btnLeaveMsg_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //我要留言

        }
    };
    //我要追蹤
    View.OnClickListener btnTrack_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //我要追蹤

        }
    };
    //我要認養
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

        btnTrack = (Button)findViewById(R.id.btnTrack);
        btnTrack.setOnClickListener(btnTrack_Click);

        btnAdopt = (Button)findViewById(R.id.btnAdopt);
        btnAdopt.setOnClickListener(btnAdopt_Click);

    }
    Button btnCheckCond,btnLeaveMsg,btnTrack,btnAdopt;
    ImageView ivPhotoOne,ivPhotoTwo,ivPhotoThree;
    TextView tvName, tvType, tvSex, tvColor, tvAge, tvArea,tvIfBirth,tvIfChip,tvHealthy,tvDisease,tvReason,tvNote;
    ViewFlipper viewFlipper;
    Animation fade_in,fade_out;
    //content_line1,content_line2,content_line3,content_line4,content_line5,
    //content_line6,content_line7,content_line8,content_line9,content_line10;
}
