package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.adapter.StandardProcedureAdapter;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseSopDivision;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandardOpActivity extends AppCompatActivity {

    private static final String TAG = "StandardOpActivity" ;

    private LinearLayout llDivisionSOP , llCrossfunctionSOP, llEmptySop;
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

        llDivisionSOP = findViewById(R.id.linear_layout_Division_sop);
        llCrossfunctionSOP = findViewById(R.id.linear_layout_Crossfunction_sop);
        llEmptySop = findViewById(R.id.linear_layout_empty_course);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        recyclerView = findViewById(R.id.recycler_view_division_sop);
        layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);

        llCrossfunctionSOP.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CrossfunctionSopActivity.class);
            startActivity(intent);
        });

        getSopDivisionList();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getSopDivisionList();
    }

    private void getSopDivisionList() {

//        int organization_id = tokenManager.getToken().getOrganization_id();

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
                Log.w(TAG, "onFailre" +t.getMessage());
            }
        });
    }


}