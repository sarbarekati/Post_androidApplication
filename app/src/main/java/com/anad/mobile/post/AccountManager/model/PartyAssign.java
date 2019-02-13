package com.anad.mobile.post.AccountManager.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class PartyAssign {


    @SerializedName("PartyAssignId")
    @Expose
    private Integer partyAssignId;
    @SerializedName("PartyAssignCode")
    @Expose
    private String partyAssignCode;
    @SerializedName("PartyAssignTitle")
    @Expose
    private String partyAssignTitle;
    @SerializedName("PartyTypeId")
    @Expose
    private Integer partyTypeId;
    @SerializedName("IsOwner")
    @Expose
    private boolean isOwner;
    @SerializedName("ByRef")
    @Expose
    private boolean byRef;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Owner")
    @Expose
    private Integer owner;
    @SerializedName("BusinessUnitId")
    @Expose
    private Integer businessUnitId;
    @SerializedName("IsActive")
    @Expose
    private boolean isActive;
    @SerializedName("PartyTypeTitle")
    @Expose
    private String partyTypeTitle;
    @SerializedName("IsDelete")
    @Expose
    private boolean isDelete;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("BusinessUnitName")
    @Expose
    private String businessUnitName;
    @SerializedName("OwnerTitle")
    @Expose
    private String ownerTitle;


    public void setPartyAssignId(Integer partyAssignId) {
        this.partyAssignId = partyAssignId;
    }

    public void setPartyAssignCode(String partyAssignCode) {
        this.partyAssignCode = partyAssignCode;
    }

    public void setPartyAssignTitle(String partyAssignTitle) {
        this.partyAssignTitle = partyAssignTitle;
    }

    public void setPartyTypeId(Integer partyTypeId) {
        this.partyTypeId = partyTypeId;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public void setByRef(boolean byRef) {
        this.byRef = byRef;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public void setBusinessUnitId(Integer businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPartyTypeTitle(String partyTypeTitle) {
        this.partyTypeTitle = partyTypeTitle;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public void setOwnerTitle(String ownerTitle) {
        this.ownerTitle = ownerTitle;
    }

    public Integer getPartyAssignId() {

        return partyAssignId;
    }

    public String getPartyAssignCode() {
        return partyAssignCode;
    }

    public String getPartyAssignTitle() {
        return partyAssignTitle;
    }

    public Integer getPartyTypeId() {
        return partyTypeId;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public boolean isByRef() {
        return byRef;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOwner() {
        return owner;
    }

    public Integer getBusinessUnitId() {
        return businessUnitId;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getPartyTypeTitle() {
        return partyTypeTitle;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public String getVersion() {
        return version;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public String getOwnerTitle() {
        return ownerTitle;
    }
}
