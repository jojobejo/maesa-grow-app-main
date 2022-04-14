package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.adapter.LampiranStandardProcedureAdapter;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseDetailSop;
import com.fauzighozali.mgamobile.model.GetResponseLampiran;

import retrofit2.Call;

public class StandardOpDetailActivity extends AppCompatActivity {

    private static final String TAG = "StandardOpDetail";

    private ImageView ivSop;
    private TextView tvTitleSop;
    private RelativeLayout rlFileSop;

    private LampiranStandardProcedureAdapter lampiranAdapter;
    private RecyclerView recyclerViewLampiran;
    private RecyclerView.LayoutManager mLayoutManager;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseDetailSop> callSop;
    private Call<GetResponseLampiran> callLampiran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_op_detail);

        ivSop = findViewById(R.id.image_view_detail_sop);
        rlFileSop = findViewById(R.id.relative_layout_file_sop);
        tvTitleSop = findViewById(R.id.text_view_title_sop);
        recyclerViewLampiran = findViewById(R.id.recycler_view_lampiran_sop);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewLampiran.setLayoutManager(mLayoutManager);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

    }



}