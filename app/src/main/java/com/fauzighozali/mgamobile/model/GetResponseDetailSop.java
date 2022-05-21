package com.fauzighozali.mgamobile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetResponseDetailSop {

    private Sop data;
    private LampiranSop lampiran;

    public LampiranSop getLampiran() {
        return lampiran;
    }

    public void setLampiran(LampiranSop lampiran) {
        this.lampiran = lampiran;
    }

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Sop getData() {
        return data;
    }

    public void setData(Sop data) {
        this.data = data;
    }
}
