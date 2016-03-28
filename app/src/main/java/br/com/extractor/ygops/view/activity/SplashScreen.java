package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Random;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.SplashView;
import br.com.extractor.ygops.view.ParentActivity;

public class SplashScreen extends ParentActivity {

    private SplashView mSplashView;
    private ViewGroup mMainView;
    private View mContentView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainView = new FrameLayout(getApplicationContext());

        // create the splash view
        mSplashView = new SplashView(getApplicationContext());
        mSplashView.setRemoveFromParentOnEnd(true);
        mSplashView.setSplashBackgroundColor(getResources().getColor(R.color.primary_dark));
        mSplashView.setRotationRadius(getResources().getDimensionPixelOffset(R.dimen.splash_rotation_radius));
        mSplashView.setCircleRadius(getResources().getDimensionPixelSize(R.dimen.splash_circle_radius));
        mSplashView.setRotationDuration(getResources().getInteger(R.integer.splash_rotation_duration));
        mSplashView.setSplashDuration(getResources().getInteger(R.integer.splash_duration));
        mSplashView.setCircleColors(new ColorGenerator().getColors());

        // add splash view to the parent view
        mMainView.addView(mSplashView);
        setContentView(mMainView);

        startLoadingData();
    }

    private void startLoadingData() {
        Random random = new Random();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadingDataEnded();
            }
        }, 1000 + random.nextInt(2000));
    }

    private void onLoadingDataEnded() {
        mSplashView.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onUpdate(float completionFraction) {

            }

            @Override
            public void onEnd() {

            }
        });
    }


}
