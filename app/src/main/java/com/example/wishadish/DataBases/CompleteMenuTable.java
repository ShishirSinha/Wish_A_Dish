package com.example.wishadish.DataBases;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CompleteMenuTable {

    @PrimaryKey
    @NonNull
    private String name;
    private String id;
    private String unit;
    private String rate;
    private String gst_per;
    private String ttype_tag;
    private String geo_tag;
    private String veg;
    private String time_created;
    private String status;

    public CompleteMenuTable(@NonNull String name, String id, String unit, String rate, String gst_per, String ttype_tag, String geo_tag, String veg, String time_created, String status) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.rate = rate;
        this.gst_per = gst_per;
        this.ttype_tag = ttype_tag;
        this.geo_tag = geo_tag;
        this.veg = veg;
        this.time_created = time_created;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getGst_per() {
        return gst_per;
    }

    public void setGst_per(String gst_per) {
        this.gst_per = gst_per;
    }

    public String getTtype_tag() {
        return ttype_tag;
    }

    public void setTtype_tag(String ttype_tag) {
        this.ttype_tag = ttype_tag;
    }

    public String getGeo_tag() {
        return geo_tag;
    }

    public void setGeo_tag(String geo_tag) {
        this.geo_tag = geo_tag;
    }

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
