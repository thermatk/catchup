package com.thermatk.android.l.catchup.data;


import com.orm.SugarRecord;

public class NesNearestEvents extends SugarRecord<NesNearestEvents> {
    String stringhash;

    public NesNearestEvents(){
    }

    public NesNearestEvents(String stringhash){
        this.stringhash = stringhash;
    }
}
