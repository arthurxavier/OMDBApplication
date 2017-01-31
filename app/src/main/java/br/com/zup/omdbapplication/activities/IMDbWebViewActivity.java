package br.com.zup.omdbapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class IMDbWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        WebView webView = new WebView(this);
        setContentView(webView);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("imdb");

        webView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress){
                activity.setProgress(progress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Toast.makeText(activity,"Erro! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl("http://www.imdb.com/title/" + id);
    }
}
