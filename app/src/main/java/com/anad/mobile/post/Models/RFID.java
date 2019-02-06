package com.anad.mobile.post.Models;

import java.util.List;

/**
 * Created by elias.mohammadi  96.11.04.
 */

public class RFID {
    private ReportCreator rfid;
    private List<MiddlePoint> middle;

    public ReportCreator getRfid() {
        return rfid;
    }

    public void setRfid(ReportCreator rfid) {
        this.rfid = rfid;
    }

    public List<MiddlePoint> getMiddle() {
        return middle;
    }

    public void setMiddle(List<MiddlePoint> middle) {
        this.middle = middle;
    }
}
