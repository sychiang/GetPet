package iii.org.tw.getpet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActAdoptPairDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adopt_pair_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();
    }

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
        ivPhoto = (ImageView)findViewById(R.id.imgDog);

        btnCheckCond = (Button)findViewById(R.id.btnCheckCond);
        //btnCheckCond.setOnClickListener(btnCheckCond_Click);

        btnLeaveMsg = (Button)findViewById(R.id.btnLeaveMsg);
        //btnLeaveMsg.setOnClickListener(btnLeaveMsg_Click);

        btnTrack = (Button)findViewById(R.id.btnTrack);
        //btnTrack.setOnClickListener(btnTrack_Click);

        btnAdopt = (Button)findViewById(R.id.btnAdopt);
        //btnAdopt.setOnClickListener(btnAdopt_Click);

    }
    Button btnCheckCond,btnLeaveMsg,btnTrack,btnAdopt;
    ImageView ivPhoto;
    TextView tvName, tvType, tvSex, tvColor, tvAge, tvArea,tvIfBirth,tvIfChip,tvHealthy,tvDisease,tvReason,tvNote;
    //content_line1,content_line2,content_line3,content_line4,content_line5,
    //content_line6,content_line7,content_line8,content_line9,content_line10;
}
