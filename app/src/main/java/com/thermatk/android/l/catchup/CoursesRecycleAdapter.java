package com.thermatk.android.l.catchup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thermatk.android.l.catchup.com.thermatk.android.l.catchup.data.NesCourse;

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
        final NesCourse item = items.get(position);
        viewHolder.courseName.setText(item.name);
        viewHolder.courseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courseIntent = new Intent(mContext, CourseSingle.class);
                courseIntent.putExtra("courseId", item.getId());
                Log.i("CatchUp", item.getId().toString());
                mContext.startActivity(courseIntent);
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