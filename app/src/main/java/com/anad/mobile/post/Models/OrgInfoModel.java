package com.anad.mobile.post.Models;

import java.util.List;

/**
 * Created by elias.mohammadi on 2018/02/28
 */

public class OrgInfoModel {
    private List<Org> orgModel;
    private int allCarCount ;

    public List<Org> getOrgModel() {
        return orgModel;
    }

    public void setOrgModel(List<Org> orgModel) {
        this.orgModel = orgModel;
    }

    public int getAllCarCount() {
        return allCarCount;
    }

    public void setAllCarCount(int allCarCount) {
        this.allCarCount = allCarCount;
    }
}
