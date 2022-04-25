package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.adapter.LampiranStandardProcedureAdapter;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseDetailSop;
import com.fauzighozali.mgamobile.model.GetResponseLampiran;
import com.fauzighozali.mgamobile.model.LampiranSop;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandardOpDetailActivity extends AppCompatActivity {

    private static final String TAG = "StandardOpDetail";

    private ImageView ivSop;
    private TextView tvTitleSop;
    private RelativeLayout rlFileSop;
    private LinearLayout llEmptyCourse;
    private Button btnRequest,btnDownload;

    private LampiranStandardProcedureAdapter lampiranAdapter;
    private RecyclerView recyclerViewLampiran;
    private RecyclerView.LayoutManager mLayoutManager;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseDetailSop> callSop;
    private Call<GetResponseDetailSop> callLampiran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_op_detail);

        llEmptyCourse = findViewById(R.id.linear_layout_empty_lampiran);
        ivSop = findViewById(R.id.image_view_detail_sop);
        rlFileSop = findViewById(R.id.relative_layout_file_sop);
        tvTitleSop = findViewById(R.id.text_view_title_detail_sop);
        btnRequest = findViewById(R.id.btn_request_download);
        btnDownload = findViewById(R.id.btn_download_file);

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
        displayData();
        displayListLampiran();
    }

    private void displayData() {
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
                    } else {
                        btnRequest.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.VISIBLE);
                    }
                    rlFileSop.setOnClickListener(v -> {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitBuilder.BASE_URL_FILE + sop.getFile()));
                        try{
                            StandardOpDetailActivity.this.startActivity(webIntent);
                        } catch (ActivityNotFoundException ex){
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetResponseDetailSop> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void displayListLampiran() {
        Intent intent = getIntent();
        callLampiran = service.getSopDetail(intent.getIntExtra("id", 0));
        callLampiran.enqueue(new Callback<GetResponseDetailSop>() {
            @Override
            public void onResponse(Call<GetResponseDetailSop> call, Response<GetResponseDetailSop> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                   List<LampiranSop> sopList = response.body().getData().getLampiranSopList();
                    if (sopList.size() > 0){
                        lampiranAdapter = new LampiranStandardProcedureAdapter(sopList, getApplicationContext());
                        recyclerViewLampiran.setAdapter(lampiranAdapter);
                    }else{
                        llEmptyCourse.setVisibility(View.VISIBLE);
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

}