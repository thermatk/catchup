package com.thermatk.android.l.catchup.com.thermatk.android.l.catchup.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thermatk.android.l.catchup.CatchUpMain;
import com.thermatk.android.l.catchup.CoursesRecycleAdapter;
import com.thermatk.android.l.catchup.MyNesClient;
import com.thermatk.android.l.catchup.com.thermatk.android.l.catchup.data.NesCourse;
import com.thermatk.android.l.catchup.com.thermatk.android.l.catchup.data.NesUpdateTimes;
import com.thermatk.android.l.catchup.R;
import com.thermatk.android.l.catchup.com.thermatk.android.l.catchup.interfaces.UpdatableFragment;

import java.util.List;

public class CourseFragment extends Fragment implements UpdatableFragment {
    RecyclerView mRecyclerView;
    public void updateFragment(){
        CoursesRecycleAdapter mAdapter = new CoursesRecycleAdapter(NesCourse.listAll(NesCourse.class), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        Log.i("CatchUp", "UPDATED COURSE");
    }

    @Override
    public void updateContent() {

        MyNesClient myNes = new MyNesClient(getActivity());
        myNes.getCurrentCourseList();
    }

    public CourseFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        CoursesRecycleAdapter mAdapter = new CoursesRecycleAdapter(NesCourse.listAll(NesCourse.class), getActivity());
        mRecyclerView.setAdapter(mAdapter);


        List<NesUpdateTimes> courseUpdatedL = NesUpdateTimes.find(NesUpdateTimes.class, "type = ?", "COURSELIST");
        if(!courseUpdatedL.isEmpty()) {
            if(courseUpdatedL.get(0).needsUpdate()) {
                ((CatchUpMain)getActivity()).viewStartLoading();
                updateContent();
            } else {
                Log.i("CatchUp", Boolean.toString(courseUpdatedL.get(0).needsUpdate()));
            }
        } else {
            Log.i("CatchUp", "NOT UPDATED IN THE PAST");
            ((CatchUpMain)getActivity()).viewStartLoading();
            updateContent();
        }
        return rootView;
    }
}

