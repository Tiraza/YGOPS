package br.com.extractor.ygops.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.view.ParentActivity;
import io.realm.Realm;

public class SplashScreen extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int SPLASH_DISPLAY_LENGTH = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();
                Profile profile = realm.where(Profile.class).findFirst();

                Intent intent;
                if(profile == null){
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
