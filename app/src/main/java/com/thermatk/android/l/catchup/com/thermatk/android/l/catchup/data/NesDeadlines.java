package com.thermatk.android.l.catchup.com.thermatk.android.l.catchup.data;

import com.orm.SugarRecord;

public class NesDeadlines extends SugarRecord<NesDeadlines> {
    public String name;
    public String description;
    boolean electronic;
    //String date;
    public NesCourse course;
    int hId;


    public NesDeadlines(){
    }

    public NesDeadlines(String name, NesCourse nescourse, int hId){
        this.name = name;
        this.course = nescourse;
        this.hId = hId;
    }
}
