package com.thermatk.android.l.catchup;

import com.orm.SugarRecord;

class NesCourse extends SugarRecord<NesCourse> {
    String name;
    int myNesId;


    public NesCourse(){
    }

    public NesCourse(String name, int mn_id){
        this.name = name;
        this.myNesId = mn_id;
    }
}
