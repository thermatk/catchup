package com.thermatk.android.l.catchup;


import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.thermatk.android.l.catchup.data.NesCourse;
import com.thermatk.android.l.catchup.fragments.DeadlinesFragment;
import com.thermatk.android.l.catchup.fragments.FilesFragment;
import com.thermatk.android.l.catchup.fragments.GradesFragment;
import com.thermatk.android.l.catchup.fragments.InfoFragment;
import com.thermatk.android.l.catchup.fragments.StudentsFragment;
import com.thermatk.android.l.catchup.interfaces.CallbackListener;


public class CourseSingle extends ActionBarActivity implements CallbackListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    public NesCourse loadedCourse;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_single);

        // Set up the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadedCourse = NesCourse.findById(NesCourse.class, getIntent().getLongExtra("courseId",0L));
        if(loadedCourse != null) {
            setTitle(loadedCourse.name);
        } else {
            finish();
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catch_up_course_single, menu);
        this.menu = menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.menu_refresh) {
            /*viewStartLoading();
            ((UpdatableFragment) getFragmentManager().findFragmentById(R.id.content_main_frame)).updateContent();
            */
            Log.i("CatchUp","refresh XD");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void successCallback(String cbMessage) {

    }

    @Override
    public void failCallback(String cbMessage) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final String titles[] = getResources().getStringArray(R.array.single_tabs_array);
        private final Fragment frags[] = new Fragment[titles.length];

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            frags[0] = new DeadlinesFragment();
            frags[1] = new GradesFragment();
            frags[2] = new FilesFragment();
            frags[3] = new InfoFragment();
            frags[4] = new StudentsFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }
    }
}
