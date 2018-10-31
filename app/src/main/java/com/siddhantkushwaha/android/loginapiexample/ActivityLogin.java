package com.siddhantkushwaha.android.loginapiexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class ActivityLogin extends AppCompatActivity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        webView = findViewById(R.id.web);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                String token = url.replaceAll(MainActivity.urlSubstring, "");

                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                intent.putExtra("TOKEN", token);
                startActivity(intent);
                finish();

                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(MainActivity.url);
    }
}
