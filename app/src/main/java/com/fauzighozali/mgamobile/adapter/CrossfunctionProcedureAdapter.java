package com.fauzighozali.mgamobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.activity.FileReaderCrossfunctionActivity;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.Crossfunction;
import com.fauzighozali.mgamobile.model.GetResponseCrossfunction;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;

public class CrossfunctionProcedureAdapter extends RecyclerView.Adapter<CrossfunctionProcedureAdapter.CrossfunctionViewHolder> {

    private static final String TAG = "CrossfunctionProcrdureAdapter";
    private List<Crossfunction> dataModelArrayList;
    private Sop dataModelArrayListCrossfunction;
    private Context context;
    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseCrossfunction> call;

    public CrossfunctionProcedureAdapter(List<Crossfunction> dataModelArrayList,Sop dataModelArrayListCrossfunction, Context context){
        this.dataModelArrayList = dataModelArrayList;
        this.dataModelArrayListCrossfunction = dataModelArrayListCrossfunction;
        this.context = context;
    }


    @NonNull
    @Override
    public CrossfunctionProcedureAdapter.CrossfunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_crossfunction_procedure_adapter, parent, false);
        return new CrossfunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrossfunctionProcedureAdapter.CrossfunctionViewHolder holder, int position) {
    Crossfunction crossfunction = dataModelArrayList.get(position);
    Sop sop = dataModelArrayListCrossfunction;

    holder.tvTitle.setText(crossfunction.getName());

    if (sop.getStatus()==0){
        holder.btnDownload.setVisibility(View.GONE);
    }else{
        holder.btnDownload.setVisibility(View.GONE);
    } if (sop.getStatus()==2){
        holder.btnDownload.setVisibility(View.VISIBLE);
        }

    holder.itemView.setOnClickListener(v -> {
        Intent intent = new Intent(v.getContext(), FileReaderCrossfunctionActivity.class);
        intent.putExtra("file",sop.getFile());
        v.getContext().startActivity(intent);
    });

    }

    @Override
    public int getItemCount() {
        return (dataModelArrayList != null)? dataModelArrayList.size():0;
    }

    public  class CrossfunctionViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private Button btnDownload;

        public CrossfunctionViewHolder(@NonNull View itemView) {
            super(itemView);

            btnDownload = itemView.findViewById(R.id.btn_download_file);
            tvTitle = itemView.findViewById(R.id.text_view_title_crossfunction);
        }
    }
}
