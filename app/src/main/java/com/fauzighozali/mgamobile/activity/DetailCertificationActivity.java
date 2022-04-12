package com.fauzighozali.mgamobile.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.api.ApiService;
import com.fauzighozali.mgamobile.api.RetrofitBuilder;
import com.fauzighozali.mgamobile.jwt.TokenManager;
import com.fauzighozali.mgamobile.model.Course;
import com.fauzighozali.mgamobile.model.GetResponseDetailCourse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCertificationActivity extends AppCompatActivity {

    private static final String TAG = "DetailCertification";

    private int CurrentProgress = 0;
    private ProgressBar progressBar;

    private Toolbar mToolbar;
    private ImageView ivCertification, ivNextPendahuluan, ivNextMateriPembelajaran, ivWatchVideo, ivNextTes;
    private TextView mTitleToolbar, tvTitle;
    private RelativeLayout rlVideo;
    private View viewVideo;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<GetResponseDetailCourse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_certification);

        mToolbar = findViewById(R.id.toolbar);
        mTitleToolbar = mToolbar.findViewById(R.id.toolbar_title);
        ivCertification = findViewById(R.id.image_view_detail_certification);
        ivNextPendahuluan = findViewById(R.id.image_view_next_pendahualan);
        ivNextMateriPembelajaran = findViewById(R.id.image_view_next_materi_pembelajaran);
        ivWatchVideo = findViewById(R.id.image_view_next_video_materi_pembelajaran);
        ivNextTes = findViewById(R.id.image_view_next_tes);
        rlVideo = findViewById(R.id.relative_layout_video);
        viewVideo = findViewById(R.id.view_video);
        tvTitle = findViewById(R.id.text_view_detail_title_course);


        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        displayData();
    }

    private void displayData() {
        Intent intent = getIntent();
        call = service.getCourseDetail(intent.getIntExtra("course_id", 0));
        call.enqueue(new Callback<GetResponseDetailCourse>() {
            @Override
            public void onResponse(Call<GetResponseDetailCourse> call, Response<GetResponseDetailCourse> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    Course course = response.body().getData();
                    Glide.with(getApplicationContext())
                            .load(RetrofitBuilder.BASE_URL_FILE + course.getImage())
                            .into(ivCertification);
                    tvTitle.setText(course.getTitle());
                    ivNextPendahuluan.setOnClickListener(v -> {
                        Intent intentToPendahuluan = new Intent(DetailCertificationActivity.this, PendahuluanActivity.class);
                        intentToPendahuluan.putExtra("description", course.getDescription());
                        startActivity(intentToPendahuluan);
                    });
                    ivNextMateriPembelajaran.setOnClickListener(v -> {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitBuilder.BASE_URL_FILE + course.getFile()));
                        try {
                            DetailCertificationActivity.this.startActivity(webIntent);
                        } catch (ActivityNotFoundException ex) {

                        }
                    });
                    if (course.getVideo().equals("") && course.getLink().equals("")) {
                        rlVideo.setVisibility(View.GONE);
                        viewVideo.setVisibility(View.GONE);
                    }else {
                        rlVideo.setVisibility(View.VISIBLE);
                        viewVideo.setVisibility(View.VISIBLE);
                    }
                    ivWatchVideo.setOnClickListener(v -> {
                        if (!course.getVideo().equals("")){
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitBuilder.BASE_URL_FILE + course.getVideo()));
                            try {
                                DetailCertificationActivity.this.startActivity(webIntent);
                            } catch (ActivityNotFoundException ex) {

                            }
                        }else {
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(course.getLink()));
                            try {
                                DetailCertificationActivity.this.startActivity(webIntent);
                            } catch (ActivityNotFoundException ex) {

                            }
                        }
                    });
                    ivNextTes.setOnClickListener(v -> displayConfirmasi());
                }
            }

            @Override
            public void onFailure(Call<GetResponseDetailCourse> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayConfirmasi(){
        Intent intent = getIntent();
        call = service.getCourseDetail(intent.getIntExtra("course_id",0));
        AlertDialog.Builder builderConfrm = new AlertDialog.Builder(this);
        builderConfrm.setCancelable(false);
        builderConfrm.setMessage("Apakah anda yakin akan memulai test ini ? ");
        builderConfrm.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cofrmDisplay();
            }
        });
        builderConfrm.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertconfirm = builderConfrm.create();
        alertconfirm.show();
    }

    public void cofrmDisplay(){
        Intent intent = getIntent();
        call = service.getCourseDetail(intent.getIntExtra("course_id", 0));
        AlertDialog.Builder buildDisplayCofrm = new AlertDialog.Builder(this);
        buildDisplayCofrm.setCancelable(false);
        buildDisplayCofrm.setMessage("test akan dimulai , semoga berhasil ");
        buildDisplayCofrm.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentToTestAct = new Intent (DetailCertificationActivity.this, TesActivity.class);
                intentToTestAct.putExtra("course_id", intent.getIntExtra("course_id",0));
                startActivity(intentToTestAct);
                finish();
            }
        });
        AlertDialog alert = buildDisplayCofrm.create();
        alert.show();
    }
}
