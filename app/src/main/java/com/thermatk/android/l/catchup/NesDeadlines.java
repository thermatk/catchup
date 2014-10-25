package com.thermatk.android.l.catchup;

import com.orm.SugarRecord;

class NesDeadlines extends SugarRecord<NesDeadlines> {
    String name;
    String description;
    boolean electronic;
    //String date;
    NesCourse course;
    int hId;


    public NesDeadlines(){
    }

    public NesDeadlines(String name, NesCourse nescourse, int hId){
        this.name = name;
        this.course = nescourse;
        this.hId = hId;
    }
}
