package com.fauzighozali.mgamobile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LampiranSop {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_id")
    @Expose
    private Integer company_id;
    @SerializedName("sop_id")
    @Expose
    private Integer sop_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("file")
    @Expose
    private String file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getSop_id() {
        return sop_id;
    }

    public void setSop_id(Integer sop_id) {
        this.sop_id = sop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
