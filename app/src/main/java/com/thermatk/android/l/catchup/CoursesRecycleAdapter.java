package com.thermatk.android.l.catchup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CoursesRecycleAdapter extends RecyclerView.Adapter<CoursesRecycleAdapter.CoursesViewHolder> {
    private List<NesCourse> items;

    private Context mContext;


    public CoursesRecycleAdapter(List<NesCourse> items, Context context) {
        this.items = items;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fragment_courses_row, viewGroup, false);
        return new CoursesViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final CoursesViewHolder viewHolder, int position) {
        NesCourse item = items.get(position);
        viewHolder.courseName.setText(item.name);
        viewHolder.courseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CourseSingle.class));
            }
        });
    }

    public final static class CoursesViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;

        public CoursesViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
        }
    }
}