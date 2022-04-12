package com.fauzighozali.mgamobile.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;

import androidx.core.content.IntentCompat;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.activity.FullScreenVideoActivity;
import com.fauzighozali.mgamobile.activity.HomeActivity;

public class FullScreenMediaController extends MediaController {
    public static ImageButton fullScreen;
    private String isFullScreen;

    public FullScreenMediaController(Context context) {
        super(context);
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        fullScreen = new ImageButton (super.getContext());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 80;
        addView(fullScreen, params);

        isFullScreen =  ((Activity)getContext()).getIntent().getStringExtra("fullScreenInd");

        if("y".equals(isFullScreen)){
            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit);
        }else{
            fullScreen.setImageResource(R.drawable.ic_fullscreen);
        }

        fullScreen.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FullScreenVideoActivity.class);
            if("y".equals(isFullScreen)){
                intent.putExtra("fullScreenInd", "");
            }else{
                intent.putExtra("fullScreenInd", "y");
            }
            getContext().startActivity(intent);
        });
    }
}
