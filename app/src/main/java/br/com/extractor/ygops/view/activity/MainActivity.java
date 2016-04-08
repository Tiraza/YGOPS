package br.com.extractor.ygops.view.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.lapism.searchview.view.SearchCodes;
import com.lapism.searchview.view.SearchView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.util.SplashView;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.activity.edit.ProfileEditActivity;
import br.com.extractor.ygops.view.activity.register.ProfileRegisterActivity;
import br.com.extractor.ygops.view.fragment.CalcFragment;
import br.com.extractor.ygops.view.fragment.HomeFragment;
import br.com.extractor.ygops.view.fragment.ListDeckFragment;
import br.com.extractor.ygops.view.fragment.ListMatchFragment;
import br.com.extractor.ygops.view.fragment.ListPlayerFragment;
import io.realm.Realm;

public class MainActivity extends ParentActivity {

    private FragmentManager fm;
    private ViewGroup mMainView;
    private SplashView mSplashView;
    private Handler mHandler = new Handler();

    private final int HOME = 1;
    private final int PLAYERS = 2;
    private final int DECKS = 3;
    private final int MATCHES = 4;
    private final int CALCULATOR = 5;
    private final int PROFILE = 7;
    //private final int CONFIGURATION = 7;
    //private final int ABOUT = 7;

    private boolean isClose = false;
    private static final Integer PROFILE_EDIT = 1;

    private Profile profile;
    private Drawer drawerResult;
    private AccountHeader headerResult;
    private ProfileDrawerItem profileDrawerItem;
    private static ArrayList<IDrawerItem> drawerItems = new ArrayList<>();
    private Boolean keyboardUp = false;

    private SearchView mSearchView;

    static {
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.home).withIcon(R.drawable.ic_home_normal).withSelectedIcon(R.drawable.ic_home_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.players).withIcon(R.drawable.ic_players_normal).withSelectedIcon(R.drawable.ic_players_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.decks).withIcon(R.drawable.ic_deck_normal).withSelectedIcon(R.drawable.ic_deck_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.matches).withIcon(R.drawable.ic_match_normal).withSelectedIcon(R.drawable.ic_match_pressed));
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.calculator).withIcon(R.drawable.ic_calc_normal).withSelectedIcon(R.drawable.ic_calc_pressed));
        drawerItems.add(new DividerDrawerItem());
        drawerItems.add(new PrimaryDrawerItem().withName(R.string.profile).withIcon(R.drawable.ic_person).withSelectedIcon(R.drawable.ic_person));
        //drawerItems.add(new SecondaryDrawerItem().withName(R.string.configuration));
        //drawerItems.add(new SecondaryDrawerItem().withName(R.string.about));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_main);
        profile = Realm.getDefaultInstance().where(Profile.class).findFirst();

        if(profile != null) {
            fm = getSupportFragmentManager();

            setupNavigationDrawer();
            mMainView = (FrameLayout) findViewById(R.id.splashContainer);

            mSplashView = new SplashView(this);
            mSplashView.setRemoveFromParentOnEnd(true);
            mSplashView.setSplashBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
            mSplashView.setRotationRadius(getResources().getDimensionPixelOffset(R.dimen.splash_rotation_radius));
            mSplashView.setCircleRadius(getResources().getDimensionPixelSize(R.dimen.splash_circle_radius));
            mSplashView.setRotationDuration(getResources().getInteger(R.integer.splash_rotation_duration));
            mSplashView.setSplashDuration(getResources().getInteger(R.integer.splash_duration));
            mSplashView.setCircleColors(new ColorGenerator().getColors());

            final View activityRootView = findViewById(R.id.activityRoot);
            activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    activityRootView.getWindowVisibleDisplayFrame(r);

                    int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                    if (heightDiff > 100) {
                        keyboardUp = true;
                    } else if (heightDiff < 100 && keyboardUp) {
                        if(mSearchView != null && mSearchView.isSearchOpen()){
                            mSearchView.hide(true);
                        }
                        keyboardUp = false;
                    }
                }
            });

            mMainView.addView(mSplashView, 0);
            startLoadingData();
        } else {
            startActivity(new Intent(this, ProfileRegisterActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(mSearchView != null && mSearchView.isSearchOpen()){
            mSearchView.hide(true);
        } else if (drawerResult != null && drawerResult.isDrawerOpen()) {
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
            Profile profile = RealmUtils.getInstance().get(Profile.class);

            profileDrawerItem.withName(profile.getNome());
            if (profile.getImage() != null) {
                profileDrawerItem.withIcon(ImageUtils.getInstance().getRoundedCornerBitmap(profile.getImage()));
            }

            headerResult.updateProfile(profileDrawerItem);
        }
    }

    private void startLoadingData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadingDataEnded();
            }
        }, 2000);
    }

    private void onLoadingDataEnded() {
        replaceFragment(new HomeFragment());
        mSplashView.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onUpdate(float completionFraction) {
            }

            @Override
            public void onEnd() {
                mSplashView = null;
            }
        });
    }

    private void setupNavigationDrawer() {
        profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName(profile.getNome());

        if(profile.getImage() != null){
            profileDrawerItem.withIcon(ImageUtils.getInstance().getRoundedCornerBitmap(profile.getImage()));
        } else {
            profileDrawerItem.withIcon(R.mipmap.ic_launcher);
        }

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(profileDrawerItem)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.drawer_bg)
                .build();

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
                            case PROFILE:
                                Intent intent = new Intent(MainActivity.this, ProfileEditActivity.class);
                                startActivityForResult(intent, PROFILE_EDIT);
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

    public SearchView getSearchView(){
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setVersion(SearchCodes.VERSION_MENU_ITEM);
        mSearchView.setStyle(SearchCodes.STYLE_MENU_ITEM_CLASSIC);
        mSearchView.setTheme(SearchCodes.THEME_LIGHT);

        mSearchView.setVoice(false);
        mSearchView.setDivider(false);
        mSearchView.setHint("Search");
        mSearchView.setHint(R.string.search);
        mSearchView.setHintSize(getResources().getDimension(R.dimen.search_text_medium));
        mSearchView.setAnimationDuration(300);
        mSearchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));

        return mSearchView;
    }
}
