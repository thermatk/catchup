package com.thermatk.android.l.catchup;


import com.orm.SugarRecord;

class NesNearestEvents extends SugarRecord<NesNearestEvents> {
    String stringhash;

    public NesNearestEvents(){
    }

    public NesNearestEvents(String stringhash){
        this.stringhash = stringhash;
    }
}
