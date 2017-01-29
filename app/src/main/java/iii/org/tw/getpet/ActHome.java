package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        initComponent();
    }

    View.OnClickListener btnAdoptSearch_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActHome.this, ActCategory.class);
            startActivity(intent);
        }
    };

    View.OnClickListener btnMsgbox_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent(ActHome.this, ActMsgBox.class);
            startActivity(intent);
        }
    };

    public void initComponent(){
        btnAdoptSearch = (Button)findViewById(R.id.btnAdoptSearch);
        btnAdoptSearch.setOnClickListener(btnAdoptSearch_Click);

        btnMsgbox = (Button)findViewById(R.id.btnMsgbox);
        btnMsgbox.setOnClickListener(btnMsgbox_Click);
    }
    Button btnAdoptSearch,btnMsgbox;
}
