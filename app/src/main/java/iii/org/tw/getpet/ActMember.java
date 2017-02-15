package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import common.CDictionary;

public class ActMember extends AppCompatActivity {
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_member);
        setTitle("會員專區");
        initComponent();
        //每次進來就先檢查登入資訊
        access_token = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_token,"");
        Log.d(CDictionary.Debug_TAG,"SHAREDPREF TOKEN: "+access_token);
        if( access_token != ""){
            UserId = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_userid,"");
            UserName = getSharedPreferences("userInfo",MODE_PRIVATE).getString(CDictionary.SK_username,"訪客");
        }
    }

    View.OnClickListener btnGoRegister_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            if(access_token == ""){
                intent = new Intent(ActMember.this, ActRegister.class);
                startActivity(intent);
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActMember.this);
                dialog.setTitle("您已是會員");
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create().show();
            }

        }
    };

    View.OnClickListener btnGoSignIn_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            intent = new Intent(ActMember.this, ActLogin.class);
            startActivityForResult(intent, CDictionary.REQUEST_LOGIN);
        }
    };

    View.OnClickListener btnGoMsgBox_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            if(access_token == ""){
                Log.d(CDictionary.Debug_TAG,"not log in");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActMember.this);
                dialog.setTitle("尚未登入, 請先登入會員");
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goLoginScreen();
                    }
                });
                dialog.create().show();
            } else {
                Log.d(CDictionary.Debug_TAG,"GET TOKEN: "+access_token);
                intent = new Intent(ActMember.this,ActMsgBox.class);
                Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
                startActivity(intent);
            }
        }
    };

    View.OnClickListener btnGoFollowing_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            if(access_token == ""){
                Log.d(CDictionary.Debug_TAG,"not log in");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActMember.this);
                dialog.setTitle("尚未登入, 請先登入會員");
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goLoginScreen();
                    }
                });
                dialog.create().show();
            } else {
                Log.d(CDictionary.Debug_TAG,"GET TOKEN: "+access_token);
                Log.d(CDictionary.Debug_TAG,"GET USER ID："+UserId);
                intent = new Intent(ActMember.this, ActFollowingList.class);
                startActivity(intent);
            }
        }
    };

    private void goLoginScreen() {
        intent = new Intent(ActMember.this, ActLogin.class);
        startActivity(intent);
    }

    public void initComponent(){
        btnGoRegister = (Button)findViewById(R.id.btnGoRegister);
        btnGoRegister.setOnClickListener(btnGoRegister_Click);

        btnGoSignIn = (Button)findViewById(R.id.btnGoSignIn);
        btnGoSignIn.setOnClickListener(btnGoSignIn_Click);

        btnGoMsgBox = (Button)findViewById(R.id.btnGoMsgBox);
        btnGoMsgBox.setOnClickListener(btnGoMsgBox_Click);

        btnGoFollowing = (Button)findViewById(R.id.btnGoFollowing);
        btnGoFollowing.setOnClickListener(btnGoFollowing_Click);

        ivPhoto1 = (ImageView)findViewById(R.id.ivPhoto1);

        viewFlipper=(ViewFlipper)findViewById(R.id.viewflipper);
    }

    Button btnGoRegister,btnGoSignIn,btnGoMsgBox,btnGoFollowing;
    ImageView ivPhoto1,ivPhoto2,ivPhoto3,ivPhoto4,ivPhoto5;
    ViewFlipper viewFlipper;
    Animation fade_in,fade_out;
}
