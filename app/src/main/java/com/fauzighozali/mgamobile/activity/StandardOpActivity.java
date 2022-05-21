package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.adapter.StandardProcedureAdapter;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseSopDivision;
import com.fauzighozali.mgamobile.model.LampiranSop;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandardOpActivity extends AppCompatActivity {

    private static final String TAG = "StandardOpActivity" ;

    private Toolbar mToolbar;
    private TextView mTilteToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout llDivisionSOP , llEmptySop;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private StandardProcedureAdapter sopAdapter;
    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseSopDivision> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_op);


        mToolbar = findViewById(R.id.toolbar);
        mTilteToolbar = mToolbar.findViewById(R.id.toolbar_title);
        mTilteToolbar.setText("Standar Oprasional Procedure List");
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        llDivisionSOP = findViewById(R.id.linear_layout_Division_sop);
        llEmptySop = findViewById(R.id.linear_layout_empty_course);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        recyclerView = findViewById(R.id.recycler_view_division_sop);
        layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(()->{
            getSopDivisionList();
            new Handler().postDelayed(()-> swipeRefreshLayout.setRefreshing(false), 2500);
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.gradient_start_color);

        getSopDivisionList();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getSopDivisionList();
    }

    private void getSopDivisionList() {
        call = service.getSopDivision();
        call.enqueue(new Callback<GetResponseSopDivision>() {
            @Override
            public void onResponse(Call<GetResponseSopDivision> call, Response<GetResponseSopDivision> response) {
                Log.w(TAG, "onResponse: " + response);
                if(response.isSuccessful()){
                    List<Sop> sopList = response.body().getData();
                    if (sopList.size() > 0){
                        sopAdapter = new StandardProcedureAdapter(sopList, getApplicationContext());
                        recyclerView.setAdapter(sopAdapter);
                    }else {
                        llEmptySop.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetResponseSopDivision> call, Throwable t) {
                Log.w(TAG, "onFailure" +t.getMessage());
            }
        });
    }
}