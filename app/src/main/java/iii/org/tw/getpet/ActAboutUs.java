package iii.org.tw.getpet;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActAboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_about_us);
        initComponent();

        tv_content.setText("以認養代替購買，以送養代替拋棄\n" +
                "這是一個非官方的網站，目的是希望透過網站及APP即時分享 狗狗 & 貓貓 的認養及送養資訊，讓想要養寵物的各位以認養代替購買，以送養代替拋棄。\n" +
                "如果需要進行認養及送養，請參考認養專區裡的認養及送養步驟進行認養及送養，給他們一個溫暖的家。\n" +
                "歡迎大家以認養代替購買，節省經費，又可發揮愛心。\n");

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

    View.OnClickListener btn_rank_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            final String appPackageName = ActAboutUs.this.getPackageName();
            try {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                startActivity(intent);
                finish();
            }
            catch (Exception e) {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
                startActivity(intent);
                finish();
            }
        }
    };

    View.OnClickListener btn_contactus_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:iiisyc92@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "[問題/建議]");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(intent);
            finish();
        }
    };

    public void initComponent(){
        btn_rank = (ImageButton)findViewById(R.id.btn_rank);
        btn_rank.setOnClickListener(btn_rank_Click);

        btn_contactus = (ImageButton)findViewById(R.id.btn_contactus);
        btn_contactus.setOnClickListener(btn_contactus_Click);

        tv_content = (TextView)findViewById(R.id.tv_content);
    }
    ImageButton btn_rank,btn_contactus;
    TextView tv_content;
}
