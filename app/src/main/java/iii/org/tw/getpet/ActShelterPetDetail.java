package iii.org.tw.getpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import common.CDictionary;

public class ActShelterPetDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shelter_pet_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        Intent intent = getIntent();
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
        if(intent.getExtras().getString(CDictionary.BK_album_file).length()>0){
            Glide.with(ActShelterPetDetail.this).load(intent.getExtras().getString(CDictionary.BK_album_file)).into(ivPhoto);
        }
    }

    private void init(){
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
    }
    ImageView ivPhoto;
    TextView tvID, tvKind, tvSex, tvBodyType, tvColor, tvAge, tvPlace, tvShelterName, tvShelterAddress, tvShelterTel, tvRemark;
}
