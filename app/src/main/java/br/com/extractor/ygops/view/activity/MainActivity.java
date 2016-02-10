package br.com.extractor.ygops.view.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ImageHelper;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.fragment.ListDeckFragment;
import br.com.extractor.ygops.view.fragment.ListMatchFragment;
import br.com.extractor.ygops.view.fragment.ListPlayerFragment;
import io.realm.Realm;

public class MainActivity extends ParentActivity {

    private Drawer.Result drawerResult;

    private final int HOME = 0;
    private final int PLAYERS = 1;
    private final int DECKS = 2;
    private final int MATCHES = 3;
    private final int CONFIGURATION = 5;
    private final int ABOUT = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_main);
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer() {
        Realm realm = Realm.getDefaultInstance();
        Profile profile = realm.where(Profile.class).findFirst();

        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName(profile.getNome());
        if(profile.getImage() != null){
            profileDrawerItem.withIcon(getDrawableFromByte(profile.getImage()));
        }

        AccountHeader headerResult = new AccountHeader();
        headerResult.withActivity(this);
        headerResult.withCompactStyle(true);
        headerResult.addProfiles(profileDrawerItem);
        headerResult.withSelectionListEnabledForSingleProfile(false);
        headerResult.withHeaderBackground(R.color.primary);

        ArrayList<IDrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.home));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.players));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.decks));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.matches));
        drawerItems.add(new DividerDrawerItem());
        drawerItems.add(new SecondaryDrawerItem().withName(R.string.configuration));
        drawerItems.add(new SecondaryDrawerItem().withName(R.string.about));

        Drawer drawer = new Drawer();
        drawer.withActivity(this);
        drawer.withToolbar(toolbar);
        drawer.withActionBarDrawerToggleAnimated(true);
        drawer.withAccountHeader(headerResult.build());
        drawer.withDrawerItems(drawerItems);
        drawer.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, IDrawerItem iDrawerItem) {
                switch (position) {
                    case HOME:
                        break;
                    case PLAYERS:
                        replaceFragment(new ListPlayerFragment());
                        break;
                    case DECKS:
                        replaceFragment(new ListDeckFragment());
                        break;
                    case MATCHES:
                        replaceFragment(new ListMatchFragment());
                        break;
                    case CONFIGURATION:
                        break;
                    case ABOUT:
                        break;
                }
            }
        });

        drawerResult = drawer.build();
        realm.close();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.fragmentContainer, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private Drawable getDrawableFromByte(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
        return new BitmapDrawable(getResources(), bitmap);
    }

    public void toggleIconToolbar(boolean show){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }
}
