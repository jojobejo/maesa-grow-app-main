package com.fauzighozali.mgamobile.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.activity.FileReaderLampiranActivity;
import com.fauzighozali.mgamobile.activity.StandardOpActivity;
import com.fauzighozali.mgamobile.activity.StandardOpDetailActivity;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.GetResponseLampiran;
import com.fauzighozali.mgamobile.model.LampiranSop;
import com.fauzighozali.mgamobile.model.Sop;

import java.util.List;

import retrofit2.Call;

public class LampiranStandardProcedureAdapter extends RecyclerView.Adapter<LampiranStandardProcedureAdapter.LampiranViewHolder> {

    private static final String TAG = "StandardProcedureAdapter";
    private List<LampiranSop> dataModelArrayList;
    private Sop dataModelArrayListSop;
    private Context context;
    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseLampiran> call;


//    public void LampiranStandardProcedureAdapterSop(Sop dataModelArrayListSop, Context context){
//        this.dataModelArrayListSop = dataModelArrayListSop;
//        this.context = context;
//    }

    public LampiranStandardProcedureAdapter(List<LampiranSop> dataModelArrayList,Sop dataModelArraySop, Context context){
        this.dataModelArrayListSop = dataModelArraySop;
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
        Sop sop = dataModelArrayListSop;
//        Sop sopListStatus = dataModelArrayListSop;

        holder.tvTitle.setText(lampiranSop.getName());

            if (sop.getStatus()==0){
                holder.btnDownload.setVisibility(View.GONE);
            } else {
                holder.btnDownload.setVisibility(View.GONE);
            }
            if (sop.getStatus()==2){
                holder.btnDownload.setVisibility(View.VISIBLE);
            }


        holder.btnDownload.setOnClickListener(v -> {
            holder.tvLinkFileDownload.setText(lampiranSop.getFile());
            String getUrlDownload = holder.tvLinkFileDownload.getText().toString();

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(RetrofitBuilder.BASE_URL_FILE + getUrlDownload));
            String title = URLUtil.guessFileName(getUrlDownload,null,null);
            request.setTitle(title);
            request.setDescription("Downloading File Pleade Wait ....");
            String cookie = CookieManager.getInstance().getCookie(getUrlDownload);
            request.addRequestHeader("cookie", cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

            Toast.makeText(v.getContext(), "Downloading File", Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FileReaderLampiranActivity.class);
            intent.putExtra("file",lampiranSop.getFile());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (dataModelArrayList != null) ? dataModelArrayList.size() : 0;
    }

    public class LampiranViewHolder extends  RecyclerView.ViewHolder {
        private TextView tvTitle,tvLinkFileDownload;
        private Button btnDownload;

        public LampiranViewHolder(View itemView){
            super(itemView);

            btnDownload = itemView.findViewById(R.id.btn_download_file);
            tvTitle = itemView.findViewById(R.id.text_view_title_lampiran_sop);
            tvLinkFileDownload = itemView.findViewById(R.id.link_download_file_lampiran);
        }
    }
}