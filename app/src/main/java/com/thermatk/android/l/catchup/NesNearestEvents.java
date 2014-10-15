package com.thermatk.android.l.catchup;


import com.orm.SugarRecord;

public class NesNearestEvents extends SugarRecord<NesNearestEvents> {
    String title;
    String edition;

    public NesNearestEvents(){
    }

    public NesNearestEvents(String title, String edition){
        this.title = title;
        this.edition = edition;
    }
}
