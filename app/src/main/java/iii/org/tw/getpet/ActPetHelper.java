package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class ActPetHelper extends AppCompatActivity {
    WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pet_helper);
        ImageButton btn_goForward, btn_goBack;

        btn_goForward = (ImageButton)findViewById(R.id.btn_goForward);
        btn_goBack = (ImageButton)findViewById(R.id.btn_goBack);

        btn_goForward.setOnClickListener(btn_goForward_Click);
        btn_goBack.setOnClickListener(btn_goBack_Click);


        mWebView = (WebView)findViewById(R.id.webView);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setInitialScale(1);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://twpetanimal.ddns.net/Home/Help");
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    View.OnClickListener btn_goForward_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            if(mWebView.canGoForward()){
                mWebView.goForward();
            }
        }
    };

    View.OnClickListener btn_goBack_Click=new View.OnClickListener(){
        public void onClick(View arg0) {
            if(mWebView.canGoBack()){
                mWebView.goBack();
            }
        }
    };

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

}
