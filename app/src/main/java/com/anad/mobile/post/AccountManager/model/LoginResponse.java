package com.anad.mobile.post.AccountManager.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {

    @SerializedName("IsSuccessful")
    private boolean isSuccessfull;
    @SerializedName("Message")
    private String message;
    @SerializedName("Title")
    private String title;
    @SerializedName("IsConfirmMessage")
    private boolean isConfirmMessage;
    @SerializedName("ConfirmKey")
    private String confirmKey;
    @SerializedName("IdentityManppingInfo")
    private ArrayList identityManppingInfo;
    @SerializedName("ReturnValue")
    private ReturnValue returnValue;


    public boolean isSuccessful() {
        return isSuccessfull;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public boolean isConfirmMessage() {
        return isConfirmMessage;
    }

    public String getConfirmKey() {
        return confirmKey;
    }

    public ArrayList getIdentityManppingInfo() {
        return identityManppingInfo;
    }

    public ReturnValue getReturnValue() {
        return returnValue;
    }

    public class ReturnValue{
        @SerializedName("UserId")
        private long userId;
        @SerializedName("UserName")
        private String userName;
        @SerializedName("PartyAssignCode")
        private String partyAssignCode;
        @SerializedName("PartyAssignTitle")
        private String partyAssignTitle;
        @SerializedName("PartyId")
        private long partyId;
        @SerializedName("CurrentBusinessUnitId")
        private long currentBusinessUnitId;
        @SerializedName("CurrentCultureId")
        private long currentCultureId;

        public long getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getPartyAssignCode() {
            return partyAssignCode;
        }

        public String getPartyAssignTitle() {
            return partyAssignTitle;
        }

        public long getPartyId() {
            return partyId;
        }

        public long getCurrentBusinessUnitId() {
            return currentBusinessUnitId;
        }

        public long getCurrentCultureId() {
            return currentCultureId;
        }
    }
}
