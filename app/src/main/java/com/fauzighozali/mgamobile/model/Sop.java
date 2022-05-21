package com.fauzighozali.mgamobile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sop {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_id")
    @Expose
    private Integer company_id;
    @SerializedName("organization_id")
    @Expose
    private Integer organization_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("lampiran")
    private List<LampiranSop> lampiranSopList;

    @SerializedName("crossfunction")
    private List<Crossfunction> crossfunctionList;

    public List<Crossfunction> getCrossfunctionList() {
        return crossfunctionList;
    }

    public void setCrossfunctionList(List<Crossfunction> crossfunctionList) {
        this.crossfunctionList = crossfunctionList;
    }

    public List<LampiranSop> getLampiranSopList() {
        return lampiranSopList;
    }

    public void setLampiranSopList(List<LampiranSop> lampiranSopList) {
        this.lampiranSopList = lampiranSopList;
    }

    private LampiranSop lampiranSop;

    public Sop(Integer id, Integer company_id, Integer organization_id, String title, String description, String image, String file, Integer status, LampiranSop lampiranSop) {
        this.id = id;
        this.company_id = company_id;
        this.organization_id = organization_id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.file = file;
        this.status = status;
        this.lampiranSop = lampiranSop;
    }

    public LampiranSop getLampiranSop() {
        return lampiranSop;
    }

    public void setLampiranSop(LampiranSop lampiranSop) {
        this.lampiranSop = lampiranSop;
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

    public Integer getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Integer organization_id) {
        this.organization_id = organization_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
