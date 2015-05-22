package com.thermatk.android.l.catchup;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.thermatk.android.l.catchup.fragments.CourseFragment;
import com.thermatk.android.l.catchup.fragments.DefaultFragment;
import com.thermatk.android.l.catchup.fragments.LearnFragment;
import com.thermatk.android.l.catchup.fragments.ScheduleFragment;
import com.thermatk.android.l.catchup.interfaces.CallbackListener;
import com.thermatk.android.l.catchup.interfaces.UpdatableFragment;

import java.util.ArrayList;


public class CatchUpMain extends AppCompatActivity implements CallbackListener {
    private Drawer.Result result = null;

    private CharSequence mTitle;
    private String[] mNavMenuTitles;

    private ProgressBar loadingBar;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_up_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        loadingBar = (ProgressBar) findViewById(R.id.progressBar);
        loadingBar.setVisibility(View.INVISIBLE);

        if (findViewById(R.id.content_main_frame) != null) {

            if (savedInstanceState != null) {

            } else {

                DefaultFragment firstFragment = new DefaultFragment();

                firstFragment.setArguments(getIntent().getExtras());

                getFragmentManager().beginTransaction()
                        .add(R.id.content_main_frame, firstFragment).commit();
            }
        }


        mNavMenuTitles = getResources().getStringArray(R.array.nav_menu_array);

        //////
        ArrayList<IDrawerItem> drItems = new ArrayList<>();

        for(String item : mNavMenuTitles) {
            drItems.add(new PrimaryDrawerItem().withName(item));
        }
        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerItems(drItems)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null && drawerItem instanceof Nameable) {
                            //getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                            selectItem(position);
                        }
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withFireOnInitialOnClick(true)
                .withSavedInstance(savedInstanceState)
                .build();
        /////
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0: fragment = new DefaultFragment();break;
            case 1: fragment = new CourseFragment();break;
            case 2: fragment = new LearnFragment();break;
            case 3: fragment = new ScheduleFragment();
                break;
            default: fragment = new DefaultFragment();
        }

        getFragmentManager().beginTransaction().replace(R.id.content_main_frame, fragment).commit();

        setTitle(mNavMenuTitles[position]);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catch_up_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_refresh) {
            viewStartLoading();
            ((UpdatableFragment) getFragmentManager().findFragmentById(R.id.content_main_frame)).updateContent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void viewStartLoading () {
        loadingBar.setVisibility(View.VISIBLE);
        menu.findItem(R.id.menu_refresh).setVisible(false);
    }
    @Override
    public void viewStopLoading() {
        menu.findItem(R.id.menu_refresh).setVisible(true);
        loadingBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void successCallback(String cbMessage) {
        viewStopLoading();
        ((UpdatableFragment) getFragmentManager().findFragmentById(R.id.content_main_frame)).updateFragment();
        Log.i("CatchUp", cbMessage);
    }

    @Override
    public void failCallback(String cbMessage) {
        viewStopLoading();

        Log.i("CatchUp", cbMessage);

    }

}
