package br.com.extractor.ygops.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.activity.register.ProfileRegisterActivity;
import io.realm.Realm;

public class SplashScreen extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int SPLASH_DISPLAY_LENGTH = 1500;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Profile profile = Realm.getDefaultInstance().where(Profile.class).findFirst();

                Intent intent;
                if (profile == null) {
                    intent = new Intent(SplashScreen.this, ProfileRegisterActivity.class);
                } else {
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                }

                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
