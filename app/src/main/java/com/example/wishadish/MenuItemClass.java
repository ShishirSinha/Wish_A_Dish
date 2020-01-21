package com.example.wishadish;

public class MenuItemClass {

    private String mItemName;
    private int mQuantity;
    private String type;
    private double mCost;

    public MenuItemClass(String mItemName, int mQuantity, String type, double mCost) {
        this.mItemName = mItemName;
        this.mQuantity = mQuantity;
        this.type = type;
        this.mCost = mCost;
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
}
