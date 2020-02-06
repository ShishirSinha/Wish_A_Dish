package com.example.wishadish;

import java.io.Serializable;

public class MenuItemClass implements Serializable {

    private String mItemName;
    private int mQuantity;
    private String type;
    private double mCost;
    private String mId;
    private String mUnit;
    private double mGstPercent;

    public MenuItemClass(String mItemName, int mQuantity, String type, double mCost, String mId, String mUnit, double mGstPercent) {
        this.mItemName = mItemName;
        this.mQuantity = mQuantity;
        this.type = type;
        this.mCost = mCost;
        this.mId = mId;
        this.mUnit = mUnit;
        this.mGstPercent = mGstPercent;
    }

    public String getmItemName() {
        return mItemName;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public String getType() {
        return type;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getmCost() {
        return mCost;
    }

    public void setmCost(double mCost) {
        this.mCost = mCost;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmUnit() {
        return mUnit;
    }

    public void setmUnit(String mUnit) {
        this.mUnit = mUnit;
    }

    public double getmGstPercent() {
        return mGstPercent;
    }

    public void setmGstPercent(double mGstPercent) {
        this.mGstPercent = mGstPercent;
    }
}
