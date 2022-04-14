package com.fauzighozali.mgamobile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetResponseToken {


    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("organization_id")
    @Expose
    private Integer organizationId;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("refreshToken")
    @Expose
    private int refreshToken;

    public Integer getOrganization_id() {
        return organizationId;
    }

    public void setOrganization_id(Integer organization_id) { this.organizationId = organization_id; }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(int refreshToken) {
        this.refreshToken = refreshToken;
    }
}
