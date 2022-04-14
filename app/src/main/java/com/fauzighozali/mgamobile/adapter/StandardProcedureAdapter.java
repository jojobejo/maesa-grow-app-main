package com.fauzighozali.mgamobile.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.activity.StandardOpDetailActivity;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseMessage;
import com.fauzighozali.mgamobile.model.GetResponseSopDivision;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;

public class StandardProcedureAdapter extends RecyclerView.Adapter<StandardProcedureAdapter.StandardProcedureViewHolder> {

    private static final String TAG = "StandardProcedureAdapter";
    private List<Sop> dataModelArrayList;
    private Context context;

    public StandardProcedureAdapter(List<Sop> dataModelArrayList, Context context){
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StandardProcedureAdapter.StandardProcedureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_standard_procedure_adapter, parent, false);
        return new StandardProcedureViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StandardProcedureAdapter.StandardProcedureViewHolder holder, int position) {
        Sop sop = dataModelArrayList.get(position);

        holder.tvTitle.setText(sop.getTitle());
        holder.tvDesc.setText(sop.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), StandardOpDetailActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (dataModelArrayList != null) ? dataModelArrayList.size() : 0;
    }

    public class StandardProcedureViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDoc;
        private TextView tvTitle, tvDesc;

        public StandardProcedureViewHolder(View itemView){
            super(itemView);
            ivDoc = itemView.findViewById(R.id.image_view_doc);
            tvTitle = itemView.findViewById(R.id.text_view_title_sop);
            tvDesc = itemView.findViewById(R.id.text_view_desc_sop);
        }
    }
}