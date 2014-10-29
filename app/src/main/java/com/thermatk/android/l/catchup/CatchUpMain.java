package com.thermatk.android.l.catchup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class CatchUpMain extends ActionBarActivity implements CallbackListener{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;


    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavMenuTitles;

    private CallbackListener callbackActivity;

    private ProgressBar loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_up_main);
        callbackActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.content_main_frame) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            DefaultFragment firstFragment = new DefaultFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.content_main_frame, firstFragment).commit();
        }


        mNavMenuTitles = getResources().getStringArray(R.array.nav_menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavMenuTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadingBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_refresh).setVisible((!drawerOpen && loadingBar.getVisibility() != View.VISIBLE));
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0: fragment = new DefaultFragment();break;
            case 1: fragment = new CourseFragment();break;
            case 2: fragment = new DefaultFragment();break;
            case 3: fragment = new DefaultFragment();break;
            default: fragment = new DefaultFragment();
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();

        // update selected item and title, then close the drawer

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catch_up_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_refresh) {
            loadingBar.setVisibility(View.VISIBLE);
            item.setVisible(false);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void successCallback(String cbMessage) {
       // Log.i("CatchUp", "MYNES HTML FROM REQUEST" + cbMessage);
       // final TextView tvInfo = (TextView)findViewById(R.id.textView1);
       // tvInfo.setText(cbMessage);
        Fragment f = getFragmentManager().findFragmentById(R.id.content_main_frame);
        if (f instanceof DefaultFragment) {
            ((DefaultFragment) getFragmentManager().findFragmentById(R.id.content_main_frame)).updateFragment();
        }

        if (f instanceof CourseFragment) {
            ((CourseFragment) getFragmentManager().findFragmentById(R.id.content_main_frame)).updateFragment();
        }
        Log.i("CatchUp", cbMessage);
    }

    @Override
    public void failCallback(String cbMessage) {

    }

    public static class DefaultFragment extends Fragment {
        public void updateFragment(){
            final TextView tvInfo = (TextView) getView().findViewById(R.id.textView1);
            tvInfo.setText("UPDATED DEFAULT");

        }

        public DefaultFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_default, container, false);


            final Button butTest2 = (Button)rootView.findViewById(R.id.button2);
            butTest2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    MyNesClient myNes = new MyNesClient(getActivity(), (CallbackListener)getActivity());
                    myNes.getCurrentCourseList();

                }
            });
            return rootView;
        }
    }
    public static class CourseFragment extends Fragment {
        public void updateFragment(){
            final TextView tvInfo = (TextView) getView().findViewById(R.id.textView1);
            tvInfo.setText("UPDATED COURSE");

        }

        public CourseFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_courses, container, false);

            RecyclerView mRecyclerView = (RecyclerView)rootView.findViewById(R.id.list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            CoursesRecycleAdapter mAdapter = new CoursesRecycleAdapter(NesCourse.listAll(NesCourse.class), getActivity());
            mRecyclerView.setAdapter(mAdapter);

            /*final Button butTest2 = (Button)rootView.findViewById(R.id.button2);
            butTest2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    MyNesClient myNes = new MyNesClient(getActivity(), (CallbackListener)getActivity());
                    myNes.getCurrentCourseList();
                    //startActivityForResult(new Intent(v.getContext(), CourseSingle.class),0);

                }
            });
            */
            return rootView;
        }
    }

}
