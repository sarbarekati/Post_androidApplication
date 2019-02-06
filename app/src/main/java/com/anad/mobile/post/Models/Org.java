package com.anad.mobile.post.Models;

/**
 * Created by public on 2018/01/06
 */

public class Org {
    private int org_Id;
    private String org_name;
    private int org_from;
    private int org_to;
    private int parent_Id;
    private int carCount;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int level;

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }



    public int getOrg_Id() {
        return org_Id;
    }

    public void setOrg_Id(int org_Id) {
        this.org_Id = org_Id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }


    public int getOrg_from() {
        return org_from;
    }

    public void setOrg_from(int org_from) {
        this.org_from = org_from;
    }

    public int getOrg_to() {
        return org_to;
    }

    public void setOrg_to(int org_to) {
        this.org_to = org_to;
    }

    public int getParent_Id() {
        return parent_Id;
    }

    public void setParent_Id(int parent_Id) {
        this.parent_Id = parent_Id;
    }
}
