package com.anad.mobile.post.Models;

import java.util.List;

/**
 * Created by public on 2018/01/06.
 */

public class SubTree  {

    private List<Cars> cars;
    private List<Org> orgs;
    private int UserOrgId;
    private int parentId;
    private Org org;

    public int getCountOfCar() {
        return CountOfCar;
    }

    public void setCountOfCar(int countOfCar) {
        CountOfCar = countOfCar;
    }

    private int CountOfCar;



    private Cars car;


    public List<Cars> getCars() {
        return cars;
    }

    public void setCars(List<Cars> cars) {
        this.cars = cars;
    }

    public List<Org> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Org> orgs) {
        this.orgs = orgs;
    }

    public int getUserOrgId() {
        return UserOrgId;
    }

    public void setUserOrgId(int userOrgId) {
        UserOrgId = userOrgId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }


}
