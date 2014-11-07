package com.thermatk.android.l.catchup.data;

import com.orm.SugarRecord;

public class NesDeadlines extends SugarRecord<NesDeadlines> {
    public String name;
    public String description;
    boolean isElectronic;
    public NesCourse course;
    int hId;
    long deadline;
    boolean isDone = false;
    String doneLink="";


    public NesDeadlines(){
    }

    public NesDeadlines(String dname, NesCourse nescourse, int hId, String descr, boolean electronic, long dead){
        this.name = dname;
        this.course = nescourse;
        this.hId = hId;
        this.description=descr;
        this.isElectronic = electronic;
        this.deadline = dead;
    }
    public void setDone(String link) {
        this.isDone = true;
        this.doneLink= link;
    }
}
