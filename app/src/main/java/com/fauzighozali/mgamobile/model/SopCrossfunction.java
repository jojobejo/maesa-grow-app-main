package com.fauzighozali.mgamobile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SopCrossfunction {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sop_id")
    @Expose
    private Integer sop_id;
    @SerializedName("crossfunction_id")
    @Expose
    private Integer crossfunction_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("crossfunction")
    private Crossfunction crossfunction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSop_id() {
        return sop_id;
    }

    public void setSop_id(Integer sop_id) {
        this.sop_id = sop_id;
    }

    public Integer getCrossfunction_id() {
        return crossfunction_id;
    }

    public void setCrossfunction_id(Integer crossfunction_id) {
        this.crossfunction_id = crossfunction_id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Crossfunction getCrossfunction() {
        return crossfunction;
    }

    public void setCrossfunction(Crossfunction crossfunction) {
        this.crossfunction = crossfunction;
    }
}
