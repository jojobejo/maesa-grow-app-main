package com.fauzighozali.mgamobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.activity.LoginActivity;
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

public class SopDivisionFragment extends Fragment {

    private static final String TAG = "SopDivisionFragment";

    private LinearLayout llEmpLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private StandardProcedureAdapter sopAdapter;
    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseSopDivision> call;


    public static Fragment getInstance(){
        SopDivisionFragment sopDivisionFragment = new SopDivisionFragment();
        return sopDivisionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sop_division, container,false);

        llEmpLayout = view.findViewById(R.id.linear_layout_empty_sop);

        tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null){
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }

        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        recyclerView = view.findViewById(R.id.recycler_view_division_sop);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getSopDivisionList();

        return view;
    }

    private void getSopDivisionList(){
        call = service.getSopDivision();
        call.enqueue(new Callback<GetResponseSopDivision>() {
            @Override
            public void onResponse(Call<GetResponseSopDivision> call, Response<GetResponseSopDivision> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()){
                    List<Sop> sopList = response.body().getData();
                    if (sopList.size() > 0){
                        sopAdapter = new StandardProcedureAdapter(sopList, getContext());
                        recyclerView.setAdapter(sopAdapter);
                    }else{
                        llEmpLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetResponseSopDivision> call, Throwable t) {
                Log.w(TAG, "onFailure" + t.getMessage());
            }
        });
    }
}