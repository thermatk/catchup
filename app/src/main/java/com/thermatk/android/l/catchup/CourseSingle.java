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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class CourseSingle extends ActionBarActivity {

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

    NesCourse loadedCourse;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Log.v("TitleAdapter - getPageTitle=", titles[position]);
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Log.v("TitleAdapter - getItem=", String.valueOf(position));
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }
    }


    public static class DeadlinesFragment extends Fragment implements UpdatableFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_course_single, container, false);
            return rootView;
        }

        @Override
        public void updateFragment() {

        }

        @Override
        public void updateContent() {

        }
    }
    public static class GradesFragment extends Fragment implements UpdatableFragment  {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_course_single, container, false);
            return rootView;
        }

        @Override
        public void updateFragment() {

        }

        @Override
        public void updateContent() {

        }
    }
    public static class FilesFragment extends Fragment implements UpdatableFragment  {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_course_single, container, false);
            return rootView;
        }

        @Override
        public void updateFragment() {

        }

        @Override
        public void updateContent() {

        }
    }

    public static class InfoFragment extends Fragment implements UpdatableFragment  {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_course_single, container, false);
            return rootView;
        }

        @Override
        public void updateFragment() {

        }

        @Override
        public void updateContent() {

        }
    }
    public static class StudentsFragment extends Fragment implements UpdatableFragment  {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_course_single, container, false);
            return rootView;
        }

        @Override
        public void updateFragment() {

        }

        @Override
        public void updateContent() {

        }
    }

}
