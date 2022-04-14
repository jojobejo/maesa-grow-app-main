package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;

public class CrossfunctionSopActivity extends AppCompatActivity {

    private static final String TAG = "CrossfunctionSOP";

    private LinearLayout llDivisionSop, llCrossfunctionSOP , llEmptySop;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

   private ApiService service;
   private TokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossfunction_sop);

        llDivisionSop = findViewById(R.id.linear_layout_Division_sop);
        llCrossfunctionSOP = findViewById(R.id.linear_layout_Crossfunction_sop);
        llEmptySop = findViewById(R.id.linear_layout_empty_course);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        recyclerView = findViewById(R.id.recycler_view_crossfunction_sop);
        layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);
    }
}