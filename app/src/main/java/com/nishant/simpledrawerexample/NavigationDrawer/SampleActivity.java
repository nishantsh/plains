package com.nishant.simpledrawerexample.NavigationDrawer;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nishant.simpledrawerexample.R;

/**
 * Created by nishant on 24/1/17.
 */

public class SampleActivity extends AppCompatActivity {

    final int[] viewWidth = new int[1];
    final int[] viewHeight = new int[1];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        final ImageView image = (ImageView) findViewById(R.id.imgVw_sample);
        ImageView imgCenter = (ImageView) findViewById(R.id.imgVw_sample_center);
        imgCenter.setVisibility(View.GONE);
        ViewTreeObserver viewTreeObserver = image.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    viewWidth[0] = image.getWidth();
                    viewHeight[0] = image.getHeight();
                }
            });
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveViewToCenter(image);
            }
        }, 2000);
        initToolbar();
    }

    public void moveViewToCenter(final View view) {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarHeight = getStatusBarHeight();
        Log.d("TAG", "Output statusBarHeight: " + statusBarHeight);
        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);
        Log.d("TAG", "OutputViewPositions: " + originalPos[0] + "  =  " + originalPos[1]);
        int xDest = dm.widthPixels / 2;

        int centerPointWidth = 0;
        if (viewWidth[0] != 0) {
            centerPointWidth = xDest - viewWidth[0] / 2;
        }
        Log.d("TAG", "Output DM-Width: " + xDest);
        xDest -= (view.getMeasuredWidth() / 2);
        int yDest = dm.heightPixels / 2;
        int centerPointHeight = yDest - viewHeight[0];
        Log.d("TAG", "Output DM-Height: " + yDest);
        TranslateAnimation anim = new TranslateAnimation(0, centerPointWidth, 0, 0);
        anim.setDuration(2000);
        view.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.imgVw_sample).setVisibility(View.GONE);
                findViewById(R.id.imgVw_sample_center).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //Set the custom toolbar
    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView txtVw = (TextView) findViewById(R.id.toolbar_title);
        txtVw.setText("Sample Activity");
        int tHeight = toolbar.getHeight();
        getToolBarHeight(tHeight);
        Log.d("TAG", "Output ToolbarHeight: " + getToolBarHeight(tHeight));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleActivity.this.finish();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getToolBarHeight(int resourceId) {
        int result = 0;
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}