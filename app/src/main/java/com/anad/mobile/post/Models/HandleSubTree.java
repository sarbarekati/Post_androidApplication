package com.anad.mobile.post.Models;

import android.content.Context;

import com.anad.mobile.post.API.FilterApi;
import com.anad.mobile.post.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elias.mohammadi 96.10.19.
 */

public class HandleSubTree {
    private static HandleSubTree objectHandle = null;
    private Context context;
    private String Url;
    private FilterApi api;
    private Map<Integer, List<Org>> subOrg;

    private List<SubTree> subList;

    private HandleSubTree(Context context) {
        this.context = context;
        api = FilterApi.getInstance(context);
        subOrg = new HashMap<>();

    }

    public static HandleSubTree getInstance(Context context) {
        if (objectHandle == null)
            objectHandle = new HandleSubTree(context);
        return objectHandle;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

  /*  public void getUserAccessByUserNameOrParentIdHandler(String url, final OnGetOrgHandler onGetOrgHandler) {
        api.getUserAccessByUserNameOrOrgId(new FilterApi.OnUserAccessOrgBack() {
            @Override
            public void OnResponse(List<Org> orgs) {
                if (orgs.size() > 0) {
                 List<Org> orgList = new ArrayList<>();
                 orgList.addAll(orgs);
                 onGetOrgHandler.onGetOrg(orgList);

                }
            }
        }, url);

    }*/


  public void getUserAccessByUserNameOrParentIdHandler(String url, final OnGetOrgInfoHandler onGetOrgInfoHandler) {
        api.getUserAccessByUserNameOrOrgId(new FilterApi.OnUserAccessOrgBack() {
            @Override
            public void OnResponse(OrgInfoModel orgInfoModel) {
                onGetOrgInfoHandler.onGetOrg(orgInfoModel);
            }
        },url);

    }


    public void getOrgHandler(String url, final OnGetOrgHandler onGetOrgHandler, final OnGetSubHandler onGetSubHandler) {
        api.getUserSubTrees(new FilterApi.OnGetUserSubTree() {
            @Override
            public void onResponseUserSubTree(List<SubTree> subTrees) {
                if (subTrees.size() > 0) {
                    List<Org> orgList = new ArrayList<>();
                    orgList.addAll(subTrees.get(subTrees.size() - 1).getOrgs());
                    onGetOrgHandler.onGetOrg(orgList);
                    onGetSubHandler.onGetSubHandler(subTrees);
                }

            }
        }, url);
    }

    public void getUserOrgSubTree(final OnGetSubHandler onGetSubHandler, int parentId) {
        String URL = Constants.URL_GET_USER_ORG_BY_PARENT + parentId;
        api.getUserOrgSubTree(new FilterApi.OnGetUserSubTree() {
            @Override
            public void onResponseUserSubTree(List<SubTree> subTrees) {
                subList = subTrees;
                onGetSubHandler.onGetSubHandler(subList);


            }
        }, URL);

    }

    public void getLastPositionOfCar(final OnGetLastPositionCar onGetLastPositionCar, int orgId) {
        String URL = Constants.URL_GET_LAST_POSITION + orgId;
        api.getLastPosition(new FilterApi.OnGetLastPosition() {
            @Override
            public void OnResponse(LastPosition lastPosition) {
                onGetLastPositionCar.onGetLastPosition(lastPosition);
            }
        }, URL);

    }


    public interface OnGetOrgHandler {
        void onGetOrg(List<Org> orgs);
    }
    public interface OnGetOrgInfoHandler {
        void onGetOrg(OrgInfoModel orgInfoModel);
    }

    public interface OnGetSubHandler {
        void onGetSubHandler(List<SubTree> subTrees);
    }

    public interface OnGetDriverHandler {
        void onGetDriverHandler(List<Cars> cars);
    }

    public interface OnGetLastPositionCar {
        void onGetLastPosition(LastPosition lastPosition);
    }

}
