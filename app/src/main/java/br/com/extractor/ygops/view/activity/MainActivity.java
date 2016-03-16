package br.com.extractor.ygops.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.activity.edit.ProfileEditActivity;
import br.com.extractor.ygops.view.fragment.CalcFragment;
import br.com.extractor.ygops.view.fragment.HomeFragment;
import br.com.extractor.ygops.view.fragment.ListDeckFragment;
import br.com.extractor.ygops.view.fragment.ListMatchFragment;
import br.com.extractor.ygops.view.fragment.ListPlayerFragment;
import io.realm.Realm;

public class MainActivity extends ParentActivity {

    private Drawer drawerResult;
    private AccountHeader headerResult;
    private ProfileDrawerItem profileDrawerItem;
    private FragmentManager fm;

    private final int HOME = 1;
    private final int PLAYERS = 2;
    private final int DECKS = 3;
    private final int MATCHES = 4;
    private final int CALCULATOR = 5;
    private final int CONFIGURATION = 7;
    private final int ABOUT = 8;

    private boolean isClose = false;
    private static final Integer PROFILE_EDIT = 1;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PROFILE_EDIT) {
            Profile profile = realm.where(Profile.class).findFirst();

            profileDrawerItem.withName(profile.getNome());
            if (profile.getImage() != null) {
                profileDrawerItem.withIcon(ImageUtils.getInstance().getRoundedCornerBitmap(profile.getImage()));
            }

            headerResult.updateProfile(profileDrawerItem);
        }
    }

    private void setupNavigationDrawer() {
        Profile profile = realm.where(Profile.class).findFirst();

        profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName(profile.getNome());
        if (profile.getImage() != null) {
            profileDrawerItem.withIcon(ImageUtils.getInstance().getRoundedCornerBitmap(profile.getImage()));
        }

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .addProfiles(profileDrawerItem)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.color.primary)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile iProfile, boolean b) {
                        startActivityForResult(new Intent(MainActivity.this, ProfileEditActivity.class), PROFILE_EDIT);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile iProfile, boolean b) {
                        return false;
                    }
                })
                .build();

        ArrayList<IDrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.home).withIcon(R.drawable.ic_home_normal).withSelectedIcon(R.drawable.ic_home_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.players).withIcon(R.drawable.ic_players_normal).withSelectedIcon(R.drawable.ic_players_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.decks).withIcon(R.drawable.ic_deck_normal).withSelectedIcon(R.drawable.ic_deck_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.matches).withIcon(R.drawable.ic_match_normal).withSelectedIcon(R.drawable.ic_match_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.calculator).withIcon(R.drawable.ic_calc_normal).withSelectedIcon(R.drawable.ic_calc_pressed));
        drawerItems.add(new DividerDrawerItem());
        drawerItems.add(new SecondaryDrawerItem().withName(R.string.configuration));
        drawerItems.add(new SecondaryDrawerItem().withName(R.string.about));

        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .withDrawerItems(drawerItems)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
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
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        onBackPressed();
                        return true;
                    }
                })
                .build();
    }

    private void replaceFragment(Fragment fragment) {
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
        ft.commit();
    }

    public void toggleIconToolbar(boolean show) {
        if (getSupportActionBar() != null) {
            if (show) {
                drawerResult.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                drawerResult.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
            }
        }
    }
}
