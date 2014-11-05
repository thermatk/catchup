package com.thermatk.android.l.catchup.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thermatk.android.l.catchup.MyNesClient;
import com.thermatk.android.l.catchup.R;
import com.thermatk.android.l.catchup.interfaces.UpdatableFragment;

public class DeadlinesFragment extends Fragment implements UpdatableFragment {

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
