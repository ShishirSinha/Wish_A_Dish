package com.example.wishadish;

public class CurrentOrderClass {

    private String mOrdernumber;
    private int mOrderAmount;
    private String mDate;
    private boolean mOrderCancelled;

    public CurrentOrderClass(String mOrdernumber, int mOrderAmount, String mDate, boolean mOrderCancelled) {
        this.mOrdernumber = mOrdernumber;
        this.mOrderAmount = mOrderAmount;
        this.mDate = mDate;
        this.mOrderCancelled = mOrderCancelled;
    }

    public String getmOrdernumber() {
        return mOrdernumber;
    }

    public void setmOrdernumber(String mOrdernumber) {
        this.mOrdernumber = mOrdernumber;
    }

    public int getmOrderAmount() {
        return mOrderAmount;
    }

    public void setmOrderAmount(int mOrderAmount) {
        this.mOrderAmount = mOrderAmount;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public boolean ismOrderCancelled() {
        return mOrderCancelled;
    }

    public void setmOrderCancelled(boolean mOrderCancelled) {
        this.mOrderCancelled = mOrderCancelled;
    }
}
