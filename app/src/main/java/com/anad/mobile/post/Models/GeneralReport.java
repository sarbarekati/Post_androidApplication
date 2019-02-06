package com.anad.mobile.post.Models;

import java.util.List;

/**
 * Created by elias.mohammadi on 96/10/26
 */

public class GeneralReport {
     public String OrgId ;
     public String startDate;
     public String endDate;
     public String driverId="0";
     public String minAvgSpeed= "0";
     public String maxAvgSpeed= "0";
     public String minSpeed="0";
     public String maxSpeed= "0";
     public String minLength="0";
     public String maxLength="0";
     public String MaghsadtakhirStart="00:00";
     public String MaghsadtakhirEnd="23:59";
     public String MaghsadTajilStart="00:00";
     public String MaghsadTajilEnd="23:59";
     public String MabdatakhirStart="00:00";
     public String MabdatakhirEnd="23:59";
     public String ModatBargiriStart="00:00";
     public String ModatBargiriEnd="23:59";
     public String MabdaMoghararStart="00:00";
     public String MabdaMoghararEnd="23:59";
     public String MaghsadMoghararStart="00:00";
     public String MaghsadMoghararEnd="23:59";
     public String ZamanMoghararTeyStart="00:00";
     public String ZamanMoghararTeyEnd="23:59";
     public String RoutName="nothing";
     public List<Integer> orgIdsList;
     public String TAG_REPORT;
}
