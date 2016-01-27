package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.view.Menu;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import br.com.extractor.ygops.R;

public class Main extends ParentActivity {

    private Drawer.Result drawerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigationDrawer();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupNavigationDrawer(){
        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName("Player");

        AccountHeader headerResult = new AccountHeader();
        headerResult.withActivity(this);
        headerResult.withCompactStyle(false);
        headerResult.addProfiles(profileDrawerItem);
        headerResult.withSelectionListEnabledForSingleProfile(false);

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
        drawer.withDisplayBelowToolbar(false);
        drawer.withTranslucentActionBarCompatibility(false);
        drawer.withTranslucentStatusBar(false);
        drawer.withActionBarDrawerToggleAnimated(true);
        drawer.withAccountHeader(headerResult.build());
        drawer.withDrawerItems(drawerItems);

        drawerResult = drawer.build();
    }

}
