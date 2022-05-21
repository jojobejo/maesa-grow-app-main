package com.fauzighozali.mgamobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.adapter.CrossfunctionProcedureAdapter;
import com.fauzighozali.mgamobile.adapter.LampiranStandardProcedureAdapter;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.fragment.DashboardFragment;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.Crossfunction;
import com.fauzighozali.mgamobile.model.GetResponseDetailSop;
import com.fauzighozali.mgamobile.model.GetResponseLampiran;
import com.fauzighozali.mgamobile.model.GetResponseMessage;
import com.fauzighozali.mgamobile.model.GetResponseSopDivision;
import com.fauzighozali.mgamobile.model.LampiranSop;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandardOpDetailActivity extends AppCompatActivity {

    private static final String TAG = "StandardOpDetail";

    private ImageView ivSop;
    private TextView tvTitleSop, tvLinkDownloadSop;
    private RelativeLayout rlFileSop;
    private Button btnRequest,btnDownload;

    private CrossfunctionProcedureAdapter crossffunctionAdapter;
    private RecyclerView recyclerViewCrossfunction;
    private RecyclerView.LayoutManager mLayoutManagerCrossfunction;

    private LampiranStandardProcedureAdapter lampiranAdapter;
    private RecyclerView recyclerViewLampiran;
    private RecyclerView.LayoutManager mLayoutManager;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseDetailSop> callSop;
    private Call<GetResponseDetailSop> callLampiran;
    private Call<GetResponseDetailSop> callStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_op_detail);

        ivSop = findViewById(R.id.image_view_detail_sop);
        rlFileSop = findViewById(R.id.relative_layout_file_sop);
        tvTitleSop = findViewById(R.id.text_view_title_detail_sop);
        btnRequest = findViewById(R.id.btn_request_download);
        btnDownload = findViewById(R.id.btn_download_file);
        tvLinkDownloadSop = findViewById(R.id.text_view_url_download_file_sop);

        recyclerViewCrossfunction = findViewById(R.id.recycler_view_crossfunction_sop);
        mLayoutManagerCrossfunction = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerViewCrossfunction.setLayoutManager(mLayoutManagerCrossfunction);

        recyclerViewLampiran = findViewById(R.id.recycler_view_lampiran_sop);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewLampiran.setLayoutManager(mLayoutManager);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        displayDataSop();
        displayListLampiran();
//        displayButtonDownloadLampiran();
        displayListCrossfunction();
    }

    private void displayDataSop() {
        Intent intent = getIntent();
        callSop = service.getSopDetail(intent.getIntExtra("id", 0));
        callSop.enqueue(new Callback<GetResponseDetailSop>() {
            @Override
            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    Sop sop = response.body().getData();
                    Glide.with(getApplicationContext())
                            .load(RetrofitBuilder.BASE_URL_FILE + sop.getImage())
                            .into(ivSop);
                    tvTitleSop.setText(sop.getTitle());

                    if (sop.getStatus() == 0){
                        btnRequest.setVisibility(View.VISIBLE);
                        btnDownload.setVisibility(View.GONE);
                        btnRequest.setOnClickListener(v -> {
                           requestDownloadFile();
                        });
                    } else {
                        btnRequest.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                    }
                    if (sop.getStatus() == 2){
                        btnRequest.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.VISIBLE);
                        btnDownload.setOnClickListener(v->{
                            downloadFileSop();
                        });
                    }
                    rlFileSop.setOnClickListener(v -> {
                        Intent intent = new Intent(v.getContext(), FileReaderActivity.class);
                        intent.putExtra("id", sop.getId());
                        v.getContext().startActivity(intent);
                    });
                }
            }


            @Override
            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

//    private  void displayButtonDownloadLampiran(){
//        Intent intent = getIntent();
//        callSop = service.getSopDetail(intent.getIntExtra("id", 0));
//        callSop.enqueue(new Callback<GetResponseDetailSop>() {
//            @Override
//            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
//                Log.w(TAG, "onResponse: " + response);
//                if (response.isSuccessful()){
//                    Sop sop = response.body().getData();
//                    List<LampiranSop> sopList = response.body().getData().getLampiranSopList();
//                    if (sopList.size() >0){
//                        new LampiranStandardProcedureAdapter(sopList,getApplicationContext()).LampiranStandardProcedureAdapterSop(sop,getApplicationContext());
//                        recyclerViewLampiran.setAdapter(lampiranAdapter);
//                    }else {
//                        recyclerViewLampiran.setVisibility(View.GONE);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
//                Log.w(TAG, "onFailure:" + t.getMessage());
//            }
//        });
//
//    }

    private void displayListLampiran() {
        Intent intent = getIntent();
        callLampiran = service.getSopDetail(intent.getIntExtra("id", 0));
        callLampiran.enqueue(new Callback<GetResponseDetailSop>() {
            @Override
            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                   List<LampiranSop> sopList = response.body().getData().getLampiranSopList();
                   Sop sop = response.body().getData();
                    if (sopList.size() > 0){
                        lampiranAdapter = new LampiranStandardProcedureAdapter(sopList, sop,getApplicationContext());
                        recyclerViewLampiran.setAdapter(lampiranAdapter);
                    }else{
                        recyclerViewLampiran.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void displayListCrossfunction(){
        Intent intent = getIntent();
        callSop = service.getSopDetail(intent.getIntExtra("id",0));
        callSop.enqueue(new Callback<GetResponseDetailSop>() {
            @Override
            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()){
                    List<Crossfunction> crossfunctionList = response.body().getData().getCrossfunctionList();
                    Sop sop = response.body().getData();
                    if (crossfunctionList.size() > 0){
                        crossffunctionAdapter = new CrossfunctionProcedureAdapter(crossfunctionList,sop,getApplicationContext());
                        recyclerViewCrossfunction.setAdapter(crossffunctionAdapter);
                    }else{
                        recyclerViewCrossfunction.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void requestDownloadFile(){
        Intent intentStatus = getIntent();
        callStatus = service.changeRequestStatus(intentStatus.getIntExtra("id",0));
        callStatus.enqueue(new Callback<GetResponseDetailSop>() {
            @Override
            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
                Log.w(TAG,"onResponse: " + response);
                Toast.makeText(StandardOpDetailActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                displayDataSop();
                displayListLampiran();
                displayListCrossfunction();
            }
            @Override
            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void downloadFileSop(){
        Intent intenDownload = getIntent();
        callSop = service.getSopDetail(intenDownload.getIntExtra("id",0));
        callSop.enqueue(new Callback<GetResponseDetailSop>() {
            @Override
            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
                Log.w(TAG, "onResponse: "+ response );
                if (response.isSuccessful()){
                    Sop sop = response.body().getData();
                    tvLinkDownloadSop.setText(sop.getFile());
                    String geturl = tvLinkDownloadSop.getText().toString();

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(RetrofitBuilder.BASE_URL_FILE + geturl));
                    String title = URLUtil.guessFileName(geturl,null,null);
                    request.setTitle(title);
                    request.setDescription("Downloading File Please Wait... ");
                    String cookie = CookieManager.getInstance().getCookie(geturl);
                    request.addRequestHeader("cookie",cookie);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                    DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(StandardOpDetailActivity.this,"Downloading Started", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {

            }
        });
    }


}