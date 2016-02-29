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

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.fragment.CalcFragment;
import br.com.extractor.ygops.view.fragment.HomeFragment;
import br.com.extractor.ygops.view.fragment.ListDeckFragment;
import br.com.extractor.ygops.view.fragment.ListMatchFragment;
import br.com.extractor.ygops.view.fragment.ListPlayerFragment;
import io.realm.Realm;

public class MainActivity extends ParentActivity {

    private Drawer drawerResult;
    private FragmentManager fm;

    private final int HOME = 1;
    private final int PLAYERS = 2;
    private final int DECKS = 3;
    private final int MATCHES = 4;
    private final int CALCULATOR = 5;
    private final int CONFIGURATION = 7;
    private final int ABOUT = 8;

    private boolean isClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_main);
        fm = getSupportFragmentManager();

        setupNavigationDrawer();
        replaceFragment(new HomeFragment(), true);
    }

    @Override
    public void onBackPressed() {
        if (drawerResult != null && drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else if (!isClose && fm.getBackStackEntryCount() == 1) {
            replaceFragment(new HomeFragment(), true);
            drawerResult.setSelection(HOME);
        } else {
            MainActivity.this.finish();
        }
    }

    private void setupNavigationDrawer() {
        Realm realm = Realm.getDefaultInstance();
        Profile profile = realm.where(Profile.class).findFirst();

        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName(profile.getNome());
        if (profile.getImage() != null) {
            profileDrawerItem.withIcon(getDrawableFromByte(profile.getImage()));
        }

        AccountHeader headerResult = new AccountHeaderBuilder()
        .withActivity(this)
        .withCompactStyle(true)
        .addProfiles(profileDrawerItem)
        .withSelectionListEnabledForSingleProfile(false)
        .withHeaderBackground(R.color.primary)
        .build();

        ArrayList<IDrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.home));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.players));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.decks));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.matches));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.calculator));
        drawerItems.add(new DividerDrawerItem());
        drawerItems.add(new SecondaryDrawerItem().withName(R.string.configuration));
        drawerItems.add(new SecondaryDrawerItem().withName(R.string.about));

        drawerResult = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .withActionBarDrawerToggleAnimated(true)
        .withAccountHeader(headerResult)
        .withDrawerItems(drawerItems)
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {            @Override
            public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
                switch (position) {
                    case HOME:
                        replaceFragment(new HomeFragment(), true);
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
                    case CALCULATOR:
                        replaceFragment(new CalcFragment());
                        break;
                    case CONFIGURATION:
                        break;
                    case ABOUT:
                        break;
                }
                return false;
            }
        })
        .build();

        realm.close();
    }

    private void replaceFragment(Fragment fragment){
        replaceFragment(fragment, false);
    }


    private void replaceFragment(Fragment fragment, boolean close) {
        isClose = close;

        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        ft.replace(R.id.fragmentContainer, fragment);
        ft.addToBackStack(null);
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.commit();
    }

    private Drawable getDrawableFromByte(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
        return new BitmapDrawable(getResources(), bitmap);
    }

    public void toggleIconToolbar(boolean show) {
        if (getSupportActionBar() != null) {
            drawerResult.getActionBarDrawerToggle().setDrawerIndicatorEnabled(!show);
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }
}
