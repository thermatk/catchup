package com.thermatk.android.l.catchup.data;


import com.orm.SugarRecord;

public class NesNearestEvents extends SugarRecord<NesNearestEvents> {
    public String current;

    public NesNearestEvents(){
    }

    public NesNearestEvents(String events){
        this.current = events;
    }
    public void update(String events) {
        this.current = events;
    }
}
