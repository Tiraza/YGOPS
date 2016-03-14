package br.com.extractor.ygops.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.Random;

import br.com.extractor.ygops.R;

/**
 * Created by Muryllo Tiraza on 11/03/2016.
 */
public class DicePicker {

    private Dialog dialog;
    private ImageView imgDice;
    private AnimationDrawable diceAnimation;

    private static final Integer DURATION = 1100;

    public DicePicker(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dice_picker);

        imgDice = (ImageView) dialog.findViewById(R.id.imgDice);
        imgDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDiceAnimation();
            }
        });

        startDiceAnimation();
    }

    public void show() {
        dialog.show();
    }

    private void startDiceAnimation(){
        imgDice.setBackgroundResource(R.drawable.dice_animation);
        diceAnimation = (AnimationDrawable) imgDice.getBackground();
        diceAnimation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                diceAnimation.stop();

                Random r = new Random();
                int low = 1;
                int high = 7;
                int result = r.nextInt(high-low) + low;

                switch (result){
                    case 1:
                        imgDice.setBackgroundResource(R.drawable.ic_dice_1);
                        break;
                    case 2:
                        imgDice.setBackgroundResource(R.drawable.ic_dice_2);
                        break;
                    case 3:
                        imgDice.setBackgroundResource(R.drawable.ic_dice_3);
                        break;
                    case 4:
                        imgDice.setBackgroundResource(R.drawable.ic_dice_4);
                        break;
                    case 5:
                        imgDice.setBackgroundResource(R.drawable.ic_dice_5);
                        break;
                    case 6:
                        imgDice.setBackgroundResource(R.drawable.ic_dice_6);
                        break;
                }
            }
        }, DURATION);
    }
}
