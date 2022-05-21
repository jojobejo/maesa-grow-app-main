package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.jwt.TokenManager;

public class FileReaderCrossfunctionActivity extends AppCompatActivity {

    private static  final String TAG = "FileReaderCrossfunctionActivity";
    WebView webView;
    ProgressBar progressBar;

    private TokenManager tokenManager;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reader_crossfunction);

        webView = findViewById(R.id.web_view_file_sop_crossfunction);
        progressBar = findViewById(R.id.progress_bar_crossfunction);
        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() {" +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                progressBar.setVisibility(View.GONE);
            }
        });

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken()== null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        Intent intent = getIntent();
        String file = intent.getStringExtra("file");
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+"http://api-staging-kms.duatanganindonesia.com/files/" + file);
    }
}