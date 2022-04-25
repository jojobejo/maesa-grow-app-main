package com.fauzighozali.mgamobile.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.activity.LoginActivity;
import com.fauzighozali.mgamobile.activity.StandardOpDetailActivity;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseDetailSop;
import com.fauzighozali.mgamobile.model.GetResponseLampiran;
import com.fauzighozali.mgamobile.model.LampiranSop;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;

public class LampiranStandardProcedureAdapter extends RecyclerView.Adapter<LampiranStandardProcedureAdapter.LampiranViewHolder> {

    private static final String TAG = "StandardProcedureAdapter";
    private List<LampiranSop> dataModelArrayList;
    private Context context;
    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseLampiran> call;

    public LampiranStandardProcedureAdapter(List<LampiranSop> dataModelArrayList, Context context){
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public LampiranStandardProcedureAdapter.LampiranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.activity_lampiran_standard_procedure_adapter, parent, false);
        return new LampiranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LampiranStandardProcedureAdapter.LampiranViewHolder holder, int position) {
        LampiranSop lampiranSop = dataModelArrayList.get(position);
        holder.tvTitle.setText(lampiranSop.getName());

        if (lampiranSop.getStatus() == 0){
            holder.btnDownload.setVisibility(View.GONE);
            holder.btnRequest.setVisibility(View.VISIBLE);
        } else{
            holder.btnDownload.setVisibility(View.VISIBLE);
            holder.btnRequest.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitBuilder.BASE_URL_FILE + lampiranSop.getFile()));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (dataModelArrayList != null) ? dataModelArrayList.size() : 0;
    }

    public class LampiranViewHolder extends  RecyclerView.ViewHolder {
        private TextView tvTitle;
        private Button btnRequest,btnDownload;

        public LampiranViewHolder(View itemView){
            super(itemView);

            btnDownload = itemView.findViewById(R.id.btn_download_file);
            btnRequest = itemView.findViewById(R.id.btn_request_download);
            tvTitle = itemView.findViewById(R.id.text_view_title_lampiran);
        }
    }
}