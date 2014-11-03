package com.thermatk.android.l.catchup.com.thermatk.android.l.catchup.data;

import com.orm.SugarRecord;

public class NesCourse extends SugarRecord<NesCourse> {
    public String name;
    public int myNesId;


    public NesCourse(){
    }

    public NesCourse(String name, int mn_id){
        this.name = name;
        this.myNesId = mn_id;
    }
}
