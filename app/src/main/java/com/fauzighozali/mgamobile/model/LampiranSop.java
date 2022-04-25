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
    @SerializedName("status")
    @Expose
    private Integer status;

    public LampiranSop(Integer id, Integer company_id, Integer sop_id, String name, String file, int status) {
        this.id = id;
        this.company_id = company_id;
        this.sop_id = sop_id;
        this.name = name;
        this.file = file;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
