package iii.org.tw.getpet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import common.CDictionary;

public class ActMsgShow extends AppCompatActivity {
    private String access_token, Email, UserName,UserId, HasRegistered, LoginProvider;

    private String msgID, msgTime, msgFrom_userID, msgFrom_userName, msgTo_userID, msgType, msgFromURL, msgContent, msgRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_show);
        setTitle("訊息內容");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        initComponent();
        Intent intent = getIntent();
        if(intent != null){
            msgFrom_userID = intent.getExtras().getString(CDictionary.BK_msg_fromuserid);
            msgFrom_userName = intent.getExtras().getString(CDictionary.BK_msg_fromusername);
            msgType = intent.getExtras().getString(CDictionary.BK_msg_subject);
            msgContent = intent.getExtras().getString(CDictionary.BK_msg_content);

            msg_subject.setText(msgType);
            msg_sender.setText(msgFrom_userName);
            msg_content.setText(msgContent);
        }
        if((msgType.equals("留言板通知"))){
            btnReply.setVisibility(View.GONE);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    @Override
    public void finish() {
        super.finish();
    }

    View.OnClickListener btnOK_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            //finish();
            Intent intent = new Intent(ActMsgShow.this, ActMsgBox.class);
            startActivity(intent);
        }
    };

    View.OnClickListener btnReply_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActMsgShow.this,ActMsgInput.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_msg_fromuserid,msgFrom_userID);
            bundle.putString(CDictionary.BK_msg_fromusername,msgFrom_userName);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    public void initComponent(){
        btnOK = (Button)findViewById(R.id.btnOK);
        btnOK.setOnClickListener(btnOK_Click);

        btnReply = (Button)findViewById(R.id.btnReply);
        btnReply.setOnClickListener(btnReply_Click);

        msg_subject = (TextView) findViewById(R.id.msg_subject);
        msg_sender = (TextView) findViewById(R.id.msg_sender);
        msg_content = (TextView) findViewById(R.id.msg_content);
    }
    Button btnOK, btnReply;
    TextView msg_subject, msg_sender, msg_content;
}
