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
import android.widget.Toast;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseDetailSop;
import com.fauzighozali.mgamobile.model.LampiranSop;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileReaderLampiranActivity extends AppCompatActivity {

    private static final String TAG = "FileReaderLampiranActivity";
    WebView webView;
    ProgressBar progressBar;

    private  List<LampiranSop> lampiranSops;
    private TokenManager tokenManager;
    private ApiService service;
    private Call<GetResponseDetailSop> call;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reader_lampiran);

        //Web View
        webView = findViewById(R.id.web_view_file_sop_lampiran);
        progressBar = findViewById(R.id.progress_bar_lampiran);
        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() {" +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                progressBar.setVisibility(View.GONE);
            }
        });

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken()==null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
//        displayFileLampiranSop();

        Intent intent = getIntent();
        String file = intent.getStringExtra("file");
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+"http://api-staging-kms.duatanganindonesia.com/files/" + file);
    }

//    private void displayFileLampiranSop(){
//        Intent intent = getIntent();
//        call = service.getSopDetail(intent.getIntExtra("id",0));
//        call.enqueue(new Callback<GetResponseDetailSop>() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
//                Log.w(TAG,"onResponse:" + response);
//                if (response.isSuccessful()){
//                   LampiranSop sop = response.body().getData().getLampiranSop();
//                    webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+"http://api-staging-kms.duatanganindonesia.com/files/" + sop.getFile());
//                }
//            }
//
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
//                Log.w(TAG,"onFailure: " + t.getMessage());
//            }
//        });
//    }
}