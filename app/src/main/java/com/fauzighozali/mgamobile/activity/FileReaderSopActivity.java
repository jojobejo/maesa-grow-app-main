package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.model.GetResponseDetailSop;
import com.fauzighozali.mgamobile.model.Sop;
import com.github.barteksc.pdfviewer.PDFView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileReaderSopActivity extends AppCompatActivity {

    private static final String TAG = "FileReaderSopActivity";
    private ApiService service;
    private Call<GetResponseDetailSop> call;
    WebView pdfView;
    int file = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reader_sop);

        String url = getIntent().getStringExtra("file");

        pdfView = findViewById(R.id.web_view_pdf);
        pdfView.setWebViewClient(new WebViewClient());
        pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
    }
}