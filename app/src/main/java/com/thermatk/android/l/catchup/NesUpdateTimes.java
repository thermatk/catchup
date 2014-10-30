package com.thermatk.android.l.catchup;

import com.orm.SugarRecord;

class NesUpdateTimes extends SugarRecord<NesUpdateTimes> {
    String type;
    long tableId;
    long lastUpdated;


    public NesUpdateTimes(){
    }

    public NesUpdateTimes(String type, long tableId){
        this.type = type;
        this.tableId = tableId;
    }

    public long setUpdated() {
        lastUpdated = System.currentTimeMillis() / 1000L;
        return lastUpdated;
    }

    public boolean needsUpdate() {
        long currentTime = System.currentTimeMillis() / 1000L;
        return (currentTime-lastUpdated)>1800;
    }

}
