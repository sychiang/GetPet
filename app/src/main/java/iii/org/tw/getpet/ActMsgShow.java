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
            msg_subject.setText(intent.getExtras().getString(CDictionary.BK_msg_subject));
            msg_sender.setText(intent.getExtras().getString(CDictionary.BK_msg_fromusername));
            msg_content.setText(intent.getExtras().getString(CDictionary.BK_msg_content));
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

    public void initComponent(){
        btnOK = (Button)findViewById(R.id.btnOK);
        btnOK.setOnClickListener(btnOK_Click);
        msg_subject = (TextView) findViewById(R.id.msg_subject);
        msg_sender = (TextView) findViewById(R.id.msg_sender);
        msg_content = (TextView) findViewById(R.id.msg_content);
    }
    Button btnOK;
    TextView msg_subject, msg_sender, msg_content;

}
