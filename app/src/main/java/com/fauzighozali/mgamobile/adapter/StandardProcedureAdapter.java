package com.fauzighozali.mgamobile.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.activity.DetailCertificationActivity;
import com.fauzighozali.mgamobile.activity.LoginActivity;
import com.fauzighozali.mgamobile.activity.StandardOpDetailActivity;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseMessage;
import com.fauzighozali.mgamobile.model.GetResponseSopDivision;
import com.fauzighozali.mgamobile.model.Sop;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandardProcedureAdapter extends RecyclerView.Adapter<StandardProcedureAdapter.StandardProcedureViewHolder> {

    private static final String TAG = "StandardProcedureAdapter";

    private List<Sop> dataModelArrayList;
    private Context context;
    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseMessage> call;

    public StandardProcedureAdapter(List<Sop> dataModelArrayList, Context context){
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StandardProcedureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_standard_procedure_adapter, parent, false);
        return new StandardProcedureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StandardProcedureViewHolder standardProcedureAdapter , int position) {
        Sop sop = dataModelArrayList.get(position);

        standardProcedureAdapter.tvTitle.setText(sop.getTitle());
        standardProcedureAdapter.tvDesc.setText(sop.getDescription());
        Glide.with(context)
                .load(RetrofitBuilder.BASE_URL_FILE + sop.getImage())
                .into(standardProcedureAdapter.ivSop);

        standardProcedureAdapter.itemView.setOnClickListener(v -> {
           Intent intent = new Intent(v.getContext(), StandardOpDetailActivity.class);
           intent.putExtra("id", sop.getId());
           v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (dataModelArrayList != null) ? dataModelArrayList.size() : 0;
    }

    public class StandardProcedureViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivSop;
        private TextView tvTitle, tvDesc,tvLampiran;

        public StandardProcedureViewHolder (View itemView){
            super(itemView);

            ivSop = itemView.findViewById(R.id.image_view_detail_sop);
            tvTitle = itemView.findViewById(R.id.text_view_title_sop);
            tvDesc = itemView.findViewById(R.id.text_view_desc_sop);
        }
    }
}