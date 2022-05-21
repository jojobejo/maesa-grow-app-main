package com.fauzighozali.mgamobile.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseCrossfunction;

import retrofit2.Call;

public class SopCrossfunctionFragment extends Fragment {

    private static final String TAG = "SopCrossfunctionFragment";

    private LinearLayout llEmptyCross;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseCrossfunction> call;

    public static Fragment getInstance(){
        SopCrossfunctionFragment sopCrossfunctionFragment = new SopCrossfunctionFragment();
        return sopCrossfunctionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sop_crossfunction, container, false);
    }
}