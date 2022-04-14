package com.fauzighozali.mgamobile.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.model.LampiranSop;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

public class LampiranStandardProcedureAdapter extends RecyclerView.Adapter<LampiranStandardProcedureAdapter.LampiranViewHolder> {

    private static final String TAG = "StandardProcedureAdapter";
    private List<LampiranSop> dataModelArrayList;
    private Context context;

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
        LampiranSop lampiran = dataModelArrayList.get(position);

        holder.tvTitle.setText(lampiran.getName());
    }

    @Override
    public int getItemCount() {
        return (dataModelArrayList != null) ? dataModelArrayList.size() : 0;
    }

    public class LampiranViewHolder extends  RecyclerView.ViewHolder {
        private TextView tvTitle;

        public LampiranViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.text_view_title_lampiran);
        }
    }
}