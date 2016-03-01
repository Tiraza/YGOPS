package br.com.extractor.ygops.util;

import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

/**
 * Created by Muryllo Tiraza on 01/03/2016.
 */
public final class CustomAnimations {

    private static final CustomAnimations INSTANCE = new CustomAnimations();

    private CustomAnimations() {
    }

    public static CustomAnimations getInstance() {
        return INSTANCE;
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);

        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);

        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 100) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(finalCount + "");
                }
            }, time);
        }
    }
}
