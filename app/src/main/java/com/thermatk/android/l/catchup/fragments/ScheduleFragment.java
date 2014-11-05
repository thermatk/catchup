package com.thermatk.android.l.catchup.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thermatk.android.l.catchup.R;
import com.thermatk.android.l.catchup.interfaces.UpdatableFragment;


public class ScheduleFragment extends Fragment implements UpdatableFragment {
    public void updateFragment(){
        final TextView tvInfo = (TextView) getView().findViewById(R.id.textView1);
        tvInfo.setText("UPDATED Schedule");
    }

    @Override
    public void updateContent() {

    }

    public ScheduleFragment() {
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

            }
        });
        return rootView;
    }
}
