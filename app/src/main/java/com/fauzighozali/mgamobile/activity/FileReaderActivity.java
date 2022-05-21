package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseDetailSop;
import com.fauzighozali.mgamobile.model.GetResponseLampiran;
import com.fauzighozali.mgamobile.model.Sop;
import com.github.barteksc.pdfviewer.PDFView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileReaderActivity extends AppCompatActivity {

    private static final String TAG = "FileReaderActivity";
    WebView webView;
    ProgressBar progressBar;

    private TextView txtTesting;

    private TokenManager tokenManager;
    private ApiService service;
    private Call<GetResponseDetailSop> call;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reader);
        // WEB VIEW

        webView = findViewById(R.id.web_view_file_sop);
        progressBar = findViewById(R.id.progress_bar_sop);
        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                progressBar.setVisibility(View.GONE);

            }
        });

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() ==null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        displayFileSop();

    }

    private void displayFileSop(){
        Intent intent = getIntent();
        call = service.getSopDetail(intent.getIntExtra("id",0));
        call.enqueue(new Callback<GetResponseDetailSop>() {
            @Override
            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()){
                    Sop sop = response.body().getData();
                    webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + "http://api-staging-kms.duatanganindonesia.com/files/" + sop.getFile());
                }
            }

            @Override
            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}