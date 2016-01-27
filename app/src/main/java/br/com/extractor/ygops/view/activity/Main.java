package br.com.extractor.ygops.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.view.ParentActivity;

public class Main extends ParentActivity {

    private Drawer.Result drawerResult;

    private final int HOME = 0;
    private final int PLAYERS = 1;
    private final int DECKS = 2;
    private final int MATCHES = 3;
    private final int CONFIGURATION = 5;
    private final int ABOUT = 6;

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
        drawer.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, IDrawerItem iDrawerItem) {
                Intent intent;
                switch (position){
                    case HOME:
                        break;
                    case PLAYERS:
                        intent = new Intent(Main.this, Players.class);
                        startActivity(intent);
                        break;
                    case DECKS:
                        intent = new Intent(Main.this, Decks.class);
                        startActivity(intent);
                        break;
                    case MATCHES:
                        intent = new Intent(Main.this, Matches.class);
                        startActivity(intent);
                        break;
                    case CONFIGURATION:
                        break;
                    case ABOUT:
                        break;
                }
            }
        });

        drawerResult = drawer.build();
    }

}
